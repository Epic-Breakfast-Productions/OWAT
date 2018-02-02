package com.ebp.owat.lib.utils.scramble.key;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class KeyMetaData {
	@JsonProperty("origHeight")
	public final long originalHeight;
	@JsonProperty("origWidth")
	public final long originalWidth;
	@JsonProperty("nodeType")
	public final String nodeType;
	
	@JsonCreator
	public KeyMetaData(@JsonProperty("origHeight") long originalHeight, @JsonProperty("origWidth") long originalWidth, @JsonProperty("nodeType") String nodeType) {
		this.originalHeight = originalHeight;
		this.originalWidth = originalWidth;
		this.nodeType = nodeType;
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
