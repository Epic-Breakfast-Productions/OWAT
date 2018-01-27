package com.ebp.owat.lib.utils.scramble.generator;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.utils.rand.LongGenerator;
import com.ebp.owat.lib.utils.scramble.MoveValidator;
import com.ebp.owat.lib.utils.scramble.ScrambleConstants;
import com.ebp.owat.lib.utils.scramble.ScrambleMove;
import com.ebp.owat.lib.utils.scramble.ScrambleMoves;

public class ScrambleMoveGenerator {
	
	protected LongGenerator numGenerator;
	protected final Matrix matrix;
	
	public ScrambleMoveGenerator(LongGenerator numGenerator, Matrix matrix) {
		this.numGenerator = numGenerator;
		this.matrix = matrix;
	}
	
	public LongGenerator getNumGenerator(){
		return numGenerator;
	}
	
	public ScrambleMoveGenerator setNumGenerator(LongGenerator numGenerator){
		this.numGenerator = numGenerator;
		return this;
	}
	
	public ScrambleMove getMove(){
		MoveValidator.throwIfMatrixTooSmallForScrambling(matrix);
		ScrambleMoves sm = numGenerator.getRandValue(ScrambleMoves.values());
		
		switch (sm){
			case SWAP:
				long[] args = new long[4];
				do{
					args[ScrambleConstants.Swap.X1] = this.numGenerator.next(this.matrix.getNumCols());
					args[ScrambleConstants.Swap.Y1] = this.numGenerator.next(this.matrix.getNumRows());
					args[ScrambleConstants.Swap.X2] = this.numGenerator.next(this.matrix.getNumCols());
					args[ScrambleConstants.Swap.Y2] = this.numGenerator.next(this.matrix.getNumRows());
				}while(
					args[ScrambleConstants.Swap.X1] == args[ScrambleConstants.Swap.X2] &&
					args[ScrambleConstants.Swap.Y1] == args[ScrambleConstants.Swap.Y2]
				);
				return new ScrambleMove(sm, args);
			case SWAP_ROW:
					return new ScrambleMove(
						sm,
						this.numGenerator.next(this.matrix.getNumRows()),
						this.numGenerator.next(this.matrix.getNumRows())
					);
			case SWAP_COL:
				return new ScrambleMove(
					sm,
					this.numGenerator.next(this.matrix.getNumCols()),
					this.numGenerator.next(this.matrix.getNumCols())
				);
			case SLIDE_ROW:
				return new ScrambleMove(
					sm,
					this.numGenerator.next(this.matrix.getNumRows()),
					this.numGenerator.next(this.matrix.getNumCols())
				);
			case SLIDE_COL:
				return new ScrambleMove(
					sm,
					this.numGenerator.next(this.matrix.getNumCols()),
					this.numGenerator.next(this.matrix.getNumRows())
				);
			case ROT_CLOCK:
			case ROT_CCLOCK:{
				long[] coords = this.getBoxCoords();
				return new ScrambleMove(
					sm,
					this.numGenerator.next(1,4),
					coords[0],
					coords[1],
					coords[2],
					coords[3]
				);
			}
		}
		throw new IllegalStateException("This should not happen.");
	}
	
	/**
	 * Gets a set to coords to describe a box.
	 *
	 * Array returned looks like:
	 * [0] = X1 \
	 * [1] = Y1 |- "Top Left"
	 * [2] = X2 \
	 * [3] = Y2 |- "Bottom Right"
	 *
	 * @return
	 */
	private long[] getBoxCoords(){
		long
			x1 = this.numGenerator.next(this.matrix.getNumCols() - 2L),
			y1 = this.numGenerator.next(this.matrix.getNumRows() - 2L),
			x2 = this.numGenerator.next(x1 + 2, this.matrix.getNumCols()),
			y2 = this.numGenerator.next(y1 + 2, this.matrix.getNumRows())
		;
		
		return new long[] {
			x1,
			y1,
			x2,
			y2
		};
	}
}
