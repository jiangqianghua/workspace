package uk.ac.gla.terrier.structures.indexing.singlepass;

import java.io.IOException;
/**
 * This class implements an iterator for reading the postings in a run, containing blocks information. 
 * @author Roi Blanco
 *
 */
public class BlockRunReader extends RunReader{
	/**
	 * Class constructor. 
	 * @param filename String with the name of the run file.
	 * @param termsFile String with the name of the terms file.
	 * @param runNo int identifying the run number.
	 * @throws IOException is an I/O error occurs.
	 */
	public BlockRunReader(String filename, String termsFile, int runNo) throws IOException{
		super(filename, termsFile, runNo);
	}
	/**
	 * Creates a BlockPostingInRun for this reader.
	 * @param maxSize the max frequency in this run (used for allocating space). 
	 */
	protected void createPosting(int maxSize){
		posting = new BlockPostingInRun(maxSize);	
	}
	
	/* *
	 * Reads the next Block posting list in the run. 
	 * @throws IOException if an I/O error occurs.
	 */
	
	 
	/*public void BLAnextPosting() throws IOException{
		int current = 0;
		posting.setTerm(readString());		
		posting.setDf(mbis.readGamma());
		posting.setTF(readTermFrequency());
		//System.err.println("Term "+posting.getTerm() + " TF="+posting.getTF() + " Nt="+posting.getDf());
		for(int i = 0; i < posting.getDf(); i++){
			posting.getDocs()[i] = mbis.readGamma() + current;
			posting.gettf()[i] = mbis.readGamma();
			current = posting.getDocs()[i];
			//read the block sequence		
			int numOfBlocks = mbis.readUnary();
			int[] blocks = new int[numOfBlocks];
			for(int j = 0; j < numOfBlocks; j++){
				// we're reading and saving gaps here, not blockids
				blocks[j] = mbis.readGamma();
			}
			((BlockPostingInRun)posting).setBlocks(i, blocks);
			//System.err.println("docid "+posting.getDocs()[i] + " tf="+posting.gettf()[i] + " numBlocks="+numOfBlocks);
		}
		currentPosting++;
		try{
			mbis.align();
		}catch(Exception e){			
		}
	}*/
}
