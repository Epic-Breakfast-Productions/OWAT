package com.ebp.owat.lib.runner;

import com.ebp.owat.lib.datastructure.matrix.ScrambleMatrix;
import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.MatrixCoordinate;
import com.ebp.owat.lib.datastructure.set.LongLinkedList;
import com.ebp.owat.lib.datastructure.value.NodeMode;
import com.ebp.owat.lib.datastructure.value.Value;
import com.ebp.owat.lib.runner.utils.MatrixMode;
import com.ebp.owat.lib.runner.utils.RunnerUtilities;
import com.ebp.owat.lib.runner.utils.Step;
import com.ebp.owat.lib.runner.utils.results.DescrambleResults;
import com.ebp.owat.lib.utils.key.ScrambleKey;
import com.ebp.owat.lib.utils.rand.OwatRandGenerator;
import com.ebp.owat.lib.utils.scramble.ScrambleMove;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Iterator;

/**
 * The runner that descrambles data.
 * @param <N> The type of value to use
 * @param <M> The type of matrix to use
 * @param <R> The random number generator to use
 */
public class DeScrambleRunner<N extends Value, M extends ScrambleMatrix<N>, R extends OwatRandGenerator> extends OwatRunner<N,M,R> {
	private static final Logger LOGGER = LoggerFactory.getLogger(DeScrambleRunner.class);

	/** The key that will be used. */
	private ScrambleKey key;
	/** The type of matrix to use. */
	private MatrixMode matrixMode;
	/** The type of data that will be used. */
	private NodeMode nodeType;
	/** The stream to use to read the scrambled data in. */
	private final InputStream dataInput;
	/** The stream to use to read the key in */
	private final InputStream keyInput;
	/** The stream to use to write the data out */
	private final OutputStream dataOutput;

	/**
	 * Constructor to set up the descrambler. To be called by the builder.
	 * @param matrixMode The type of matrix to use. Null to auto determine best one to use.
	 * @param dataInput The scrambled data to input.
	 * @param keyInput The key data to input.
	 * @param dataOutput The stream to output the descrambled data.
	 */
	private DeScrambleRunner(
		MatrixMode matrixMode,
		InputStream dataInput,
		InputStream keyInput,
		OutputStream dataOutput
	){
		if(dataInput == null){
			throw new IllegalArgumentException("Invalid null parameter(s) given. Must specify data to input.");
		}
		if(dataOutput == null){
			throw new IllegalArgumentException("Invalid null parameter(s) given. Must specify a method of outputting the descrambled data.");
		}
		if(keyInput == null) {
			throw new IllegalArgumentException("Invalid null parameter(s) given. Must specify a method of inputting the key data.");
		}

		this.matrixMode = matrixMode;
		this.dataInput = dataInput;
		this.keyInput = keyInput;
		this.dataOutput = dataOutput;
	}

	/**
	 * Builder for the descrambler class. Used to easily setup the descrambler class.
	 */
	public static class Builder{
		/** The type of matrix to use */
		private MatrixMode matrixMode = null;
		/** The stream to use to read the scrambled data in. */
		private InputStream dataInput;
		/** The stream to use to read the key in */
		private InputStream keyInput;
		/** The stream to use to write the data out */
		private OutputStream dataOutput;

		/**
		 * Sets the type of matrix to use.
		 * @param matrixMode The type of matrix to use.
		 * @return This builder, for chaining calls.
		 */
		public Builder setMatrixMode(MatrixMode matrixMode){
			this.matrixMode = matrixMode;
			return this;
		}

		/**
		 * Sets the scrambler data input stream to give the descrambler.
		 * @param is The input stream
		 * @return This builder for chained setting methods.
		 */
		public DeScrambleRunner.Builder setDataInput(InputStream is){
			this.dataInput = is;
			return this;
		}

		/**
		 * Sets the scrambled data in the form of a string.
		 * @param data The scrambled data to set.
		 * @return This builder for chained setting methods.
		 */
		public DeScrambleRunner.Builder setDataInput(String data){
			return this.setDataInput(new ByteArrayInputStream(RunnerUtilities.getByteArrFromString(data)));
		}

		/**
		 * Sets the data input stream to the file specified.
		 *
		 * TODO:: move file verification from running code to this method.
		 *
		 * @param file The file of scrambed data to descramble.
		 * @return This builder for chained setting methods.
		 * @throws FileNotFoundException If something went wrong.
		 */
		public DeScrambleRunner.Builder setDataInput(File file) throws FileNotFoundException {
			return this.setDataInput(new FileInputStream(file));
		}

