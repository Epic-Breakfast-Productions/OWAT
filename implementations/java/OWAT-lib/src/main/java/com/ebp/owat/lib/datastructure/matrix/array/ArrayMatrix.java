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
 * Does not support setting null values.
 * @param <T> The type of data the matrix holds.
 */
public class ArrayMatrix<T> extends Matrix<T> {

	/** The 2d array to hold values. array.get(ROW#).get(COL#) */
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

		for(ArrayList<T> curCol : this.array){
			curCol.add(null);
		}
	}

	@Override
	public void addCol() {
		if(this.initIfNoRowsCols()){
			return;
		}

		this.array.add(new ArrayList<>());
		ArrayList<T> newCol = this.array.get(this.array.size() - 1);

		for(int i = 0; i < this.getNumRows(); i++){
			newCol.add(null);
		}
	}

	@Override
	public List<T> removeRow() {
		if(!this.hasRowsCols()){
			return null;
		}
		LinkedList<T> out = new LinkedList<>();

		for (ArrayList<T> curCol : this.array) {
			T remVal = curCol.remove(curCol.size() - 1);

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
	public List<T> removeCol() {
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
	public T setValue(MatrixCoordinate nodeToReplace, T newValue) {
		MatrixValidator.throwIfNotOnMatrix(this, nodeToReplace);
		T old = this.array.get((int)nodeToReplace.getX()).set((int)nodeToReplace.getY(), newValue);

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
		return this.array.get((int)node.getX()).get((int)node.getY()) != null;
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
		T val = this.array.get((int)coordIn.getX()).get((int)coordIn.getY());

		if(val == null){
			return this.defaultValue;
		}
		return val;
	}

	@Override
	public List<T> getCol(MatrixCoordinate coordIn) {
		MatrixValidator.throwIfNoRowsCols(this);
		//get the first element in each column
		List<T> out = new ArrayList<>();

		for(ArrayList<T> curCol : this.array){
			out.add(curCol.get((int)coordIn.getX()));
		}

		return out;
	}

	@Override
	public List<T> getRow(MatrixCoordinate coordIn) {
		MatrixValidator.throwIfNoRowsCols(this);
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
		return this.array.size();
	}

	@Override
	public long getNumRows() {
		if(this.getNumCols() != 0){
			return this.array.get(0).size();
		}
		return 0;
	}
}
