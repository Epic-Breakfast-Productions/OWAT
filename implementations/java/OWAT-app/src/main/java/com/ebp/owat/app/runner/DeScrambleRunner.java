package com.ebp.owat.app.runner;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.Scrambler;
import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.Coordinate;
import com.ebp.owat.lib.datastructure.set.LongLinkedList;
import com.ebp.owat.lib.datastructure.value.NodeMode;
import com.ebp.owat.lib.datastructure.value.Value;
import com.ebp.owat.lib.utils.rand.OwatRandGenerator;
import com.ebp.owat.lib.utils.scramble.ScrambleMove;
import com.ebp.owat.lib.utils.scramble.key.ScrambleKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;

public class DeScrambleRunner<N extends Value, M extends Matrix<N> & Scrambler, R extends OwatRandGenerator> extends OwatRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(DeScrambleRunner.class);
	
	/** The key that will be used. */
	private ScrambleKey key;
	/** The type of data that will be used. */
	private NodeMode nodeType;
	/** The stream to use to read the scrambled data in. */
	private final InputStream dataInput;
	/** The stream to use to read the key in */
	private final InputStream keyInput;
	/** The stream to use to write the data out */
	private final OutputStream dataOutput;
	
	private RunnerUtilities<N, M, R> utils = new RunnerUtilities<>();
	
	private DeScrambleRunner(InputStream dataInput, InputStream keyInput, OutputStream dataOutput){
		if(dataInput == null){
			throw new IllegalArgumentException("Invalid null parameter(s) given. Must specify data to input.");
		}
		if(dataOutput == null){
			throw new IllegalArgumentException("Invalid null parameter(s) given. Must specify a method of outputting the descrambled data.");
		}
		if(keyInput == null) {
			throw new IllegalArgumentException("Invalid null parameter(s) given. Must specify a method of inputting the key data.");
		}
		
		this.dataInput = dataInput;
		this.keyInput = keyInput;
		this.dataOutput = dataOutput;
	}
	
	public static class Builder{
		/** The stream to use to read the scrambled data in. */
		private InputStream dataInput;
		/** The stream to use to read the key in */
		private InputStream keyInput;
		/** The stream to use to write the data out */
		private OutputStream dataOutput;
		
		private byte[] getByteArrFromString(String str){
			return str.getBytes(StandardCharsets.UTF_8);
		}
		
		public DeScrambleRunner.Builder setDataInput(InputStream is){
			this.dataInput = is;
			return this;
		}
		
		public DeScrambleRunner.Builder setDataInput(String data){
			return this.setDataInput(new ByteArrayInputStream(getByteArrFromString(data)));
		}
		
		public DeScrambleRunner.Builder setDataInput(File file) throws FileNotFoundException {
			return this.setDataInput(new FileInputStream(file));
		}
		
		public DeScrambleRunner.Builder setDataOutput(OutputStream os){
			this.dataOutput = os;
			return this;
		}
		
		public DeScrambleRunner.Builder setKeyInput(InputStream is){
			this.keyInput = is;
			return this;
		}
		
		public DeScrambleRunner build(){
			return new DeScrambleRunner(dataInput, keyInput, dataOutput);
		}
	}
	
	
	
	@Override
	public void doSteps() throws IOException {
		M matrix;
		
		this.setCurStep(Step.LOAD_KEY);
		LOGGER.info("Loading key...");
		this.key = OBJECT_MAPPER.readValue(this.keyInput, ScrambleKey.class);
		this.nodeType = this.key.meta.getNodeMode();
		
		this.setCurStep(Step.LOAD_SCRAMBLED_DATA);
		LOGGER.info("Loading scrambled data...");
		{
			LongLinkedList<Byte> data = this.utils.readDataIn(this.dataInput);
			LOGGER.debug("Length of scrambled data: {} bytes", data.sizeL());
			matrix = this.utils.getMatrix(data, this.nodeType, this.key.meta.dataHeight, this.key.meta.dataWidth);
		}
		
		this.setCurStep(Step.DESCRAMBLING);
		LOGGER.info("Descrambling data...");
		LOGGER.debug("Number of moves: {}", this.key.getNumMoves());
		{
			Iterator<ScrambleMove> it = this.key.getMovesIt();
			while(it.hasNext()){
				matrix.doScrambleMove(it.next());
			}
		}
		
		this.setCurStep(Step.OUT_DESCRAMBLED_DATA);
		LOGGER.info("Outputting descrambled data...");
		matrix = (M) matrix.getSubMatrix(new Coordinate(matrix,0,0), this.key.meta.originalHeight, this.key.meta.originalWidth);
		{
			byte[] bytes = this.utils.getMatrixAsBytes(matrix, this.nodeType);
			if(this.key.meta.lastColIndex != -1){
				bytes = Arrays.copyOfRange(bytes, 0, bytes.length - (int)(matrix.getNumCols() - this.key.meta.lastColIndex + 1));
			}
			LOGGER.debug("Number of bytes to output: {}", bytes.length);
			this.dataOutput.write(bytes);
		}
		
		this.setCurStep(Step.DONE_DESCRAMBLING);
		LOGGER.info("Done descrambling data...");
	}
}
