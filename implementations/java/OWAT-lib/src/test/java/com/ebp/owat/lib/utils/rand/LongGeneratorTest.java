package com.ebp.owat.lib.utils.rand;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Tests the basic functionality of the LongGenerator.
 *
 * Created by Greg Stewart on 4/14/17.
 */
@RunWith(Parameterized.class)
public class LongGeneratorTest {
	private Logger LOGGER = LoggerFactory.getLogger(LongGeneratorTest.class);
	
	private static final int NUM_TEST_ITERATIONS = 20_000_000;
	
	private static final long TEST_LOWER = 100L;
	private static final long TEST_UPPER = 200L;
	
	private static final String[] VALUES = new String[]{"Hello", "world"};
	private static final List<String> VALUES_AS_LIST = Arrays.asList(VALUES);
	
	/** The different exceptions to test. */
	@Parameterized.Parameters
	public static Collection exceptionsToTest() {
		return Arrays.asList(new Object[][] {
			{ RandGenerator.class },
			{ ThreadLocalRandGenerator.class }
		});
	}
	
	private final Class<? extends LongGenerator> curGenClass;
	
	public LongGeneratorTest(Class<? extends LongGenerator> curGenClass){
		this.curGenClass = curGenClass;
	}
	
	@Test
	public void testLongGenerators() throws Throwable{
		LOGGER.info("Testing the LongGenerator class: {}", curGenClass);
		Constructor<? extends LongGenerator> constBase = curGenClass.getConstructor();
		
		LongGenerator gen = constBase.newInstance();
		
		gen.next();
		
		for(long i = 0; i < NUM_TEST_ITERATIONS; i++){
			long val = gen.next(TEST_UPPER);
			assertTrue(val < TEST_UPPER);
		}
		
		for(long i = 0; i < NUM_TEST_ITERATIONS; i++){
			long val = gen.next(TEST_LOWER, TEST_UPPER);
			assertTrue(val < TEST_UPPER);
			assertTrue(val >= TEST_LOWER);
		}
		
		for(long i = 0; i < NUM_TEST_ITERATIONS; i++){
			assertTrue(VALUES_AS_LIST.contains(
				gen.getRandValue(VALUES)
			));
		}
		
		LOGGER.info("DONE Testing the LongGenerator class: {}", curGenClass);
	}
}
