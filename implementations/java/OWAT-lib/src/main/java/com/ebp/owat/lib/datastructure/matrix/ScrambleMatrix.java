package com.ebp.owat.lib.datastructure.matrix;

import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.MatrixCoordinate;
import com.ebp.owat.lib.datastructure.set.LongLinkedList;
import com.ebp.owat.lib.utils.scramble.MoveValidator;
import com.ebp.owat.lib.utils.scramble.ScrambleConstants;
import com.ebp.owat.lib.utils.scramble.ScrambleMove;
import com.ebp.owat.lib.utils.scramble.ScrambleMoves;

import java.util.Iterator;
import java.util.List;

/**
 * Describes a matrix that will be scrambling nodes.
 *
 * Defines all the scrambling moves required by the OWAT protocol.
 *
 * Created by Greg Stewart on 3/28/17.
 */
public abstract class ScrambleMatrix<T> extends Matrix<T> {
	/**
	 * Swaps two nodes in the matrix.
	 * @param sm The move to get the parameters from.
	 * @throws IllegalArgumentException If the ScrambleMove given is somehow invalid.
	 */
	public void swap(ScrambleMove sm) {
		MoveValidator.throwIfInvalidMove(this, sm, ScrambleMoves.SWAP);

		MatrixCoordinate coordOne = new MatrixCoordinate(this, sm.getArg(ScrambleConstants.Swap.X1), sm.getArg(ScrambleConstants.Swap.Y1));
		MatrixCoordinate coordTwo = new MatrixCoordinate(this, sm.getArg(ScrambleConstants.Swap.X2), sm.getArg(ScrambleConstants.Swap.Y2));

		if(this.hasValue(coordOne) && this.hasValue(coordTwo)){
			this.setValue(coordTwo, this.setValue(coordOne, this.get(coordTwo)));
		}else if(this.hasValue(coordOne)){
			this.setValue(coordTwo, this.get(coordOne));
			this.clearNode(coordOne);
		}else if(this.hasValue(coordTwo)){
			this.setValue(coordOne, this.get(coordTwo));
			this.clearNode(coordTwo);
		}
	}
	
	/**
	 * Swaps two rows
	 * @param sm The move to get the parameters from.
	 * @throws IllegalArgumentException If the ScrambleMove given is somehow invalid.
	 */
	public void swapRows(ScrambleMove sm) {
		MoveValidator.throwIfInvalidMove(this, sm, ScrambleMoves.SWAP_ROW);

		final long rowIndexOne = sm.getArg(ScrambleConstants.SwapRow.ROWCOL1);
		final long rowIndexTwo = sm.getArg(ScrambleConstants.SwapRow.ROWCOL2);

		List<T> rowOne = this.getRow(rowIndexOne);
		List<T> rowTwo = this.getRow(rowIndexTwo);

		this.replaceRow(rowIndexOne, rowTwo);
		this.replaceRow(rowIndexTwo, rowOne);
	}
	
	/**
	 * Swaps two columns
	 * @param sm The move to get the parameters from.
	 * @throws IllegalArgumentException If the ScrambleMove given is somehow invalid.
	 */
	public void swapCols(ScrambleMove sm) {
		MoveValidator.throwIfInvalidMove(this, sm, ScrambleMoves.SWAP_COL);

		final long colIndexOne = sm.getArg(ScrambleConstants.SwapCol.ROWCOL1);
		final long colIndexTwo = sm.getArg(ScrambleConstants.SwapCol.ROWCOL2);

		List<T> colOne = this.getCol(colIndexOne);
		List<T> colTwo = this.getCol(colIndexTwo);

		this.replaceCol(colIndexOne, colTwo);
		this.replaceCol(colIndexTwo, colOne);
	}

	/**
	 * This slides a list of values down the number of spots specified.
	 * @param list The list of values to slide.
	 * @param numToSlide The number of spots to slide.
	 * @return The slid list.
	 */
	protected List<T> slideList(List<T> list, long numToSlide){
		LongLinkedList<T> output = new LongLinkedList<>(list);

		Iterator<T> it = list.iterator();
		for(long curInd = 0; curInd < output.sizeL(); curInd++){
			T curVal = it.next();
			long newIndex = Math.floorMod((curInd + numToSlide),output.sizeL());
			output.set(newIndex, curVal);
		}

		return output;
	}
	
