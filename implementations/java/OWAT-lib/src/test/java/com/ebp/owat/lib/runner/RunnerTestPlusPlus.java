package com.ebp.owat.lib.runner;

import com.ebp.owat.lib.datastructure.value.NodeMode;
import com.ebp.owat.lib.runner.utils.MatrixMode;
import com.ebp.owat.lib.runner.utils.ScrambleMode;
import com.ebp.owat.lib.runner.utils.results.RunResults;
import com.ebp.owat.lib.utils.rand.ThreadLocalRandGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

/**
 * These will take a while.
 *
 * Stress test of the scrambling/descrambling process.
 */
@RunWith(Parameterized.class)
public class RunnerTestPlusPlus {
	private static final Logger LOGGER = LoggerFactory.getLogger(RunnerTestPlusPlus.class);

	/** Flag to enable or disable file reporting results to CSV. Normal state is false. */
	private static final boolean REPORT_TO_CSV = false;

	/** Flag to run a short test. Runs a reasonable test set for normal testing if set to true. Normally true. */
	private static final boolean SHORT = true;

	/** Flag to run a test for each type of matrix available. Otherwise runs as 'auto'. Will DRASTICALLY increase runtime. */
	private static final boolean MODES_TEST = false;

	private static final int START = 1,
		INC = 50,
		BOUND = 15_000;

	private final byte data[];

	private final MatrixMode matrixMode;

	public RunnerTestPlusPlus(byte data[], MatrixMode matrixMode){
		this.data = data;
		this.matrixMode = matrixMode;
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

		builder.setMatrixMode(this.matrixMode);
		builder.setDataInput(new ByteArrayInputStream(this.data));
		builder.setDataOutput(scrambledDataOutput);
		builder.setKeyOutput(keyOutput);
		builder.setNodeType(mode);
		builder.setRand(new ThreadLocalRandGenerator());

		LOGGER.info("Scrambling test data.");

		{
			ScrambleRunner scrambleRunner = builder.build();
			scrambleRunner.doSteps();
			report(scrambleRunner.getLastRunResults());
		}

		LOGGER.info("DONE Scrambling test data.");

		ByteArrayOutputStream deScrambledDataOutput = new ByteArrayOutputStream();

		DeScrambleRunner.Builder deScrambleBuilder = new DeScrambleRunner.Builder();

		String scrambledData = scrambledDataOutput.toString();

		LOGGER.info("Scrambled data: (length: {}) \"{}\"", scrambledData.length(), scrambledData);

		deScrambleBuilder.setDataInput(new ByteArrayInputStream(scrambledData.getBytes(StandardCharsets.UTF_8)));
		deScrambleBuilder.setKeyInput(new ByteArrayInputStream(keyOutput.toByteArray()));
		deScrambleBuilder.setDataOutput(deScrambledDataOutput);

		LOGGER.info("Descrambling test data.");

		{
			DeScrambleRunner deScrambleRunner = deScrambleBuilder.build();
			deScrambleRunner.doSteps();
			report(deScrambleRunner.getLastRunResults());
		}

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
	public static Collection<Object[]> getByteArraysToTest(){
		Collection<Object[]> params = new LinkedList<>();

		for(int i = START; i <= BOUND; i+= (SHORT?BOUND/4:INC)){
			Object curTest[] = new Object[2];

			if(MODES_TEST){
				for(MatrixMode curMode : MatrixMode.values()) {
					curTest[0] = getTestData(i);
					curTest[1] = curMode;
					params.add(curTest.clone());
				}
			}else{
				curTest[0] = getTestData(i);
				curTest[1] = null;
				params.add(curTest);
			}
		}

		return params;
	}

	private static byte[] getTestData(int numBytes){
		byte output[] = new byte[numBytes];

		for(int i = 0; i < numBytes; i++){
			output[i] = (byte)i;
		}

		return output;
	}

	private static String RESULTS_DIR = "out/";
	private static String SCRAMBLING_RESULTS_CSV = RESULTS_DIR + "scramblingResults.csv";
	private static String DESCRAMBLING_RESULTS_CSV = RESULTS_DIR + "descramblingResults.csv";
	private static boolean SCRAM_SCV_EXISTS;
	private static boolean DESCRAM_SCV_EXISTS;

	static {
		File f = new File(SCRAMBLING_RESULTS_CSV);
		SCRAM_SCV_EXISTS = f.exists() && !f.isDirectory();
		DESCRAM_SCV_EXISTS = f.exists() && !f.isDirectory();
	}

	private static void report(RunResults results) throws IOException {
		if(!REPORT_TO_CSV){
			return;
		}
		String outFileLoc = (results.getScrambleMode() == ScrambleMode.SCRAMBLING ? SCRAMBLING_RESULTS_CSV : DESCRAMBLING_RESULTS_CSV);
		LOGGER.debug("Writing run data to \"{}\"", outFileLoc);
		try(
			FileWriter fw = new FileWriter(outFileLoc, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw)
		){
			if(results.getScrambleMode() == ScrambleMode.SCRAMBLING){
				out.println(
					results.getCsvLine(!SCRAM_SCV_EXISTS)
				);
				SCRAM_SCV_EXISTS = true;
			}else{
				out.println(results.getCsvLine(!DESCRAM_SCV_EXISTS));
				DESCRAM_SCV_EXISTS = true;
			}
		} catch (IOException e) {
			LOGGER.error("Error writing result to CSV file: ", e);
			throw e;
		}
	}
}
