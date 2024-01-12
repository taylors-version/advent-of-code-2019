package com.ben.aoc;

import static org.junit.Assert.*;

import org.junit.Test;


public class AppTest 
{   
	@Test
	public void testTDD() {
		Day14 aoc = new Day14("tdd.txt");
		assertEquals(2, aoc.puzzle1());
	}
	
	@Test
	public void testTDD2() {
		Day14 aoc = new Day14("tdd2.txt");
		assertEquals(5, aoc.puzzle1());
	}
	
	@Test
	public void testExample2() {
		Day14 aoc = new Day14("givenTest2.txt");
		assertEquals(165, aoc.puzzle1());
	}
	
	@Test
	public void testExample3() {
		Day14 aoc = new Day14("givenTest3.txt");
		assertEquals(13312, aoc.puzzle1());
	}
	
	@Test
	public void testExample4() {
		Day14 aoc = new Day14("givenTest4.txt");
		assertEquals(180697, aoc.puzzle1());
	}
	
	@Test
	public void testExample5() {
		Day14 aoc = new Day14("givenTest5.txt");
		assertEquals(2210736, aoc.puzzle1());
	}

	
}
