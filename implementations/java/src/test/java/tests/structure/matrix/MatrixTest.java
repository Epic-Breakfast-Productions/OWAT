package tests.structure.matrix;

import com.ebp.owat.lib.datastructure.matrix.Hash.HashedMatrix;
import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.ScramblingMatrix;
import com.sun.istack.internal.Nullable;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Greg Stewart on 3/30/17.
 */
@RunWith(Theories.class)
public class MatrixTest {
	private Logger LOGGER = LoggerFactory.getLogger(MatrixTest.class);
	
	@DataPoints
	public static List<Class<? extends Matrix>> matrixTypesToTest = Arrays.asList(
			HashedMatrix.class
	);
	
	private Matrix<Integer> getTestingInstance(Class<? extends Matrix> curClass) throws Exception{
		return curClass.getConstructor(ScramblingMatrix.Type.class).newInstance(ScramblingMatrix.Type.SCRAMBLING);
	}
	
	@Theory(nullsAccepted = false)
	public void testAddRowCol(Class<? extends Matrix> matrixClass) throws Exception{
		LOGGER.info("Testing adding rows and columns for {}", matrixClass);
		Matrix<Integer> matrix = getTestingInstance(matrixClass);
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
