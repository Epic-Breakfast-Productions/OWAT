package com.ebp.owat.lib.structure.matrix;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.Coordinate;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ebp.owat.lib.testUtils.TestUtils;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class OverriddenMatrixTest<T extends Matrix<Integer>> extends MatrixTest<T> {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseMatrixTest.class);
	
	public OverriddenMatrixTest(Class<T> curMatrixClass) {
		super(curMatrixClass);
	}
	
	/*
		Tests start here
	 */
	
	@Test
	public void addRowColTest() throws Exception {
		T m = this.getTestingInstance();
		
		m.addCol();
		assertEquals(1, m.getNumCols());
		assertEquals(1, m.getNumRows());
		
		m.addCol();
		assertEquals(2, m.getNumCols());
		assertEquals(1, m.getNumRows());
		
		m = this.getTestingInstance();
		
		m.addRow();
		assertEquals(1, m.getNumRows());
		assertEquals(1, m.getNumCols());
		
		m.addRow();
		assertEquals(2, m.getNumRows());
		assertEquals(1, m.getNumCols());
		
		m.addCol();
		assertEquals(2, m.getNumCols());
		assertEquals(2, m.getNumRows());
		
		assertEquals(0, m.numElements());
	}
	
	@Test
	public void addRowsColsWithNumTest() throws Exception {
		T m = this.getTestingInstance();
		
		m.addCols(1);
		assertEquals(1, m.getNumCols());
		assertEquals(1, m.getNumRows());
		
		m.addCols(2);
		assertEquals(3, m.getNumCols());
		assertEquals(1, m.getNumRows());
		
		m = this.getTestingInstance();
		
		m.addRows(1);
		assertEquals(1, m.getNumCols());
		assertEquals(1, m.getNumRows());
		
		m.addRows(2);
		assertEquals(1, m.getNumCols());
		assertEquals(3, m.getNumRows());
		
		m.addCols(2);
		assertEquals(3, m.getNumCols());
		assertEquals(3, m.getNumRows());
		
		m.addCols(0);
		assertEquals(3, m.getNumCols());
		assertEquals(3, m.getNumRows());
		
		try{
			m.addCols(-1);
			Assert.fail();
		}catch (IllegalArgumentException e){
			//nothing to do
		}
		try{
			m.addRows(-1);
			Assert.fail();
		}catch (IllegalArgumentException e){
			//nothing to do
		}
	}
	
	@Test
	public void addColsWithCollectionTest() throws Exception {
		T m = this.getTestingInstance();
		Object def = m.getDefaultValue();
		
		assertTrue(m.addCols(Arrays.asList(1)));
		assertEquals(1, m.numElements());
		assertEquals(1, m.getNumRows());
		assertEquals(1, m.getNumCols());
		assertEquals(1, (int)m.get(0L,0L));
		
		m = this.getTestingInstance();
		
		assertTrue(m.addCols(Arrays.asList(1,2,3,4)));
		
		assertEquals(4, m.numElements());
		assertEquals(1, m.getNumRows());
		assertEquals(4, m.getNumCols());
		
		TestUtils.assertMatrix(
			new Object[][]{
				{1,2,3,4}
			},
			m
		);
		
		m.addRow();
		
		assertTrue(m.addCols(Arrays.asList(5,6,7,8)));
		assertEquals(8, m.numElements());
		TestUtils.assertMatrix(
			new Object[][]{
				{1,2,3,4, 5, 7},
				{def,def,def,def, 6, 8}
			},
			m
		);
		
		assertFalse(m.addCols(Arrays.asList(9,10,11)));
		
		assertEquals(11, m.numElements());
		TestUtils.assertMatrix(
			new Object[][]{
				{1,2,3,4,5,7,9,11},
				{def,def,def,def, 6, 8,10,def}
			},
			m
		);
	}
	
	@Test
	public void addRowsWithCollectionTest() throws Exception {
		T m = this.getTestingInstance();
		Object def = m.getDefaultValue();
		
		assertTrue(m.addRows(Arrays.asList(1)));
		
		assertEquals(1, m.numElements());
		assertEquals(1, m.getNumRows());
		assertEquals(1, m.getNumCols());
		assertEquals(1, (int)m.get(0,0));
		
		m = this.getTestingInstance();
		
		assertTrue(m.addRows(Arrays.asList(1,2,3,4)));
		
		assertEquals(4, m.numElements());
		assertEquals(4, m.getNumRows());
		assertEquals(1, m.getNumCols());
		
		TestUtils.assertMatrix(
			new Object[][]{
				{1},
				{2},
				{3},
				{4}
			},
			m
		);
		
		m.addCol();
		
		assertTrue(m.addRows(Arrays.asList(5,6,7,8)));
		
		assertEquals(8, m.numElements());
		TestUtils.assertMatrix(
			new Object[][]{
				{1, def},
				{2, def},
				{3, def},
				{4, def},
				{5, 6},
				{7, 8}
			},
			m
		);
		
		assertFalse(m.addRows(Arrays.asList(9,10,11)));
		
		assertEquals(11, m.numElements());
		TestUtils.assertMatrix(
			new Object[][]{
				{1, def},
				{2, def},
				{3, def},
				{4, def},
				{5, 6},
				{7, 8},
				{9, 10},
				{11, def}
			},
			m
		);
	}
	
	@Test
	public void removeColTest() throws Exception {
		T m = this.getTestingInstance();
		Object d = m.getDefaultValue();
		
		m.grow(1);
		m.setValue(0,0,1);
		
		assertEquals(Arrays.asList(1), m.removeCol());
		
		assertEquals(0, m.getNumCols());
		assertEquals(0, m.getNumRows());
		assertEquals(0, m.numElements());
		
		m = this.getTestingInstance();
		m.grow(5);
		
		assertEquals(Arrays.asList(d,d,d,d,d), m.removeCol());
		
		assertEquals(4, m.getNumCols());
		assertEquals(5, m.getNumRows());
		
		m.setValue(3,0,1);
		m.setValue(3,1,2);
		m.setValue(3,2,3);
		m.setValue(3,3,4);
		m.setValue(3,4,5);
		
		assertEquals(Arrays.asList(1,2,3,4,5), m.removeCol());
		
		assertEquals(3, m.getNumCols());
		assertEquals(5, m.getNumRows());
		assertEquals(0, m.numElements());
		TestUtils.assertMatrix(
			new Object[][]{
				{d,d,d},
				{d,d,d},
				{d,d,d},
				{d,d,d},
				{d,d,d}
			},
			m
		);
		
		m.setValue(1, 0, 1);
		m.setValue(1, 1, 2);
		m.setValue(1, 2, 3);
		m.setValue(1, 3, 4);
		m.setValue(1, 4, 5);
		
		m.setValue(2, 0, 6);
		m.setValue(2, 1, 7);
		m.setValue(2, 2, 8);
		m.setValue(2, 3, 9);
		m.setValue(2, 4, 10);
		
		assertEquals(Arrays.asList(1,2,3,4,5), m.removeCol(1));
		
		assertEquals(2, m.getNumCols());
		assertEquals(5, m.getNumRows());
		assertEquals(5, m.numElements());
		TestUtils.assertMatrix(
			new Object[][]{
				{d,6},
				{d,7},
				{d,8},
				{d,9},
				{d,10}
			},
			m
		);
	}
	
	@Test
	public void removeRowTest() throws Exception {
		T m = this.getTestingInstance();
		Object d = m.getDefaultValue();
		
		m.grow(1);
		m.setValue(0,0,1);
		
		assertEquals(Arrays.asList(1), m.removeRow());
		
		assertEquals(0, m.getNumCols());
		assertEquals(0, m.getNumRows());
		assertEquals(0, m.numElements());
		
		m = this.getTestingInstance();
		m.grow(5);
		
		assertEquals(Arrays.asList(d,d,d,d,d), m.removeRow());
		
		assertEquals(4, m.getNumRows());
		assertEquals(5, m.getNumCols());
		
		m.setValue(0,3,1);
		m.setValue(1,3,2);
		m.setValue(2,3,3);
		m.setValue(3,3,4);
		m.setValue(4,3,5);
		
		assertEquals(Arrays.asList(1,2,3,4,5), m.removeRow());
		
		assertEquals(3, m.getNumRows());
		assertEquals(5, m.getNumCols());
		assertEquals(0, m.numElements());
		TestUtils.assertMatrix(
			new Object[][]{
				{d,d,d,d,d},
				{d,d,d,d,d},
				{d,d,d,d,d}
			},
			m
		);
		
		m.setValue(0, 1, 1);
		m.setValue(1, 1, 2);
		m.setValue(2, 1, 3);
		m.setValue(3, 1, 4);
		m.setValue(4, 1, 5);
		
		m.setValue(0, 2, 6);
		m.setValue(1, 2, 7);
		m.setValue(2, 2, 8);
		m.setValue(3, 2, 9);
		m.setValue(4, 2, 10);
		
		assertEquals(Arrays.asList(1,2,3,4,5), m.removeRow(1));
		
		assertEquals(2, m.getNumRows());
		assertEquals(5, m.getNumCols());
		assertEquals(5, m.numElements());
		TestUtils.assertMatrix(
			new Object[][]{
				{d,d,d,d,d},
				{6,7,8,9,10}
			},
			m
		);
	}
	
	@Test
	public void setValueTest() throws Exception{
		T m = this.getTestingInstance();
		Integer d = m.getDefaultValue();
		
		m.grow(1);
		
		Object result = m.setValue(0,0,1);
		
		assertEquals(1, m.numElements());
		assertEquals(m.getDefaultValue(), result);
		
		m.grow(3);
		
		m.setValue(3,3, 2);
		m.setValue(1,2, 3);
		assertEquals(3, m.numElements());
		
		TestUtils.assertMatrix(
			new Object[][]{
				{1,d,d,d},
				{d,d,d,d},
				{d,3,d,d},
				{d,d,d,2},
			},
			m
		);
		
		m.setValue(0,1,d);
		assertEquals(4, m.numElements());
	}
	
	@Test
	public void testGet() throws Exception{
		T m = this.getTestingInstance();
		Object d = m.getDefaultValue();
		
		m.grow(1);
		
		assertEquals(d, m.get(0,0));
		
		m.setValue(0,0,1);
		assertEquals(1, (int)m.get(0,0));
	}
	
	@Test
	public void testGetRow() throws Exception{
		T m = this.getTestingInstance();
		Object d = m.getDefaultValue();
		
		m.grow(1);
		
		assertEquals(Arrays.asList(d), m.getRow(0));
		
		m.addCols(Arrays.asList(1,2,3,4));
		
		assertEquals(Arrays.asList(d,1,2,3,4), m.getRow(0));
		
		m.addRow();
		
		assertEquals(Arrays.asList(d,d,d,d,d), m.getRow(1));
	}
	
	@Test
	public void testGetCol() throws Exception{
		T m = this.getTestingInstance();
		Object d = m.getDefaultValue();
		
		m.grow(1);
		
		assertEquals(Arrays.asList(d), m.getCol(0));
		
		m.addRows(Arrays.asList(1,2,3,4));
		
		assertEquals(Arrays.asList(d,1,2,3,4), m.getCol(0));
		
		m.addCol();
		
		assertEquals(Arrays.asList(d,d,d,d,d), m.getCol(1));
	}
	
	@Test
	public void testReplaceRow() throws Exception {
		T m = this.getTestingInstance();
		Integer d = m.getDefaultValue();
		
		m.grow(5);
		
		List<Integer> result = m.replaceRow(0, Arrays.asList(1,2,3,4,5));
		
		assertEquals(Arrays.asList(d,d,d,d,d), result);
		assertEquals(Arrays.asList(1,2,3,4,5), m.getRow(0));
		assertEquals(5, m.numElements());
		
		result = m.replaceRow(3, Arrays.asList(5,4,3,2,1));
		
		assertEquals(Arrays.asList(d,d,d,d,d), result);
		assertEquals(Arrays.asList(5,4,3,2,1), m.getRow(3));
		assertEquals(10, m.numElements());
		
		result = m.replaceRow(3, Arrays.asList(1,2,3,4,5));
		
		assertEquals(Arrays.asList(5,4,3,2,1), result);
		assertEquals(Arrays.asList(1,2,3,4,5), m.getRow(3));
		assertEquals(10, m.numElements());
		
		result = m.replaceRow(0, Arrays.asList(d,d,d,d,d));
		
		assertEquals(Arrays.asList(1,2,3,4,5), result);
		assertEquals(Arrays.asList(d,d,d,d,d), m.getRow(0));
		assertEquals(5, m.numElements());
	}
	
	@Test
	public void testReplaceCol() throws Exception {
		T m = this.getTestingInstance();
		Integer d = m.getDefaultValue();
		
		m.grow(5);
		
		List<Integer> result = m.replaceCol(0, Arrays.asList(1,2,3,4,5));
		
		assertEquals(Arrays.asList(d,d,d,d,d), result);
		assertEquals(Arrays.asList(1,2,3,4,5), m.getCol(0));
		assertEquals(5, m.numElements());
		
		result = m.replaceCol(3, Arrays.asList(5,4,3,2,1));
		
		assertEquals(Arrays.asList(d,d,d,d,d), result);
		assertEquals(Arrays.asList(5,4,3,2,1), m.getCol(3));
		assertEquals(10, m.numElements());
		
		result = m.replaceCol(3, Arrays.asList(1,2,3,4,5));
		
		assertEquals(Arrays.asList(5,4,3,2,1), result);
		assertEquals(Arrays.asList(1,2,3,4,5), m.getCol(3));
		assertEquals(10, m.numElements());
		
		result = m.replaceCol(0, Arrays.asList(d,d,d,d,d));
		
		assertEquals(Arrays.asList(1,2,3,4,5), result);
		assertEquals(Arrays.asList(d,d,d,d,d), m.getCol(0));
		assertEquals(5, m.numElements());
	}
	
	@Test
	public void testGetSubMatrix() throws Exception {
		T m = this.getTestingInstance();
		
		m.grow(1);
		
		m.setValue(0,0,1);
		
		T result = (T)m.getSubMatrix(new Coordinate(m, 0,0), 1);
		
		assertEquals(m.numElements(), result.numElements());
		TestUtils.assertMatrix(
			new Object[][]{
				{1}
			},
			result
		);
		
		m.grow(1);
		m.setValue(1,0,2);
		m.setValue(0,1,3);
		m.setValue(1,1,4);
		
		result = (T)m.getSubMatrix(new Coordinate(m, 0,0), 2);
		
		assertEquals(m.numElements(), result.numElements());
		TestUtils.assertMatrix(
			new Object[][]{
				{1,2},
				{3,4}
			},
			result
		);
		
		TestUtils.assertMatrix(
			new Object[][]{
				{1}
			},
			m.getSubMatrix(new Coordinate(m, 0,0), 1)
		);
		
		m = this.getTestingInstance();
		
		Integer n = m.getDefaultValue();
		
		m.grow(5L);
		
		m.replaceRow(0, Arrays.asList( 0, n, 2, n, 4));
		m.replaceRow(1, Arrays.asList( n, 6, n, 8, n));
		m.replaceRow(2, Arrays.asList(10, n,12,13,14));
		m.replaceRow(3, Arrays.asList(15,16, n,18,19));
		m.replaceRow(4, Arrays.asList(20, n,22, n,24));
		
		result = (T)m.getSubMatrix(new Coordinate(m, 1,1), 3);
		
		assertEquals(6, result.numElements());
		TestUtils.assertMatrix(
			new Object[][]{
				{6, n, 8},
				{n, 12, 13},
				{16, n, 18}
			},
			result
		);
	}
	
	
	@Test
	public void testReplaceSubMatrix() throws Exception {
	
	}
}
