package com.ebp.owat.lib.datastructure.matrix.linked;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.ScrambleMatrix;
import com.ebp.owat.lib.datastructure.matrix.linked.utils.Direction;
import com.ebp.owat.lib.datastructure.matrix.linked.utils.LinkedMatrixNode;
import com.ebp.owat.lib.datastructure.matrix.linked.utils.nodePosition.FixedNode;
import com.ebp.owat.lib.datastructure.matrix.linked.utils.nodePosition.NodePosition;
import com.ebp.owat.lib.datastructure.matrix.utils.MatrixValidator;
import com.ebp.owat.lib.datastructure.matrix.utils.Plane;
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
public class LinkedScramblingMatrix<T> extends ScrambleMatrix<T> {
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
	 * @param destination The coordinate of the node to get.
	 * @return The node at the coordinate given.
	 */
	private LinkedMatrixNode<T> getMatrixNode(MatrixCoordinate destination){
		MatrixValidator.throwIfNotOnMatrix(this, destination);

		NodePosition<T> curPosition = this.getClosestHeldPosition(destination);

		while(!curPosition.baseCoordEquals(destination)){
			if(curPosition.getY() > destination.getY()){
				curPosition.moveNorth();
			}else if(curPosition.getY() < destination.getY()){
				curPosition.moveSouth();
			}
			if(curPosition.getX() > destination.getX()){
				curPosition.moveWest();
			}else if(curPosition.getX() < destination.getX()){
				curPosition.moveEast();//TODO:: this made alot of tests fail. why?
			}
		}
		LinkedMatrixNode<T> output = curPosition.getNode();

		if(output == null){
			throw new IllegalStateException("Failed to get a node. This should not happen.");
		}

		return output;
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
		lastNode.setWest(this.getMatrixNode(FixedPosition.NORTH_EAST));
		for(long l = 1; l < this.getNumRows(); l++){
			LinkedMatrixNode<T> curNode = new LinkedMatrixNode<>();

			lastNode.setSouth(curNode);

			curNode
				.getNorth()
				.getWest()
				.getSouth()
				.setEast(curNode);

			if(this.addedNode()){
				this.referenceNodes.add(new NodePosition<>(this, curNode));
			}

			lastNode = curNode;
		}

		this.resetNodePositions();
	}

	/**
	 * Moves or removes bordering positions held on a border given.
	 *
	 * Moves the node if possible; removes it if it didn't move.
	 *
	 * @param dir The direction of the border.
	 */
	private void moveOrRemoveBorderingPositions(Direction dir){
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

		this.moveOrRemoveBorderingPositions(SOUTH);

		if(this.getNumCols() <= 1) {
			do{
				if(node.hasValue()) {
					output.add(node.getValue());
					this.numElementsHeld--;
				}else{
					output.add(this.getDefaultValue());
				}
				node = node.getEast();
			}while(node != null);
			this.clear();
		}else{
			do{
				if(node.hasValue()) {
					output.add(node.getValue());
					this.numElementsHeld--;
				}else{
					output.add(this.getDefaultValue());
				}
				LinkedMatrixNode<T> upper = node.getNorth();
				upper.setSouth(null);

				node.setNorth(null);
				node.setWest(null);

				node = node.getEast();
			}while(node != null);

			this.numRows--;
			this.resetNodePositions();
		}

		return output;
	}

	@Override
	public List<T> removeCol() throws IndexOutOfBoundsException {
		if(!this.hasRowsCols()){
			return null;
		}
		LinkedMatrixNode<T> node = this.getMatrixNode(NORTH_EAST);

		this.moveOrRemoveBorderingPositions(EAST);

		List<T> output = new LongLinkedList<>();

		if(this.getNumCols() <= 1){
			do{
				if(node.hasValue()) {
					output.add(node.getValue());
					this.numElementsHeld--;
				}else{
					output.add(this.getDefaultValue());
				}
				node = node.getSouth();
			}while(node != null);
			this.clear();
		}else{
			do{
				if(node.hasValue()) {
					output.add(node.getValue());
					this.numElementsHeld--;
				}else{
					output.add(this.getDefaultValue());
				}
				LinkedMatrixNode<T> west = node.getWest();
				west.setEast(null);

				node.setWest(null);
				node.setNorth(null);

				node = node.getSouth();
			}while(node != null);
			this.numCols--;
			this.resetNodePositions();
		}

		return output;
	}

	@Override
	public T setValue(MatrixCoordinate nodeToReplace, T newValue) {
		LinkedMatrixNode<T> node = this.getMatrixNode(nodeToReplace);

		if(node.hasValue()){
			return node.setValue(newValue);
		}else{
			this.numElementsHeld++;
			node.setValue(newValue);
			return this.defaultValue;
		}
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

		if(node.getDir(SOUTH) == null){
			return output;
		}

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

		if(node.getDir(EAST) == null){
			return output;
		}

		do{
			node = node.getDir(EAST);
			output.add(node.getValue());
		}while(!node.isBorder(EAST));

		return output;
	}

	@Override
	protected LinkedScramblingMatrix<T> getNewInstance() {
		return new LinkedScramblingMatrix<>();
	}

	@Override
	public void clear() {
		this.referenceNodes.clear();
		this.numCols = 0;
		this.numRows = 0;
		this.numElementsHeld = 0;
	}

	//TODO:: override replaceRow/Col
	//TODO:: override iterator


	@Override
	public List<T> replaceRow(MatrixCoordinate matrixCoordinate, Collection<T> newValues) throws IndexOutOfBoundsException {
		//TODO
		return super.replaceRow(matrixCoordinate, newValues);
	}

	@Override
	public List<T> replaceCol(MatrixCoordinate matrixCoordinate, Collection<T> newValues) throws IndexOutOfBoundsException {
		//TODO
		return super.replaceCol(matrixCoordinate, newValues);
	}

	@Override
	public LinkedScramblingMatrix<T> getSubMatrix(MatrixCoordinate topLeft, long height, long width) {
		MatrixValidator.throwIfNotOnMatrix(this, topLeft);
		MatrixValidator.throwIfBadIndex(this,topLeft.getY() + height - 1, Plane.Y);
		MatrixValidator.throwIfBadIndex(this,topLeft.getX() + width - 1, Plane.X);

		LinkedScramblingMatrix<T> output = this.getNewInstance();
		output.grow(width, height);

		LinkedMatrixNode<T> rowStart = null;

		MatrixCoordinate worker = new MatrixCoordinate(output);

		for(int i = 0; i < height; i++){
			if(rowStart == null){
				rowStart = this.getMatrixNode(topLeft);
			}else{
				rowStart = rowStart.getSouth();
			}

			LinkedMatrixNode<T> cur = rowStart;

			worker.setY(i);

			for(int j = 0; j < width; j++){
				worker.setX(j);
				if(cur.hasValue()){
					output.setValue(worker, cur.getValue());
				}
				cur = cur.getEast();
			}
		}
		return output;
	}

	@Override
	public void replaceSubMatrix(Matrix<T> subMatrix, MatrixCoordinate topLeft, long height, long width) {
		MatrixValidator.throwIfNotOnMatrix(this, topLeft);
		MatrixValidator.throwIfBadIndex(this,topLeft.getY() + height - 1, Plane.Y);
		MatrixValidator.throwIfBadIndex(this,topLeft.getX() + width - 1, Plane.X);
		MatrixValidator.throwIfBadIndex(subMatrix, height - 1, Plane.Y);
		MatrixValidator.throwIfBadIndex(subMatrix, width - 1, Plane.X);

		//TODO
		super.replaceSubMatrix(subMatrix, topLeft, height, width);
	}


}
