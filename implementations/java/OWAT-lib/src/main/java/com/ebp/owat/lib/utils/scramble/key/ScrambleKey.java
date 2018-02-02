package com.ebp.owat.lib.utils.scramble.key;


import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.set.LongLinkedList;
import com.ebp.owat.lib.datastructure.value.BitValue;
import com.ebp.owat.lib.datastructure.value.Value;
import com.ebp.owat.lib.utils.scramble.ScrambleMove;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

import static com.ebp.owat.lib.utils.scramble.key.SerializationConstants.*;

public class ScrambleKey {
	private static final String BIT_TYPE_STR = "bit";
	private static final String BYTE_TYPE_STR = "byte";
	
	
	@JsonProperty(META)
	public final KeyMetaData meta;
	private LongLinkedList<ScrambleMove> moves;
	
	private static String getTypeStr(Class<? extends Value> type){
		if(type.equals(BitValue.class)){
			return BIT_TYPE_STR;
		}
		return BYTE_TYPE_STR;
	}
	
	public ScrambleKey(long originalHeight, long originalWidth, Class<? extends Value> type) {
		this.meta = new KeyMetaData(originalHeight, originalWidth, getTypeStr(type));
		this.moves = new LongLinkedList<>();
	}
	
	@JsonCreator
	public ScrambleKey(@JsonProperty(META) KeyMetaData meta, @JsonProperty(SCRAMBLE) String movesStr){
		this.meta = meta;
		this.moves = ScrambleMove.parseMulti(movesStr);
	}
	
	public ScrambleKey(Matrix<? extends Value> matrix){
		//TODO
		throw new UnsupportedOperationException("Haven't done this yet.");
		//this.meta = new KeyMetaData(matrix.getHeight(), matrix.getWidth(), getTypeStr((Class<? extends Value>) matrix.getClass().getComponentType()));
		//this.moves = new LongLinkedList<>();
	}
	
	public void addMove(ScrambleMove move){
		this.moves.addFirst(move);
	}
	
	//TODO:: how to go through the moves (when descrambling)? Iterator based? pop off values?
	
	@JsonGetter(SCRAMBLE)
	public String getMoves(){
		StringBuilder sb = new StringBuilder();
		
		for(ScrambleMove curMove : moves){
			curMove.toKeyString(sb);
		}
		
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ScrambleKey that = (ScrambleKey) o;
		return Objects.equals(meta, that.meta) &&
			Objects.equals(moves, that.moves);
	}
	
	@Override
	public int hashCode() {
		
		return Objects.hash(meta, moves);
	}
}
