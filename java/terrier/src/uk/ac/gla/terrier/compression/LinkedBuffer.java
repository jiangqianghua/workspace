package uk.ac.gla.terrier.compression;

import java.io.IOException;

/**
 * Implements an element of a linked list that contains a byte array
 * @author Roi Blanco
 *
 */
public class LinkedBuffer {

	/** The internal buffer. */
	protected byte buffer[];
	/** The size of the internal buffer*/
	protected int bufferSize;
	
	/** The default size of the internal buffer in bytes 
	* The buffer has to be big enough to allocate a single write, i.e we cannot write
	* *at once* more than bufferSize bytes
	*/
	public final static int DEFAULT_BUFFER_SIZE = 1024;
	
	/** The next buffer in the list */
	protected LinkedBuffer next = null;
	
	/** The position in the buffer */
	private int position = 0;
	
	/**
	 * Default Constructor. Uses a buffer of DEFAULT_BUFFER_SIZE bytes size.
	 *
	 */
	public LinkedBuffer(){
 		this(DEFAULT_BUFFER_SIZE);
	}
	
	/**
	 * Constructor
	 * @param bufferSize size in bytes of the buffer.
	 */
	public LinkedBuffer(int bufferSize){
		buffer = new byte[bufferSize];
		this.bufferSize = bufferSize;
	}
	
	/**
	 * @return the next linked buffer in the list (or null)
	 */
	public LinkedBuffer getNext(){
		return next;
	}

	/**
	 * Set the next buffer in the list
	 * @param next next LinkedBuffer in the list
	 */
	public void setNext(LinkedBuffer next){
		this.next = next;
	}
	
	/**
	 * Writes a byte in the buffer
	 * @param b int containing the byte to write 
	 * @return true iff the buffer has used all its capacity
	 * @throws IOException
	 */
	public boolean write( final int b ) throws IOException {	
		buffer[ position++ ] = (byte)b;
		return position == bufferSize;
	}

	/**
	 * Writes a byte in the buffer
	 * @param b byte to write
	 * @return true iff the buffer has used all its capacity
	 * @throws IOException
	 */
	public boolean writeByte(final byte b) throws IOException{		
		buffer[ position++ ] = b;
		return position == bufferSize;
	}
	
	/**
	 * @return The size of the buffer
	 */
	public int getBufferSize(){
		return bufferSize;
	}
	
	/**
	 * @return The current position in the buffer
	 */
	public int getPosition(){
		return position;
	}
	
	/**
	 * @return The byte buffer (byte[])
	 */
	public byte[] getBuffer(){
		return buffer;
	}	
}
