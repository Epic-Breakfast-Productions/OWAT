package com.ebp.owat.lib.structure.value;

import com.ebp.owat.lib.datastructure.value.ValueFlag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class ValueFlagTest {
	
	private final ValueFlag flag;
	
	@Parameterized.Parameters
	public static ValueFlag[] getClassesToTest() {
		return ValueFlag.values();
	}
	
	public ValueFlagTest(ValueFlag flag){
		this.flag = flag;
	}
	
	@Test
	public void testValidOffset(){
		byte test = 1;
		boolean found = false;
		for(int i = 1; i <= 8; i++){
			if(test == this.flag.offset){
				found = true;
				break;
			}
			test = (byte)(test<<1);
		}
		assertTrue("Offset of flag was invalid (not a valid flag for bitwise operations).",found);
	}
	
	@Test
	public void testSetOffset(){
		byte test = 0;
		
		assertEquals(this.flag.offset, this.flag.setFlag(test));
	}
	
	@Test
	public void testGetOffset(){
		byte test = (byte)255;
		
		assertTrue(this.flag.getFlag(test));
		test = 0;
		assertFalse(this.flag.getFlag(test));
	}
}
