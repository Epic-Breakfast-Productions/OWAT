package tests.utils.rand;

import com.ebp.owat.lib.utils.rand.RandGenerator;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import static org.junit.Assert.assertTrue;

/**
 * Test to test the RandGenerator to get random BigIntegers.
 *
 * Created by Greg Stewart on 4/12/17.
 */
public class RandGeneratorTest {
	private Logger LOGGER = LoggerFactory.getLogger(RandGeneratorTest.class);
	
	private BigInteger testLower = BigInteger.valueOf(1231);
	private BigInteger testUpper = BigInteger.valueOf(9929);
	private BigInteger testLowerBig = new BigInteger(Long.MAX_VALUE + "0");
	private BigInteger testUpperBig = new BigInteger(Long.MAX_VALUE + "00");
	
	private static final int NUM_TEST_ITERATIONS = 10000000;
	
	@Test
	public void testRandGenerator(){
		LOGGER.info("Testing the RandGenerator.");
		RandGenerator test = new RandGenerator(testUpper, testLower);
		BigInteger cur;
		
		LOGGER.info("Testing getting numbers within the integer range.");
		for(int i = 0; i < NUM_TEST_ITERATIONS; i++){
			cur = test.next();
			assertTrue("Number greater than the upper bounds." + " Iteration: " +i, cur.compareTo(testLower) >= 0);
			assertTrue("Number lower than the lower bounds." + " Iteration: " +i, cur.compareTo(testUpper) <= 0);
		}
		
		test = new RandGenerator(testUpperBig, testLowerBig);
		
		LOGGER.info("Testing getting numbers outside the integer range.");
		for(int i = 0; i < NUM_TEST_ITERATIONS; i++){
			cur = test.next();
			assertTrue("Number greater than the upper bounds." + " Iteration: " +i, cur.compareTo(testLowerBig) >= 0);
			assertTrue("Number lower than the lower bounds." + " Iteration: " +i,cur.compareTo(testUpperBig) <= 0);
		}
		
		test = new RandGenerator(testUpperBig, testLower);
		
		LOGGER.info("Testing getting numbers mixed around the integer range.");
		for(int i = 0; i < NUM_TEST_ITERATIONS; i++){
			cur = test.next();
			assertTrue("Number greater than the upper bounds." + " Iteration: " +i, cur.compareTo(testLower) >= 0);
			assertTrue("Number lower than the lower bounds." + " Iteration: " +i,cur.compareTo(testUpperBig) <= 0);
		}
		LOGGER.info("Done.");
	}
}
