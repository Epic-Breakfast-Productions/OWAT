package com.ebp.owat.lib.datastructure.matrix.array;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.utils.MatrixValidator;
import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.MatrixCoordinate;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Matrix that utilizes a 2d array as its internal structure.
 *
 * Only supports coordinates up to int max size.
 *
 * <pre>
 * {
 *     {col1, col2}, //row 1
 *     {col1, col2}  //row 2
 * }
 * </pre>
 *
 * Does not support setting null values.
 * @param <T> The type of data the matrix holds.
 */
public class ArrayMatrix<T> extends Matrix<T> {

	/** The 2d array to hold values. array.get(ROW#).get(COL#) Row, Column to make things conceptually easier when  thinking about array initialization. TODO:: standardify this and verify all methods follow */
	protected ArrayList<ArrayList<T>> array = null;

	/** The number of elements held in the matrix. */
	protected long numElementsHeld = 0;

	private boolean initIfNoRowsCols(){
		if(!this.hasRowsCols()){
			this.array = new ArrayList<>();
			ArrayList<T> newCol = new ArrayList<>();
			newCol.add(null);
			this.array.add(newCol);
			return true;
		}
		return false;
	}

	@Override
	public void addRow() {
		if(this.initIfNoRowsCols()){
			return;
		}

		ArrayList<T> newRow = new ArrayList<>();
		for(int i = 0; i < this.getNumCols(); i++){
			newRow.add(null);
		}
		this.array.add(newRow);
	}

	@Override
	public void addCol() {
		if(this.initIfNoRowsCols()){
			return;
		}

		for(ArrayList<T> curRow : this.array){
			curRow.add(null);
		}
	}

	@Override
	public List<T> removeRow() {
		if(!this.hasRowsCols()){
			return null;
		}
		List<T> out = this.array.remove(this.array.size() - 1);

		for (int i = 0; i < out.size(); i++) {
			if(out.get(i) == null){
				out.set(i, this.getDefaultValue());
			}else{
				this.numElementsHeld--;
			}
		}

		if(this.array.size() == 0) {
			this.array = null;
		}
		return out;
	}

	@Override
	public List<T> removeCol() {
		if(!this.hasRowsCols()){
			return null;
		}
		LinkedList<T> out = new LinkedList<>();

		for (ArrayList<T> curRow : this.array) {
			T remVal = curRow.remove(curRow.size() - 1);

			if(remVal == null){
				out.addLast(this.getDefaultValue());
			}else {
				this.numElementsHeld--;
				out.addLast(remVal);
			}
		}

		if(this.array.get(0).size() == 0){
			this.array = null;
		}

		return out;
	}

	@Override
	public T setValue(MatrixCoordinate nodeToReplace, T newValue) {
		MatrixValidator.throwIfNotOnMatrix(this, nodeToReplace);
		T old = this.array.get((int)nodeToReplace.getRow()).set((int)nodeToReplace.getCol(), newValue);

		//TODO;; contemplate what happens when a user inserts a null value
		if(old == null && newValue != null){
			this.numElementsHeld++;
		}else if(old != null && newValue == null){
			this.numElementsHeld--;
		}

		return old;
	}

	@Override
	public boolean hasValue(MatrixCoordinate node) {
		MatrixValidator.throwIfNotOnMatrix(this, node);
		return this.array.get((int)node.getRow()).get((int)node.getCol()) != null;
	}

	@Override
	public T clearNode(MatrixCoordinate nodeToClear) {
		return this.setValue(nodeToClear, null);
	}

	@Override
	public long numElements() {
		return this.numElementsHeld;
	}

	@Override
	public T get(MatrixCoordinate coordIn) {
		MatrixValidator.throwIfNotOnMatrix(this, coordIn);
		T val = this.array.get((int)coordIn.getRow()).get((int)coordIn.getCol());

		if(val == null){
			return this.defaultValue;
		}
		return val;
	}

	@Override
	public List<T> getCol(MatrixCoordinate coordIn) {
		MatrixValidator.throwIfNoRowsCols(this);

		List<T> out = new ArrayList<>();

		for(ArrayList<T> curRow : this.array){
			out.add(curRow.get((int)coordIn.getCol()));
		}

		return out;
	}

	@Override
	public List<T> getRow(MatrixCoordinate coordIn) {
		MatrixValidator.throwIfNoRowsCols(this);
		//noinspection unchecked
		return (List<T>) this.array.get((int)coordIn.getY()).clone();
	}

	@Override
	public Matrix<T> getSubMatrix(MatrixCoordinate topLeft, long height, long width) {
		//TODO
		return null;
	}

	@Override
	public void replaceSubMatrix(Matrix<T> matrix, MatrixCoordinate topLeft, long height, long width) {
		//TODO
	}

	@Override
	public long getNumCols() {
		if(this.array == null){
			return 0;
		}
		return this.array.get(0).size();
	}

	@Override
	public long getNumRows() {
		if(this.getNumCols() != 0){
			return this.array.size();
		}
		return 0;
	}
}
