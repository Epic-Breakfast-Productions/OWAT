package com.ebp.owat.lib.structure.matrix;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.array.ArrayScramblingMatrix;
import com.ebp.owat.lib.datastructure.matrix.hash.HashedScramblingMatrix;
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
public abstract class MatrixTest <T extends Matrix<Integer>> {
	private static final Logger LOGGER = LoggerFactory.getLogger(MatrixTest.class);
	
	protected final Class<T> curMatrixClass;
	
	public MatrixTest(Class<T> curMatrixClass){
		this.curMatrixClass = curMatrixClass;
	}
	
	@Parameterized.Parameters
	public static Collection getMatrixClassesToTest(){
		return Arrays.asList(new Object[][] {
			{ HashedScramblingMatrix.class },
			{ ArrayScramblingMatrix.class },
			//{ LinkedScramblingMatrix.class }//28 tests fail,
		});
	}
	
	@Before
	public void setUp() throws Exception {
		LOGGER.info("Testing the {}", this.curMatrixClass);
	}
	
	@After
	public void tearDown() throws Exception {
		LOGGER.info("Done testing the {}", this.curMatrixClass);
	}
	
	@SuppressWarnings("unchecked")
	protected T getTestingInstance() throws Exception {
		LOGGER.debug("Getting instance of {} matrix.", this.curMatrixClass.getName());
		return this.curMatrixClass.getConstructor().newInstance();
	}
}
