package testModels.structure.set;

import com.ebp.owat.lib.dataStructure.set.LongLinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;

/**
 * Class to build the necessary objects to make the LongLinkedList tests work.
 *
 * Created by Greg Stewart on 4/1/17.
 */
public class LongLinkedListTestModels {
	static private final Logger LOGGER = LoggerFactory.getLogger(LongLinkedListTestModels.class);
	/** The nodes for testing. */
	public static final long testingCapacity = 5L;
	public static final Collection<Boolean> testingArray = Arrays.asList(true, false, true, false);
}
