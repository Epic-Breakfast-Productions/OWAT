package tests.utils.rand;

import com.ebp.owat.lib.utils.rand.LongGenerator;
import com.ebp.owat.lib.utils.rand.OwatRandException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testModels.utils.rand.TestLongGen;

import static org.junit.Assert.assertTrue;

/**
 * Tests the basic functionality of the LongGenerator.
 *
 * Created by Greg Stewart on 4/14/17.
 */
public class LongGeneratorTest {
	private Logger LOGGER = LoggerFactory.getLogger(LongGeneratorTest.class);
	
	private long lower = 100L;
	private long upper = 200L;
	
	@Test
	public void testRandGeneratorConstructors() {
		LOGGER.info("Testing the LongGenerator constructors.");
		LongGenerator test = new TestLongGen();
		
		LOGGER.info("Testing the LongGenerator constructors.");
		
		assertTrue("Default lower bound was not set in constructor.", test.getLowerBound() == LongGenerator.DEFAULT_LOWER);
		assertTrue("Default upper bound was not set in constructor.", test.getUpperBound() == LongGenerator.DEFAULT_UPPER);
		
		test = new TestLongGen(upper, lower);
		
		assertTrue("Got wrong lower bound back.", test.getLowerBound() == lower);
		assertTrue("Got wrong upper bound back.", test.getUpperBound() == upper);
		
		boolean failed = false;
		try{
			new TestLongGen(lower, upper);
		}catch (OwatRandException e){
			failed = true;
		}
		assertTrue("Didn't throw an exception when passing inverted upper and lower.", failed);
	}
	
	@Test
	public void testRandGeneratorSetters(){
		LOGGER.info("Testing the LongGenerator setters.");
		LongGenerator test = new TestLongGen();
		
		LOGGER.info("Testing setBounds().");
		test.setBounds(upper, lower);
		assertTrue("Got wrong lower bound back.", test.getLowerBound() == lower);
		assertTrue("Got wrong upper bound back.", test.getUpperBound() == upper);
		
		LOGGER.info("Testing setLower().");
		test = new TestLongGen(upper, LongGenerator.DEFAULT_LOWER);
		test.setLowerBound(lower);
		assertTrue("Got wrong lower bound back.", test.getLowerBound() == lower);
		boolean failed = false;
		try{
			test.setLowerBound(upper + 1L);
		}catch (OwatRandException e){
			failed = true;
		}
		assertTrue("Didn't throw an exception when setting lower to a value higher than the set upper value.", failed);
		
		LOGGER.info("Testing setUpper().");
		test = new TestLongGen(lower + 1, lower);
		test.setUpperBound(upper);
		assertTrue("Got wrong upper bound back.", test.getUpperBound() == upper);
		failed = false;
		try{
			test.setUpperBound(lower - 1L);
		}catch (OwatRandException e){
			failed = true;
		}
		assertTrue("Didn't throw an exception when setting upper to a value lower than the set lower value.", failed);
	}
}
