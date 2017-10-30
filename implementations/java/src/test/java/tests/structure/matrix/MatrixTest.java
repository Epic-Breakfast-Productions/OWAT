package tests.structure.matrix;

import com.ebp.owat.lib.datastructure.matrix.Hash.HashedMatrix;
import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.ScramblingMatrix;
import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.Coordinate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Created by Greg Stewart on 3/30/17.
 */
@RunWith(Parameterized.class)
public class MatrixTest {
	private Logger LOGGER = LoggerFactory.getLogger(MatrixTest.class);
	
	@Parameterized.Parameters
	public static Collection matrixTypesToTest() {
		return Arrays.asList(new Object[][] {
				{ HashedMatrix.class }
		});
	}
	
	private static Matrix<Integer> getTestingInstance(Class<? extends Matrix> curClass) throws Exception{
		return curClass.getConstructor(ScramblingMatrix.Type.class).newInstance(ScramblingMatrix.Type.SCRAMBLING);
	}
	
	private final Class<? extends Matrix> curMatrixClass;
	
	public MatrixTest(Class<? extends Matrix> curMatrixClass){
		this.curMatrixClass = curMatrixClass;
	}
	
	@Test
	public void testAddRowCol() throws Exception{
		LOGGER.info("Testing adding rows and columns for {}", curMatrixClass);
		Matrix<Integer> matrix = getTestingInstance(curMatrixClass);
		assertThat(matrix.getNumCols(), is(0L));
		assertThat(matrix.getNumRows(), is(0L));
		matrix.addCol();
		assertThat(matrix.getNumCols(), is(1L));
		assertThat(matrix.getNumRows(), is(1L));
		matrix = getTestingInstance(curMatrixClass);
		matrix.addRow();
		assertThat(matrix.getNumCols(), is(1L));
		assertThat(matrix.getNumRows(), is(1L));
		matrix.addRow();
		assertThat(matrix.getNumCols(), is(1L));
		assertThat(matrix.getNumRows(), is(2L));
		matrix.addCols(2);
		assertThat(matrix.getNumCols(), is(3L));
		assertThat(matrix.getNumRows(), is(2L));
		matrix.addRows(2);
		assertThat(matrix.getNumCols(), is(3L));
		assertThat(matrix.getNumRows(), is(4L));
		matrix = getTestingInstance(curMatrixClass);
		matrix.addCols(2);
		assertThat(matrix.getNumCols(), is(2L));
		assertThat(matrix.getNumRows(), is(1L));
		matrix = getTestingInstance(curMatrixClass);
		matrix.addRows(2);
		assertThat(matrix.getNumCols(), is(1L));
		assertThat(matrix.getNumRows(), is(2L));
		
		try{
			matrix.addRows(-1L);
			Assert.fail();
		}catch (IllegalArgumentException e){
			//no need to do anything
		}
		try{
			matrix.addCols(-1L);
			Assert.fail();
		}catch (IllegalArgumentException e){
			//no need to do anything
		}
		
		//TODO:: finish with add__(Collection)
	}
	
	@Test
	public void testMatrixSettersGetters() throws Exception{
		LOGGER.info("Testing setters and getters for {}", curMatrixClass);
		
		Matrix<Integer> matrix = getTestingInstance(curMatrixClass);
		
		matrix.addRow();
		
		int firstVal = 1;
		assertNull(matrix.setValue(new Coordinate(matrix, 0, 0), firstVal));
		assertEquals((Integer)firstVal, matrix.get(0, 0));
		assertEquals((Integer)firstVal, matrix.get(new Coordinate(matrix, 0, 0)));
		assertEquals((Integer)firstVal, matrix.setValue(new Coordinate(matrix, 0, 0), 2));
		try{
			matrix.get(1, 0);
			Assert.fail();
		}catch (IndexOutOfBoundsException e){
			//nothing to do
		}
		//TODO:: test getters & setters
	}
	
	@Test
	public void testMatrixRowColGettersSetters() throws Exception {
		LOGGER.info("Testing row/col getters and setters for {}", curMatrixClass);
		//TODO:: test replace row/col values
	}
	
	@Test
	public void testMatrixRowColAddRem() throws Exception {
		LOGGER.info("Testing row/col adding/removing for {}", curMatrixClass);
		//TODO:: test adding/removing rows/cols
	}
	
	@Test
	public void testMatrixDefaultVal() throws Exception {
		LOGGER.info("Testing default value for {}", curMatrixClass);
		//TODO:: test default val set
	}
}
