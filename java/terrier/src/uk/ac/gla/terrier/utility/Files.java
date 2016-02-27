package uk.ac.gla.terrier.utility;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.zip.CRC32;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/** Utililty class for opening readers/writers and input/output streams to files. Handles gzipped files
  * on the fly, ie if a file ends in ".gz" or ".GZ", then it will be opened using a GZipInputStream/GZipOutputStream.
  * All returned Streams, Readers, Writers etc are Buffered. If a charset encoding is not specified, then the system default is used.
  */
public class Files
{
	/** Opens a reader to the file called file. Provided for easy overriding for encoding support etc in 
	  * child classes. Called from openNextFile().
	  * @param file File to open.
	  * @return BufferedReader of the file
	  */
	public static BufferedReader openFileReader(File file) throws IOException
	{
		return openFileReader(file.getPath(),null);
	}

	/** Opens a reader to the file called filename. Provided for easy overriding for encoding support etc in
	  * child classes. Called from openNextFile().
	  * @param file File to open.
	  * @param charset Character set encoding of file. null for system default.
	  * @return BufferedReader of the file
	  */
	public static BufferedReader openFileReader(File file, String charset) throws IOException
	{
		return openFileReader(file.getPath(), charset);
	}

	/** Opens a reader to the file called filename. Provided for easy overriding for encoding support etc in
	  * child classes. Called from openNextFile().
	  * @param filename File to open.
	  * @return BufferedReader of the file
	  */
	public static BufferedReader openFileReader(String filename) throws IOException
	{
		return openFileReader(filename,null);
	}
	

	/** Opens a reader to the file called filename. Provided for easy overriding for encoding support etc in 
	  * child classes. Called from openNextFile().
	  * @param filename File to open.
	  * @param charset Character set encoding of file. null for system default.
	  * @return BufferedReader of the file
	  */
	public static BufferedReader openFileReader(String filename, String charset) throws IOException
	{
		BufferedReader rtr = null;
		if (filename.toLowerCase().endsWith("gz")) {
			rtr = new BufferedReader(
				 charset == null				
				? new InputStreamReader(new GZIPInputStream(new FileInputStream(filename)))
				: new InputStreamReader(new GZIPInputStream(new FileInputStream(filename)), charset)
			);
		} else {
			rtr = new BufferedReader(
				charset == null 
					? new FileReader(filename)
					: new InputStreamReader(new FileInputStream(filename), charset)
				);
		}
		return rtr;
	}

	/** Opens an InputStream to a file called file. 
	  * @param file File to open.
	  * @return InputStream of the file
	  */
	public static InputStream openFileStream(File file) throws IOException
	{
		return openFileStream(file.getPath());
	}

	/** Opens an InputStream to a file called filename.
	  * @param filename File to open.
	  * @return InputStream of the file
	  */
	public static InputStream openFileStream(String filename) throws IOException
	{
		BufferedInputStream rtr = null;
		if (filename.toLowerCase().endsWith("gz")) {
			rtr = new BufferedInputStream(
				new GZIPInputStream(new FileInputStream(filename)));
		} else {
			rtr = new BufferedInputStream(new FileInputStream(filename));
		}
		return (InputStream) rtr;
	}

	/** Opens an OutputStream to a file called file.
	  * @param file File to open.
	  * @return OutputStream of the file
	  */
	public static OutputStream writeFileStream(File file) throws IOException
	{
		return writeFileStream(file.getPath());
	}

	/** Opens an OutputStream to a file called filename.
	  * @param filename File to open.
	  * @return OutputStream of the file
	  */
	public static OutputStream writeFileStream(String filename) throws IOException
	{
		BufferedOutputStream rtr = null;
		if (filename.toLowerCase().endsWith("gz")) {
			rtr = new BufferedOutputStream(
				new GZIPOutputStream(new FileOutputStream(filename)));
		} else {
			rtr = new BufferedOutputStream(new FileOutputStream(filename));
		}
		return (OutputStream) rtr;
	}

