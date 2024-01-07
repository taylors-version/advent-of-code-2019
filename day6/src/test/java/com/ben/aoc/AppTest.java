package com.ben.aoc;

import static org.junit.Assert.*;

import org.junit.Test;


public class AppTest 
{    
	
	@Test
	public void testExample() {
		Day6 aoc = new Day6("givenTest.txt");
		assertEquals(42, aoc.puzzle1());
	}
	
	@Test
	public void testExample2() {
		Day6 aoc = new Day6("givenTest2.txt");
		assertEquals(4, aoc.puzzle2());
	}
    
}
