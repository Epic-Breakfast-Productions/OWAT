package com.ebp.owat.lib.datastructure.matrix.array;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.MatrixCoordinate;

import java.util.Collection;
import java.util.List;

/**
 * Matrix that utilizes a 2d array as its internal structure
 * @param <T> The type of data the matrix holds.
 */
public class ArrayMatrix<T> extends Matrix<T> {
	@Override
	public void addRow() {
		//TODO
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
		//TODO
		return false;
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
		//TODO
		return 0;
	}

	@Override
	public T get(MatrixCoordinate coordIn) {
		//TODO
		return null;
	}

	@Override
	public List<T> getCol(MatrixCoordinate coordIn) {
		//TODO
		return null;
	}

	@Override
	public List<T> getRow(MatrixCoordinate coordIn) {
		//TODO
		return null;
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
}
