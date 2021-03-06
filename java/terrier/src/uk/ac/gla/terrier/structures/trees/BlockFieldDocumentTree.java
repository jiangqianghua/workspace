/*
 * Terrier - Terabyte Retriever 
 * Webpage: http://ir.dcs.gla.ac.uk/terrier 
 * Contact: terrier{a.}dcs.gla.ac.uk
 * University of Glasgow - Department of Computing Science
 * http://www.gla.ac.uk/
 * 
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific language governing rights and limitations
 * under the License.
 *
 * The Original Code is BlockFieldDocumentTree.java.
 *
 * The Original Code is Copyright (C) 2004-2008 the University of Glasgow.
 * All Rights Reserved.
 *
 * Contributor(s):
 *   Douglas Johnson <johnsoda{a.}dcs.gla.ac.uk> (original author)
 *   Vassilis Plachouras <vassilis{a.}dcs.gla.ac.uk>
 */
package uk.ac.gla.terrier.structures.trees;
import java.util.Set;
/**
 * A binary tree that is used when indexing with
 * blocks and html information.
 * @author Douglas Johnson, Vassilis Plachouras
 * @version $Revision: 1.14 $
 * @deprecated
 */
public class BlockFieldDocumentTree extends FieldDocumentTree {
	        
	/** The root of the binary tree.*/
	protected BlockFieldDocumentTreeNode treeRoot = null;
	/** The number of blocks in the tree.*/
	protected int numberOfBlocks=0;
	/** A TreeNode buffer used in the traverseInOrder method.*/
	private BlockFieldDocumentTreeNode[] nodeBuffer;	
	
	/** 
	 * Empties the tree.
	 */
	public void empty() {
		super.empty();
		numberOfBlocks = 0;
	}
	
	/**
	 * Returns the number of blocks in the tree.
	 * @return int the number of pointers in the tree.
	 */
	public int getNumberOfBlocks() {
		return numberOfBlocks;
	}
	/**
	 * Inserts a term with a given block id in the tree.
	 * @param newTerm the term to insert.
	 * @param blockId the block id of the term.
	 */
	public void insert(String newTerm, int blockId) {
		if (treeRoot == null) {
			treeRoot = new BlockFieldDocumentTreeNode(newTerm, blockId);
			numberOfNodes++;
			numberOfPointers++;
			numberOfBlocks++;
		} else {
			BlockFieldDocumentTreeNode tmpNode = (BlockFieldDocumentTreeNode)treeRoot;
			while (true) {
				int lexicographicOrder = tmpNode.term.compareTo(newTerm);
				if (lexicographicOrder == 0) {
					tmpNode.frequency++;
					numberOfPointers++;
					if(tmpNode.insertBlock(blockId))
						numberOfBlocks++;
					break;
				} else if (lexicographicOrder > 0) {
					if (tmpNode.left == null) {
						tmpNode.left = new BlockFieldDocumentTreeNode(newTerm, blockId);
						numberOfNodes++;
						numberOfPointers++;
						numberOfBlocks++;
						break;
					} else
						tmpNode = tmpNode.left;
				} else {
					if (tmpNode.right == null) {
						tmpNode.right = new BlockFieldDocumentTreeNode(newTerm, blockId);
						numberOfNodes++;
						numberOfPointers++;
						numberOfBlocks++;
						break;
					} else
						tmpNode = tmpNode.right;
				}
			}
		}
	}
		
	/**
	 * Inserts a term with a given block id, in a specified field, in the tree.
	 * @param newTerm the term to insert.
	 * @param blockId the block id of the term.
	 * @param field the field the term appears in 
	 */
	public void insert(String newTerm, int blockId, String field) {
		if (treeRoot == null) {
			treeRoot = new BlockFieldDocumentTreeNode(newTerm, blockId, field);
			numberOfNodes++;
			numberOfPointers++;
			numberOfBlocks++;
		} else {
			BlockFieldDocumentTreeNode tmpNode = (BlockFieldDocumentTreeNode)treeRoot;
			while (true) {
				int lexicographicOrder = tmpNode.term.compareTo(newTerm);
				if (lexicographicOrder == 0) {
					tmpNode.frequency++;
					tmpNode.addToFieldScore(field);
					numberOfPointers++;
					if(tmpNode.insertBlock(blockId))
						numberOfBlocks++;
					break;
				} else if (lexicographicOrder > 0) {
					if (tmpNode.left == null) {
						tmpNode.left = new BlockFieldDocumentTreeNode(newTerm, blockId, field);
						numberOfNodes++;
						numberOfPointers++;
						numberOfBlocks++;
						break;
					} else
						tmpNode = tmpNode.left;
				} else {
					if (tmpNode.right == null) {
						tmpNode.right = new BlockFieldDocumentTreeNode(newTerm, blockId, field);
						numberOfNodes++;
						numberOfPointers++;
						numberOfBlocks++;
						break;
					} else
						tmpNode = tmpNode.right;
				}
			}
		}
	}
	
	/**
	 * Inserts a term with a given block id, in a specified field, in the tree.
	 * @param newTerm the term to insert.
	 * @param blockId the block id of the term.
	 * @param fields the fields the term appears in 
	 */
	public void insert(String newTerm, int blockId, Set<String> fields) {
		if (treeRoot == null) {
			treeRoot = new BlockFieldDocumentTreeNode(newTerm, blockId, fields);
			numberOfNodes++;
			numberOfPointers++;
			numberOfBlocks++;
		} else {
			BlockFieldDocumentTreeNode tmpNode = (BlockFieldDocumentTreeNode)treeRoot;
			while (true) {
				int lexicographicOrder = tmpNode.term.compareTo(newTerm);
				if (lexicographicOrder == 0) {
					tmpNode.frequency++;
					tmpNode.addToFieldScore(fields);
					numberOfPointers++;
					if(tmpNode.insertBlock(blockId))
						numberOfBlocks++;
					//if(tmpNode.blockTree.insert(blockId)){
					//	numberOfBlocks++;
					//}
					break;
				} else if (lexicographicOrder > 0) {
					if (tmpNode.left == null) {
						tmpNode.left = new BlockFieldDocumentTreeNode(newTerm, blockId, fields);
						numberOfNodes++;
						numberOfPointers++;
						numberOfBlocks++;
						break;
					} else
						tmpNode = tmpNode.left;
				} else {
					if (tmpNode.right == null) {
						tmpNode.right = new BlockFieldDocumentTreeNode(newTerm, blockId, fields);
						numberOfNodes++;
						numberOfPointers++;
						numberOfBlocks++;
						break;
					} else
						tmpNode = tmpNode.right;
				}
			}
		}
	}
	/**
	 * Returns an array of the term nodes of the tree.
	 */
	public FieldDocumentTreeNode[] toArray() {
		nodeBuffer = new BlockFieldDocumentTreeNode[numberOfNodes];
		counter = 0;
		traversePreOrder(treeRoot);
		return nodeBuffer;
	}
	
	/** 
	 * The helper for returning the buffer of terms of the document.
	 * @param node The node to insert to the buffer
	 */
	protected void traversePreOrder(BlockFieldDocumentTreeNode node) {
		if (node == null) 
			return;
		nodeBuffer[counter] = node;
		counter++;
		traversePreOrder(node.left);
		traversePreOrder(node.right);
	}
}
