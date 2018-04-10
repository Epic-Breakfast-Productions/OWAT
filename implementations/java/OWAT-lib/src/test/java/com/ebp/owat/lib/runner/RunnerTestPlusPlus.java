package com.ebp.owat.lib.runner;

import com.ebp.owat.lib.datastructure.value.NodeMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * These will take a while.
 *
 * Stress test of the
 */
@RunWith(Parameterized.class)
public class RunnerTestPlusPlus {
	private static final Logger LOGGER = LoggerFactory.getLogger(RunnerTestPlusPlus.class);

	private static final int START = 1,
		INC = 50,
		BOUND = 10_000;

	private static final boolean SHORT = true;

	//TODO:: setup reporting to aggregate results

	private final byte data[];

	public RunnerTestPlusPlus(byte data[]){
		this.data = data;
	}

	private static ScrambleRunner.Builder getBuilder(){
		ScrambleRunner.Builder builder = new ScrambleRunner.Builder();

		return builder;
	}

	private void runTest(NodeMode mode) throws IOException {
		LOGGER.info("Testing {} scrambling. Test Data: {}", mode, this.data);
		ScrambleRunner.Builder builder = getBuilder();

		ByteArrayOutputStream scrambledDataOutput = new ByteArrayOutputStream();
		ByteArrayOutputStream keyOutput = new ByteArrayOutputStream();

		builder.setDataInput(new ByteArrayInputStream(this.data));
		builder.setDataOutput(scrambledDataOutput);
		builder.setKeyOutput(keyOutput);
		builder.setNodeType(mode);

		LOGGER.info("Scrambling test data.");

		builder.build().doSteps();

		LOGGER.info("DONE Scrambling test data.");

		ByteArrayOutputStream deScrambledDataOutput = new ByteArrayOutputStream();

		DeScrambleRunner.Builder deScrambleBuilder = new DeScrambleRunner.Builder();

		String scrambledData = scrambledDataOutput.toString();
		//String scrambledData = String.valueOf(scrambledDataOutput.toByteArray());


		LOGGER.info("Scrambled data: (length: {}) \"{}\"", scrambledData.length(), scrambledData);

		deScrambleBuilder.setDataInput(new ByteArrayInputStream(scrambledData.getBytes(StandardCharsets.UTF_8)));
		deScrambleBuilder.setKeyInput(new ByteArrayInputStream(keyOutput.toByteArray()));
		deScrambleBuilder.setDataOutput(deScrambledDataOutput);

		LOGGER.info("Descrambling test data.");

		deScrambleBuilder.build().doSteps();

		LOGGER.info("DONE descrambling data.");

		byte output[] = deScrambledDataOutput.toByteArray();

		LOGGER.info("Expected:({}) {}", this.data.length, this.data);
		LOGGER.info("Got:     ({}) {}", output.length, output);
		assertEquals(this.data.length, output.length);
		for(int i = 0; i < this.data.length; i++){
			assertEquals(
				this.data[i],
				output[i]
			);
		}
	}

	@Test
	public void testByteModePp() throws IOException {
		this.runTest(NodeMode.BYTE);
	}

	@Test
	public void testBitModePp() throws IOException {
		this.runTest(NodeMode.BIT);
	}

	@Parameterized.Parameters
	public static Collection getByteArraysToTest(){
		return getMultiTestData();
	}

	private static List<byte[]> getMultiTestData(){
		LinkedList<byte[]> testData = new LinkedList<>();


		for(int i = START; i <= BOUND; i+= (SHORT?BOUND/10:INC)){
			testData.add(getTestData(i));
		}

		return testData;
	}

	private static byte[] getTestData(int numBytes){
		byte output[] = new byte[numBytes];

		for(int i = 0; i < numBytes; i++){
			output[i] = (byte)i;
		}

		return output;
	}
}
