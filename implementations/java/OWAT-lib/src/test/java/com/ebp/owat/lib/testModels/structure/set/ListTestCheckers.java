package com.ebp.owat.lib.testModels.structure.set;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Utilities to verify lists for testing
 * Created by Greg Stewart on 7/8/2017.
 */
public interface ListTestCheckers {
	/**
	 * Tests that the different lists are the same.
	 *
	 * @param listInQuestion The list we are testing
	 * @param verifyingLists The list(s) that, which combined, will be used to test the list against.
	 */
	static void testListsAreTheSame(List<? extends Object> listInQuestion, boolean testLengths, List<? extends Object>... verifyingLists) {
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
	
	static void testListsAreTheSame(Object[] listInQuestion, boolean testLengths, Object[]... verifyingLists) {
		List<Object> verifyingList = new ArrayList<>();
		for (Object[] curList : verifyingLists) {
			verifyingList.addAll(Arrays.asList(curList));
		}
		
		testListsAreTheSame(Arrays.asList(listInQuestion), testLengths, verifyingList);
	}
}
