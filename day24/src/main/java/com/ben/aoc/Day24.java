package com.ben.aoc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Day24 {
	
	char[][]map = new char[5][];
	Set<Long> states = new HashSet<Long>();
	
	public Day24(String fileName) {
		List<String> lines = Util.readFile(getClass(), fileName);
		
		for(int i = 0; i<lines.size(); i++) {
			map[i] = lines.get(i).toCharArray();
		}
	}

	public long puzzle1() {
		
		char[][] mapCopy = Arrays.copyOf(map, 5);
		states.add(mapValue(mapCopy));
		
		while(true) {
			char[][]newMap = tick(mapCopy);
			long mapValue = mapValue(newMap);

			if(states.contains(mapValue)) {
				return mapValue;
			}
			states.add(mapValue);
			mapCopy = newMap;
		}
		
	}


	public long puzzle2() {
		return 0;
	}
	
	private char[][] tick(char[][] map) {
		
		char[][] newMap = new char[5][5];
		
		for(int j = 0; j < map.length; j++) {
			for(int i = 0; i< map[0].length; i++) {
				int bugCount = 0;
				bugCount += getPointValue(map, j-1, i);
				bugCount += getPointValue(map, j+1, i);
				bugCount += getPointValue(map, j, i-1);
				bugCount += getPointValue(map, j, i+1);
				if(bugCount == 1) {
					newMap[j][i] = '#';
				}else if(bugCount == 2 && map[j][i] == '.') {
					newMap[j][i] = '#';
				}else {
					newMap[j][i] = '.';
				}
			}
		}
		
		
		return newMap;
	}
	
	private int getPointValue(char[][] map, int y, int x) {
		if(x<0 || x > 4 || y < 0 || y > 4) {
			return 0;
		}
		char c = map[y][x];
		return c == '#' ? 1 : 0;
	}
	
	private long mapValue(char[][] map) {
		long result = 0;
		int pow = 0;
		for(int j = 0; j < map.length; j++) {
			for(int i = 0; i < map[0].length; i++) {
				if(map[j][i] == '#') {
					result += Math.pow(2, pow);
				}
				pow++;
			}
		}
		
		return result;
	}
	
}
