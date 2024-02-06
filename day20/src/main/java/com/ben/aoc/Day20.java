package com.ben.aoc;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

import org.javatuples.Pair;

import com.ben.aoc.dijkstra.Dijkstra;


public class Day20 {
	static char[][] maze;
	IntPoint startPos;
	
	int outerUpEdge = 0;
	int innerUpEdge = 0;
	int innerDownEdge = 0;
	int outerDownEdge = 0;
	
	int outerLeftEdge = 0;
	int innerLeftEdge = 0;
	int innerRightEdge = 0;
	int outerRightEdge = 0;
	
	Map<IntPoint, Warp> doors = new HashMap<IntPoint, Warp>();
	static Map<String, Warp> warps = new HashMap<String, Warp>();
	
	
	
	public Day20(String fileName) {
		List<String> lines = Util.readFile(getClass(), fileName);
		
		maze = new char[lines.size()][];
		for(int i = 0; i<lines.size(); i++) {
			String line = lines.get(i);
			maze[i] = line.toCharArray();
			line.chars().filter(c -> c == ' ').count();
			if(line.contains("#") && outerUpEdge == 0) {
				outerUpEdge = i;
			}else if(line.chars().filter(c -> c == ' ').count() > 4 && outerUpEdge != 0 && innerUpEdge == 0) {
				innerUpEdge = i-1;
			}else if(line.chars().filter(c -> c == ' ').count() < 5 && innerUpEdge != 0 && innerDownEdge == 0) {
				innerDownEdge = i;
			}else if(line.chars().filter(c -> c == ' ').count() > 4 && innerDownEdge != 0 && outerDownEdge == 0) {
				outerDownEdge = i-1;
			}
		}
		for(int i = 0; i<maze[0].length; i++) {
			if(outerLeftEdge == 0) {
				if(columnContainsChar(i, '#')) {
					outerLeftEdge = i;
				}
			}else if(innerLeftEdge == 0) {
				if(columnContainsSpaces(i)) {
					innerLeftEdge = i-1;
				}
			}else if(innerRightEdge == 0) {
				if(!columnContainsSpaces(i)) {
					innerRightEdge = i;
				}
			}else if(outerRightEdge == 0) {
				if(columnContainsSpaces(i)) {
					outerRightEdge = i-1;
				}
			}
		}
		findWarps();
		
		for(Entry<IntPoint, Warp> e : doors.entrySet()) {
			findEdges(e.getKey(), e.getValue());
		}
	}

	public long puzzle1() {
		MazeState start = new MazeState("AA", 0, false);
		MazeState finish = (MazeState) Dijkstra.calculateShortestPath(start);
		
		return finish.getDistance();
	}


	public long puzzle2() {
		
		MazeRecursiveState start = new MazeRecursiveState("AA", 0, 0, false);
		MazeRecursiveState finish = (MazeRecursiveState) Dijkstra.calculateShortestPath(start);
		
		return finish.getDistance();
	}
	
	private void findEdges(IntPoint start, Warp node) {
		Queue<Pair<IntPoint, Integer>> queue = new ArrayDeque<Pair<IntPoint, Integer>>();
		Set<IntPoint> visited = new HashSet<IntPoint>();
		
		queue.add(new Pair<IntPoint, Integer>(start, 0));
		Pair<IntPoint, Integer> current;
		
		while(!queue.isEmpty()) {
			current = queue.remove();
			IntPoint point = current.getValue0();
			
			if(isEdgePoint(point) && start!=point) {
				String destName = doors.get(point).getName();
				if(isOuterPoint(point)) {
					destName = destName + "o";
				}else {
					destName = destName + "i";
				}
				Warp destination = warps.get(destName);
				int distance = current.getValue1();
				if(!destName.equals("ZZo")) {
					distance++;
				}
				node.addDestination(destination, distance);
			}else {
				visited.add(point);
				for(IntPoint neighbour : nextPoints(point, visited)) {
					Pair<IntPoint, Integer> next = new Pair<IntPoint, Integer>(neighbour, current.getValue1()+1);
					queue.add(next);
				}
			}
		}
	}
	
