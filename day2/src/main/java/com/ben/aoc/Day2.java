package com.ben.aoc;

import java.util.Arrays;
import java.util.List;

public class Day2 {
	
	int[] ints;
	
	public Day2(String fileName) {
		List<String> lines = Util.readFile(getClass(), fileName);
		ints = Arrays.stream(lines.get(0).split(",")).mapToInt(Integer::parseInt).toArray();
		
	}

	public long puzzle1() {
		int[] input = Arrays.copyOf(ints, ints.length);
		input[1] = 12;
		input[2] = 2;
		
		Computer computer = new Computer(input);
		
		return computer.intCode()[0];
	}
	
	public long puzzle2() {
		
		for(int noun = 0; noun<=99; noun++) {
			for(int verb = 0; verb<=99; verb++) {
				int[] input = Arrays.copyOf(ints, ints.length);
				input[1] = noun;
				input[2] = verb;
				
				Computer computer = new Computer(input);
				if(computer.intCode()[0] == 19690720) {
					return 100 * noun + verb;
				}
			}
		}
		
		return 0;
	}
	
}