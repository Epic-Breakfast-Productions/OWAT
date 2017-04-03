package tests.structure.node;

import com.ebp.owat.lib.dataStructure.node.BitNode;
import com.ebp.owat.lib.dataStructure.node.Node;
import com.ebp.owat.lib.dataStructure.node.value.BitValue;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testModels.structure.node.NodeTestModels;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests to ensure that the different nodes operate appropriately.
 *
 * Created by Greg Stewart on 3/26/17.
 */
public class NodeTest {
	private Logger LOGGER = LoggerFactory.getLogger(NodeTest.class);
	
	/**
	 * These will be arranged as such:
	 *
	 * 0 1 2
	 * 3 4 5
	 * 6 7 8
	 */
	private ArrayList<BitNode> testingNodes = NodeTestModels.getTestingNodes();
	
	@Test
	public void testNodeDirsMadeCorrectly(){
		LOGGER.info("Testing that nodes were created correctly for the other tests, in so testing that direction setting works.");
		
		assertTrue(this.testingNodes.get(0).borders(this.testingNodes.get(1)));
		assertTrue(this.testingNodes.get(0).borders(this.testingNodes.get(3)));
		
		assertTrue(this.testingNodes.get(1).borders(this.testingNodes.get(0)));
		assertTrue(this.testingNodes.get(1).borders(this.testingNodes.get(2)));
		assertTrue(this.testingNodes.get(1).borders(this.testingNodes.get(4)));
		
		assertTrue(this.testingNodes.get(2).borders(this.testingNodes.get(1)));
		assertTrue(this.testingNodes.get(2).borders(this.testingNodes.get(5)));
		
		assertTrue(this.testingNodes.get(3).borders(this.testingNodes.get(0)));
		assertTrue(this.testingNodes.get(3).borders(this.testingNodes.get(4)));
		assertTrue(this.testingNodes.get(3).borders(this.testingNodes.get(6)));
		
		assertTrue(this.testingNodes.get(4).borders(this.testingNodes.get(1)));
		assertTrue(this.testingNodes.get(4).borders(this.testingNodes.get(3)));
		assertTrue(this.testingNodes.get(4).borders(this.testingNodes.get(5)));
		assertTrue(this.testingNodes.get(4).borders(this.testingNodes.get(7)));
		
		assertTrue(this.testingNodes.get(5).borders(this.testingNodes.get(4)));
		assertTrue(this.testingNodes.get(5).borders(this.testingNodes.get(2)));
		assertTrue(this.testingNodes.get(5).borders(this.testingNodes.get(8)));
		
		assertTrue(this.testingNodes.get(6).borders(this.testingNodes.get(3)));
		assertTrue(this.testingNodes.get(6).borders(this.testingNodes.get(7)));
		
		assertTrue(this.testingNodes.get(7).borders(this.testingNodes.get(6)));
		assertTrue(this.testingNodes.get(7).borders(this.testingNodes.get(4)));
		assertTrue(this.testingNodes.get(7).borders(this.testingNodes.get(8)));
		
		assertTrue(this.testingNodes.get(8).borders(this.testingNodes.get(7)));
		assertTrue(this.testingNodes.get(8).borders(this.testingNodes.get(5)));
	}
	
	@Test
	public void testNodeBordersSpecifics() {
		LOGGER.info("Testing that testing for bordering specific sides works.");
		
		assertTrue(this.testingNodes.get(4).borders(this.testingNodes.get(1), Node.NodeDir.NORTH));
		assertTrue(this.testingNodes.get(4).borders(this.testingNodes.get(3), Node.NodeDir.WEST));
		assertTrue(this.testingNodes.get(4).borders(this.testingNodes.get(5), Node.NodeDir.EAST));
		assertTrue(this.testingNodes.get(4).borders(this.testingNodes.get(7), Node.NodeDir.SOUTH));
	}
	
	@Test
	public void testNodeTradesValues() {
		LOGGER.info("Testing that nodes can trade values appropriately.");
		
		BitValue valueFromZero = this.testingNodes.get(0).getValue();
		BitValue valueFromOne = this.testingNodes.get(1).getValue();
		
		this.testingNodes.get(0).trade(this.testingNodes.get(1));
		
		assertTrue(this.testingNodes.get(0).getValue() == valueFromOne);
		assertTrue(this.testingNodes.get(1).getValue() == valueFromZero);
	}
	
	@Test
	public void testNodeBorder() {
		LOGGER.info("Testing that nodes can appropriately determine if it is a border or not.");
		
		LOGGER.info("Testing nodes that aren't borders to the whole structure.");
		assertFalse(this.testingNodes.get(4).isBorder());
		
		LOGGER.info("Testing nodes that are borders to the whole structure.");
		assertTrue(this.testingNodes.get(1).isBorder());
		assertTrue(this.testingNodes.get(0).isBorder());
		assertTrue(this.testingNodes.get(2).isBorder());
		assertTrue(this.testingNodes.get(3).isBorder());
		assertTrue(this.testingNodes.get(5).isBorder());
		assertTrue(this.testingNodes.get(6).isBorder());
		assertTrue(this.testingNodes.get(7).isBorder());
		assertTrue(this.testingNodes.get(8).isBorder());
		
		LOGGER.info("Testing nodes that are borders to the whole structure in specific directions.");
		assertTrue(this.testingNodes.get(1).isBorder(Node.NodeDir.NORTH));
		assertTrue(this.testingNodes.get(3).isBorder(Node.NodeDir.WEST));
		assertTrue(this.testingNodes.get(5).isBorder(Node.NodeDir.EAST));
		assertTrue(this.testingNodes.get(7).isBorder(Node.NodeDir.SOUTH));
	}
	
	@Test
	public void testNodeCorner() {
		LOGGER.info("Testing that nodes can appropriately determine if it is a corner or not.");
		
		LOGGER.info("Testing nodes that aren't corners.");
		assertFalse(this.testingNodes.get(4).isCorner());
		assertFalse(this.testingNodes.get(1).isCorner());
		assertFalse(this.testingNodes.get(3).isCorner());
		assertFalse(this.testingNodes.get(5).isCorner());
		assertFalse(this.testingNodes.get(7).isCorner());
		
		LOGGER.info("Testing nodes that are corners.");
		assertTrue(this.testingNodes.get(0).isCorner());
		assertTrue(this.testingNodes.get(2).isCorner());
		assertTrue(this.testingNodes.get(6).isCorner());
		assertTrue(this.testingNodes.get(8).isCorner());
	}
}
