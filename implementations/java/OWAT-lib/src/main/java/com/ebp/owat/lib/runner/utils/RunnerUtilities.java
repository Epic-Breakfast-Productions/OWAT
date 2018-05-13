package com.ebp.owat.lib.runner.utils;

import com.ebp.owat.lib.datastructure.matrix.MatrixIterator;
import com.ebp.owat.lib.datastructure.matrix.ScrambleMatrix;
import com.ebp.owat.lib.datastructure.matrix.array.ArrayScramblingMatrix;
import com.ebp.owat.lib.datastructure.matrix.hash.HashedScramblingMatrix;
import com.ebp.owat.lib.datastructure.matrix.linked.LinkedScramblingMatrix;
import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.MatrixCoordinate;
import com.ebp.owat.lib.datastructure.set.LongLinkedList;
import com.ebp.owat.lib.datastructure.value.BitValue;
import com.ebp.owat.lib.datastructure.value.ByteValue;
import com.ebp.owat.lib.datastructure.value.NodeMode;
import com.ebp.owat.lib.datastructure.value.Value;
import com.ebp.owat.lib.utils.rand.OwatRandGenerator;
import com.ebp.owat.lib.utils.scramble.MoveValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import static com.ebp.owat.lib.datastructure.value.NodeMode.BIT;
import static com.ebp.owat.lib.datastructure.value.NodeMode.BYTE;

/**
 * Utilities to abstract the functionality needed by the runners.
 *
 * Not static due to the types.
 *
 * @param <N> The type of value held
 * @param <M> The matrix with the type N
 * @param <R> The type of random generator
 */
public class RunnerUtilities<N extends Value, M extends ScrambleMatrix<N>, R extends OwatRandGenerator> {
	private static final Logger LOGGER = LoggerFactory.getLogger(RunnerUtilities.class);
	private static final java.util.Base64.Decoder DECODER = Base64.getDecoder();

	/**
	 * Gets a byte array from a string.
	 * @param str The string to turn into a byte array.
	 * @return The byte array from the string.
	 */
	public static byte[] getByteArrFromString(String str){
		return str.getBytes(StandardCharsets.UTF_8);
	}

	/**
	 * General constructor. Needed for the types.
	 */
	public RunnerUtilities() {

	}

	/**
	 * Reads the data in from they input stream. Closes stream when done.
	 *
	 * @param dataInput The stream to get the data from.
	 * @param decode If we need to decode from Base64
	 * @return A list of the data read in.
	 * @throws IOException If something goes wrong with the read.
	 */
	public LongLinkedList<Byte> readDataIn(InputStream dataInput, boolean decode) throws IOException {
		LongLinkedList<Byte> output = new LongLinkedList<>();
		try {
			int readResult = dataInput.read();
			while (readResult != -1) {
				byte curByte = (byte) readResult;
				output.addLast(curByte);
				readResult = dataInput.read();
			}
		} finally {
			if (dataInput != null) {
				dataInput.close();
			}
		}
		if (decode) {
			byte data[] = new byte[output.size()];

			int count = 0;
			for (byte curByte : output) {
				data[count] = curByte;
				count++;
			}

			data = DECODER.decode(data);

			output = new LongLinkedList<>();

			for (byte curByte : data) {
				output.addLast(curByte);
			}
		}
		return output;
	}

	/**
	 * Reads the data into a {@link LongLinkedList<Byte> long linked list}, not Base64 decoding the data.
	 * @param dataInput The input stream to read bytes from.
	 * @return The list of bytes gotten.
	 * @throws IOException If something went wrong during IO.
	 */
	public LongLinkedList<Byte> readDataIn(InputStream dataInput) throws IOException {
		return readDataIn(dataInput, false);
	}

	/**
	 * Fills the matrix given with the data given.
	 *
	 * @param emptyMatrix The empty matrix to insert values into.
	 * @param values The values to enter into the matrix
	 * @param height The hight of the resulting matrix. (optional, to be used with height)
	 * @param width The width of the resulting matrix. (optional, to be used with width)
	 * @throws IllegalStateException if the matrix given is not empty.
	 */
	private void fillMatrixWithData(M emptyMatrix, LongLinkedList values, long height, long width) {
		if (height < 1 || width < 1) {
			//noinspection unchecked
			emptyMatrix.grow(values);
		} else {
			//noinspection unchecked
			emptyMatrix.grow(height, width, values);
		}
	}

