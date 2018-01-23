package com.ebp.owat.lib.utils;

public class ScrambleMove {
	private final ScrambleMoves move;
	private final long[] args;
	
	public ScrambleMove(ScrambleMoves move, long ... args){
		this.move = move;
		if(args.length != this.move.numArgs){
			throw new IllegalArgumentException("Invalid number of arguments passed for the move given.");
		}
		this.args = args;
	}
	
	public String toKeyString(){
		return null;//TODO
	}
	
	public static ScrambleMove parse(String move){
		//TODO
		return null;
	}
}
