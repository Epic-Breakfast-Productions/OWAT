package testModels.structure.set;

import com.ebp.owat.lib.dataStructure.set.BigLinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

/**
 * Class to build the necessary objects to make the BigLinkedList tests work.
 *
 * Created by Greg Stewart on 4/1/17.
 */
public class BigLinkedListTestModels {
	static private Logger LOGGER = LoggerFactory.getLogger(BigLinkedListTestModels.class);
	/** The nodes for testing. */
	private static BigLinkedList<Boolean> testingNodeList = null;
	private static BigLinkedList<Boolean> bigTestingNodeList = null;
	
	public static final BigInteger testSize = BigInteger.valueOf(50L);
	public static final BigInteger bigTestSize = BigInteger.valueOf(2147483648L);
	
	private static Boolean testVal = new Boolean(true);
	
	public static void buildBigTestingNodeList(){
		LOGGER.info("Building the BIG node list ({} nodes).", bigTestSize);
		bigTestingNodeList = new BigLinkedList<>();
		BigInteger modAmount = BigInteger.valueOf(10000000L);
		for(BigInteger i = BigInteger.ZERO; i.compareTo(bigTestSize) < 0; i = i.add(BigInteger.ONE)){
			//LOGGER.debug("Iterating... {}", i);
			bigTestingNodeList.add(testVal);
			if(i.mod(modAmount).compareTo(BigInteger.ZERO) == 0){
				LOGGER.info("At number {}/.", i, bigTestSize);
			}
		}
		LOGGER.info("Done.");
	}
	
	public static void buildTestingNodeList(){
		LOGGER.info("Building the smaller node list ({} nodes).", testSize);
		testingNodeList = new BigLinkedList<>();
		
		for(BigInteger i = BigInteger.ZERO; i.compareTo(testSize) < 0; i = i.add(BigInteger.ONE)){
			testingNodeList.add(testVal);
		}
		LOGGER.info("Done.");
	}
	
	public static BigLinkedList<Boolean> getTestingNodeList(boolean bigList){
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
