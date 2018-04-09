package com.ebp.owat.lib.datastructure.value;

import com.ebp.owat.lib.utils.key.SerializationConstants;

/**
 * Enum representations of the value types.
 */
public enum NodeMode {
	BIT(SerializationConstants.BIT_TYPE_STR, BitValue.class),BYTE(SerializationConstants.BYTE_TYPE_STR, ByteValue.class);
	
	public final String typeStr;
	public final Class<? extends Value> typeClass;
	
	NodeMode(String typeStr, Class<? extends Value> typeClass){
		this.typeStr = typeStr;
		this.typeClass = typeClass;
	}
}
