package tests;

import com.ebp.owat.lib.OwatException;
import com.ebp.owat.lib.datastructure.OwatStructureException;
import com.ebp.owat.lib.datastructure.io.OwatNodeIOException;
import com.ebp.owat.lib.datastructure.matrix.OwatMatrixException;
import com.ebp.owat.lib.datastructure.matrix.utils.OwatMatrixUtilException;
import com.ebp.owat.lib.datastructure.node.OwatNodeException;
import com.ebp.owat.lib.datastructure.node.value.OwatNodeValueException;
import com.ebp.owat.lib.datastructure.set.OwatNodeSetException;
import com.ebp.owat.lib.utils.OwatUtilException;
import com.ebp.owat.lib.utils.rand.OwatRandException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ExceptionTests {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionTests.class);
	
	private List<Class<? extends OwatException>> exceptionsToTest = Arrays.asList(
			OwatException.class,
			OwatStructureException.class,
			OwatNodeIOException.class,
			OwatMatrixException.class,
			OwatNodeException.class,
			OwatNodeValueException.class,
			OwatNodeSetException.class,
			OwatUtilException.class,
			OwatRandException.class,
			OwatMatrixUtilException.class
	);
	
	private static final Throwable TEST_THROWABLE = new Throwable("Test throwable message");
	private static final String TEST_MESSAGE = "TEST MESSAGE FOR EXCEPTION TESTING";
	
	@Test
	public void testExceptions() throws Throwable{
		LOGGER.info("Testing all the custom exceptions (mainly for coverage...).");
		
		for (Class<? extends OwatException> curClass : exceptionsToTest ) {
			LOGGER.debug("Testing: {}", curClass.getName());
			Constructor<? extends OwatException> constBase = curClass.getConstructor();
			Constructor<? extends OwatException> constStr = curClass.getConstructor(String.class);
			Constructor<? extends OwatException> constThro = curClass.getConstructor(Throwable.class);
			Constructor<? extends OwatException> constStrThro = curClass.getConstructor(String.class, Throwable.class);
			
			OwatException curException = constBase.newInstance();
			
			curException = constStr.newInstance(TEST_MESSAGE);
			assertEquals(TEST_MESSAGE, curException.getMessage());
			
			curException = constThro.newInstance(TEST_THROWABLE);
			assertEquals(TEST_THROWABLE, curException.getCause());
			
			curException = constStrThro.newInstance(TEST_MESSAGE, TEST_THROWABLE);
			assertEquals(TEST_MESSAGE, curException.getMessage());
			assertEquals(TEST_THROWABLE, curException.getCause());
		}
		
	}

}
