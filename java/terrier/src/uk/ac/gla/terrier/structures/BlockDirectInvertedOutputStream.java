package uk.ac.gla.terrier.structures;

import java.io.IOException;
import uk.ac.gla.terrier.compression.BitOut;

/** Writes a block direct or block inverted index, when passed appropriate posting lists.
  * @author Craig Macdonald
  * @since 2.0
  * @version $Revision: 1.3 $
  */
public class BlockDirectInvertedOutputStream extends DirectInvertedOutputStream {

	/** Creates a new output stream, writing a BitOutputStream to the specified file. The number of binary bits
	  * for fields must also be specified.
	  * @param filename Location of the file to write to
	  * @param binaryBits the number of fields in this index 
	  */
	public BlockDirectInvertedOutputStream(String filename, int binaryBits) throws IOException
	{
		super(filename, binaryBits);
	}
	/** Creates a new output stream, writing to the specified BitOut implementation.  The number of binary bits
	  * for fields must also be specified.
	  * @param out BitOut implementation to write the file to 
	  * @param binaryBits the number of fields in this index 
	  */
	public BlockDirectInvertedOutputStream(BitOut out, int binaryBits)
	{
		super(out, binaryBits);
	}
	
	/**
	 * Writes the given block postings to the bit file. This method assumes that
	 * field information is provided as well.
	 * @param postings the postings list to write.
	 * @param startOffset The location of the first posting to write out.
	 * @param Length The number of postings to be written out.
	 * @param firstId the first identifier to write. This can be 
	 *        an id plus one, or the gap of the current id and the previous one.
	 */
	protected void  writeFieldPostings(final int[][] postings, int offset, final int Length, final int firstId)
		throws IOException {
		
		//local variables in order to reduce the number
		//of times we need to access a two-dimensional array
		final int[] postings0 = postings[0];
		final int[] postings1 = postings[1];
		final int[] postings2 = postings[2];
		final int[] postings3 = postings[3];
		final int[] postings4 = postings[4];
		
		//write the first posting from the term's postings list
		output.writeGamma(firstId);						//write document id 
		output.writeUnary(postings1[offset]);    			//write frequency
		output.writeBinary(binaryBits, postings2[offset]);	//write fields if binaryBits>0
		
		int blockIndex = 0;								//the index of the current block id
		
		//correct the blockoffset if we're not writing from the start of the posting list
		if (offset != 0)
			for(int i=0;i<offset;i++)
				blockIndex += postings3[i];
		
		int blockFrequency = postings3[offset];				//the number of block ids to write
		output.writeUnary(blockFrequency + 1);     			//write block frequency
		if (blockFrequency > 0)
		{
			output.writeGamma(postings4[blockIndex]+1);	//write the first block id
			blockIndex++;									//move to the next block id
			for (int i=1; i<blockFrequency; i++) {			//write the next blockFrequency-1 ids
				//write the gap between consequtive block ids
				output.writeGamma(postings4[blockIndex]-postings4[blockIndex-1]);
				blockIndex++;
			}
		}
		offset++;
		
		//write the rest of the postings from the term's postings list
		final int length = postings[0].length;
		for (; offset < Length; offset++) {
			output.writeGamma(postings0[offset] - postings0[offset - 1]);	//write gap of document ids
			output.writeUnary(postings1[offset]);					//write term frequency
			output.writeBinary(binaryBits, postings2[offset]);		//write fields if binaryBits>0
			blockFrequency = postings3[offset];						//number of block ids to write
			output.writeUnary(blockFrequency + 1);					//write block frequency
			if (blockFrequency  > 0)
			{
				output.writeGamma(postings4[blockIndex]+1);			//write the first block id
				blockIndex++;										//move to the next block id
				for (int i=1; i<blockFrequency; i++) {
					//write the gap between consequtive block ids
					output.writeGamma(postings4[blockIndex]-postings4[blockIndex-1]);
					blockIndex++;
				}
			}
		}
	}

	/**
	 * Writes the given block postings to the bit file. This method assumes that
	 * field information is not provided.
	 * @param postings the postings list to write.
	 * @param firstId the first identifier to write. This can be 
	 *        an id plus one, or the gap of the current id and the previous one.
	 * @param startOffset The location of the first posting to write out.
	 * @param Length The number of postings to be written out.
	 * @throws IOException if an error occurs during writing to a file.
	 */
	protected void writeNoFieldPostings(int[][] postings, int offset, final int Length, int firstId) 
		throws IOException {
		
		//local variables in order to reduce the number
		//of times we need to access a two-dimensional array
		final int[] postings0 = postings[0];
		final int[] postings1 = postings[1];
		final int[] postings3 = postings[3];
		final int[] postings4 = postings[4];
		
		//write the first posting from the term's postings list
		output.writeGamma(firstId);						//write document id 
		output.writeUnary(postings1[offset]);    			//write frequency
		int blockIndex = 0;								//the index of the current block id
		if (offset != 0)
			for(int i=0;i<offset;i++)
				blockIndex += postings3[i];
				
		int blockFrequency = postings3[offset];				//the number of block ids to write
		output.writeUnary(blockFrequency + 1);    			//write block frequency
		if (blockFrequency  > 0)
		{
			output.writeGamma(postings4[blockIndex]+1);		//write the first block id
			blockIndex++;									//move to the next block id
			for (int i=1; i<blockFrequency; i++) {			//write the next blockFrequency-1 ids
				//write the gap between consequtive block ids
				output.writeGamma(postings4[blockIndex]-postings4[blockIndex-1]);
				blockIndex++;
			}
		}
		offset++;
		
		//write the rest of the postings from the term's postings list
		final int length = postings0.length;
		for (; offset < Length; offset++) {
			output.writeGamma(postings0[offset] - postings0[offset - 1]);	//write gap of document ids
			output.writeUnary(postings1[offset]);					//write term frequency
			blockFrequency = postings3[offset];							//number of block ids to write
			output.writeUnary(blockFrequency + 1);				//write block frequency
			if (blockFrequency > 0)
			{
				output.writeGamma(postings4[blockIndex]+1);		//write the first block id
				blockIndex++;											//move to the next block id
				for (int i=1; i<blockFrequency; i++) {
					//write the gap between consequtive block ids
					output.writeGamma(postings4[blockIndex]-postings4[blockIndex-1]);
					blockIndex++;
				}
			}
		}		
	}
	

}
