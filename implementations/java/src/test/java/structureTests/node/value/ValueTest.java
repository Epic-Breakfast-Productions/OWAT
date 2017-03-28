package structureTests.node.value;

import com.ebp.owat.lib.dataStructure.node.value.BitValue;
import com.ebp.owat.lib.dataStructure.node.value.ByteValue;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests to ensure that the different values operate appropriately.
 *
 * Created by Greg Stewart on 3/26/17.
 */
public class ValueTest {
	
	private Logger LOGGER = LoggerFactory.getLogger(ValueTest.class);
	
	@Test
	public void testBitValueWithoutIsOriginal() {
		LOGGER.info("Starting the test for a BitValue.");
		BitValue valueTesting;
		
		LOGGER.info("Testing when giving a value of false.");
		valueTesting = new BitValue(false);
		assertFalse(valueTesting.getPrimValue());
		assertFalse(valueTesting.isOriginal());
		
		LOGGER.info("Testing when giving a value of true.");
		valueTesting = new BitValue(true);
		assertTrue(valueTesting.getPrimValue());
		assertFalse(valueTesting.isOriginal());
		
		LOGGER.info("Testing when giving a value of true, and is original.");
		valueTesting = new BitValue(true, true);
		assertTrue(valueTesting.getPrimValue());
		assertTrue(valueTesting.isOriginal());
		
		LOGGER.info("Testing when giving a value of true, and is not original.");
		valueTesting = new BitValue(true, false);
		assertTrue(valueTesting.getPrimValue());
		assertFalse(valueTesting.isOriginal());
		
		LOGGER.info("Testing when giving a value of false, and is original.");
		valueTesting = new BitValue(false, true);
		assertFalse(valueTesting.getPrimValue());
		assertTrue(valueTesting.isOriginal());
		
		LOGGER.info("Testing when giving a value of false, and is not original.");
		valueTesting = new BitValue(false, false);
		assertFalse(valueTesting.getPrimValue());
		assertFalse(valueTesting.isOriginal());
	}
	
	@Test
	public void testByteValue() {
		LOGGER.info("Starting the test for a ByteValue.");
		ByteValue valueTesting;
		byte testingVal = 111;
		
		LOGGER.info("Testing when giving a value.");
		valueTesting = new ByteValue(testingVal);
		assertTrue(valueTesting.getPrimValue() == testingVal);
		assertFalse(valueTesting.isOriginal());
		
		testingVal = 25;
		LOGGER.info("Testing when giving a value, and is not original.");
		valueTesting = new ByteValue(testingVal, false);
		assertTrue(valueTesting.getPrimValue() == testingVal);
		assertFalse(valueTesting.isOriginal());
		
		testingVal = 83;
		LOGGER.info("Testing when giving a value, and is original.");
		valueTesting = new ByteValue(testingVal, true);
		assertTrue(valueTesting.getPrimValue() == testingVal);
		assertTrue(valueTesting.isOriginal());
	}
}
