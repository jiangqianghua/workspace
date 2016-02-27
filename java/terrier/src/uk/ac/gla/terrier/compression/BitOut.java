package uk.ac.gla.terrier.compression;

import java.io.Closeable;
import java.io.IOException;

/** Interface describing the writing compression methods supported
 * by the BitFile classes.
 * @author Craig Macdonald
 * @version $Revision: 1.2 $
 * @since 2.0
 */
public interface BitOut extends Closeable {
    /**
     * Returns the byte offset of the stream.
     * It corresponds to the position of the
     * byte in which the next bit will be written.
     * @return the byte offset in the stream.
     */
    public long getByteOffset();
    /**
     * Returns the bit offset in the last byte.
     * It corresponds to the position in which
     * the next bit will be written.
     * @return the bit offset in the stream.
     */
    public byte getBitOffset();

	/**
	 * Writes an integer x using unary encoding. The encoding is a sequence of x -1 zeros and 1 one:
	 * 1, 01, 001, 0001, etc ..
	 * This method is not failsafe, it doesn't check if the argument is 0 or negative. 
	 * @param x the number to write
	 * @return the number of bits written
	 * @throws IOException if an I/O error occurs.
	 */
	public int writeUnary( int x ) throws IOException;
	/**
	 * Writes an integer x into the stream using gamma encoding.
	 * This method is not failsafe, it doesn't check if the argument is 0 or negative.
	 * @param x the int number to write
	 * @return the number of bits written
	 * @throws IOException if an I/O error occurs.
	 */
	public int writeGamma( int x ) throws IOException;
	/**
	 * Writes an integer in binary format to the stream.
	 * @param len size in bits of the number.
	 * @param x the integer to write.
	 * @return the number of bits written.
	 * @throws IOException if an I/O error occurs.
	 */
	public int writeBinary(int len, int x) throws IOException;
	
}
