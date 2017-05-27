package com.ebp.owat.lib.dataStructure.set;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Defines a big linked list, to work well with large numbers of nodes.
 *
 * Created by Greg Stewart on 4/1/17.
 */
public class LongLinkedList<E> extends LinkedList {
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
	public LongLinkedList(){
		this.type = Type.NA;
	}
	
	/**
	 * Creates an empty NodeList and sets its type.
	 * @param typeIn The type to set this to.
	 */
	public LongLinkedList(Type typeIn){
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
		throw new OwatNodeSetException("Due to the fact that this can return values greater than MAXINT, please use the separate function, listSize();.");
	}
	
	/**
	 * Gets the size of this list.
	 *
	 * @return The size of this list.
	 */
	public long listSize(){
		if(super.size() != Integer.MAX_VALUE){
			return super.size();
		}
		ListIterator<E> curSpot = this.listIterator(Integer.MAX_VALUE);
		long curCount = Integer.MAX_VALUE;
		
		while(curSpot.hasNext()){
			curCount++;
		}
		return curCount;
	}
	
	/**
	 * Gets a sub list of this list.
	 *
	 * @param numToGet The number of nodes to get. If not enough nodes, just puts in as many as are there.
	 * @param cutListOff If we are to remove these nodes from the list.
	 * @return The list of nodes from this list.
	 */
	public LongLinkedList<E> getSubList(long numToGet, boolean cutListOff){
		LongLinkedList<E> output = new LongLinkedList<>();
		
		if(cutListOff){
			long listSize = this.listSize();
			for(long cur = 0; cur < numToGet && listSize > 0; cur++){
				output.add(this.removeFirst());
			}
		}else{
			Iterator<E> it = this.listIterator();
			long cur = 0;
			while(cur < numToGet && it.hasNext()){
				output.add(it.next());
				cur++;
			}
		}
		return output;
	}
	
	@Override
	public boolean addAll(int i, Collection collection) {
		//TODO:: redo this to work with our way of doing this long linked list
		return super.addAll(i, collection);
	}
	
	@Override
	public Object get(int i) {
		//TODO:: redo this to work with our way of doing this long linked list
		return super.get(i);
	}
	
	@Override
	public Object set(int i, Object o) {
		//TODO:: redo this to work with our way of doing this long linked list
		return super.set(i, o);
	}
	
	@Override
	public void add(int i, Object o) {
		//TODO:: redo this to work with our way of doing this long linked list
		super.add(i, o);
	}
	
	@Override
	public Object remove(int i) {
		//TODO:: redo this to work with our way of doing this long linked list
		return super.remove(i);
	}
	
	@Override
	public int indexOf(Object o) {
		//TODO:: redo this to work with our way of doing this long linked list
		return super.indexOf(o);
	}
	
	@Override
	public int lastIndexOf(Object o) {
		//TODO:: redo this to work with our way of doing this long linked list
		return super.lastIndexOf(o);
	}
	
	@Override
	public boolean isEmpty() {
		return this.listSize() == 0;
	}
}
