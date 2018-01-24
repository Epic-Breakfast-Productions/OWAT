package com.ebp.owat.lib.datastructure.set;

import java.io.Serializable;

/**
 * The value of the LongLinkedList class.
 *
 * Created by Greg Stewart on 5/28/17.
 */
class LongListNode<T> implements Serializable, Cloneable{
	private LongListNode<T> nextNode = null;
	private LongListNode<T> prevNode = null;
	private T data;
	
	/**
	 * Basic constructor. Sets nothing.
	 */
	public LongListNode(){
	
	}
	
	/**
	 * Constructor to take in the data held.
	 * @param data The data this value should hold.
	 */
	public LongListNode(T data){
		this();
		this.setData(data);
	}
	
	/**
	 * Constructor to set all the elements of this value.
	 * @param data The data this value is to hold.
	 * @param previousNode The value before this value.
	 * @param nextNode The value after this value.
	 */
	public LongListNode(T data, LongListNode<T> previousNode, LongListNode<T> nextNode){
		this(data);
		this.setPrev(previousNode);
		this.setNext(nextNode);
	}
	
	/**
	 * Gets the data held by this value.
	 * @return The data held by this value.
	 */
	public T getData(){
		return data;
	}
	
	/**
	 * Sets the data held by this value.
	 * @param data The new data for this value.
	 * @return This LongListNode object.
	 */
	public LongListNode<T> setData(T data){
		this.data = data;
		return this;
	}
	
	/**
	 * Replaces the data in this value. Semmantically the same as setData, but this returns the old data.
	 * @param data The new data for this value.
	 * @return The data that was held by this value.
	 */
	public T replaceData(T data){
		T curData = this.data;
		this.data = data;
		return data;
	}
	
	/**
	 * Gets the next value.
	 * @return The next value.
	 */
	public LongListNode<T> next(){
		return this.nextNode;
	}
	
	/**
	 * Sets the next value. Automatically has the next value set this one to be its previous.
	 * @param next The new value that is set to be next.
	 * @return This LongListNode object.
	 */
	public LongListNode<T> setNext(LongListNode<T> next){
		this.nextNode = next;
		if(this.nextNode != null && this.nextNode.prev() != this){
			this.nextNode.setPrev(this);
		}
		return this;
	}
	
	/**
	 * Replaces the next value. Semmantically the same as setNext(), but returns the value that was replaced.
	 * @param next The value to set the next value to.
	 * @return The former next value.
	 */
	public LongListNode<T> replaceNext(LongListNode<T> next){
		LongListNode<T> curNext = this.nextNode;
		this.setNext(next);
		return curNext;
	}
	
	/**
	 * Gets the previous value.
	 * @return The value previous to this one in the list.
	 */
	public LongListNode<T> prev(){
		return this.prevNode;
	}
	
	/**
	 * Sets the value previous to this one.
	 * @param prev The new value to be previous to this one.
	 * @return This LongListNode object.
	 */
	public LongListNode<T> setPrev(LongListNode<T> prev){
		this.prevNode = prev;
		if(this.prevNode != null && this.prevNode.next() != this){
			this.prevNode.setNext(this);
		}
		return this;
	}
	
	/**
	 * Replaces the value previous to this one. Semmantically the same as setPrev(), but returns the former previous value.
	 * @param prev The new value to be previous to this one.
	 * @return The former previous value.
	 */
	public LongListNode<T> replacePrev(LongListNode<T> prev){
		LongListNode<T> curPrev = this.prevNode;
		this.setPrev(prev);
		return curPrev;
	}
	
	public boolean hasPrev(){
		return this.prev() != null;
	}
	
	public boolean hasNext(){
		return this.next() != null;
	}
}
