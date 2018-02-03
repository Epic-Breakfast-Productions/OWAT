package com.ebp.owat.lib.datastructure.matrix;

import com.ebp.owat.lib.utils.scramble.ScrambleMove;

/**
 * Describes a matrix that will be scrambling nodes.
 *
 * Defines all the scrambling moves required by the OWAT protocol.
 *
 * Created by Greg Stewart on 3/28/17.
 */
public interface Scrambler {
	/**
	 * Swaps two nodes in the matrix.
	 * @param sm The move to get the parameters from.
	 * @throws IllegalArgumentException If the ScrambleMove given is somehow invalid.
	 */
	void swap(ScrambleMove sm);
	
	/**
	 * Swaps two rows
	 * @param sm The move to get the parameters from.
	 * @throws IllegalArgumentException If the ScrambleMove given is somehow invalid.
	 */
	void swapRows(ScrambleMove sm);
	
	/**
	 * Swaps two columns
	 * @param sm The move to get the parameters from.
	 * @throws IllegalArgumentException If the ScrambleMove given is somehow invalid.
	 */
	void swapCols(ScrambleMove sm);
	
	/**
	 * Slides a row down (towards the higher indexes). Wraps the values around.
	 * @param sm The move to get the parameters from.
	 * @throws IllegalArgumentException If the ScrambleMove given is somehow invalid.
	 */
	void slideRow(ScrambleMove sm);
	
	/**
	 * Slides a col down (towards the higher indexes). Wraps the values around.
	 * @param sm The move to get the parameters from.
	 * @throws IllegalArgumentException If the ScrambleMove given is somehow invalid.
	 */
	void slideCol(ScrambleMove sm);
	
	/**
	 * Rotates a sub matrix by 90 degrees a certain number of times.
	 * @param sm The move to get the parameters from.
	 * @throws IllegalArgumentException If the ScrambleMove given is somehow invalid.
	 */
	void rotBox(ScrambleMove sm);
	
	/**
	 * Does a scramble move passed to it. Simply passes it to the appropriate method.
	 * @param sm The move to do.
	 * @throws IllegalArgumentException If the ScrambleMove given is somehow invalid.
	 */
	default void doScrambleMove(ScrambleMove sm){
		switch (sm.move){
			case SWAP:
				this.swap(sm);
				break;
			case SWAP_ROW:
				this.swapRows(sm);
				break;
			case SWAP_COL:
				this.swapCols(sm);
				break;
			case SLIDE_ROW:
				this.slideRow(sm);
				break;
			case SLIDE_COL:
				this.slideCol(sm);
				break;
			case ROT_BOX:
				this.rotBox(sm);
				break;
			default:
				throw new IllegalArgumentException("The move given was invalid; Not a proper move.");
		}
	}
}
