package uk.ac.gla.terrier.indexing;
import org.apache.log4j.Logger;
import uk.ac.gla.terrier.indexing.Collection;
import uk.ac.gla.terrier.utility.ApplicationSetup;

/** 
  * Implements a factory for Collection objects. Pass the name of the desired 
  * Collection(s) to loadCollection() or loadCollections(). loadCollection can take
  * a comma separated list of collections, while loadCollections takes a String array
  * of Collection names, in the same order. The package name <tt>uk.ac.gla.terrier.indexing.</tt>
  * is prepended to any Collections named without any explicit packages.
  * <p>
  * The bottom Collection is specified last, and this is loaded first. Ie Collections
  * are loaded right to left. 
  * For instance: <tt>PassageCollection,TRECCollection</tt> would instantiate a PassageCollection
  * which has as its only constructor parameter, a TREC Collection. Ie, the correponding code would
  * be <tt>new PassageCollection(new TRECCollection());</tt>
  * @author  Craig Macdonald craigm{a.}dcs.gla.ac.uk
  * @version $Revision: 1.3 $
  * @since 1.1.0, 14/01/2007
  */
public class CollectionFactory
{
	/** logger for this class */
	protected static Logger logger = Logger.getRootLogger();

	/** Use the default property <tt>trec.collection.class</tt>, or it's default value TRECCollection */
	public static Collection loadCollections()
	{
		return loadCollection(ApplicationSetup.getProperty("trec.collection.class", "TRECCollection"));
	}

	/** Returns null on error */
	public static Collection loadCollection(String CollectionName)
	{
		return loadCollections( CollectionName.split("\\s*,\\s*") );
	}

	/** Returns null on error */
	public static Collection loadCollections(final String[] collNames)
	{
		final int collCount = collNames.length;

		/* first load the innermost collection, ie the one that does the reading */	
		Collection rtr = null;
		final String firstCollectionName = normaliseCollectionName(collNames[collCount-1]);
		try{
			rtr = (Collection) Class.forName(firstCollectionName).newInstance();
		} catch (Exception e) {
            logger.error("ERROR: First Collection class named "+ firstCollectionName + " not found", e);
            return null;
        }

		/* now load any wrapper collections requested */
		int i = collCount-2;
		if (collCount>1)
		try{
			for(;i>=0;i--)
			{
				Collection newColl = (Collection) Class.forName(normaliseCollectionName(collNames[i]))
					.getConstructor(new Class[]{Collection.class})
         	       .newInstance(new Object[]{rtr});	
				rtr = newColl;
			}
        } catch (Exception e) {
            logger.error("ERROR: Subsequent Collection class named "+ collNames[i] + " not found", e);
			rtr = null;
        }
		/* return the outermost collection */
		return rtr;
	}

	/** prepends <tt>uk.ac.gla.terrier.indexing.</tt> to any Collections without a package */
	public static String normaliseCollectionName(String collectionName)
	{
		if (collectionName.indexOf('.') == -1)
            collectionName = "uk.ac.gla.terrier.indexing."+collectionName;
		return collectionName;
	}
}
