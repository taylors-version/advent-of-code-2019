package com.ben.aoc;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;


public class AppTest 
{    
	
	@Test
	public void testExample() {
		String testString = "109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99";
		String[] testArray = testString.split(",");
		Computer com = new Computer(testArray);
		com.intCode();
		List<Long> output = com.getOutputs();
		for(int i = 0; i < output.size(); i++) {
			assertEquals((Long)output.get(i), (Long)Long.parseLong(testArray[i]));
		}
	}
	
	@Test
	public void testExample2() {
		String testString = "104,1125899906842624,99";
		String[] testArray = testString.split(",");
		Computer com = new Computer(testArray);
		com.intCode();
		List<Long> output = com.getOutputs();
		assertEquals(1125899906842624L, (long)output.get(output.size()-1));
	}
	
	@Test
	public void testExample3() {
		String testString = "109,-1,4,1,99";
		String[] testArray = testString.split(",");
		Computer com = new Computer(testArray);
		com.intCode(5);
		List<Long> output = com.getOutputs();
		assertEquals(-1L, (long)output.get(output.size()-1));
	}
	
	@Test
	public void testExample4() {
		String testString = "109,-1,104,1,99";
		String[] testArray = testString.split(",");
		Computer com = new Computer(testArray);
		com.intCode(5);
		List<Long> output = com.getOutputs();
		assertEquals(1L, (long)output.get(output.size()-1));
	}
	
	@Test
	public void testExample5() {
		String testString = "109,-1,204,1,99";
		String[] testArray = testString.split(",");
		Computer com = new Computer(testArray);
		com.intCode(5);
		List<Long> output = com.getOutputs();
		assertEquals(109L, (long)output.get(output.size()-1));
	}
	
	@Test
	public void testExample6() {
		String testString = "109,1,9,2,204,-6,99";
		String[] testArray = testString.split(",");
		Computer com = new Computer(testArray);
		com.intCode(5);
		List<Long> output = com.getOutputs();
		assertEquals(204L, (long)output.get(output.size()-1));
	}
	
	@Test
	public void testExample7() {
		String testString = "109,1,109,9,204,-6,99";
		String[] testArray = testString.split(",");
		Computer com = new Computer(testArray);
		com.intCode(5);
		List<Long> output = com.getOutputs();
		assertEquals(204L, (long)output.get(output.size()-1));
	}
	
	@Test
	public void testExample8() {
		String testString = "109,1,209,-1,204,-106,99";
		String[] testArray = testString.split(",");
		Computer com = new Computer(testArray);
		com.intCode(5);
		List<Long> output = com.getOutputs();
		assertEquals(204L, (long)output.get(output.size()-1));
	}
	
	@Test
	public void testExample9() {
		String testString = "109,1,3,3,204,2,99";
		String[] testArray = testString.split(",");
		Computer com = new Computer(testArray);
		com.intCode(5);
		List<Long> output = com.getOutputs();
		assertEquals(5L, (long)output.get(output.size()-1));
	}
	
	@Test
	public void testExample10() {
		String testString = "109,1,203,2,204,2,99";
		String[] testArray = testString.split(",");
		Computer com = new Computer(testArray);
		com.intCode(5);
		List<Long> output = com.getOutputs();
		assertEquals(5L, (long)output.get(output.size()-1));
	}
}
