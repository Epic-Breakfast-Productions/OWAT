package tests.utils.rand;

import com.ebp.owat.lib.utils.rand.RandGenerator;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

/**
 * Test to test the RandGenerator to get random longs.
 *
 * Created by Greg Stewart on 4/12/17.
 */
public class RandGeneratorTest {
	private Logger LOGGER = LoggerFactory.getLogger(RandGeneratorTest.class);
	
	private long testLower = 1231L;
	private long testUpper = 9929L;
	private long testLowerBig = (long)Integer.MAX_VALUE * 10L;
	private long testUpperBig = (long)Integer.MAX_VALUE * 100L;
	
	private static final int NUM_TEST_ITERATIONS = 10000000;
	
	private RandGenerator test;
	private long cur;
	
	@Test
	public void testRandGeneratorWithinIntRange(){
		test = new RandGenerator(testUpper, testLower);
		
		LOGGER.info("Testing getting numbers within the integer range.");
		for(int i = 0; i < NUM_TEST_ITERATIONS; i++){
			cur = test.next();
			assertTrue("Number greater than the upper bounds." + " Iteration: " +i, cur >= testLower);
			assertTrue("Number lower than the lower bounds." + " Iteration: " +i, cur <= testUpper);
		}
	}
	
	@Test
	public void testRandGeneratorOutsideIntRange(){
		test = new RandGenerator(testUpperBig, testLowerBig);
		LOGGER.info("Testing getting numbers outside the integer range.");
		for(int i = 0; i < NUM_TEST_ITERATIONS; i++){
			cur = test.next();
			assertTrue("Number greater than the upper bounds." + " Iteration: " +i, cur >= testLowerBig);
			assertTrue("Number lower than the lower bounds." + " Iteration: " +i,cur <= testUpperBig);
		}
	}
	
	@Test
	public void testRandGeneratorMixedRange(){
		test = new RandGenerator(testUpperBig, testLower);
		LOGGER.info("Testing getting numbers mixed around the integer range.");
		for(int i = 0; i < NUM_TEST_ITERATIONS; i++){
			cur = test.next();
			assertTrue("Number greater than the upper bounds." + " Iteration: " +i, cur >= testLower);
			assertTrue("Number lower than the lower bounds." + " Iteration: " +i,cur <= testUpperBig);
		}
	}
}
