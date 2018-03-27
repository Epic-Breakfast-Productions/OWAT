package com.ebp.owat.lib.datastructure.matrix.Linked.utils.nodePosition;

import com.ebp.owat.lib.datastructure.matrix.Linked.LinkedMatrix;
import com.ebp.owat.lib.datastructure.matrix.Linked.utils.Direction;
import com.ebp.owat.lib.datastructure.matrix.Linked.utils.LinkedMatrixNode;

import static com.ebp.owat.lib.datastructure.matrix.Linked.utils.Direction.*;

public class FixedNode<T> extends NodePosition<T>{
	public enum FixedPosition{
		NORTH_WEST(NORTH, WEST),
		NORTH_EAST(NORTH, EAST),
		SOUTH_WEST(SOUTH, WEST),
		SOUTH_EAST(SOUTH, EAST);

		public final Direction goDirOne;
		public final Direction goDirTwo;

		FixedPosition(Direction goDirOne, Direction goDirTwo){
			this.goDirOne = goDirOne;
			this.goDirTwo = goDirTwo;
		}
	}

	public final FixedPosition position;

	public FixedNode(LinkedMatrix<T> matrix, LinkedMatrixNode<T> node, FixedPosition position) {
		super(matrix, node);
		this.position = position;
		this.resetPosition();
	}

	@Override
	public boolean resetPosition() {
		super.resetPosition();
		return
			this.goDirTilBorder(this.position.goDirOne) ||
			this.goDirTilBorder(this.position.goDirTwo);
	}

	private boolean goDirTilBorder(Direction dir){
		boolean moved = false;
		while(!this.node.isBorder(dir)){
			if(this.move(dir)){
				moved = true;
			}
		}
		return moved;
	}

	@Override
	protected boolean moveNorth() {
		if(this.node.isBorder(NORTH)){
			return false;
		}
		this.node = this.node.getDir(NORTH);
		return true;
	}

	@Override
	protected boolean moveSouth() {
		if(this.node.isBorder(SOUTH)){
			return false;
		}
		this.node = this.node.getDir(SOUTH);
		return true;
	}

	@Override
	protected boolean moveEast() {
		if(this.node.isBorder(EAST)){
			return false;
		}
		this.node = this.node.getDir(EAST);
		return true;
	}

	@Override
	protected boolean moveWest() {
		if(this.node.isBorder(WEST)){
			return false;
		}
		this.node = this.node.getDir(WEST);
		return true;
	}
}