		/**
		 * Sets the output stream to send the descrambled data to.
		 * @param os The output stream to use.
		 * @return This builder object, for chaining calls.
		 */
		public DeScrambleRunner.Builder setDataOutput(OutputStream os){
			this.dataOutput = os;
			return this;
		}

		/**
		 * Sets the key input stream to use.
		 * @param is The input stream of the key.
		 * @return This builder, for chaining calls.
		 */
		public DeScrambleRunner.Builder setKeyInput(InputStream is){
			this.keyInput = is;
			return this;
		}

		/**
		 * Builds the actual runner.
		 * @return The runner setup with the builder.
		 */
		public DeScrambleRunner build(){
			return new DeScrambleRunner(
				matrixMode,
				dataInput,
				keyInput,
				dataOutput
			);
		}
	}

	@Override
	public synchronized DescrambleResults getLastRunResults() {
		DescrambleResults results = (DescrambleResults) this.lastRunResults;
		if(results == null){
			return null;
		}
		return results.clone();
	}

	@Override
	public void doSteps() throws IOException {
		DescrambleResults runResults = new DescrambleResults();
		this.setLastRunResults(runResults);
		long start, end;
		M matrix;

		runResults.setCurStep(Step.LOAD_KEY);
		start = System.currentTimeMillis();
		LOGGER.info("Loading key...");
		{
			byte decompressedKey[] = utils.decompressBytes(this.keyInput);
			this.key = OBJECT_MAPPER.readValue(decompressedKey, ScrambleKey.class);
			this.nodeType = this.key.meta.getNodeMode();
			runResults.setNodeMode(this.nodeType);
		}
		end = System.currentTimeMillis();
		runResults.setElapsedTime(Step.LOAD_KEY, start, end);

		runResults.setCurStep(Step.LOAD_SCRAMBLED_DATA);
		start = System.currentTimeMillis();
		LOGGER.info("Loading scrambled data...");

		{
			LongLinkedList<Byte> data = this.utils.readDataIn(this.dataInput, true);
			runResults.setNumBytesIn(data.sizeL());

			if(this.matrixMode == null){
				this.matrixMode = MatrixMode.determineModeToUse(data.sizeL());
			}

			runResults.setMatrixMode(this.matrixMode);

			LOGGER.debug("Length of scrambled data: {} bytes", data.sizeL());
			LOGGER.debug("Using matrix type: {}", this.matrixMode.name);
			matrix = this.utils.getMatrix(data, this.matrixMode, this.nodeType, this.key.meta.dataHeight, this.key.meta.dataWidth);
			runResults.setMatrixSize(matrix.size());
		}
		end = System.currentTimeMillis();
		runResults.setElapsedTime(Step.LOAD_SCRAMBLED_DATA, start, end);

		runResults.setCurStep(Step.DESCRAMBLING);
		start = System.currentTimeMillis();
		LOGGER.info("Descrambling data...");

		{
			long numMoves = this.key.getNumMoves();
			LOGGER.debug("Number of moves: {}", numMoves);
			runResults.setCurStepProgMax(numMoves);
			long l = 0;
			Iterator<ScrambleMove> it = this.key.getMovesIt();
			while(it.hasNext()){
				matrix.doScrambleMove(it.next());
				runResults.setCurStepProg(l++);
			}
		}
		end = System.currentTimeMillis();
		runResults.setElapsedTime(Step.DESCRAMBLING, start, end);

		runResults.setCurStep(Step.OUT_DESCRAMBLED_DATA);
		start = System.currentTimeMillis();
		LOGGER.info("Outputting descrambled data...");
		matrix = (M) matrix.getSubMatrix(new MatrixCoordinate(matrix,0,0), this.key.meta.originalHeight, this.key.meta.originalWidth);
		{
			long length = matrix.size();
			if(this.key.meta.lastColIndex == 0) {
				length -= matrix.getNumCols() - 1;
			}else if(this.key.meta.lastColIndex > 0){
				length -= key.meta.lastColIndex;
			}
			byte[] bytes = this.utils.getMatrixAsBytes(matrix, this.nodeType, length);
			runResults.setNumBytesOut(bytes.length);
			LOGGER.debug("Number of bytes to output: {}", bytes.length);
			this.dataOutput.write(bytes);
		}
		end = System.currentTimeMillis();
		runResults.setElapsedTime(Step.OUT_DESCRAMBLED_DATA, start, end);

		runResults.setCurStep(Step.DONE_DESCRAMBLING);
		LOGGER.info("Done descrambling data...");
	}
}
