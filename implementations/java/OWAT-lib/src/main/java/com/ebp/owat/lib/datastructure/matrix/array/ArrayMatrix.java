package com.ebp.owat.lib.datastructure.matrix.array;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.utils.MatrixValidator;
import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.MatrixCoordinate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Matrix that utilizes a 2d array as its internal structure.
 *
 * Only supports coordinates up to int max size.
 * @param <T> The type of data the matrix holds.
 */
public class ArrayMatrix<T> extends Matrix<T> {

	/** The 2d array to hold values. */
	protected ArrayList<ArrayList<T>> array = null;

	/** The number of elements held in the matrix. */
	protected long numElementsHeld = 0;

	private boolean initIfNoRowsCols(){
		if(!this.hasRowsCols()){
			this.array = new ArrayList<>();
			ArrayList<T> newCol = new ArrayList<>();
			//TODO:: properly init this
			return true;
		}
		return false;
	}

	@Override
	public void addRow() {
		if(this.initIfNoRowsCols()){
			return;
		}
	}

	@Override
	public boolean addRows(Collection<T> valuesIn) {
		//TODO
		return false;
	}

	@Override
	public void addCol() {
		//TODO
	}

	@Override
	public boolean addCols(Collection<T> valuesIn) {
		//TODO
		return false;
	}

	@Override
	public List<T> removeRow() {
		//TODO
		return null;
	}

	@Override
	public List<T> removeCol() {
		//TODO
		return null;
	}

	@Override
	public T setValue(MatrixCoordinate nodeToReplace, T newValue) {
		//TODO
		return null;
	}

	@Override
	public boolean hasValue(MatrixCoordinate node) {
		MatrixValidator.throwIfNotOnMatrix(this, node);
		return this.array.get((int)node.getX()).get((int)node.getY()) != null;
	}

	@Override
	public T clearNode(MatrixCoordinate nodeToClear) {
		//TODO
		return null;
	}

	@Override
	public List<T> replaceRow(MatrixCoordinate matrixCoordinate, Collection<T> newValues) throws IndexOutOfBoundsException {
		//TODO
		return null;
	}

	@Override
	public List<T> replaceCol(MatrixCoordinate matrixCoordinate, Collection<T> newValues) throws IndexOutOfBoundsException {
		//TODO
		return null;
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
