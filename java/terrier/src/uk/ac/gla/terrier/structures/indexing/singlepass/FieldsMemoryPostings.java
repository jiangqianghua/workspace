package uk.ac.gla.terrier.structures.indexing.singlepass;

import java.io.IOException;

import uk.ac.gla.terrier.structures.indexing.DocumentPostingList;
import uk.ac.gla.terrier.structures.trees.FieldDocumentTreeNode;
/**
 * Class for handling posting lists containing field information in memory while indexing.
 * @author Roi Blanco
 *
 */
public class FieldsMemoryPostings extends MemoryPostings{
	
	/**
	 * Add the terms in a FieldDocumentTreeNode to the postings in memory.
	 * @param terms FieldDocumentTreeNode containing the term information.
	 * @param currentId Current document Identifier. 
	 * @throws IOException if an I/O error occurs.
	 */
	public void addTerms(FieldDocumentTreeNode[] terms, int currentId) throws IOException{
		for (int termNo = 0; termNo < terms.length; termNo++) {
			add(terms[termNo].term,currentId,terms[termNo].frequency, terms[termNo].getFieldScore());					
		}	
	}
	
	public void addTerms(DocumentPostingList docPostings, int docid) throws IOException{
		for (String term : docPostings.termSet())
			add(term, docid, docPostings.getFrequency(term), docPostings.getFields(term));
	}
	
	/**
	 * Adds an occurrence of a term in a document to the posting in memory.
	 * @param term String representing the term.
	 * @param doc int containing the document identifier.
	 * @param frequency int containing the frequency of the term in the document.
	 * @param fieldScore int containing the field score for the term in the document.
	 * @throws IOException if an I/O error occurs.
	 */
	public void add(String term, int doc, int frequency, int fieldScore) throws IOException{
		FieldPosting post;
		if((post = (FieldPosting) postings.get(term)) != null) {						
			post.insert(doc, frequency, fieldScore);
			int tf = post.getTF();
			// Update the max size
			if(maxSize < tf) maxSize = tf; 
		}
		else{
			post = new FieldPosting();
			post.writeFirstDoc(doc, frequency, fieldScore);			
			postings.put(term,post);
		}
		numPointers++;
	}	
}
