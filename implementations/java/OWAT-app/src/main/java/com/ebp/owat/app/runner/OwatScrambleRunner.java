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
import com.ebp.owat.lib.utils.scramble.key.ScrambleKey;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.Iterator;

public class OwatScrambleRunner<N extends Value, M extends Matrix<N> & Scrambler> extends OwatRunner {
	/** The random number generator to use. */
	private OwatRandGenerator rand;
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
	
	private OwatScrambleRunner(byte[] randSeed, InputStream dataInput, OutputStream dataOutput, OutputStream keyOutput, NodeMode nodeType){
		if(
			dataInput == null ||
				dataOutput == null ||
				keyOutput == null ||
				nodeType == null
			){
			throw new IllegalArgumentException("Invalid null parameters given.");
		}
		
		this.rand = new RandGenerator(new SecureRandom(randSeed));
		this.dataInput = dataInput;
		this.dataOutput = dataOutput;
		this.keyOutput = keyOutput;
		this.nodeType = nodeType;
	}
	
	public class Builder {
		/** The random number generator to use. */
		private byte[] randSeed = null;
		/** The type of data that will be used. */
		private NodeMode nodeType = NodeMode.BIT;
		/** The stream to use to read the original data in. */
		private InputStream dataInput = null;
		/** The stream to use to write the data out */
		private OutputStream dataOutput = null;
		/** The stream to use to write the key out */
		private OutputStream keyOutput = null;
		
		private byte[] getByteArrFromString(String str){
			return str.getBytes(StandardCharsets.UTF_8);
		}
		
		public Builder setRandSeed(byte[] seed){
			this.randSeed = seed;
			return this;
		}
		
		public Builder setRandSeed(String seed){
			return this.setRandSeed(getByteArrFromString(seed));
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
		
		/**
		 * Builds the runner with the data given.
		 * @return The runner setup with the data given.
		 */
		public OwatScrambleRunner build(){
			return new OwatScrambleRunner(
				this.randSeed,
				this.dataInput,
				this.dataOutput,
				this.keyOutput,
				this.nodeType
			);
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
	
	
	@SuppressWarnings("unchecked")
	private M getBitMatrix(LongLinkedList<Byte> data){
		M matrix = (M) new HashedScramblingMatrix<BitValue>();
		
		Iterator<Byte> it = data.destructiveIterator();
		LongLinkedList<BitValue> bitValues = new LongLinkedList<>();
		
		while (it.hasNext()){
			byte curByte = it.next();
			bitValues.addAll(BitValue.fromByte(curByte, true));
		}
		
		matrix.grow((Collection<N>) bitValues);
		
		return matrix;
	}
	
	@SuppressWarnings("unchecked")
	private M getByteMatrix(LongLinkedList<Byte> data){
		M matrix = (M) new HashedScramblingMatrix<ByteValue>();
		
		Iterator<Byte> it = data.destructiveIterator();
		LongLinkedList<ByteValue> byteValues = new LongLinkedList<>();
		
		while (it.hasNext()){
			byte curByte = it.next();
			byteValues.addLast(new ByteValue(curByte,true));
		}
		
		matrix.grow((Collection<N>) byteValues);
		
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
		//TODO
	}
	
	
	
	public void doScramble() throws IOException {
		M matrix;
		//TODO
		{
			this.setCurStep(Step.LOAD_DATA);
			LongLinkedList<Byte> data = this.readDataIn();
			
			//TODO:: into matrix
			matrix = this.getMatrix(data);
			
			
			long origDataHeight = matrix.getNumRows();
			long origDataWidth = matrix.getNumCols();
			
			//TODO:: pad data
			
			
			long dataHeight;
			long dataWidth;
			
			//TODO:: build key
			this.key = new ScrambleKey(origDataHeight, origDataWidth, matrix.getNumRows(), matrix.getNumCols(), this.nodeType.typeClass);
		}
		
		//TODO:: SCRAMBLE
		
		//TODO:: write out data
		
		//TODO:: write out key
	}
}
