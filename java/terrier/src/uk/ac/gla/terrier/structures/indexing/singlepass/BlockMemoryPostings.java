package uk.ac.gla.terrier.structures.indexing.singlepass;

import java.io.IOException;

import uk.ac.gla.terrier.structures.indexing.BlockDocumentPostingList;
import uk.ac.gla.terrier.structures.indexing.DocumentPostingList;
import uk.ac.gla.terrier.structures.trees.BlockFieldDocumentTreeNode;
import uk.ac.gla.terrier.structures.trees.FieldDocumentTreeNode;

/**
 * Class for handling posting lists containing block information in memory while indexing.
 * @author Roi Blanco
 *
 */
public class BlockMemoryPostings extends MemoryPostings{
	/**
	 * Add the terms in a FieldDocumentTreeNode to the postings in memory.
	 * @param terms FieldDocumentTreeNode containing the term information.
	 * @param currentId Current document Identifier. 
	 * @throws IOException if an I/O error occurs.
	 */
	public void addTerms(FieldDocumentTreeNode[] terms, int currentId) throws IOException{
		for (int termNo = 0; termNo < terms.length; termNo++) {
			add(terms[termNo].term,currentId,terms[termNo].frequency, ((BlockFieldDocumentTreeNode[])terms)[termNo]);					
		}	
	}
	
	public void addTerms(DocumentPostingList _docPostings, int docid) throws IOException {
		BlockDocumentPostingList docPostings = (BlockDocumentPostingList)  _docPostings;
		for (String term : docPostings.termSet())
			add(term, docid, docPostings.getFrequency(term), docPostings.getBlocks(term));
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
		BlockPosting post;		
		
		if((post =(BlockPosting) postings.get(term)) != null) {						
			post.insert(doc, frequency, blockTree);
			int tf = post.getTF();
			// Update the max size
			if(maxSize < tf) maxSize = tf; 
		}
		else{
			post = new BlockPosting();
			post.writeFirstDoc(doc, frequency, blockTree);			
			postings.put(term,post);
		}
		numPointers++;
	}	
	
	public void add(String term, int doc, int frequency, int[] blocks) throws IOException{
		BlockPosting post;
		
		if((post =(BlockPosting) postings.get(term)) != null) {						
			post.insert(doc, frequency, blocks);
			int tf = post.getTF();
			// Update the max size
			if(maxSize < tf) maxSize = tf; 
		}
		else{
			post = new BlockPosting();
			//if (term.equals("abat"))
			//{
			//	System.err.println("term=abat tf="+frequency+ " blockCount="+blocks.length);
			//}
			post.writeFirstDoc(doc, frequency, blocks);			
			postings.put(term,post);
		}
		numPointers++;
	}	
}
