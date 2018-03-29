package com.ebp.owat.lib.datastructure.matrix.Linked.utils.nodePosition;

import com.ebp.owat.lib.datastructure.matrix.Linked.LinkedMatrix;
import com.ebp.owat.lib.datastructure.matrix.Linked.utils.Direction;
import com.ebp.owat.lib.datastructure.matrix.Linked.utils.LinkedMatrixNode;
import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.Coordinate;
import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.MatrixCoordinate;

import static com.ebp.owat.lib.datastructure.matrix.Linked.utils.Direction.*;

/**
 * Describes the position of a node on the matrix
 *
 * TODO:: should probably extend MatrixCoordinate?
 * @param <T> The type of value the nodes hold.
 */
public class NodePosition<T> extends MatrixCoordinate {
	protected LinkedMatrixNode<T> node = null;

	private NodePosition(LinkedMatrix<T> matrix, Coordinate coord) {
		super(matrix, coord.getX(), coord.getY());
	}

	protected NodePosition(LinkedMatrix<T> matrix, LinkedMatrixNode<T> node) {
		this(matrix, node.getCoord());
		this.node = node;
	}

	/**
	 * Gets the node held at this position.
	 * @return
	 */
	public LinkedMatrixNode<T> getNode(){
		return node;
	}

	/**
	 * Moves this position one node North.
	 * @return If the position actually moved or not.
	 */
	public boolean moveNorth(){
		if(this.node.isBorder(NORTH)){
			return false;
		}
		this.node = this.node.getDir(NORTH);
		this.incY();
		return true;
	}

	/**
	 * Moves this position one node South.
	 * @return If the position actually moved or not.
	 */
	public boolean moveSouth(){
		if(this.node.isBorder(SOUTH)){
			return false;
		}
		this.node = this.node.getDir(SOUTH);
		this.decY();
		return true;
	}

	/**
	 * Moves this position one node East.
	 * @return If the position actually moved or not.
	 */
	public boolean moveEast(){
		if(this.node.isBorder(EAST)){
			return false;
		}
		this.node = this.node.getDir(EAST);
		this.incX();
		return true;
	}

	/**
	 * Moves this position one node West.
	 * @return If the position actually moved or not.
	 */
	public boolean moveWest(){
		if(this.node.isBorder(WEST)){
			return false;
		}
		this.node = this.node.getDir(WEST);
		this.decX();
		return true;
	}

	/**
	 * Moves this position one node in the direction given.
	 * @param dir The direction to move the position.
	 * @return If the position actually moved or not.
	 */
	public boolean move(Direction dir){
		switch (dir){
			case NORTH:
				return moveNorth();
			case SOUTH:
				return moveSouth();
			case EAST:
				return moveEast();
			case WEST:
				return moveWest();
		}
		throw new IllegalArgumentException();
	}

	/**
	 * Method to reset the position of the node, if the node needs to do so.
	 * @return If the node actually moved or not.
	 */
	public boolean resetPosition(){
		return false;
	}

	@Override
	public NodePosition<T> clone() {
		return new NodePosition<T>((LinkedMatrix<T>) this.matrix, (Coordinate)this);
	}
}
