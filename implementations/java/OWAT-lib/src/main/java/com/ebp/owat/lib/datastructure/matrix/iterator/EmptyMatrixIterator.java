package com.ebp.owat.lib.datastructure.matrix.iterator;

import java.util.NoSuchElementException;

/**
 * Iterator to describe an empty matrix.
 * @param <T> The type of value held by the matrix.
 */
public class EmptyMatrixIterator<T> extends MatrixIterator<T> {

	@Override
	public T peekNext() {
		throw new NoSuchElementException("Matrix is empty.");
	}

	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public T next() {
		throw new NoSuchElementException("Matrix is empty.");
	}
}
