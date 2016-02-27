package uk.ac.gla.terrier.compression;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * This class extends the BitByteOutputStream, so it provides the compression writting functions, but
 * uses a MemoryOutputStream as an underlying OutputStream, so it is needed to be flushed to disk separately.
 * It uses BitByteOutputStream and not BitOutputStream because the MemoryOutputStream class has already its own buffer 
 * (to keep everything in memory), and extra buffering is inadequate.
 * @author Roi Blanco
 *
 */
public class MemorySBOS extends BitByteOutputStream {
	/** The underlying MemoryOutputStream */
	protected MemoryOutputStream mos; 
	/**
	 * Constructor for the class. Instanciates the BitByteOutputStream and MemoryOutputStream classes. 
	 * @throws IOException if an I/O error occurs.
	 */
	public MemorySBOS() throws IOException{
		super();
		mos = new MemoryOutputStream();
		dos = new DataOutputStream(mos);
	}
	
	/**
	 * Writes the underlying MemoryOutputStream in String format.
	 */
	public String toString(){
		return mos.toString();
	}	
		
	/**
	 * @return The underlying MemoryOutputStream.
	 */
	public MemoryOutputStream getMOS(){
		return mos;
	}	
}