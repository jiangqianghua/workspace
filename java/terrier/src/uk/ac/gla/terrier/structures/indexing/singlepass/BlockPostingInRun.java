package uk.ac.gla.terrier.structures.indexing.singlepass;

import java.io.IOException;
import java.util.Vector;

import uk.ac.gla.terrier.compression.BitOut;
/** Class holding the information for a posting list read
 * from a previously written run at disk. Used in the merging phase of the Single pass inversion method.
 * This class knows how to append itself to a {@link uk.ac.gla.terrier.compression.BitOut} and it
 * represents a posting with blocks information <code>(tf, df, [docid, idf, blockFr [blockid]])</code>
 * @author Roi Blanco
 *
 */
public class BlockPostingInRun extends SimplePostingInRun{
	/** Vector array containing the blocks for each document */
	//protected Vector<int[]> blocks;	
	/**
	 * Constructor for the class.
	 * @param maxSize Max number of elements for the posting list.
	 */
	public BlockPostingInRun(int maxSize){
		super(maxSize);
		//blocks = new Vector<int[]>(maxSize);		
	}

	/**
	 * Sets the block information for a document
	 * @param doc The identifier of the document.
	 * @param newBlocks The block array containing the block information.
	 */
	/*public void setBlocks(int doc, int[] newBlocks){
		blocks.add(doc, newBlocks);		
	}*/
	/**
	 * Writes the document data of this posting to a {@link uk.ac.gla.terrier.compression.BitOut} 
	 * It encodes the data with the right compression methods.
	 * The stream is written as <code>d1, idf(d1), blockNo(d1), bid1, bid2, ...,  d2 - d1, idf(d2), blockNo(d2), ...</code> etc
	 * @param bos BitOut to be written.
	 * @param last int representing the last document written in this posting.
	 * @return The last posting written.
	 */
	/*public int append(BitOut bos, int last)  throws IOException{
		int[] blockArray;
		for(int i = 0; i < getDf(); i++){
			bos.writeGamma(getDocs()[i] - last);
			bos.writeUnary(gettf()[i]);
			last = getDocs()[i];
			// append the blocks
			blockArray = blocks.get(i);
			bos.writeUnary(blockArray.length);
			for(int j = 0; j < blockArray.length; j++){
				/* we're writing gaps here, not blockids */
				/*bos.writeGamma(blockArray[j]);			
			}
		}
		blocks.clear();
		return last;
	}*/
	public int append(BitOut bos, int last)  throws IOException{
		int current = -1;
		for(int i = 0; i < termDf; i++){
			int docid = postingSource.readGamma() + current;
			bos.writeGamma(docid - last);
			bos.writeUnary(postingSource.readGamma());
			current = last = docid;	
			
			//now deal with blocks
			final int numOfBlocks = postingSource.readUnary() -1;
			bos.writeUnary(numOfBlocks+1);
			if (numOfBlocks > 0)
				for(int j = 0; j < numOfBlocks; j++){
					/* we're reading and saving gaps here, not blockids */
					bos.writeGamma(postingSource.readGamma());
				}
		}
		try{
			postingSource.align();
		}catch(Exception e){
			// last posting
		}
		return last;
	}
}