	/**
	 * Gets a matrix of the type appropriate for the size of matrix.
	 * @param matrixMode The type of matrix to use.
	 * @param mode The nodemode to use
	 * @return The matrix
	 */
	private M getNewMatrix(MatrixMode matrixMode, NodeMode mode){
		switch (matrixMode){
			case HASHED:
				//noinspection unchecked
				return (M)(
					mode == BIT ?
					new HashedScramblingMatrix<BitValue>() :
					new HashedScramblingMatrix<ByteValue>()
				);
			case ARRAY:
				//noinspection unchecked
				return (M)(
					mode == BIT ?
						new ArrayScramblingMatrix<BitValue>() :
						new ArrayScramblingMatrix<ByteValue>()
				);
			case LINKED:
				//noinspection unchecked
				return (M)(
					mode == BIT ?
						new LinkedScramblingMatrix<BitValue>() :
						new LinkedScramblingMatrix<ByteValue>()
				);
			/*
			 * Only add a type when it is fully implemented; ALL tests pass
			 */
			default:
				throw new UnsupportedOperationException("Attempted to use unsupported matrix type: "+matrixMode.name);
		}
	}

	/**
	 * Gets a bit matrix from the data given.
	 *
	 * @param matrixMode The type of matrix to use.
	 * @param data The data to fill the matrix with.
	 * @param height The height to make the matrix. (-1 for auto)
	 * @param width The width to make the matrix. (-1 for auto)
	 * @return The bit matrix with the data given.
	 */
	private M getBitMatrix(MatrixMode matrixMode, LongLinkedList<Byte> data, long height, long width) {

		M matrix = this.getNewMatrix(matrixMode, BIT);

		Iterator<Byte> it = data.destructiveIterator();
		LongLinkedList<BitValue> bitValues = new LongLinkedList<>();

		while (it.hasNext()) {
			byte curByte = it.next();
			bitValues.addAll(BitValue.fromByte(curByte, true));
		}
		this.fillMatrixWithData(matrix, bitValues, height, width);

		return matrix;
	}

	/**
	 * Gets a byte matrix comprising the data given.
	 * @param matrixMode The type of matrix to use.
	 * @param data The data to put into the matrix.
	 * @param height The height of the matrix (-1 for auto)
	 * @param width The width of the matrix (-1 for auto)
	 * @return The matrix with the data given.
	 */
	private M getByteMatrix(MatrixMode matrixMode, LongLinkedList<Byte> data, long height, long width) {
		M matrix = this.getNewMatrix(matrixMode, BYTE);

		Iterator<Byte> it = data.destructiveIterator();
		LongLinkedList<ByteValue> byteValues = new LongLinkedList<>();

		while (it.hasNext()) {
			byte curByte = it.next();
			byteValues.addLast(new ByteValue(curByte, true));
		}

		this.fillMatrixWithData(matrix, byteValues, height, width);

		return matrix;
	}

	/**
	 * Gets a matrix built with the data given.
	 *
	 * @param data     The data read in.
	 * @param nodeType The type of node to use.
	 * @param height   The height of the matrix to make. -1 for Automatic.
	 * @param width    The width of the matrix to make. -1 for Automatic.
	 * @return A matrix built with the data given.
	 */
	public M getMatrix(LongLinkedList<Byte> data, MatrixMode matrixMode, NodeMode nodeType, long height, long width) {
		switch (nodeType) {
			case BIT:
				LOGGER.debug("Matrix set to Bit nodes.");
				return this.getBitMatrix(matrixMode, data, height, width);
			case BYTE:
				LOGGER.debug("Matrix set to Bit nodes.");
				return this.getByteMatrix(matrixMode, data, height, width);
			default:
				throw new IllegalStateException();
		}
	}

	/**
	 * Gets a matrix built with the data given, automatically setting height/width.
	 *
	 * @param data     The data read in.
	 * @param matrixMode The type of matrix this should be.
	 * @param nodeType The type of node to use.
	 * @return A matrix built with the data given.
	 */
	public M getMatrix(LongLinkedList<Byte> data, MatrixMode matrixMode, NodeMode nodeType) {
		return getMatrix(data, matrixMode, nodeType, -1, -1);
	}

	/**
	 * Gets a list of random values.
	 * @param numValues The number of values to generate.
	 * @param rand The random number generator to use.
	 * @param nodeType The type of node used.
	 * @return A list of random values.
	 */
	public LongLinkedList<N> getRandListOfValues(long numValues, OwatRandGenerator rand, NodeMode nodeType) {
		LongLinkedList<N> output = new LongLinkedList<>();

		if (nodeType == BIT) {
			for (long i = 0; i < numValues; i++) {
				//noinspection unchecked
				output.add((N) new BitValue(rand.nextBool(), false));
			}
		} else if (nodeType == BYTE) {
			for (long i = 0; i < numValues; i++) {
				//noinspection unchecked
				output.add((N) new ByteValue(rand.nextByte(), false));
			}
		}

		return output;
	}

