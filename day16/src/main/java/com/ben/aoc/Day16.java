package com.ben.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Day16 {
	List<String> lines;
	List<List<Integer>> adds = new ArrayList<List<Integer>>();
	List<List<Integer>> subs = new ArrayList<List<Integer>>();
	int[] input;
	
	public Day16(String fileName) {
		lines = Util.readFile(getClass(), fileName);
		/*String line = lines.get(0);
		input = new int[line.length()];
		for(int i = 0; i<input.length; i++) {
			input[i] = (int)line.charAt(i)-'0';
		}
		for(int i = 0; i<input.length; i++) {
			List<Integer> adder = new ArrayList<Integer>();
			List<Integer> subber = new ArrayList<Integer>();
			int repeat = i+1;
			for(int j = 0; j<(repeat*input.length/2); j++) {
				for(int k = i; k<repeat+i; k++) {
					int adding = j*4*repeat+k;
					int subbing = adding+repeat*2;
					if(adding<input.length) {
						adder.add(adding);
					}else {
						break;
					}
					if(subbing<input.length) {
						subber.add(subbing);
					}
				}
			}
			adds.add(adder);
			subs.add(subber);
		}*/
	}

	public String puzzle1() {
		String line = lines.get(0);
		input = new int[line.length()];
		for(int i = 0; i<input.length; i++) {
			input[i] = (int)line.charAt(i)-'0';
		}
		for(int i = 0; i<input.length; i++) {
			List<Integer> adder = new ArrayList<Integer>();
			List<Integer> subber = new ArrayList<Integer>();
			int repeat = i+1;
			for(int j = 0; j<(repeat*input.length/2); j++) {
				for(int k = i; k<repeat+i; k++) {
					int adding = j*4*repeat+k;
					int subbing = adding+repeat*2;
					if(adding<input.length) {
						adder.add(adding);
					}else {
						break;
					}
					if(subbing<input.length) {
						subber.add(subbing);
					}
				}
			}
			adds.add(adder);
			subs.add(subber);
		}
		
		for (int counter = 0; counter<100; counter++) {
			int[] output = new int[input.length];
			for (int i = 0; i<input.length; i++) {
				List<Integer> adder = adds.get(i);
				List<Integer> subber = subs.get(i);
				int sum = 0;
				for(int adding : adder) {
					sum+=input[adding];
				}
				for(int subbing : subber) {
					sum-=input[subbing];
				}
				output[i] = (Math.abs(sum)%10);
			}
			input = Arrays.copyOf(output, output.length);
			
		}
		return printNDigits(input, 8, 0);
		
	}


	public String puzzle2() {
		String line = lines.get(0);
		int offset = getOffset(line);
		input = new int[line.length()*10000];
		for(int multiple = 0; multiple<10000; multiple++) {
			for(int i = 0; i<line.length(); i++) {
				input[i+(multiple*line.length())] = (int)line.charAt(i)-'0';
			}
		}
		
		for (int i = 0; i<100; i++) {
			int[] output = new int[input.length];
			for(int j = input.length -1 ; j >= offset; j--){
				if(j == input.length-1) {
					output[j] = input[j];
				}else {
					output[j] = (output[j+1] + input[j])%10;
				}
			}
			input = Arrays.copyOf(output, output.length);
		}
		
		return printNDigits(input, 8, offset);
	}
	
	private String printNDigits(int[] in, int n, int offset) {
		StringBuilder sb = new StringBuilder();
		for(int i = offset; i<n+offset; i++) {
			sb.append(input[i]);
		}
		return sb.toString();
	}
	
	private int getOffset(String in) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i<7; i++) {
			sb.append(in.charAt(i));
		}
		return Integer.parseInt(sb.toString());
	}
	
}
