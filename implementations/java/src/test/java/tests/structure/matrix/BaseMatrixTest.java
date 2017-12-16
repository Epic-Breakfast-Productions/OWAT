package tests.structure.matrix;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class BaseMatrixTest extends MatrixTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseMatrixTest.class);
	
	public BaseMatrixTest(Class<? extends Matrix> curMatrixClass) {
		super(curMatrixClass);
	}
	
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}
	
	@Override
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Parameterized.Parameters
	public static Collection matrixTypesToTest() {
		return getMatrixClassesToTest();
	}
	
	/*
		Tests start here
	 */
	
	@Test
	public void constructorTest() throws Exception {
		this.getTestingInstance();
	}
	
	@Test
	public void testIsEmpty() throws Exception {
		Matrix testingMatrix = this.getTestingInstance();
		assertTrue("Newly instantiated matrix is not empty for some reason.", testingMatrix.isEmpty());
	}
	
	@Test
	public void testSize() throws Exception {
		Matrix testingMatrix = this.getTestingInstance();
		
		assertEquals(0,testingMatrix.size());
	}
	
	@Test
	public void testNumColsRows() throws Exception {
		Matrix testingMatrix = this.getTestingInstance();
		
		assertEquals(0,testingMatrix.getNumCols());
		assertEquals(0,testingMatrix.getNumRows());
	}
	
	@Test
	public void testHasRowsCols() throws Exception {
		Matrix testingMatrix = this.getTestingInstance();
		
		assertFalse(testingMatrix.hasRowsCols());
	}
	
	@Test
	public void testNumElementsHeld() throws Exception {
		Matrix testingMatrix = this.getTestingInstance();
		
		assertEquals(0,testingMatrix.numElements());
	}
}
