package uk.ac.gla.terrier.structures.indexing.singlepass;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.File;
import uk.ac.gla.terrier.compression.BitIn;
import uk.ac.gla.terrier.compression.BitInputStream;
import uk.ac.gla.terrier.utility.ApplicationSetup;
import uk.ac.gla.terrier.utility.Files;

/**
 * This class implements an iterator for reading the postings in a run. It can be extended for different types of 
 * postings (fields, blocks).
 * @author Roi Blanco
 *
 */
public class RunReader {
	 /** has the UTF encoding been used to write out terms? */	
	protected final boolean UTFindexing = ("true".equals(ApplicationSetup.getProperty("string.use_utf", "false")));
	
	/**
	 * Constructor for the class. 
	 * @param filename String with the name of the run file.
	 * @param termsFile String with the name of the terms file.
	 * @param runNo int identifying the run number.
	 * @throws IOException is an I/O error occurs.
	 */
	public RunReader(String filename, String termsFile, int runNo) throws IOException{
		mbis = new BitInputStream(filename);
		stringDIS = new DataInputStream( Files.openFileStream(termsFile) );
		if (new File(filename).length() > 0)
		{
			maxSize = mbis.readGamma();
			size = mbis.readGamma();
		}
		createPosting(maxSize);
		currentPosting = 0;
		this.runNo = runNo;		
	}
	
	/**
	 * Input stream for reading the run.
	 */
	protected final BitIn mbis;
	/**
	 * Input stream for reading the terms.
	 */
	protected final DataInputStream stringDIS;	
	/** Number of postings in this run */
	protected int size;
	/** Current Posting List number */
	protected int currentPosting;
	/** max number of pointers any term in the run */
	protected int maxSize;
	/** Curent posting being read */ 
	protected SimplePostingInRun posting;
	/** Run number */
	protected int runNo;
	
	/**
	 * @return The number for this run.
	 */
	public int getRunNo() {
		return runNo;
	}
	
	/**
	 * Setter for the run number.
	 * @param runNo int containing the run number.
	 */
	public void setRunNo(int runNo) {
		this.runNo = runNo;
	}
	
	/**
	 * @return The max size of any posting list in this run
	 */
	public int getMaxSize() {
		return maxSize;
	}
	
	/**
	 * Setter for the max size of a term's posting list
	 * @param maxSize int containing the max size
	 */
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}	
	
	/**
	 * @return The number of postings in this run.
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Setter for the number of postings in this run.
	 * @param size int containing the number of postings in this run.
	 */
	public void setSize(int size) {
		this.size = size;
	}	
	
	/**
	 * @return The current posting being read.
	 */
	public SimplePostingInRun getPosting(){
		return posting;
	}	
	
	/**
	 * Hook method for creating the right Posting class.
	 * @param maxSize the max number of documents for any term in this run (used for allocating space). 
	 */
	protected void createPosting(int maxSize){
		posting = new SimplePostingInRun(maxSize);	
	}
	
	/**
	 * Closes the RunReader and the underlying streams.
	 * @throws IOException if an I/O error occurs.
	 */
	public void close() throws IOException{		
		mbis.close();
		stringDIS.close();
	}
	
	/**
	 * @return true iff there are more postings to be read in the run.
	 */
	public boolean hasNext(){
		return currentPosting != size;		
	}	
	
	/**
	 * Reads the term frequency for the current posting, and aligns the stream.
	 * @return the frequency read.
	 * @throws IOException if an I/O error occurs.
	 */
	public int readTermFrequency() throws IOException{
		int temp = mbis.readGamma();
		mbis.align();
		return temp;
	}
	
	/**
	 * Reads the String identifying a term from the underlying stream.
	 * @return the String with the term.
	 * @throws IOException if an I/O error occurs.
	 */
	public String readString() throws IOException{
		int size = mbis.readGamma();
		if (UTFindexing)
		{
			//readUTF knows the length of a written string, size not needed 
			return stringDIS.readUTF();
		}
		else	
		{
			byte[] array = new byte[size];
			stringDIS.read(array,0,size);
			return new String(array);
		}
			
	}
	
	/**
	 * Reads the next posting list in the run. It implements a hook method in
	 * the template pattern (for reading the data in a run). 
	 * @throws IOException if an I/O error occurs.
	 */
	public void nextPosting() throws IOException{
		int current = 0;
		posting.setTerm(readString());
		posting.setDf(mbis.readGamma());
		posting.setTF(readTermFrequency());
		posting.setPostingSource(mbis);
		currentPosting++;
	}
}
