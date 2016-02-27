package uk.ac.gla.terrier.utility;

/** Handy methods for resizing arrays, and other handy array methods
 * This is a fresh implementation of the capacity methods, without the
 * use of any prelicensed code.
 * @author Ben He
 * @version $Revision: 1.4 $ */
public class ArrayUtils {
	/* TODO: use as an integer to reduce FP operations */
	/** the Golden ration (&phi;). */ 
	protected static final double GOLDEN_RATIO = 1.618;
	
	/** Grow an array to ensure it is the desired length. 
 	  * @param array input array
 	  * @param length ensure array is this length 
 	  * @return new array with desired length */
	public static byte[] ensureCapacity(byte[] array, int length){
		if (array.length < length){
			byte[] buffer = new byte[length];
			System.arraycopy(array, 0, buffer, 0, array.length);
			array = buffer;
		}
		return array;
	}
	
	/** Grow an array to ensure it is the desired length. Only copy the first preserve
	 * elements from the input array 
	 * @param array input array
	 * @param length new desired length
	 * @param preserve amount of old array to copy to new array in case of reallocation 
	 * @return new array with desired length */
	public static byte[] ensureCapacity(byte[] array, int length, int preserve){
		if (array.length < length){
			byte[] buffer = new byte[length];
			System.arraycopy(array, 0, buffer, 0, preserve);
			array = buffer;
		}
		return array;
	}
	
	/** Grow an array to ensure it is <i>at least</i> the desired length. The golden ratio
	 * is involved in the new length
	 * @param array input array
	 * @param length minimuim length of new array
	 * @return new array appropriately sized
	 */
	public static byte[] grow(byte[] array, int length){
		final int oldlength = array.length; 
		if (oldlength < length){
			int newsize = Math.max(length, (int)(((double)oldlength)*GOLDEN_RATIO));
			byte[] buffer = new byte[newsize];
			System.arraycopy(array, 0, buffer, 0, oldlength);			
			array = buffer;
		}
		return array;
	}
	
	/** Grow an array to ensure it is <i>at least</i> the desired length. The golden ratio
	 * is involved in the new length. Only copy the first preserve
	 * elements from the input array.
	 * @param array input array
	 * @param length minimuim length of new array
	 * @return new array appropriately sized
	 */
	public static byte[] grow(byte[] array, int length, int preserve){
		if (array.length < length){
			int newsize = Math.max(length, (int)((double)array.length*GOLDEN_RATIO));
			byte[] buffer = new byte[newsize];
			System.arraycopy(array, 0, buffer, 0, preserve);
			array = buffer;
		}
		return array;
	}


	/** Join some strings together.
	  * @param in Strings to join
	  * @param join Character or String to join by */
    public static String join (String[] in, String join)
    {
        final StringBuilder s = new StringBuilder();
        for(String i : in)
        {
            s.append(i);
            s.append(join);
        }
        s.setLength(s.length() - join.length());
        return s.toString();
    }

}
