package com.ebp.owat.lib.utils.scramble;

import com.ebp.owat.lib.datastructure.set.LongLinkedList;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.ebp.owat.lib.utils.scramble.ScrambleConstants.*;

public class ScrambleMove {
	private final ScrambleMoves move;
	private final long[] args;
	
	public ScrambleMove(ScrambleMoves move, long ... args) {
		this.move = move;
		if (args.length != this.move.numArgs) {
			throw new IllegalArgumentException("Invalid number of arguments passed for the move given.");
		}
		this.args = args;
	}
	
	public String toKeyString() {
		StringBuilder sb = new StringBuilder(this.move.opStr);
		sb.append(OP_SEP);
		
		switch (this.move) {
			case SWAP:
				sb.append(this.args[Swap.X1])
					.append(COORD_SEP)
					.append(this.args[Swap.Y1])
					.append(ARG_SEP)
					.append(this.args[Swap.X2])
					.append(COORD_SEP)
					.append(this.args[Swap.Y2]);
				break;
			case SWAP_ROW:
			case SWAP_COL:
				sb.append(this.args[SwapRow.ROWCOL1])
					.append(ARG_SEP)
					.append(this.args[SwapRow.ROWCOL2]);
				break;
			case SLIDE_ROW:
			case SLIDE_COL:
				sb.append(this.args[SlideCol.ROWCOL])
					.append(ARG_SEP)
					.append(this.args[SlideCol.NUMTOSLIDE]);
				break;
			case ROT_CLOCK:
			case ROT_CCLOCK:
				sb.append(this.args[RotateClock.ROTNUM])
					.append(ARG_SEP)
					.append(this.args[RotateClock.X1])
					.append(COORD_SEP)
					.append(this.args[RotateClock.Y1])
					.append(ARG_SEP)
					.append(this.args[RotateClock.X2])
					.append(COORD_SEP)
					.append(this.args[RotateClock.Y2]);
				break;
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
		
		String[] moveStrings = moves.split("(?<=" + ScrambleConstants.MOVE_END + ")");
		
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