package com.ebp.owat.lib.datastructure.matrix.Hash;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.Scrambler;
import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.MatrixCoordinate;
import com.ebp.owat.lib.datastructure.set.LongLinkedList;
import com.ebp.owat.lib.utils.scramble.MoveValidator;
import com.ebp.owat.lib.utils.scramble.ScrambleConstants;
import com.ebp.owat.lib.utils.scramble.ScrambleMove;
import com.ebp.owat.lib.utils.scramble.ScrambleMoves;

import java.util.Iterator;
import java.util.List;

public class HashedScramblingMatrix<T> extends HashedMatrix<T> implements Scrambler {
	
	@Override
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
	
	@Override
	public void swapRows(ScrambleMove sm) {
		MoveValidator.throwIfInvalidMove(this, sm, ScrambleMoves.SWAP_ROW);
		
		final long rowIndexOne = sm.getArg(ScrambleConstants.SwapRow.ROWCOL1);
		final long rowIndexTwo = sm.getArg(ScrambleConstants.SwapRow.ROWCOL2);
		
		List<T> rowOne = this.getRow(rowIndexOne);
		List<T> rowTwo = this.getRow(rowIndexTwo);
		
		this.replaceRow(rowIndexOne, rowTwo);
		this.replaceRow(rowIndexTwo, rowOne);
	}
	
	@Override
	public void swapCols(ScrambleMove sm) {
		MoveValidator.throwIfInvalidMove(this, sm, ScrambleMoves.SWAP_COL);
		
		final long colIndexOne = sm.getArg(ScrambleConstants.SwapCol.ROWCOL1);
		final long colIndexTwo = sm.getArg(ScrambleConstants.SwapCol.ROWCOL2);
		
		List<T> colOne = this.getCol(colIndexOne);
		List<T> colTwo = this.getCol(colIndexTwo);
		
		this.replaceCol(colIndexOne, colTwo);
		this.replaceCol(colIndexTwo, colOne);
	}
	
	private List<T> slideList(List<T> list, long numToSlide){
		LongLinkedList<T> output = new LongLinkedList<>(list);
		
		Iterator<T> it = list.iterator();
		for(long curInd = 0; curInd < output.sizeL(); curInd++){
			T curVal = it.next();
			long newIndex = Math.floorMod((curInd + numToSlide),output.sizeL());
			output.set(newIndex, curVal);
		}
		
		return output;
	}
	
	@Override
	public void slideRow(ScrambleMove sm) {
		MoveValidator.throwIfInvalidMove(this, sm, ScrambleMoves.SLIDE_ROW);
		
		final long rowIndex = sm.getArg(ScrambleConstants.SlideRow.ROWCOL);
		final long numToSlide = sm.getArg(ScrambleConstants.SlideRow.NUMTOSLIDE);
		
		List<T> row = this.getRow(rowIndex);
		
		this.replaceRow(rowIndex, this.slideList(row, numToSlide));
	}
	
	@Override
	public void slideCol(ScrambleMove sm) {
		MoveValidator.throwIfInvalidMove(this, sm, ScrambleMoves.SLIDE_COL);
		
		final long colIndex = sm.getArg(ScrambleConstants.SlideRow.ROWCOL);
		final long numToSlide = sm.getArg(ScrambleConstants.SlideRow.NUMTOSLIDE);
		
		List<T> col = this.getCol(colIndex);
		
		this.replaceCol(colIndex, this.slideList(col, numToSlide));
	}
	
	@Override
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
				
				LongLinkedList<List<T>> newRows = new LongLinkedList<>();
				
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
}
