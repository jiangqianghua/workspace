package uk.ac.gla.terrier.compression;
/** Utility methods for use in the BitFile classes.
 * @author Craig Macdonald
 * @version $Revision: 1.2 $ 
 * @since 2.0 */
public class BitUtilities {
	/** Returns the most significant bit for any positive integer, 0 for -1. Computed by iteration */
	public static int mostSignficantBit_Loop(int x)
	{
		if (x == 0)
			return -1;
		int bit = 0;
		
		for (int i=2;i<Integer.MAX_VALUE;i=i<<1)
		{
			if (x<=i-1)
				return bit;
			bit++;
		}
		return bit -1;
	}
	
	/** Returns the most significant bit for any positive integer. 0 for -1. Computed by hard-coded binary search. */
	public static int mostSignificantBit(int x)
	{
		if (x >= 1<< 16)
			if (x >= 1<<24)
				if (x >= 1<<28)
					if (x >= 1<<30)
						/* special case x < 2^31 for all x */
						/*if (x >= 1<<31)
							return 31;
						else*/
							return 30;
					else
						if (x >= 1<<29)
							return 29;
						else
							return 28;
				else
					if (x >= 1<<26)
						if (x >= 1<<27)
							return 27;
						else
							return 26;
					else
						if (x >= 1<<25)
							return 25;
						else
							return 24;
			else
				if (x >= 1<<20) 
					if (x >= 1<<22)
						if (x >= 1 <<23)
							return 23;
						else
							return 22;
					else
						if (x >= 1 <<21)
							return 21;
						else
							return 20;
				else
					if (x >= 1<<18) 
						if (x >= 1 <<19)
							return 19;
						else 
							return 18;
					else	
						if (x >= 1 <<17)
							return 17;
						else 
							return 16;			
		else
			
			if (x >= 1<<8)
				if (x >= 1<<12)
					if (x >= 1<<14)
						if (x >= 1<<15)
							return 15;
						else
							return 14;
					else
						if (x >= 1<<13)
							return 13;
						else
							return 12;
				else
					if (x >= 1<<10)
						if (x >= 1<<11)
							return 11;
						else
							return 10;
					else
						if (x >= 1<<9)
							return 9;
						else
							return 8;
			else
				if (x >= 1<<4) 
					if (x >= 1<<6)
						if (x >= 1 <<7)
							return 7;
						else
							return 6;
					else
						if (x >= 1 <<5)
							return 5;
						else
							return 4;
				else
					if (x >= 1<<2) /* x >= 4 */
						if (x >= 1 <<3)
							return 3;
						else 
							return 2;
					else	
						if (x >= 1 <<1)
							return 1;
						else 
							if (x == 0)
								return -1;
							return 0;
	}
	
	/** Returns the most significant bit for any byte value. 0 gives MSB -1. */
	public static int mostSignificantBit(byte x)
	{
		if (x >= 1<<4) 
			if (x >= 1<<6)
				if (x >= 1 <<7)
					return 7;
				else
					return 6;
			else
				if (x >= 1 <<5)
					return 5;
				else
					return 4;
		else
			if (x >= 1<<2)
				if (x >= 1 <<3)
					return 3;
				else 
					return 2;
			else	
				if (x >= 1 <<1)
					return 1;
				else 
					if (x == 0)
						return -1;
					return 0;
	}
	/** Array of output of mostSignficantBit(byte) for quicker lookups */	
	public static final int[] MSB_BYTES = {
		-1,0, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 
		5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 
		6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 
		6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 
		7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 
		7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 
		7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 
		7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7
	};
}
