package tests.structure.set;

import com.ebp.owat.lib.dataStructure.node.BitNode;
import com.ebp.owat.lib.dataStructure.set.BigLinkedList;
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
 * Tests the BigLinkedList class.
 *
 * Created by Greg Stewart on 4/1/17.
 */
public class BigLinkedListTest {
	private Logger LOGGER = LoggerFactory.getLogger(BigLinkedListTest.class);
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	private BigLinkedList<Boolean> testingNodeList = BigLinkedListTestModels.getTestingNodeList(false);
	
	@Test(expected = OwatNodeSetException.class)
	public void nodeListSizeExceptionTest(){
		testingNodeList.size();
	}
	
	@Test
	public void smallNodeListSizeTest(){
		assertTrue("Smaller node list size is wrong. Expected: " + BigLinkedListTestModels.testSize.toString() + " Actual: " + testingNodeList.listSize().toString(), BigLinkedListTestModels.testSize.compareTo(testingNodeList.listSize()) == 0);
	}
	
	@Ignore
	@Test
	public void largeNodeListSizeTest(){
		BigLinkedList<Boolean> testingBigNodeList = BigLinkedListTestModels.getTestingNodeList(true);
		assertTrue("Larger node list size is wrong.", BigLinkedListTestModels.bigTestSize.compareTo(testingBigNodeList.listSize()) == 0);
	}
	
	
	@Test public void nodeListTypeTest(){
		LOGGER.info("Testing that NodeLists can correctly get their set types.");
		testingNodeList = new BigLinkedList<>(BigLinkedList.Type.COL);
		assertEquals(BigLinkedList.Type.COL, testingNodeList.getType());
		testingNodeList = new BigLinkedList<>(BigLinkedList.Type.ROW);
		assertEquals(BigLinkedList.Type.ROW, testingNodeList.getType());
		testingNodeList = new BigLinkedList<>(BigLinkedList.Type.NA);
		assertEquals(BigLinkedList.Type.NA, testingNodeList.getType());
		testingNodeList = new BigLinkedList<>();
		assertEquals(BigLinkedList.Type.NA, testingNodeList.getType());
	}
	
	@Ignore
	@Test
	public void nodeListSubListTest(){
		//TODO:: this
	}
}
