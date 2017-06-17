package com.ebp.owat.lib.dataStructure.set;

import java.io.Serializable;
import java.util.*;

/**
 * Defines a long linked list, a linked list that inherently works using a long instead of int.
 *
 * TODO:: Organize methods on what type of operation they perform (add/remove/etc)
 *
 * Created by Greg Stewart on 4/1/17.
 */
public class LongLinkedList<E> implements Serializable, Cloneable, Iterable<E>, Collection<E>, Deque<E>, List<E>, Queue<E> {
	/** The default capacity a list would be initialized with. */
	public static final long DEFAULT_CAPACITY = Long.MAX_VALUE;
	/** The capacity this list should be set to. Default capacity is {@link Long#MAX_VALUE Long.MAX_VALUE}. */
	private long capacity = DEFAULT_CAPACITY;
	/** The length of the list. */
	private long length = 0;
	/** The first node in this list. */
	private LongListNode<E> first = null;
	/** The last node in this list. */
	private LongListNode<E> last = null;
	
	/**
	 * Default constructor. Creates an empty list.
	 */
	public LongLinkedList(){}
	
	/**
	 * Constructor that creates a list containing the elements given.
	 * @param c The collection of elements to put into the list.
	 */
	public LongLinkedList(Collection<E> c){
		this();
		this.addAll(c);
	}
	
	/**
	 * Constructor to create a list with the capacity given.
	 * @param l The capacity this list should max out at.
	 */
	public LongLinkedList(long l){
		this();
		this.capacity = l;
	}
	
	/**
	 * Constructor to create this list with a set max capacity and a beginning set of elements.
	 * @param l The max capacity to give this list.
	 * @param c The collection of elements to start this list off with.
	 */
	public LongLinkedList(long l, Collection<E> c){
		this(l);
		this.addAll(c);
	}
	
	/**
	 * Gets the capacity this list is set to.
	 * @return The capacity of this list.
	 */
	public long getCapacity(){
		return this.capacity;
	}
	
	/**
	 * Determines if this list is at capacity or not.
	 * @return False if not at capacity, true if at capacity.
	 */
	public boolean atCapacity(){//TODO:: test
		return this.length >= capacity;
	}
	
	/**
	 * Determines if this list is at capacity or not after adding a theoretical number of nodes.
	 * @param l The theoretical number of nodes to be added.
	 * @return If this list is at capacity or not after adding a theoretical number of nodes.
	 */
	public boolean atCapacity(long l){//TODO:: test
		return this.length + l > capacity;
	}
	
	/**
	 * Throws an IllegalStateException if the list is at capacity. Use before any operation that adds elements to the list.
	 */
	private void throwIfAtCapacity(){
		if(this.atCapacity()){
			throw new IllegalStateException("List is already at capacity. Cannot add more to the list.");
		}
	}
	
	/**
	 * Throws an IllegalStateException if the list is at capacity, or will be if l number of nodes are added. Use before any operation that adds elements to the list.
	 * @param l The theoretical number of nodes to be added.
	 */
	private void throwIfAtCapacity(long l){
		if(this.atCapacity(l)){
			throw new IllegalStateException("List is already at capacity. Cannot add more to the list.");
		}
	}
	
	/**
	 * Throws an NoSuchElementException if the list is empty. Use before any operation that removes elements from the list.
	 */
	private void throwIfEmpty(){
		if(this.isEmpty()){
			throw new NoSuchElementException("List is empty. Cannot remove a node.");
		}
	}
	
	/**
	 * Determines if this list's length is greater than Integer.MAX_INT
	 * @return If this list's length is greater than Integer.MAX_INT
	 */
	public boolean lengthGTMaxInt(){
		return this.length > Integer.MAX_VALUE;
	}
	
	/**
	 * Throws an IllegalStateException if the list has more than {@link Integer#MAX_VALUE Integer.MAX_VALUE} elements in it.
	 */
	private void throwIfLengthGTMaxInt(){
		if(this.lengthGTMaxInt()){
			throw new IllegalStateException("List has more elements than Integer.MAX_INT. Please use the other similarly named method made for longs.");
		}
	}
	
