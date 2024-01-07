package com.ben.aoc;

import java.util.Map;
import static java.util.Map.entry;

import java.util.ArrayList;
import java.util.List;  

public class Computer {
	
	private static final Map<Integer, Integer> instructionParamCount = Map.ofEntries(
			entry(1, 3), //Add
			entry(2, 3), //multiply
			entry(3, 1), //input
			entry(4, 1), //output
			entry(5, 2), //JifTrue
			entry(6, 2), //JifFalse
			entry(7, 3), //LessThan
			entry(8, 3), //Equals
			entry(99, 0) //terminate
			);
	
	private String[] instructions;
	
	public Computer(String[] ints) {
		this.instructions = ints;
	}
	
	public List<Integer> intCode() {
		return intCode(0);
	}
	
	public List<Integer> intCode(int input) {
		int[] inputs = new int[1];
		inputs[0] = input;
		return intCode(inputs);
	}
	
	public List<Integer> intCode(int[] inputs) {
		List<Integer> outputs = new ArrayList<Integer>();
		
		boolean found99 = false;
		int instructionPointer = 0;
		int inputPointer = 0;
		
		while(!found99) {
			String instruction = instructions[instructionPointer];
			
			//Ensure all opcodes are at least 2 digits long to start with
			if(instruction.length() < 2) {
				instruction = padLeftZeros(instruction, 2);
			}
			
			int opCode = Integer.parseInt(instruction.substring(instruction.length() - 2));
			String params  = padLeftZeros(instruction.substring(0, instruction.length() - 2), instructionParamCount.get(opCode));
			switch (opCode) {
			case 1:
				add(instructionPointer, params);
				instructionPointer+= instructionParamCount.get(opCode) + 1;
				break;
			case 2:
				multiply(instructionPointer, params);
				instructionPointer+= instructionParamCount.get(opCode) + 1;
				break;
			case 3:
				store(instructionPointer, inputs[inputPointer]);
				inputPointer++;
				instructionPointer+= instructionParamCount.get(opCode) + 1;
				break;
			case 4:
				outputs.add(output(instructionPointer, params));
				instructionPointer+= instructionParamCount.get(opCode) + 1;
				break;
			case 5:
				instructionPointer = jumpIfTrue(instructionPointer, params);
				break;
			case 6:
				instructionPointer = jumpIfFalse(instructionPointer, params);
				break;
			case 7:
				lessThan(instructionPointer, params);
				instructionPointer+= instructionParamCount.get(opCode) + 1;
				break;
			case 8:
				equalsTo(instructionPointer, params);
				instructionPointer+= instructionParamCount.get(opCode) + 1;
				break;
			case 99:
				found99 = true;
				break;
			}
			
		}
		if(outputs.isEmpty()) {
			outputs.add(Integer.parseInt(instructions[0]));
		}
		return outputs;
	}
	
	private void add(int position, String params) {
		
		int aValue = Integer.parseInt(instructions[position+1]);
		int bValue = Integer.parseInt(instructions[position+2]);
		int storeLocation = Integer.parseInt(instructions[position+3]);
		
		int a = params.charAt(2) == '1' ? aValue : Integer.parseInt(instructions[aValue]);
		int b = params.charAt(1) == '1' ? bValue : Integer.parseInt(instructions[bValue]);
		

		int sum = a + b;
		instructions[storeLocation] = Integer.toString(sum);
	}
	
	private void multiply(int position, String params) {
		int aValue = Integer.parseInt(instructions[position+1]);
		int bValue = Integer.parseInt(instructions[position+2]);
		int storeLocation = Integer.parseInt(instructions[position+3]);
		
		int a = params.charAt(2) == '1' ? aValue : Integer.parseInt(instructions[aValue]);
		int b = params.charAt(1) == '1' ? bValue : Integer.parseInt(instructions[bValue]);
		
		int product = a * b;
		instructions[storeLocation] = Integer.toString(product);
	}
	
	private void store(int position, int input) {
		int storeLocation = Integer.parseInt(instructions[position+1]);
		
		instructions[storeLocation] = Integer.toString(input);
	}
	
	private int output(int position, String params) {
		int aValue = Integer.parseInt(instructions[position+1]);
		
		int out = params.charAt(0) == '1' ? aValue : Integer.parseInt(instructions[aValue]);
		
		return out;
	}
	
	private int jumpIfTrue(int position, String params) {
		int testValue = Integer.parseInt(instructions[position+1]);
		int bValue = Integer.parseInt(instructions[position+2]);
		
		int test = params.charAt(1) == '1' ? testValue : Integer.parseInt(instructions[testValue]);
		int b = params.charAt(0) == '1' ? bValue : Integer.parseInt(instructions[bValue]);
		
		return test != 0 ? b : (position + instructionParamCount.get(5) + 1);
	}
	
	private int jumpIfFalse(int position, String params) {
		int testValue = Integer.parseInt(instructions[position+1]);
		int bValue = Integer.parseInt(instructions[position+2]);
		
		int test = params.charAt(1) == '1' ? testValue : Integer.parseInt(instructions[testValue]);
		int b = params.charAt(0) == '1' ? bValue : Integer.parseInt(instructions[bValue]);
		
		return test == 0 ? b : (position + instructionParamCount.get(6) + 1);
	}
	
	private void lessThan(int position, String params) {
		int aValue = Integer.parseInt(instructions[position+1]);
		int bValue = Integer.parseInt(instructions[position+2]);
		int storeLocation = Integer.parseInt(instructions[position+3]);
		
		int a = params.charAt(2) == '1' ? aValue : Integer.parseInt(instructions[aValue]);
		int b = params.charAt(1) == '1' ? bValue : Integer.parseInt(instructions[bValue]);
		
		int storeValue = a < b ? 1 : 0;
		
		instructions[storeLocation] = Integer.toString(storeValue);
	}
	
	private void equalsTo(int position, String params) {
		int aValue = Integer.parseInt(instructions[position+1]);
		int bValue = Integer.parseInt(instructions[position+2]);
		int storeLocation = Integer.parseInt(instructions[position+3]);
		
		int a = params.charAt(2) == '1' ? aValue : Integer.parseInt(instructions[aValue]);
		int b = params.charAt(1) == '1' ? bValue : Integer.parseInt(instructions[bValue]);
		
		int storeValue = a == b ? 1 : 0;
		
		instructions[storeLocation] = Integer.toString(storeValue);
	}
	
	private String padLeftZeros(String inputString, int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
		    sb.append('0');
		}

		return sb.substring(inputString.length()) + inputString;
	}
	
}
