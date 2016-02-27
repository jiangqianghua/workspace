package uk.ac.gla.terrier.structures.indexing;

import gnu.trove.TObjectIntHashMap;
import gnu.trove.TObjectIntProcedure;
import uk.ac.gla.terrier.sorting.HeapSortInt;
import uk.ac.gla.terrier.utility.ApplicationSetup;
import uk.ac.gla.terrier.utility.TermCodes;
/** Represents the postings of one document. Uses HashMaps internally.
  * <p>
  * <b>Properties:</b><br>
  * <ul><li><tt>indexing.avg.unique.terms.per.doc</tt> - number of unique terms per doc on average, used to tune the initial 
  * size of the haashmaps used in this class.</li></ul>
  */
public class DocumentPostingList {
	/** number of unique terms per doc on average, used to tune the initial size of the haashmaps used in this class. */
	protected static final int AVG_DOCUMENT_UNIQUE_TERMS =
		Integer.parseInt(ApplicationSetup.getProperty("indexing.avg.unique.terms.per.doc", "120"));

	/** number of unique terms in this class so far. Should equal the size of occurrences and term_fields */	
	protected int numberOfPointers = 0;
	/** length of the document so far. Sum of the term frequencies inserted so far. */
	protected int documentLength = 0;

	/** mapping term to tf mapping */	
	protected final TObjectIntHashMap<String> occurrences = new TObjectIntHashMap<String>(AVG_DOCUMENT_UNIQUE_TERMS);
	/** mapping term to field bitset */
	protected final TObjectIntHashMap<String> term_fields = new TObjectIntHashMap<String>(AVG_DOCUMENT_UNIQUE_TERMS);
	/** number of fields in this index */
	protected final int fieldCount;

	/** Make a new postings list for a document. No fields */	
	public DocumentPostingList()
	{
		this.fieldCount = 0;
	}
	
	/** Make a new postings list for a document, with the specified number of fields
	  * @param fieldCount number of fields marked in this index */
	public DocumentPostingList(int fieldCount)
	{
		this.fieldCount = fieldCount;
	}
	
	public String[] termSet()
	{
		return occurrences.keys(new String[0]);
	}
	
	public int getFrequency(String term)
	{
		return occurrences.get(term);
	}
	
	public int getFields(String term)
	{
		return term_fields.get(term);
	}

	/** Removes all postings from this document */
	public void clear()
	{
		occurrences.clear();
		term_fields.clear();
	}

	/** Returns the total number of tokens in this document */	
	public int getDocumentLength()
	{
		return documentLength;
	}

	/** Returns the number of unique terms in this document. */
	public int getNumberOfPointers()
	{
		return numberOfPointers;
	}
	/** Insert a term into the posting list of this document 
	  * @param term the Term being inserted */
	public void insert(final String term)
	{
		occurrences.adjustOrPutValue(term,1,1);
		documentLength++;
	}
	/**  Insert a term into the posting list of this document, in the given field
	  * @param term the Term being inserted
	  * @param fieldNum the id of the field that the term was found in */
	public void insert(final String term, final int fieldNum)
	{
		occurrences.adjustOrPutValue(term,1,1);
		if (fieldNum > 0)
		{
			final int InScore = 1<<(fieldCount - fieldNum);
			final int ExScore = term_fields.get(term);
			term_fields.put(term, InScore|ExScore);
		}
		documentLength++;
	}

	/**  Insert a term into the posting list of this document, in the given field
      * @param term the Term being inserted
      * @param fieldNums the ids of the fields that the term was found in */
	public void insert(final String term, final int[] fieldNums)
    {
        occurrences.adjustOrPutValue(term,1,1);
		final int l = fieldNums.length; 
		int InScore = term_fields.get(term);
		for(int i=0;i<l;i++)
		{
			if (fieldNums[i] > 0)
				InScore |= 1<<(fieldCount - fieldNums[i]);
		}
        term_fields.put(term, InScore);
        documentLength++;
    }

	/** returns the postings suitable to be written into the direct index */
	public int[][] getPostings()
	{
		final int termCount = occurrences.size();
		final int[] termids = new int[termCount];
		final int[] tfs = new int[termCount];
		final int[] fields = new int[termCount];
		if (fieldCount > 0)
		{
			occurrences.forEachEntry( new TObjectIntProcedure<String>() {
                int i=0;
                public boolean execute(final String a, final int b)
                {
                    termids[i] = TermCodes.getCode(a);
                    tfs[i] = b;
                    fields[i++] = term_fields.get(a);
                    return true;
                }
            });	
			HeapSortInt.ascendingHeapSort(termids, tfs, fields);
            return new int[][]{termids, tfs, fields};
		}
		else
		{
			occurrences.forEachEntry( new TObjectIntProcedure<String>() { 
				int i=0;
				public boolean execute(final String a, final int b)
				{
					termids[i] = TermCodes.getCode(a);
					tfs[i++] = b;
					return true;
				}
			});
			HeapSortInt.ascendingHeapSort(termids, tfs);
			return new int[][]{termids, tfs};	
		}
	}
	
}