	/**
	 * Throws an IndexOutOfBoundsException if the index given is out of bounds.
	 * @param i The index to test.
	 */
	private void throwIfIndexOutOfBounds(long i){
		if(i > this.length || i < 0){
			throw new IndexOutOfBoundsException("Index given is out of bounds.");
		}
	}
	
	/**
	 * Removes a given node. Assumes the node is part of this list.
	 * @param node The node in this list that is to be removed.
	 * @throws NoSuchElementException If the list is empty.
	 */
	private void removeNode(LongListNode<E> node){
		if(node == null){
			throw new IllegalArgumentException("Node given cannot be null.");
		}
		this.throwIfEmpty();
		if(this.first == node){
			this.first = node.next();
			this.first.setPrev(null);
		}else if(this.last == node){
			this.last = node.prev();
			this.last.setNext(null);
		}else{
			node.prev().setNext(node.next());
		}
		this.length--;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////// Queue/Dequeue methods /////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void addFirst(E e) {
		this.throwIfAtCapacity();
		this.length++;
		if(this.first == null){
			this.first = new LongListNode<>(e);
			this.last = this.first;
		}else{
			this.first.setPrev(new LongListNode<>(e));
			this.first = this.first.prev();
		}
	}
	
	@Override
	public void addLast(E e) {
		this.throwIfAtCapacity();
		this.length++;
		if(this.last == null){
			this.last = new LongListNode<>(e);
			this.first = this.last;
		}else{
			this.last.setNext(new LongListNode<>(e));
			this.last = this.last.next();
		}
	}
	
	@Override
	public boolean offerFirst(E e) {
		try{
			this.addFirst(e);
		}catch (IllegalStateException ex){
			return false;
		}
		return true;
	}
	
	@Override
	public boolean offerLast(E e) {
		try{
			this.addLast(e);
		}catch (IllegalStateException ex){
			return false;
		}
		return true;
	}
	
	@Override
	public E removeFirst() {
		this.throwIfEmpty();
		this.length--;
		E value = this.first.getData();
		this.first = this.first.next();
		return value;
	}
	
	@Override
	public E removeLast() {
		this.throwIfEmpty();
		this.length--;
		E value = this.last.getData();
		this.last = this.last.prev();
		return value;
	}
	
	@Override
	public E pollFirst() {
		try{
			return this.removeFirst();
		}catch (NoSuchElementException ex){
			return null;
		}
	}
	
	@Override
	public E pollLast() {
		try{
			return this.removeLast();
		}catch (NoSuchElementException ex){
			return null;
		}
	}
	
	@Override
	public E getFirst() {
		this.throwIfEmpty();
		return this.first.getData();
	}
	
	@Override
	public E getLast() {
		this.throwIfEmpty();
		return this.last.getData();
	}
	
	@Override
	public E peekFirst() {
		try{
			return this.getFirst();
		}catch (NoSuchElementException ex){
			return null;
		}
	}
	
	@Override
	public E peekLast() {
		try{
			return this.getLast();
		}catch (NoSuchElementException ex){
			return null;
		}
	}
	
	@Override
	public boolean removeFirstOccurrence(Object o) {//TODO:: test
		ListIterator<LongListNode<E>> it = this.listNodeIterator();
		
		while(it.hasNext()){
			LongListNode<E> curNode = it.next();
			E curVal = curNode.getData();
			if(o == null){
				if(curVal == null){
					this.removeNode(curNode);
					return true;
				}
			}else{
				if(curVal.equals(o)){
					this.removeNode(curNode);
					return true;
				}
			}
		}//foreach node
		return false;
	}
	
	@Override
	public boolean removeLastOccurrence(Object o) {
		ListIterator<LongListNode<E>> it = this.descendingListNodeIterator();
		while(it.hasNext()){
			LongListNode<E> curNode = it.next();
			E curVal = curNode.getData();
			if(o == null){
				if(curVal == null){
					this.removeNode(curNode);
					return true;
				}
			}else{
				if(curVal.equals(o)){
					this.removeNode(curNode);
					return true;
				}
			}
		}//foreach node
		return false;
	}
	
