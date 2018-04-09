package com.ebp.owat.lib.datastructure.matrix;

import java.util.Iterator;

/**
 * An iterator that is specifically used to go over a matrix.
 * @param <T> The value this will return.
 */
public abstract class MatrixIterator<T> implements Iterator<T> {
	//TODO:: rework to use coord
	protected long curRow = 0;
	protected long curCol = 0;
	
	/**
	 * Determines if we are on the
	 * @return
	 */
	public boolean onNewRow(){
		return this.hasNext() && curCol <= 0;
	}

	/**
	 * Gets the next value without moving to it.
	 * @return The next value.
	 */
	public abstract T peekNext();

	/**
	 * Gets the current row the iterator is on.
	 * @return The row the iterator is currently on.
	 */
	public long getCurRow(){
		return curRow;
	}

	/**
	 * Gets the current column the iterator is on.
	 * @return The column the iterator is on.
	 */
	public long getCurCol(){
		return curCol;
	}
}
