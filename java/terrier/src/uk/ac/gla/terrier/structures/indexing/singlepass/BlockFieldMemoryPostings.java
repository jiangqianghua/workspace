package uk.ac.gla.terrier.structures.indexing.singlepass;

import java.io.IOException;

import uk.ac.gla.terrier.structures.indexing.BlockDocumentPostingList;
import uk.ac.gla.terrier.structures.indexing.DocumentPostingList;
import uk.ac.gla.terrier.structures.trees.BlockFieldDocumentTreeNode;
/**
 * Class for handling posting lists containing block and field information in memory while indexing.
 * @author Roi Blanco
 *
 */
public class BlockFieldMemoryPostings extends BlockMemoryPostings{
	
	public void addTerms(DocumentPostingList _docPostings, int docid) throws IOException {
		BlockDocumentPostingList docPostings = (BlockDocumentPostingList)  _docPostings;
		for (String term : docPostings.termSet())
			add(term, docid, docPostings.getFrequency(term) , docPostings.getFields(term), docPostings.getBlocks(term));
	}
	
	/**
	 * Adds an occurrence of a term in a document to the posting in memory.
	 * @param term String representing the term.
	 * @param doc int containing the document identifier.
	 * @param frequency int containing the frequency of the term in the document.
	 * @param blockTree int BlockFieldDocumentTreeNode block information the term in the document.
	 * @throws IOException if an I/O error occurs.
	 */
	public void add(String term, int doc, int frequency, BlockFieldDocumentTreeNode blockTree) throws IOException{
		BlockFieldPosting post;		
		
		if((post =(BlockFieldPosting) postings.get(term)) != null) {						
			post.insert(doc, frequency, blockTree);
			int tf = post.getTF();			
			if(maxSize < tf) maxSize = tf; 
		}
		else{
			post = new BlockFieldPosting();
			post.writeFirstDoc(doc, frequency, blockTree);			
			postings.put(term,post);
		}
		numPointers++;
	}	
	
	public void add(String term, int doc, int frequency, int fieldScore, int[] blockids)  throws IOException{
		BlockFieldPosting post;	
		if((post =(BlockFieldPosting) postings.get(term)) != null) {						
			post.insert(doc, frequency, fieldScore, blockids);
			int tf = post.getTF();			
			if(maxSize < tf) maxSize = tf; 
		}
		else{
			post = new BlockFieldPosting();
			post.writeFirstDoc(doc, frequency, fieldScore, blockids);			
			postings.put(term,post);
		}
		numPointers++;
	}
}