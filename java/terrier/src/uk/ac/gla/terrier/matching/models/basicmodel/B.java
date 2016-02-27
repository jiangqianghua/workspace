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
 * The Original Code is B.java.
 *
 * The Original Code is Copyright (C) 2004-2008 the University of Glasgow.
 * All Rights Reserved.
 *
 * Contributor(s):
 *   Ben He <ben{a.}dcs.gla.ac.uk> 
 */
package uk.ac.gla.terrier.matching.models.basicmodel;
import uk.ac.gla.terrier.matching.models.Idf;

/**
 * This class implements the B basic model for randomness. B stands
 * for Bose-Einstein statistics.
 * @author Ben He
 * @version $Revision: 1.8 $
 */
public class B extends BasicModel{
	/** The name of the model. */
	protected String modelName = "B";
	/** 
	 * A default constructor.
	 */
	public B(){
		super();
	}
	/**
	 * Returns the name of the model.
	 * @return the name of the model
	 */
	public String getInfo(){
		return this.modelName;
	}
	/**
	 * This method computes the score for the implemented weighting model.
	 * @param tf The term frequency in the document
	 * @param documentFrequency The document frequency of the term
	 * @param termFrequency the term frequency in the collection
	 * @param keyFrequency The normalised query term frequency.
	 * @return the score returned by the implemented weighting model.
	 */
	public double score(
		double tf,
		double documentFrequency,
		double termFrequency,
		double keyFrequency){
		return keyFrequency * (
			- i.log(numberOfDocuments - 1)
			- Idf.REC_LOG_2_OF_E
			+ stirlingPower(
				numberOfDocuments
					+ termFrequency
					- 1d,
				numberOfDocuments
					+ termFrequency
					- tf
					- 2d)
			- stirlingPower(termFrequency, termFrequency - tf));
	}
}
