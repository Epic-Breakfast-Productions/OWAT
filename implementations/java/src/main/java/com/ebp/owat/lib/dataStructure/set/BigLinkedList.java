package com.ebp.owat.lib.dataStructure.set;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Defines a big linked list, to work well with large numbers of nodes.
 *
 * Created by Greg Stewart on 4/1/17.
 */
public abstract class BigLinkedList<E> extends LinkedList {
	/**
	 * The types of lists a NodeList can be.
	 */
	public enum Type {
		ROW,COL,NA
	}
	
	/**
	 * The type of list this is.
	 */
	private final Type type;
	
	/**
	 * Basic constructor. Sets the type to NA.
	 */
	public BigLinkedList(){
		this.type = Type.NA;
	}
	
	/**
	 * Creates an empty NodeList and sets its type.
	 * @param typeIn The type to set this to.
	 */
	public BigLinkedList(Type typeIn){
		this.type = typeIn;
	}
	
	/**
	 * Gets the type of list this is.
	 *
	 * @return The type of list this is.
	 */
	public Type getType(){
		return this.type;
	}
	
	/**
	 * DO NOT USE, use listSize() instead.
	 * @return nothing. Always throws OwatNodeSetException saying to use listSize() instead.
	 */
	@Override
	public int size() throws OwatNodeSetException{
		throw new OwatNodeSetException("Due to the fact that this can return values greater than MAXINT, please use the seprate function, listSize();.");
	}
	
	/**
	 * Gets the size of this list.
	 *
	 * @return The size of this list.
	 */
	public BigInteger listSize(){
		if(super.size() != Integer.MAX_VALUE){
			return BigInteger.valueOf(super.size());
		}
		ListIterator<E> curSpot = this.listIterator(Integer.MAX_VALUE);
		BigInteger curCount = BigInteger.valueOf(Integer.MAX_VALUE);
		
		while(curSpot.hasNext()){
			curCount = curCount.add(BigInteger.ONE);
		}
		return curCount;
	}
}
