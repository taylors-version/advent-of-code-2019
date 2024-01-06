package com.ben.aoc;

import static org.junit.Assert.*;

import org.junit.Test;


public class AppTest 
{    
    @Test
    public void testExample()
    {
    	Day3 aoc = new Day3("givenTest.txt");
    	assertEquals(6, aoc.puzzle1());
    }
    
    @Test
    public void testExample2()
    {
    	Day3 aoc = new Day3("givenTest2.txt");
    	assertEquals(159, aoc.puzzle1());
    }
    
    @Test
    public void testExample3()
    {
    	Day3 aoc = new Day3("givenTest3.txt");
    	assertEquals(135, aoc.puzzle1());
    }
    
    @Test
    public void test2Example()
    {
    	Day3 aoc = new Day3("givenTest.txt");
    	assertEquals(30, aoc.puzzle2());
    }
    
    @Test
    public void test2Example2()
    {
    	Day3 aoc = new Day3("givenTest2.txt");
    	assertEquals(610, aoc.puzzle2());
    }
    
    @Test
    public void test2Example3()
    {
    	Day3 aoc = new Day3("givenTest3.txt");
    	assertEquals(410, aoc.puzzle2());
    }
    
}
