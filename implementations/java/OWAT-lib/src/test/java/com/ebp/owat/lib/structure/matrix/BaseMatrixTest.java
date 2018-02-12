package com.ebp.owat.lib.structure.matrix;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ebp.owat.lib.testUtils.TestUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static com.ebp.owat.lib.testUtils.TestUtils.assert2dArrayEquals;

/**
 * A test of methods from the abstract Matrix class that are already implemented in that class.
 */
public class BaseMatrixTest<T extends Matrix<Integer>> extends MatrixTest<T> {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseMatrixTest.class);
	
	public BaseMatrixTest(Class<T> curMatrixClass) {
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
		T m = this.getTestingInstance();
		assertTrue("Newly instantiated matrix is not empty for some reason.", m.isEmpty());
		
		m.grow(1);
		assertTrue(m.isEmpty());
		
		m.setValue(0,0,1);
		assertFalse(m.isEmpty());
	}
	
	@Test
	public void testSize() throws Exception {
		T m = this.getTestingInstance();
		
		assertEquals(0,m.size());
		m.grow(1);
		assertEquals(1,m.size());
		
		m.grow(1);
		assertEquals(4,m.size());
		
		m.grow(1);
		assertEquals(9,m.size());
		
		m.removeCol(0);
		
		assertEquals(6,m.size());
		
		m.removeRow(0);
		
		assertEquals(4,m.size());
	}
	
	@Test
	public void testIsFull() throws Exception {
		T m = this.getTestingInstance();
		
		assertFalse(m.isFull());
		
		m.grow(1);
		
		assertFalse(m.isFull());
		
		m.setValue(0,0,1);
		
		assertTrue(m.isFull());
		
		m.grow(1);
		assertFalse(m.isFull());
		
		m.setValue(1,0,1);
		m.setValue(0,1,1);
		m.setValue(1,1,1);
		
		assertTrue(m.isFull());
	}
	
	@Test
	public void testNumColsRows() throws Exception {
		T testingMatrix = this.getTestingInstance();
		
		assertEquals(0,testingMatrix.getNumCols());
		assertEquals(0,testingMatrix.getNumRows());
	}
	
	@Test
	public void testHasRowsCols() throws Exception {
		T testingMatrix = this.getTestingInstance();
		
		assertFalse(testingMatrix.hasRowsCols());
		testingMatrix.addRow();
		assertTrue(testingMatrix.hasRowsCols());
	}
	
	@Test
	public void testNumElementsHeld() throws Exception {
		T m = this.getTestingInstance();
		
		assertEquals(0, m.numElements());
		
		m.grow(1);
		
		assertEquals(0, m.numElements());
		
		m.setValue(0,0,1);
		assertEquals(1, m.numElements());
		
		m.clearNode(0,0);
		assertEquals(0, m.numElements());
		
		m.grow(1);
		assertEquals(0, m.numElements());
		m.setValue(0,0,1);
		assertEquals(1, m.numElements());
		m.setValue(1,0,1);
		assertEquals(2, m.numElements());
		m.setValue(0,1,1);
		assertEquals(3, m.numElements());
		m.setValue(1,1,1);
		assertEquals(4, m.numElements());
	}
	
	@Test
	public void testDefaultVal() throws Exception {
		T m = this.getTestingInstance();
		
		Integer obj = m.getDefaultValue();
		
		assertNull(obj);
		
		Integer testObj = 1;
		obj = testObj;
		
		m.setDefaultValue(obj);
		
		assertEquals(testObj, m.getDefaultValue());
		
		m.grow(1);
		
		assertEquals(testObj, m.get(0,0));
		
		m = this.getTestingInstance();
		
		assertTrue(m.isDefaultValue(null));
		assertFalse(m.isDefaultValue(0));
		assertFalse(m.isDefaultValue(1));
		
		m.setDefaultValue(0);
		
		assertFalse(m.isDefaultValue(null));
		assertTrue(m.isDefaultValue(0));
		assertFalse(m.isDefaultValue(1));
	}
	
	@Test
	public void testGrowWithNum() throws Exception{
		T m = this.getTestingInstance();
		
		m.grow(1);
		assertEquals(1, m.getNumCols());
		assertEquals(1, m.getNumRows());
		
		m.grow(1);
		assertEquals(2, m.getNumCols());
		assertEquals(2, m.getNumRows());
		
		m.grow(2);
		assertEquals(4, m.getNumCols());
		assertEquals(4, m.getNumRows());
		
		m.grow(0);
		assertEquals(4, m.getNumCols());
		assertEquals(4, m.getNumRows());
		
		try{
			m.grow(-1);
			Assert.fail();
		}catch (IllegalArgumentException e){
			//nothing to do
		}
	}
	
	@Test
	public void testGrowWithCollection() throws Exception {
		T m = this.getTestingInstance();
		
		assertTrue(m.grow(Arrays.asList(1)));
		TestUtils.assertMatrix(
			new Object[][]{
				{1}
			},
			m
		);
		
		m = this.getTestingInstance();
		assertFalse(m.grow(Arrays.asList(1,2)));
		TestUtils.assertMatrix(
			new Object[][]{
				{1,2},
				{m.getDefaultValue(), m.getDefaultValue()}
			},
			m
		);
		
		m = this.getTestingInstance();
		assertTrue(m.grow(Arrays.asList(1,2,3,4)));
		TestUtils.assertMatrix(
			new Object[][]{
				{1,2},
				{3,4}
			},
			m
		);
		
		m = this.getTestingInstance();
		assertFalse(m.grow(Arrays.asList(1,2,3,4,5,6,7,8)));
		TestUtils.assertMatrix(
			new Object[][]{
				{1,2,3},
				{4,5,6},
				{7,8,m.getDefaultValue()}
			},
			m
		);
		
		m = this.getTestingInstance();
		assertTrue(m.grow(Arrays.asList(1,2,3,4,5,6,7,8,9)));
		TestUtils.assertMatrix(
			new Object[][]{
				{1,2,3},
				{4,5,6},
				{7,8,9}
			},
			m
		);
		
		m = this.getTestingInstance();
		assertTrue(m.grow(Arrays.asList(1)));
		try{
			assertTrue(m.grow(Arrays.asList(1)));
			Assert.fail();
		}catch (IllegalStateException e){
		
		}
	}
	
	@Test
	public void testGrowWithCollectionAlternating() throws Exception {
		T m = this.getTestingInstance();
		
		assertTrue(m.growAlternating(Arrays.asList(1)));
		TestUtils.assertMatrix(
			new Object[][]{
				{1}
			},
			m
		);
		
		m = this.getTestingInstance();
		
		assertTrue(m.growAlternating(Arrays.asList(1,2,3,4)));
		
		TestUtils.assertMatrix(
			new Object[][]{
				{1, 2},
				{3, 4}
			},
			m
		);
		
		m = this.getTestingInstance();
		
		assertFalse(m.growAlternating(Arrays.asList(1,2,3,4,5)));
		
		TestUtils.assertMatrix(
			new Object[][]{
				{1, 2, 5},
				{3, 4, m.getDefaultValue()}
			},
			m
		);
		
		assertTrue(m.growAlternating(Arrays.asList(6,7,8,9,10,11)));
		
		TestUtils.assertMatrix(
			new Object[][]{
				{1, 2, 5, 9},
				{3, 4, m.getDefaultValue(), 10},
				{6, 7, 8, 11}
			},
			m
		);
		
	}
	
	@Test
	public void testClearNode() throws Exception {
		T m = this.getTestingInstance();
		
		m.grow(1);
		
		m.setValue(0,0,1);
		
		m.clearNode(0,0);
		
		assertEquals(m.getDefaultValue(), m.get(0,0));
	}
	
	@Test
	public void testTrimTo() throws Exception {
		T m = this.getTestingInstance();
		Integer N = m.getDefaultValue();
		
		m.grow(5);
		
		m.trimTo(3,3);
		
		assertEquals(3, m.getNumCols());
		assertEquals(3, m.getNumRows());
		
		m.grow(2);
		
		m.replaceRow(0, Arrays.asList( 0, N, 2, N, 4));
		m.replaceRow(1, Arrays.asList( N, 6, N, 8, N));
		m.replaceRow(2, Arrays.asList(10, N,12,13,14));
		m.replaceRow(3, Arrays.asList(15,16, N,18,19));
		m.replaceRow(4, Arrays.asList(20, N,22, N,24));
		
		m.trimTo(3,2);
		
		assertEquals(3, m.numElements());
		assertEquals(3, m.getNumCols());
		assertEquals(2, m.getNumRows());
		TestUtils.assertMatrix(
			new Object[][]{
				{ 0, N, 2},
				{ N, 6, N}
			},
			m
		);
	}
	
	@Test
	public void testTrimBy() throws Exception {
		T m = this.getTestingInstance();
		
		m.grow(5);
		
		m.trimBy(3,3);
		
		assertEquals(2, m.getNumCols());
		assertEquals(2, m.getNumRows());
	}
	
	@Test
	public void testIterator() throws Exception {
		T m = this.getTestingInstance();
		
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
		
		try{
			it.next();
			Assert.fail();
		}catch (NoSuchElementException e){
			//nothing to do
		}
	}
	
	@Test
	public void testTo2dArray() throws Exception {
		T m = this.getTestingInstance();
		
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
	
	@Test
	public void testRemoveRowColEmptyMatrix() throws Exception {
		T m = this.getTestingInstance();
		
		assertNull(m.removeCol());
		assertNull(m.removeRow());
	}
	
	@Test
	public void testHasValue() throws Exception {
		T m = this.getTestingInstance();
		m.grow(2);
		m.setValue(0,0,1);
		m.setValue(1,1,1);
		
		assertTrue(m.hasValue(0,0));
		assertTrue(m.hasValue(1,1));
		assertFalse(m.hasValue(0,1));
		assertFalse(m.hasValue(1,0));
	}
}
