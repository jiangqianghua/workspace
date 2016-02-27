package uk.ac.gla.terrier.structures.indexing.singlepass;

import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import org.apache.log4j.Logger;

import uk.ac.gla.terrier.structures.indexing.DocumentPostingList;
import uk.ac.gla.terrier.structures.trees.FieldDocumentTreeNode;

/**
 * Class for handling Simple posting lists in memory while indexing.
 * @author Roi Blanco
 */
public class MemoryPostings {
	/** logger to use in this class */
	protected static Logger logger = Logger.getRootLogger(); 
	
	/** Hashmap indexed by the term, containing the posting lists*/
	protected HashMap<String, Posting> postings = new HashMap<String, Posting>();
	/** The number of documents for any term in this run */
	protected int maxSize = 1;
	/** Number of pointers (<term,document> tuples in memory in this run. */
	protected long numPointers = 0;
	
	/**
	 * Add the terms in a FieldDocumentTreeNode to the postings in memory.
	 * @param terms FieldDocumentTreeNode containing the term information.
	 * @param currentId Current document Identifier. 
	 * @throws IOException if an I/O error occurs.
	 */
	public void addTerms(FieldDocumentTreeNode[] terms, int currentId) throws IOException {
		for (int termNo = 0; termNo < terms.length; termNo++) {
			add(terms[termNo].term,currentId,terms[termNo].frequency);					
		}	
	}
	
	/**
	 * Add the terms in a DocumentPostingList to the postings in memory.
	 * @param docPostings DocumentPostingList containing the term information for the denoted document.
	 * @param docid Current document Identifier. 
	 * @throws IOException if an I/O error occurs.
	 */
	public void addTerms(DocumentPostingList docPostings, int docid) throws IOException {
		for (String term : docPostings.termSet())
			add(term, docid, docPostings.getFrequency(term));
	}
	
	/**
	 * Adds an occurrence of a term in a document to the posting in memory.
	 * @param term String representing the term.
	 * @param doc int containing the document identifier.
	 * @param frequency int containing the frequency of the term in the document.
	 * @throws IOException if an I/O error occurs.
	 */
	public void add(String term, int doc, int frequency) throws IOException{
		Posting post;
		numPointers++;
		if((post = postings.get(term)) != null) {						
			post.insert(doc, frequency);
			
			final int df = post.getDocF();
			if(df > maxSize) maxSize = df; 
		}
		else{	
			post = new Posting();
			post.writeFirstDoc(doc, frequency);			
			postings.put(term, post);
		}
	}
	
	/**
	 * Triggers the writting of the postings in memory to disk.
	 * @param file name of the file to write the postings.
	 * @throws IOException if an I/O error occurs.
	 */
	public void finish(String[] file) throws IOException{	
		//if(postings.size() != 0){
			logger.debug("Writing run "+file[0]);
			TreeMap<String, Posting> sortedPostings = new TreeMap<String, Posting>(postings);
			writeToDisk(file[0], file[1], sortedPostings);
			logger.debug(" done");
		//}
	}
	
	/** Returns the number of terms in this posting list. 
	 * @return the number of posting lists in memory.
	 */
	public int getSize(){
		return postings.size();
	}
	
	/** Returns the number of pointers in this posting list. Pointers
	 * are unique (term,docid) tuples.
	 * @return the number of pointers in memory.
	 */
	public long getPointers()
	{
		return numPointers;
	}
	
	/**
	 * Writes the contents of the postings in memory to disk.
	 * @param file String containing the filename to write the postings.
	 * @param termsFile String containing the filename to write the terms.
	 * @param postings the TreeMap<String,Posting> containing the posting lists in memory.
	 * @throws IOException if an I/O error occurs.
	 */
	private void writeToDisk(String file, String termsFile, TreeMap<String, Posting> postings) throws IOException{
		final RunWriter writer = new RunWriter(file, termsFile);
		if (postings.size() != 0){
			writer.beginWrite(maxSize, postings.size());
		
			for( Entry<String,Posting> entry : postings.entrySet())
			{
				writer.writeTerm(entry.getKey(), entry.getValue());					
			}
		}
		writer.finishWrite();
	}
}

