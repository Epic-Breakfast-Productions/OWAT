package com.ebp.owat.lib.runner.utilities;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.set.LongLinkedList;
import com.ebp.owat.lib.datastructure.value.NodeMode;
import com.ebp.owat.lib.datastructure.value.Value;
import com.ebp.owat.lib.runner.utils.MatrixMode;
import com.ebp.owat.lib.utils.rand.OwatRandGenerator;
import com.ebp.owat.lib.utils.rand.RandGenerator;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.Assert.*;

public class RunnerSimpleUtilitiesTest extends RunnerUtilTest {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(RunnerSimpleUtilitiesTest.class);

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
	public void addRandRowOrColTest() throws IOException {
		LongLinkedList<Byte> bytes = utilities.readDataIn(new ByteArrayInputStream("8".getBytes(StandardCharsets.UTF_8)));
		Matrix m = utilities.getMatrix(bytes,MatrixMode.HASHED,NodeMode.BYTE);

		assertTrue("Was not full before testing could begin.", m.isFull());

		OwatRandGenerator rand = new RandGenerator();

		for(int i = 0; i < 50; i++){
			this.utilities.addRandRowOrCol(m, rand, NodeMode.BYTE);
			assertTrue(
				"Matrix was not full after adding a random row. Iteration: " + i +" Height: " + m.getHeight() + " Width: " + m.getWidth() + " Size: " + m.size() + " # elements: " + m.numElements(),
				m.isFull()
			);
		}
	}

	@Test
	public void testGetMatrixAsBytes(){
		//TODO:: getMatrixAsBytes
	}

	@Test
	public void testByteArrCompression() throws IOException {
		byte original[] = "HEllo Worldasdaswf wefi;3w4jo;9 f41309 3q4jq34t0[8u34qg5t09[3q54g poi nqg r3oiuerq gnoerq gjh; qerg8oj345qg o835i4gjoe;qi5g lki;erhgklj;dfaghnk;ljetrqb5n3o45ituy3qotjewgfslkfgjsdlfweuteoligjdfglkdfsjngaldskiughrekgjhefgpiodasfgyuerghkermngerlkgjdfng".getBytes(StandardCharsets.UTF_8);

		LOGGER.debug("Original:     {}", original);

		byte compressed[] = utilities.compressBytes(original);

		ByteArrayInputStream compressdStream = new ByteArrayInputStream(compressed);

		byte decompressed[] = utilities.decompressBytes(compressdStream);
		LOGGER.debug("decompressed: {}", decompressed);

		assertTrue("Compressed and decompressed data were not equal.", Arrays.equals(original, decompressed));
	}
}
