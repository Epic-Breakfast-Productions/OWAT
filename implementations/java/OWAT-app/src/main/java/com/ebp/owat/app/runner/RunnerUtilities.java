package com.ebp.owat.app.runner;

import com.ebp.owat.lib.datastructure.matrix.Hash.HashedScramblingMatrix;
import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.Scrambler;
import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.Coordinate;
import com.ebp.owat.lib.datastructure.set.LongLinkedList;
import com.ebp.owat.lib.datastructure.value.BitValue;
import com.ebp.owat.lib.datastructure.value.ByteValue;
import com.ebp.owat.lib.datastructure.value.NodeMode;
import com.ebp.owat.lib.datastructure.value.Value;
import com.ebp.owat.lib.utils.rand.OwatRandGenerator;
import com.ebp.owat.lib.utils.scramble.MoveValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

public class RunnerUtilities<N extends Value, M extends Matrix<N> & Scrambler, R extends OwatRandGenerator> {
	private static final Logger LOGGER = LoggerFactory.getLogger(RunnerUtilities.class);
	
	public RunnerUtilities(){
	
	}

	/**
	 * Reads the data in from they input stream. Closes stream when done.
	 * @param dataInput The stream to get the data from.
	 * @return A list of the data read in.
	 * @throws IOException If something goes wrong with the read.
	 */
	public LongLinkedList<Byte> readDataIn(InputStream dataInput) throws IOException {
		LongLinkedList<Byte> output = new LongLinkedList<>();
		
		try{
			byte curByte = (byte)dataInput.read();
			
			while (curByte != -1){
				output.addLast(curByte);
				curByte = (byte)dataInput.read();
			}
		}finally {
			if(dataInput != null){
				dataInput.close();
			}
		}
		return output;
	}
	
