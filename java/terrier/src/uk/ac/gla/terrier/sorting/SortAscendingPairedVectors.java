/*
 * Terrier - Terabyte Retriever
 *
 * Webpage:	http://ir.dcs.gla.ac.uk/terrier 
 * Contact: terrier{a.}dcs.gla.ac.uk
 * University of Glasgow - Department of Computing Science
 * http://www.gla.ac.uk/
 * 
 * Copyright (C) 2004, 2005 University of Glasgow
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or any later version.
 *  
 * This package is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package uk.ac.gla.terrier.sorting;
/** 
 * This class sorts a pair of arrays, where the corresponding entries
 * are related. The result is that the first array is sorted in ascending
 * order, and the second is transformed in a way that the 
 * corresponding entries are in the correct places.
 * @author Gianni Amati, Vassilis Plachouras
 * @version $Revision: 1.9 $
 */
public class SortAscendingPairedVectors {
	
	/**
	 * Quick sort algorithm.
	 * @param a
	 * @param u
	 * @param lo0
	 * @param hi0
	 */
    private static void quickSort(int a[], int u[], int lo0, int hi0) {
        int lo = lo0;
        int hi = hi0;
        double mid;
		int dummy;
        if (hi0 > lo0) {
            mid = a[(lo0 + hi)>>>1];
            while (lo <= hi) {
                while ((lo < hi0) && (a[lo] < mid))
                    ++lo;
                while ((hi > lo0) && (a[hi] > mid))
                    --hi;
                if (lo <= hi) {
                    //swapping two elements
                	//swap(a, u, lo, hi);
                    dummy = a[lo];
                    a[lo] = a[hi];
                    a[hi] = dummy;
                    dummy = u[lo];
                    u[lo] = u[hi];
                    u[hi] = dummy;
                    //end of swapping
                    
                    ++lo;
                    --hi;
                }
            }
            if (lo0 < hi)
                quickSort(a, u, lo0, hi);
            if (lo < hi0)
                quickSort(a, u, lo, hi0);
        }
    }
    
    /**
     * Sorts the two vectors with respect to the
     * ascending order of the first one.
     * @param a the first vector to sort.
     * @param u the second vector to sort.
     */
    public static void sort(int[] a, int u[]) {
	    quickSort(a, u, 0, a.length - 1);
    }
}
