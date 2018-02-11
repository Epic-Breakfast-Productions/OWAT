package com.ebp.owat.lib.datastructure.value;

import java.util.Objects;

public abstract class Value<T> {
	protected byte flags = 0;
	
	public boolean isOriginalData(){
		return ValueFlag.IS_ORIGINAL.getFlag(this.flags);
	}
	
	public abstract T getValue();
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Value<?> value = (Value<?>) o;
		return flags == value.flags;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(flags);
	}
}
