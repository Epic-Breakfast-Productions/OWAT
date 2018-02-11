package com.ebp.owat.lib.structure.value;

import com.ebp.owat.lib.datastructure.set.LongLinkedList;
import com.ebp.owat.lib.datastructure.value.BitValue;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BitValueTest {
	
	@Test
	public void testFromByte(){
		List<BitValue> result = BitValue.fromByte((byte)0, true);
		assertEquals(
			Arrays.asList(
				new BitValue(false, true),
				new BitValue(false, true),
				new BitValue(false, true),
				new BitValue(false, true),
				new BitValue(false, true),
				new BitValue(false, true),
				new BitValue(false, true),
				new BitValue(false, true)
			),
			result
		);
		
		result = BitValue.fromByte((byte)2, false);
		assertEquals(
			Arrays.asList(
				new BitValue(false, false),
				new BitValue(true, false),
				new BitValue(false, false),
				new BitValue(false, false),
				new BitValue(false, false),
				new BitValue(false, false),
				new BitValue(false, false),
				new BitValue(false, false)
			),
			result
		);
	}
	
	
	@Test
	public void testToByte(){
		try{
			BitValue.toByte(new LongLinkedList<>());
			Assert.fail();
		}catch (IllegalArgumentException e){
			//nothing to do
		}
		
		byte result = BitValue.toByte(Arrays.asList(
			new BitValue(false, false),
			new BitValue(true, false),
			new BitValue(false, false),
			new BitValue(false, false),
			new BitValue(false, false),
			new BitValue(false, false),
			new BitValue(false, false),
			new BitValue(false, false)
		));
		assertEquals((byte)2, result);
	}
}
