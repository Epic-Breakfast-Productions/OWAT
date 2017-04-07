package com.ebp.owat.lib.utils.rand;

import java.math.BigInteger;
import java.util.Random;

/**
 * A BigIntegerGenerator that gets its random numbers from a simple rand().
 *
 * //TODO:: tests
 *
 * Created by Greg Stewart on 4/6/17.
 */
public class RandGenerator extends BigIntegerGenerator {
	/** The random number generator to use. */
	private Random rand;
	
	/**
	 * Default constructor.
	 */
	public RandGenerator() {
		super();
		this.setRandom(null);
	}
	
	/**
	 * Constructor to set the upper and lower bounds.
	 *
	 * @param upperIn The upper bound to give this generator.
	 * @param lowerIn The lower bound to give this generator.
	 */
	public RandGenerator(BigInteger upperIn, BigInteger lowerIn) {
		super(upperIn, lowerIn);
		this.rand = new Random();
	}
	
	/**
	 * Constructor to set the Random.
	 * @param randIn The Random to give this object.
	 */
	public RandGenerator(Random randIn){
		this();
		this.setRandom(randIn);
	}
	
	/**
	 * Constructs a new RandGenerator.
	 * @param randIn The Random to give this object.
	 * @param upperIn The upper bounds.
	 * @param lowerIn The lower bounds.
	 */
	public RandGenerator(Random randIn, BigInteger upperIn, BigInteger lowerIn){
		this(upperIn, lowerIn);
		this.setRandom(randIn);
	}
	
	/**
	 * Sets the random number generator of this object.
	 * @param randIn The Random to give this object.
	 * @return This RandGenerator.
	 */
	public RandGenerator setRandom(Random randIn){
		if(randIn == null){
			this.rand = new Random();
		}else{
			this.rand = randIn;
		}
		return this;
	}
	
	@Override
	public BigInteger next() {
		//http://stackoverflow.com/questions/2290057/how-to-generate-a-random-biginteger-value-in-java
		BigInteger adjUpper = this.getUpperBound().subtract(this.getLowerBound());
		int nlen = adjUpper.bitLength();
		BigInteger nm1 = adjUpper.subtract(BigInteger.ONE);
		BigInteger r, s;
		do {
			s = new BigInteger(nlen + 100, rand);
			r = s.mod(adjUpper);
		} while (s.subtract(r).add(nm1).bitLength() >= nlen + 100);
		
		r = r.add(this.getLowerBound());
		
		return r;
	}
}
