package com.ebp.owat.lib.datastructure.matrix.linked;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.hash.HashedScramblingMatrix;
import com.ebp.owat.lib.datastructure.matrix.linked.utils.Direction;
import com.ebp.owat.lib.datastructure.matrix.linked.utils.LinkedMatrixNode;
import com.ebp.owat.lib.datastructure.matrix.linked.utils.nodePosition.FixedNode;
import com.ebp.owat.lib.datastructure.matrix.linked.utils.nodePosition.NodePosition;
import com.ebp.owat.lib.datastructure.matrix.utils.MatrixValidator;
import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.DistanceCalc;
import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.MatrixCoordinate;
import com.ebp.owat.lib.datastructure.set.LongLinkedList;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static com.ebp.owat.lib.datastructure.matrix.linked.utils.Direction.EAST;
import static com.ebp.owat.lib.datastructure.matrix.linked.utils.Direction.SOUTH;
import static com.ebp.owat.lib.datastructure.matrix.linked.utils.nodePosition.FixedNode.FixedPosition;
import static com.ebp.owat.lib.datastructure.matrix.linked.utils.nodePosition.FixedNode.FixedPosition.NORTH_EAST;
import static com.ebp.owat.lib.datastructure.matrix.linked.utils.nodePosition.FixedNode.FixedPosition.SOUTH_WEST;

/**
 * A matrix whose underlying structure is a linked lattice.
 * @param <T>
 */
public class LinkedScramblingMatrix<T> extends Matrix<T> {
	/**
	 * The frequency of how often reference nodes are added.
	 */
	private static final long ADD_REFERENCE_FREQUENCY = 100;


	private long curAddCount = 0;

	/** The number of elements held in the matrix. */
	protected long numElementsHeld = 0;


	/** The number of rows held by this object. */
	protected long numRows = 0L;

	/** The number of columns held by this object. */
	protected long numCols = 0L;

	/**
	 * @return If the added node should be made a reference.
	 */
	private boolean addedNode(){
		this.curAddCount++;
		if(curAddCount >= ADD_REFERENCE_FREQUENCY){
			this.curAddCount = 0;
			return true;
		}
		return false;
	}

	/** The nodes we have to keep track of. */
	protected Collection<NodePosition<T>> referenceNodes = new LinkedList<>();

	private void initFirstNode(){
		if(this.hasRowsCols()){
			throw new IllegalStateException("Cannot init nodes when we already have them.");
		}

		LinkedMatrixNode<T> headNode = new LinkedMatrixNode<>();
		this.numCols++;
		this.numRows++;

		for(FixedNode.FixedPosition curPos : FixedNode.FixedPosition.values()){
			this.referenceNodes.add(
				new FixedNode<>(this, headNode, curPos)
			);
		}
	}

	/**
	 * Inits this matrix if needed.
	 * @return If this actually init'ed the matrix.
	 */
	private boolean initIfNoRowsCols(){
		if(!this.hasRowsCols()){
			this.initFirstNode();
			return true;
		}
		return false;
	}

	/**
	 * Resets all the node positions in the matrix.
	 * @return If any of the positions moved.
	 */
	private boolean resetNodePositions(){
		boolean movedNodes = false;
		for(NodePosition<T> curPos : this.referenceNodes){
			movedNodes |= curPos.resetPosition();
		}
		return movedNodes;
	}

	/**
	 * Gets the node closest to the coordinate given.
	 * @param coord The coordinate we want to get.
	 * @return The node closest node to the coordinate given.
	 */
	private NodePosition<T> getClosestHeldPosition(MatrixCoordinate coord){
		NodePosition<T> output = null;
		for(NodePosition<T> curPosition : this.referenceNodes){
			if(output == null){
				output = curPosition;
				continue;
			}
			if(DistanceCalc.nodeIsCloserThan(coord, curPosition, output)){
				output = curPosition;
			}
		}
		if(output == null){
			//TODO:: handle this better?
			throw new IllegalStateException("No nodes held.");
		}
		return output.clone();
	}

	/**
	 * Gets the matrixNode at the coordinate given.
	 * @param coord The coordinate of the node to get.
	 * @return The node at the coordinate given.
	 */
	private LinkedMatrixNode<T> getMatrixNode(MatrixCoordinate coord){
		MatrixValidator.throwIfNotOnMatrix(this, coord);
		//TODO:: returns null sometimes, unacceptable.

		NodePosition<T> curPosition = this.getClosestHeldPosition(coord);

		while(!curPosition.baseCoordEquals(coord)){
			if(curPosition.getY() > coord.getY()){
				curPosition.moveNorth();
			}
			if(curPosition.getY() < coord.getY()){
				curPosition.moveSouth();
			}
			if(curPosition.getX() > coord.getX()){
				curPosition.moveEast();
			}
			if(curPosition.getX() < coord.getX()){
				curPosition.moveWest();
			}
		}
		return curPosition.getNode();
	}

	/**
	 * Gets a fixed position's node.
	 * @param fixedPosition The fixed position to get the node of.
	 * @return The node at the fixed position.
	 */
	private LinkedMatrixNode<T> getMatrixNode(FixedPosition fixedPosition){
		MatrixValidator.throwIfNoRowsCols(this);

		for(NodePosition<T> curNode : this.referenceNodes){
			if(curNode instanceof FixedNode && ((FixedNode<T>) curNode).position == fixedPosition){
				return curNode.getNode();
			}
		}

		throw new IllegalStateException("Invalid fixed position given.");
	}

