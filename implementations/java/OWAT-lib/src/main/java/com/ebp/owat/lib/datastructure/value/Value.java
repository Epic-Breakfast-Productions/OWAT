package com.ebp.owat.lib.datastructure.value;

public abstract class Value<T> {
	protected byte flags = 0;
	
	
	public boolean isOriginalData(){
		return ValueFlag.IS_ORIGINAL.getFlag(this.flags);
	}
	
	public abstract T getValue();
}
