package uk.ac.gla.terrier.structures.indexing.singlepass;

import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import uk.ac.gla.terrier.compression.BitOut;
import uk.ac.gla.terrier.compression.BitOutputStream;
import uk.ac.gla.terrier.structures.LexiconOutputStream;

/**
 * Merges a set of N runs using a priority queue. Each element of the queue is a {@link uk.ac.gla.terrier.structures.indexing.singlepass.RunReader}
 * each one pointing at a different run in disk. Each run is sorted, so we only need to compare the heads of the 
 * element in the queue in each merging step.
 * As the runs are being merged, they are written to disk using a {@link uk.ac.gla.terrier.compression.BitOutputStream}. 
 * @author Roi Blanco
 *
 */
public class RunsMerger {
	
	/**
	 * Implements a comparator for RunReaders (so it can be used by the queue).
	 * It decides the next reader by the lexicographical order of the terms in the top elements of the readers.
	 * @author Roi Blanco
	 *
	 */
	public static class PostingComparator implements Comparator<RunReader>{
		public int compare(RunReader x, RunReader y) {			
			int tmp = x.getPosting().getTerm().compareTo(y.getPosting().getTerm());
			if (tmp != 0) return tmp;
			else
				return x.getRunNo() - y.getRunNo();
		}		
	}
	
	/**
	 * Heap for the postings coming from different runs.
	 * It uses an alphabetical order using the terms
	 */	 
	protected Queue<RunReader> queue;		
	/** BitOutputStream used to write the merged postings to disk*/
	protected BitOut bos;	
	/** RunReader reference for instantiation*/
	protected RunReader run;
	/** Last term written to disk (useful for terms appearing in mutiple runs */
	protected String lastTermWritten = "";
	/** Frequency in the run of the last term merged */ 
	protected int lastFreq = 0;
	/**Last document written in the stream*/
	protected int lastDocument = 0; 
	/** Last document's frequency*/
	protected int lastDocFreq = 0;
	/** RunReader reference for merging */
	protected RunReader myRun;
	/** Number of terms written */
	protected int currentTerm = 0;
	/** Number of pointers written */
	protected int numberOfPointers = 0;
	
	/**
	 * Template for creating the appropriate RunReader. 
	 * @param file String containing the name of the file for this run.
	 * @param termsFile String containing the name of the terms file for this run.
	 * @param runNo number of run.
	 * @throws IOException
	 */
	protected void createRunReader(String file, String termsFile, int runNo) throws IOException {
		run = new RunReader(file, termsFile, runNo);	
	}
	
	/**
	 * @return the last frequency written.
	 */
	public int getLastFreq(){
		return lastFreq;
	}
	
	/**
	 * @return the last document frequency written.
	 */
	public int getLastDocFreq(){
		return lastDocFreq;
	}	
	
	/**
	 * @return the number of terms written.
	 */
	public int getNumberOfTerms(){
		return currentTerm;
	}
	
	/**
	 * @return the number of pointers written.
	 */
	public int getNumberOfPointers(){
		return numberOfPointers;
	}
	
	/** Indicates whether the merging is done or not
	 * @return true if there are no more elements to merge
	 */
	public boolean isDone(){
		return queue.isEmpty();
	}
	
	/**
	 * @return the byte offset in the BitOut (used for lexicon writting)
	 */
	public long getByteOffset(){
		return bos.getBitOffset() == 0? bos.getByteOffset() - 1: bos.getByteOffset(); 
	}
	
	/**
	 * @return the bit offset in the BitOut (used for lexicon writting)
	 */
	public int getBitOffset(){
		return bos.getBitOffset() == 0? 7: bos.getBitOffset() - 1;
	}
	
	/**
	 * @return the String with the last term written to disk.
	 */
	public String getLastTermWritten() {
		return lastTermWritten;
	}
	
	/**
	 * Setter for the last term written.
	 * @param lastTermWritten String with the last term written. 
	 */
	public void setLastTermWritten(String lastTermWritten) {
		this.lastTermWritten = lastTermWritten;
	}
	
	/**
	 * Begins the merge, initilialising the structures.
	 * Notice that the file names must be in order of run-id	
	 * @param files String[][] two-dimensional array containing the file names of the runs and terms.
	 * @param size number of runs in disk.
	 * @param fileName String with the file name of the final inverted file.
	 * @throws IOException if an I/O error occurs.
	 */
	public void init(String[][] files, int size, String fileName) throws IOException{
		queue = new PriorityQueue<RunReader>(size, new PostingComparator());		
		bos = new BitOutputStream(fileName);
		for(int i = 0; i < size; i++){			
			createRunReader(files[i][0], files[i][1],i);
			run.nextPosting();
			queue.add(run);
		}
	}
	
	/**
	 * Begins the multiway merging phase.
	 * @param files String[][] array containing the filenames.
	 * @param size number of runs to be merged.
	 * @param fileName output filename.
	 * @throws IOException if an I/O error occurs. 
	 */
	public void beginMerge(String[][] files, int size, String fileName) throws IOException{		
		init(files, size, fileName);
		myRun = queue.poll();
		while(myRun.getPosting().getTerm().equals(" ")) myRun = queue.poll();		
		lastDocument = myRun.getPosting().append(bos,-1);
		lastFreq = myRun.getPosting().getTF();
		lastDocFreq = myRun.getPosting().getDf();	
		lastTermWritten = myRun.getPosting().getTerm();
		if(myRun.hasNext()){
			myRun.nextPosting();			
			queue.add(myRun);			
		}else{
			myRun.close();
		}		
	}
	
	/**
	 * Mergers one term in the runs. If a run is exhausted, it is closed and removed from the queue. 
	 * @param lexStream LexiconOutputStream used to write the lexicon.
	 * @throws IOException if an I/O error occurs.
	 */
	public void mergeOne(LexiconOutputStream lexStream) throws IOException{		
		myRun = queue.poll();
		if(myRun.getPosting().getTerm().equals(lastTermWritten)){
			// append the term --> keep the data in memory
			lastDocument = myRun.getPosting().append(bos, lastDocument);
			lastFreq += myRun.getPosting().getTF();
			lastDocFreq += myRun.getPosting().getDf();
		}else{			
			lexStream.writeNextEntry(lastTermWritten, currentTerm++, lastDocFreq, lastFreq, this.getByteOffset(), (byte)this.getBitOffset());
			// write the new term
			numberOfPointers += lastDocFreq;
			lastDocument = myRun.getPosting().append(bos,-1);
			lastFreq = myRun.getPosting().getTF();
			lastDocFreq = myRun.getPosting().getDf();
			lastTermWritten = myRun.getPosting().getTerm();
		}
		if(myRun.hasNext()){
			myRun.nextPosting();
			queue.add(myRun);
		}else{
			myRun.close();
		}
	}
	
	/**
	 * Ends the merging phase, writes the last entry and closes the streams.
	 * @param lexStream LexiconOutputStream used to write the lexicon.
	 * @throws IOException if an I/O error occurs.	
	 */	
	public void endMerge(LexiconOutputStream lexStream) throws IOException{
		lexStream.writeNextEntry(lastTermWritten, currentTerm++, lastDocFreq, lastFreq, this.getByteOffset(), (byte)this.getBitOffset());		
		numberOfPointers += lastDocFreq;
		bos.close();
		myRun.close();
	}	
}
