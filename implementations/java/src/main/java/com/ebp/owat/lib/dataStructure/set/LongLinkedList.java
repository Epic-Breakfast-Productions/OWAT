package com.ebp.owat.lib.dataStructure.set;

import java.io.Serializable;
import java.util.*;

/**
 * Defines a big linked list, to work well with large numbers of nodes.
 *
 * Created by Greg Stewart on 4/1/17.
 */
public class LongLinkedList<E> implements Serializable, Cloneable, Iterable<E>, Collection<E>, Deque<E>, List<E>, Queue<E> {
	private long length = 0;
	private LongListNode<E> first;
	private LongListNode<E> last;
	
	
	
	@Override
	public void addFirst(E e) {
		//TODO
	}
	
	@Override
	public void addLast(E e) {
		//TODO
	}
	
	@Override
	public boolean offerFirst(E e) {
		//TODO
		return false;
	}
	
	@Override
	public boolean offerLast(E e) {
		//TODO
		return false;
	}
	
	@Override
	public E removeFirst() {
		//TODO
		return null;
	}
	
	@Override
	public E removeLast() {
		//TODO
		return null;
	}
	
	@Override
	public E pollFirst() {
		//TODO
		return null;
	}
	
	@Override
	public E pollLast() {
		//TODO
		return null;
	}
	
	@Override
	public E getFirst() {
		//TODO
		return null;
	}
	
	@Override
	public E getLast() {
		//TODO
		return null;
	}
	
	@Override
	public E peekFirst() {
		//TODO
		return null;
	}
	
	@Override
	public E peekLast() {
		//TODO
		return null;
	}
	
	@Override
	public boolean removeFirstOccurrence(Object o) {
		//TODO
		return false;
	}
	
	@Override
	public boolean removeLastOccurrence(Object o) {
		//TODO
		return false;
	}
	
	@Override
	public boolean offer(E e) {
		//TODO
		return false;
	}
	
	@Override
	public E remove() {
		//TODO
		return null;
	}
	
	@Override
	public E poll() {
		//TODO
		return null;
	}
	
	@Override
	public E element() {
		//TODO
		return null;
	}
	
	@Override
	public E peek() {
		//TODO
		return null;
	}
	
	@Override
	public void push(E e) {
		//TODO
	}
	
	@Override
	public E pop() {
		//TODO
		return null;
	}
	
	@Override
	public Iterator<E> descendingIterator() {
		//TODO
		return null;
	}
	
	@Override
	public boolean addAll(int i, Collection<? extends E> collection) {
		//TODO
		return false;
	}
	
	@Override
	public E get(int i) {
		//TODO
		return null;
	}
	
	@Override
	public E set(int i, E e) {
		//TODO
		return null;
	}
	
	@Override
	public void add(int i, E e) {
		//TODO
	}
	
	@Override
	public E remove(int i) {
		//TODO
		return null;
	}
	
	@Override
	public int indexOf(Object o) {
		//TODO
		return 0;
	}
	
	@Override
	public int lastIndexOf(Object o) {
		//TODO
		return 0;
	}
	
	@Override
	public ListIterator<E> listIterator() {
		//TODO
		return null;
	}
	
	@Override
	public ListIterator<E> listIterator(int i) {
		//TODO
		return null;
	}
	
	@Override
	public List<E> subList(int i, int i1) {
		//TODO
		return null;
	}
	
	@Override
	public int size() {
		//TODO
		return 0;
	}
	
	@Override
	public boolean isEmpty() {
		//TODO
		return false;
	}
	
	@Override
	public boolean contains(Object o) {
		//TODO
		return false;
	}
	
	@Override
	public Object[] toArray() {
		//TODO
		return new Object[0];
	}
	
	@Override
	public <T> T[] toArray(T[] ts) {
		//TODO
		return null;
	}
	
	@Override
	public boolean add(E e) {
		//TODO
		return false;
	}
	
	@Override
	public boolean remove(Object o) {
		//TODO
		return false;
	}
	
	@Override
	public boolean containsAll(Collection<?> collection) {
		//TODO
		return false;
	}
	
	@Override
	public boolean addAll(Collection<? extends E> collection) {
		//TODO
		return false;
	}
	
	@Override
	public boolean removeAll(Collection<?> collection) {
		//TODO
		return false;
	}
	
	@Override
	public boolean retainAll(Collection<?> collection) {
		//TODO
		return false;
	}
	
	@Override
	public void clear() {
		//TODO
	}
	
	@Override
	public Iterator<E> iterator() {
		//TODO
		return null;
	}
}
