package com.ben.aoc;

import static org.junit.Assert.*;

import org.junit.Test;


public class AppTest 
{    
	
	@Test
	public void testExample() {
		Day7 aoc = new Day7("givenTest.txt");
		assertEquals(43210, aoc.puzzle1());
	}

	@Test
	public void test2Example() {
		Day7 aoc = new Day7("givenTest2.txt");
		assertEquals(139629729, aoc.puzzle2());
	}

}
