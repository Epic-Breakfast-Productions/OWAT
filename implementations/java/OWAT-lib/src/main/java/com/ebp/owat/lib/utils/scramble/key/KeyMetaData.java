package com.ebp.owat.lib.utils.scramble.key;

import com.ebp.owat.lib.datastructure.value.BitValue;
import com.ebp.owat.lib.datastructure.value.NodeMode;
import com.ebp.owat.lib.datastructure.value.Value;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

import static com.ebp.owat.lib.utils.scramble.key.SerializationConstants.*;

public class KeyMetaData {
	@JsonProperty(ORIGINAL_HEIGHT)
	public final long originalHeight;
	@JsonProperty(ORIGINAL_WIDTH)
	public final long originalWidth;
	@JsonProperty(DATA_HEIGHT)
	public final long dataHeight;
	@JsonProperty(DATA_WIDTH)
	public final long dataWidth;
	@JsonProperty(NODE_TYPE)
	public final String nodeType;
	@JsonProperty(LAST_COL_INDEX)
	public final long lastColIndex;
	
	@JsonCreator
	public KeyMetaData(
		@JsonProperty(ORIGINAL_HEIGHT) long originalHeight,
		@JsonProperty(ORIGINAL_WIDTH) long originalWidth,
		@JsonProperty(DATA_HEIGHT) long dataHeight,
		@JsonProperty(DATA_WIDTH) long dataWidth,
		@JsonProperty(NODE_TYPE) String nodeType,
		@JsonProperty(value = LAST_COL_INDEX, defaultValue = "-1") long lastColIndex
	) {
		this.originalHeight = originalHeight;
		this.originalWidth = originalWidth;
		this.dataHeight = dataHeight;
		this.dataWidth = dataWidth;
		this.nodeType = nodeType;
		this.lastColIndex = lastColIndex;
	}
	
	/**
	 * Gets the string used to define what kind of node the matrix holds.
	 * @param type The type of Value.
	 * @return The string representation for
	 */
	public static String getTypeStr(Class<? extends Value> type){
		if(type.equals(BitValue.class)){
			return BIT_TYPE_STR;
		}
		return BYTE_TYPE_STR;
	}
	
	@JsonIgnore
	public NodeMode getNodeMode(){
		switch (this.nodeType){
			case SerializationConstants.BIT_TYPE_STR:
				return NodeMode.BIT;
			case SerializationConstants.BYTE_TYPE_STR:
				return NodeMode.BYTE;
			default:
				throw new IllegalStateException();
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		KeyMetaData that = (KeyMetaData) o;
		return originalHeight == that.originalHeight &&
			originalWidth == that.originalWidth &&
			Objects.equals(nodeType, that.nodeType);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(originalHeight, originalWidth, nodeType);
	}
}
