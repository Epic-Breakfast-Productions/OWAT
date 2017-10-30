package tests.structure.matrix;

import com.ebp.owat.lib.datastructure.matrix.Hash.HashedMatrix;
import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.ScramblingMatrix;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
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
		assertThat(matrix.getNumRows(), is(0L));
		matrix.addRow();
		assertThat(matrix.getNumCols(), is(1L));
		assertThat(matrix.getNumRows(), is(1L));
		matrix.addCols(2);
		assertThat(matrix.getNumCols(), is(3L));
		assertThat(matrix.getNumRows(), is(1L));
		matrix.addRows(2);
		assertThat(matrix.getNumCols(), is(3L));
		assertThat(matrix.getNumRows(), is(3L));
		//TODO:: finish with add__(Collection)
	}
	
	//TODO:: test replace node values
	//TODO:: test replace row/col values
	//TODO:: test removing rows/cols
	//TODO:: test getters
	//TODO:: test default val set
}
