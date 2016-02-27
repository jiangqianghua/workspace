package uk.ac.gla.terrier.structures;
/** Interface defining that an index structure wants access to the
 *  Index object it is associated with. This is usually such that
 *  it can configure itself further using various index properties.
 *  @since 2.0
 *  @author Craig Macdonald
 *  @version $Revision: 1.1 $
 */
public interface IndexConfigurable
{
	/** Tell the implementer which Index object it is associated with.
	  * @param i Index object to use
	  */
	public void setIndex(Index i);
}
