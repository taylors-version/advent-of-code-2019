package com.ben.aoc;

import java.util.List;

public class Day9 {
	
	String[] instructions;
	
	
	public Day9(String fileName) {
		List<String> lines = Util.readFile(getClass(), fileName);
		instructions = lines.get(0).split(",");
		
	}

	public long puzzle1() {
		Computer boost = new Computer(instructions);
		boolean success = boost.intCode(1);
		List<Long> output = boost.getOutputs();
		System.out.println(success);
		return output.get(output.size()-1);
	}
	
	public long puzzle2() {
		Computer boost = new Computer(instructions);
		boolean success = boost.intCode(2);
		List<Long> output = boost.getOutputs();
		System.out.println(success);
		return output.get(output.size()-1);
	}
}
	
