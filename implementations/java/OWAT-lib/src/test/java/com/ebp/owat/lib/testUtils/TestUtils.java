package com.ebp.owat.lib.testUtils;

import com.ebp.owat.lib.datastructure.matrix.Matrix;

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
}