	/** Opens an Writer to a file called file. System default encoding will be used.
	  * @param file File to open.
	  * @return Writer of the file
	  */
	public static Writer writeFileWriter(File file) throws IOException
	{
		return writeFileWriter(file.getPath(), null);
	}

	/** Opens an Writer to a file called file.
	  * @param file File to open.
	  * @param charset Character set encoding of file. null for system default.
	  * @return Writer of the file
	  */
	public static Writer writeFileWriter(File file, String charset) throws IOException
	{
		return writeFileWriter(file.getPath(), charset);
	}
	/** Opens an Writer to a file called file. System default encoding will be used.
	  * @param filename File to open.
	  * @return Writer of the file
	  */
	public static Writer writeFileWriter(String filename) throws IOException
	{
		return writeFileWriter(filename, null);
	}

	/** Opens an Writer to a file called file.
	  * @param filename File to open.
	  * @param charset Character set encoding of file. null for system default.
	  * @return Writer of the file
	  */
	public static Writer writeFileWriter(String filename, String charset) throws IOException
	{
		BufferedWriter rtr = null;
		if (filename.toLowerCase().endsWith("gz")) {
			rtr = new BufferedWriter(
				 charset == null
				? new OutputStreamWriter(new GZIPOutputStream(new FileOutputStream(filename)))
				: new OutputStreamWriter(new GZIPOutputStream(new FileOutputStream(filename)), charset)
			);
		} else {
			rtr = new BufferedWriter(
				charset == null
					? new FileWriter(filename)
					: new OutputStreamWriter(new FileOutputStream(filename), charset)
				);
		}
		return rtr;		
	}

	//from: http://schmidt.devlib.org/java/copy-file.html
	/** buffer size for copying files */
	private static final int bufferSize = 4 * 1024;
	/** print time taken when copying file */
	private static final boolean clock = false;
	/** CRC32 the file while being copied, to allow
	  * integrity to be verified */
	private static final boolean verify = false;

	/** Copy a file from srcFile to destFile.
	  * @return null if OK
	  * @throws IOException if there was a problem copying */
	public static Long copyFile(String srcFilename, String destFilename)
		throws IOException {
		return copyFile(new File(srcFilename), new File(destFilename));
	}
	/** Copy a file from srcFile to destFile.
	  * @return null if OK
	  * @throws IOException if there was a problem copying */	
	public static Long copyFile(File srcFile, File destFile)
		throws IOException {
		final InputStream in = openFileStream(srcFile);
		final OutputStream out = writeFileStream(destFile);
		long millis = System.currentTimeMillis();
		final CRC32 checksum = verify ? new CRC32() : null;
		if (verify) {
			checksum.reset();
		}
		byte[] buffer = new byte[bufferSize];
		int bytesRead;
		if (verify)
			while ((bytesRead = in.read(buffer)) >= 0) {
				checksum.update(buffer, 0, bytesRead);
				out.write(buffer, 0, bytesRead);
			}
		else
			while ((bytesRead = in.read(buffer)) >= 0)
				out.write(buffer, 0, bytesRead);	
		out.close();
		in.close();
		if (clock) {
			millis = System.currentTimeMillis() - millis;
			//System.err.println("Second(s): " + (millis/1000L));
		}
		if (verify) {
			return new Long(checksum.getValue());
		} else {
			return null;
		}
	}

	/** Returns the CRC checksum of denoted file */
	public static Long createChecksum(File file) throws IOException {
		long millis = System.currentTimeMillis();
		InputStream in = openFileStream(file);
		CRC32 checksum = new CRC32();
		checksum.reset();
		byte[] buffer = new byte[bufferSize];
		int bytesRead;
		while ((bytesRead = in.read(buffer)) >= 0) {
			checksum.update(buffer, 0, bytesRead);
		}
		in.close();
		if (clock) {
			millis = System.currentTimeMillis() - millis;
			//System.out.println("Second(s): " + (millis/1000L));
		}
		return new Long(checksum.getValue());
	}



}
