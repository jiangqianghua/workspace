package uk.ac.gla.terrier.terms;

/** A do-nothing term pipeline object. 
 *  Simply passes the term onto the next component of the pipeline. 
 *  @author Craig Macdonald
 *  @version $Revision: 1.1 $
 */
public class NoOp implements TermPipeline
{

	/** The implementation of a term pipeline.*/
    protected final TermPipeline next;

    /**
     * Constructs an instance of the class, given the next
     * component in the pipeline.
     * @param next TermPipeline the next component in
     *      the term pipeline.
     */
    public NoOp(TermPipeline next)
    {
        this.next = next;
    }

	/** Pass the term onto the next term pipeline object,
	 *  without making any changes to it.
	 * @param t The term
	 */
	public final void processTerm(final String t)
    {
        if (t == null)
            return;
        next.processTerm(t);
    }
}
