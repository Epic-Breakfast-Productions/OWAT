package tests.structure.set;

import com.ebp.owat.lib.dataStructure.set.LongLinkedList;
import com.ebp.owat.lib.dataStructure.set.OwatNodeSetException;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testModels.structure.set.LongLinkedListTestModels;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests the LongLinkedList class.
 *
 * Created by Greg Stewart on 4/1/17.
 */
public class LongLinkedListTest {
	private Logger LOGGER = LoggerFactory.getLogger(LongLinkedListTest.class);
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	private LongLinkedList<Boolean> testingNodeList;
	
	@Test(expected = OwatNodeSetException.class)
	public void nodeListSizeExceptionTest(){
		testingNodeList = LongLinkedListTestModels.getTestingNodeList(false);
		testingNodeList.size();
	}
	
	@Test
	public void smallNodeListSizeTest(){
		testingNodeList = LongLinkedListTestModels.getTestingNodeList(false);
		assertTrue("Smaller node list size is wrong. Expected: " + LongLinkedListTestModels.testSize + " Actual: " + testingNodeList.listSize(), LongLinkedListTestModels.testSize == testingNodeList.listSize());
	}
	
	@Ignore
	@Test
	public void largeNodeListSizeTest(){
		LongLinkedList<Boolean> testingBigNodeList = LongLinkedListTestModels.getTestingNodeList(true);
		assertTrue("Larger node list size is wrong. Expected: " + LongLinkedListTestModels.bigTestSize + " Actual: " + testingBigNodeList.listSize(), LongLinkedListTestModels.bigTestSize == testingBigNodeList.listSize());
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
	
	@Test
	public void nodeListSubListTest(){
		testingNodeList = LongLinkedListTestModels.getTestingNodeList(false);
		
		long originalSize = LongLinkedListTestModels.testSize;
		
		LongLinkedList<Boolean> sublist = testingNodeList.getSubList(0, false);
		assertTrue("Getting a sublist of 0 entries returned a list that was not empty.", sublist.isEmpty());
		
		long numToGet = 5;
		
		sublist = testingNodeList.getSubList(numToGet, false);
		assertTrue("The sublist returned was the wrong length. Expected: " + numToGet + " Was: "+sublist.listSize(), sublist.listSize() == numToGet);
		
		sublist = testingNodeList.getSubList(numToGet, true);
		assertTrue("The sublist returned was the wrong length. Expected: " + numToGet + " Was: "+sublist.listSize(), sublist.listSize() == numToGet);
		assertTrue("The original list after cutoff was the wrong length. Expected: " + (originalSize - numToGet) + " Was: "+testingNodeList.listSize(), testingNodeList.listSize() == originalSize - numToGet);
	}
}
