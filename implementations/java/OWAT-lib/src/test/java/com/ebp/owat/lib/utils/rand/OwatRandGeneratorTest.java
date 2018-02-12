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

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests the basic functionality of the OwatRandGenerator.
 *
 * Created by Greg Stewart on 4/14/17.
 */
@RunWith(Parameterized.class)
public class OwatRandGeneratorTest {
	private Logger LOGGER = LoggerFactory.getLogger(OwatRandGeneratorTest.class);
	
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
	
	private final Class<? extends OwatRandGenerator> curGenClass;
	
	public OwatRandGeneratorTest(Class<? extends OwatRandGenerator> curGenClass){
		this.curGenClass = curGenClass;
	}
	
	@Test
	public void testLongGenerators() throws Throwable{
		LOGGER.info("Testing the OwatRandGenerator class: {}", curGenClass);
		Constructor<? extends OwatRandGenerator> constBase = curGenClass.getConstructor();
		
		OwatRandGenerator gen = constBase.newInstance();
		
		gen.nextLong();
		
		for(long i = 0; i < NUM_TEST_ITERATIONS; i++){
			long val = gen.nextLong(TEST_UPPER);
			assertTrue(val < TEST_UPPER);
		}
		
		for(long i = 0; i < NUM_TEST_ITERATIONS; i++){
			long val = gen.nextLong(TEST_LOWER, TEST_UPPER);
			assertTrue(val < TEST_UPPER);
			assertTrue(val >= TEST_LOWER);
		}
		
		for(long i = 0; i < NUM_TEST_ITERATIONS; i++){
			assertTrue(VALUES_AS_LIST.contains(
				gen.getRandValue(VALUES)
			));
		}
		
		byte bytee = gen.nextByte();
		
		for(long i = 0; i < NUM_TEST_ITERATIONS; i++){
			bytee = gen.nextByteChar();
			char cha = (char)bytee;
			assertNotEquals(-1, OwatRandGenerator.CHARSET.indexOf(cha));
		}
		
		LOGGER.info("DONE Testing the OwatRandGenerator class: {}", curGenClass);
	}
}