	private LinkedMatrixNode<T> getMatrixNode(long xIn, long yIn){
		return this.getMatrixNode(new MatrixCoordinate(this, xIn, yIn));
	}

	@Override
	public void addRow() {
		if(this.initIfNoRowsCols()){
			return;
		}

		this.numRows++;
		LinkedMatrixNode<T> lastNode = new LinkedMatrixNode<>();
		lastNode.setNorth(this.getMatrixNode(FixedPosition.SOUTH_WEST));
		for(long l = 1; l < this.getNumCols(); l++){
			LinkedMatrixNode<T> curNode = new LinkedMatrixNode<>();

			lastNode.setEast(curNode);

			curNode.getWest().getNorth().getEast().setSouth(curNode);

			if(this.addedNode()){
				this.referenceNodes.add(new NodePosition<>(this, curNode));
			}

			lastNode = curNode;
		}

		this.resetNodePositions();
	}

	@Override
	public void addCol() {
		if(this.initIfNoRowsCols()){
			return;
		}

		this.numCols++;
		LinkedMatrixNode<T> lastNode = new LinkedMatrixNode<>();
		lastNode.setEast(this.getMatrixNode(FixedPosition.NORTH_EAST));
		for(long l = 1; l < this.getNumRows(); l++){
			LinkedMatrixNode<T> curNode = new LinkedMatrixNode<>();

			lastNode.setWest(curNode);

			curNode.getNorth().getEast().getSouth().setWest(curNode);

			if(this.addedNode()){
				this.referenceNodes.add(new NodePosition<>(this, curNode));
			}

			lastNode = curNode;
		}

		this.resetNodePositions();
	}

	private void moveOrRemoveBordering(Direction dir){
		List<NodePosition<T>> toRemove = new LinkedList<>();
		for(NodePosition<T> curPos : this.referenceNodes){
			if(curPos.getNode().isBorder(dir)){
				if(!curPos.move(dir.opposite())){
					toRemove.add(curPos);
				}
			}
		}
		for (NodePosition<T> curToRem : toRemove){
			this.referenceNodes.remove(curToRem);
		}
	}

	@Override
	public List<T> removeRow() throws IndexOutOfBoundsException {
		if(!this.hasRowsCols()){
			return null;
		}

		List<T> output = new LongLinkedList<>();

		LinkedMatrixNode<T> node = this.getMatrixNode(SOUTH_WEST);

		this.moveOrRemoveBordering(SOUTH);

		do{
			output.add(node.getValue());
			LinkedMatrixNode<T> upper = node.getNorth();
			upper.setSouth(null);

			node.setNorth(null);
			node.setWest(null);

			node = node.getEast();
		}while(node != null);

		this.resetNodePositions();
		return output;
	}

	@Override
	public List<T> removeCol() throws IndexOutOfBoundsException {
		if(!this.hasRowsCols()){
			return null;
		}
		//TODO:: move node positions left one in last col
		LinkedMatrixNode<T> node = this.getMatrixNode(NORTH_EAST);

		this.moveOrRemoveBordering(EAST);


		List<T> output = new LongLinkedList<>();

		do{
			output.add(node.getValue());
			LinkedMatrixNode<T> west = node.getWest();
			west.setEast(null);

			node.setWest(null);
			node.setNorth(null);

			node = node.getSouth();
		}while(node != null);

		this.resetNodePositions();
		return output;
	}

	@Override
	public T setValue(MatrixCoordinate nodeToReplace, T newValue) {
		LinkedMatrixNode<T> node = this.getMatrixNode(nodeToReplace);

		T valToReturn = node.getValue();
		node.setValue(newValue);
		this.numElementsHeld++;

		return valToReturn;
	}

	@Override
	public boolean hasValue(MatrixCoordinate coordinate) {
		LinkedMatrixNode<T> node = this.getMatrixNode(coordinate);

		return node.hasValue();
	}

	@Override
	public T clearNode(MatrixCoordinate nodeToClear) {
		LinkedMatrixNode<T> node = this.getMatrixNode(nodeToClear);

		this.numElementsHeld--;
		return node.clearValue();
	}

	@Override
	public long getNumCols() {
		return this.numCols;
	}

	@Override
	public long getNumRows() {
		return this.numRows;
	}

	@Override
	public long numElements() {
		return this.numElementsHeld;
	}

	@Override
	public T get(MatrixCoordinate coordIn) {
		return this.getMatrixNode(coordIn).getValue(this.getDefaultValue());
	}

	@Override
	public List<T> getCol(MatrixCoordinate coordIn) {
		MatrixValidator.throwIfNotOnMatrix(this, coordIn);

		LongLinkedList<T> output = new LongLinkedList<>();
		LinkedMatrixNode<T> node = this.getMatrixNode(coordIn.getX(), 0);
		output.add(node.getValue());

		do{
			node = node.getDir(Direction.SOUTH);
			output.add(node.getValue());
		}while(!node.isBorder(Direction.SOUTH));

		return output;
	}

	@Override
	public List<T> getRow(MatrixCoordinate coordIn) {
		MatrixValidator.throwIfNotOnMatrix(this, coordIn);

		LongLinkedList<T> output = new LongLinkedList<>();
		LinkedMatrixNode<T> node = this.getMatrixNode(0, coordIn.getY());
		output.add(node.getValue());

		do{
			node = node.getDir(EAST);
			output.add(node.getValue());
		}while(!node.isBorder(EAST));

		return output;
	}

	@Override
	protected Matrix<T> getNewInstance() {
		return new HashedScramblingMatrix<>();
	}
}
