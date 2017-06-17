package tests.structure.set;

import com.ebp.owat.lib.dataStructure.set.LongLinkedList;
import com.ebp.owat.lib.dataStructure.set.OwatNodeSetException;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testModels.structure.set.LongLinkedListTestModels;

import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
	
	private LongLinkedList<Long> testingNodeList;
	
	private boolean thrown = false;
	private boolean done = false;
	
	@Before
	public void setup(){
		thrown = false;
		done = false;
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
		
		testingNodeList = new LongLinkedList<>(LongLinkedListTestModels.testingCapacity, LongLinkedListTestModels.fullTestingArray);
		assertEquals("Constructor has wrong number of nodes.", LongLinkedListTestModels.fullTestingArray.size(), testingNodeList.sizeL());
		assertEquals("Constructor didn't set the capacity correctly.", LongLinkedListTestModels.testingCapacity, testingNodeList.getCapacity());
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////// Queue/Dequeue methods /////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testLongLinkedListQueueMethodsAddFirstLast(){
		LOGGER.info("Testing LongLinkedListNodes.addFirst/Last that come from the Queue/Dequeue interface.");
		
		LOGGER.info("Testing add first/last with an empty list.");
		testingNodeList = new LongLinkedList<>();
		testingNodeList.addFirst(0L);
		assertEquals("addFirst did not add an element to the list.", 1, testingNodeList.size());
		testingNodeList = new LongLinkedList<>();
		testingNodeList.addLast(5L);
		assertEquals("addLast did not add an element to the list.", 1, testingNodeList.size());
		
		LOGGER.info("Testing add first/last with a list.");
		testingNodeList = new LongLinkedList<>(LongLinkedListTestModels.fullTestingArray);
		testingNodeList.addFirst(0L);
		assertEquals("addFirst did not add an element to the list.", LongLinkedListTestModels.fullTestingArray.size() + 1, testingNodeList.size());
		testingNodeList = new LongLinkedList<>(LongLinkedListTestModels.fullTestingArray);
		testingNodeList.addLast(5L);
		assertEquals("addLast did not add an element to the list.", LongLinkedListTestModels.fullTestingArray.size() + 1, testingNodeList.size());
		
		LOGGER.info("Testing add first/last with a full list.");
		testingNodeList = new LongLinkedList<>(LongLinkedListTestModels.testingCapacity, LongLinkedListTestModels.fullTestingArray);
		try{
			testingNodeList.addFirst(0L);
			Assert.fail("addFirst in full list did not fail.");
		}catch (IllegalStateException e){}
		try{
			testingNodeList.addLast(0L);
			Assert.fail("addLast in full list did not fail.");
		}catch (IllegalStateException e){}
	}
	
	@Test
	public void testLongLinkedListQueueMethodsOfferFirstLast(){
		LOGGER.info("Testing LongLinkedListNodes.offerFirst/Last that come from the Queue/Dequeue interface.");
		
		LOGGER.info("Testing add first/last with an empty list.");
		testingNodeList = new LongLinkedList<>();
		assertTrue(testingNodeList.offerFirst(0L));
		assertEquals("offerFirst did not add an element to the list.", 1, testingNodeList.size());
		testingNodeList = new LongLinkedList<>();
		assertTrue(testingNodeList.offerLast(0L));
		assertEquals("offerLast did not add an element to the list.", 1, testingNodeList.size());
		
		LOGGER.info("Testing offer first/last with a list.");
		testingNodeList = new LongLinkedList<>(LongLinkedListTestModels.fullTestingArray);
		assertTrue(testingNodeList.offerFirst(0L));
		assertEquals("offerFirst did not add an element to the list.", LongLinkedListTestModels.fullTestingArray.size() + 1, testingNodeList.size());
		testingNodeList = new LongLinkedList<>(LongLinkedListTestModels.fullTestingArray);
		assertTrue(testingNodeList.offerLast(0L));
		assertEquals("offerLast did not add an element to the list.", LongLinkedListTestModels.fullTestingArray.size() + 1, testingNodeList.size());
		
		LOGGER.info("Testing offer first/last with a full list.");
		testingNodeList = new LongLinkedList<>(LongLinkedListTestModels.testingCapacity, LongLinkedListTestModels.fullTestingArray);
		assertFalse("offerFirst returned true after list being full", testingNodeList.offerFirst(0L));
		assertFalse("offerLast returned true after list being full", testingNodeList.offerLast(0L));
	}
	
	@Test
	public void testLongLinkedListQueueMethodsRemoveFirstLast(){
		LOGGER.info("Testing LongLinkedList's remove first/last methods.");
		
		LOGGER.info("Testing that it throws on empty list.");
		testingNodeList = new LongLinkedList<>();
		try{
			testingNodeList.removeFirst();
			Assert.fail("Failed to throw when removing from empty list.");
		}catch(NoSuchElementException e){}
		try{
			testingNodeList.removeLast();
			Assert.fail("Failed to throw when removing from empty list.");
		}catch(NoSuchElementException e){}
		
		LOGGER.info("Testing that the LongLinkedList appropriately removes elements.");
		testingNodeList = new LongLinkedList<>(LongLinkedListTestModels.testingArray);
		
		Long valReturned = testingNodeList.removeLast();
		assertEquals("Wrong value returned.",valReturned, LongLinkedListTestModels.testingArray.get(LongLinkedListTestModels.testingArray.size() - 1));
		assertEquals("Resulting size was wrong.", testingNodeList.size(), LongLinkedListTestModels.testingArray.size() - 1);
		
		valReturned = testingNodeList.removeFirst();
		assertEquals("Wrong value returned.",valReturned, LongLinkedListTestModels.testingArray.get(0));
		assertEquals("Resulting size was wrong.", testingNodeList.size(), LongLinkedListTestModels.testingArray.size() - 2);
	}
}
