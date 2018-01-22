package com.ebp.owat.lib.datastructure.value;

/**
 *  A Node that holds a ByteValue.
 *
 * Created by Greg Stewart on 3/26/17.
 */
public class ObjValue<T> extends Value<T> {
	private final T value;
	
	public ObjValue(T value) {
		this.value = value;
	}
	
	public ObjValue(T value, boolean isOriginal){
		this(value);
		if(isOriginal){
			this.flags = ValueFlag.IS_ORIGINAL.setFlag(this.flags);
		}
	}
	
	@Override
	public T getValue() {
		return this.value;
	}
}
