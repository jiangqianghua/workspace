package uk.ac.gla.terrier.structures;
/** Marks an index structure class that can be closed. This interface differs
 * from that in java.io in that this method 
 * does not throw an IOException. It is used by the Index object
 * to ensure that all data structures are closed, to prevent file
 * handles and the like leaking.
 * @author Craig Macdonald
 * @since 2.0
 * @version $Revision: 1.2 $
 */
public interface Closeable {
	/** close this index data structure */
	public void close();
}
