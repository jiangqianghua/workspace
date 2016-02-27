package uk.ac.gla.terrier.structures.indexing;

import uk.ac.gla.terrier.structures.LexiconOutputStream;
import uk.ac.gla.terrier.structures.BlockLexiconOutputStream;
import gnu.trove.*;
import java.util.Arrays;
import java.io.IOException;
import uk.ac.gla.terrier.utility.TermCodes;

/** LexiconMap implementation that also keeps track of the number of blocks that a term occurrs in.
  * This is useful for sizing the block inverted index */
public class BlockLexiconMap extends LexiconMap
{
	/** Total number of blocks in this index */
	protected long numberOfBlocks = 0;
	/** Mapping term to blocks */
	protected final TObjectIntHashMap<String> blockFreqs = new TObjectIntHashMap<String>();
	/**
	 * Inserts a new term in the lexicon map.
	 * @param term The term to be inserted.
	 * @param tf The id of the term.
	 */
	public void insert(final String term, final int tf, final int blockfreq)
	{
		tfs.adjustOrPutValue(term, tf, tf);
		nts.adjustOrPutValue(term, 1 , 1);
		blockFreqs.put(term, blockfreq);
		numberOfPointers++;
		numberOfBlocks+=blockfreq;
	}

	/** Clear the lexicon map */
	public void clear()
	{
		super.clear();
		blockFreqs.clear();
	}

	/** Inserts all the terms from a document posting
	  * into the lexicon map
	  * @param _doc The postinglist for that document - must be a instance of BlockDocumentPostingList.
	  */
	public void insert(final DocumentPostingList _doc)
	{
		final BlockDocumentPostingList doc = (BlockDocumentPostingList)_doc;
		doc.occurrences.forEachEntry( new TObjectIntProcedure<String>() {
				public boolean execute(final String t, final int tf)
				{
					tfs.adjustOrPutValue(t, tf, tf);
					nts.adjustOrPutValue(t, 1 , 1);
					numberOfPointers++;
					final int bf = doc.term_blocks.get(t).size();
					blockFreqs.adjustOrPutValue(t, bf, bf);
					numberOfBlocks+=bf;
					return true;
				}
			});
	}

	/** Stores the lexicon map to a lexicon stream as a sequence of entries.
	  * @param _lexiconStream The lexicon output stream to store to. */
	public void storeToStream(final LexiconOutputStream _lexiconStream) throws IOException {
		final BlockLexiconOutputStream lexiconStream = (BlockLexiconOutputStream)_lexiconStream;
		final byte zerob = (byte)0;
		final long zerol = (long)0;
		final String[] terms = tfs.keys(new String[0]);
		Arrays.sort(terms);
		for (String t : terms)
		{
			lexiconStream.writeNextEntry(t, TermCodes.getCode(t), nts.get(t), tfs.get(t), blockFreqs.get(t), zerol, zerob);
		}
	}

}
