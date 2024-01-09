package com.ben.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import com.ben.aoc.maths.Maths;

public class Day7 {
	
	String[] instructions;
	
	
	public Day7(String fileName) {
		List<String> lines = Util.readFile(getClass(), fileName);
		instructions = lines.get(0).split(",");
		
	}

	public long puzzle1() {
		long result = 0;
		
		int[] entries = {0, 1, 2, 3, 4};
		Integer[] combos = IntStream.of(entries).boxed().toArray(Integer[]::new);
		
		List<Integer[]> configurations = new ArrayList<Integer[]>();
		configurations = Maths.getAllPermutations(combos.length, combos, configurations);
		
		for(Integer[] configuration : configurations) {
			String[] inputA = Arrays.copyOf(instructions, instructions.length);
			String[] inputB = Arrays.copyOf(instructions, instructions.length);
			String[] inputC = Arrays.copyOf(instructions, instructions.length);
			String[] inputD = Arrays.copyOf(instructions, instructions.length);
			String[] inputE = Arrays.copyOf(instructions, instructions.length);
			
			Computer ampA = new Computer(inputA);
			Computer ampB = new Computer(inputB);
			Computer ampC = new Computer(inputC);
			Computer ampD = new Computer(inputD);
			Computer ampE = new Computer(inputE);
			
			Long[] paramsA = {(long)configuration[0], 0L};
			ampA.intCode(Arrays.asList(paramsA));
			List<Long> resultA = ampA.getOutputs();
			
			Long[] paramsB = {(long)configuration[1], resultA.get(resultA.size()-1)};
			ampB.intCode(Arrays.asList(paramsB));
			List<Long> resultB = ampB.getOutputs();
			
			Long[] paramsC = {(long)configuration[2], resultB.get(resultB.size()-1)};
			ampC.intCode(Arrays.asList(paramsC));
			List<Long> resultC = ampC.getOutputs();
			
			Long[] paramsD = {(long)configuration[3], resultC.get(resultC.size()-1)};
			ampD.intCode(Arrays.asList(paramsD));
			List<Long> resultD = ampD.getOutputs();
			
			Long[] paramsE = {(long)configuration[4], resultD.get(resultD.size()-1)};
			ampE.intCode(Arrays.asList(paramsE));
			List<Long> resultE = ampE.getOutputs();
			long e = resultE.get(resultE.size()-1);
			
			result = Math.max(result, (int)e);
			
		}	
		
		return result;
	}
	
	public long puzzle2() {
		long result = 0;
				
		int[] entries = {5, 6, 7, 8, 9};
		Integer[] combos = IntStream.of(entries).boxed().toArray(Integer[]::new);
		
		List<Integer[]> configurations = new ArrayList<Integer[]>();
		configurations = Maths.getAllPermutations(combos.length, combos, configurations);
		
		for(Integer[] configuration : configurations) {
			String[] inputA = Arrays.copyOf(instructions, instructions.length);
			String[] inputB = Arrays.copyOf(instructions, instructions.length);
			String[] inputC = Arrays.copyOf(instructions, instructions.length);
			String[] inputD = Arrays.copyOf(instructions, instructions.length);
			String[] inputE = Arrays.copyOf(instructions, instructions.length);
			
			List<Long> paramsA = new ArrayList<Long>();
			List<Long> paramsB;
			List<Long> paramsC;
			List<Long> paramsD;
			List<Long> paramsE;
			
			Computer ampA = new Computer(inputA);
			Computer ampB = new Computer(inputB);
			Computer ampC = new Computer(inputC);
			Computer ampD = new Computer(inputD);
			Computer ampE = new Computer(inputE);
			
			paramsA.add((long)configuration[0]);
			paramsA.add(0L);
			
			boolean ampEFinished = false;
			
			while(ampEFinished == false) {
				ampA.intCode(paramsA);
				
				paramsB = new ArrayList<Long>();
				paramsB.add((long)configuration[1]);
				paramsB.addAll(ampA.getOutputs());
				ampB.intCode(paramsB);
				
				paramsC = new ArrayList<Long>();
				paramsC.add((long)configuration[2]);
				paramsC.addAll(ampB.getOutputs());
				ampC.intCode(paramsC);
				
				paramsD = new ArrayList<Long>();
				paramsD.add((long)configuration[3]);
				paramsD.addAll(ampC.getOutputs());
				ampD.intCode(paramsD);
				
				paramsE = new ArrayList<Long>();
				paramsE.add((long)configuration[4]);
				paramsE.addAll(ampD.getOutputs());
				ampEFinished = ampE.intCode(paramsE);
				
				paramsA = new ArrayList<Long>();
				paramsA.add((long)configuration[0]);
				paramsA.add(0L);
				paramsA.addAll(ampE.getOutputs());
			}
			
			List<Long> resultE = ampE.getOutputs();
			result = Math.max(result, resultE.get(resultE.size()-1));
			
		}	
		
		return result;
	}
}
	
