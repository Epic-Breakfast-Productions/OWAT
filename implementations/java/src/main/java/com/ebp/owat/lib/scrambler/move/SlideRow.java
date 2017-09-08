package com.ebp.owat.lib.scrambler.move;

import com.ebp.owat.lib.dataStructure.matrix.LinkedMatrix;
import com.ebp.owat.lib.dataStructure.matrix.utils.coordinate.Coordinate;

/**
 * Created by stewy on 4/4/17.
 */
public class SlideRow extends ScrambleMove {
	/**
	 * Constructor to set the matrix we are dealing with.
	 *
	 * @param matrixIn The matrix this move is dealing with.
	 */
	public SlideRow(LinkedMatrix matrixIn) {
		super(matrixIn);
	}
	
	/**
	 * Constructor to set the marix and the direction the move will take.
	 *
	 * @param matrixIn The matrix this move is dealing with.
	 * @param dirIn    The direction this move is to do.
	 */
	public SlideRow(LinkedMatrix matrixIn, Direction dirIn) {
		super(matrixIn, dirIn);
	}
	
	
	public void doMove(Coordinate nodeOne, Coordinate nodeTwo){
		//TODO:: swap the values
	}
}
