package com.ebp.owat.app.runner;

import com.ebp.owat.lib.datastructure.value.NodeMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class RunnerTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(RunnerTest.class);
	
	private final String data;

	public RunnerTest(String data){
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

		builder.setDataInput(new ByteArrayInputStream(this.data.getBytes(StandardCharsets.UTF_8)));
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

		String output = deScrambledDataOutput.toString();

		LOGGER.info("Expected: {}", this.data);
		LOGGER.info("Got:      {}", output);
		assertEquals(this.data, output);
	}

	@Test
	public void testByteMode() throws IOException {
		this.runTest(NodeMode.BYTE);
	}

	@Test
	public void testBitMode() throws IOException {
		this.runTest(NodeMode.BIT);
	}

	@Parameterized.Parameters
	public static Collection getMatrixClassesToTest(){
		return Arrays.asList(new Object[][] {
			{ "a" },
			{ "ab" },
			{ "hello world"},
			{ "hello world12345" },
			{ "Super Secret Message that cannot get into the wrong hands. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam in velit sapien. Nullam id pharetra metus. In ac massa dignissim, cursus lorem ut, tincidunt libero. Nulla ac posuere enim. Ut ante tellus, faucibus sit amet elit scelerisque, semper imperdiet lorem. Ut in nulla vel eros euismod euismod. Donec tincidunt quam turpis, aliquet porta quam bibendum ac. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus."}
		});
	}
}
