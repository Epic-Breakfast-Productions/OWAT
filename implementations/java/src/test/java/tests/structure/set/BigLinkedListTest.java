package tests.structure.set;

import com.ebp.owat.lib.dataStructure.node.BitNode;
import com.ebp.owat.lib.dataStructure.set.OwatNodeSetException;
import com.ebp.owat.lib.dataStructure.set.node.NodeList;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testModels.structure.set.BigLinkedListTestModels;


/**
 * Tests the BigLinkedList class.
 *
 * Created by Greg Stewart on 4/1/17.
 */
public class BigLinkedListTest {
	private Logger LOGGER = LoggerFactory.getLogger(BigLinkedListTest.class);
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	private NodeList<BitNode> testingNodeList = BigLinkedListTestModels.getTestingNodeList(false);
	private NodeList<BitNode> testingBigNodeList = BigLinkedListTestModels.getTestingNodeList(true);
	
	@Test public void nodeListSizeTest(){
		//TODO, test that the sizes of the lists are correct
		
		
		
		//test that the overridden size() fails
		exception.expect(OwatNodeSetException.class);
		testingNodeList.size();
	}
	
	@Test public void nodeListTypeTest(){
		//TODO
	}
	@Test public void nodeListShiftTest(){
		//TODO
	}
}