	private boolean columnContainsChar(int column, char c) {
		for(int j = 0; j<maze.length; j++) {
			if(maze[j][column] == c) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean columnContainsSpaces(int column) {
		int count = 0;
		for(int j = 0; j<maze.length; j++) {
			if(maze[j][column] == ' ') {
				count++;
			}
			if(count >4) {
				return true;
			}
		}
		
		return false;
	}
	
	private void findWarps() {
		for(int i = 2; i<maze.length-2 && i<maze[0].length-2; i++) {
			if(i<maze[0].length-2) {
				//check top edge
				if(maze[outerUpEdge][i] == '.') {
					String name = String.valueOf(maze[outerUpEdge-2][i]) + String.valueOf(maze[outerUpEdge-1][i]);
					storeWarp(name, new IntPoint(i, outerUpEdge), false);
				}
				//check inner top
				if(maze[innerUpEdge][i] == '.' && i > innerLeftEdge && i < innerRightEdge) {
					String name = String.valueOf(maze[innerUpEdge+1][i]) + String.valueOf(maze[innerUpEdge+2][i]);
					storeWarp(name, new IntPoint(i, innerUpEdge), true);
				}
				//check inner down
				if(maze[innerDownEdge][i] == '.' && i > innerLeftEdge && i < innerRightEdge) {
					String name = String.valueOf(maze[innerDownEdge-2][i]) + String.valueOf(maze[innerDownEdge-1][i]);
					storeWarp(name, new IntPoint(i, innerDownEdge), true);
				}
				//check down edge
				if(maze[outerDownEdge][i] == '.') {
					String name = String.valueOf(maze[outerDownEdge+1][i]) + String.valueOf(maze[outerDownEdge+2][i]);
					storeWarp(name, new IntPoint(i, outerDownEdge), false);
				}
			}
			if(i<maze.length-2) {
				//check left edge
				if(maze[i][outerLeftEdge] == '.') {
					String name = String.valueOf(maze[i][outerLeftEdge-2]) + String.valueOf(maze[i][outerLeftEdge-1]);
					storeWarp(name, new IntPoint(outerLeftEdge, i), false);
				}
				//check inner left edge
				if(maze[i][innerLeftEdge] == '.' && i > innerUpEdge && i < innerDownEdge) {
					String name = String.valueOf(maze[i][innerLeftEdge+1]) + String.valueOf(maze[i][innerLeftEdge+2]);
					storeWarp(name, new IntPoint(innerLeftEdge, i), true);
				}
				//check inner right edge
				if(maze[i][innerRightEdge] == '.' && i > innerUpEdge && i < innerDownEdge) {
					String name = String.valueOf(maze[i][innerRightEdge-2]) + String.valueOf(maze[i][innerRightEdge-1]);
					storeWarp(name, new IntPoint(innerRightEdge, i), true);
				}
				//check right edge
				if(maze[i][outerRightEdge] == '.') {
					String name = String.valueOf(maze[i][outerRightEdge+1]) + String.valueOf(maze[i][outerRightEdge+2]);
					storeWarp(name, new IntPoint(outerRightEdge, i), false);
				}
			}
		}
	}
	
	private void storeWarp(String name, IntPoint point, boolean isInner) {
		Warp node = new Warp(name, isInner);
		String newName = isInner ? name + "i" : name + "o";
		warps.put(newName, node);
		doors.put(point, node);
		if(name.equals("AA")) {
			startPos = point;
		}
	}
	
	private List<IntPoint> nextPoints(IntPoint currentPoint, Set<IntPoint> visited){
		List<IntPoint> nextPoints = new ArrayList<IntPoint>();
		for(Point<Integer> p : currentPoint.allNeighbours()) {
			IntPoint point = (IntPoint) p;
			if(!visited.contains(point) && mazeChar(point) == '.') {
				nextPoints.add(point);
			}
		}
		return nextPoints;
	}
	
	private char mazeChar(IntPoint point) {
		return maze[point.getY()][point.getX()];
	}
	
	private boolean isEdgePoint(IntPoint point) {
		if(isOuterPoint(point)) {
			return true;
		}
		
		int x = point.getX();
		int y = point.getY();

		if((x > innerLeftEdge && x < innerRightEdge) && (y == innerUpEdge || y == innerDownEdge)) {
			return true;
		}
		if((y > innerUpEdge && y < innerDownEdge) && (x == innerLeftEdge || x == innerRightEdge)) {
			return true;
		}
		
		return false;
	}
	
	private boolean isOuterPoint(IntPoint point) {
		int x = point.getX();
		int y = point.getY();
		
		if(y == outerUpEdge || y == outerDownEdge) {
			return true;
		}
		if(x == outerLeftEdge || x == outerRightEdge) {
			return true;
		}
		
		return false;
	}
	
}
