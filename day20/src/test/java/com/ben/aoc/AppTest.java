package com.ben.aoc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class AppTest 
{    
	
	@Test
	public void testExample() {
		Day20 aoc = new Day20("givenTest.txt");
		assertEquals(23, aoc.puzzle1());
	}
	
	/*
	@Test
	public void testExample2() {
		Day20 aoc = new Day20("givenTest2.txt");
		assertEquals(58, aoc.puzzle1());
	}
	
	@Test
	public void test2Example() {
		Day20 aoc = new Day20("givenTest.txt");
		assertEquals(26, aoc.puzzle2());
	}
	
	@Test
	public void test2Example3() {
		Day20 aoc = new Day20("givenTest3.txt");
		assertEquals(396, aoc.puzzle2());
	}
*/
}
