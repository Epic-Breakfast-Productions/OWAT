package com.ebp.owat.lib.datastructure.matrix.Linked.utils.nodePosition;

import com.ebp.owat.lib.datastructure.matrix.Linked.LinkedMatrix;

public class FixedNode<T> extends NodePosition<T>{
	public enum FixedPosition{
		NORTH_WEST,
		NORTH_EAST,
		SOUTH_WEST,
		SOUTH_EAST
	}

	public final FixedPosition position;

	public FixedNode(LinkedMatrix<T> matrix, FixedPosition position) {
		super(matrix);
		this.position = position;
	}


	@Override
	public void resetPosition() {
		super.resetPosition();

	}
}
