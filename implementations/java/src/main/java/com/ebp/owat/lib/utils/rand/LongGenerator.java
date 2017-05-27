package com.ebp.owat.lib.utils.rand;

/**
 * Describes a random number generator for use with the rest of OWAT
 *
 * TODO:: test
 *
 * Created by Greg Stewart on 4/6/17.
 */
public abstract class LongGenerator {
	public static final long DEFAULT_LOWER = 0L;
	public static final long DEFAULT_UPPER = 1L;
	
	private long lowerBound = DEFAULT_LOWER;
	private long upperBound = DEFAULT_UPPER;
	
	/**
	 * Default constructor.
	 */
	public LongGenerator(){}
	
	/**
	 * Constructor to set the upper and lower bounds.
	 * @param lowerIn The lower bound to give this generator.
	 * @param upperIn The upper bound to give this generator.
	 */
	public LongGenerator(long upperIn, long lowerIn){
		this.setBounds(upperIn, lowerIn);
	}
	
	/**
	 * Gets the lowerbound of this generator.
	 * @return The lower bound this generator is set to.
	 */
	public long getLowerBound(){
		return this.lowerBound;
	}
	
	/**
	 * Gets the upperbound of this generator.
	 * @return The upper bound this generator is set to.
	 */
	public long getUpperBound(){
		return this.upperBound;
	}
	
	/**
	 * Sets the lower bound of this generator.
	 * @param lowerIn The lower bound to give this generator.
	 * @return This generator object.
	 */
	public LongGenerator setLowerBound(long lowerIn){
		if(lowerIn >= this.upperBound) {
			throw new OwatRandException("Can't set lower bound. It is greater than the upper bound. ("+lowerIn+" >= "+this.getUpperBound()+")");
		}else{
			this.lowerBound = lowerIn;
		}
		return this;
	}
	
	/**
	 * Sets the upper bound of this generator.
	 * @param upperIn The upper bound to give this generator.
	 * @return This generator object.
	 */
	public LongGenerator setUpperBound(long upperIn){
		if(upperIn <= this.lowerBound) {
			throw new OwatRandException("Can't set upper bound. It is less than the lower bound. ("+upperIn+" <= "+this.getLowerBound()+")");
		}else {
			this.upperBound = upperIn;
		}
		return this;
	}
	
	/**
	 * Sets the upper and lower bounds of this generator.
	 *
	 * @param upperIn The upper bound to set this generator to.
	 * @param lowerIn The lower bound to set this generator to.
	 * @return This generator object.
	 */
	public LongGenerator setBounds(long upperIn, long lowerIn){
		this.setLowerBound(0L);
		this.setUpperBound(upperIn);
		this.setLowerBound(lowerIn);
		return this;
	}
	
	public abstract long next();
}
