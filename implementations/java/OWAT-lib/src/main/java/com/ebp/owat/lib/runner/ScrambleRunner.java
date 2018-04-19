package com.ebp.owat.lib.runner;

import com.ebp.owat.lib.runner.utils.*;
import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.Scrambler;
import com.ebp.owat.lib.datastructure.set.LongLinkedList;
import com.ebp.owat.lib.datastructure.value.NodeMode;
import com.ebp.owat.lib.datastructure.value.Value;
import com.ebp.owat.lib.utils.key.ScrambleKey;
import com.ebp.owat.lib.utils.rand.OwatRandGenerator;
import com.ebp.owat.lib.utils.rand.RandGenerator;
import com.ebp.owat.lib.utils.rand.ThreadLocalRandGenerator;
import com.ebp.owat.lib.utils.scramble.ScrambleMove;
import com.ebp.owat.lib.utils.scramble.generator.ScrambleMoveGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.SecureRandom;
import java.util.Base64;

import static com.ebp.owat.lib.runner.utils.RunnerUtilities.getByteArrFromString;

/**
 * Runner for scrambling data.
 * @param <N> The type of value to use
 * @param <M> The type of matrix to use
 * @param <R> The random number generator to use
 */
public class ScrambleRunner<N extends Value, M extends Matrix<N> & Scrambler, R extends OwatRandGenerator> extends OwatRunner<N,M,R> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ScrambleRunner.class);
	private static final java.util.Base64.Encoder ENCODER = Base64.getEncoder();

	/**
	 * The default mode of creating a matrix.
	 */
	protected static final NodeMode DEFAULT_MODE = NodeMode.BIT;
	
	/** The random number generator to use. */
	private R rand;
	/** The key that will be used. */
	private ScrambleKey key;
	/** The type of matrix to use. */
	private MatrixMode matrixMode;
	/** The type of data that will be used. */
	private final NodeMode nodeType;
	/** The stream to use to read the original data in. */
	private final InputStream dataInput;
	/** The stream to use to write the data out */
	private final OutputStream dataOutput;
	/** The stream to use to write the key out */
	private final OutputStream keyOutput;
	/** The minimum number of steps */
	private long minNumScrambleSteps = -1;

	/**
	 * Constructor to setup the runner. To be called by the builder.
	 * @param rand The random number generator to use.
	 * @param dataInput The data to scramble.
	 * @param dataOutput The data stream to output the scrambled data to.
	 * @param keyOutput The data stream to output the key to.
	 * @param matrixMode The type of matrix to use.
	 * @param nodeType The type of node to use in the matrix.
	 */
	private ScrambleRunner(
		R rand,
		InputStream dataInput,
		OutputStream dataOutput,
		OutputStream keyOutput,
		MatrixMode matrixMode,
		NodeMode nodeType
	){
		if(rand == null){
			throw new IllegalArgumentException("Invalid null parameter(s) given. Must specify a random number generator or seed.");
		}
		if(dataInput == null){
			throw new IllegalArgumentException("Invalid null parameter(s) given. Must specify data to input.");
		}
		if(dataOutput == null){
			throw new IllegalArgumentException("Invalid null parameter(s) given. Must specify a method of outputting the scrambled data.");
		}
		if(keyOutput == null) {
			throw new IllegalArgumentException("Invalid null parameter(s) given. Must specify a method of outputting the key data.");
		}
		if(nodeType == null){
			throw new IllegalArgumentException("Invalid null parameter(s) given. Must specify what node type to use.");
		}
		
		this.rand = rand;
		this.dataInput = dataInput;
		this.dataOutput = dataOutput;
		this.keyOutput = keyOutput;
		this.matrixMode = matrixMode;
		this.nodeType = nodeType;
	}

	/**
	 * Sets the minimum number of scramble steps to use.
	 * @param num The number to use.
	 */
	private void setMinNumScrambleSteps(long num){
		this.minNumScrambleSteps = num;
	}

	/**
	 * Builder to setup the scramble runner.
	 * @param <N> The type of value to use
	 * @param <M> The type of matrix to use
	 * @param <R> The random number generator to use
	 */
	public static class Builder<N extends Value, M extends Matrix<N> & Scrambler, R extends OwatRandGenerator> {
		/** The random number generator to use. */
		private R rand = (R)new ThreadLocalRandGenerator();
		/** The type of matrix to use. */
		private MatrixMode matrixMode = null;
		/** The type of data that will be used. */
		private NodeMode nodeType = DEFAULT_MODE;
		/** The stream to use to read the original data in. */
		private InputStream dataInput = null;
		/** The stream to use to write the data out */
		private OutputStream dataOutput = null;
		/** The stream to use to write the key out */
		private OutputStream keyOutput = null;
		/** The minimum number of steps */
		private long minNumScrambleSteps = -1;

		/**
		 * Sets the random number generator using the seed given.
		 * @param seed The seed to use to make the random number generator.
		 * @return This builder, for chaining setter calls.
		 */
		public Builder setRand(byte[] seed){
			this.rand = (R)new RandGenerator(new SecureRandom(seed));
			return this;
		}

		/**
		 * Sets the random number generator using the seed given.
		 * @param seed The seed to use to make the random number generator.
		 * @return This builder, for chaining setter calls.
		 */
		public Builder setRand(String seed){
			return this.setRand(getByteArrFromString(seed));
		}

		/**
		 * Sets the random number generator to use.
		 * @param rand The random number generator to use.
		 * @return This builder, for chaining setter calls.
		 */
		public Builder setRand(R rand){
			this.rand = rand;
			return this;
		}

		/**
		 * Sets the type of matrix to use.
		 * @param matrixMode The type of matrix to use.
		 * @return This builder, for chaining setter calls.
		 */
		public Builder setMatrixMode(MatrixMode matrixMode){
			this.matrixMode = matrixMode;
			return this;
		}

		/**
		 * Sets the type of node to use.
		 * @param nodeType The type of node to use.
		 * @return This builder, for chaining setter calls.
		 */
		public Builder setNodeType(NodeMode nodeType){
			this.nodeType = nodeType;
			return this;
		}

		/**
		 * Sets the input stream of data to scramble.
		 * @param is The input stream to use.
		 * @return This builder, for chaining setter calls.
		 */
		public Builder setDataInput(InputStream is){
			this.dataInput = is;
			return this;
		}

		/**
		 * Sets the data to scramble.
		 * @param data The data to scramble.
		 * @return This builder, for chaining setter calls.
		 */
		public Builder setDataInput(String data){
			return this.setDataInput(new ByteArrayInputStream(getByteArrFromString(data)));
		}

		/**
		 * Sets the data to scramble from a file.
		 *
		 * TODO:: change the file validation to this
		 *
		 * @param file The file to use.
		 * @return This builer, for chaining setter calls.
		 * @throws FileNotFoundException
		 */
		public Builder setDataInput(File file) throws FileNotFoundException {
			return this.setDataInput(new FileInputStream(file));
		}

		/**
		 * Sets the scrambled data output to the one given.
		 *
		 * @param os The output stream to use.
		 * @return This builder, for chaining setter calls.
		 */
		public Builder setDataOutput(OutputStream os){
			this.dataOutput = os;
			return this;
		}

		//TODO:: make setDataOutput(File)

		/**
		 * Sets the output stream to use for outputting the key.
		 * @param os The output stream to use.
		 * @return This builder, for chaining calls.
		 */
		public Builder setKeyOutput(OutputStream os){
			this.keyOutput = os;
			return this;
		}

		//TODO:: make setKeyOutput(File)

		/**
		 * Sets the minimum number of scramble steps to use.
		 * @param num The minimum number of steps to use.
		 * @return This builder, for chaining setter calls.
		 */
		public Builder setMinNumScrambleSteps(long num){
			this.minNumScrambleSteps = num;
			return this;
		}
		
		/**
		 * Builds the runner with the data given.
		 * @return The runner setup with the data given.
		 */
		public ScrambleRunner build(){
			ScrambleRunner<N, M, R> runner =  new ScrambleRunner<>(
				this.rand,
				this.dataInput,
				this.dataOutput,
				this.keyOutput,
				this.matrixMode,
				this.nodeType
			);

			runner.setMinNumScrambleSteps(this.minNumScrambleSteps);
			
			return runner;
		}
	}
	
	@Override
	public void doSteps() throws IOException {
		RunResults runResults = new RunResults(ScrambleMode.SCRAMBLING, this.nodeType);
		this.setLastRunResults(runResults);

		long start, end;
		M matrix;
		
		{
			runResults.setCurStep(Step.LOAD_DATA);
			start = System.currentTimeMillis();
			LOGGER.info("Loading data...");
			
			LongLinkedList<Byte> data = this.utils.readDataIn(this.dataInput);

			if(this.matrixMode == null){
				this.matrixMode = MatrixMode.determineModeToUse(data.sizeL());
			}

			runResults.setMatrixMode(this.matrixMode);

			LOGGER.debug("Length of original data: {} bytes", data.sizeL());
			LOGGER.debug("Using matrix type: {}", this.matrixMode.name);
			matrix = this.utils.getMatrix(data, this.matrixMode, this.nodeType);

			LOGGER.debug("Size of matrix with just original data: {}rows x {}cols, {} values", matrix.getHeight(), matrix.getWidth(), matrix.numElements());

			long origDataHeight = matrix.getNumRows();
			long origDataWidth = matrix.getNumCols();

			long lastRowIndex;
			if(matrix.isFull()){
				lastRowIndex = -1;
			}else{
				lastRowIndex = matrix.size() - matrix.numElements();
				//lastRowIndex = matrix.getNumRows() - lastRowIndex - 1;
			}
			end = System.currentTimeMillis();
			runResults.setElapsedTime(Step.LOAD_DATA, start, end);

			runResults.setCurStep(Step.PAD_DATA);
			start = System.currentTimeMillis();
			LOGGER.info("Padding data...");

			this.utils.padMatrix(matrix, this.rand, this.nodeType);
			LOGGER.debug("Size of matrix: {}rows x {}cols, {} values", matrix.getNumRows(), matrix.getNumCols(), matrix.size());
			this.key = new ScrambleKey(origDataHeight, origDataWidth, matrix.getNumRows(), matrix.getNumCols(), this.nodeType.typeClass, lastRowIndex);

			end = System.currentTimeMillis();
			runResults.setElapsedTime(Step.PAD_DATA, start, end);
		}

		runResults.setCurStep(Step.SCRAMBLING);
		start = System.currentTimeMillis();
		LOGGER.info("Scrambling Data...");
		
		if(this.minNumScrambleSteps < 0){
			this.minNumScrambleSteps = this.utils.determineMinStepsToTake(matrix, this.rand);
		}

		long numSteps = this.utils.determineNumStepsToTake(matrix, this.rand, this.minNumScrambleSteps);
		LOGGER.debug("Number of steps in scramble: {}", numSteps);
		runResults.setCurStepProgMax(numSteps);
		ScrambleMoveGenerator generator = new ScrambleMoveGenerator(this.rand, matrix);
		for(long l = 0; l < numSteps; l++){
			ScrambleMove curMove = generator.getMove();
			matrix.doScrambleMove(curMove);
			this.key.addMove(curMove);
			runResults.setCurStepProg(l);
		}

		end = System.currentTimeMillis();
		runResults.setElapsedTime(Step.SCRAMBLING, start, end);

		runResults.setCurStep(Step.OUT_SCRAMBLED_DATA);
		start = System.currentTimeMillis();
		LOGGER.info("Outputting scrambled data...");
		{
			byte[] bytes = ENCODER.encode(this.utils.getMatrixAsBytes(matrix, this.nodeType));
			LOGGER.debug("Number of bytes to output: {}", bytes.length);
			this.dataOutput.write(bytes);
		}
		end = System.currentTimeMillis();
		runResults.setElapsedTime(Step.OUT_SCRAMBLED_DATA, start, end);

		runResults.setCurStep(Step.OUT_KEY);
		start = System.currentTimeMillis();
		LOGGER.info("Outputting key...");

		{
			byte compressedKey[] = utils.compressBytes(OBJECT_MAPPER.writeValueAsBytes(this.key));

			this.keyOutput.write(compressedKey);
		}

		end = System.currentTimeMillis();
		runResults.setElapsedTime(Step.OUT_KEY, start, end);

		runResults.setCurStep(Step.DONE_SCRAMBLING);
		LOGGER.info("Done Scrambling.");
	}
}
