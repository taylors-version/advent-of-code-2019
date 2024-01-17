package com.ben.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day17 {
	
	
	Computer ascii;
	String[] instructions;
	List<Long> output;
	char[][] grid;
	IntPoint start;
	
	final static String mmr = "C,A,C,B,B,C,A,B,A,A" + (char)10;
	final static String A = "L,12,R,8,R,8" + (char)10;
	final static String B = "L,8,R,6,R,6,R,10,L,8" + (char)10;
	final static String C = "L,8,R,10,L,8,R,8" + (char)10;

	
	boolean tankFound = false;
	
	public Day17(String fileName) {
		List<String> lines = Util.readFile(getClass(), fileName);
		instructions = lines.get(0).split(",");
	}

	public long puzzle1() {
		long result = 0;
		
		String[] ints = Arrays.copyOf(instructions, instructions.length);
		ascii = new Computer(ints);
		
		ascii.intCode();
		
		output = ascii.getOutputs();
		int rowLength = output.indexOf(10L);
		grid = new char[output.size()/rowLength][rowLength];
		int row = 0;
		int column = 0;
		for(long l : output) {
			char character = (char)l;
			switch(character) {
			case 10:
				row++;
				column=0;
				break;
			default:
				grid[row][column] = character;
				if(character == '^') {
					start = new IntPoint(column, row);
				}
				column++;
				break;
			}
		}
		
		List<IntPoint> intersections = new ArrayList<IntPoint>();
		
		for(int i = 0; i<grid.length; i++) {
			for(int j = 0; j<grid[0].length; j++) {
				if(grid[i][j] != '.') {
					boolean isIntersection = true;
					IntPoint intersection = new IntPoint(j, i);
					for(Point<Integer> neighbour : intersection.allNeighbours()) {
						if(isValidPoint(neighbour)) {
							if(grid[neighbour.getY()][neighbour.getX()] == '.') {
								isIntersection = false;
							}
						}else {
							isIntersection = false;
						}
					}
					if(isIntersection) {
						intersections.add(intersection);
					}
				}
			}
		}
		
		for(IntPoint point : intersections) {
			result += point.getX()*point.getY();
		}
		return result;
	}
	
	public long puzzle2() {
		String[] ints = Arrays.copyOf(instructions, instructions.length);
		ints[0] = "2";
		ascii = new Computer(ints);
		
		List<Long> inputs = new ArrayList<Long>();
		String input = mmr + A + B + C + "n" + (char)10;
		
		for(char c : input.toCharArray()) {
			int i  = c;
			inputs.add((long) i);
		}
		
		ascii.intCode(inputs);
		
		List<Long> output = ascii.getOutputs();
		return output.get(output.size() - 1);
	}
	
	private boolean isValidPoint(Point<Integer> point) {
		if(point.getX() >= 0 && point.getX()<grid[0].length && point.getY()>= 0 && point.getY()<grid.length) {
			return true;
		}
		return false;
	}
}