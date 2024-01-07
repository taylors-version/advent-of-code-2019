package com.ben.aoc;

import java.util.Arrays;
import java.util.List;

public class Day2 {
	
	String[] instructions;
	
	public Day2(String fileName) {
		List<String> lines = Util.readFile(getClass(), fileName);
		instructions = lines.get(0).split(",");
		
	}

	public long puzzle1() {
		String[] input = Arrays.copyOf(instructions, instructions.length);
		input[1] = "12";
		input[2] = "2";
		
		Computer computer = new Computer(input);
		
		return computer.intCode().get(0);
	}
	
	public long puzzle2() {
		
		for(int noun = 0; noun<=99; noun++) {
			for(int verb = 0; verb<=99; verb++) {
				String[] input = Arrays.copyOf(instructions, instructions.length);
				input[1] = Integer.toString(noun);
				input[2] = Integer.toString(verb);
				
				Computer computer = new Computer(input);
				if(computer.intCode().get(0) == 19690720) {
					return 100 * noun + verb;
				}
			}
		}
		
		return 0;
	}
	
}