package tests.structure.set;

import com.ebp.owat.lib.dataStructure.set.LongLinkedList;
import com.ebp.owat.lib.dataStructure.set.OwatNodeSetException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testModels.structure.set.LongLinkedListTestModels;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests the LongLinkedList class.
 *
 * Created by Greg Stewart on 4/1/17.
 */
public class LongLinkedListTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(LongLinkedListTest.class);
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	private LongLinkedList<Boolean> testingNodeList;
	
	@Before
	public void setup(){
		testingNodeList = null;
	}
	
	@Test
	public void testLongLinkedListConstructors(){
		LOGGER.info("Testing the constructors of the LongLinkedList");
		
		testingNodeList = new LongLinkedList<>();
		assertEquals("Basic constructor didn't set the default capacity correctly.", LongLinkedList.DEFAULT_CAPACITY, testingNodeList.getCapacity());
		assertEquals("Basic constructor somehow has nodes in it.", 0, testingNodeList.sizeL());
		
		testingNodeList = new LongLinkedList<>(LongLinkedListTestModels.testingCapacity);
		assertEquals("Constructor didn't set the capacity correctly.", LongLinkedListTestModels.testingCapacity, testingNodeList.getCapacity());
		
		testingNodeList = new LongLinkedList<>(LongLinkedListTestModels.testingArray);
		assertEquals("Constructor has wrong number of nodes.", LongLinkedListTestModels.testingArray.size(), testingNodeList.sizeL());
		
		testingNodeList = new LongLinkedList<>(LongLinkedListTestModels.testingCapacity, LongLinkedListTestModels.testingArray);
		assertEquals("Constructor has wrong number of nodes.", LongLinkedListTestModels.testingArray.size(), testingNodeList.sizeL());
		assertEquals("Constructor didn't set the capacity correctly.", LongLinkedListTestModels.testingCapacity, testingNodeList.getCapacity());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testFullListThrowsException(){
		//TODO
	}
	
	
}
