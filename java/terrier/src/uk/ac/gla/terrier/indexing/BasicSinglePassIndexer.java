
package uk.ac.gla.terrier.indexing;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import uk.ac.gla.terrier.structures.Index;
import uk.ac.gla.terrier.structures.LexiconInputStream;
import uk.ac.gla.terrier.structures.LexiconOutputStream;
import uk.ac.gla.terrier.structures.UTFLexiconInputStream;
import uk.ac.gla.terrier.structures.UTFLexiconOutputStream;
import uk.ac.gla.terrier.structures.indexing.DocumentIndexBuilder;
import uk.ac.gla.terrier.structures.indexing.DocumentPostingList;
import uk.ac.gla.terrier.structures.indexing.LexiconBuilder;
import uk.ac.gla.terrier.structures.indexing.singlepass.FieldRunMerger;
import uk.ac.gla.terrier.structures.indexing.singlepass.FieldsMemoryPostings;
import uk.ac.gla.terrier.structures.indexing.singlepass.MemoryPostings;
import uk.ac.gla.terrier.structures.indexing.singlepass.RunsMerger;
import uk.ac.gla.terrier.structures.trees.FieldDocumentTree;
import uk.ac.gla.terrier.structures.trees.FieldDocumentTreeNode;
import uk.ac.gla.terrier.utility.ApplicationSetup;
import uk.ac.gla.terrier.utility.FieldScore;

/**
 * This class indexes a document collection (skipping the direct file construction). It implements a single-pass algorithm,
 * that operates in two phases:<br>
 * First, it traverses the document collection, passes the terms through the TermPipeline and builds an in-memory
 * representation of the posting lists. When it has exhausted the main memory, it flushes the sorted posting to disk, along
 * with the lexicon, and continues traversing the collection.<br>
 * The second phase, merges the sorted runs (with their partial lexicons) in disk to create the final inverted file.
 * This class follows the template pattern, so the main bulk of the code is reused for block (and fields) indexing. There are a few hook methods,
 * that chooses the right classes to instanciate, depending on the indexing options defined.
 * <p>
 * <b>Properties:</b>
 * <ul>
 * <li><tt>memory.reserved</tt> - amount of free memory threshold before a run is committed.</li>
 * <li><tt>memory.heap.usage</tt> - amount of max heap allocated to JVM before a run is committed.</li>
 * <li><tt>docs.check</tt> - how often to check the amount of free memory.</li>
 * </ul> 
 * @author Roi Blanco
 * @version $Revision: 1.3 $
 */

public class BasicSinglePassIndexer extends BasicIndexer{

	/** Current document Id */
	protected int currentId = 0;
	/** Memory threshold. If a memory check falls below this threshold, the postings in memory are flushed to disk */
	protected static final long MEMORY_RESERVED = ApplicationSetup.MEMORY_THRESHOLD_SINGLEPASS;
	/** how much of the maximum allowed heap must be in use before a run can be passed */
	protected static final double MEMORY_HEAP_USAGE_MIN_THRESHOLD = Double.parseDouble(ApplicationSetup.getProperty("memory.heap.usage", "0.85"));
	/** Number of documents read per memory check */
	protected final long docsPerCheck = ApplicationSetup.DOCS_CHECK_SINGLEPASS;
	/** Runtime system JVM running this instance of Terrier */
	protected static final Runtime runtime = Runtime.getRuntime();

	/** Number of documents read since the memory consumption was last checked */
	protected int numberOfDocsSinceCheck = 0;
	/** Number of documents read since the memory runs were last flushed to disk */
	protected int numberOfDocsSinceFlush = 0;
	/** Memory status after flush */
	protected long memoryAfterFlush = -1;
	/** Queue with the file names for the runs in disk */
	protected Queue<String[]> fileNames = new LinkedList<String[]>();
	/** Number of the current Run to be written in disk */
	protected int currentFile = 0;
	/** Structure that keeps the posting lists in memory */
	protected MemoryPostings mp;
	/** Structure for merging the run */
	protected RunsMerger merger;

