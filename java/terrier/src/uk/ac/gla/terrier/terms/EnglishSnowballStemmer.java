package uk.ac.gla.terrier.terms;

/** English stemmer implmented by Snowball.
  * @author Craig Macdonald <craigm{a.}dcs.gla.ac.uk>
  * @version $Revision: 1.1 $
  */
public class EnglishSnowballStemmer extends SnowballStemmer
{
	public EnglishSnowballStemmer(TermPipeline n)
	{
		super("English", n);
	}
}
