package com.ebp.owat.lib.utils.scramble;

import java.util.regex.Pattern;

public class ScrambleConstants {
	static final int PATTERN_FLAG = Pattern.CASE_INSENSITIVE;
	
	static final char MOVE_END = ';';
	static final char ARG_SEP = ',';
	static final char COORD_SEP = 'x';
	static final char COORD_SEP_CAP = 'P';
	static final char OP_SEP = ':';
	
	static final String MOVE_SEP_REGEX = "(?<=" + ScrambleConstants.MOVE_END + ")";
	
	public static class Swap {
		public static final String OP = "sw";
		public static final int X1 = 0;
		public static final int Y1 = 1;
		public static final int X2 = 2;
		public static final int Y2 = 3;
	}
	
	private static abstract class SwapRowCol {
		public static final int ROWCOL1 = 0;
		public static final int ROWCOL2 = 1;
	}
	
	public static class SwapRow extends SwapRowCol {
		public static final String OP = "swr";
	}
	
	public static class SwapCol extends SwapRowCol {
		public static final String OP = "swc";
	}
	
	private static abstract class SlideRowCol {
		public static final int ROWCOL = 0;
		public static final int NUMTOSLIDE = 1;
	}
	
	public static class SlideRow extends SlideRowCol {
		public static final String OP = "slr";
	}
	
	public static class SlideCol extends SlideRowCol {
		public static final String OP = "slc";
	}
	
	private static abstract class Rotate {
		public static final int ROTNUM = 0;
		public static final int X1 = 1;
		public static final int Y1 = 2;
		public static final int X2 = 3;
		public static final int Y2 = 4;
	}
	
	public static class RotateClock extends Rotate {
		public static final String OP = "rcl";
	}
	
	public static class RotateCounterClock extends Rotate {
		public static final String OP = "rcc";
	}
}
