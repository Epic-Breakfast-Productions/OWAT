package com.ebp.owat.lib.runner.utilities;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.MatrixIterator;
import com.ebp.owat.lib.datastructure.set.LongLinkedList;
import com.ebp.owat.lib.datastructure.value.ByteValue;
import com.ebp.owat.lib.datastructure.value.NodeMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class RunnerUtilGetMatrixTest extends RunnerUtilTest {

	private final String testData;
	private final NodeMode nodeType;
	private final long expectedNumHeld;
	private final long expectedHeight;
	private final long expectedWidth;
	private final boolean expectFull;

	public RunnerUtilGetMatrixTest(String testData, NodeMode nodeType, long expectedHeight, long expectedWidth){
		this.testData = testData;
		this.nodeType = nodeType;
		this.expectedHeight = expectedHeight;
		this.expectedWidth = expectedWidth;
		this.expectedNumHeld = (this.nodeType == NodeMode.BYTE ? this.testData.length() : this.testData.length() * 8L );
		this.expectFull = this.expectedNumHeld == this.expectedHeight * this.expectedWidth;
	}

	private void assertMatrix(Matrix m){

		assertEquals(this.expectedHeight, m.getHeight());
		assertEquals(this.expectedWidth, m.getWidth());

		assertEquals(this.expectFull, m.isFull());
		assertEquals(this.expectedNumHeld, m.numElements());

		if(this.nodeType == NodeMode.BIT){
			return;//TODO:: figure out how to verify contents of bit matrix
		}

		MatrixIterator it = m.iterator();

		for(byte curByte : this.testData.getBytes()){
			ByteValue curVal = (ByteValue) it.next();
			assertEquals(curByte, (byte)curVal.getValue());
		}

		assertEquals(!this.expectFull, it.hasNext());
	}

	@Test
	public void testGetMatrix() throws IOException {
		LongLinkedList<Byte> bytes = utilities.readDataIn(new ByteArrayInputStream(testData.getBytes(StandardCharsets.UTF_8)));
		Matrix m = utilities.getMatrix(bytes,this.nodeType);
		this.assertMatrix(m);
	}

	@Test
	public void testGetMatrixWithHW() throws IOException {
		LongLinkedList<Byte> bytes = utilities.readDataIn(new ByteArrayInputStream(testData.getBytes(StandardCharsets.UTF_8)));
		Matrix m = utilities.getMatrix(bytes,this.nodeType, this.expectedHeight, this.expectedWidth);
		this.assertMatrix(m);
	}

	@Parameterized.Parameters
	public static Collection getMatrixClassesToTest(){
		return Arrays.asList(new Object[][] {
			{ "a", NodeMode.BYTE, 1, 1},
			{ "hello world", NodeMode.BYTE, 3, 4},
			{ "hello world12345", NodeMode.BYTE, 4, 4},
			{ "a", NodeMode.BIT, 3, 3},
			{ "hello world", NodeMode.BIT, 9, 10},
			{ "hello world12345", NodeMode.BIT, 11, 12},
			{ "hello wo", NodeMode.BIT, 8, 8}
		});
	}
}