	/**
	 * Adds a random row or column to the matrix given.
	 * @param matrix The matrix to add to.
	 * @param rand The random number generator to use.
	 * @param nodeType The type of node used.
	 */
	public void addRandRowOrCol(M matrix, OwatRandGenerator rand, NodeMode nodeType) {
		if (rand.nextBool()) {
			matrix.addRow();
			List<N> newVals = this.getRandListOfValues(matrix.getNumCols(), rand, nodeType);

			matrix.replaceRow(matrix.getNumRows() - 1, newVals);
		} else {
			matrix.addCol();
			List<N> newVals = this.getRandListOfValues(matrix.getNumRows(), rand, nodeType);

			matrix.replaceCol(matrix.getNumCols() - 1, newVals);
		}
	}

	/**
	 * Determines the number of rows and columns that should be used to pad the matrix.
	 *
	 * Does this based on the number of elements already in the matrix.
	 *
	 * Link to graph of the function used:
	 * https://www.desmos.com/calculator/tq6r7kviyc
	 * @param num The number of elements already in the matrix.
	 * @return The number of rows and/or columns to be added.
	 */
	private static long determineNumToPad(long num) {
		long a = 5,
			b = 700,
			c = 15;
		return (long) (Math.pow(Math.E, (a - (num / b))) + c);
	}

	/**
	 * Pads the matrix given with random data.
	 * @param matrix The matrix to add data to.
	 * @param rand The random number generator to use.
	 * @param nodeType The type of node used.
	 */
	public void padMatrix(M matrix, OwatRandGenerator rand, NodeMode nodeType) {
		if (!matrix.isFull()) {
			MatrixCoordinate curPos = new MatrixCoordinate(matrix, matrix.getWidth() - 1, matrix.getHeight() - 1);
			do {
				if (matrix.hasValue(curPos)) {
					throw new IllegalStateException("Could not fill empty part of matrix; Hit a value but still not full.");
				}
				switch (nodeType) {
					case BIT:
						//noinspection unchecked
						matrix.setValue(curPos, (N) new BitValue(rand.nextBool(), false));
						break;
					case BYTE:
						//noinspection unchecked
						matrix.setValue(curPos, (N) new ByteValue(rand.nextByte(), false));
						break;
				}

				curPos = curPos.clone();
				if (curPos.getCol() == 0) {
					curPos.setX(matrix.getWidth() - 1);
					curPos.setY(curPos.getY() - 1);
				} else {
					curPos.setX(curPos.getX() - 1);
				}
			} while (!matrix.isFull());
		}

		while (matrix.getNumCols() < MoveValidator.MIN_SIZE_FOR_SCRAMBLING) {
			matrix.addCol();
			List list = this.getRandListOfValues(matrix.getNumRows(), rand, nodeType);
			//noinspection unchecked
			matrix.replaceCol(matrix.getNumCols() - 1, list);
		}
		while (matrix.getNumRows() < MoveValidator.MIN_SIZE_FOR_SCRAMBLING) {
			matrix.addRow();
			List list = this.getRandListOfValues(matrix.getNumCols(), rand, nodeType);
			//noinspection unchecked
			matrix.replaceRow(matrix.getNumRows() - 1, list);
		}

		long numRowsColsToGenerate = determineNumToPad(matrix.size());
		LOGGER.debug("Adding a total of {} rows and/or columns of dummy data to the matrix.", numRowsColsToGenerate);
		for (long i = 0; i < numRowsColsToGenerate; i++) {
			addRandRowOrCol(matrix, rand, nodeType);
		}

		//ensure that bit marices can be serialized properly
		if (nodeType == BIT) {
			while (matrix.size() % 8 != 0) {
				addRandRowOrCol(matrix, rand, nodeType);
			}
		}
	}

	/**
	 * Gets a number based on the number given. Used to help determine what number of steps to use.
	 *
	 * Link to graph of function used:
	 * https://www.desmos.com/calculator/6lusvo9prb
	 *
	 * @param num The number to base the calculation on.
	 * @return The number gotten from the function.
	 */
	private static long getNormallizedNumber(long num) {
		long a = 50,
			b = 1,
			h = 1,
			c = 1,
			k = 1;

		return (long) Math.ceil(
			a * Math.log(
				b * Math.pow(num - h, c)
			) + k
		);
	}