	@Override
	public boolean offer(E e) {
		return this.offerLast(e);
	}
	
	@Override
	public E remove() {
		return this.removeFirst();
	}
	
	@Override
	public E poll() {
		return this.pollFirst();
	}
	
	@Override
	public E element() {
		return this.getFirst();
	}
	
	@Override
	public E peek() {
		return this.peekFirst();
	}
	
	@Override
	public void push(E e) {
		this.addFirst(e);
	}
	
	@Override
	public E pop() {
		return removeFirst();
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////// List methods //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public boolean addAll(int i, Collection<? extends E> collection) {
		return this.addAll((long)i, collection);
	}
	
	/**
	 * Same as {@link LongLinkedList#addAll(int, Collection) addAll(int, Collection)}, except uses a long instead of an int.
	 * @param i
	 * @param collection
	 * @return
	 */
	public boolean addAll(long i, Collection<? extends E> collection) {//TODO:: test
		this.throwIfIndexOutOfBounds(i);
		this.throwIfAtCapacity(collection.size());
		ListIterator<LongListNode<E>> it = this.listNodeIterator();
		LongListNode<E> nodeToInsertAt = this.getNode(i);
		for(E curElement : collection){
			new LongListNode<E>(
					curElement,
					nodeToInsertAt.prev(),
					nodeToInsertAt
			);
		}
		return false;
	}
	
	@Override
	public E get(int i) {
		return this.get((long)i);
	}
	
	/**
	 * Same as {@link LongLinkedList#get(int) get(int)}, except uses a long instead of an int.
	 * @param i
	 * @return
	 */
	public E get(long i){//TODO:: test
		this.throwIfIndexOutOfBounds(i);
		ListIterator<E> it = this.listIterator();
		long count = 0;
		while(it.hasNext()){
			if(count == i){
				return it.next();
			}
			it.next();
			count++;
		}
		return null;
	}
	
	/**
	 * Gets the actual node from the list at the index.
	 * @param i The index of the node to get.
	 * @return The LongListNode at the given index
	 * @throws IndexOutOfBoundsException If the index given is out of bounds.
	 */
	private LongListNode<E> getNode(long i){
		this.throwIfIndexOutOfBounds(i);
		ListIterator<LongListNode<E>> it = this.listNodeIterator();
		long count = 0;
		while(it.hasNext()){
			if(count == i){
				return it.next();
			}
			it.next();
			count++;
		}
		throw new IllegalStateException("Did not return with a LongListNode. This should not happen.");
	}
	
	@Override
	public E set(int i, E e) {
		return this.set((long)i, e);
	}
	
	/**
	 * Same as {@link LongLinkedList#set(int, E) set(int, E)}, except that this takes a long instead of an int.
	 * @param i
	 * @param e
	 * @return
	 */
	public E set(long i, E e) {//TODO:: test
		this.throwIfIndexOutOfBounds(i);
		ListIterator<LongListNode<E>> it = this.listNodeIterator();
		long count = 0;
		while(it.hasNext()){
			if(count == i){
				LongListNode<E> curNode = it.next();
				E val = curNode.getData();
				curNode.setData(e);
				return val;
			}
			it.next();
			count++;
		}
		return null;
	}
	
	@Override
	public void add(int i, E e) {
		this.add((long)i, e);
	}
	
	/**
	 * Same as {@link LongLinkedList#add(int, E) add(int, E)}, except uses a long instead of an int.
	 * @param i
	 * @param e
	 */
	public void add(long i, E e) {//TODO:: test
		this.throwIfIndexOutOfBounds(i);
		this.throwIfAtCapacity();
		ListIterator<LongListNode<E>> it = this.listNodeIterator();
		long count = 0;
		while(it.hasNext()){
			if(count == i){
				LongListNode<E> curNode = it.next();
				new LongListNode<>(e, curNode.prev(), curNode);
				return;
			}
			it.next();
			count++;
		}
	}
	
	@Override
	public E remove(int i) {
		return this.remove((long)i);
	}
	
