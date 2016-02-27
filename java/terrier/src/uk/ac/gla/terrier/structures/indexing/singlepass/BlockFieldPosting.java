package uk.ac.gla.terrier.structures.indexing.singlepass;

import java.io.IOException;

import uk.ac.gla.terrier.structures.trees.BlockFieldDocumentTreeNode;
import uk.ac.gla.terrier.utility.FieldScore;
/**
 * Class representing a posting list in memory containing fields and block iformation.
 * It keeps the information for <code>tf, df, field</code> and the sequence <code>[doc, idf, bockNo [blockId]]</code>
 * @author Roi Blanco
 *
 */
public class BlockFieldPosting extends BlockPosting{
	/** The number of different fields that are used for indexing field information.*/	
	protected static final int fieldTags = FieldScore.FIELDS_COUNT;
	/**
	 * Writes the first document in the posting list.
	 * @param doc the document identifier.
	 * @param frequency the frequency of the term in the document.
	 * @param tree BlockFieldDocumentTreeNode containing the block information
	 * @throws IOException if an I/O error ocurrs.
	 */	
	public void writeFirstDoc(final int doc, final int frequency, BlockFieldDocumentTreeNode tree) throws IOException{
		super.writeFirstDoc(doc, frequency);
		//write the blocks in the memoryStream
		docIds.writeBinary(fieldTags,tree.getFieldScore());
		
		
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
	}
	
	public void  writeFirstDoc(final int doc, final int frequency, int fieldScore, int[] blockids) throws IOException{
		super.writeFirstDoc(doc, frequency);
		if (fieldTags> 0)
			docIds.writeBinary(fieldTags, fieldScore);
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
	  docIds.writeBinary(fieldTags, tree.getFieldScore());
	  
	  int[] blockids = tree.getBlockIds();
	  final int blockCount = blockids.length;
	  docIds.writeUnary(blockCount+1);
	  if (blockCount  >0)
	  {	
	  	docIds.writeGamma(blockids[0]+1);
	  	for (int i=1; i<blockCount; i++) {
			  docIds.writeGamma(blockids[i] - blockids[i-1]);
	  	}
	  }
	  
//	  BlockTree blockTree = tree.blockTree;
//		BlockTreeNode[] blockTreeNodes = blockTree.toArray();
//		
//		docIds.writeUnary(blockTreeNodes.length);
//		
//		BlockTreeNode blockTreeNode1 = blockTreeNodes[0];
//		docIds.writeGamma(blockTreeNode1.blockId+1);		
//		int blockTreeNodesLength = blockTreeNodes.length;
//		for (int i=1; i<blockTreeNodesLength; i++) {
//			blockTreeNode1 = blockTreeNodes[i];
//			docIds.writeGamma(blockTreeNode1.blockId - blockTreeNodes[i-1].blockId);
//		}			 
	  return c;
	}
	
	public int insert(final int doc, final int freq, final int fieldScore, final int[] blockids) throws IOException{
		int c = insert(doc, freq);
		if (fieldTags> 0)
			docIds.writeBinary(fieldTags, fieldScore);
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
