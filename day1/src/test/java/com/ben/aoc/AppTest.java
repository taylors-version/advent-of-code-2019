package com.ben.aoc;

import static org.junit.Assert.*;

import org.junit.Test;


public class AppTest 
{    
    @Test
    public void testExample()
    {
    	Day1 day1 = new Day1("givenTest.txt");
    	assertEquals(34241, day1.puzzle1());
    }
    
    @Test
    public void test2Example()
    {
    	Day1 day1 = new Day1("givenTest.txt");
    	assertEquals(2+2+966+50346, day1.puzzle2());
    }
    
}
