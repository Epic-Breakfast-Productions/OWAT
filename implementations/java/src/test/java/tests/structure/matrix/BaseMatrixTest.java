package tests.structure.matrix;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.MatrixIterator;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

import static org.junit.Assert.*;
import static testUtils.TestUtils.assert2dArrayEquals;

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
	
	//TODO:: test clearNode()
	//TODO:: test trim()
	//TODO:: test isFull()
	//TODO:: test setValue(long,long,T)
	
	@Test
	public void testIterator() throws Exception {
		Matrix m = this.getTestingInstance();
		
		assertFalse(m.iterator().hasNext());
		
		m.grow(1);
		
		Iterator it = m.iterator();
		assertTrue(it.hasNext());
		assertEquals(m.getDefaultValue(), it.next());
		assertFalse(it.hasNext());
		
		m.grow(1);
		
		it = m.iterator();
		
		while(it.hasNext()){
			assertEquals(m.getDefaultValue(), it.next());
		}
		
		m.setValue(0,0,1);
		m.setValue(1,0,2);
		m.setValue(0,1,3);
		m.setValue(1,1,4);
		
		it = m.iterator();
		
		assertEquals(1,it.next());
		assertEquals(2,it.next());
		assertEquals(3,it.next());
		assertEquals(4,it.next());
		assertFalse(it.hasNext());
		
	}
	
	@Test
	public void testTo2dArray() throws Exception {
		Matrix m = this.getTestingInstance();
		
		m.addRow();
		
		Object[][] result = m.to2dArray();
		
		assert2dArrayEquals(
			new Object[][]{{m.getDefaultValue()}},
			result
		);
		
		m.setValue(0,0,Integer.MAX_VALUE);
		
		result = m.to2dArray();
		
		assert2dArrayEquals(
			new Object[][]{{Integer.MAX_VALUE}},
			result
		);
		
		m.grow(1);
		result = m.to2dArray();
		
		assert2dArrayEquals(
			new Object[][]{{Integer.MAX_VALUE,m.getDefaultValue()},{m.getDefaultValue(),m.getDefaultValue()}},
			result
		);
		
		m.setValue(1,0,new Integer(1));
		m.setValue(0,1,new Integer(2));
		m.setValue(1,1,new Integer(3));
		result = m.to2dArray();
		
		assert2dArrayEquals(
			new Object[][]{{Integer.MAX_VALUE,new Integer(1)},{new Integer(2),new Integer(3)}},
			result
		);
	}
}
