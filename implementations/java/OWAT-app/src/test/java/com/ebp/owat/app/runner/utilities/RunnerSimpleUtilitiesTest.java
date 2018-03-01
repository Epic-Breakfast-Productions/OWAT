package com.ebp.owat.app.runner.utilities;

import com.ebp.owat.app.runner.RunnerUtilities;
import com.ebp.owat.lib.datastructure.matrix.Hash.HashedMatrix;
import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.set.LongLinkedList;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

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

	@Test
	public void testPadMatrix() {
		Matrix matrix = new HashedMatrix();
		//TODO
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
