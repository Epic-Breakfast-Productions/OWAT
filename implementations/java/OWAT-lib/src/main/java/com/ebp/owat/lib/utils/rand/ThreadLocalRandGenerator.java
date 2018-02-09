package com.ebp.owat.lib.utils.rand;

import java.util.concurrent.ThreadLocalRandom;

/**
 * A OwatRandGenerator that gets its random numbers from a simple rand().
 *
 * Created by Greg Stewart on 4/6/17.
 */
public class ThreadLocalRandGenerator extends OwatRandGenerator {
	/**
	 * Default constructor.
	 */
	public ThreadLocalRandGenerator() {
		super();
	}
	
	@Override
	public long nextLong() {
		return ThreadLocalRandom.current().nextLong();
	}
	
	@Override
	public long nextLong(long lowerBound, long upperBound) {
		return ThreadLocalRandom.current().nextLong(lowerBound, upperBound);
	}
	
	@Override
	public long nextLong(long upperBound) {
		return ThreadLocalRandom.current().nextLong(upperBound);
	}
}
