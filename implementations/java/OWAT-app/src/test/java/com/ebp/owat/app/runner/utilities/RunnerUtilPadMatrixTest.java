package com.ebp.owat.app.runner.utilities;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.set.LongLinkedList;
import com.ebp.owat.lib.datastructure.value.NodeMode;
import com.ebp.owat.lib.utils.rand.RandGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class RunnerUtilPadMatrixTest extends RunnerUtilTest {

	private final String testData;
	private final NodeMode nodeType;

	public RunnerUtilPadMatrixTest(String testData, NodeMode mode) {
		this.testData = testData;
		this.nodeType = mode;
	}

	@Test
	public void testPadMatrix() throws IOException {
		LongLinkedList<Byte> bytes = utilities.readDataIn(new ByteArrayInputStream(testData.getBytes(StandardCharsets.UTF_8)));
		Matrix m = utilities.getMatrix(bytes,this.nodeType);

		long initHeight = m.getHeight();
		long initWidth = m.getHeight();

		this.utilities.padMatrix(m, new RandGenerator(), this.nodeType);

		assertTrue("Matrix was not full after padding.", m.isFull());

		assertTrue(m.getHeight() > initHeight);
		assertTrue(m.getWidth() > initWidth);

		//TODO:: test initial matrix was not changed.
	}

	@Parameterized.Parameters
	public static Collection getMatrixClassesToTest(){
		return Arrays.asList(new Object[][] {
			{ "a", NodeMode.BYTE},
			{ "hello world", NodeMode.BYTE},
			{ "hello world12345", NodeMode.BYTE},
			{ "a", NodeMode.BIT},
			{ "hello world", NodeMode.BIT},
			{ "hello world12345", NodeMode.BIT},
			{ "hello wo", NodeMode.BIT}
		});
	}
}
