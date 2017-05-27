package com.ebp.owat.lib.utils.rand;

import java.math.BigInteger;

/**
 * Describes a random number generator for use with the rest of OWAT
 *
 * TODO:: test
 *
 * Created by Greg Stewart on 4/6/17.
 */
public abstract class BigIntegerGenerator {
	public static final BigInteger DEFAULT_LOWER = BigInteger.ZERO;
	public static final BigInteger DEFAULT_UPPER = BigInteger.ONE;
	
	private BigInteger lowerBound = DEFAULT_LOWER;
	private BigInteger upperBound = DEFAULT_UPPER;
	
	/**
	 * Default constructor.
	 */
	public BigIntegerGenerator(){}
	
	/**
	 * Constructor to set the upper and lower bounds.
	 * @param lowerIn The lower bound to give this generator.
	 * @param upperIn The upper bound to give this generator.
	 */
	public BigIntegerGenerator(BigInteger upperIn, BigInteger lowerIn){
		this.setBounds(upperIn, lowerIn);
	}
	
	/**
	 * Gets the lowerbound of this generator.
	 * @return The lower bound this generator is set to.
	 */
	public BigInteger getLowerBound(){
		return this.lowerBound;
	}
	
	/**
	 * Gets the upperbound of this generator.
	 * @return The upper bound this generator is set to.
	 */
	public BigInteger getUpperBound(){
		return this.upperBound;
	}
	
	/**
	 * Sets the lower bound of this generator.
	 * @param lowerIn The lower bound to give this generator.
	 * @return This generator object.
	 */
	public BigIntegerGenerator setLowerBound(BigInteger lowerIn){
		if(lowerIn == null){
			throw new NullPointerException("Can't set lower bound. Lower bound in was null.");
		}else if(lowerIn.compareTo(this.upperBound) >= 0) {
			throw new OwatRandException("Can't set lower bound. It is greater than the upper bound. ("+lowerIn.toString()+" >= "+this.getUpperBound().toString()+")");
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
	public BigIntegerGenerator setUpperBound(BigInteger upperIn){
		if(upperIn == null) {
			throw new NullPointerException("Can't set upper bound. Upper bound in was null.");
		}else if(upperIn.compareTo(this.lowerBound) <= 0) {
			throw new OwatRandException("Can't set upper bound. It is less than the lower bound. ("+upperIn.toString()+" <= "+this.getLowerBound().toString()+")");
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
	public BigIntegerGenerator setBounds(BigInteger upperIn, BigInteger lowerIn){
		this.setLowerBound(BigInteger.ZERO);
		this.setUpperBound(upperIn);
		this.setLowerBound(lowerIn);
		return this;
	}
	
	public abstract BigInteger next();
}