	/**
	 * Slides a row down (towards the higher indexes). Wraps the values around.
	 * @param sm The move to get the parameters from.
	 * @throws IllegalArgumentException If the ScrambleMove given is somehow invalid.
	 */
	public void slideRow(ScrambleMove sm) {
		MoveValidator.throwIfInvalidMove(this, sm, ScrambleMoves.SLIDE_ROW);

		final long rowIndex = sm.getArg(ScrambleConstants.SlideRow.ROWCOL);
		final long numToSlide = sm.getArg(ScrambleConstants.SlideRow.NUMTOSLIDE);

		List<T> row = this.getRow(rowIndex);

		this.replaceRow(rowIndex, this.slideList(row, numToSlide));
	}
	
	/**
	 * Slides a col down (towards the higher indexes). Wraps the values around.
	 * @param sm The move to get the parameters from.
	 * @throws IllegalArgumentException If the ScrambleMove given is somehow invalid.
	 */
	public void slideCol(ScrambleMove sm) {
		MoveValidator.throwIfInvalidMove(this, sm, ScrambleMoves.SLIDE_COL);

		final long colIndex = sm.getArg(ScrambleConstants.SlideRow.ROWCOL);
		final long numToSlide = sm.getArg(ScrambleConstants.SlideRow.NUMTOSLIDE);

		List<T> col = this.getCol(colIndex);

		this.replaceCol(colIndex, this.slideList(col, numToSlide));
	}
	
	/**
	 * Rotates a sub matrix by 90 degrees a certain number of times.
	 * @param sm The move to get the parameters from.
	 * @throws IllegalArgumentException If the ScrambleMove given is somehow invalid.
	 */
	public void rotBox(ScrambleMove sm) {
		MoveValidator.throwIfInvalidMove(this, sm, ScrambleMoves.ROT_BOX);
		long numTimesToRotate = sm.getArg(ScrambleConstants.RotateBox.ROTNUM);
		numTimesToRotate = (numTimesToRotate<0 ? numTimesToRotate + 4 : numTimesToRotate);
		MatrixCoordinate topLeft = new MatrixCoordinate(this, sm.getArg(ScrambleConstants.RotateBox.X), sm.getArg(ScrambleConstants.RotateBox.Y));
		long size = sm.getArg(ScrambleConstants.RotateBox.SIZE);

		Matrix<T> subMatrix = this.getSubMatrix(topLeft, size);

		for(long i = 0; i < numTimesToRotate; i++){
			//transpose
			{
				LongLinkedList<List<T>> rows = new LongLinkedList<>();

				for(long j = 0; j < subMatrix.getNumRows(); j++){
					rows.addLast(subMatrix.getRow(j));
				}

				long j = 0;
				for(List<T> curRow : rows){
					subMatrix.replaceCol(j, curRow);
					j++;
				}
			}

			//flip
			{
				LongLinkedList<List<T>> rows = new LongLinkedList<>();

				for(long j = 0; j < subMatrix.getNumRows(); j++){
					rows.addLast(subMatrix.getRow(j));
				}

				Iterator<List<T>> it = rows.destructiveIterator();

				long j = 0;
				while (it.hasNext()){
					LongLinkedList<T> newRow = new LongLinkedList<>();
					List<T> curRow = it.next();
					for(T curItem : curRow){
						newRow.addFirst(curItem);
					}
					subMatrix.replaceRow(j, newRow);
					j++;
				}
			}
		}

		this.replaceSubMatrix(subMatrix, topLeft);
	}
	
	/**
	 * Does a scramble move passed to it. Simply passes it to the appropriate method.
	 * @param sm The move to do.
	 * @throws IllegalArgumentException If the ScrambleMove given is somehow invalid.
	 */
	public void doScrambleMove(ScrambleMove sm){
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
