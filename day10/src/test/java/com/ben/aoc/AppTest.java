package com.ben.aoc;

import static org.junit.Assert.*;

import org.junit.Test;


public class AppTest 
{    
	
	@Test
	public void testExample() {
		Day10 aoc = new Day10("givenTest.txt");
		assertEquals(8, aoc.puzzle1());
	}
	
	@Test
	public void testExample2() {
		Day10 aoc = new Day10("givenTest2.txt");
		assertEquals(33, aoc.puzzle1());
	}
	
	@Test
	public void testExample3() {
		Day10 aoc = new Day10("givenTest3.txt");
		assertEquals(35, aoc.puzzle1());
	}
	
	@Test
	public void testExample4() {
		Day10 aoc = new Day10("givenTest4.txt");
		assertEquals(41, aoc.puzzle1());
	}
	
	@Test
	public void testExample5() {
		Day10 aoc = new Day10("givenTest5.txt");
		assertEquals(210, aoc.puzzle1());
		assertEquals(802, aoc.puzzle2());
	}

}
