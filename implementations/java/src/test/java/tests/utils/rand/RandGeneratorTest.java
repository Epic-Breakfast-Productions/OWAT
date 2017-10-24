package tests.utils.rand;

import com.ebp.owat.lib.utils.rand.RandGenerator;
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
		LOGGER.info("Testing RandGenerator extra constructors.");
		RandGenerator test = new RandGenerator();
		
		assertNotNull(test.getRandom());
		
		Random rand = new Random();
		test = new RandGenerator(rand);
		
		assertEquals("Random set by constructor is not the same as the one given to the constructor.", rand, test.getRandom());
		
		rand = new Random();
		
		test = new RandGenerator(rand, testUpper, testLower);
		
		assertEquals("Random set by constructor is not the same as the one given to the constructor.", rand, test.getRandom());
		assertEquals("Upper bound set by constructor is not the same as the one given to the constructor.", testUpper, test.getUpperBound());
		assertEquals("Random set by constructor is not the same as the one given to the constructor.", testLower, test.getLowerBound());
	}
}
