package com.ebp.owat.lib.utils.scramble;

import com.ebp.owat.lib.datastructure.set.LongLinkedList;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.ebp.owat.lib.utils.scramble.ScrambleConstants.*;

public class ScrambleMove {
	private final ScrambleMoves move;
	private final long[] args;
	
	public ScrambleMove(ScrambleMoves move, long... args) {
		this.move = move;
		if (args.length != this.move.numArgs) {
			throw new IllegalArgumentException("Invalid number of arguments passed for the move given.");
		}
		this.args = args;
	}
	
	public String toKeyString() {
		StringBuilder sb = new StringBuilder(this.move.opStr);
		sb.append(OP_SEP);
		
		for (int i = 0, left = this.args.length; i < this.args.length; i++, left--) {
			sb.append(this.args[i]);
			boolean evenNumLeft = left % 2 != 0;
			if (evenNumLeft) {
				sb.append(COORD_SEP);
			} else {
				sb.append(ARG_SEP);
			}
		}
		
		sb.append(MOVE_END);
		return sb.toString();
	}
	
	public static ScrambleMove parse(String move) {
		ScrambleMoves sm = ScrambleMoves.determineMove(move);
		String sanitizedMove = move.replaceAll("\\s", "");
		sanitizedMove = sanitizedMove.substring(sanitizedMove.lastIndexOf(":") + 1, sanitizedMove.length() - 1);
		String[] moveParts = sanitizedMove.split("(" + ARG_SEP + "|" + COORD_SEP + "|" + COORD_SEP_CAP + ")");
		long[] args = new long[moveParts.length];
		for (int i = 0; i < moveParts.length; i++) {
			args[i] = Long.parseLong(moveParts[i]);
		}
		return new ScrambleMove(sm, args);
	}
	
	public static List<ScrambleMove> parseMulti(String moves) {
		LongLinkedList<ScrambleMove> output = new LongLinkedList<>();
		
		String[] moveStrings = moves.split("(?<=" + ScrambleConstants.OP_SEP + ")");
		
		for (String curMove : moveStrings) {
			output.addLast(parse(curMove));
		}
		return output;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ScrambleMove that = (ScrambleMove) o;
		return move == that.move &&
			Arrays.equals(args, that.args);
	}
	
	@Override
	public int hashCode() {
		int result = Objects.hash(move);
		result = 31 * result + Arrays.hashCode(args);
		return result;
	}
}