package com.ebp.owat.lib.utils.scramble;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.utils.MatrixValidator;
import com.ebp.owat.lib.datastructure.matrix.utils.Plane;

public class MoveValidator {
	public static final long MIN_SIZE_FOR_SCRAMBLING = 5;
	public static final long MIN_SIZE_FOR_ROTATION = 2;
	
	public static boolean matrixIsTooSmallForScrambling(Matrix matrix) {
		return matrix.getNumCols() < MIN_SIZE_FOR_SCRAMBLING ||
			matrix.getNumRows() < MIN_SIZE_FOR_SCRAMBLING;
	}
	
	/**
	 *
	 * @param matrix
	 * @throws IllegalStateException If the matrix is too small for scrambling.
	 */
	public static void throwIfMatrixTooSmallForScrambling(Matrix matrix) {
		if (matrixIsTooSmallForScrambling(matrix)) {
			throw new IllegalStateException("Matrix too small for scrambling.");
		}
	}
	
	public static void throwIfInvalidMove(Matrix matrix, ScrambleMove move) {
		switch (move.move) {
			case SWAP:
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.Swap.X1), Plane.X);
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.Swap.Y1), Plane.Y);
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.Swap.X2), Plane.X);
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.Swap.Y2), Plane.Y);
				
				if (
					move.getArg(ScrambleConstants.Swap.X1) == move.getArg(ScrambleConstants.Swap.X2) &&
						move.getArg(ScrambleConstants.Swap.Y1) == move.getArg(ScrambleConstants.Swap.Y2)
					) {
					throw new IllegalArgumentException("Can't swap a node's value with itself.");
				}
				break;
			case SWAP_ROW:
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.SwapRow.ROWCOL1), Plane.Y);
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.SwapRow.ROWCOL2), Plane.Y);
				break;
			case SWAP_COL:
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.SwapCol.ROWCOL1), Plane.X);
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.SwapCol.ROWCOL2), Plane.X);
				break;
			case SLIDE_ROW:
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.SlideRow.ROWCOL), Plane.Y);
				break;
			case SLIDE_COL:
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.SlideCol.ROWCOL), Plane.X);
				break;
			case ROT_BOX:
				if (!(move.getArg(ScrambleConstants.RotateBox.ROTNUM) >= 1 && move.getArg(ScrambleConstants.RotateBox.ROTNUM) <= 3)) {
					throw new IllegalArgumentException("Invalid number of rotations given. Given: " + move.getArg(ScrambleConstants.RotateBox.ROTNUM));
				}
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.RotateBox.X), Plane.X);
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.RotateBox.Y), Plane.Y);
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.RotateBox.SIZE) + move.getArg(ScrambleConstants.RotateBox.X), Plane.X);
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.RotateBox.SIZE) + move.getArg(ScrambleConstants.RotateBox.Y), Plane.Y);
				
				if (move.getArg(ScrambleConstants.RotateBox.SIZE) < MIN_SIZE_FOR_ROTATION) {
					throw new IllegalArgumentException("Invalid size of sub matrix.");
				}
				break;
			default:
				throw new IllegalArgumentException("Unsupported move passed to validator.");
		}
	}
	
	public static void throwIfInvalidMove(Matrix matrix, ScrambleMove move, ScrambleMoves moveToBe) {
		if(move.move != moveToBe){
			throw new IllegalArgumentException("Move given was not what was expected. Expected: " + moveToBe + ", Got: " + move.move);
		}
		throwIfInvalidMove(matrix, move);
	}
}