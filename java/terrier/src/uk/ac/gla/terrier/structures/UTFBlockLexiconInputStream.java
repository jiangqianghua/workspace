/*
 * Terrier - Terabyte Retriever 
 * Webpage: http://ir.dcs.gla.ac.uk/terrier 
 * Contact: terrier{a.}dcs.gla.ac.uk
 * University of Glasgow - Department of Computing Science
 * http://www.gla.ac.uk/
 * 
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific language governing rights and limitations
 * under the License.
 *
 * The Original Code is BlockLexiconInputStream.java.
 *
 * The Original Code is Copyright (C) 2004-2008 the University of Glasgow.
 * All Rights Reserved.
 *
 * Contributor(s):
 *   Douglas Johnson <johnsoda{a.}dcs.gla.ac.uk> (original author)
 *   Vassilis Plachouras <vassilis{a.}dcs.gla.ac.uk> 
 */
package uk.ac.gla.terrier.structures;
import java.io.*;
import java.util.Arrays;

import org.apache.log4j.Logger;

import uk.ac.gla.terrier.utility.ApplicationSetup;
import uk.ac.gla.terrier.utility.StringTools;
/**
 * An input stream for accessing sequentially the entries
 * of a block lexicon.
 * @author Douglas Johnson, Vassilis Plachouras
 * @version $Revision: 1.11 $
 */
public class UTFBlockLexiconInputStream extends BlockLexiconInputStream {
	/** The logger used */
	private static Logger logger = Logger.getRootLogger();
	/** The term represented as an array of bytes.*/
	protected byte[] termCharacters = new byte[ApplicationSetup.STRING_BYTE_LENGTH +2];
	
	/** A zero buffer for writing to the file.*/
	private byte[] junkBuffer = new byte[ApplicationSetup.STRING_BYTE_LENGTH];
	/** 
	 * The total number of different blocks a term appears in.
	 */
	protected int blockFrequency;
	/**
	 * A default constructor.
	 */
	public UTFBlockLexiconInputStream() {
		super();
		entrySize = UTFBlockLexicon.lexiconEntryLength;
	}
	/**
	 * A constructor given the filename.
	 * @param filename java.lang.String the name of the lexicon file.
	 */
	public UTFBlockLexiconInputStream(String filename) {
		super(filename);
		entrySize = UTFBlockLexicon.lexiconEntryLength;
	}
	/**
	 * A constructor given the filename.
	 * @param file java.io.File the name of the lexicon file.
	 */
	public UTFBlockLexiconInputStream(File file) {
		super(file);
		entrySize = UTFBlockLexicon.lexiconEntryLength;
	}
	/**
	 * Read the next lexicon entry.
	 * @return the number of bytes read if there is no error, 
	 *		 otherwise returns -1 in case of EOF
	 * @throws java.io.IOException if an I/O error occurs
	 */
	public int readNextEntry() throws IOException {
		try {
			startBitOffset = (byte) (endBitOffset + 1);
			startOffset = endOffset;
			if (startBitOffset == 8) {
				startOffset = endOffset + 1;
				startBitOffset = 0;
			}
			
			term = lexiconStream.readUTF();
			lexiconStream.read(junkBuffer, 0, ApplicationSetup.STRING_BYTE_LENGTH - StringTools.utf8_length(term));
			
			termId = lexiconStream.readInt();
			documentFrequency = lexiconStream.readInt();
			blockFrequency = lexiconStream.readInt();
			termFrequency = lexiconStream.readInt();
			endOffset = lexiconStream.readLong();
			endBitOffset = lexiconStream.readByte();
			numPointersRead += documentFrequency;
			numTokensRead += termFrequency;
			numTermsRead++;
			return Lexicon.lexiconEntryLength;
		} catch (EOFException eofe) {
			return -1;
		}
	}
	
	/**
		* Returns the number of entries in the lexicon file.
		*/
		public int numberOfEntries(){
				return (int)(lexiconFilelength / UTFBlockLexicon.lexiconEntryLength);
		}
	
	/**
	 * Read the next lexicon entry, where the term is saved as a byte array. No attempt is
	 * made to parse the byte array and the padding bytes into a String. Use this method when
	 * you want to get the bytes of the string using getTermCharacters(). This method does
	 * NOT work with getTerm()
	 * @return the number of bytes read if there is no error, 
	 *		 otherwise returns -1 in case of EOF
	 * @throws java.io.IOException if an I/O error occurs
	 */
	public int readNextEntryBytes() throws IOException {
		try {
			startBitOffset = (byte) (endBitOffset + 1);
			startOffset = endOffset;
			if (startBitOffset == 8) {
				startOffset = endOffset + 1;
				startBitOffset = 0;
			}

			Arrays.fill(termCharacters, (byte)0);
			lexiconStream.read(termCharacters, 0, ApplicationSetup.STRING_BYTE_LENGTH +2);

			termId = lexiconStream.readInt();
			documentFrequency = lexiconStream.readInt();
			blockFrequency = lexiconStream.readInt();
			termFrequency = lexiconStream.readInt();
			endOffset = lexiconStream.readLong();
			endBitOffset = lexiconStream.readByte();
			numPointersRead += documentFrequency;
			numTokensRead += termFrequency;
			numTermsRead++;
			return Lexicon.lexiconEntryLength;
		} catch (EOFException eofe) {
			return -1;
		}
	}
	
	/**
	 * Prints out the contents of the lexicon file to check.
	 */
	public void print() {
		int i = 0; //counter
		int entryLength = Lexicon.lexiconEntryLength;
		try {
			while (readNextEntry() != -1) {
				System.out.println(
					""
						+ (i * entryLength)
						+ ", "
						+ term.trim()
						+ ", "
						+ termId
						+ ", "
						+ documentFrequency
						+ ", "
						+ blockFrequency
						+ ", "
						+ termFrequency
						+ ", "
						+ endBitOffset);
				i++;
			}
		} catch (IOException ioe) {
			logger.fatal(
				"Input/Output exception while reading the document index input stream. Stack trace follows.");
			logger.fatal("Exiting ...");
			System.exit(1);
		}
	}

	/**
	 * Returns the string representation of the term.
	 * @return the string representation of the already found term.
	 */
	public String getTerm()
	{
		return term;
	}
	
	/** 
	 * Returns the bytes of the String. Only valid is readNextEntryByte was used.
	 * @return the byte array holding the term's byte representation
	 */
	public byte[] getTermCharacters() {
		return termCharacters;
	}
}
