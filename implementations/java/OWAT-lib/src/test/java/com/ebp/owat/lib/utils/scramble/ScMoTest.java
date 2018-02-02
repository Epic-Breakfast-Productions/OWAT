package com.ebp.owat.lib.utils.scramble;

import java.util.Arrays;
import java.util.Collection;

public abstract class ScMoTest {
	protected static final Collection<String> SWAP = Arrays.asList("sw:10x1,100x399;", " sw : 10 x 1 , 100 x 399 ; ","SW:10x1,100X399;");
	protected static final Collection<String> SWAP_ROW = Arrays.asList("swr:10,100;", " swr : 10 , 100 ; ");
	protected static final Collection<String> SWAP_COL = Arrays.asList("swc:100,399;", " swc : 100 , 399 ; ");
	protected static final Collection<String> SLIDE_ROW = Arrays.asList("slr:100,399;"," slr : 100 , 399 ; ","slr:100,-399;");
	protected static final Collection<String> SLIDE_COL = Arrays.asList("slc:100,399;"," slc : 100 , 399 ; ","slc:100,-399;");
	protected static final Collection<String> ROT_BOX = Arrays.asList("rot:1,1x2,3x4;"," rot : 1 , 1 x 2 , 3 x 4 ; ","rot:-1,1x2,3x4;");
	protected static final Collection<String> INVALID = Arrays.asList("", "sw:10x1,100x399");
}
