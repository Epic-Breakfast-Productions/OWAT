package com.ebp.owat.lib.structure.value;

import com.ebp.owat.lib.datastructure.value.BitValue;
import com.ebp.owat.lib.datastructure.value.ByteValue;
import com.ebp.owat.lib.datastructure.value.Value;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ValueTest {
	
	private final Value value;
	
	private final boolean expectOriginal;
	
	private final Object expectedValue;
	
	@Parameterized.Parameters
	public static Collection getMatrixClassesToTest(){
		return Arrays.asList(new Object[][] {
			{new BitValue(true, true), true, true},
			{new BitValue(false, true), false, true},
			{new BitValue(true, false), true, false},
			{new BitValue(false, false), false, false},
			
			{new ByteValue((byte)0, true), (byte)0, true},
			{new ByteValue((byte)5, false), (byte)5, false}
		});
	}
	
	public ValueTest(Value flag, Object value, boolean isOriginal){
		this.value = flag;
		this.expectOriginal = isOriginal;
		this.expectedValue = value;
	}
	
	@Test
	public void testIsOriginal(){
		assertEquals(this.expectOriginal, value.isOriginalData());
	}
	
	@Test
	public void testValue(){
		assertEquals(this.expectedValue, value.getValue());
	}
}
