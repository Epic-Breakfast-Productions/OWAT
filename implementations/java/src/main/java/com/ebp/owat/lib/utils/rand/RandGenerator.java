package com.ebp.owat.lib.utils.rand;

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
	 * Constructor to set the upper and lower bounds.
	 *
	 * @param upperIn The upper bound to give this generator.
	 * @param lowerIn The lower bound to give this generator.
	 */
	public RandGenerator(long upperIn, long lowerIn) {
		super(upperIn, lowerIn);
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
	 * Constructs a new RandGenerator.
	 * @param randIn The Random to give this object.
	 * @param upperIn The upper bounds.
	 * @param lowerIn The lower bounds.
	 */
	public RandGenerator(Random randIn, long upperIn, long lowerIn){
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
	
	/**
	 * Gets the Random generator this uses.
	 * @return The Random generator this uses.
	 */
	public Random getRandom(){
		return this.rand;
	}
	
	@Override
	public long next() {
		return this.getLowerBound()+((long)(this.rand.nextDouble()*(this.getUpperBound()-this.getLowerBound())));
	}
}
