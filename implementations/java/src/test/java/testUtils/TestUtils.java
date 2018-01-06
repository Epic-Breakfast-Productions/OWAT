package testUtils;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TestUtils {
	
	public static void assert2dArrayEquals(Object[][] expected, Object[][] actual){
		assertEquals(expected.length, actual.length);
		
		for (int i = 0; i < expected.length; i++) {
			assertArrayEquals(expected[i], actual[i]);
		}
	}
}
