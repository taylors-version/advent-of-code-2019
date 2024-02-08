package com.ben.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day23 {
	
	private static final int COMPUTER_COUNT = 50;
	
	String[] program;
	
	public Day23(String fileName) {
		List<String> lines = Util.readFile(getClass(), fileName);
		program = lines.get(0).split(",");
	}

	public long puzzle1() {
		Computer[] nics = new Computer[COMPUTER_COUNT];
		List<List<Long>> inputs = new ArrayList<List<Long>>(COMPUTER_COUNT);
		List<List<Long>> outputs = new ArrayList<List<Long>>(COMPUTER_COUNT);
		int[] inputPosition = new int[COMPUTER_COUNT];
		int[] outputPosition = new int[COMPUTER_COUNT];
		
		
		for(int i = 0; i<nics.length; i++) {
			nics[i] = new Computer(Arrays.copyOf(program, program.length));
			
			List<Long> input = new ArrayList<Long>();
			input.add((long)i);
			inputs.add(i, input);
			nics[i].intCode(inputs.get(i));
			outputs.add(i, nics[i].getOutputs());
			
			inputPosition[i] = 0;
			outputPosition[i] = 0;
		}
		
		while(true) {
			for(int i = 0; i<COMPUTER_COUNT; i++) {
				List<Long> output = outputs.get(i);
				int outputSize = output.size();
				if(outputSize > outputPosition[i]) {
					for(int pos = outputPosition[i]; pos < outputSize; pos+=3) {
						long address = output.get(pos);
						long x = output.get(pos+1);
						long y = output.get(pos+2);
						if(address == 255) {
							return y;
						}
						List<Long> addressIn = inputs.get((int)address);
						addressIn.add(x);
						addressIn.add(y);
					}
				}
				outputPosition[i] = outputSize;
			}
			for(int i = 0; i<COMPUTER_COUNT; i++) {
				
				List<Long>input = inputs.get(i);
				if(inputPosition[i] == input.size()-1) {
					input.add(-1l);
					inputPosition[i]+=1;
				}else {
					inputPosition[i]+=2;
				}
				nics[i].intCode(input);
				List<Long> output = nics[i].getOutputs();
				outputs.set(i, output);
			}
		}

	}
	
	public long puzzle2() {		
		Computer[] nics = new Computer[COMPUTER_COUNT];
		List<List<Long>> inputs = new ArrayList<List<Long>>(COMPUTER_COUNT);
		List<List<Long>> outputs = new ArrayList<List<Long>>(COMPUTER_COUNT);
		int[] inputPosition = new int[COMPUTER_COUNT];
		int[] outputPosition = new int[COMPUTER_COUNT];
		
		long natX = 0;
		long natY = 0;
		long oldNatY = 0;
		
		
		for(int i = 0; i<nics.length; i++) {
			nics[i] = new Computer(Arrays.copyOf(program, program.length));
			
			List<Long> input = new ArrayList<Long>();
			input.add((long)i);
			inputs.add(i, input);
			nics[i].intCode(inputs.get(i));
			outputs.add(i, nics[i].getOutputs());
			
			inputPosition[i] = 0;
			outputPosition[i] = 0;
		}
		int counter = 0;
		while(true) {
			boolean emptyQueues = true;
			for(int i = 0; i<COMPUTER_COUNT; i++) {
				List<Long> output = outputs.get(i);
				int outputSize = output.size();
				if(outputSize > outputPosition[i]) {
					emptyQueues = false;
					for(int pos = outputPosition[i]; pos < outputSize; pos+=3) {
						long address = output.get(pos);
						long x = output.get(pos+1);
						long y = output.get(pos+2);
						if(address == 255) {
							natX = x;
							natY = y;
						}else {
							List<Long> addressIn = inputs.get((int)address);
							addressIn.add(x);
							addressIn.add(y);
						}
					}
				}else {
					
				}
				outputPosition[i] = outputSize;
			}
			boolean nottransmitting = true;
			for(int i = 0; i<COMPUTER_COUNT; i++) {
				
				List<Long>input = inputs.get(i);
				if(inputPosition[i] == input.size()-1) {
					input.add(-1l);
					inputPosition[i]+=1;
				}else {
					nottransmitting = false;
					inputPosition[i]+=2;
				}
				nics[i].intCode(input);
				List<Long> output = nics[i].getOutputs();
				outputs.set(i, output);
			}
			if(emptyQueues && nottransmitting && counter >0) {
				if(natY == oldNatY) {
					return natY;
				}
				List<Long>input = inputs.get(0);
				input.add(natX);
				input.add(natY);
				oldNatY = natY;
			}
			counter ++;
		}
	}
	
}