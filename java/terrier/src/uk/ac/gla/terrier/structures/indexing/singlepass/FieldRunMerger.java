package uk.ac.gla.terrier.structures.indexing.singlepass;

import java.io.IOException;

/**
 * Merges a set of N runs using a priority queue. Each element of the queue is a {@link uk.ac.gla.terrier.structures.indexing.singlepass.RunReader}
 * each one pointing at a different run in disk. Each run is sorted, so we only need to compare the heads of the 
 * element in the queue in each merging step.
 * As the runs are being merged, they are written to disk using a {@link uk.ac.gla.terrier.compression.BitOutputStream}. 
 * @author Roi Blanco
 *
 */
public class FieldRunMerger extends RunsMerger {
	/**
	 * Creates a FieldRunReader for this merger. 
	 * @param file String containing the name of the file for this run.
	 * @param termsFile String containing the name of the terms file for this run.
	 * @param runNo number of run.
	 * @throws IOException
	 */
	protected void createRunReader(String file, String termsFile, int runNo) throws IOException {
		run = new FieldRunReader(file, termsFile, runNo);		
	}
}
