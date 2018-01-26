package com.ebp.owat.lib.utils.rand;

/**
 * Describes a random number generator for use with the rest of OWAT
 * Created by Greg Stewart on 4/6/17.
 */
public abstract class LongGenerator {
	/**
	 * Default constructor.
	 */
	public LongGenerator(){}
	
	/**
	 * Gets a random long, no consideration of bounds. Allways positive
	 * @return A random long.
	 */
	public abstract long next();
	
	/**
	 * Gets a random long, with a maximum possible value.
	 * @param upperBound
	 * @return
	 */
	public abstract long next(long upperBound);
	
	/**
	 * Gets a random long.
	 * @param lowerBound
	 * @param upperBound
	 * @return
	 */
	public long next(long lowerBound, long upperBound){
		return this.next(upperBound - lowerBound) + lowerBound;
	}
	
	/**
	 * Gets a random value from the set brought in.
	 * https://stackoverflow.com/questions/450807/how-do-i-make-the-method-return-type-generic
	 * @param vals The values to choose from.
	 * @param <T> The type of object.
	 * @return A random value from the set given.
	 */
	@SafeVarargs
	public final <T> T getRandValue(T ... vals){
		if(vals == null || vals.length == 0){
			throw new IllegalArgumentException("Cannot choose from an empty or null set.");
		}
		return vals[(int)this.next(vals.length-1)];
	}
}
