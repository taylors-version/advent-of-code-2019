package com.ben.aoc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class AppTest 
{    
	
	
	@Test
	public void testExample() {
		Day24 aoc = new Day24("givenTest.txt");
		assertEquals(2129920, aoc.puzzle1());
	}
	
}
