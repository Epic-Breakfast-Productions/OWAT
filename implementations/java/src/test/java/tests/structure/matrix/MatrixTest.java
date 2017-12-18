package tests.structure.matrix;

import com.ebp.owat.lib.datastructure.matrix.Hash.HashedMatrix;
import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.ScramblingMatrix;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Greg Stewart on 3/30/17.
 */
@RunWith(Parameterized.class)
public abstract class MatrixTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(MatrixTest.class);
	
	protected final Class<? extends Matrix> curMatrixClass;
	
	public MatrixTest(Class<? extends Matrix> curMatrixClass){
		this.curMatrixClass = curMatrixClass;
	}
	
	@Parameterized.Parameters
	public static Collection getMatrixClassesToTest(){
		return Arrays.asList(new Object[][] {
				{ HashedMatrix.class }
		});
	}
	
	@Before
	public void setUp() throws Exception {
		LOGGER.info("Testing the basic implemented methods of {}", this.curMatrixClass);
	}
	
	@After
	public void tearDown() throws Exception {
		LOGGER.info("Done testing the basic implemented methods of {}", this.curMatrixClass);
	}
	
	@SuppressWarnings("unchecked")
	protected Matrix<Integer> getTestingInstance() throws Exception{
		LOGGER.debug("Getting instance of {} matrix.", this.curMatrixClass.getName());
		return this.curMatrixClass.getConstructor(ScramblingMatrix.Type.class).newInstance(ScramblingMatrix.Type.SCRAMBLING);
	}
}
