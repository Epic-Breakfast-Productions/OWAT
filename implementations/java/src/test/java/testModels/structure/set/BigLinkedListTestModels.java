package testModels.structure.set;

import com.ebp.owat.lib.dataStructure.set.LongLinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to build the necessary objects to make the LongLinkedList tests work.
 *
 * Created by Greg Stewart on 4/1/17.
 */
public class BigLinkedListTestModels {
	static private Logger LOGGER = LoggerFactory.getLogger(BigLinkedListTestModels.class);
	/** The nodes for testing. */
	private static LongLinkedList<Boolean> testingNodeList = null;
	private static LongLinkedList<Boolean> bigTestingNodeList = null;
	
	public static final long testSize = 50L;
	public static final long bigTestSize = (long)Integer.MAX_VALUE + 10L;
	
	private static Boolean testVal = new Boolean(true);
	
	public static void buildBigTestingNodeList(){
		LOGGER.info("Building the BIG node list ({} nodes).", bigTestSize);
		bigTestingNodeList = new LongLinkedList<>();
		long modAmount = bigTestSize/10L;
		for(long i = 0L; i < bigTestSize; i++){
			bigTestingNodeList.add(testVal);
			if(i % modAmount == 0){
				LOGGER.info("At number {}/{}.", i, bigTestSize);
			}
		}
		LOGGER.info("Done.");
	}
	
	public static void buildTestingNodeList(){
		LOGGER.info("Building the smaller node list ({} nodes).", testSize);
		testingNodeList = new LongLinkedList<>();
		
		for(long i = 0L; i < testSize; i++){
			testingNodeList.add(testVal);
		}
		LOGGER.info("Done.");
	}
	
	public static LongLinkedList<Boolean> getTestingNodeList(boolean bigList){
		if(bigList){
			if (bigTestingNodeList == null) {
				buildBigTestingNodeList();
			}
			return testingNodeList;
		}else {
			if (testingNodeList == null) {
				buildTestingNodeList();
			}
			return testingNodeList;
		}
	}
}
