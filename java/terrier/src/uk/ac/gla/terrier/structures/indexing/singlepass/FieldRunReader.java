package uk.ac.gla.terrier.structures.indexing.singlepass;

import java.io.IOException;

import uk.ac.gla.terrier.utility.FieldScore;
/**
 * This class implements an iterator for reading the postings in a run, containing field information. 
 * @author Roi Blanco
 *
 */
public class FieldRunReader extends RunReader {
	/** The number of different fields that are used for indexing field information.*/	
	protected static final int fieldTags = FieldScore.FIELDS_COUNT;
	
	/**
	 * Class constructor. 
	 * @param filename String with the name of the run file.
	 * @param termsFile String with the name of the terms file.
	 * @param runNo int identifying the run number.
	 * @throws IOException is an I/O error occurs.
	 */
	public FieldRunReader(String filename, String termsFile, int runNo) throws IOException{
		super(filename, termsFile, runNo);		
	}
	
	/**
	 * Creates a FieldPostingInRun for this reader.
	 * @param maxSize the max frequency in this run (used for allocating space). 
	 */
	protected void createPosting(int maxSize){
		posting = new FieldPostingInRun(maxSize);	
	}
}
