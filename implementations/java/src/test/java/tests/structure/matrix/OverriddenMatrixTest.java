package tests.structure.matrix;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testUtils.TestUtils;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OverriddenMatrixTest extends MatrixTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseMatrixTest.class);
	
	public OverriddenMatrixTest(Class<? extends Matrix> curMatrixClass) {
		super(curMatrixClass);
	}
	
	/*
		Tests start here
	 */
	
	@Test
	public void addRowColTest() throws Exception {
		Matrix m = this.getTestingInstance();
		
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
	}
	
	@Test
	public void addRowsColsWithNumTest() throws Exception {
		Matrix m = this.getTestingInstance();
		
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
		Matrix m = this.getTestingInstance();
		Object def = m.getDefaultValue();
		
		assertTrue(m.addCols(Arrays.asList(1)));
		
		assertEquals(1, m.getNumRows());
		assertEquals(1, m.getNumCols());
		assertEquals(1, m.get(0,0));
		
		m = this.getTestingInstance();
		
		assertTrue(m.addCols(Arrays.asList(1,2,3,4)));
		
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
		
		TestUtils.assertMatrix(
			new Object[][]{
				{1,2,3,4, 5, 7},
				{def,def,def,def, 6, 8}
			},
			m
		);
		
		assertFalse(m.addCols(Arrays.asList(9,10,11)));
		
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
		Matrix m = this.getTestingInstance();
		Object def = m.getDefaultValue();
		
		assertTrue(m.addRows(Arrays.asList(1)));
		
		assertEquals(1, m.getNumRows());
		assertEquals(1, m.getNumCols());
		assertEquals(1, m.get(0,0));
		
		m = this.getTestingInstance();
		
		assertTrue(m.addRows(Arrays.asList(1,2,3,4)));
		
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
	public void removeRowColTest() throws Exception {
		Matrix m = this.getTestingInstance();
		
		//TODO
	}
	//TODO: test removeRow/Col
	//TODO: test setValue
	//TODO: test replaceRow/Col
	//TODO: test get
	//TODO: test getCol/Row
}