	private void fillMatrixWithData(M emptyMatrix, LongLinkedList values, long height, long width){
		if(height < 1 || width < 1){
			emptyMatrix.grow(values);
		}else{
			emptyMatrix.grow(height, width, values);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	private M getBitMatrix(LongLinkedList<Byte> data, long height, long width){
		M matrix = (M) new HashedScramblingMatrix<BitValue>();
		
		Iterator<Byte> it = data.destructiveIterator();
		LongLinkedList<BitValue> bitValues = new LongLinkedList<>();
		
		while (it.hasNext()){
			byte curByte = it.next();
			bitValues.addAll(BitValue.fromByte(curByte, true));
		}
		this.fillMatrixWithData(matrix, bitValues, height, width);
		
		return matrix;
	}
	
	@SuppressWarnings("unchecked")
	private M getByteMatrix(LongLinkedList<Byte> data, long height, long width){
		M matrix = (M) new HashedScramblingMatrix<ByteValue>();
		
		Iterator<Byte> it = data.destructiveIterator();
		LongLinkedList<ByteValue> byteValues = new LongLinkedList<>();
		
		while (it.hasNext()){
			byte curByte = it.next();
			byteValues.addLast(new ByteValue(curByte,true));
		}
		
		this.fillMatrixWithData(matrix, byteValues, height, width);
		
		return matrix;
	}

	/**
	 * Gets a matrix built with the data given.
	 * @param data The data read in.
	 * @param nodeType The type of node to use.
	 * @param height The height of the matrix to make. -1 for Automatic.
	 * @param width The width of the matrix to make. -1 for Automatic.
	 * @return A matrix built with the data given.
	 */
	public M getMatrix(LongLinkedList<Byte> data, NodeMode nodeType, long height, long width){
		switch (nodeType){
			case BIT:
				LOGGER.debug("Matrix set to Bit nodes.");
				return this.getBitMatrix(data, height, width);
			case BYTE:
				LOGGER.debug("Matrix set to Bit nodes.");
				return this.getByteMatrix(data, height, width);
			default:
				throw new IllegalStateException();
		}
	}

	/**
	 * Gets a matrix built with the data given, automatically setting height/width.
	 * @param data The data read in.
	 * @param nodeType The type of node to use.
	 * @return A matrix built with the data given.
	 */
	public M getMatrix(LongLinkedList<Byte> data, NodeMode nodeType) {
		return getMatrix(data, nodeType, -1, -1);
	}

	public LongLinkedList<N> getListOfValues(long numValues, OwatRandGenerator rand, NodeMode nodeType){
		LongLinkedList<N> output = new LongLinkedList<>();
		
		if(nodeType == NodeMode.BIT){
			for(long i = 0; i < numValues; i++){
				output.add((N) new BitValue(rand.nextBool(), false));
			}
		}else if(nodeType == NodeMode.BYTE){
			for(long i = 0; i < numValues; i++){
				output.add((N) new ByteValue(rand.nextByte(), false));
			}
		}
		
		return output;
	}
	
	public void addRandRowOrCol(M matrix, OwatRandGenerator rand, NodeMode nodeType){
		if(rand.nextBool()){
			matrix.addRow();
			List<N> newVals =  this.getListOfValues(matrix.getNumCols(), rand, nodeType);

			matrix.replaceRow(matrix.getNumRows() - 1, newVals);
		}else{
			matrix.addCol();
			List<N> newVals =  this.getListOfValues(matrix.getNumRows(), rand, nodeType);

			matrix.replaceCol(matrix.getNumCols() -1, newVals);
		}
	}
	
	public void padMatrix(M matrix, OwatRandGenerator rand, NodeMode nodeType){
		if(!matrix.isFull()) {
			Coordinate curPos = new Coordinate(matrix, matrix.getWidth() - 1, matrix.getHeight() - 1);
			do{
				if(matrix.hasValue(curPos)){
					throw new IllegalStateException("Could not fill empty part of matrix; Hit a value but still not full.");
				}
				switch (nodeType){
					case BIT:
						matrix.setValue(curPos, (N) new BitValue(rand.nextBool(), false));
						break;
					case BYTE:
						matrix.setValue(curPos, (N) new ByteValue(rand.nextByte(), false));
						break;
				}

				if(curPos.getCol() == 0){
					curPos.setX(matrix.getWidth() - 1);
					curPos.setY(curPos.getY() - 1);
				}else{
					curPos.setX(curPos.getX() - 1);
				}

			}while(!matrix.isFull());
		}

		while (matrix.getNumCols() < MoveValidator.MIN_SIZE_FOR_SCRAMBLING){
			matrix.addCol();
			matrix.replaceCol(matrix.getNumCols() -1, this.getListOfValues(matrix.getNumRows(), rand, nodeType));
		}
		while (matrix.getNumRows() < MoveValidator.MIN_SIZE_FOR_SCRAMBLING){
			matrix.addRow();
			matrix.replaceRow(matrix.getNumRows() -1, this.getListOfValues(matrix.getNumCols(), rand, nodeType));
		}
		
		long numRowsColsToGenerate = matrix.size();
		LOGGER.debug("Adding a total of {} rows and columns of dummy data to the matrix.", numRowsColsToGenerate);
		for(long i = 0; i < numRowsColsToGenerate; i++){
			addRandRowOrCol(matrix, rand, nodeType);
		}
		
		//ensure that bit marices can be serialized properly
		if(nodeType == NodeMode.BIT){
			while (matrix.size() % 8 != 0){
				addRandRowOrCol(matrix, rand, nodeType);
			}
		}
	}
	
	public long determineMinStepsToTake(M matrix, OwatRandGenerator rand){
		//TODO::make this calculation smarter. Logrithmical?
		long min = matrix.size() * 2L;
		long max = min * 2L;
		return rand.nextLong(min, max);
	}
	public long determineMaxStepsToTake(M matrix, OwatRandGenerator rand, long minNumScrambleSteps){
		//TODO::make this calculation smarter. Logrithmical?
		long tempMin = minNumScrambleSteps + matrix.size();
		long tempMax = tempMin * 2L;
		return rand.nextLong(tempMin, tempMax);
	}
	
	public byte[] getMatrixAsBytes(M matrix, NodeMode nodeType){
		byte[] bytes;
		if(nodeType == NodeMode.BIT){
			Iterator<BitValue> bitIt = (Iterator<BitValue>) matrix.iterator();
			
			LongLinkedList<BitValue> tempBits;
			LongLinkedList<Byte> tempBytes = new LongLinkedList<>();
			
			while (bitIt.hasNext()){
				tempBits = new LongLinkedList<>();
				for(short i = 0; i < 8; i++){
					if(!bitIt.hasNext()){
						throw new IllegalStateException("Matrix was not set up properly; invalid number of bits in matrix.");
					}
					tempBits.addLast(bitIt.next());
				}
				tempBytes.addLast(BitValue.toByte(tempBits));
			}
			
			bytes = new byte[tempBytes.size()];
			Iterator<Byte> it = tempBytes.destructiveIterator();
			for(int i = 0; i < bytes.length; i++){
				bytes[i] = it.next();
			}
		}else if(nodeType == NodeMode.BYTE){
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
}
