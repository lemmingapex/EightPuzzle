package sw.cos226.utils;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Uses merge-sort like algorithm to count inversions.  O(n*log(n))
 * 
 * @author scott
 *
 * @param <T>
 */
public class InversionCounter<T extends Comparable<? super T>> {

	public InversionCounter() {

	}

	@SuppressWarnings("unchecked")
	public Integer invCount(T[] pNumbers) throws IllegalArgumentException {
		int length = pNumbers.length;

		if (length < 2) {
			return 0;
		} else {
			int firstSize = length / 2;
			int secondSize = length - (length / 2);

			// Split array in 1/2.
			T[] firstHalf = (T[])Array.newInstance(pNumbers.getClass().getComponentType(), firstSize);
			T[] secondHalf = (T[])Array.newInstance(pNumbers.getClass().getComponentType(), secondSize);
			System.arraycopy(pNumbers, 0, firstHalf, 0, firstSize);
			System.arraycopy(pNumbers, length / 2, secondHalf, 0, secondSize);
			// Recursively sort first half and sort second half. Then merge
			return invCount(firstHalf) + invCount(secondHalf) + merge(pNumbers, firstHalf, secondHalf);
		}
	}

	@SuppressWarnings("unchecked")
	private Integer merge(T[] pNumbers, T[] pHalf1, T[] pHalf2) {
		Integer count = 0;

		// Half1 index, Half2 index, result index
		int i = 0, j = 0;

		while (i < pHalf1.length || j < pHalf2.length) {
	        if (i == pHalf1.length) {
	        	pNumbers[i+j] = pHalf2[j];
	            j++;
	        } else if (j == pHalf2.length) {
	        	pNumbers[i+j] = pHalf1[i];
	            i++;
	        } else if (pHalf1[i].compareTo(pHalf2[j]) <= 0) {
	        	pNumbers[i+j] = pHalf1[i];
	            i++;                
	        } else {
	        	pNumbers[i+j] = pHalf2[j];
	            count += pHalf1.length-i;
	            j++;
	        }
	    }

		return count;
	}	
}