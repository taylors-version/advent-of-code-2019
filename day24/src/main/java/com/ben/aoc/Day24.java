package com.ben.aoc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class Day24 {
	
	char[][]map = new char[5][];
	Map<Integer, char[][]> recursiveMaps = new HashMap<Integer, char[][]>();
	int minMap = 0;
	int maxMap = 0;
	
	
	public Day24(String fileName) {
		List<String> lines = Util.readFile(getClass(), fileName);
		
		for(int i = 0; i<lines.size(); i++) {
			map[i] = lines.get(i).toCharArray();
		}
	}

	public long puzzle1() {
		Set<Long> states = new HashSet<Long>();
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
		long result = 0;
		char[][] map0 = Arrays.copyOf(map, 5);
		
		recursiveMaps.put(0, map0);
		for(int i = 0; i<200; i++) {
			recursiveTick();
		}
		
		for(Entry<Integer, char[][]> entry : recursiveMaps.entrySet()) {
			result+=countBugs(entry.getValue());
		}
		
		return result;
	}
	
	private char[][] tick(char[][] map) {
		
		char[][] newMap = new char[5][5];
		
		for(int j = 0; j < map.length; j++) {
			for(int i = 0; i< map[0].length; i++) {
				int bugCount = 0;
				bugCount += getPointValue(map, i, j-1);
				bugCount += getPointValue(map, i, j+1);
				bugCount += getPointValue(map, i-1, j);
				bugCount += getPointValue(map, i+1, j);
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
	
	private void recursiveTick() {
		Map<Integer, char[][]> tempCollection = new HashMap<Integer, char[][]>();
		for(Entry<Integer, char[][]> entry : recursiveMaps.entrySet()) {
			char[][] thisMap = entry.getValue();
			char[][] newMap = new char[5][5];
			
			for(int j = 0; j < thisMap.length; j++) {
				for(int i = 0; i < thisMap[0].length; i++) {
					if(!(j==2 && i==2)) { //ignore middle point as that's a new map
						int bugCount = getRecursiveBugCount(entry.getKey(), i, j);
						if(bugCount == 1) {
							newMap[j][i] = '#';
						}else if(bugCount == 2 && thisMap[j][i] != '#') {
							newMap[j][i] = '#';
						}else {
							newMap[j][i] = '.';
						}
					}else {
						newMap[j][i] = '.';	
					}
				}
			}
			tempCollection.put(entry.getKey(), newMap);
		}
		//Check to add a new top and bottom layer
		char[][] newBottomMap = new char[5][5];
		boolean createNewBottom = false;
		for(int j = 0; j < newBottomMap.length; j++) {
			for(int i = 0; i < newBottomMap[0].length; i++) {
				if(!(j==2 && i==2)) { //ignore middle point as that's a new map
					int bugCount = getRecursiveBugCount(minMap-1, i, j);
					if(bugCount == 1 || bugCount ==2) {
						newBottomMap[j][i] = '#';
						createNewBottom = true;
					}else {
						newBottomMap[j][i] = '.';
					}
				}else {
					newBottomMap[j][i] = '.';	
				}
			}
		}
		if(createNewBottom) {
			tempCollection.put(minMap-1, newBottomMap);
			minMap--;
		}
		
		char[][] newTopMap = new char[5][5];
		boolean createNewTop = false;
		for(int j = 0; j < newBottomMap.length; j++) {
			for(int i = 0; i < newBottomMap[0].length; i++) {
				if(!(j==2 && i==2)) { //ignore middle point as that's a new map
					int bugCount = getRecursiveBugCount(maxMap+1, i, j);
					if(bugCount == 1 || bugCount ==2) {
						newTopMap[j][i] = '#';
						createNewTop = true;
					}else {
						newTopMap[j][i] = '.';
					}
				}else {
					newTopMap[j][i] = '.';
				}
			}
		}
		if(createNewTop) {
			tempCollection.put(maxMap+1, newTopMap);
			maxMap++;
		}
		recursiveMaps = tempCollection;
	}
	
	private int getPointValue(char[][] map, int x, int y) {
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
	
	private long countBugs(char[][] map) {
		long result = 0;
		for(int j = 0; j < map.length; j++) {
			for(int i = 0; i < map[0].length; i++) {
				if(map[j][i] == '#') {
					result ++;
				}
			}
		}
		return result;
	}
	
	private int getRecursiveBugCount(int layer, int x, int y) {
		int count = 0;
		
		count+= getBugCountAbove(layer, x, y);
		count+= getBugCountBelow(layer, x, y);
		count+= getBugCountLeft(layer, x, y);
		count+= getBugCountRight(layer, x, y);
		
		return count;
	}
	
	/**
	 * Returns bug count above this cell (recursive maps)
	 * @param layer
	 * @param x
	 * @param y
	 * @return
	 */
	private int getBugCountAbove(int layer, int x, int y) {
		char[][] map = recursiveMaps.get(layer);
		if(map == null) {
			map = new char[5][5];
		}
		if(y == 3 && x == 2) {//Look into above map
			if(!recursiveMaps.containsKey(layer +1)) {
				return 0;
			}
			char[][] aboveMap = recursiveMaps.get(layer + 1);
			int bugCount = 0;
			for(int i = 0; i < aboveMap[0].length; i++) {
				char c = aboveMap[4][i];
				if(c == '#') {
					bugCount++;
				}
			}
			return bugCount;
			
		}
		if(y == 0) {//look into below map
			if(!recursiveMaps.containsKey(layer - 1)) {
				return 0;
			}
			char[][] belowMap = recursiveMaps.get(layer - 1);
			char c = belowMap[1][2];
			return c == '#' ? 1 : 0;
		}
		char c = map[y-1][x];
		return c == '#' ? 1 : 0;
	}
	
	/**
	 * Returns bug count below this cell (recursive maps)
	 * @param layer
	 * @param x
	 * @param y
	 * @return
	 */
	private int getBugCountBelow(int layer, int x, int y) {
		char[][] map = recursiveMaps.get(layer);
		if(map == null) {
			map = new char[5][5];
		}
		if(y == 1 && x == 2) { //Look into above map
			if(!recursiveMaps.containsKey(layer +1)) {
				return 0;
			}
			char[][] aboveMap = recursiveMaps.get(layer + 1);
			int bugCount = 0;
			for(int i = 0; i < aboveMap[0].length; i++) {
				char c = aboveMap[0][i];
				if(c == '#') {
					bugCount++;
				}
			}
			return bugCount;
		}
		if(y == 4) { //look into below map
			if(!recursiveMaps.containsKey(layer - 1)) {
				return 0;
			}
			char[][] belowMap = recursiveMaps.get(layer - 1);
			char c = belowMap[3][2];
			return c == '#' ? 1 : 0;
		}
		char c = map[y+1][x];
		return c == '#' ? 1 : 0;
	}
	
	/**
	 * Returns bug count left of this cell (recursive maps)
	 * @param layer
	 * @param x
	 * @param y
	 * @return
	 */
	private int getBugCountLeft(int layer, int x, int y) {
		char[][] map = recursiveMaps.get(layer);
		if(map == null) {
			map = new char[5][5];
		}
		if(x == 3 && y == 2) { //Look into above map
			if(!recursiveMaps.containsKey(layer +1)) {
				return 0;
			}
			char[][] aboveMap = recursiveMaps.get(layer + 1);
			int bugCount = 0;
			for(int i = 0; i < aboveMap[0].length; i++) {
				char c = aboveMap[i][4];
				if(c == '#') {
					bugCount++;
				}
			}
			return bugCount;
		}
		if(x == 0) { //look into below map
			if(!recursiveMaps.containsKey(layer - 1)) {
				return 0;
			}
			char[][] belowMap = recursiveMaps.get(layer - 1);
			char c = belowMap[2][1];
			return c == '#' ? 1 : 0;
		}
		char c = map[y][x-1];
		return c == '#' ? 1 : 0;
	}
	
	/**
	 * Returns bug count right of this cell (recursive maps)
	 * @param layer
	 * @param x
	 * @param y
	 * @return
	 */
	private int getBugCountRight(int layer, int x, int y) {
		char[][] map = recursiveMaps.get(layer);
		if(map == null) {
			map = new char[5][5];
		}
		if(x == 1 && y ==2) { //Look into above map
			if(!recursiveMaps.containsKey(layer +1)) {
				return 0;
			}
			char[][] aboveMap = recursiveMaps.get(layer + 1);
			int bugCount = 0;
			for(int i = 0; i < aboveMap[0].length; i++) {
				char c = aboveMap[i][0];
				if(c == '#') {
					bugCount++;
				}
			}
			return bugCount;
		}
		if(x == 4) { //look into below map
			if(!recursiveMaps.containsKey(layer - 1)) {
				return 0;
			}
			char[][] belowMap = recursiveMaps.get(layer - 1);
			char c = belowMap[2][3];
			return c == '#' ? 1 : 0;
		}
		char c = map[y][x+1];
		return c == '#' ? 1 : 0;
		
		
	}
	
}