	/** Number of documents indexed */
	protected int numberOfDocuments = 0;
	/** Number of tokens indexed */
	protected long numberOfTokens = 0;
	/** Number of unique terms indexed */
	protected int numberOfUniqueTerms = 0;
	/** Number of pointers indexed */
	protected long numberOfPointers = 0;
	/** what class should be used to read the generated inverted index? */
	protected String invertedIndexClass = "uk.ac.gla.terrier.structures.InvertedIndex";
	/** what class should be used to read the inverted index as a stream? */
	protected String invertedIndexInputStreamClass = "uk.ac.gla.terrier.structures.InvertedIndexInputStream";
	/**
	 * Constructs an instance of a BasicSinglePassIndexer, using the given path name
	 * for storing the data structures.
	 * @param pathname String the path where the datastructures will be created.
	 */
	public BasicSinglePassIndexer(String pathname, String prefix) {
		super(pathname, prefix);
		//createMemoryPostings();
	}



	@Override
	public void createDirectIndex(Collection[] collections) {
		createInvertedIndex(collections);
	}
	@Override
	public void createInvertedIndex(){}




	/**
	 *  Builds the inverted file and lexicon file for the given collections
	 * Loops through each document in each of the collections,
	 * extracting terms and pushing these through the Term Pipeline
	 * (eg stemming, stopping, lowercase).
	 *  @param collections Collection[] the collections to be indexed.
	 */
	public void createInvertedIndex(Collection[] collections) {
		logger.info("Creating IF (no direct file)..");
		long startCollection, endCollection;
		fileNames = new LinkedList<String[]>();	
		numberOfDocuments = currentId = numberOfDocsSinceCheck = numberOfDocsSinceFlush = numberOfUniqueTerms = 0;
		numberOfTokens = numberOfPointers = 0;
		createMemoryPostings();
		currentIndex = Index.createNewIndex(path, prefix);
		docIndexBuilder = new DocumentIndexBuilder(currentIndex);
		MAX_DOCS_PER_BUILDER = Integer.parseInt(ApplicationSetup.getProperty("indexing.max.docs.per.builder", "0"));
		final boolean boundaryDocsEnabled = BUILDER_BOUNDARY_DOCUMENTS.size() > 0;
		final int collections_length = collections.length;
		boolean stopIndexing = false;
		System.gc();
		memoryAfterFlush = runtime.freeMemory();
		logger.debug("Starting free memory: "+memoryAfterFlush/1000000+"M");

		for(int collectionNo = 0; ! stopIndexing && collectionNo < collections_length; collectionNo++)
		{
			Collection collection = collections[collectionNo];
			startCollection = System.currentTimeMillis();
			while(collection.nextDocument())
			{
				/* get the next document from the collection */
				final String docno = collection.getDocid();
				Document doc = collection.getDocument();
				if (doc == null)
					continue;
				numberOfDocuments++;
				/* setup for parsing */
				createDocumentPostings();

				String term; //term we're currently processing
				numOfTokensInDocument = 0;
				//get each term in the document
				while (!doc.endOfDocument()) {

					if ((term = doc.getNextTerm())!=null && !term.equals("")) {
						termFields = doc.getFields();
						/* pass term into TermPipeline (stop, stem etc) */
						pipeline_first.processTerm(term);
						/* the term pipeline will eventually add the term to this object. */
					}
					if (MAX_TOKENS_IN_DOCUMENT > 0 &&
							numOfTokensInDocument > MAX_TOKENS_IN_DOCUMENT)
						break;
				}
				//if we didn't index all tokens from document,
				//we need to get to the end of the document.
				while (!doc.endOfDocument())
					doc.getNextTerm();
				/* we now have all terms in the DocumentTree, so we save the document tree */
				if (termsInDocument.getDocumentLength() == 0)
				{	/* this document is empty, add the minimum to the document index */
					// Nothing in the ifile
					indexEmpty(docno);
				}
				else
				{	/* index this document */
					numberOfTokens += numOfTokensInDocument;
					indexDocument(docno, numOfTokensInDocument, termsInDocument);
				}

				if (MAX_DOCS_PER_BUILDER>0 && numberOfDocuments >= MAX_DOCS_PER_BUILDER)
				{
					stopIndexing = true;
					break;
				}

				if (boundaryDocsEnabled && BUILDER_BOUNDARY_DOCUMENTS.contains(docno))
				{
					logger.warn("Document "+docno+" is a builder boundary document. Boundary forced.");
					stopIndexing = true;
					break;
				}
				termsInDocument.clear();
			}
			try{
				mp.finish(finishMemoryPosting());
			}catch(Exception e){
				e.printStackTrace();
			}
			endCollection = System.currentTimeMillis();
			long partialTime = (endCollection-startCollection)/1000;
			logger.info("Collection #"+collectionNo+ " took "+partialTime+ " seconds to build the runs for "+numberOfDocuments+" documents\n");
			logger.info("Merging "+fileNames.size()+" runs...");
			startCollection = System.currentTimeMillis();
			performMultiWayMerge();
			docIndexBuilder.finishedCollections();
			endCollection = System.currentTimeMillis();
			logger.info("Collection #"+collectionNo+" took "+((endCollection-startCollection)/1000)+" seconds to merge\n ");
			logger.info("Collection #"+collectionNo+" total time "+( (endCollection-startCollection)/1000+partialTime));
			long secs = ((endCollection-startCollection)/1000);
			if (secs > 3600)
                 logger.info("Rate: "+((double)numberOfDocuments/((double)secs/3600.0d))+" docs/hour");
		}
		//createStatistics();
		currentIndex.flush();
		finishedInvertedIndexBuild();
	}

