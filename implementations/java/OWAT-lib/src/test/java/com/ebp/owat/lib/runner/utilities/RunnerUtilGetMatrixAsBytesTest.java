package com.ebp.owat.lib.runner.utilities;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.set.LongLinkedList;
import com.ebp.owat.lib.datastructure.value.NodeMode;
import com.ebp.owat.lib.runner.utils.MatrixMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class RunnerUtilGetMatrixAsBytesTest extends RunnerUtilTest {

	private final String testData;
	private final NodeMode nodeType;

	public RunnerUtilGetMatrixAsBytesTest(String testData, NodeMode nodeType){
		this.testData = testData;
		this.nodeType = nodeType;

	}

	@Test
	public void testGetMatrixAsBytes() throws IOException {
		LongLinkedList<Byte> bytes = utilities.readDataIn(new ByteArrayInputStream(testData.getBytes(StandardCharsets.UTF_8)));
		Matrix m = utilities.getMatrix(bytes,MatrixMode.HASHED,this.nodeType);

		//this.utilities.getMatrixAsBytes(m, this.nodeType);
		//TODO actually... do something?
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
