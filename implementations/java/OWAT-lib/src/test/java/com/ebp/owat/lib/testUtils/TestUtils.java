package com.ebp.owat.lib.testUtils;

import com.ebp.owat.lib.datastructure.matrix.Matrix;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TestUtils {
	
	public static void assert2dArrayEquals(Object[][] expected, Object[][] actual){
		assertEquals(expected.length, actual.length);
		
		for (int i = 0; i < expected.length; i++) {
			assertArrayEquals(expected[i], actual[i]);
		}
	}
	
	public static void assertMatrix(Object[][] expected, Matrix actual) {
		assertEquals(expected.length, actual.getNumRows());
		
		for(int i = 0; i < expected.length; i++){
			assertEquals(expected[i].length, actual.getNumCols());
			
			for(int j = 0; j < expected[i].length; j++){
				assertEquals(
					expected[i][j],
					actual.get(j, i)
				);
			}
		}
	}

	/**
	 *
	 * <pre>
	 {
	    { 0, N, 2, N, 4},
	    { N, 6, N, 8, N},
	    {10, N,12,13,14},
	    {15,16, N,18,19},
	    {20, N,22, N,24}
	 }
	 </pre>
	 * @param testMatrix
	 */
	public static void setupMatrix(Matrix<Integer> testMatrix){
		Integer N = testMatrix.getDefaultValue();
		
		testMatrix.grow(5L);
		
		testMatrix.replaceRow(0, Arrays.asList( 0, N, 2, N, 4));
		testMatrix.replaceRow(1, Arrays.asList( N, 6, N, 8, N));
		testMatrix.replaceRow(2, Arrays.asList(10, N,12,13,14));
		testMatrix.replaceRow(3, Arrays.asList(15,16, N,18,19));
		testMatrix.replaceRow(4, Arrays.asList(20, N,22, N,24));
	}
}