	/**
	 * Adds the terms and possibly the field information of a document in
	 * the current lexicon and in the direct index. It also updates the document index.
	 * @param docno String the identifier of the document to index.
	 * @param numOfTokensInDocument int the number of indexed tokens in the document.
	 * @param termsInDocument FieldDocumentTree the temrs of the document to add to
	 *		the inverted file.
	 */
	protected void indexDocument(String docno, int numOfTokensInDocument, FieldDocumentTree termsInDocument) {
		FieldDocumentTreeNode[] terms = termsInDocument.toArray();
		if (terms.length > 1) {
			numberOfDocsSinceCheck++; numberOfDocsSinceFlush++;
			if(docsPerCheck == numberOfDocsSinceCheck){
				long memoryII = runtime.freeMemory();
				logger.debug("Memory Check ("+memoryII/1000000+"M)");
				if(memoryII < MEMORY_RESERVED){
					logger.debug("Free memory ("+memoryII/1000000+"M) below threshold ("+MEMORY_RESERVED/1000000+"M): "
							+"indexing "+numberOfDocsSinceFlush +" docs used "+(memoryAfterFlush - memoryII)/1000000+"M of memory, terms="+mp.getSize()+" pointers="+mp.getPointers());
					try {
						mp.finish(finishMemoryPosting());
						createMemoryPostings();
						System.gc();
						logger.debug("Memory After release "+runtime.freeMemory()/1000000+"M)");
					} catch (IOException ioe) {
						logger.error("Failed writting run in doc "+docno, ioe);
					}
					numberOfDocsSinceFlush = 0;
				}
				numberOfDocsSinceCheck = 0;
			}
			try{
				mp.addTerms(terms, currentId);
				docIndexBuilder.addEntryToBuffer(docno, numOfTokensInDocument);
			}catch(IOException ioe){
				logger.error("Failed to index "+docno, ioe);
			}
			currentId++;
		}
	}

