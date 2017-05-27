package tests.utils.rand;

import com.ebp.owat.lib.utils.rand.LongGenerator;
import com.ebp.owat.lib.utils.rand.OwatRandException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

/**
 * Tests the basic functionality of the LongGenerator.
 *
 * Created by Greg Stewart on 4/14/17.
 */
public class LongGeneratorTest {
	private Logger LOGGER = LoggerFactory.getLogger(LongGeneratorTest.class);
	
	//TODO:: move to models
	private class TestGen extends LongGenerator {
		@Override
		public long next() {
			return 0L;
		}
		
		/**
		 * Default constructor.
		 */
		public TestGen() {
			super();
		}
		
		/**
		 * Constructor to set the upper and lower bounds.
		 *
		 * @param upperIn The upper bound to give this generator.
		 * @param lowerIn The lower bound to give this generator.
		 */
		public TestGen(long upperIn, long lowerIn) {
			super(upperIn, lowerIn);
		}
	}
	
	//TODO:: split into multiple tests
	@Test
	public void testRandGenerator() {
		LOGGER.info("Testing the LongGenerator.");
		LongGenerator test = new TestGen();
		
		LOGGER.info("Testing the LongGenerator constructors.");
		
		assertTrue("Default lower bound was not set in constructor.", test.getLowerBound() == LongGenerator.DEFAULT_LOWER);
		assertTrue("Default upper bound was not set in constructor.", test.getUpperBound() == LongGenerator.DEFAULT_UPPER);
		
		
		long lower = 100L;
		long upper = 200L;
		test = new TestGen(upper, lower);
		
		assertTrue("Got wrong lower bound back.", test.getLowerBound() == lower);
		assertTrue("Got wrong upper bound back.", test.getUpperBound() == upper);
		
		boolean failed = false;
		try{
			new TestGen(lower, upper);
		}catch (OwatRandException e){
			failed = true;
		}
		assertTrue("Didn't throw an exception when passing inverted upper and lower.", failed);
		
		//TODO:: test setters.
	}
}
