package tests;

import com.ebp.owat.lib.OwatException;
import com.ebp.owat.lib.datastructure.OwatStructureException;
import com.ebp.owat.lib.datastructure.io.OwatNodeIOException;
import com.ebp.owat.lib.datastructure.matrix.OwatMatrixException;
import com.ebp.owat.lib.datastructure.matrix.utils.OwatMatrixUtilException;
import com.ebp.owat.lib.datastructure.value.OwatValueException;
import com.ebp.owat.lib.datastructure.set.OwatSetException;
import com.ebp.owat.lib.utils.OwatUtilException;
import com.ebp.owat.lib.utils.rand.OwatRandException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ExceptionTests {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionTests.class);
	
	/** The different exceptions to test. */
	@Parameterized.Parameters
	public static Collection exceptionsToTest() {
		return Arrays.asList(new Object[][] {
				{ OwatException.class },
				{ OwatStructureException.class },
				{ OwatNodeIOException.class },
				{ OwatMatrixException.class },
				{ OwatValueException.class },
				{ OwatSetException.class },
				{ OwatUtilException.class },
				{ OwatRandException.class },
				{ OwatMatrixUtilException.class }
		});
	}
	
	private static final Throwable TEST_THROWABLE = new Throwable("Test throwable message");
	private static final String TEST_MESSAGE = "TEST MESSAGE FOR EXCEPTION TESTING";
	
	private final Class<? extends OwatException> curExceptionClass;
	
	public ExceptionTests(Class<? extends OwatException> curExceptionClass){
		this.curExceptionClass = curExceptionClass;
	}
	
	@Test
	public void testExceptions() throws Throwable{
		LOGGER.debug("Testing: {}", curExceptionClass.getName());
		Constructor<? extends OwatException> constBase = curExceptionClass.getConstructor();
		Constructor<? extends OwatException> constStr = curExceptionClass.getConstructor(String.class);
		Constructor<? extends OwatException> constThro = curExceptionClass.getConstructor(Throwable.class);
		Constructor<? extends OwatException> constStrThro = curExceptionClass.getConstructor(String.class, Throwable.class);
		
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
