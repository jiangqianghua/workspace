package uk.ac.gla.terrier.structures.indexing.singlepass;

import java.io.IOException;

import uk.ac.gla.terrier.utility.FieldScore;

/** Class holding the information for a posting list read
 * from a previously written run at disk. Used in the merging phase of the Single pass inversion method.
 * This class knows how to append itself to a {@link uk.ac.gla.terrier.compression.BitOutputStream} and it
 * represents a posting with field information <code>(tf, df, [docid, idf, fieldScore])</code>
 * @author Roi Blanco
 *
 */
public class FieldPosting extends Posting{
	/** The number of different fields that are used for indexing field information.*/	
	protected static final int fieldTags = FieldScore.FIELDS_COUNT;
	/**
	 * Writes the first document in the posting list.
	 * @param doc the document identifier.
	 * @param frequency the frequency of the term in the document.
	 * @param fieldScore field score for the term in the document.
	 * @throws IOException if an I/O error ocurrs.
	 */	
	public void writeFirstDoc(final int doc, final int frequency, final int fieldScore) throws IOException{
		writeFirstDoc(doc, frequency);
		docIds.writeBinary(fieldTags, fieldScore);
	}
	
	/**
	 * Inserts a new document in the posting list. Document insertions must be done
	 * in order.  
	 * @param doc the document identifier.
	 * @param freq the frequency of the term in the document.
	 * @param fieldScore field score for the term in the document.
	 * @return the updated term frequency.
	 * @throws IOException if and I/O error occurs.
	 */
	public int insert(final int doc, final int freq, final int fieldScore) throws IOException{		
	  int c = insert(doc, freq);
	  docIds.writeBinary(fieldTags, fieldScore);
	  return c;
	}
}