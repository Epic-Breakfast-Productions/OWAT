package tests.structure.set;

import com.ebp.owat.lib.dataStructure.set.LongLinkedList;
import com.ebp.owat.lib.dataStructure.set.OwatNodeSetException;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testModels.structure.set.LongLinkedListTestModels;

import java.util.*;

import static org.junit.Assert.*;

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
	
	/**
	 * Tests that the different lists are the same.
	 * @param listInQuestion The list we are testing
	 * @param verifyingLists The list(s) that, which combined, will be used to test the list against.
	 */
	private void testListsAreTheSame(List listInQuestion, boolean testLengths, List<Long>... verifyingLists){
		List<Long> verifyingList = new ArrayList<>();
		
		for(List curList : verifyingLists){
			verifyingList.addAll(curList);
		}
		
		if(testLengths) {
			assertEquals("The lists are of different lengths.", listInQuestion.size(), verifyingList.size());
		}
		
		for(int i = 0; i < listInQuestion.size(); i++){
			assertEquals(
					"The values inserted were wrong.",
					listInQuestion.get(i),
					verifyingList.get(i)
			);
		}
	}
	
	
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
	
	@Test
	public void testLongLinkedListQueueMethodsPollFirstLast(){
		LOGGER.info("Testing LongLinkedList's poll first/last methods.");
		
		LOGGER.info("Testing that it returns null on empty list.");
		testingNodeList = new LongLinkedList<>();
		assertNull("Empty list failed to return null on poll.", testingNodeList.pollFirst());
		assertNull("Empty list failed to return null on poll.", testingNodeList.pollLast());
		
		LOGGER.info("Testing that the LongLinkedList appropriately polls elements.");
		testingNodeList = new LongLinkedList<>(LongLinkedListTestModels.testingArray);
		
		Long valReturned = testingNodeList.pollLast();
		assertEquals("Wrong value returned.",valReturned, LongLinkedListTestModels.testingArray.get(LongLinkedListTestModels.testingArray.size() - 1));
		assertEquals("Resulting size was wrong.", testingNodeList.size(), LongLinkedListTestModels.testingArray.size() - 1);
		
		valReturned = testingNodeList.pollFirst();
		assertEquals("Wrong value returned.",valReturned, LongLinkedListTestModels.testingArray.get(0));
		assertEquals("Resulting size was wrong.", testingNodeList.size(), LongLinkedListTestModels.testingArray.size() - 2);
	}
	
	
	@Test
	public void testLongLinkedListQueueMethodsGetFirstLast(){
		LOGGER.info("Testing LongLinkedList's get first/last methods.");
		
		LOGGER.info("Testing that it throws on empty list.");
		testingNodeList = new LongLinkedList<>();
		try{
			testingNodeList.getFirst();
			Assert.fail("Failed to throw when getting from empty list.");
		}catch(NoSuchElementException e){}
		try{
			testingNodeList.getLast();
			Assert.fail("Failed to throw when getting from empty list.");
		}catch(NoSuchElementException e){}
		
		LOGGER.info("Testing that the LongLinkedList appropriately gets elements.");
		testingNodeList = new LongLinkedList<>(LongLinkedListTestModels.testingArray);
		
		Long valReturned = testingNodeList.getLast();
		assertEquals("Wrong value returned.",valReturned, LongLinkedListTestModels.testingArray.get(LongLinkedListTestModels.testingArray.size() - 1));
		
		valReturned = testingNodeList.getFirst();
		assertEquals("Wrong value returned.",valReturned, LongLinkedListTestModels.testingArray.get(0));
	}
	
	@Test
	public void testLongLinkedListQueueMethodsPeekFirstLast(){
		LOGGER.info("Testing LongLinkedList's peek first/last methods.");
		
		LOGGER.info("Testing that it returns null on empty list.");
		testingNodeList = new LongLinkedList<>();
		assertNull("Empty list failed to return null on peek.", testingNodeList.peekFirst());
		assertNull("Empty list failed to return null on peek.", testingNodeList.peekLast());
		
		LOGGER.info("Testing that the LongLinkedList appropriately peeks elements.");
		testingNodeList = new LongLinkedList<>(LongLinkedListTestModels.testingArray);
		
		Long valReturned = testingNodeList.peekLast();
		assertEquals("Wrong value returned.",valReturned, LongLinkedListTestModels.testingArray.get(LongLinkedListTestModels.testingArray.size() - 1));
		valReturned = testingNodeList.peekFirst();
		assertEquals("Wrong value returned.",valReturned, LongLinkedListTestModels.testingArray.get(0));
	}
	
	
	@Test
	public void testLongLinkedListQueueMethodsRemoveFirstLastOccurrence(){
		LOGGER.info("Testing the remove first/last occurrence methods.");
		
		testingNodeList = new LongLinkedList<>();
		assertFalse("returned true when should have returned false in empty list.", testingNodeList.removeFirstOccurrence(0L));
		assertFalse("returned true when should have returned false in empty list.", testingNodeList.removeLastOccurrence(0L));
		
		testingNodeList = new LongLinkedList<>(LongLinkedListTestModels.testingArray);
		testingNodeList.addAll(LongLinkedListTestModels.testingArray);
		
		long origLength = testingNodeList.size();
		
		assertFalse("returned true when should have returned false in populated list.", testingNodeList.removeFirstOccurrence(-1L));
		assertFalse("returned true when should have returned false in populated list.", testingNodeList.removeLastOccurrence(-1L));
		
		assertTrue("", testingNodeList.removeFirstOccurrence(0L));
		assertEquals(testingNodeList.size(), origLength - 1);
		
		assertTrue("", testingNodeList.removeLastOccurrence(0L));
		assertEquals(testingNodeList.size(), origLength - 2);
		
		testingNodeList.addAll(LongLinkedListTestModels.testingArrayWithNulls);
		origLength = testingNodeList.size();
		
		assertTrue("", testingNodeList.removeFirstOccurrence(null));
		assertEquals(testingNodeList.size(), origLength - 1);
		
		assertTrue("", testingNodeList.removeLastOccurrence(null));
		assertEquals(testingNodeList.size(), origLength - 2);
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////// List methods //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void testLongLinkedListListMethodsAddAll(){
		LOGGER.info("Testing the (List)addAll method for LongLinkedList.");
		testingNodeList = new LongLinkedList<>(LongLinkedListTestModels.testingArray);
		LOGGER.info("Testing that methods throws appropriately on bad index.");
		try{
			testingNodeList.addAll((int)-1, LongLinkedListTestModels.testingArray);
			Assert.fail("Failed to throw on negative index.");
		}catch (IndexOutOfBoundsException e){}
		try{
			int outOfBoundsIndex = testingNodeList.size();
			testingNodeList.addAll(outOfBoundsIndex, LongLinkedListTestModels.testingArray);
			LOGGER.error("Numbers: Index given: {}, Size of list: {}", outOfBoundsIndex, testingNodeList.size());
			Assert.fail("Failed to throw on out of bounds index.");
		}catch (IndexOutOfBoundsException e){}
		
		testingNodeList = new LongLinkedList<>();
		
		LOGGER.info("Testing that nodes can be appropriately be inserted into empty list.");
		assertTrue(testingNodeList.addAll(0, LongLinkedListTestModels.testingArray));
		testListsAreTheSame(testingNodeList, true, LongLinkedListTestModels.testingArray);
		
		//TODO:: test inserting in start, middle, end makes appropriate list. Test at capacity.
		
		assertTrue(testingNodeList.addAll(0, LongLinkedListTestModels.fullTestingArray));
		testListsAreTheSame(testingNodeList, true, LongLinkedListTestModels.fullTestingArray, LongLinkedListTestModels.testingArray);
		
		
	}
	
	@Test
	public void testLongLinkedListListMethodsGet(){
		LOGGER.info("Testing the get method for LongLinkedList.");
		
		testingNodeList = new LongLinkedList<>(LongLinkedListTestModels.testingArray);
		
		LOGGER.info("Testing that methods throws appropriately on bad index.");
		try{
			testingNodeList.get((int)-1);
			Assert.fail("Failed to throw on negative index.");
		}catch (IndexOutOfBoundsException e){}
		try{
			int outOfBoundsIndex = testingNodeList.size();
			testingNodeList.get(outOfBoundsIndex);
			LOGGER.error("Numbers: Index given: {}, Size of list: {}", outOfBoundsIndex, testingNodeList.size());
			Assert.fail("Failed to throw on out of bounds index.");
		}catch (IndexOutOfBoundsException e){}
		
		LOGGER.info("Testing that we get correct values back.");
		for(int i = 0; i < testingNodeList.size(); i++){
			assertEquals(
				"The value returned by the LongLinkedList was wrong.",
				LongLinkedListTestModels.testingArray.get(i),
				testingNodeList.get(i)
			);
		}
	}
	
	
	
}
