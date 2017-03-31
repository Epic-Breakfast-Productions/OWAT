package testModels.structure.node;

import com.ebp.owat.lib.dataStructure.node.BitNode;
import com.ebp.owat.lib.dataStructure.node.value.BitValue;

import java.util.ArrayList;

/**
 * Class to generate nodes for testing.
 *
 * Created by Greg Stewart on 3/30/17.
 */
public class NodeTestModels {
	/** The nodes for testing. */
	private static ArrayList<BitNode> testingNodes = null;
	
	/**
	 * Sets up the testing nodes for testing.
	 */
	private static void buildTestingNodes(){
		testingNodes = new ArrayList<>(9);
		for(int i = 0; i < 9; i++){
			testingNodes.add(
					new BitNode(
							new BitValue(i%2 == 0)
					)
			);
		}
		
		testingNodes.get(0).setEastNode(testingNodes.get(1));
		testingNodes.get(1).setEastNode(testingNodes.get(2)).setSouthNode(testingNodes.get(4));
		testingNodes.get(2).setSouthNode(testingNodes.get(5));
		testingNodes.get(5).setWestNode(testingNodes.get(4)).setSouthNode(testingNodes.get(8));
		testingNodes.get(8).setWestNode(testingNodes.get(7));
		testingNodes.get(7).setNorthNode(testingNodes.get(4)).setWestNode(testingNodes.get(6));
		testingNodes.get(6).setNorthNode(testingNodes.get(3));
		testingNodes.get(3).setNorthNode(testingNodes.get(0)).setEastNode(testingNodes.get(4));
	}
	
	/**
	 * Gets the testing nodes.
	 *
	 * @return The nodes for testing.
	 */
	public static ArrayList<BitNode> getTestingNodes(){
		if(testingNodes == null){
			buildTestingNodes();
		}
		return testingNodes;
	}
}