	/**
	 * Adds the terms and possibly the field information of a document in
	 * the current lexicon and in the direct index. It also updates the document index.
	 * @param docno String the identifier of the document to index.
	 * @param numOfTokensInDocument int the number of indexed tokens in the document.
	 * @param termsInDocument DocumentPostingList the terms of the document to add to
	 *		the inverted file.
	 */
	protected void indexDocument(String docno, int numOfTokensInDocument, DocumentPostingList termsInDocument)
	{
		if (termsInDocument.getDocumentLength() > 0) {
			numberOfDocsSinceCheck++; numberOfDocsSinceFlush++;
			if(docsPerCheck == numberOfDocsSinceCheck){
				long memoryFree = runtime.freeMemory();
				/* For some JVMs,  runtime.totalMemory() = Long.MAX_VALUE as the memory usage of java is not suppressed
				 * in this scenario, assume that Java has grown to full adult size */
				final double memoryAllocated = (runtime.maxMemory() == Long.MAX_VALUE )
						? 1.0d
						: (double)(runtime.totalMemory()) / (double)(runtime.maxMemory());
				logger.debug("Memory Check Free: "+memoryFree/1000000+"M, allocated "+(memoryAllocated*100)+"%");


				if(memoryAllocated > MEMORY_HEAP_USAGE_MIN_THRESHOLD && memoryFree < MEMORY_RESERVED){
					logger.debug("Free memory ("+memoryFree/1000000+"M) below threshold ("+MEMORY_RESERVED/1000000+"M): "
						+"indexing "+numberOfDocsSinceFlush +" docs used "+(memoryAfterFlush - memoryFree)/1000000+"M of memory, terms="+mp.getSize()+" pointers="+mp.getPointers());
					try {
						mp.finish(finishMemoryPosting());
					} catch (IOException ioe) {
						logger.error("Failed writing run at doc "+docno, ioe);
					} catch (Error e) {
						logger.error("Error writing run out at doc "+docno, e);
					}
					createMemoryPostings();
					System.gc();
					memoryAfterFlush = runtime.freeMemory();
					logger.debug("Free memory After release "+(memoryAfterFlush/1000000)+"M, "
						+(memoryAfterFlush - memoryFree)/1000000+"M freed");
					numberOfDocsSinceFlush = 0;
				}
				numberOfDocsSinceCheck = 0;
			}
			try{
				mp.addTerms(termsInDocument, currentId);
				docIndexBuilder.addEntryToBuffer(docno, numOfTokensInDocument);
			}catch(IOException ioe){
				logger.error("Failed to index "+docno, ioe);
			}
			currentId++;
		}
	}

	/**
	 * Adds the name of the current run + partial lexicon to be flushed in disk.
	 * @return the two dimensional String[] array with the names of the run and partial lexicon to write.
	 */
	protected String[] finishMemoryPosting(){
		String[] names = new String[2];
		names[0] = fileNameNoExtension + "Run."+(currentFile);
		names[1] = fileNameNoExtension + "Run."+(currentFile++)+".str";
		fileNames.add(names);
		return names;
	}

	/**
	 * Uses the merger class to perform a k multiway merge
	 * in a set of previously written runs.
	 * The file names and the number of runs are given by the private queue
	 */
	public void performMultiWayMerge(){
		String[][] fileNames = getFileNames();
		LexiconOutputStream lexStream = createLexiconOutputStream(path, prefix);
		try{
			if (useFieldInformation)
				createFieldRunMerger();
			else
				createRunMerger();
			merger.beginMerge(fileNames, fileNames.length, path + ApplicationSetup.FILE_SEPARATOR + prefix +  ApplicationSetup.IFSUFFIX);
			while(!merger.isDone()){
				merger.mergeOne(lexStream);
			}
			merger.endMerge(lexStream);
			lexStream.close();
			numberOfUniqueTerms = merger.getNumberOfTerms();
			numberOfPointers = merger.getNumberOfPointers();
			// Delete the runs files
			for(int i = 0; i < fileNames.length; i++){
				if(!new File(fileNames[i][0]).delete())
					logger.warn("Unable to delete : "+fileNames[i][0]);
				if(!new File(fileNames[i][1]).delete())
					logger.warn("Unable to delete : "+fileNames[i][1]);
			}
			currentIndex.setIndexProperty("num.Terms", ""+numberOfUniqueTerms);
			currentIndex.setIndexProperty("num.Pointers", ""+numberOfPointers);
			currentIndex.setIndexProperty("num.Tokens", ""+numberOfTokens);
			createLexicon(numberOfUniqueTerms);
			currentIndex.addIndexStructure(
					"inverted",
					invertedIndexClass,
					"uk.ac.gla.terrier.structures.Lexicon,java.lang.String,java.lang.String",
					"lexicon,path,prefix");
			currentIndex.addIndexStructureInputStream(
                    "inverted",
                    invertedIndexInputStreamClass,
                    "java.lang.String,java.lang.String,uk.ac.gla.terrier.structures.LexiconInputStream",
                    "path,prefix,lexicon-inputstream");
			currentIndex.setIndexProperty("num.inverted.fields.bits", ""+FieldScore.FIELDS_COUNT );
		}catch(IOException e){
			logger.error("Problem in performMultiWayMerge", e);
		}
	}