	public E remove(long i) {//TODO:: test
		this.throwIfIndexOutOfBounds(i);
		this.throwIfEmpty();
		ListIterator<LongListNode<E>> it = this.listNodeIterator();
		
		long count = 0;
		while(it.hasNext()){
			if(count == i){
				LongListNode<E> curNode = it.next();
				E val = curNode.getData();
				curNode.prev().setNext(curNode.next());
				return val;
			}
			it.next();
			count++;
		}
		return null;
	}
	
	@Override
	public int indexOf(Object o) {//TODO:: test
		this.throwIfLengthGTMaxInt();
		return (int)this.indexOfL(o);
	}
	
	/**
	 * Same as {@link LongLinkedList#indexOf(Object) indexOf(Object)}, except returns a long instead of an int.
	 * @param o
	 * @return
	 */
	public long indexOfL(Object o) {//TODO:: test
		Iterator<E> it = this.iterator();
		long count = 0;
		while(it.hasNext()){
			if(o == null) {
				if (it.next() == null) {
					return count;
				}
			}else{
				if(o.equals(it.next())){
					return count;
				}
			}
			count++;
		}
		return -1;
	}
	
	@Override
	public int lastIndexOf(Object o) {
		this.throwIfLengthGTMaxInt();
		return (int)this.lastIndexOfL(o);
	}
	
	/**
	 * Same as {@link LongLinkedList#lastIndexOf(Object) lastIndexOf(Object)}, except returns a long instead of int.
	 * @param o
	 * @return
	 */
	public long lastIndexOfL(Object o) {//TODO:: test
		Iterator<E> it = this.descendingIterator();
		long count = this.length;
		while(it.hasNext()){
			if(o == null) {
				if (it.next() == null) {
					return count;
				}
			}else{
				if(o.equals(it.next())){
					return count;
				}
			}
			count--;
		}
		return -1;
	}
	
	@Override
	public List<E> subList(int i, int i1) {
		return this.subList((long) i, (long) i1);
	}
	
	/**
	 * Same as {@link LongLinkedList#subList(int, int) subList(int, int)}, except uses longs instead of ints.
	 *
	 * Throws IndexOutOfBoundsException if either is out of bounds.
	 * @param i
	 * @param i1
	 * @return
	 */
	public List<E> subList(long i, long i1) {//TODO:: test
		if(i > i1){
			throw new IllegalArgumentException("i can not be greater than i1.");
		}
		this.throwIfIndexOutOfBounds(i);
		this.throwIfIndexOutOfBounds(i1);
		long numToGet = i1 - i;
		
		LongLinkedList<E> output = new LongLinkedList<>();
		
		LongListNode<E> curNode = this.getNode(i);
		for(int j = 0; j < numToGet; j++){
			output.addLast(curNode.getData());
			curNode = curNode.next();
		}
		
		return output;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////// Collection methods /////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public int size() {//TODO:: test
		if(this.lengthGTMaxInt()){
			return Integer.MAX_VALUE;
		}
		return (int)this.length;
	}
	
	public long sizeL() {
		return this.length;
	}
	
	@Override
	public boolean isEmpty() {
		return this.length == 0;
	}
	
	@Override
	public boolean contains(Object o) {
		return this.indexOfL(o) != -1;
	}
	
	@Override
	public Object[] toArray() {//TODO:: test
		this.throwIfLengthGTMaxInt();
		Object[] output = new Object[(int)this.length];
		ListIterator<E> it = this.listIterator();
		for(int i = 0; i < output.length && it.hasNext(); i++){
			output[i] = it.next();
		}
		return output;
	}
	
	@Override
	public <T> T[] toArray(T[] ts) {
		throw new UnsupportedOperationException("This method is unsupported, and is not currently planned to be supported. If you need this method to be supported, please contact the developer.");
	}
	
	@Override
	public boolean add(E e) {
		if(this.atCapacity()){
			return false;
		}
		
		this.length++;
		if(this.first == null){
			this.first = new LongListNode<>(
					e
			);
			this.last = this.first;
		}else {
			this.last = new LongListNode<>(
					e,
					this.last,
					null
			);
		}
		return true;
	}
	
