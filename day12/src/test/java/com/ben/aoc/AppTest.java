package com.ben.aoc;

import static org.junit.Assert.*;

import org.junit.Test;


public class AppTest 
{    
	
	@Test
	public void testExample() {
		Day12 aoc = new Day12("givenTest.txt");
		assertEquals(2772, aoc.puzzle2());
	}
	
	@Test
	public void testExample2() {
		Day12 aoc = new Day12("givenTest2.txt");
		assertEquals(4686774924L, aoc.puzzle2());
	}

}
