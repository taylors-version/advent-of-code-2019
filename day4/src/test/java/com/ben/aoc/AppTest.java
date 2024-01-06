package com.ben.aoc;

import static org.junit.Assert.*;

import org.junit.Test;


public class AppTest 
{    
	
	@Test
	public void testrules() {
		Day4 aoc = new Day4(1,1);
		assertTrue(aoc.notDecreasing(111111));
		assertTrue(aoc.notDecreasing(123456));
		assertFalse(aoc.notDecreasing(223450));
		assertTrue(aoc.repeatingDigits(111111));
		assertTrue(aoc.repeatingDigits(223450));
		assertFalse(aoc.repeatingDigits(123456));
		assertTrue(aoc.doubleDigits(112233));
		assertFalse(aoc.doubleDigits(123444));
		assertFalse(aoc.doubleDigits(444321));
		assertTrue(aoc.doubleDigits(111122));
		assertTrue(aoc.doubleDigits(221111));
	}
    
}
