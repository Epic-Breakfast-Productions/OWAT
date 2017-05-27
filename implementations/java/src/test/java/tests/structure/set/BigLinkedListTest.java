package tests.structure.set;

import com.ebp.owat.lib.dataStructure.set.LongLinkedList;
import com.ebp.owat.lib.dataStructure.set.OwatNodeSetException;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testModels.structure.set.BigLinkedListTestModels;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Tests the LongLinkedList class.
 *
 * Created by Greg Stewart on 4/1/17.
 */
public class BigLinkedListTest {
	private Logger LOGGER = LoggerFactory.getLogger(BigLinkedListTest.class);
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	private LongLinkedList<Boolean> testingNodeList = BigLinkedListTestModels.getTestingNodeList(false);
	
	@Test(expected = OwatNodeSetException.class)
	public void nodeListSizeExceptionTest(){
		testingNodeList.size();
	}
	
	@Test
	public void smallNodeListSizeTest(){
		assertTrue("Smaller node list size is wrong. Expected: " + BigLinkedListTestModels.testSize + " Actual: " + testingNodeList.listSize(), BigLinkedListTestModels.testSize == testingNodeList.listSize());
	}
	
	@Ignore
	@Test
	public void largeNodeListSizeTest(){
		LongLinkedList<Boolean> testingBigNodeList = BigLinkedListTestModels.getTestingNodeList(true);
		assertTrue("Larger node list size is wrong. Expected: " + BigLinkedListTestModels.bigTestSize + " Actual: " + testingBigNodeList.listSize(), BigLinkedListTestModels.bigTestSize == testingBigNodeList.listSize());
	}
	
	@Test public void nodeListTypeTest(){
		LOGGER.info("Testing that NodeLists can correctly get their set types.");
		testingNodeList = new LongLinkedList<>(LongLinkedList.Type.COL);
		assertEquals(LongLinkedList.Type.COL, testingNodeList.getType());
		testingNodeList = new LongLinkedList<>(LongLinkedList.Type.ROW);
		assertEquals(LongLinkedList.Type.ROW, testingNodeList.getType());
		testingNodeList = new LongLinkedList<>(LongLinkedList.Type.NA);
		assertEquals(LongLinkedList.Type.NA, testingNodeList.getType());
		testingNodeList = new LongLinkedList<>();
		assertEquals(LongLinkedList.Type.NA, testingNodeList.getType());
	}
	
	@Ignore
	@Test
	public void nodeListSubListTest(){
		//TODO:: this
	}
}
