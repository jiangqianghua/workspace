//
//  LexiconMap.java
//  
//
//  Created by Craig Macdonald on 24/04/2007.
//  Copyright 2007 __MyCompanyName__. All rights reserved.
//
package uk.ac.gla.terrier.structures.indexing;
import gnu.trove.TObjectIntHashMap;
import gnu.trove.TObjectIntProcedure;

import java.io.IOException;
import java.util.Arrays;

import uk.ac.gla.terrier.structures.LexiconOutputStream;
import uk.ac.gla.terrier.utility.ApplicationSetup;
import uk.ac.gla.terrier.utility.TermCodes;
/** This class keeps track of the total counts of terms within a bundle of documents being indexed.
  * Internally, uses hashmaps. This class replaces the LexiconTree etc.
  * <P><b>Properties</b><ul>
  * <li><tt>indexing.avg.unique.terms.per.bundle</tt> - the unique number of terms expected to be indexed in a bundle of documents. Not a limit, just a hint for the sizing of the hashmaps.Default to 120. </li>
  * </ul>
  */
public class LexiconMap {
	/** Number of unique terms expected to be indexed in a bundle of documents.*/
	protected static final int BUNDLE_AVG_UNIQUE_TERMS =
		Integer.parseInt(ApplicationSetup.getProperty("indexing.avg.unique.terms.per.bundle", "120"));
	
	/** number of different terms */
	protected int numberOfNodes = 0;
	/** number of different entries there will be in the inverted index */
	protected int numberOfPointers = 0;
	/** mapping: term to term frequency in the collection */
	protected final TObjectIntHashMap<String>tfs = new TObjectIntHashMap<String>(BUNDLE_AVG_UNIQUE_TERMS);
	/** mapping: term to document frequency */
	protected final TObjectIntHashMap<String>nts = new TObjectIntHashMap<String>(BUNDLE_AVG_UNIQUE_TERMS);	

	/** Clear the lexicon map */
	public void clear()	
	{
		tfs.clear();
		nts.clear();
	}

	/**
	 * Inserts a new term in the lexicon map.
	 * @param term The term to be inserted.
	 * @param tf The id of the term.
	 */
	public void insert(final String term, final int tf)
	{
		tfs.adjustOrPutValue(term, tf, tf);
		nts.adjustOrPutValue(term, 1 , 1);
		numberOfPointers++;
	}

	/** Inserts all the terms from a document posting
	  * into the lexicon map
	  * @param doc The postinglist for that document
	  */
	public void insert(DocumentPostingList doc)
	{
		doc.occurrences.forEachEntry(
			new TObjectIntProcedure<String>() {
				public boolean execute(final String t, final int tf)
				{
					//insert(a,b);
					tfs.adjustOrPutValue(t, tf, tf);
					nts.adjustOrPutValue(t, 1 , 1);
					return true;
				}
			});
	}
	
	/** Stores the lexicon tree to a lexicon stream as a sequence of entries.
	  * The binary tree is traversed in order, by called the method
	  * traverseAndStoreToStream.
	  * @param lexiconStream The lexicon output stream to store to. */
	public void storeToStream(LexiconOutputStream lexiconStream) throws IOException {
		final byte zerob = (byte)0;
		final long zerol = (long)0;
		final String[] terms = tfs.keys(new String[0]);
		Arrays.sort(terms);
		for (String t : terms)
		{
			lexiconStream.writeNextEntry(t, TermCodes.getCode(t), nts.get(t), tfs.get(t), zerol, zerob);
		}
	}
	
	/**
	* Returns the numbe of nodes in the tree.
	* @return int the number of nodes in the tree.
	*/
	public int getNumberOfNodes() {
		return tfs.size();
	}
	/**
	 * Returns the number of pointers in the tree.
	 * @return int the number of pointers in the tree.
	 */
	public int getNumberOfPointers() {
		return numberOfPointers;
	}
	
}
