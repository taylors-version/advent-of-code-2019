package com.ben.aoc;

import java.util.Arrays;
import java.util.List;

public class Day5 {
	
	String[] instructions;
	
	public Day5(String fileName) {
		List<String> lines = Util.readFile(getClass(), fileName);
		instructions = lines.get(0).split(",");
		
	}

	public long puzzle1() {
		String[] input = Arrays.copyOf(instructions, instructions.length);
		
		Computer computer = new Computer(input);
		
		computer.intCode(1);
		
		return 0;
	}
	
	public long puzzle2() {
		String[] input = Arrays.copyOf(instructions, instructions.length);
		
		Computer computer = new Computer(input);
		
		computer.intCode(5);
		
		return 0;
	}
	
}