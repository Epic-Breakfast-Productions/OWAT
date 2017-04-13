package testModels.structure.set;

import com.ebp.owat.lib.dataStructure.node.BitNode;
import com.ebp.owat.lib.dataStructure.set.BigLinkedList;

/**
 * Class to build the necessary objects to make the BigLinkedList tests work.
 *
 * Created by Greg Stewart on 4/1/17.
 */
public class BigLinkedListTestModels {
	/** The nodes for testing. */
	private static BigLinkedList<BitNode> testingNodeList = null;
	private static BigLinkedList<BitNode> bigTestingNodeList = null;
	
	public static void buildBigTestingNodeList(){
		bigTestingNodeList = new BigLinkedList<>();
	}
	
	public static void buildTestingNodeList(){
		testingNodeList = new BigLinkedList<>();
	}
	
	public static BigLinkedList<BitNode> getTestingNodeList(boolean bigList){
		if(bigList){
			if (bigTestingNodeList == null) {
				buildBigTestingNodeList();
			}
			return testingNodeList;
		}else {
			if (testingNodeList == null) {
				buildTestingNodeList();
			}
			return testingNodeList;
		}
	}
}
