package com.ebp.owat.lib.dataStructure.matrix;

/**
 * Describes a matrix that will be scrambling nodes.
 *
 * Defines all the scrambling moves required by the OWAT protocol.
 *
 * Created by Greg Stewart on 3/28/17.
 */
public abstract class ScramblingMatrix<T> extends Matrix<T> {
	private static final Type DEFAULT_TYPE = Type.SCRAMBLING;
	
	/**
	 * The type of scrambling matrix this is.
	 */
	public enum Type {
		/** Means the matrix will do scrambling moves in the 'forward' manner. */
		SCRAMBLING,
		/** Means the matrix will do scrambling moves in the 'backwards' manner. */
		DESCRAMBLING
	}
	
	/** If this matrix is meant to be a scrambling or descrambling matrix. */
	public final Type type;
	
	/**
	 * Constructor to set if this matrix is a scrambling one.
	 * @param type If this is a scrambling matrix or a de-scrambling one
	 */
	protected ScramblingMatrix(Type type) {
		super();
		this.type = type;
	}
	
	//TODO:: add abstract scramble moves
}
