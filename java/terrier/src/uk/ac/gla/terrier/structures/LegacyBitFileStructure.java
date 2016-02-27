package uk.ac.gla.terrier.structures;

import java.io.IOException;

/** This is marker interface which is used by Index to detect if an index structure
 *  has been written by Terrier 1.xx version of Terrier, and that hence it should
 *  be reopened using the OldBitFile classes. Implementing classes provide a
 *  reOpenLegacyBitFile() method, which reopens their internal file using the
 *  older OldBitFile class.
 *  @author Craig Macdonald
 *  @version $Revision: 1.1 $
 *  @since 2.0
 */
interface LegacyBitFileStructure {
	/** forces the data structure to reopen the underlying bitfile
	 *  using the legacy implementation of BitFile (OldBitFile)
	 * @throws IOException
	 */
	public void reOpenLegacyBitFile() throws IOException;
	
}
