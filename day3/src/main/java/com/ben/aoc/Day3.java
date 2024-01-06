package com.ben.aoc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day3 {
	
	Map<CartesianIntPoint, Long> path1Points;
	Map<CartesianIntPoint, Long> path2Points;
	Set<CartesianIntPoint> path1Set;
	Set<CartesianIntPoint> path2Set;
	
	public Day3(String fileName) {
		List<String>lines = Util.readFile(getClass(), fileName);
		List<String> path1Instructions = Arrays.asList(lines.get(0).split(","));
		List<String> path2Instructions = Arrays.asList(lines.get(1).split(","));
		path1Points = buildMapOfPoints(path1Instructions);
		path2Points = buildMapOfPoints(path2Instructions);
				
		path1Set = new HashSet<CartesianIntPoint>();
		path2Set = new HashSet<CartesianIntPoint>();
		
		for(Map.Entry<CartesianIntPoint, Long> entry : path1Points.entrySet()) {
			path1Set.add(entry.getKey());
		}
		for(Map.Entry<CartesianIntPoint, Long> entry : path2Points.entrySet()) {
			path2Set.add(entry.getKey());
		}
		
		path1Set.retainAll(path2Set);
	}

	public long puzzle1() {
		long result = Long.MAX_VALUE;
		CartesianIntPoint origin = new CartesianIntPoint(0, 0);

		
		for(CartesianIntPoint point : path1Set) {
			result = Math.min(result, origin.manhattanDistance(point));
		}
		
		return result;
	}
	
	public long puzzle2() {
		long result = Long.MAX_VALUE;
		
		for(CartesianIntPoint intersection : path1Set) {
			long totalSteps = path1Points.get(intersection) + path2Points.get(intersection);
			result = Math.min(totalSteps, result);
		}

		return result;
	}
	
	private Map<CartesianIntPoint, Long> buildMapOfPoints(List<String> instructions){
		Map<CartesianIntPoint, Long> points = new HashMap<CartesianIntPoint, Long>();
		
		CartesianIntPoint position = new CartesianIntPoint(0, 0);
		long count = 0;
		for(String instruction : instructions) {
			char direction = instruction.charAt(0);
			int distance = Integer.parseInt(instruction.substring(1));
			switch(direction) {
			case 'U':
				for(int i = 1; i<= distance; i++) {
					int x = position.getX();
					int y = position.getY() + i;
					count++;
					
					points.putIfAbsent(new CartesianIntPoint(x, y), count);
				}
				position = new CartesianIntPoint(position.getX(), position.getY() + distance);
				break;
			case 'D':
				for(int i = 1; i<= distance; i++) {
					int x = position.getX();
					int y = position.getY() - i;
					count++;
					
					points.putIfAbsent(new CartesianIntPoint(x, y), count);
				}
				position = new CartesianIntPoint(position.getX(), position.getY() - distance);
				break;
			case 'R':
				for(int i = 1; i<= distance; i++) {
					int x = position.getX() + i;
					int y = position.getY();
					count++;
					
					points.putIfAbsent(new CartesianIntPoint(x, y), count);
				}
				position = new CartesianIntPoint(position.getX() + distance, position.getY());
				break;
			case 'L':
				for(int i = 1; i<= distance; i++) {
					int x = position.getX() - i;
					int y = position.getY();
					count++;
					
					points.putIfAbsent(new CartesianIntPoint(x, y), count);
				}
				position = new CartesianIntPoint(position.getX() - distance, position.getY());
				break;
			}	
		}
		return points;
	}
	
}
