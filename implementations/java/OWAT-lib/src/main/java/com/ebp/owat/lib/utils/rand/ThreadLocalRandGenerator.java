package com.ebp.owat.lib.utils.rand;

import java.util.concurrent.ThreadLocalRandom;

/**
 * A LongGenerator that gets its random numbers from a simple rand().
 *
 * Created by Greg Stewart on 4/6/17.
 */
public class ThreadLocalRandGenerator extends LongGenerator {
	/**
	 * Default constructor.
	 */
	public ThreadLocalRandGenerator() {
		super();
	}
	
	@Override
	public long next() {
		return ThreadLocalRandom.current().nextLong();
	}
	
	@Override
	public long next(long lowerBound, long upperBound) {
		return ThreadLocalRandom.current().nextLong(lowerBound, upperBound);
	}
	
	@Override
	public long next(long upperBound) {
		return ThreadLocalRandom.current().nextLong(upperBound);
	}
}
