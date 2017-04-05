package testModels.structure.set;

import com.ebp.owat.lib.dataStructure.node.BitNode;
import com.ebp.owat.lib.dataStructure.set.node.NodeList;

/**
 * Class to build the necessary objects to make the BigLinkedList tests work.
 *
 * Created by Greg Stewart on 4/1/17.
 */
public class BigLinkedListTestModels {
	/** The nodes for testing. */
	private static NodeList<BitNode> testingNodeList = null;
	private static NodeList<BitNode> bigTestingNodeList = null;
	
	public static void buildBigTestingNodeList(){
		bigTestingNodeList = new NodeList<>();
	}
	
	public static void buildTestingNodeList(){
		testingNodeList = new NodeList<>();
	}
	
	public static NodeList<BitNode> getTestingNodeList(boolean bigList){
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
