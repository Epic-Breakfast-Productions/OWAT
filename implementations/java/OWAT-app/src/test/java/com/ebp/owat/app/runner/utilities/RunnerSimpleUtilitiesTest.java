package com.ebp.owat.app.runner.utilities;

import com.ebp.owat.app.runner.RunnerUtilities;
import com.ebp.owat.lib.datastructure.matrix.Hash.HashedMatrix;
import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.set.LongLinkedList;
import com.ebp.owat.lib.datastructure.value.NodeMode;
import com.ebp.owat.lib.datastructure.value.Value;
import com.ebp.owat.lib.utils.rand.RandGenerator;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RunnerSimpleUtilitiesTest extends RunnerUtilTest {

	@Test
	public void testReadDataIn() throws IOException {
		ByteArrayInputStream is = new ByteArrayInputStream("Hello World".getBytes(StandardCharsets.UTF_8));
		LongLinkedList<Byte> result = utilities.readDataIn(is);

		assertEquals(
			Arrays.asList((byte)'H', (byte)'e', (byte)'l', (byte)'l', (byte)'o', (byte)' ', (byte)'W', (byte)'o', (byte)'r', (byte)'l', (byte)'d'),
			result
		);
	}


	private void assertListOfValues(LongLinkedList<Value> values, long expectedSize){
		assertEquals("List given was the wrong size.", expectedSize, values.size());

		for (Value curVal : values){
			assertNotNull("List given has null values.", curVal);
		}
	}

	@Test
	public void testGetListOfValues() {
		this.assertListOfValues(
			utilities.getListOfValues(1, new RandGenerator(), NodeMode.BIT),
			1
		);
		this.assertListOfValues(
			utilities.getListOfValues(2, new RandGenerator(), NodeMode.BIT),
			2
		);
		this.assertListOfValues(
			utilities.getListOfValues(10, new RandGenerator(), NodeMode.BIT),
			10
		);
		this.assertListOfValues(
			utilities.getListOfValues(50, new RandGenerator(), NodeMode.BIT),
			50
		);
		this.assertListOfValues(
			utilities.getListOfValues(11, new RandGenerator(), NodeMode.BIT),
			11
		);
	}

	@Test
	public void testDetermineMinStepsToTake() {
		//TODO:: determineMinStepsToTake
	}

	@Test
	public void testDetermineMaxStepsToTake() {
		//TODO:: determineMaxStepsToTake
	}

	@Test
	public void testGetMatrixAsBytes(){
		//TODO:: getMatrixAsBytes
	}
}
