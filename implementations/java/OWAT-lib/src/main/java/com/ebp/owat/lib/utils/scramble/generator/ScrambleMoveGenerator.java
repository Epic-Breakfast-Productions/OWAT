package com.ebp.owat.lib.utils.scramble.generator;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.utils.rand.OwatRandGenerator;
import com.ebp.owat.lib.utils.scramble.MoveValidator;
import com.ebp.owat.lib.utils.scramble.ScrambleConstants;
import com.ebp.owat.lib.utils.scramble.ScrambleMove;
import com.ebp.owat.lib.utils.scramble.ScrambleMoves;

/**
 * Generator of scrambling moves.
 */
public class ScrambleMoveGenerator {
	/** The number generator to use. */
	protected OwatRandGenerator numGenerator;
	/** The matrix to generate moves for. */
	protected final Matrix matrix;

	/**
	 * Constructor for the generator.
	 * @param numGenerator The number generator to use.
	 * @param matrix The matrix to generate moves for.
	 */
	public ScrambleMoveGenerator(OwatRandGenerator numGenerator, Matrix matrix) {
		this.numGenerator = numGenerator;
		this.matrix = matrix;
	}

	/**
	 * Gets the number generator being used.
	 * @return The number generator being used by this move generator
	 */
	public OwatRandGenerator getNumGenerator(){
		return numGenerator;
	}

	/**
	 * Sets the number generator being used.
	 * @param numGenerator The new generator to use.
	 * @return This move generator.
	 */
	public ScrambleMoveGenerator setNumGenerator(OwatRandGenerator numGenerator){
		this.numGenerator = numGenerator;
		return this;
	}

	/**
	 * Gets a new move.
	 * @return A new move to be used to scramble the matrix.
	 */
	public ScrambleMove getMove(){
		MoveValidator.throwIfMatrixTooSmallForScrambling(this.matrix);
		ScrambleMoves sm = this.numGenerator.getRandValue(ScrambleMoves.values());
		
		switch (sm){
			case SWAP:
				long[] args = new long[4];
				do{
					args[ScrambleConstants.Swap.X1] = this.numGenerator.nextLong(this.matrix.getNumCols());
					args[ScrambleConstants.Swap.Y1] = this.numGenerator.nextLong(this.matrix.getNumRows());
					args[ScrambleConstants.Swap.X2] = this.numGenerator.nextLong(this.matrix.getNumCols());
					args[ScrambleConstants.Swap.Y2] = this.numGenerator.nextLong(this.matrix.getNumRows());
				}while(
					args[ScrambleConstants.Swap.X1] == args[ScrambleConstants.Swap.X2] &&
					args[ScrambleConstants.Swap.Y1] == args[ScrambleConstants.Swap.Y2]
				);
				return new ScrambleMove(sm, args);
			case SWAP_ROW:
					return new ScrambleMove(
						sm,
						this.numGenerator.nextLong(this.matrix.getNumRows()),
						this.numGenerator.nextLong(this.matrix.getNumRows())
					);
			case SWAP_COL:
				return new ScrambleMove(
					sm,
					this.numGenerator.nextLong(this.matrix.getNumCols()),
					this.numGenerator.nextLong(this.matrix.getNumCols())
				);
			case SLIDE_ROW:
				return new ScrambleMove(
					sm,
					this.numGenerator.nextLong(this.matrix.getNumRows()),
					this.numGenerator.nextLong(this.matrix.getNumCols())
				);
			case SLIDE_COL:
				return new ScrambleMove(
					sm,
					this.numGenerator.nextLong(this.matrix.getNumCols()),
					this.numGenerator.nextLong(this.matrix.getNumRows())
				);
			case ROT_BOX:{
				long
					x = this.numGenerator.nextLong(this.matrix.getNumCols() - MoveValidator.MIN_SIZE_FOR_ROTATION),
					y = this.numGenerator.nextLong(this.matrix.getNumRows() - MoveValidator.MIN_SIZE_FOR_ROTATION),
					maxSize = Math.max(x,y),
					s = this.numGenerator.nextLong(maxSize, this.matrix.getNumCols() - maxSize)
					;
				return new ScrambleMove(sm, this.numGenerator.nextLong(1,4), x, y, s);
			}
		}
		throw new IllegalStateException("This should not happen.");
	}
	
	/**
	 * Gets a set to coords to describe a box.
	 *
	 * Array returned looks like:
	 * [0] = X \
	 * [1] = Y |- "Top Left"
	 * [2] = X2 \
	 * [3] = Y2 |- "Bottom Right"
	 *
	 * @return a set of coords to describe a box.
	 */
	private long[] getBoxCoords(){
		long
			x1 = this.numGenerator.nextLong(this.matrix.getNumCols() - 2L),
			y1 = this.numGenerator.nextLong(this.matrix.getNumRows() - 2L),
			x2 = this.numGenerator.nextLong(x1 + 2, this.matrix.getNumCols()),
			y2 = this.numGenerator.nextLong(y1 + 2, this.matrix.getNumRows())
		;
		
		return new long[] {
			x1,
			y1,
			x2,
			y2
		};
	}
}