	/**
	 * @return the String[][] structure with the name of the runs files and partial lexicons.
	 */
	protected String[][] getFileNames(){
		String[][] files =  new String[fileNames.size()][2];
		int i = 0;
		while(!fileNames.isEmpty()){
			files[i++] = fileNames.poll();
		}
		return files;
	}

	/**
	 * Hook method that creates the right LexiconBuilder instance
	 * @throws IOException
	 */
	protected void createLexicon(int numberOfEntries) throws IOException{
		final LexiconInputStream lis = createLexiconInputStream(path, prefix);
		LexiconBuilder.createLexiconIndex(lis, numberOfEntries, lis.getEntrySize(), path, prefix );
		currentIndex.addIndexStructure(
				"lexicon",
				UTFIndexing ? "uk.ac.gla.terrier.structures.UTFLexicon" :"uk.ac.gla.terrier.structures.Lexicon" );
		currentIndex.addIndexStructureInputStream(
				"lexicon",
				UTFIndexing ? "uk.ac.gla.terrier.structures.UTFLexiconInputStream" :"uk.ac.gla.terrier.structures.LexiconInputStream");
	}

	/**
	 * Hook method that creates the suitable statistics file (calls CollectionStatistics.createCollectionStatistics)
	 */
	public void createStatistics(){
		/*CollectionStatistics.createCollectionStatistics(
				numberOfDocuments, numberOfTokens,
				numberOfUniqueTerms,numberOfPointers,
				new String[] {"uk.ac.gla.terrier.structures.Lexicon",
						"uk.ac.gla.terrier.structures.DocumentIndexEncoded",
						"uk.ac.gla.terrier.structures.DirectIndex",
				"uk.ac.gla.terrier.structures.InvertedIndex"}
		);	*/
	}

	/**
	 * Hook method that creates the rigth LexiconOutputStream instance.
 	 * @param name filename for the lexicon file.
	 */
	protected LexiconOutputStream createLexiconOutputStream(String path, String prefix){
		return UTFIndexing ? new UTFLexiconOutputStream(path, prefix) : new LexiconOutputStream(path, prefix);
	}

	/**
	 * Hook method that creates the rigth LexiconOutputStream instance.
 	 * @param name filename for the lexicon file.
	 */
	protected LexiconInputStream createLexiconInputStream(String path, String prefix){
		return UTFIndexing ? new UTFLexiconInputStream(path, prefix) : new LexiconInputStream(path, prefix);
	}

	/**
	 * Hook method that creates a FieldRunMerger instance
	 * @throws IOException if an I/O error occurs.
	 */
	protected void createFieldRunMerger() throws IOException{
		merger = new FieldRunMerger();
	}


	/**
	 * Hook method that creates a RunsMerger instance
	 * @throws IOException if an I/O error occurs.
	 */
	protected void createRunMerger() throws IOException{
		merger = new RunsMerger();
	}

	/**
	 * Hook method that creates the right type of MemoryPostings class.
	 */
	protected void createMemoryPostings(){
		if (useFieldInformation)
			mp = new FieldsMemoryPostings();
		else
			mp = new MemoryPostings();
	}

	/** Adds an entry to document index for empty document with the 
	  * specified docno, only if IndexEmptyDocuments is set to true.
	  * @param docno Document number of document to index
	  * @see uk.ac.gla.terrier.indexing.Indexer#indexEmpty(String) indexEmpty in Indexer
	  */
	protected void indexEmpty(final String docno)
	{
		/* add doc to documentindex, even though it's empty */
		if(IndexEmptyDocuments)
			try
			{
				logger.warn("Adding empty document "+docno);
				docIndexBuilder.addEntryToBuffer(docno, 0);
				currentId++;
			}
			catch (IOException ioe)
			{
				logger.error("Failed to index empty document "+docno, ioe);
			}
	}


}
