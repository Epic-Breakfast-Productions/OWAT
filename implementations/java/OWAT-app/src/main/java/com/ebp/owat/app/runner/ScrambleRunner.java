package com.ebp.owat.app.runner;

import com.ebp.owat.lib.datastructure.matrix.Hash.HashedScramblingMatrix;
import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.Scrambler;
import com.ebp.owat.lib.datastructure.set.LongLinkedList;
import com.ebp.owat.lib.datastructure.value.BitValue;
import com.ebp.owat.lib.datastructure.value.ByteValue;
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
import java.util.Iterator;

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
		private NodeMode nodeType = NodeMode.BIT;
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
	
	private LongLinkedList<Byte> readDataIn() throws IOException {
		LongLinkedList<Byte> output = new LongLinkedList<>();
		
		try{
			byte curByte = (byte)this.dataInput.read();
			
			while (curByte != -1){
				output.addLast(curByte);
				curByte = (byte)this.dataInput.read();
			}
		}finally {
			if(this.dataInput != null){
				this.dataInput.close();
			}
		}
		return output;
	}
	
	
	private void fillMatrixWithOriginalData(M emptyMatrix, LongLinkedList values){
		emptyMatrix.grow(values);
	}
	
	
	@SuppressWarnings("unchecked")
	private M getBitMatrix(LongLinkedList<Byte> data){
		M matrix = (M) new HashedScramblingMatrix<BitValue>();
		
		matrix.setDefaultValue((N) new BitValue(false, false));
		
		Iterator<Byte> it = data.destructiveIterator();
		LongLinkedList<BitValue> bitValues = new LongLinkedList<>();
		
		while (it.hasNext()){
			byte curByte = it.next();
			bitValues.addAll(BitValue.fromByte(curByte, true));
		}
		
		this.fillMatrixWithOriginalData(matrix, bitValues);
		
		return matrix;
	}
	
	@SuppressWarnings("unchecked")
	private M getByteMatrix(LongLinkedList<Byte> data){
		M matrix = (M) new HashedScramblingMatrix<ByteValue>();
		
		matrix.setDefaultValue((N) new ByteValue((byte)0, false));
		
		Iterator<Byte> it = data.destructiveIterator();
		LongLinkedList<ByteValue> byteValues = new LongLinkedList<>();
		
		while (it.hasNext()){
			byte curByte = it.next();
			byteValues.addLast(new ByteValue(curByte,true));
		}
		
		this.fillMatrixWithOriginalData(matrix, byteValues);
		
		return matrix;
	}
	
	private M getMatrix(LongLinkedList<Byte> data){
		switch (this.nodeType){
			case BIT:
				return this.getBitMatrix(data);
			case BYTE:
				return this.getByteMatrix(data);
			default:
				throw new IllegalStateException();
		}
	}
	
	private void padMatrix(M matrix){
		//TODO:: fill last bits of end row if need be
		//TODO:: throw random data (actually pad the matrix)
		//TODO:: if a bit matrix, ensure size() is divisible by 8 to ensure proper serialization.
	}
	
	private void determineMinStepsToTake(M matrix){
		long sizeSquared = (long)Math.pow(matrix.size(), 2);
		this.minNumScrambleSteps = this.rand.nextLong(
			sizeSquared, sizeSquared + matrix.size()
		);
	}
	private void determineMaxStepsToTake(M matrix){
		long tempMin = this.minNumScrambleSteps + matrix.size();
		long tempMax = tempMin * 2L;
		this.maxNumScrambleSteps = this.rand.nextLong(tempMin, tempMax);
	}
	
	private byte[] getMatrixAsBytes(M matrix){
		byte[] bytes;
		if(this.nodeType == NodeMode.BIT){
			Iterator<BitValue> bitIt = (Iterator<BitValue>) matrix.iterator();
			
			LongLinkedList<BitValue> tempBits;
			LongLinkedList<Byte> tempBytes = new LongLinkedList<>();
			
			while (bitIt.hasNext()){
				tempBits = new LongLinkedList<>();
				for(short i = 0; i < 8; i++){
					tempBits.addLast(bitIt.next());
				}
				tempBytes.addLast(BitValue.toByte(tempBits));
			}
			
			bytes = new byte[tempBytes.size()];
			Iterator<Byte> it = tempBytes.destructiveIterator();
			for(int i = 0; i < bytes.length; i++){
				bytes[i] = it.next();
			}
		}else if(this.nodeType == NodeMode.BYTE){
			bytes = new byte[(int)matrix.size()];
			int i = 0;
			Iterator<ByteValue> it = (Iterator<ByteValue>) matrix.iterator();
			while (it.hasNext()){
				bytes[i] = it.next().getValue();
				i++;
			}
		}else{
			throw new IllegalStateException();
		}
		return bytes;
	}
	
	@Override
	public void doSteps() throws IOException {
		M matrix;
		
		{
			this.setCurStep(Step.LOAD_DATA);
			LongLinkedList<Byte> data = this.readDataIn();
			
			matrix = this.getMatrix(data);
			
			long origDataHeight = matrix.getNumRows();
			long origDataWidth = matrix.getNumCols();
			
			long lastRowIndex;
			if(matrix.isFull()){
				lastRowIndex = -1;
			}else{
				long numLeftInLastRow = matrix.size() - matrix.numElements();
				lastRowIndex = matrix.getNumRows() - numLeftInLastRow - 1;
			}
			
			this.setCurStep(Step.PAD_DATA);
			this.padMatrix(matrix);
			
			this.key = new ScrambleKey(origDataHeight, origDataWidth, matrix.getNumRows(), matrix.getNumCols(), this.nodeType.typeClass, lastRowIndex);
		}
		
		this.setCurStep(Step.SCRAMBLING);
		
		if(this.minNumScrambleSteps < 0){
			this.determineMinStepsToTake(matrix);
		}
		if(this.maxNumScrambleSteps < 0 || this.maxNumScrambleSteps <= this.minNumScrambleSteps) {
			this.determineMaxStepsToTake(matrix);
		}
		
		long numSteps = this.rand.nextLong(this.minNumScrambleSteps, this.maxNumScrambleSteps);
		ScrambleMoveGenerator generator = new ScrambleMoveGenerator(this.rand, matrix);
		for(long l = 0; l < numSteps; l++){
			ScrambleMove curMove = generator.getMove();
			matrix.doScrambleMove(curMove);
			this.key.addMove(curMove);
		}
		
		this.setCurStep(Step.OUT_SCRAMBLED_DATA);
		
		{
			byte[] bytes = this.getMatrixAsBytes(matrix);
			
			this.dataOutput.write(bytes);
		}
		
		this.setCurStep(Step.OUT_KEY);
		
		this.keyOutput.write(OBJECT_MAPPER.writeValueAsBytes(this.key));
		
		this.setCurStep(Step.DONE_SCRAMBLING);
	}
}
