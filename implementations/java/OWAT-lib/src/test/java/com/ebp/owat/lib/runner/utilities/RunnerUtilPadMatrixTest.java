package com.ebp.owat.lib.runner.utilities;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.ScrambleMatrix;
import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.MatrixCoordinate;
import com.ebp.owat.lib.datastructure.set.LongLinkedList;
import com.ebp.owat.lib.datastructure.value.NodeMode;
import com.ebp.owat.lib.runner.utils.MatrixMode;
import com.ebp.owat.lib.utils.rand.RandGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
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
		ScrambleMatrix m = utilities.getMatrix(bytes,MatrixMode.HASHED,this.nodeType);

		long initHeight = m.getHeight();
		long initWidth = m.getWidth();
		long initNumHeld = m.numElements();

		this.utilities.padMatrix(m, new RandGenerator(), this.nodeType);

		assertTrue("Matrix was not full after padding.", m.isFull());

		assertTrue(m.getHeight() >= initHeight);
		assertTrue(m.getWidth() >= initWidth);

		bytes = utilities.readDataIn(new ByteArrayInputStream(testData.getBytes(StandardCharsets.UTF_8)));
		Matrix mTwo = utilities.getMatrix(bytes,MatrixMode.HASHED,this.nodeType);

		Iterator itOrig = m.getSubMatrix(
			new MatrixCoordinate(m),
			initHeight, initWidth).iterator();
		Iterator itTwo = mTwo.iterator();

		long count = 0;
		while (itOrig.hasNext()){
			if(count >= initNumHeld){
				break;
			}
			assertEquals(itTwo.next(), itOrig.next());
			count++;
		}
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
