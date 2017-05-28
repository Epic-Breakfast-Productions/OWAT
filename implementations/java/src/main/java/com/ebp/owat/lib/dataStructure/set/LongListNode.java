package com.ebp.owat.lib.dataStructure.set;

import java.io.Serializable;

/**
 * The node of the LongLinkedList class.
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
	 * @param data The data this node should hold.
	 */
	public LongListNode(T data){
		this();
		this.setData(data);
	}
	
	/**
	 * Constructor to set all the elements of this node.
	 * @param data The data this node is to hold.
	 * @param previousNode The node before this node.
	 * @param nextNode The node after this node.
	 */
	public LongListNode(T data, LongListNode<T> previousNode, LongListNode<T> nextNode){
		this(data);
		this.setPrev(previousNode);
		this.setNext(nextNode);
	}
	
	/**
	 * Gets the data held by this node.
	 * @return The data held by this node.
	 */
	public T getData(){
		return data;
	}
	
	/**
	 * Sets the data held by this node.
	 * @param data The new data for this node.
	 * @return This LongListNode object.
	 */
	public LongListNode<T> setData(T data){
		this.data = data;
		return this;
	}
	
	/**
	 * Replaces the data in this node. Semmantically the same as setData, but this returns the old data.
	 * @param data The new data for this node.
	 * @return The data that was held by this node.
	 */
	public T replaceData(T data){
		T curData = this.data;
		this.data = data;
		return data;
	}
	
	/**
	 * Gets the next node.
	 * @return The next node.
	 */
	public LongListNode<T> next(){
		return this.nextNode;
	}
	
	/**
	 * Sets the next node. Automatically has the next node set this one to be its previous.
	 * @param next The new node that is set to be next.
	 * @return This LongListNode object.
	 */
	public LongListNode<T> setNext(LongListNode<T> next){
		this.nextNode = next;
		if(this.nextNode != null){
			this.nextNode.setPrev(this);
		}
		return this;
	}
	
	/**
	 * Replaces the next node. Semmantically the same as setNext(), but returns the node that was replaced.
	 * @param next The node to set the next node to.
	 * @return The former next node.
	 */
	public LongListNode<T> replaceNext(LongListNode<T> next){
		LongListNode<T> curNext = this.nextNode;
		this.setNext(next);
		return curNext;
	}
	
	/**
	 * Gets the previous node.
	 * @return The node previous to this one in the list.
	 */
	public LongListNode<T> prev(){
		return this.prevNode;
	}
	
	/**
	 * Sets the node previous to this one.
	 * @param prev The new node to be previous to this one.
	 * @return This LongListNode object.
	 */
	public LongListNode<T> setPrev(LongListNode<T> prev){
		this.prevNode = prev;
		if(this.prevNode != null){
			this.prevNode.setNext(this);
		}
		return this;
	}
	
	/**
	 * Replaces the node previous to this one. Semmantically the same as setPrev(), but returns the former previous node.
	 * @param prev The new node to be previous to this one.
	 * @return The former previous node.
	 */
	public LongListNode<T> replacePrev(LongListNode<T> prev){
		LongListNode<T> curPrev = this.prevNode;
		this.setPrev(prev);
		return curPrev;
	}
}
