package com.ben.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Day11 {
	
	String[] instructions;
	Map<IntPoint, Integer> painting;
	int minX = 0;
	int maxX = 0;
	int minY = 0;
	int maxY = 0;
	
	public Day11(String fileName) {
		List<String> lines = Util.readFile(getClass(), fileName);
		instructions = lines.get(0).split(",");
		
	}

	public long puzzle1() {
		String[] ints = Arrays.copyOf(instructions, instructions.length);
		painting = new HashMap<IntPoint, Integer>();
		IntPoint currentPosition = new IntPoint(0, 0);
		painting.put(currentPosition, 0);
		
		Direction robotDirection = new Direction('u');
		
		Computer robot = new Computer(ints);
		List<Long> inputs = new ArrayList<Long>();
		
		boolean robotFinished = false;
		while (!robotFinished) {
			int currentColour = painting.containsKey(currentPosition) ? painting.get(currentPosition) : 0;
			inputs.add((long) currentColour);
			robotFinished = robot.intCode(inputs);
			List<Long> outputs = robot.getOutputs();
			long newColour = outputs.get(outputs.size()-2);
			long turn = outputs.get(outputs.size()-1);
			painting.put(currentPosition, (int)newColour);
			robotDirection = turn == 1 ? robotDirection.rotateClockwise() : robotDirection.rotateAntiClockwise();
			currentPosition = (IntPoint) currentPosition.getByDirection(robotDirection);
		}
		
		return painting.size();
	}
	
	public String puzzle2() {
		String[] ints = Arrays.copyOf(instructions, instructions.length);
		painting = new HashMap<IntPoint, Integer>();
		IntPoint currentPosition = new IntPoint(0, 0);
		painting.put(currentPosition, 1);
		
		Direction robotDirection = new Direction('u');
		
		Computer robot = new Computer(ints);
		List<Long> inputs = new ArrayList<Long>();
		
		boolean robotFinished = false;
		while (!robotFinished) {
			int currentColour = painting.containsKey(currentPosition) ? painting.get(currentPosition) : 0;
			inputs.add((long) currentColour);
			robotFinished = robot.intCode(inputs);
			List<Long> outputs = robot.getOutputs();
			long newColour = outputs.get(outputs.size()-2);
			long turn = outputs.get(outputs.size()-1);
			painting.put(currentPosition, (int)newColour);
			robotDirection = turn == 1 ? robotDirection.rotateClockwise() : robotDirection.rotateAntiClockwise();
			currentPosition = (IntPoint) currentPosition.getByDirection(robotDirection);
			minX = Math.min(minX, currentPosition.getX());
			maxX = Math.max(maxX, currentPosition.getX());
			minY = Math.min(minY, currentPosition.getY());
			maxY = Math.max(maxY, currentPosition.getY());
		}
		
		return printPainting();
	}
	
	private String printPainting() {
		int[][] arrayOutput = new int[maxY-minY+1][maxX-minX+1];
		
		for(Entry<IntPoint, Integer> entry : painting.entrySet()) {
			IntPoint point = entry.getKey();
			int x = point.getX() - minX;
			int y = point.getY() - minY;
			arrayOutput[y][x] = entry.getValue();
		}
		
		String result = "\n";
		for(int i = 0; i<arrayOutput.length; i++) {
			for(int j = 0; j<arrayOutput[i].length; j++) {
				int value = arrayOutput[i][j];
				String emoji = value == 0 ? "⬛️" : "⬜";
				result = result + emoji;
			}
			result = result + "\n";
		}
		
		return result;
	}
}
	
