package uk.ac.gla.terrier.structures.indexing.singlepass;

import java.io.IOException;

import uk.ac.gla.terrier.compression.BitOut;
import uk.ac.gla.terrier.utility.FieldScore;

/** Class holding the information for a posting list read
 * from a previously written run at disk. Used in the merging phase of the Single pass inversion method.
 * This class knows how to append itself to a {@link uk.ac.gla.terrier.compression.BitOut} and it
 * represents a posting with blocks and field information <code>(tf, df, [docid, idf, fieldScore, blockFr [blockid]])</code>
 * @author Roi Blanco
 *
 */
public class BlockFieldPostingInRun extends BlockPostingInRun{
	/** array with the field scores*/
	//private int[] fields;
	/** The number of different fields that are used for indexing field information.*/	
	protected static final int fieldTags = FieldScore.FIELDS_COUNT;
	/**
	 * Constructor for the class.
	 * @param maxSize Max number of elements for the posting list.
	 */
	public BlockFieldPostingInRun(int maxSize){
		super(maxSize);	
		//fields = new int[maxSize];
	}
	/**
	 * @return the int[] with the field scores for the documents in this posting 
	 */
	/*public int[] getFields() {
		return fields;
	}*/
	/**
	 * Writes the document data of this posting to a {@link uk.ac.gla.terrier.compression.BitOut} 
	 * It encodes the data with the right compression methods.
	 * The stream is written as <code>d1, idf(d1), fieldScore(d1), blockNo(d1), bid1, bid2, ...,  d2 - d1, idf(d2), fieldScore(d2), blockNo(d2), ...</code> etc
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
			bos.writeBinary(fieldTags, fields[i]);
			blockArray = blocks.get(i);
			bos.writeUnary(blockArray.length);			
			for(int j = 0; j < blockArray.length; j++){				
				bos.writeGamma(blockArray[j]);			
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
			//deal with fields
			bos.writeBinary(fieldTags, postingSource.readBinary(fieldTags));
			
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
