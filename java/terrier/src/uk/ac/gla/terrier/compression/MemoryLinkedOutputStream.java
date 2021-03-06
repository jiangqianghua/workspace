package uk.ac.gla.terrier.compression;

import java.io.IOException;
import java.io.OutputStream;

/**
 * This class implements an OutputStream that writes everything in memory, and never flushes the data to disk. It uses
 * a Linked list of byte[] arrays, probably of different sizes, to keep track of the new allocations. This class needs more memory (the pointers)
 * than the MemoryOutputStream class, but it avoids the reallocation of arrays in memory. 
 * @author Roi Blanco
 *
 */
public class MemoryLinkedOutputStream extends OutputStream {
	/** Reference to the first buffer in the list*/
	private LinkedBuffer firstBuffer;
	/** Reference to the current buffer in the list (for reading)*/
	private LinkedBuffer currentBuffer;
	/** The default size of the internal buffer in bytes*/
	public final static int DEFAULT_BUFFER_SIZE = 1024;		
	/** Reference to the linked structure */
	protected MemoryLinkedOutputStream next = null;
	
	/**
	 * Instanciates a MemoryLinkedOutputStream with the buffer size set to
	 * DEFAULT_BUFFER_SIZE
	 *
	 */
	public MemoryLinkedOutputStream(){
		this(DEFAULT_BUFFER_SIZE);
	}

	/**
	 * Instanciates a MemoryLinkedOutputStream specifying the buffer size. 
	 * @param bufferSize int size of the first buffer
	 *
	 */
	public MemoryLinkedOutputStream(int bufferSize){
		firstBuffer = new LinkedBuffer(bufferSize);
		currentBuffer = firstBuffer;
	}
	
	/**
	 * @return the next buffer in the list (next of current)
	 */
	public MemoryLinkedOutputStream getNext(){
		return next;
	}
	
	/**
	 * @return the position in the current buffer.
	 */
	public int getPos(){
		return currentBuffer.getPosition();
	}
	
	/** Sets the structure for reading */
	public void beginRead(){
		currentBuffer = firstBuffer;
	}
	
	/**
	 * Writes a byte into the current buffer. If it fills the buffer, it moves to the next one (doubling the capacity).
	 * @param  b the byte to write 
	 * @throws java.io.IOException if an I/O error occurs.
	 */
	public void write( final int b ) throws IOException {	
		if(currentBuffer.write(b)){
			LinkedBuffer newBuffer = new LinkedBuffer(currentBuffer.getBufferSize() * 2);
			currentBuffer.setNext(newBuffer);
			currentBuffer = newBuffer;
		}
	}
	
	/**
	 * @return the current byte[] buffer.
	 */
	public byte[] getBuffer(){
		return currentBuffer.getBuffer();
	}
	
	/**
	 * @return true if there is a buffer next to the current one.
	 */
	public boolean isNext(){
		return currentBuffer.getNext() != null;
	}
	
	/**
	 * Moves the pointer to the next buffer (reading)
	 */
	public void  nextBuffer(){
		currentBuffer = currentBuffer.getNext();		
	}
	
	/**
	 * Empty method
	 */
	public void flush() throws IOException {}	
}