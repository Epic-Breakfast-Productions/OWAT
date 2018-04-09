package com.ebp.owat.lib.utils.key;

import com.ebp.owat.lib.datastructure.value.BitValue;
import com.ebp.owat.lib.datastructure.value.NodeMode;
import com.ebp.owat.lib.datastructure.value.Value;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

import static com.ebp.owat.lib.utils.key.SerializationConstants.*;

/**
 * Class to organize the data in the key's metadata section.
 */
public class KeyMetaData {
	/** The height of the original data. */
	@JsonProperty(ORIGINAL_HEIGHT)
	public final long originalHeight;
	/** The width of the original data. */
	@JsonProperty(ORIGINAL_WIDTH)
	public final long originalWidth;
	/** The height of the data */
	@JsonProperty(DATA_HEIGHT)
	public final long dataHeight;
	/** The width of the data */
	@JsonProperty(DATA_WIDTH)
	public final long dataWidth;
	/** The type of node used. */
	@JsonProperty(NODE_TYPE)
	public final String nodeType;
	/** The end of the original data in the last row of the original dataset. */
	@JsonProperty(LAST_COL_INDEX)
	public final long lastColIndex;

	/**
	 * Constructor to set all the data.
	 * @param originalHeight The height of the original data.
	 * @param originalWidth The width of the original data.
	 * @param dataHeight The height of the overall data.
	 * @param dataWidth The width of the overall data.
	 * @param nodeType The type of node used.
	 * @param lastColIndex The  end of the original data in the last row of the original dataset.
	 */
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

	/**
	 * Gets the type of node used.
	 * @return The type of node used.
	 */
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
