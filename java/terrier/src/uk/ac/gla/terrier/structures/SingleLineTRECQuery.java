package uk.ac.gla.terrier.structures;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import uk.ac.gla.terrier.utility.ApplicationSetup;
import uk.ac.gla.terrier.utility.Files;

/** This class can be used to extract batch queries from a simpler format than the TREC format.
  * In particular, this class reads queries, one per line, verbatim from the specified file(s).
  * Empty lines and lines starting with # are ignored.
  * Moreover, it assumes that the first token on each line is the query Id. This can be controlled
  * by the properties <tt>SingleLineTRECQuery.queryid.exists</tt> (default true).
  * Use this class by specifying <tt>trec.topics.parser=SingleLineTRECQuery</tt> and running
  * TRECQuerying or TrecTerrier as normal. */
public class SingleLineTRECQuery extends TRECQuery
{
	/** Constructor - default */
	public SingleLineTRECQuery() {
		super();
	}

	/** Reads queries from the specified file */
	public SingleLineTRECQuery(File queryfile){
		super(queryfile);
	}

	/** Reads querries from the specified filename */
	public SingleLineTRECQuery(String queryfilename){
		super(queryfilename);
	}

	/** Extracts queries from the specified filename, adding their contents to vecStringQueries and the
	  * corresponding query ids to vecStringIds. 
	  * @return true if some queries were successfully read */
	public boolean extractQuery(String queryfilename, Vector vecStringQueries, Vector vecStringIds)
	{
		boolean gotSome = false;
		final boolean QueryLineHasQueryID = Boolean.parseBoolean(ApplicationSetup.getProperty("SingleLineTRECQuery.queryid.exists","true"));
		logger.info("Extracting queries from "+queryfilename + " - queryids "+QueryLineHasQueryID);
		try {
			BufferedReader br;
			File queryFile = new File(queryfilename);
			if (!(queryFile.exists() && queryFile.canRead())) {
				logger.error("The topics file " + queryfilename + " does not exist, or it cannot be read.");
				return false;
			}
			br = Files.openFileReader(queryfilename, "UTF-8");	

			String line = null;
			int queryCount =0;
			while((line = br.readLine()) != null)
			{
				line = line.trim();
				if (line.startsWith("#"))
				{
					//comment encountered - skip line
					continue;
				}
				queryCount++;
				String queryID;
				String query;
				if (QueryLineHasQueryID)
				{
					final int firstSpace = line.indexOf(' ');
					queryID = line.substring(0,firstSpace);
					query = line.substring(firstSpace+1).replaceAll("\\.", " ");
				}
				else
				{
					query = line.replaceAll("\\.", " ");
					queryID = ""+queryCount;
				}
				vecStringQueries.add(query);
				vecStringIds.add(queryID);
				gotSome = true;
				logger.debug("Extracted queryID "+queryID+" "+query);
			}

		} catch (IOException ioe) {
			logger.error("IOException while extracting queries: ",ioe);	
			return gotSome;
		}
		logger.info("Extracted "+ vecStringQueries.size() + " queries");
		return gotSome;
	}
}
