package tests.utils.rand;

import com.ebp.owat.lib.utils.rand.BigIntegerGenerator;
import com.ebp.owat.lib.utils.rand.OwatRandException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

import static org.junit.Assert.assertTrue;

/**
 * Tests the basic functionality of the BigInteger.
 *
 * Created by Greg Stewart on 4/14/17.
 */
public class BigIntegerGeneratorTest {
	private Logger LOGGER = LoggerFactory.getLogger(BigIntegerGeneratorTest.class);
	
	private class testGen extends BigIntegerGenerator{
		@Override
		public BigInteger next() {
			return null;
		}
		
		/**
		 * Default constructor.
		 */
		public testGen() {
			super();
		}
		
		/**
		 * Constructor to set the upper and lower bounds.
		 *
		 * @param upperIn The upper bound to give this generator.
		 * @param lowerIn The lower bound to give this generator.
		 */
		public testGen(BigInteger upperIn, BigInteger lowerIn) {
			super(upperIn, lowerIn);
		}
	}
	
	@Test
	public void testRandGenerator() {
		LOGGER.info("Testing the BigIntegerGenerator.");
		BigIntegerGenerator test = new testGen();
		BigInteger lower = BigInteger.valueOf(100);
		BigInteger upper = BigInteger.valueOf(200);
		
		LOGGER.info("Testing the BigIntegerGenerator constructors.");
		
		
		assertTrue("Default lower bound was not set in constructor.", test.getLowerBound().compareTo(BigIntegerGenerator.DEFAULT_LOWER) == 0);
		assertTrue("Default upper bound was not set in constructor.", test.getUpperBound().compareTo(BigIntegerGenerator.DEFAULT_UPPER) == 0);
		
		
		test = new testGen(upper, lower);
		
		assertTrue("Got wrong lower bound back.", test.getLowerBound().compareTo(lower) == 0);
		assertTrue("Got wrong upper bound back.", test.getUpperBound().compareTo(upper) == 0);
		
		boolean failed = false;
		try{
			new testGen(lower, upper);
		}catch (OwatRandException e){
			failed = true;
		}
		assertTrue("Didn't throw an exception when passing inverted upper and lower.", failed);
		
		//TODO:: test setters.
	}
	
	
}
