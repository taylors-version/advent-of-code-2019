package com.ben.aoc;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

import com.ben.aoc.dijkstra.Dijkstra;
import com.ben.aoc.dijkstra.State;

public class Day15 {
	
	String[] instructions;
	Computer robot;
	List<Long> inputs;
	static Map<CartesianIntPoint, Integer> maze;
	CartesianIntPoint currentPoint;
	boolean tank = false;
	int count = 0;
	int maxX = 0;
	int minX = 0;
	int maxY = 0;
	int minY = 0;
	
	boolean tankFound = false;
	
	public Day15(String fileName) {
		List<String> lines = Util.readFile(getClass(), fileName);
		instructions = lines.get(0).split(",");
		String[] ints = Arrays.copyOf(instructions, instructions.length);
		maze = new HashMap<CartesianIntPoint, Integer>();
		currentPoint = new CartesianIntPoint(0,0);
		maze.put(currentPoint, 1);
		int direction = 1;
		robot = new Computer(ints);
		inputs = new ArrayList<Long>();
		findWall(direction);
		while(!tankFound) {
			direction = followWall(direction);
		}

		ints = Arrays.copyOf(instructions, instructions.length);
		currentPoint = new CartesianIntPoint(0,0);
		direction = 1;
		robot = new Computer(ints);
		inputs = new ArrayList<Long>();
		findWall(direction);
		tankFound = false;
		while(!tankFound) {
			direction = followWallReverse(direction);
		}
	}

	public long puzzle1() {
		System.out.println(printMaze());
		MazeState state = (MazeState) Dijkstra.calculateShortestPath(new MazeState(new CartesianIntPoint(0, 0)));
		return state.getDistance();
	}
	
	public long puzzle2() {
		long result = 0;
		MazeState.reset();
		Set<State> startState = new HashSet<State>();
		startState.add(new MazeState(currentPoint));
		Queue<Set<State>> queue = new ArrayDeque<Set<State>>();
		queue.add(startState);
		
		Set<State> currentStates;
		
		while(!queue.isEmpty()) {
			currentStates = queue.remove();
			Set<State> nextStates = new HashSet<State>();
			for(State state : currentStates) {
				nextStates.addAll(state.next());
			}
			if(!nextStates.isEmpty()) {
				queue.add(nextStates);
				result++;
			}
		}
		return result;
	}
	
	private void findWall(int direction) {
		while(sendCommand(direction) != 0);
	}
	
	/**
	 * 
	 * @param direction of wall from robot
	 * @return new direction of wall
	 */
	private int followWall(int direction) {
		int walkingDirection = clockWise(direction);
		while(true) {
			if(test(direction)) {
				return antiClockWise(direction);
			}
			if(sendCommand(walkingDirection) == 0) {
				return clockWise(direction);
			}
		}
	}
	
	private int followWallReverse(int direction) {
		int walkingDirection = antiClockWise(direction);
		while(true) {
			if(test(direction)) {
				return clockWise(direction);
			}
			if(sendCommand(walkingDirection) == 0) {
				return antiClockWise(direction);
			}
		}
	}
	
	/**
	 * Checks if the direction is free of walls. Walks to that step if it is
	 * 
	 * @param direction to peep in
	 * @return true if not a wall
	 */
	private boolean test(int direction) {
		int result = sendCommand(direction);
		if(result == 0) {
			return false;
		}
		return true;
	}
	
	private int sendCommand(int command) {
		inputs.add((long)command);
		robot.intCode(inputs);
		List<Long> outputs = robot.getOutputs();
		int output = Math.toIntExact(outputs.get(outputs.size() - 1));
		CartesianIntPoint nextPoint = currentPoint;

		switch(command) {
		case 1:
			nextPoint = (CartesianIntPoint) currentPoint.above();
			break;
		case 2:
			nextPoint = (CartesianIntPoint) currentPoint.below();
			break;
		case 3:
			nextPoint = (CartesianIntPoint) currentPoint.left();
			break;
		case 4:
			nextPoint = (CartesianIntPoint) currentPoint.right();
			break;
		}
		int x = nextPoint.getX();
		int y = nextPoint.getY();
		maze.put(new CartesianIntPoint(x, y), output+1);
		if(output != 0) {
			count ++;
			currentPoint = nextPoint;
		}
		maxX = Math.max(maxX, nextPoint.getX());
		maxY = Math.max(maxY, nextPoint.getY());
		minX = Math.min(minX, nextPoint.getX());
		minY = Math.min(minY, nextPoint.getY());
		//System.out.print(printMaze());
		if(output == 2) {
			tankFound = true;
		}
		return output;
	}
	
	private int clockWise(int d) {
		switch(d) {
		case 1:
			return 4;
		case 2:
			return 3;
		case 3:
			return 1;
		case 4:
			return 2;
		}
		return 0;
	}
	
	private int antiClockWise(int d) {
		switch(d) {
		case 4:
			return 1;
		case 3:
			return 2;
		case 1:
			return 3;
		case 2:
			return 4;
		}
		return 0;
	}
	
	private String printMaze() {
		int xDiff = maxX - minX;
		int yDiff = maxY - minY;
		
		int[][] map = new int[yDiff + 1][xDiff + 1];
		for(Entry<CartesianIntPoint, Integer> entry : maze.entrySet()) {
			CartesianIntPoint point = entry.getKey();
			int x = point.getX() - minX;
			int y = yDiff - (point.getY() - minY);
			map[y][x] = point.equals(currentPoint) && entry.getValue() != 3 ? 20 : point.equals(new CartesianIntPoint(0, 0)) ? 10 : entry.getValue();
		}
		
		String result = "\n";
		for(int i = 0; i<map.length; i++) {
			for(int j = 0; j<map[i].length; j++) {
				int value = map[i][j];
				String emoji = value == 20 ? "R" : value == 10 ? "S" : value == 1 ? "#" : value == 2 ? "." : value == 3 ? "X" : " ";
				result = result + emoji;
			}
			result = result + "\n";
		}
		
		return result;
	}
}