package com.ben.aoc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class AppTest 
{    
	
	@Test
	public void testExample() {
		Day18 aoc = new Day18("givenTest.txt");
		assertEquals(8, aoc.puzzle1());
	}
	
	@Test
	public void testExample2() {
		Day18 aoc = new Day18("givenTest2.txt");
		assertEquals(86, aoc.puzzle1());
	}
	
	@Test
	public void testExample3() {
		Day18 aoc = new Day18("givenTest3.txt");
		assertEquals(132, aoc.puzzle1());
	}
	
	@Test
	public void testExample4() {
		Day18 aoc = new Day18("givenTest4.txt");
		assertEquals(136, aoc.puzzle1());
	}
	
	@Test
	public void testExample5() {
		Day18 aoc = new Day18("givenTest5.txt");
		assertEquals(81, aoc.puzzle1());
	}
	
	@Test
	public void test2Example1() {
		Day18 aoc = new Day18("givenTest6.txt");
		assertEquals(24, aoc.puzzle2());
	}
	
	@Test
	public void test2Example2() {
		Day18 aoc = new Day18("givenTest7.txt");
		assertEquals(72, aoc.puzzle2());
	}
	
	@Test
	public void test2Example3() {
		Day18 aoc = new Day18("givenTest8.txt");
		assertEquals(32, aoc.puzzle2());
	}

}
