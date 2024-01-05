package com.ben.aoc;

public class App 
{
    public static void main( String[] args )
    {
        Day1 day1 = new Day1("input.txt");
        System.out.println("puzzle 1: " + day1.puzzle1());
        System.out.println("puzzle 2: " + day1.puzzle2());
    }
}
