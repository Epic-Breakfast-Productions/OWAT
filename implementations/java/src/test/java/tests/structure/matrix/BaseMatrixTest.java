package tests.structure.matrix;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.MatrixIterator;
import org.junit.Assert;
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
		
		//TODO:: test with elements inside
	}
	
	@Test
	public void testSize() throws Exception {
		Matrix testingMatrix = this.getTestingInstance();
		
		assertEquals(0,testingMatrix.size());
		testingMatrix.grow(1);
		assertEquals(1,testingMatrix.size());
		
		testingMatrix.grow(1);
		assertEquals(4,testingMatrix.size());
		
		testingMatrix.grow(1);
		assertEquals(9,testingMatrix.size());
		
		//TODO:: test when removing
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
		testingMatrix.addRow();
		assertTrue(testingMatrix.hasRowsCols());
	}
	
	@Test
	public void testNumElementsHeld() throws Exception {
		Matrix testingMatrix = this.getTestingInstance();
		
		assertEquals(0, testingMatrix.numElements());
		//TODO:: more thouroughly test
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
		//TODO:: test that newly added rows/cols get the default value
	}
	
	@Test
	public void testGrowWithNum() throws Exception{
		Matrix testingMatrix = this.getTestingInstance();
		
		testingMatrix.grow(1);
		assertEquals(1, testingMatrix.getNumCols());
		assertEquals(1, testingMatrix.getNumRows());
		
		testingMatrix.grow(1);
		assertEquals(2, testingMatrix.getNumCols());
		assertEquals(2, testingMatrix.getNumRows());
		
		testingMatrix.grow(2);
		assertEquals(4, testingMatrix.getNumCols());
		assertEquals(4, testingMatrix.getNumRows());
		
		testingMatrix.grow(0);
		assertEquals(4, testingMatrix.getNumCols());
		assertEquals(4, testingMatrix.getNumRows());
		
		try{
			testingMatrix.grow(-1);
			Assert.fail();
		}catch (IllegalArgumentException e){
			//nothing to do
		}
	}
	
	@Test
	public void testGrowWithCollection() throws Exception {
		//TODO
	}
	
	//TODO:: test trim()
	//TODO:: test isFull()
	
	@Test
	public void testIterator() throws Exception {
		Matrix testingMatrix = this.getTestingInstance();
		
		assertFalse(testingMatrix.iterator().hasNext());
		
		testingMatrix.grow(1);
		
		/* TODO:: after implementing getters
		MatrixIterator it = testingMatrix.iterator();
		assertTrue(it.hasNext());
		assertEquals(testingMatrix.getDefaultValue(), it.next());
		assertFalse(testingMatrix.iterator().hasNext());
		*/
		
		//TODO:: test with actual values and larger matrix
	}
	
	@Test
	public void testTo2dArray() throws Exception {
		//TODO:: test to2dArray()
	}
}
