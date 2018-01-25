package com.ebp.owat.lib.testModels.utils.rand;

import com.ebp.owat.lib.utils.rand.LongGenerator;

/**
 * Test class for testing basic LongGenerator functionality.
 *
 * Created by Greg Stewart on 5/27/17.
 */
public class TestLongGen extends LongGenerator {
	@Override
	public long next() {
		return 0L;
	}
	
	/**
	 * Default constructor.
	 */
	public TestLongGen() {
		super();
	}
	
	/**
	 * Constructor to set the upper and lower bounds.
	 *
	 * @param upperIn The upper bound to give this generator.
	 * @param lowerIn The lower bound to give this generator.
	 */
	public TestLongGen(long upperIn, long lowerIn) {
		super(upperIn, lowerIn);
	}
}
