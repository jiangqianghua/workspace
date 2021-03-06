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
 * The Original Code is FieldDocumentTree.java.
 *
 * The Original Code is Copyright (C) 2004-2008 the University of Glasgow.
 * All Rights Reserved.
 *
 * Contributor(s):
 *   Douglas Johnson <johnsoda{a.}dcs.gla.ac.uk> (original author)
 *   Craig Macdonlad <craigm{a.}dcs.gla.ac.uk>
 */
package uk.ac.gla.terrier.structures.trees;
import java.util.Set;

/**
 * The binary tree used when we build the direct index
 * with field information. One FieldDocumentTree represents the
 * terms in one document.
 * @see uk.ac.gla.terrier.structures.trees.FieldDocumentTreeNode
 * @author Douglas Johnson, Craig Macdonald
 * @version $Revision: 1.16 $
 * @deprecated
 */
public class FieldDocumentTree {
	/** The root of the binary tree.*/
	protected FieldDocumentTreeNode treeRoot = null;
	/** The number of nodes in the tree.*/
	protected int numberOfNodes;
	/** The number of pointers in the tree.*/
	protected int numberOfPointers;
	/** A counter for using in the traversePreOrder method.*/
	protected int counter;
	
	/** A FieldDocumentTreeNode buffer used in the traversePreOrder method.*/
	private FieldDocumentTreeNode[] nodeBuffer;
	/** 
	 * Empties the tree.
	 */
	public void empty() {
		treeRoot = null;
		numberOfPointers = 0;
		numberOfNodes = 0;
	}
	/**
	* Returns the number of nodes in the tree.
	* @return int the number of nodes in the tree.
	*/
	public int getNumberOfNodes() {
		return numberOfNodes;
	}
	/**
	 * Returns the number of pointers in the tree.
	 * @return int the number of pointers in the tree.
	 */
	public int getNumberOfPointers() {
		return numberOfPointers;
	}

	/** Finds or creates a new node for term newTerm. If a new node
	  * is created, the numberOfNodes and numberOfPointers are incremented.
	  * @return The node found or created representing newTerm
	  */
	protected FieldDocumentTreeNode findOrNew(String newTerm)
	{
		FieldDocumentTreeNode rtr = null;
		if (treeRoot==null) {
			rtr = treeRoot = new FieldDocumentTreeNode(newTerm);
			numberOfNodes++;
			numberOfPointers++;
		} else {
			FieldDocumentTreeNode tmpNode = treeRoot;
			while (true) {
				int lexicographicOrder = tmpNode.term.compareTo(newTerm);
				if (lexicographicOrder == 0) {
					rtr = tmpNode;
					tmpNode.frequency++;
					numberOfPointers++;
					break;
				} else if (lexicographicOrder > 0) {
					if (tmpNode.left==null) {
						rtr = tmpNode.left = new FieldDocumentTreeNode(newTerm);
						numberOfNodes++;
						numberOfPointers++;
						break;
					} else 
						tmpNode = tmpNode.left; 
				} else {/*lexicographicOrder < 0*/
					if (tmpNode.right==null) {
						rtr = tmpNode.right = new FieldDocumentTreeNode(newTerm);
						numberOfNodes++;
						numberOfPointers++;						
						break;
					} else 
						tmpNode = tmpNode.right;
				}
			}
		}
		return rtr;
	}

	/**
	 * Inserts a new term in the field document binary tree.
	 * @param newTerm The term to be inserted.
	 */
	public FieldDocumentTreeNode insert(String newTerm) {
		return findOrNew(newTerm);
	}
	
	/**
	 * Inserts a new term in the field document binary tree, adding
	 * field to the field score.
	 * @param newTerm String the term to be inserted.
	 * @param field String the field in which the term appears.
	 */
	public FieldDocumentTreeNode insert(String newTerm, String field) {
		FieldDocumentTreeNode rtr = findOrNew(newTerm);
		rtr.addToFieldScore(field);
		return rtr;
	}
	/**
	 * Inserts a new term in the field document binary tree, adding
	 * fields to the field score.
	 * @param newTerm String the term to be inserted.
	 * @param fields HashSet the fields in which the term appears.
	 */
	public FieldDocumentTreeNode insert(String newTerm, Set<String> fields) {
		FieldDocumentTreeNode rtr = findOrNew(newTerm);
		rtr.addToFieldScore(fields);
		return rtr;
	}
	
	

	public interface FDTnodeProcedure{
		
		/**
		 * Perfoms some task when given a FDT tree node.
		 * @param node
		 */
		public void execute(FieldDocumentTreeNode node);
		
	}
	
	/**
	 * Applies a input procedure to each node of the tree
	 * recursively. The traversal is preorder.
	 * @param proc
	 */
	public void forEachNode(FDTnodeProcedure proc)
	{
		traverseInOrder(treeRoot, proc);
	}
	
	protected void traverseInOrder(FieldDocumentTreeNode node, FDTnodeProcedure proc) {
		if (node == null) 
			return;
		traverseInOrder(node.left,proc);
		proc.execute(node);
		traverseInOrder(node.right,proc);
	}
	
	/**
	 * Helper class that implements copying of array
	 * as a procedure to be executed on each node of 
	 * the tree.
	 * @author kanej
	 *
	 */
	/*
	private class toArrayProcedure implements FDTnodeProcedure
	{
		public void execute(FieldDocumentTreeNode node)
		{
			nodeBuffer[counter] = node;
		}
	}*/
	
	/**
	 * Returns an array of the term nodes of the tree.
	 */
	/*
	public FieldDocumentTreeNode[] toArray() {
		nodeBuffer = new FieldDocumentTreeNode[numberOfNodes];
		counter = 0;
		traversePreOrder(treeRoot, new toArrayProcedure());
		return nodeBuffer;
	}*/
	
	
	
	
	/**
	 * Returns an array of the term nodes of the tree.
	 */
	
	public FieldDocumentTreeNode[] toArray() {
		nodeBuffer = new FieldDocumentTreeNode[numberOfNodes];
		counter = 0;
		traversePreOrder(treeRoot);
		return nodeBuffer;
	}
	/** 
	 * The helper for returning the buffer of terms of the document.
	 * @param node FieldDocumentTreeNode The node to insert to the buffer
	 */
	
	protected void traversePreOrder(FieldDocumentTreeNode node) {
		if (node == null)
			return;
		nodeBuffer[counter] = node;
		counter++;
		traversePreOrder(node.left);
		traversePreOrder(node.right);
	}
}
