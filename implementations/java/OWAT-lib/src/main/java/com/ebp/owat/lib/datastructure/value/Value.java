package com.ebp.owat.lib.datastructure.value;

import java.util.Objects;

/**
 * The value a matrix holds.
 * @param <T> The type held by this value.
 */
public abstract class Value<T> {
	protected byte flags = 0;

	/**
	 * Determines if this value is part of the original data.
	 * @return If this value is part of the original data.
	 */
	public boolean isOriginalData(){
		return ValueFlag.IS_ORIGINAL.getFlag(this.flags);
	}

	/**
	 * Gets the value held.
	 * @return The value held.
	 */
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
