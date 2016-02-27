package uk.ac.gla.terrier.structures.indexing.singlepass;

import java.io.IOException;

import uk.ac.gla.terrier.utility.FieldScore;
/**
 * This class implements an iterator for reading the postings in a run, containing blocks and field information. 
 * @author Roi Blanco
 *
 */
public class BlockFieldRunReader extends RunReader{
	protected static final int fieldTags = FieldScore.FIELDS_COUNT;
	/**
	 * Class constructor. 
	 * @param filename String with the name of the run file.
	 * @param termsFile String with the name of the terms file.
	 * @param runNo int identifying the run number.
	 * @throws IOException is an I/O error occurs.
	 */	
	public BlockFieldRunReader(String filename, String termsFile, int runNo) throws IOException{
		super(filename, termsFile, runNo);
	}
	/**
	 * Creates a BlockFieldPostingInRun for this reader.
	 * @param maxSize the max frequency in this run (used for allocating space). 
	 */
	protected void createPosting(int maxSize){
		posting = new BlockFieldPostingInRun(maxSize);	
	}
	
	/**
	 * Reads the next BlockField posting list in the run. 
	 * @throws IOException if an I/O error occurs.
	 */
	/*public void nextPosting() throws IOException{
		int current = 0;
		posting.setTerm(readString());
		posting.setDf(mbis.readGamma());
		posting.setTF(readTermFrequency());		
		for(int i = 0; i < posting.getDf(); i++){
			posting.getDocs()[i] = mbis.readGamma() + current;
			posting.gettf()[i] = mbis.readGamma();
			((BlockFieldPostingInRun)posting).getFields()[i] = mbis.readBinary(fieldTags);
			current = posting.getDocs()[i];
			//read the block sequence		
			int numOfBlocks = mbis.readUnary();
			int[] blocks = new int[numOfBlocks];			
			for(int j = 0; j < numOfBlocks; j++){
				blocks[j] = mbis.readGamma();
			}
			((BlockPostingInRun)posting).setBlocks (i, blocks);
		}	
		currentPosting++;
		try{
			mbis.align();
		}catch(Exception e){			
		}
	}*/
}
