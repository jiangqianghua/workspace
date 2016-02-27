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
 * The Original Code is CollectionStatistics.java.
 *
 * The Original Code is Copyright (C) 2004-2008 the University of Glasgow.
 * All Rights Reserved.
 *
 * Contributor(s):
 *   Vassilis Plachouras <vassilis{a.}dcs.gla.ac.uk> 
 */
package uk.ac.gla.terrier.structures;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

import uk.ac.gla.terrier.utility.ApplicationSetup;
/**
 * This class provides basic statistics for the indexed
 * collection of documents, such as the average length of documents,
 * or the total number of documents in the collection. <br />
 * After indexing, statistics are saved in the PREFIX.log file, along
 * with the classes that should be used for the Lexicon, the DocumentIndex,
 * the DirectIndex and the InvertedIndex. This means that an index knows
 * how it was build and how it should be opened again.
 *
 * @author Gianni Amati, Vassilis Plachouras, Craig Macdonald
 * @version $Revision: 1.28 $
 */
public class CollectionStatistics {
	/** The logger used */
	private static Logger logger = Logger.getRootLogger();
	/** The total number of documents in the collection.*/
	protected int numberOfDocuments;
	
	/** The total number of tokens in the collection.*/
	protected long numberOfTokens;
	/** 
	 * The total number of pointers in the inverted file.
	 * This corresponds to the sum of the document frequencies for
	 * the terms in the lexicon.
	 */
	protected long numberOfPointers;
	/**
	 * The total number of unique terms in the collection.
	 * This corresponds to the number of entries in the lexicon.
	 */
	protected int numberOfUniqueTerms;
	/**
	 * The average length of a document in the collection.
	 */
	protected double averageDocumentLength;

	/**
	 * New log files also contain the class names of the indices that should be
	 * used to open this Index.
	 */
	protected String[] Classes;

	/** If we fail to find a classes line in the .log files, then use these as the
	  * default classes. */
	protected static final String[] defaultClasses = 
		{ "uk.ac.gla.terrier.structures.Lexicon", 
			"uk.ac.gla.terrier.structures.DocumentIndexEncoded", 
			"uk.ac.gla.terrier.structures.DirectIndex", 
			"uk.ac.gla.terrier.structures.InvertedIndex"};

	public CollectionStatistics(int numDocs, int numTerms, long numTokens, long numPointers)
	{
		numberOfDocuments = numDocs;
		numberOfUniqueTerms = numTerms;
		numberOfTokens = numTokens;
		numberOfPointers = numPointers;
		Classes = defaultClasses;
		if (numberOfDocuments != 0)
			averageDocumentLength =
				(1.0D * numberOfTokens) / (1.0D * numberOfDocuments);
		else
			averageDocumentLength = 0.0D;
	}
	
	/** @deprecated */
	public CollectionStatistics() throws IOException
	 {
		this(ApplicationSetup.LOG_FILENAME);
	}

	/** @deprecated */
	public CollectionStatistics(String Path, String Prefix) throws IOException
	{
		this(Path + ApplicationSetup.FILE_SEPARATOR + Prefix + ApplicationSetup.LOG_SUFFIX);
	}

	/** @deprecated */
	public CollectionStatistics(String filename) throws IOException 
	{
		File file = new File(filename);
		if (file.exists()) {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String inputLine = br.readLine();
			String[] stats = inputLine.split(" +");
			numberOfDocuments = Integer.parseInt(stats[0]);
			numberOfTokens = Long.parseLong(stats[1]);
			numberOfUniqueTerms = Integer.parseInt(stats[2]);
			numberOfPointers = Long.parseLong(stats[3]);

			if (numberOfDocuments != 0)
				averageDocumentLength =
					(1.0D * numberOfTokens) / (1.0D * numberOfDocuments);
			else
				averageDocumentLength = 0.0D;

			inputLine = br.readLine();
			if (inputLine != null)
			{
				Classes = inputLine.split(" +");
			}
			else
			{
				Classes = defaultClasses;	
			}
			
			br.close();
		}
		else
		{
			logger.error("Error: Collection statistics not found at "+filename);
		}
	}

	/** @deprecated */
	public static void createCollectionStatistics(
		String Path, String Prefix,
		int docs,
        long tokens,
        int terms,
        long pointers, String[] classes)
	{
		createCollectionStatistics(Path + ApplicationSetup.FILE_SEPARATOR + Prefix + ApplicationSetup.LOG_SUFFIX,
			docs,tokens,terms,pointers,classes);
	}

	/** @deprecated */
	public static void createCollectionStatistics(
        int docs,
        long tokens,
        int terms,
        long pointers, String[] classes)
	{
		createCollectionStatistics(ApplicationSetup.LOG_FILENAME,
			docs,tokens,terms,pointers,classes);
	}
	
	/**
	 * Given the collection statistics, it stores them in a file with 
	 * a standard name.
	 * 
	 * @param docs The number of documents in the collection
	 * @param tokens The number of tokens in the collection
	 * @param terms The number of terms in the collection
	 * @param pointers The number of pointers in the collection
	 * @deprecated
	 */
	public static void createCollectionStatistics(
		String filename,
		int docs,
		long tokens,
		int terms,
		long pointers, String[] classes) {

		try {
			PrintWriter pw = new PrintWriter(new FileWriter(filename));
			/*numberOfDocuments = docs;
			numberOfTokens = tokens;
			numberOfUniqueTerms = terms;
			numberOfPointers = pointers;
			if (numberOfDocuments != 0)
				averageDocumentLength =
					(1.0D * numberOfTokens) / (1.0D * numberOfDocuments);
			else
				averageDocumentLength = 0.0D;*/
			pw.print(docs);
			pw.print(" ");
			pw.print(tokens);
			pw.print(" ");
			pw.print(terms);
			pw.print(" ");
			pw.println(pointers);
			
			//now write out the classes string
			for(int i=0;i<classes.length;i++)
			{
				pw.print(classes[i]);
				//no space at the end of the line
				if (i < classes.length -1)
					pw.print(" ");
			}
			pw.println("");
			pw.close();
		} catch (IOException ioe) {
			logger.fatal(
				"Input/Output exception while initialising the " +
				"CollectionStatistics class. Stack trace follows.",ioe);
			logger.fatal("Exiting ...");
			System.exit(1);
		}
	}
	/**
	 * Returns the documents' average length.
	 * @return the average length of the documents in the collection.
	 */
	public double getAverageDocumentLength() {
		return averageDocumentLength;
	}
	/**
	 * Returns the total number of documents in the collection.
	 * @return the total number of documents in the collection
	 */
	public int getNumberOfDocuments() {
		return numberOfDocuments;
	}
	/**
	 * Returns the total number of pointers in the collection.
	 * @return the total number of pointers in the collection
	 */
	public long getNumberOfPointers() {
		return numberOfPointers;
	}
	/**
	 * Returns the total number of tokens in the collection.
	 * @return the total number of tokens in the collection
	 */
	public long getNumberOfTokens() {
		return numberOfTokens;
	}
	/**
	 * Returns the total number of unique terms in the lexicon.
	 * @return the total number of unique terms in the lexicon
	 */
	public int getNumberOfUniqueTerms() {
		return numberOfUniqueTerms;
	}

	/**
	 * Returns the classes line given in the log file. Used by the
	 * Index to determine which classes it should load for this Index.
	 * @deprecated
	 */
	public String[] getClasses() {
		return Classes;
	}	
}
