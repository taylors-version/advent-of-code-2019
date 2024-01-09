package com.ben.aoc;

import java.util.Map;

import static java.util.Map.entry;

import java.util.ArrayList;
import java.util.Arrays;
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
			entry(9, 1), //adjust relative pointer
			entry(99, 0) //terminate
			);
	
	private String[] instructions;
	private int instructionPointer = 0;
	private int inputPointer = 0;
	private int relativePointer = 0;
	private List<Long> outputs;
	
	public Computer(String[] ints) {
		this.instructions = ints;
		outputs = new ArrayList<Long>();
	}
	
	public List<Long> getOutputs(){
		return outputs;
	}
	
	public boolean intCode() {
		return intCode(0);
	}
	
	public boolean intCode(int input) {
		List<Long> inputs = new ArrayList<Long>();
		inputs.add((long)input);
		return intCode(inputs);
	}
	
	public boolean intCode(List<Long> inputs) {
		
		boolean found99 = false;
		
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
				while(inputPointer >= inputs.size()) {
					return false;
				}
				store(instructionPointer,inputs.get(inputPointer), params);
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
			case 9:
				adjustRelativePointer(instructionPointer, params);
				instructionPointer+= instructionParamCount.get(opCode) + 1;
				break;
			case 99:
				found99 = true;
				break;
			}
			
		}
		return true;
	}
	
	private void add(int position, String params) {
		long a = getValue(position+1, params.charAt(2), false);
		long b = getValue(position+2, params.charAt(1), false);
		int storeLocation = (int)getValue(position+3, params.charAt(0), true);
		
		long sum = a + b;
		store(storeLocation, Long.toString(sum));
	}
	
	private void multiply(int position, String params) {
		long a = getValue(position+1, params.charAt(2), false);
		long b = getValue(position+2, params.charAt(1), false);
		int storeLocation = (int)getValue(position+3, params.charAt(0), true);
		
		long product = a * b;
		store(storeLocation, Long.toString(product));
	}
	
	private void store(int position, long input, String params) {
		int storeLocation = (int)getValue(position + 1, params.charAt(0), true);
		
		store(storeLocation, Long.toString(input));
	}
	
	private long output(int position, String params) {
		return getValue(position+1, params.charAt(0), false);
	}
	
	private int jumpIfTrue(int position, String params) {
		long test = getValue(position+1, params.charAt(1), false);
		int b = (int)getValue(position+2, params.charAt(0), false);
		
		return test != 0 ? b : (position + instructionParamCount.get(5) + 1);
	}
	
	private int jumpIfFalse(int position, String params) {
		long test = getValue(position+1, params.charAt(1), false);
		int b = (int)getValue(position+2, params.charAt(0), false);
		
		return test == 0 ? b : (position + instructionParamCount.get(6) + 1);
	}
	
	private void lessThan(int position, String params) {
		long a = getValue(position+1, params.charAt(2), false);
		long b = getValue(position+2, params.charAt(1), false);
		int storeLocation = (int)getValue(position+3, params.charAt(0), true);
		
		int storeValue = a < b ? 1 : 0;
		
		store(storeLocation, Integer.toString(storeValue));
	}
	
	private void equalsTo(int position, String params) {
		long a = getValue(position+1, params.charAt(2), false);
		long b = getValue(position+2, params.charAt(1), false);
		int storeLocation = (int)getValue(position+3, params.charAt(0), true);
		
		int storeValue = a == b ? 1 : 0;
		
		store(storeLocation, Long.toString(storeValue));
	}
	
	private void adjustRelativePointer(int position, String params) {
		relativePointer+=getValue(instructionPointer+1, params.charAt(0), false);
	}
	
	private String padLeftZeros(String inputString, int length) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
		    sb.append('0');
		}

		return sb.substring(inputString.length()) + inputString;
	}
	
	private long getValue(int position, char param, boolean store) {
		long posValue = Long.parseLong(instructions[position]);
		if(!store) {
			switch (param) {
			case '0':
				return posValue < instructions.length ? Long.parseLong(instructions[(int)posValue]) : 0;
			case '1':
				return posValue;
			case '2':
				posValue += relativePointer;
				return posValue < instructions.length ? Long.parseLong(instructions[(int)posValue]) : 0;
			}
		}else {
			switch (param) {
			case '2':
				return posValue + relativePointer;
			default:
					return posValue;
			}
		}
		return 0;
	}
	
	private void store(int position, String value) {
		if(position >= instructions.length) {
			String[] temp = Arrays.copyOf(instructions, position+1);
			Arrays.fill(temp, instructions.length, temp.length, "");
			instructions = temp;
		}
		instructions[position] = value;
		
	}
	
}
