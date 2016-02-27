package uk.ac.gla.terrier.structures;

/** Contains all the information about one entry in the Lexicon. 
  * Created to make thread-safe lookups in the Lexicon easier. */
public class LexiconEntry {

	/** Create an empty LexiconEntry */
	public LexiconEntry(){}

	/** Create a lexicon entry with the following information.
	  * @param t the term 
	  * @param tid the term id
	  * @param n_t the number of documents the term occurs in (document frequency)
	  * @param TF the total count of therm t in the collection
	  */
	public LexiconEntry(String t, int tid, int n_t, int TF)
	{
		this.term =t;
		this.termId = tid;
		this.n_t = n_t;
		this.TF = TF;
	}

	/** increment this lexicon entry by another */
	public void add(LexiconEntry le)
	{
		this.n_t += le.n_t;
		this.TF  += le.TF;
	}

	/** alter this lexicon entry to subtract another lexicon entry */
	public void subtract(LexiconEntry le)
	{
		this.n_t -= le.n_t;
		this.TF  -= le.TF;
	}

	/** the term of this entry */	
	public String term;
	/** the termid of this entry */
	public int termId;
	/** the number of document that this entry occurs in */
	public int n_t;
	/** the total number of occurrences of the term in the index */
	public int TF;
	/** the start offset of the entry in the inverted index */
	public long startOffset;
	/** the start bit offset of the entry in the inverted index */
	public byte startBitOffset;
	/** the end offset of the entry in the inverted index */
	public long endOffset;
	/** the end bit offset of the entry in the inverted index */
	public byte endBitOffset;

	/** returns a string representation of this lexicon entry */	
	public String toString() {
		return term + " " + termId + " " + n_t + " " + TF + " " + startOffset + " " + startBitOffset + " " + endOffset + " " + endBitOffset;
	}
}
