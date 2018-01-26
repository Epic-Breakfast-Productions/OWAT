package com.ebp.owat.lib.utils.rand;

import java.security.SecureRandom;
import java.util.Random;

/**
 * A LongGenerator that gets its random numbers from a simple rand().
 *
 * Created by Greg Stewart on 4/6/17.
 */
public class RandGenerator extends LongGenerator {
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
	 * Constructor to set the Random.
	 * @param randIn The Random to give this object.
	 */
	public RandGenerator(Random randIn){
		this();
		this.setRandom(randIn);
	}
	
	/**
	 * Sets the random number generator of this object.
	 * @param randIn The Random to give this object.
	 * @return This RandGenerator.
	 */
	public RandGenerator setRandom(Random randIn){
		if(randIn == null){
			this.rand = new SecureRandom();
		}else{
			this.rand = randIn;
		}
		return this;
	}
	
	/**
	 * Gets the Random generator this uses.
	 * @return The Random generator this uses.
	 */
	public Random getRandom(){
		return this.rand;
	}
	
	@Override
	public long next() {
		return this.rand.nextLong();
	}
	
	@Override
	public long next(long upperBound) {
		return (long)(this.rand.nextDouble()*upperBound);
	}
}
