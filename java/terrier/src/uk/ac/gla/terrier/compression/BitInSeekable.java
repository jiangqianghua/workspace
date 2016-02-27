package uk.ac.gla.terrier.compression;

import java.io.Closeable;
import java.io.IOException;

/** Interface for BitFile and OldBitFile - future proofing for returning new objects for readReset
 * instead of just the object itself.
 * @author Craig Macdonald
 * @version $Revision: 1.1 $
 * @since 2.0
 */
public interface BitInSeekable extends Closeable {
	/**
	 * Reads from the file a specific number of bytes and after this
	 * call, a sequence of read calls may follow. The offsets given 
	 * as arguments are inclusive. For example, if we call this method
	 * with arguments 0, 2, 1, 7, it will read in a buffer the contents 
	 * of the underlying file from the third bit of the first byte to the 
	 * last bit of the second byte.
	 * @param startByteOffset the starting byte to read from
	 * @param startBitOffset the bit offset in the starting byte
	 * @param endByteOffset the ending byte 
	 * @param endBitOffset the bit offset in the ending byte. 
	 *        This bit is the last bit of this entry.
	 * @return Returns the BitIn object to use to read that data
	 */	
	public BitIn readReset(long startByteOffset, byte startBitOffset, long endByteOffset, byte endBitOffset) throws IOException;
}
