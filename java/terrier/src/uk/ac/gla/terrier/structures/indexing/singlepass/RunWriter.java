package uk.ac.gla.terrier.structures.indexing.singlepass;

import java.io.DataOutputStream;
import java.io.IOException;

import uk.ac.gla.terrier.compression.BitOutputStream;
import uk.ac.gla.terrier.compression.MemorySBOS;
import uk.ac.gla.terrier.utility.ApplicationSetup;
import uk.ac.gla.terrier.utility.Files;
import uk.ac.gla.terrier.utility.StringTools;
/**
 * This class writes a run to disk. The data written depends on the specific subclass.
 * This one, writes the Nt, TF and the <docid, tf> sequence.
 * It also writes the max frequency of a term in the run (useful for allocating memory during the merging phase).
 * @author Roi Blanco
 */
public class RunWriter {
	/** Underlying {@link uk.ac.gla.terrier.compression.BitOutputStream} to write the compressed objects */
	protected final BitOutputStream bos;
	/** Underlying {@link java.io.DataOutputStream} to write the term Strings */
	protected final DataOutputStream stringDos;
	/** should we use the UTF encoding to write out terms? */
	protected final boolean UTFindexing = ("true".equals(ApplicationSetup.getProperty("string.use_utf", "false")));

	/** other constructor for use by subclasses */
	protected RunWriter(BitOutputStream bos, DataOutputStream stringDos) throws IOException
	{
		this.bos = bos;
		this.stringDos = stringDos;
	}
	
	/**
	 * Instanciates a RunWriter, given the filenames to write.
	 * @param fileName name of the file to write the posting lists data. 
	 * @param termsFile name of the file to write the terms.
	 * @throws IOException if an I/O error occurs.
	 */
	public RunWriter(String fileName, String termsFile) throws IOException{
		bos = new BitOutputStream(fileName);
		stringDos = new DataOutputStream( Files.writeFileStream(termsFile));
	}
	
	/**
	 * Writes the headers of the run.
	 * @param maxSize max size of a posting.
	 * @param size number of postings in the run.
	 * @throws IOException if an I/O error occurs.
	 */
	public void beginWrite(int maxSize, int size) throws IOException{			
		bos.writeGamma(maxSize);
		bos.writeGamma(size);		
	}
	
	/**
	 * Writes the information for a given term.
	 * @param term the term to write.
	 * @param post the Posting with the data of the term.
	 * @throws IOException if an I/O error occurs.
	 */
	public void writeTerm(final String term, final Posting post) throws IOException{		
		
		if (UTFindexing)
		{
			bos.writeGamma(StringTools.utf8_length(term));
			stringDos.writeUTF(term);
		}
		else
		{
			bos.writeGamma(term.length());
			stringDos.writeBytes(term);
		}
		bos.writeGamma(post.getDocF());
		bos.writeGamma(post.getTF());
		//System.err.println("Writing "+term + " TF="+post.getTF()+ " Nt="+post.getDocF());
		final MemorySBOS Docs = post.getDocs();
		Docs.pad();
		/* when reading, ie RunReader and it's children classes
		 * an align call is required here. */
		bos.append(Docs.getMOS().getBuffer(), Docs.getMOS().getPos());
	}
		
	/**
	 * Closes the output streams.
	 * @throws IOException if an I/O error occurs.
	 */
	public void finishWrite() throws IOException{			
		bos.close();
		stringDos.close();
	}
}
