package com.ben.aoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day19 {
	
	
	String[] instructions;
	Map<IntPoint, Boolean> cache = new HashMap<IntPoint, Boolean>();
	
	public Day19(String fileName) {
		List<String> lines = Util.readFile(getClass(), fileName);
		instructions = lines.get(0).split(",");
	}

	public long puzzle1() {
		long result = 0;
		
		
		for(int x = 0; x<50; x++) {
			for(int y=0; y<50; y++) {
				IntPoint point = new IntPoint(x,y);
				if(isPointInTractor(point)) {
					result++;
				}
			}
		}
		return result;
	}
	
	public long puzzle2() {
		
		IntPoint testPoint = findFirstPoint();
		IntPoint rowStart = new IntPoint(testPoint.getX(), testPoint.getY());
		while(true) {
			if(isPointStartOfSanta(testPoint)) {
				return testPoint.getX()*10000 + testPoint.getY();
			}
			testPoint = getNextPoint(testPoint, rowStart);
			if(testPoint.getY() != rowStart.getY()) {
				rowStart = new IntPoint(testPoint.getX(), testPoint.getY());
			}
		}
	}
	
	private boolean isPointStartOfSanta(IntPoint point) {
		int x = point.getX();
		int y = point.getY();
		IntPoint farEast = new IntPoint(x+99, y);
		IntPoint farSouth = new IntPoint(x, y+99);
		IntPoint farSouthEast = new IntPoint(x+99, y+99);
		
		return (isPointInTractor(farEast) && isPointInTractor(farSouth) && isPointInTractor(farSouthEast));
	}
	
	private boolean isPointInTractor(IntPoint point) {
		if(!cache.containsKey(point)) {
			String[] ins = new String[instructions.length];
			for(int i =0; i<ins.length; i++) {
				ins[i] = instructions[i];
			}
			Computer drone = new Computer(ins);
			List<Long> inputs = new ArrayList<Long>();
			inputs.add((long)point.getX());
			inputs.add((long)point.getY());
			drone.intCode(inputs);
			List<Long> outputs = drone.getOutputs();
			long val = outputs.get(outputs.size() - 1);
			cache.put(point, val == 1L);
		}
		return cache.get(point);
	}
	
	private IntPoint findFirstPoint() {
		int n = 1;
		
		while(true) {
			for(IntPoint p : getSouthEastPoints(n)) {
				if(isPointInTractor(p)) {
					return p;
				}
			}
			n++;
		}
	}
	
	private List<IntPoint> getSouthEastPoints(int n){
		List<IntPoint> points = new ArrayList<IntPoint>();
		
		for(int i = 0; i<n; i++) {
			points.add(new IntPoint(n, i));
			points.add(new IntPoint(i, n));
		}
		points.add(new IntPoint(n, n));
		
		return points;
	}
	
	private IntPoint getNextPoint(IntPoint current, IntPoint rowStart) {
		
		if(isPointInTractor((IntPoint) current.right())) {
			return (IntPoint) current.right();
		}
		IntPoint nextPoint = (IntPoint) rowStart.below();
		while(!isPointInTractor(nextPoint)) {
			nextPoint = (IntPoint) nextPoint.right();
		}
		return nextPoint;
		
	}
	
}