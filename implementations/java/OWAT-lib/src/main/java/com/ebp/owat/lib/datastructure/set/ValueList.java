package com.ebp.owat.lib.datastructure.set;

import java.util.Collection;

/**
 * Describes a list of data nodes for the OWAT process.
 *
 * Created by Greg Stewart on 5/28/17.
 */
public class ValueList<Value> extends LongLinkedList<Value> {
	/**
	 * The types of lists a ValueList can be.
	 */
	public enum Type {
		ROW,COL,NA
	}
	
	/** The type to set when a type is not given. */
	public static final Type DEFAULT_TYPE = Type.NA;
	
	/**
	 * The type of list this is.
	 */
	public final Type type;
	
	/**
	 * Basic constructor. Sets type to {@link ValueList#DEFAULT_TYPE DEFAULT_TYPE}
	 */
	public ValueList(){
		this.type = DEFAULT_TYPE;
	}
	
	/**
	 * Constructor to set the type.
	 * @param t The type to give this list.
	 */
	public ValueList(Type t){
		if(t == null){
			this.type = DEFAULT_TYPE;
		}else {
			this.type = t;
		}
	}
	
	/**
	 * Constructor to set the type, and an initial set of nodes.
	 * @param t The type to give this list. If null, sets to {@link ValueList#DEFAULT_TYPE DEFAULT_TYPE}
	 * @param collection The initial set to give the list.
	 */
	public ValueList(Type t, Collection<Value> collection){
		this(t);
		this.addAll(collection);
	}
	
	/**
	 * Constructor to setup the list with an  initial set of nodes. Type defaults to {@link ValueList#DEFAULT_TYPE DEFAULT_TYPE}.
	 * @param collection The initial set to give the list.
	 */
	public ValueList(Collection<Value> collection){
		this();
		this.addAll(collection);
	}
}
