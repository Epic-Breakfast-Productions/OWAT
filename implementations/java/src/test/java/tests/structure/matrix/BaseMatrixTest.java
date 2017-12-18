package tests.structure.matrix;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * A test of methods from the abstract Matrix class that are already implemented in that class.
 */
public class BaseMatrixTest extends MatrixTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseMatrixTest.class);
	
	public BaseMatrixTest(Class<? extends Matrix> curMatrixClass) {
		super(curMatrixClass);
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
	
	@Test
	public void testDefaultVal() throws Exception {
		Matrix testingMatrix = this.getTestingInstance();
		
		Object obj = testingMatrix.getDefaultValue();
		
		assertNull(obj);
		
		Object testObj = "this is some value";
		obj = testObj;
		
		testingMatrix.setDefaultValue(obj);
		
		assertEquals(testObj, testingMatrix.getDefaultValue());
	}
	
	//TODO:: test trim()
	//TODO:: test isFull()
	//TODO:: test iterator
}
