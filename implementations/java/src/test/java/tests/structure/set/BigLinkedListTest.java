package tests.structure.set;

import com.ebp.owat.lib.dataStructure.node.BitNode;
import com.ebp.owat.lib.dataStructure.set.BigLinkedList;
import com.ebp.owat.lib.dataStructure.set.OwatNodeSetException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testModels.structure.set.BigLinkedListTestModels;

import static org.junit.Assert.assertEquals;


/**
 * Tests the BigLinkedList class.
 *
 * Created by Greg Stewart on 4/1/17.
 */
public class BigLinkedListTest {
	private Logger LOGGER = LoggerFactory.getLogger(BigLinkedListTest.class);
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	private BigLinkedList<BitNode> testingNodeList = BigLinkedListTestModels.getTestingNodeList(false);
	private BigLinkedList<BitNode> testingBigNodeList = BigLinkedListTestModels.getTestingNodeList(true);
	
	@Test public void nodeListSizeTest(){
		//TODO, test that the sizes of the lists are correct
		
		//test that the overridden size() fails
		exception.expect(OwatNodeSetException.class);
		testingNodeList.size();
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
}