	/**
	 * Determines the number of steps to take to scramble the matrix.
	 * @param matrix The matrix being scrambled.
	 * @param rand The random number generator to use.
	 * @param minNumScrambleSteps The bare minimum number of steps to use.
	 * @return The number of steps to take to scramble the matrix.
	 */
	public long determineNumStepsToTake(M matrix, OwatRandGenerator rand, long minNumScrambleSteps) {
		long min = minNumScrambleSteps + getNormallizedNumber(matrix.size()) + matrix.size();
		long max = rand.nextLong(min + 1, min + (long) Math.ceil(Math.sqrt(matrix.size())) + rand.nextLong(1000));
		return rand.nextLong(min, max);
	}

	/**
	 * Determines the appropriate minimum number of steps to take.
	 * @param matrix The matrix being scrambled.
	 * @param rand The random number generator being used.
	 * @return The appropriate minimum number of steps to take.
	 */
	public long determineMinStepsToTake(M matrix, OwatRandGenerator rand) {
		return determineNumStepsToTake(
			matrix,
			rand,
			getNormallizedNumber(matrix.size())
		);
	}

	/**
	 * Gets the matrix given as an array of bytes.
	 * @param matrix The matrix
	 * @param nodeType The node type used
	 * @param length The number of elements in the matrix to go through.
	 * @return The matrix given as an array of bytes.
	 */
	public byte[] getMatrixAsBytes(M matrix, NodeMode nodeType, long length) {
		byte[] bytes;
		if (nodeType == BIT) {
			//noinspection unchecked
			MatrixIterator<BitValue> bitIt = (MatrixIterator<BitValue>) matrix.iterator();

			LongLinkedList<BitValue> tempBits;
			LongLinkedList<Byte> tempBytes = new LongLinkedList<>();

			for(long l = 0; l < length; l += 8) {
				tempBits = new LongLinkedList<>();
				for (short i = 0; i < 8; i++) {
					if (!bitIt.hasNext()) {
						throw new IllegalStateException("Matrix was not set up properly; invalid number of bits in matrix.");
					}

					BitValue val = bitIt.next();

					if (val == null) {
						throw new IllegalStateException("Cannot handle null values in matrix.");
					}

					tempBits.addLast(val);

				}
				tempBytes.addLast(BitValue.toByte(tempBits));
			}

			bytes = new byte[tempBytes.size()];
			Iterator<Byte> it = tempBytes.destructiveIterator();
			for (int i = 0; i < bytes.length; i++) {
				bytes[i] = it.next();
			}
		} else if (nodeType == NodeMode.BYTE) {
			bytes = new byte[(int)length];
			//noinspection unchecked
			Iterator<ByteValue> it = (Iterator<ByteValue>) matrix.iterator();
			for (int i = 0; i < length; i++) {
				bytes[i] = it.next().getValue();
			}
		} else {
			throw new IllegalStateException();
		}
		return bytes;
	}

	/**
	 * Gets the matrix given as an array of bytes.
	 * @param matrix The matrix
	 * @param nodeType The type of node used.
	 * @return The matrix given as an array of bytes.
	 */
	public byte[] getMatrixAsBytes(M matrix, NodeMode nodeType) {
		return getMatrixAsBytes(matrix, nodeType, matrix.size());
	}

	/**
	 * Compresses a set of bytes using GZIP.
	 * @param bytes The bytes to compress.
	 * @return The compressed bytes.
	 */
	public byte[] compressBytes(byte[] bytes){
		byte output[];
		try(
			ByteArrayOutputStream os = new ByteArrayOutputStream(bytes.length);
			GZIPOutputStream gzos = new GZIPOutputStream(os);
		) {
			gzos.write(bytes);
			gzos.close();
			os.close();
			output = os.toByteArray();
		} catch (IOException e) {
			LOGGER.error("Somehow an exception happened compressing the data. Error: ", e);
			throw new RuntimeException(e);
		}
		LOGGER.debug("Size of key before compression: {}. After: {}", bytes.length, output.length);
		return output;
	}

	/**
	 * Decompresses a set of bytes compressed using GZip.
	 * @param bytes The compressed bytes.
	 * @return The decompressed bytes.
	 * @throws IOException If something went wrong during decompression.
	 */
	public byte[] decompressBytes(InputStream bytes) throws IOException {
		byte[] buffer = new byte[1024];
		try(
			GZIPInputStream gzipper = new GZIPInputStream(bytes);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
		){
			int len;
			while ((len = gzipper.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}

			gzipper.close();
			out.close();
			return out.toByteArray();
		} catch (IOException e) {
			LOGGER.error("Error decompressing data: ", e);
			throw e;
		}
	}
}