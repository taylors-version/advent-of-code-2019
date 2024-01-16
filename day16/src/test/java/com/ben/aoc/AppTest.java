package com.ben.aoc;

import static org.junit.Assert.*;

import org.junit.Test;


public class AppTest 
{   

	@Test
	public void testExample() {
		Day16 aoc = new Day16("givenTest.txt");
		assertEquals("24176176", aoc.puzzle1());
	}
	
	@Test
	public void testExample2() {
		Day16 aoc = new Day16("givenTest2.txt");
		assertEquals("73745418", aoc.puzzle1());
	}
	
	@Test
	public void testExample3() {
		Day16 aoc = new Day16("givenTest3.txt");
		assertEquals("52432133", aoc.puzzle1());
	}
	
}
