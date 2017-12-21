package tests.structure.matrix;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

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
		Matrix testingMatrix = this.getTestingInstance();
		
		testingMatrix.addCol();
		assertEquals(1, testingMatrix.getNumCols());
		assertEquals(1, testingMatrix.getNumRows());
		
		testingMatrix.addCol();
		assertEquals(2, testingMatrix.getNumCols());
		assertEquals(1, testingMatrix.getNumRows());
		
		testingMatrix = this.getTestingInstance();
		
		testingMatrix.addRow();
		assertEquals(1, testingMatrix.getNumRows());
		assertEquals(1, testingMatrix.getNumCols());
		
		testingMatrix.addRow();
		assertEquals(2, testingMatrix.getNumRows());
		assertEquals(1, testingMatrix.getNumCols());
		
		testingMatrix.addCol();
		assertEquals(2, testingMatrix.getNumCols());
		assertEquals(2, testingMatrix.getNumRows());
	}
	
	@Test
	public void addRowsColsWithNumTest() throws Exception {
		Matrix testingMatrix = this.getTestingInstance();
		
		testingMatrix.addCols(1);
		assertEquals(1, testingMatrix.getNumCols());
		assertEquals(1, testingMatrix.getNumRows());
		
		testingMatrix.addCols(2);
		assertEquals(3, testingMatrix.getNumCols());
		assertEquals(1, testingMatrix.getNumRows());
		
		testingMatrix = this.getTestingInstance();
		
		testingMatrix.addRows(1);
		assertEquals(1, testingMatrix.getNumCols());
		assertEquals(1, testingMatrix.getNumRows());
		
		testingMatrix.addRows(2);
		assertEquals(1, testingMatrix.getNumCols());
		assertEquals(3, testingMatrix.getNumRows());
		
		testingMatrix.addCols(2);
		assertEquals(3, testingMatrix.getNumCols());
		assertEquals(3, testingMatrix.getNumRows());
		
		testingMatrix.addCols(0);
		assertEquals(3, testingMatrix.getNumCols());
		assertEquals(3, testingMatrix.getNumRows());
		
		try{
			testingMatrix.addCols(-1);
			Assert.fail();
		}catch (IllegalArgumentException e){
			//nothing to do
		}
		try{
			testingMatrix.addRows(-1);
			Assert.fail();
		}catch (IllegalArgumentException e){
			//nothing to do
		}
	}
	
	@Test
	public void addRowsColsWithCollectionTest() throws Exception {
		//TODO: test addRowsColsWithCollection
	}
	
	@Test
	public void removeRowColTest() throws Exception {
		Matrix testingMatrix = this.getTestingInstance();
		
		//TODO
	}
	//TODO: test removeRow/Col
	//TODO: test setValue
	//TODO: test replaceRow/Col
	//TODO: test get
	//TODO: test getCol/Row
}
