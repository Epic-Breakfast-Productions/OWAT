package com.ebp.owat.lib.utils.scramble.generator;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.utils.rand.LongGenerator;
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
		if(!matrix.hasRowsCols()){
			throw new IllegalStateException("Matrix has to have rows and columns to scramble.");
		}
		ScrambleMoves sm = numGenerator.getRandValue(ScrambleMoves.values());
		
		switch (sm){
			case SWAP:
				return new ScrambleMove(
					sm,
					this.numGenerator.next(this.matrix.getNumCols()),
					this.numGenerator.next(this.matrix.getNumRows()),
					this.numGenerator.next(this.matrix.getNumCols()),
					this.numGenerator.next(this.matrix.getNumRows())
				);
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
			case ROT_CLOCK: {
				long[] coords = this.getBoxCoords();
				return new ScrambleMove(
					sm,
					this.numGenerator.next(3),
					this.numGenerator.next(coords[0]),
					this.numGenerator.next(coords[1]),
					this.numGenerator.next(coords[2]),
					this.numGenerator.next(coords[3])
				);
			}
			case ROT_CCLOCK: {
				long[] coords = this.getBoxCoords();
				return new ScrambleMove(
					sm,
					this.numGenerator.next(3),
					this.numGenerator.next(coords[0]),
					this.numGenerator.next(coords[1]),
					this.numGenerator.next(coords[2]),
					this.numGenerator.next(coords[3])
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
		//TODO:: make this happen for real. need to ensure their two points are valid to eachother (top right (x/y1) is actually rop right, bottom left (x/y2) is actually bottom left)
		return new long[] {
			this.matrix.getNumCols(),
			this.matrix.getNumRows(),
			this.matrix.getNumCols(),
			this.matrix.getNumRows()
		};
	}
}
