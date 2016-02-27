package uk.ac.gla.terrier.structures.indexing.singlepass;

import java.io.IOException;

import uk.ac.gla.terrier.structures.trees.BlockFieldDocumentTreeNode;

/**
 * Class representing a posting list in memory with block information
 * It keeps the information for <code>DF, TF</code>, and the sequence <code>[doc, tf, blockCount, [blockId]] </code>
 * @author Roi Blanco
 *
 */
public class BlockPosting extends Posting{
	/**
	 * Writes the first document in the posting list.
	 * @param doc the document identifier.
	 * @param frequency the frequency of the term in the document.
	 * @param tree BlockFieldDocumentTreeNode containing the block information
	 * @throws IOException if an I/O error ocurrs.
	 */	
	public void writeFirstDoc(final int doc, final int frequency, BlockFieldDocumentTreeNode tree) throws IOException{
		super.writeFirstDoc(doc, frequency);
		int[] blockids = tree.getBlockIds();
		final int blockCount = blockids.length;
		
		docIds.writeUnary(blockCount+1);
		if (blockCount > 0)
		{	
			docIds.writeGamma(blockids[0]+1);
			for (int i=1; i<blockCount; i++) {
				docIds.writeGamma(blockids[i] - blockids[i-1]);
			}
		}
//		BlockTree blockTree = tree.blockTree;
//		BlockTreeNode[] blockTreeNodes = blockTree.toArray();
//		docIds.writeUnary(blockTreeNodes.length);
//		
//		BlockTreeNode blockTreeNode1 = blockTreeNodes[0];
//		docIds.writeGamma(blockTreeNode1.blockId+1);
//		
//		int blockTreeNodesLength = blockTreeNodes.length;
//		for (int i=1; i<blockTreeNodesLength; i++) {
//			blockTreeNode1 = blockTreeNodes[i];
//			docIds.writeGamma(blockTreeNode1.blockId - blockTreeNodes[i-1].blockId);
//		}		
	}
	
	/**
	 * Writes the first document in the posting list.
	 * @param doc the document identifier.
	 * @param frequency the frequency of the term in the document.
	 * @param blockids the blockids for all the term
	 * @throws IOException if an I/O error ocurrs.
	 */	
	public void writeFirstDoc(final int doc, final int frequency, final int[] blockids) throws IOException{
		super.writeFirstDoc(doc, frequency);
		final int blockCount = blockids.length;
		
		docIds.writeUnary(blockCount+1);
		if (blockCount > 0)
		{	
			docIds.writeGamma(blockids[0]+1);
			for (int i=1; i<blockCount; i++) {
				docIds.writeGamma(blockids[i] - blockids[i-1]);
			}
		}
	}
	
	/**
	 * Inserts a new document in the posting list. Document insertions must be done
	 * in order.  
	 * @param doc the document identifier.
	 * @param freq the frequency of the term in the document.
	 * @param tree BlockFieldDocumentTreeNode containing the block information
	 * @return the updated term frequency.
	 * @throws IOException if and I/O error occurs.
	 */
	public int insert(final int doc, final int freq, BlockFieldDocumentTreeNode tree) throws IOException{		
		int c = insert(doc, freq);
		int[] blockids = tree.getBlockIds();
		final int blockCount = blockids.length;
		
		docIds.writeUnary(blockCount+1);
		if (blockCount > 0)
		{	
			docIds.writeGamma(blockids[0]+1);
			for (int i=1; i<blockCount; i++) {
				docIds.writeGamma(blockids[i] - blockids[i-1]);
			}
		}
//		BlockTree blockTree = tree.blockTree;
//		BlockTreeNode[] blockTreeNodes = blockTree.toArray();
//		
//		docIds.writeUnary(blockTreeNodes.length);
//		
//		BlockTreeNode blockTreeNode1 = blockTreeNodes[0];
//		docIds.writeGamma(blockTreeNode1.blockId+1);
//		
//		int blockTreeNodesLength = blockTreeNodes.length;
//		for (int i=1; i<blockTreeNodesLength; i++) {
//			blockTreeNode1 = blockTreeNodes[i];
//			docIds.writeGamma(blockTreeNode1.blockId - blockTreeNodes[i-1].blockId);
//		}			 
		return c;
	}
	
	/**
	 * Inserts a new document in the posting list. Document insertions must be done
	 * in order.  
	 * @param doc the document identifier.
	 * @param freq the frequency of the term in the document.
	 * @param blockids the blockids for all the term
	 * @return the updated term frequency.
	 * @throws IOException if and I/O error occurs.
	 */
	public int insert(final int doc, final int freq, final int[] blockids) throws IOException{
		final int c = insert(doc, freq);
		final int blockCount = blockids.length;
		
		docIds.writeUnary(blockCount+1);
		if (blockCount > 0)
		{	
			docIds.writeGamma(blockids[0]+1);
			for (int i=1; i<blockCount; i++) {
				docIds.writeGamma(blockids[i] - blockids[i-1]);
			}
		}
		return c;
	}
	
}
