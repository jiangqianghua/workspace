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
 * This class sorts five arrays, where the corresponding entries
 * are related. The result is that the first array is sorted in ascending
 * order, and the rest are transformed in a way that the 
 * corresponding entries are in the correct places.
 * @author Douglas Johnson
 * @version $Revision: 1.7 $
 */
public class SortAscendingQuintupleVectors {
	
	/**
	 * The quick sort algorithm.
	 * @param a
	 * @param u
	 * @param e
	 * @param o
	 * @param f
	 * @param lo0
	 * @param hi0
	 */
	private static void quickSort(int a[], int u[], int e[], int o[], int f[], int lo0, int hi0) 
	{
		int lo = lo0;
		int hi = hi0;
		int dummy;
		double mid, mid2;
		if (hi0 > lo0) {
			mid = a[(lo0+hi0)>>>1 /*(lo0 + hi0) / 2*/];
			mid2 = u[(lo0+hi0)>>>1 /*(lo0 + hi0) / 2*/];
			while (lo <= hi) {
				while ((lo < hi0)
					&& (a[lo] == mid)
					&& (u[lo] < mid2)
					|| (lo < hi0)
					&& (a[lo] < mid))
					++lo;
				while ((hi > lo0)
					&& (a[hi] == mid)
					&& (u[hi] > mid2)
					|| (hi > lo0)
					&& (a[hi] > mid))
					--hi;
				if (lo <= hi) {
					//start swapping
					//swap(a, u, e, o, f, lo, hi);
					dummy = a[lo];
					a[lo] = a[hi];
					a[hi] = dummy;
					
					dummy = u[lo];
					u[lo] = u[hi];
					u[hi] = dummy;
					
					dummy = e[lo];
					e[lo] = e[hi];
					e[hi] = dummy;
					
					dummy = o[lo];
					o[lo] = o[hi];
					o[hi] = dummy;
					
					dummy = f[lo];
					f[lo] = f[hi];
					f[hi] = dummy;
					//end swapping
					++lo;
					--hi;
				}
			}
			if (lo0 < hi)
				quickSort(a, u, e, o, f, lo0, hi);
			if (lo < hi0)
				quickSort(a, u, e, o, f, lo, hi0);
		}
	}
    /**
	* Sorts the five vectors with respect to the
    * ascending order of the first one.
    * @param a the first vector to sort.
    * @param u the second vector to sort.
    * @param e the third vector to sort.
    * @param o the fourth vector to sort.
    * @param f the fifth vector to sort.
	*/
	public static void sort(int[] a, int u[], int e[], int o[], int f[]) {
		quickSort(a, u, e, o, f, 0, a.length - 1);
	}
}
