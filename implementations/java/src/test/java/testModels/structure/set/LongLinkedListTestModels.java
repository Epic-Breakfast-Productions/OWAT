package testModels.structure.set;

import com.ebp.owat.lib.dataStructure.set.LongLinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Class to build the necessary objects to make the LongLinkedList tests work.
 *
 * Created by Greg Stewart on 4/1/17.
 */
public class LongLinkedListTestModels {
	static private final Logger LOGGER = LoggerFactory.getLogger(LongLinkedListTestModels.class);
	/**
	 * The nodes for testing.
	 */
	public static final long testingCapacity = 5L;
	public static final List<Long> testingArray = Arrays.asList(0L, 1L, 2L, 3L);
	public static final List<Long> testingArrayWithNulls = Arrays.asList(0L, null, 2L, null);
	public static final List<Long> fullTestingArray = Arrays.asList(0L, 1L, 2L, 3L, 4L);
	public static final List<Long> shortTestingArray = Arrays.asList(0L, 1L);
	
	/**
	 * Tests that the different lists are the same.
	 *
	 * @param listInQuestion The list we are testing
	 * @param verifyingLists The list(s) that, which combined, will be used to test the list against.
	 */
	public static void testListsAreTheSame(List<? extends Object> listInQuestion, boolean testLengths, List<? extends Object>... verifyingLists) {
		List<? extends Object> verifyingList = new ArrayList<>();
		
		for (List curList : verifyingLists) {
			verifyingList.addAll(curList);
		}
		
		if (testLengths) {
			assertEquals("The lists are of different lengths.", verifyingList.size(), listInQuestion.size());
		}
		
		for (int i = 0; i < listInQuestion.size(); i++) {
			assertEquals(
					"The values inserted were wrong.",
					verifyingList.get(i),
					listInQuestion.get(i)
			);
		}
	}
	
	public static void testListsAreTheSame(Object[] listInQuestion, boolean testLengths, Object[]... verifyingLists) {
		List<Object> verifyingList = new ArrayList<>();
		for (Object[] curList : verifyingLists) {
			verifyingList.addAll(Arrays.asList(curList));
		}
		
		testListsAreTheSame(Arrays.asList(listInQuestion), testLengths, verifyingList);
	}
}
