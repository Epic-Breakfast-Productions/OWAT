package com.ebp.owat.lib.testModels.structure.set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Class to build the necessary objects to make the LongLinkedList tests work.
 *
 * Created by Greg Stewart on 4/1/17.
 */
public class LongLinkedListTestModels{
	static private final Logger LOGGER = LoggerFactory.getLogger(LongLinkedListTestModels.class);
	/**
	 * The nodes for testing.
	 */
	public static final long testingCapacity = 5L;
	public static final List<Long> testingArray = Arrays.asList(0L, 1L, 2L, 3L);
	public static final List<Long> testingArrayWithNulls = Arrays.asList(0L, null, 2L, null);
	public static final List<Long> fullTestingArray = Arrays.asList(0L, 1L, 2L, 3L, 4L);
	public static final List<Long> shortTestingArray = Arrays.asList(0L, 1L);
	
}
