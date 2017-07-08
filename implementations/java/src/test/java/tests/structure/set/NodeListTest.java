package tests.structure.set;

import com.ebp.owat.lib.dataStructure.node.Node;
import com.ebp.owat.lib.dataStructure.set.NodeList;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testModels.structure.set.ListTestCheckers;
import testModels.structure.set.NodeListTestModels;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

/**
 * Tests the NodeList class.
 *
 * Created by Greg Stewart on 7/8/2017.
 */
public class NodeListTest{
	private static final Logger LOGGER = LoggerFactory.getLogger(NodeListTest.class);
	
	private NodeList<Node> testingNodeList;
	private LinkedList<Node> checkingNodeList;
	
	@Before
	public void setup(){
		testingNodeList = null;
		checkingNodeList = null;
	}
	
	@Test
	public void testNodeListConstructors(){
		LOGGER.info("Testing the NodeList constructors.");
		
		LOGGER.info("Testing the basic constructors.");
		this.testingNodeList = new NodeList<>();
		this.checkingNodeList = new LinkedList<>();
		
		assertEquals(NodeList.DEFAULT_TYPE, this.testingNodeList.type);
		ListTestCheckers.testListsAreTheSame(this.testingNodeList, true, this.checkingNodeList);
		
		LOGGER.info("Testing NodeList(Type).");
		this.testingNodeList = new NodeList<>(NodeList.Type.COL);
		assertEquals(NodeList.Type.COL, this.testingNodeList.type);
		this.testingNodeList = new NodeList<>((NodeList.Type)null);
		assertEquals(NodeList.DEFAULT_TYPE, this.testingNodeList.type);
		
		LOGGER.info("Testing NodeList(Collection).");
		this.testingNodeList = new NodeList<>(NodeListTestModels.testingArrayWithNulls);
		this.checkingNodeList = new LinkedList<>(NodeListTestModels.testingArrayWithNulls);
		
		assertEquals(NodeList.DEFAULT_TYPE, this.testingNodeList.type);
		ListTestCheckers.testListsAreTheSame(this.testingNodeList, true, this.checkingNodeList);
		
		LOGGER.info("Testing NodeList(Type, Collection).");
		this.testingNodeList = new NodeList<>(NodeList.Type.COL, NodeListTestModels.testingArrayWithNulls);
		this.checkingNodeList = new LinkedList<>(NodeListTestModels.testingArrayWithNulls);
		
		assertEquals(NodeList.Type.COL, this.testingNodeList.type);
		ListTestCheckers.testListsAreTheSame(this.testingNodeList, true, this.checkingNodeList);
		
		
		this.testingNodeList = new NodeList<>(null, NodeListTestModels.testingArrayWithNulls);
		assertEquals(NodeList.DEFAULT_TYPE, this.testingNodeList.type);
	}
}