	@Override
	public boolean remove(Object o) {
		return this.removeFirstOccurrence(o);
	}
	
	@Override
	public boolean containsAll(Collection<?> collection) {//TODO:: test
		Iterator<?> it = collection.iterator();
		while(it.hasNext())
			if (!this.contains(it.next()))
				return false;
		return true;
	}
	
	/**
	 * Determines if all of the elements given are inside this list. Acts the same as {@link LongLinkedList#containsAll(Collection) containsAll(Collection)}, but runs in O(n) instead of O(n*m).
	 * @param collection The collection of objects to determine if they are in the list.
	 * @return If all of the elements given are inside this list.
	 */
	public boolean containsAllElements(Collection<E> collection){//TODO:: test
		Map<E, Boolean> tracker = new HashMap<>();
		
		Iterator<E> it = collection.iterator();
		while (it.hasNext()){
			tracker.put(it.next(), false);
		}
		
		it = this.listIterator();
		while (it.hasNext()){
			E curVal = it.next();
			if(tracker.containsKey(curVal)){
				tracker.replace(curVal, true);
			}
		}
		
		for(Map.Entry<E, Boolean> curEntry : tracker.entrySet()){
			if(!curEntry.getValue()){
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean addAll(Collection<? extends E> collection) {//TODO:: test
		if(collection == null){
			return false;
		}
		this.throwIfAtCapacity(collection.size());
		for(E curElement : collection){
			if(!this.add(curElement)){
				return false;
			}
		}
		return true;
	}
	
	@Override
	public boolean removeAll(Collection<?> collection) {//TODO:: test
		Iterator<?> it = collection.iterator();
		boolean changed = false;
		while(it.hasNext()){
			try {
				this.remove(it.next());
				changed = true;
			}catch(NoSuchElementException e){
				break;
			}
		}
		return changed;
	}
	
	@Override
	public boolean retainAll(Collection<?> collection) {//TODO:: test
		LongListNode curNode = this.first;
		
		if(this.length == 0){
			return false;
		}
		if(this.length == 1){
			if(!collection.contains(curNode.getData())){
				this.clear();
				return true;
			}else{
				return false;
			}
		}
		
		boolean changed = false;
		while (curNode != null){
			if(!collection.contains(curNode.getData())){
				changed = true;
				boolean isFirst = curNode == this.first;
				boolean isLast = curNode == this.first;
				
				if(isFirst && isLast){
					this.clear();
					break;
				}else if(isFirst){
					curNode = curNode.next();
					this.removeFirst();
				}else if(isLast){
					this.removeLast();
					curNode = null;
				}else{
					curNode.next().setPrev(curNode.prev());
					curNode = curNode.next();
				}
			}else{
				curNode = curNode.next();
			}
		}
		return changed;
	}
	
	@Override
	public void clear() {
		this.first = null;
		this.last = null;
		this.length = 0;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////// Iterable methods //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public Iterator<E> iterator() {
		return this.listIterator();
	}
	
	@Override
	public Iterator<E> descendingIterator() {
		return this.descendingListIterator();
	}
	
	@Override
	public ListIterator<E> listIterator() {//TODO:: test
		return new ListIterator<E>() {
			private LongListNode<E> curNode = first;
			private boolean startedIt = false;
			@Override
			public boolean hasNext() {
				return curNode != null ? curNode.hasNext() : false;
			}
			@Override
			public E next() {
				if(!this.hasNext()){
					throw new NoSuchElementException("No more elements to iterate through.");
				}
				if(!startedIt){
					startedIt = true;
					return curNode.getData();
				}
				curNode = curNode.next();
				return curNode.getData();
			}
			
			@Override
			public boolean hasPrevious() {
				return curNode != null ? curNode.hasPrev() : false;
			}
			
			@Override
			public E previous() {
				if(!this.hasPrevious()){
					throw new NoSuchElementException("No more elements to iterate through.");
				}
				curNode = curNode.prev();
				return curNode.getData();
			}
			
			@Override
			public int nextIndex() {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public int previousIndex() {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public void set(E e) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public void add(E e) {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	public ListIterator<E> descendingListIterator(){
		return new ListIterator<E>() {
			private LongListNode<E> curNode = last;
			private boolean startedIt = false;
			
			@Override
			public boolean hasNext() {
				return curNode != null ? curNode.hasPrev() : false;
			}
			
			@Override
			public E next() {
				if(!this.hasNext()){
					throw new NoSuchElementException("No more elements to iterate through.");
				}
				if(!startedIt){
					startedIt = true;
					return curNode.getData();
				}
				curNode = curNode.prev();
				return curNode.getData();
			}
			
			@Override
			public boolean hasPrevious() {
				return curNode != null ? curNode.hasNext() : false;
			}
			
			@Override
			public E previous() {
				if(!this.hasPrevious()){
					throw new NoSuchElementException("No more elements to iterate through.");
				}
				curNode = curNode.next();
				return curNode.getData();
			}
			
			@Override
			public int nextIndex() {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public int previousIndex() {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public void set(E e) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public void add(E e) {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	@Override
	public ListIterator<E> listIterator(int i) {
		return this.listIterator((long)i);
	}
	
	/**
	 * Same as {@link #listIterator(int) listIterator(int)}, except uses long, as per this class.
	 * @param i The index the ListIterator should be at.
	 * @return A ListIterator already at the element at the index given.
	 */
	public ListIterator<E> listIterator(long i) {//TODO:: test
		ListIterator<E> it = this.listIterator();
		for(long j = 0; j < i; j++){
			it.next();
		}
		return it;
	}
	
	/**
	 * Iterator that returns the actual nodes and not just the values. Used for internal methods only.
	 * @return ListIterator that returns nodes not values.
	 */
	private ListIterator<LongListNode<E>> listNodeIterator() {
		return new ListIterator<LongListNode<E>>() {
			private LongListNode<E> curNode = first;
			private boolean startedIt = false;
			@Override
			public boolean hasNext() {
				return curNode != null ? curNode.hasNext() : false;
			}
			@Override
			public LongListNode<E> next() {
				if(!this.hasNext()){
					throw new NoSuchElementException("No more elements to iterate through.");
				}
				if(!startedIt){
					startedIt = true;
					return curNode;
				}
				curNode = curNode.next();
				return curNode;
			}
			
			@Override
			public boolean hasPrevious() {
				return curNode != null ? curNode.hasPrev() : false;
			}
			
			@Override
			public LongListNode<E> previous() {
				if(!this.hasPrevious()){
					throw new NoSuchElementException("No more elements to iterate through.");
				}
				curNode = curNode.prev();
				return curNode;
			}
			
			@Override
			public int nextIndex() {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public int previousIndex() {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public void set(LongListNode<E> e) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public void add(LongListNode<E> e) {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	/**
	 * Descending iterator that returns the actual nodes and not just the values. Used for internal methods only.
	 * @return Descending ListIterator that returns nodes not values.
	 */
	private ListIterator<LongListNode<E>> descendingListNodeIterator() {
		return new ListIterator<LongListNode<E>>() {
			private LongListNode<E> curNode = last;
			private boolean startedIt = false;
			
			@Override
			public boolean hasNext() {
				return curNode != null ? curNode.hasPrev() : false;
			}
			
			@Override
			public LongListNode<E> next() {
				if(!this.hasNext()){
					throw new NoSuchElementException("No more elements to iterate through.");
				}
				if(!startedIt){
					startedIt = true;
					return curNode;
				}
				curNode = curNode.prev();
				return curNode;
			}
			
			@Override
			public boolean hasPrevious() {
				return curNode != null ? curNode.hasNext() : false;
			}
			
			@Override
			public LongListNode<E> previous() {
				if(!this.hasPrevious()){
					throw new NoSuchElementException("No more elements to iterate through.");
				}
				curNode = curNode.next();
				return curNode;
			}
			
			@Override
			public int nextIndex() {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public int previousIndex() {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public void set(LongListNode<E> e) {
				throw new UnsupportedOperationException();
			}
			
			@Override
			public void add(LongListNode<E> e) {
				throw new UnsupportedOperationException();
			}
		};
	}
}
