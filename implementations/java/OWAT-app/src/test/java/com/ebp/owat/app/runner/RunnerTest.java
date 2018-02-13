package com.ebp.owat.app.runner;

import com.ebp.owat.lib.datastructure.value.NodeMode;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class RunnerTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(RunnerTest.class);
	
	//TODO:: test using parameters (string to scramble, node type)
	
	@Test
	public void testScrambleRunnerBuilder(){
		ScrambleRunner.Builder builder = new ScrambleRunner.Builder();
		try {
			builder.build();
			Assert.fail();
		}catch (IllegalArgumentException e){
			//nothing to do
		}
		builder.setRand("Hello world");
		builder.setDataInput(new ByteArrayInputStream("lorem ipsum".getBytes(StandardCharsets.UTF_8)));
		builder.setDataOutput(new ByteArrayOutputStream());
		builder.setKeyOutput(new ByteArrayOutputStream());
		builder.setNodeType(NodeMode.BYTE);
		
		builder.build();
	}
	
	@Test
	public void testScrambleRunner() throws IOException {
		ScrambleRunner.Builder scrambleBuilder = new ScrambleRunner.Builder();
		/*
		LOGGER.info("Testing square byte.");
		scrambleBuilder.setRand("Hello world");
		scrambleBuilder.setDataInput(new ByteArrayInputStream("lorem ipsum!!".getBytes(StandardCharsets.UTF_8)));
		scrambleBuilder.setDataOutput(new ByteArrayOutputStream());
		scrambleBuilder.setKeyOutput(new ByteArrayOutputStream());
		scrambleBuilder.setNodeType(NodeMode.BYTE);
		scrambleBuilder.build().doSteps();
		
		LOGGER.info("Testing non square byte.");
		scrambleBuilder.setRand("Hello world");
		scrambleBuilder.setDataInput(new ByteArrayInputStream("lorem ipsum".getBytes(StandardCharsets.UTF_8)));
		scrambleBuilder.setDataOutput(new ByteArrayOutputStream());
		scrambleBuilder.setKeyOutput(new ByteArrayOutputStream());
		scrambleBuilder.setNodeType(NodeMode.BYTE);
		scrambleBuilder.build().doSteps();
		
		LOGGER.info("Testing bit 1");
		scrambleBuilder.setRand("Hello world");
		scrambleBuilder.setDataInput(new ByteArrayInputStream("lorem ipsum!!".getBytes(StandardCharsets.UTF_8)));
		scrambleBuilder.setDataOutput(new ByteArrayOutputStream());
		scrambleBuilder.setKeyOutput(new ByteArrayOutputStream());
		scrambleBuilder.setNodeType(NodeMode.BIT);
		scrambleBuilder.build().doSteps();
		
		LOGGER.info("Testing bit 2.");
		scrambleBuilder.setRand("Hello world");
		scrambleBuilder.setDataInput(new ByteArrayInputStream("lorem ipsum".getBytes(StandardCharsets.UTF_8)));
		scrambleBuilder.setDataOutput(new ByteArrayOutputStream());
		scrambleBuilder.setKeyOutput(new ByteArrayOutputStream());
		scrambleBuilder.setNodeType(NodeMode.BIT);
		scrambleBuilder.build().doSteps();
		/* */
		
		
		
		
		LOGGER.info("Testing to/from.");
		
		scrambleBuilder = new ScrambleRunner.Builder();
		
		//String testData = "lorem ipsum This is a test message 1234567890!@#$%^&*()_+-={}[],.<>/?;:'\"\\|";
		String testData = "hi";
		
		ByteArrayOutputStream scrambledDataOutput = new ByteArrayOutputStream();
		ByteArrayOutputStream keyOutput = new ByteArrayOutputStream();
		
		scrambleBuilder.setDataInput(new ByteArrayInputStream(testData.getBytes(StandardCharsets.UTF_8)));
		scrambleBuilder.setDataOutput(scrambledDataOutput);
		scrambleBuilder.setKeyOutput(keyOutput);
		scrambleBuilder.setNodeType(NodeMode.BYTE);
		scrambleBuilder.build().doSteps();
		
		
		ByteArrayOutputStream deScrambledDataOutput = new ByteArrayOutputStream();
		
		DeScrambleRunner.Builder deScrambleBuilder = new DeScrambleRunner.Builder();
		
		String scrambledData = scrambledDataOutput.toString();
		
		LOGGER.debug("Scrambled data: {}", scrambledData);
		
		deScrambleBuilder.setDataInput(new ByteArrayInputStream(scrambledData.getBytes(StandardCharsets.UTF_8)));
		deScrambleBuilder.setKeyInput(new ByteArrayInputStream(keyOutput.toByteArray()));
		deScrambleBuilder.setDataOutput(deScrambledDataOutput);
		
		deScrambleBuilder.build().doSteps();
		
		String output = deScrambledDataOutput.toString();
		assertEquals(testData, output);
	}
}
