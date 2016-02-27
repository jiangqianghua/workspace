package uk.ac.gla.terrier.structures.indexing.singlepass;

import java.io.IOException;
import uk.ac.gla.terrier.compression.BitIn;
import uk.ac.gla.terrier.compression.BitOut;

/** Class holding the information for a posting list read
 * from a previously written run at disk. Used in the merging phase of the Single pass inversion method.
 * This class knows how to append itself to a {@link uk.ac.gla.terrier.compression.BitOut} and it
 * represents the simpler class of posting <code>(TF, df, [docid, tf])</code>
 * @author Roi Blanco
 *
 */
public class SimplePostingInRun {
	
	/** source for postings to be read from */
	protected BitIn postingSource;
	/** tf for the current posting */
	protected int termTF;
	/** Current term */
	protected String term;	
	/** Document frequency */
	protected int termDf;	
	
	/**
	 * Constructor for the class.
	 * @param maxSize Max number of elements for the posting list.
	 */
	public SimplePostingInRun(int maxSize){
		//docs = new int[maxSize];
		//tfs = new int[maxSize];
		termTF = 0;
	}
	
	/**
	 * @return the document frequency for the term.
	 */
	public int getDf() {
		return termDf;
	}

	/**
	 * Setter for the document frequency.
	 * @param df int with the new document frequency.
	 */
	public void setDf(int df) {
		this.termDf = df;
	}

	/**
	 * @return The term String in this posting list.
	 */
	public String getTerm() {
		return term;
	}	
	
	/**
	 * Setter for the term.
	 * @param term String containing the term for this posting list.
	 */
	public void setTerm(String term) {
		this.term = term;
	}
	
	/**
	 * @return the term frequency.
	 */
	public int getTF() {
		return termTF;
	}
	
	/**
	 * Setter for the term frequency.
	 * @param tf the new term frequency.
	 */
	public void setTF(int tf) {
		this.termTF = tf;
	}
	
	/** Set where the postings should be read from */
	public void setPostingSource(BitIn source)
	{
		postingSource = source;
	}
	
	/**
	 * Writes the document data of this posting to a {@link uk.ac.gla.terrier.compression.BitOut} 
	 * It encodes the data with the right compression methods.
	 * The stream is written as <code>d1, idf(d1) , d2 - d1, idf(d2)</code> etc.
	 * @param bos BitOut to be written.
	 * @param last int representing the last document written in this posting.
	 * @return The last posting written.
	 */
	public int append(BitOut bos, int last)  throws IOException{
		int current = -1;
		for(int i = 0; i < termDf; i++){
			final int docid = postingSource.readGamma() + current;
			//System.err.println("posting "+i + " docid="+docid + " offset="+postingSource.getByteOffset());
			bos.writeGamma(docid - last);
			bos.writeUnary(postingSource.readGamma());
			current = last = docid;		
		}
		try{
			postingSource.align();
		}catch(Exception e){
			// last posting
		}
		return last;
	}
}
