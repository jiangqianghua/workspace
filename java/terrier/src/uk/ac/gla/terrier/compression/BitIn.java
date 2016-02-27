package uk.ac.gla.terrier.compression;


import java.io.Closeable;
import java.io.IOException;

/** Interface describing the read compression methods supported
 * by the BitFile and BitInputStream classes.
 * @author Craig Macdonald
 * @version $Revision: 1.2 $
 * @since 2.0
 */
public interface BitIn extends Closeable {

	/**
	 * Returns the byte offset of the stream.
	 * It corresponds to the position of the 
	 * byte in which the next bit will be written.
	 * Use only when writting
	 * @return the byte offset in the stream.
	 */
	public long getByteOffset();
	/**
	 * Returns the bit offset in the last byte.
	 * It corresponds to the position in which
	 * the next bit will be written.
	 * Use only when writting.
	 * @return the bit offset in the stream.
	 */
	public byte getBitOffset();
	
	/**
	 * Reads a unary encoded integer from the underlying stream 
	 * @return the number read
	 * @throws IOException if an I/O error occurs
	 */
	public int readUnary() throws IOException;
	/**
	 * Reads a gamma encoded integer from the underlying stream
	 * @return the number read
	 * @throws IOException if an I/O error occurs
	 */
	public int readGamma() throws IOException;
	/**
	 * Reads a binary integer from the already read buffer.
	 * @param len the number of binary bits to read
	 * @throws IOException if an I/O error occurs
	 * @return the decoded integer
	 */
	public int readBinary(int len) throws IOException;

	/** Skip a number of bits while reading the bit file.
	 * @param len The number of bits to skip
	 * @throws IOException if an I/O error occurs
	 */
    public void skipBits(int len) throws IOException;

    /**
     * Aligns the stream to the next byte
     * @throws IOException if an I/O error occurs
     */
    public void align() throws IOException;
}
