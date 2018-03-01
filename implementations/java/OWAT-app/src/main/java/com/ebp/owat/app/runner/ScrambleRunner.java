package com.ebp.owat.app.runner;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.Scrambler;
import com.ebp.owat.lib.datastructure.set.LongLinkedList;
import com.ebp.owat.lib.datastructure.value.NodeMode;
import com.ebp.owat.lib.datastructure.value.Value;
import com.ebp.owat.lib.utils.rand.OwatRandGenerator;
import com.ebp.owat.lib.utils.rand.RandGenerator;
import com.ebp.owat.lib.utils.rand.ThreadLocalRandGenerator;
import com.ebp.owat.lib.utils.scramble.ScrambleMove;
import com.ebp.owat.lib.utils.scramble.generator.ScrambleMoveGenerator;
import com.ebp.owat.lib.utils.scramble.key.ScrambleKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.Instant;

public class ScrambleRunner<N extends Value, M extends Matrix<N> & Scrambler, R extends OwatRandGenerator> extends OwatRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(ScrambleRunner.class);
	
	/** The random number generator to use. */
	private R rand;
	/** The key that will be used. */
	private ScrambleKey key;
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
	/** The maximum number of steps */
	private long maxNumScrambleSteps = -1;
	
	private RunnerUtilities<N, M, R> utils = new RunnerUtilities<>();
	
	private ScrambleRunner(R rand, InputStream dataInput, OutputStream dataOutput, OutputStream keyOutput, NodeMode nodeType){
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
		this.nodeType = nodeType;
	}
	
	private void setMinNumScrambleSteps(long num){
		this.minNumScrambleSteps = num;
	}
	
	private void setMaxNumScrambleSteps(long num){
		this.maxNumScrambleSteps = num;
	}
	
	public static class Builder<N extends Value, M extends Matrix<N> & Scrambler, R extends OwatRandGenerator> {
		/** The random number generator to use. */
		private R rand = (R)new ThreadLocalRandGenerator();
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
		/** The maximum number of steps */
		private long maxNumScrambleSteps = -1;
		
		private byte[] getByteArrFromString(String str){
			return str.getBytes(StandardCharsets.UTF_8);
		}
		
		public Builder setRand(byte[] seed){
			this.rand = (R)new RandGenerator(new SecureRandom(seed));
			return this;
		}
		
		public Builder setRand(String seed){
			return this.setRand(getByteArrFromString(seed));
		}
		
		public Builder setRand(R rand){
			this.rand = rand;
			return this;
		}
		
		public Builder setNodeType(NodeMode nodeType){
			this.nodeType = nodeType;
			return this;
		}
		
		public Builder setDataInput(InputStream is){
			this.dataInput = is;
			return this;
		}
		
		public Builder setDataInput(String data){
			return this.setDataInput(new ByteArrayInputStream(getByteArrFromString(data)));
		}
		
		public Builder setDataInput(File file) throws FileNotFoundException {
			return this.setDataInput(new FileInputStream(file));
		}
		
		public Builder setDataOutput(OutputStream os){
			this.dataOutput = os;
			return this;
		}
		
		public Builder setKeyOutput(OutputStream os){
			this.keyOutput = os;
			return this;
		}
		
		public Builder setMinNumScrambleSteps(long num){
			this.minNumScrambleSteps = num;
			return this;
		}
		
		public Builder setMaxNumScrambleSteps(long num){
			this.maxNumScrambleSteps = num;
			return this;
		}
		
		/**
		 * Builds the runner with the data given.
		 * @return The runner setup with the data given.
		 */
		public ScrambleRunner build(){
			ScrambleRunner runner =  new ScrambleRunner(
				this.rand,
				this.dataInput,
				this.dataOutput,
				this.keyOutput,
				this.nodeType
			);
			
			runner.setMaxNumScrambleSteps(this.maxNumScrambleSteps);
			runner.setMinNumScrambleSteps(this.minNumScrambleSteps);
			
			return runner;
		}
	}
	
	@Override
	public void doSteps() throws IOException {
		this.resetTiming();
		long start, end;
		M matrix;
		
		{
			this.setCurStep(Step.LOAD_DATA);
			start = System.currentTimeMillis();
			LOGGER.info("Loading data...");
			
			LongLinkedList<Byte> data = this.utils.readDataIn(this.dataInput);
			LOGGER.debug("Length of original data: {} bytes", data.sizeL());
			matrix = this.utils.getMatrix(data, this.nodeType);
			
			long origDataHeight = matrix.getNumRows();
			long origDataWidth = matrix.getNumCols();
			
			LOGGER.debug("Size of matrix with just original data: {}rows x {}cols", origDataHeight, origDataWidth);
			
			long lastRowIndex;
			if(matrix.isFull()){
				lastRowIndex = -1;
			}else{
				long numLeftInLastRow = matrix.size() - matrix.numElements();
				lastRowIndex = matrix.getNumRows() - numLeftInLastRow - 1;
			}
			end = System.currentTimeMillis();
			this.setElapsedTime(Step.LOAD_DATA, start, end);

			this.setCurStep(Step.PAD_DATA);
			start = System.currentTimeMillis();
			LOGGER.info("Padding data...");

			this.utils.padMatrix(matrix, this.rand, this.nodeType);
			LOGGER.debug("Size of matrix: {}rows x {}cols", matrix.getNumRows(), matrix.getNumCols());
			this.key = new ScrambleKey(origDataHeight, origDataWidth, matrix.getNumRows(), matrix.getNumCols(), this.nodeType.typeClass, lastRowIndex);

			end = System.currentTimeMillis();
			this.setElapsedTime(Step.PAD_DATA, start, end);
		}
		
		this.setCurStep(Step.SCRAMBLING);
		start = System.currentTimeMillis();
		LOGGER.info("Scrambling Data...");
		
		if(this.minNumScrambleSteps < 0){
			this.minNumScrambleSteps = this.utils.determineMinStepsToTake(matrix, this.rand);
		}
		if(this.maxNumScrambleSteps < 0 || this.maxNumScrambleSteps <= this.minNumScrambleSteps) {
			this.maxNumScrambleSteps = this.utils.determineMaxStepsToTake(matrix, this.rand, this.minNumScrambleSteps);
		}
		
		long numSteps = this.rand.nextLong(this.minNumScrambleSteps, this.maxNumScrambleSteps);
		LOGGER.debug("Number of steps in scramble: {} (chosen from a range from {} to {})", numSteps, this.minNumScrambleSteps, this.maxNumScrambleSteps);
		ScrambleMoveGenerator generator = new ScrambleMoveGenerator(this.rand, matrix);
		for(long l = 0; l < numSteps; l++){
			ScrambleMove curMove = generator.getMove();
			matrix.doScrambleMove(curMove);
			this.key.addMove(curMove);
		}

		end = System.currentTimeMillis();
		this.setElapsedTime(Step.SCRAMBLING, start, end);
		
		this.setCurStep(Step.OUT_SCRAMBLED_DATA);
		start = System.currentTimeMillis();
		LOGGER.info("Outputting scrambled data...");
		{
			byte[] bytes = this.utils.getMatrixAsBytes(matrix, this.nodeType);
			LOGGER.debug("Number of bytes to output: {}", bytes.length);
			this.dataOutput.write(bytes);
		}
		end = System.currentTimeMillis();
		this.setElapsedTime(Step.OUT_SCRAMBLED_DATA, start, end);
		
		this.setCurStep(Step.OUT_KEY);
		start = System.currentTimeMillis();
		LOGGER.info("Outputting key...");
		
		this.keyOutput.write(OBJECT_MAPPER.writeValueAsBytes(this.key));

		end = System.currentTimeMillis();
		this.setElapsedTime(Step.OUT_KEY, start, end);

		this.setCurStep(Step.DONE_SCRAMBLING);
		LOGGER.info("Done Scrambling.");
	}
}
