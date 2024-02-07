package com.ben.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day21 {
	
	
	Computer ascii;
	String[] instructions;
	List<Long> output;
	char[][] grid;
	IntPoint start;
	
	final static String WALK = "WALK" + (char)10;
	final static String RUN = "RUN" + (char)10;
	
	final static String ORTJ = "OR T J" + (char)10;
	final static String NOTAJ = "NOT A J" + (char)10;
	final static String NOTAT = "NOT A T" + (char)10;
	final static String NOTBJ = "NOT B J" + (char)10;
	final static String NOTBT = "NOT B T" + (char)10;
	final static String NOTCT = "NOT C T" + (char)10;
	final static String ANDDJ = "AND D J" + (char)10;
	final static String ANDHJ = "AND H J" + (char)10;
	
	
	public Day21(String fileName) {
		List<String> lines = Util.readFile(getClass(), fileName);
		instructions = lines.get(0).split(",");
	}

	public long puzzle1() {
		String[] ints = Arrays.copyOf(instructions, instructions.length);
		ascii = new Computer(ints);
		
		List<Long> inputs = new ArrayList<Long>();
		String input = NOTAJ + NOTBT + ORTJ + NOTCT + ORTJ + ANDDJ + WALK;
		
		for(char c : input.toCharArray()) {
			int i = c;
			inputs.add((long) i);
		}
		
		ascii.intCode(inputs);
		
		List<Long> output = ascii.getOutputs();
		int outputSize = output.size();
		
		for(int o = 0; o<outputSize-1; o++) {
			long l = output.get(o);
			int i = (int) l;
			System.out.print((char)i);
		}
		return output.get(outputSize-1);
	}
	
	public long puzzle2() {

		String[] ints = Arrays.copyOf(instructions, instructions.length);
		ascii = new Computer(ints);
		
		List<Long> inputs = new ArrayList<Long>();
		String input = NOTBJ + NOTCT + ORTJ + ANDDJ + ANDHJ + NOTAT + ORTJ + RUN;
		
		for(char c : input.toCharArray()) {
			int i = c;
			inputs.add((long) i);
		}
		
		ascii.intCode(inputs);
		
		List<Long> output = ascii.getOutputs();
		int outputSize = output.size();
		
		for(int o = 0; o<outputSize-1; o++) {
			long l = output.get(o);
			int i = (int) l;
			System.out.print((char)i);
		}
		return output.get(outputSize-1);
	}
	
}