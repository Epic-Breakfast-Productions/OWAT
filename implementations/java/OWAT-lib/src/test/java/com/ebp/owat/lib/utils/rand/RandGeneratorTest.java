package com.ebp.owat.lib.utils.rand;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test to test the RandGenerator to get random longs.
 *
 * Created by Greg Stewart on 4/12/17.
 */
public class RandGeneratorTest {
	private Logger LOGGER = LoggerFactory.getLogger(RandGeneratorTest.class);
	
	private static final long testLower = 1231L;
	private static final long testUpper = 9929L;
	
	@Test
	public void testRandGeneratorExtraConstructors(){
	
	}
}
