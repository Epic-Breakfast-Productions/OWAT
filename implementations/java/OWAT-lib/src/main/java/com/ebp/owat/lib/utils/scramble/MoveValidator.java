package com.ebp.owat.lib.utils.scramble;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.utils.MatrixValidator;
import com.ebp.owat.lib.datastructure.matrix.utils.Plane;

public class MoveValidator {
	public static final long MIN_SIZE_FOR_SCRAMBLING = 5;
	
	public static void throwIfMatrixTooSmallForScrambling(Matrix matrix){
		if(
			matrix.getNumCols() < MIN_SIZE_FOR_SCRAMBLING ||
			matrix.getNumRows() < MIN_SIZE_FOR_SCRAMBLING
		) {
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
				
				if(
					move.getArg(ScrambleConstants.Swap.X1) == move.getArg(ScrambleConstants.Swap.X2) &&
					move.getArg(ScrambleConstants.Swap.Y1) == move.getArg(ScrambleConstants.Swap.Y2)
				){
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
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.SlideRow.NUMTOSLIDE), Plane.X);
				break;
			case SLIDE_COL:
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.SlideCol.ROWCOL), Plane.X);
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.SlideCol.NUMTOSLIDE), Plane.Y);
				break;
			case ROT_BOX:
				if (!(move.getArg(ScrambleConstants.RotateBox.ROTNUM) >= 1 && move.getArg(ScrambleConstants.RotateBox.ROTNUM) <= 3)) {
					throw new IllegalStateException("Invalid number of rotations given. Given: "+move.getArg(ScrambleConstants.RotateBox.ROTNUM));
				}
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.RotateBox.X1), Plane.X);
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.RotateBox.Y1), Plane.Y);
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.RotateBox.X2), Plane.X);
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.RotateBox.Y2), Plane.Y);
				
				if(
					(move.getArg(ScrambleConstants.RotateBox.X1) >= move.getArg(ScrambleConstants.RotateBox.X2) - 1) ||
					(move.getArg(ScrambleConstants.RotateBox.Y1) >= move.getArg(ScrambleConstants.RotateBox.Y2) - 1)
				){
					throw new IllegalArgumentException(
						"Points given do not make a valid box. Given: ("+move.getArg(ScrambleConstants.Swap.X1)+","+move.getArg(ScrambleConstants.Swap.Y1)+"),("+move.getArg(ScrambleConstants.Swap.X2)+","+move.getArg(ScrambleConstants.Swap.Y2)+")"
					);
				}
				break;
			default:
				throw new IllegalArgumentException("Unsupported move passed to validator.");
		}
	}
}
