package uk.ac.gla.terrier.querying.parser;

/** Thrown by the Manager when it cannot parse the query */
public class QueryParserException extends Exception
{
	public QueryParserException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public QueryParserException(String message)
	{
		super(message);
	}
}
