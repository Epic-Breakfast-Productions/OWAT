package tests.utils.rand;

import com.ebp.owat.lib.utils.rand.LongGenerator;
import com.ebp.owat.lib.utils.rand.OwatRandException;
import com.ebp.owat.lib.utils.rand.RandGenerator;
import org.junit.Assert;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Tests the basic functionality of the LongGenerator.
 *
 * Created by Greg Stewart on 4/14/17.
 */
@RunWith(Theories.class)
public class LongGeneratorTest {
	private Logger LOGGER = LoggerFactory.getLogger(LongGeneratorTest.class);
	
	private static final int NUM_TEST_ITERATIONS = 10000000;
	
	private static final long TEST_LOWER = 100L;
	private static final long TEST_UPPER = 200L;
	
	/** The different exceptions to test. */
	@DataPoints
	public static List<Class<? extends LongGenerator>> exceptionsToTest = Arrays.asList(
			RandGenerator.class
	);
	
	@Theory
	public void testLongGenerators(Class<? extends LongGenerator> curClass) throws Throwable{
		Constructor<? extends LongGenerator> constBase = curClass.getConstructor();
		Constructor<? extends LongGenerator> constBounds = curClass.getConstructor(Long.TYPE, Long.TYPE);
		
		LongGenerator gen = constBase.newInstance();
		
		assertThat("Default TEST_LOWER bound was not set in constructor.", gen.getLowerBound(), is(LongGenerator.DEFAULT_LOWER));
		assertThat("Default TEST_UPPER bound was not set in constructor.", gen.getUpperBound(), is(LongGenerator.DEFAULT_UPPER));
		
		gen = constBounds.newInstance(TEST_UPPER, TEST_LOWER);
		
		assertThat("Got wrong TEST_LOWER bound back.", TEST_LOWER, is(gen.getLowerBound()));
		assertThat("Got wrong TEST_UPPER bound back.", TEST_UPPER, is(gen.getUpperBound()));
		
		try {
			constBounds.newInstance(TEST_LOWER, TEST_UPPER);
			Assert.fail("Didn't throw an exception when passing inverted TEST_UPPER and TEST_LOWER.");
		}catch (Throwable e){
			//hacky but works
			if(!(e.getCause() instanceof OwatRandException)){
				Assert.fail();
			}
		}
		gen = constBase.newInstance();
		
		LOGGER.info("Testing setBounds().");
		gen.setBounds(TEST_UPPER, TEST_LOWER);
		assertThat("Got wrong TEST_LOWER bound back.", TEST_LOWER, is(gen.getLowerBound()));
		assertThat("Got wrong TEST_UPPER bound back.", TEST_UPPER, is(gen.getUpperBound()));
		
		LOGGER.info("Testing setLower().");
		gen = constBounds.newInstance(TEST_UPPER, TEST_LOWER);
		gen.setLowerBound(TEST_LOWER);
		assertThat("Got wrong TEST_LOWER bound back.", TEST_LOWER, is(gen.getLowerBound()));
		try{
			gen.setLowerBound(TEST_UPPER + 1L);
			Assert.fail("Didn't throw an exception when setting TEST_LOWER to a value higher than the set TEST_UPPER value.");
		}catch (OwatRandException e){
			//no need to do anything
		}
		
		LOGGER.info("Testing setUpper().");
		gen = constBounds.newInstance(TEST_LOWER + 1, TEST_LOWER);
		gen.setUpperBound(TEST_UPPER);
		assertThat("Got wrong TEST_UPPER bound back.", TEST_UPPER, is(gen.getUpperBound()));
		try{
			gen.setUpperBound(TEST_LOWER - 1L);
			Assert.fail("Didn't throw an exception when setting TEST_UPPER to a value TEST_LOWER than the set TEST_LOWER value.");
		}catch (OwatRandException e){
			//no need to do anything
		}
		
		gen = constBounds.newInstance(TEST_UPPER, TEST_LOWER);
		LOGGER.info("Testing getting numbers within the integer range using {}.", curClass);
		for(int i = 0; i < NUM_TEST_ITERATIONS; i++){
			long cur = gen.next();
			assertTrue("Number greater than the TEST_UPPER bounds.", cur >= TEST_LOWER);
			assertTrue("Number TEST_LOWER than the TEST_LOWER bounds.", cur <= TEST_UPPER);
		}
		LOGGER.info("Done testing.");
	}
}
