package com.ebp.owat.app.runner;

import com.ebp.owat.lib.datastructure.value.NodeMode;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ScrambleRunnerTest {
	
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
		ScrambleRunner.Builder builder = new ScrambleRunner.Builder();
		builder.setRand("Hello world");
		builder.setDataInput(new ByteArrayInputStream("lorem ipsum!!".getBytes(StandardCharsets.UTF_8)));
		builder.setDataOutput(new ByteArrayOutputStream());
		builder.setKeyOutput(new ByteArrayOutputStream());
		builder.setNodeType(NodeMode.BYTE);
		
		builder.build().doSteps();
	}
}
