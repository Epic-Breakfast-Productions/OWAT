package testModels.structure.set;

import com.ebp.owat.lib.dataStructure.node.BitNode;
import com.ebp.owat.lib.dataStructure.node.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Class to build the necessary objects to make the LongLinkedList tests work.
 *
 * Created by Greg Stewart on 4/1/17.
 */
public class NodeListTestModels {
	static private final Logger LOGGER = LoggerFactory.getLogger(NodeListTestModels.class);
	/**
	 * The nodes for testing.
	 */
	public static final List<Node> testingArray = Arrays.asList(new BitNode(null),new BitNode(null),new BitNode(null));
	public static final List<Node> testingArrayWithNulls = Arrays.asList(new BitNode(null), null, new BitNode(null), null);
	
}
