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
import org.javatuples.Quartet;


public class Day18 {
	static char[][] maze;
	List<IntPoint> nodes = new ArrayList<IntPoint>();
	Set<Character> keys = new HashSet<Character>();
	List<IntPoint> ats;
	Map<Character, KeyInfo> keyinfos = new HashMap<Character, KeyInfo>();
	
	Map<Pair<Set<Character>, Character>, Integer> oldcache;
	
	int total = 0;
	
	public Day18(String fileName) {
		List<String> lines = Util.readFile(getClass(), fileName);
		
		maze = new char[lines.size()][];
		ats = new ArrayList<IntPoint>();
		for(int i = 0; i<lines.size(); i++) {
			maze[i] = lines.get(i).toCharArray();
			for(Character c : lines.get(i).toCharArray()) {
				if(Character.isLowerCase(c)) {
					nodes.add(new IntPoint(lines.get(i).indexOf(c), i));
					keyinfos.put(c, new KeyInfo(c));
				}
				if(c == '@') {
					ats.add(new IntPoint(lines.get(i).indexOf('@'), i));
					nodes.add(new IntPoint(lines.get(i).indexOf('@'), i));
					keyinfos.put(c, new KeyInfo(c));
				}
			}
		}
	}

	public long puzzle1() {
		
		for(IntPoint p : nodes) {
			findEdges(p);
		}

		 
		oldcache = new HashMap<Pair<Set<Character>,Character>, Integer>();
		
		int shortestPath = getShortestPath('@', 0);
		return shortestPath;
	}


	public long puzzle2() {
		updateMaze();

		
		
		return 0;
	}
	
	private int getShortestPath(char startChar, int count) {
		Pair<Set<Character>, Character> p = new Pair<Set<Character>, Character>(keys, startChar);
		if(oldcache.containsKey(p)) {
			return oldcache.get(p);
		}
		
		//total++;
		
		KeyInfo keyInfo = keyinfos.get(startChar);
		keys.add(startChar);
		int minPath = Integer.MAX_VALUE;
		
		for(Entry<Character, Pair<Integer, Set<Character>>> e : keyInfo.destinations.entrySet()) {
			if(!keys.contains(e.getKey()) && keys.containsAll(e.getValue().getValue1())) { //Haven't gone to this one yet, and I have all the keys for doors
				int subPath = getShortestPath(e.getKey(), count + 1);
				int path = e.getValue().getValue0() + subPath;
				minPath = Math.min(minPath, path);
			}
		}
		
		keys.remove(startChar);
		
		int result = minPath<Integer.MAX_VALUE ? minPath : 0;
		
		Set<Character> k = new HashSet<Character>();
		k.addAll(keys);
		p = new Pair<Set<Character>, Character>(k, startChar);
		oldcache.put(p, result);
		
		return result;
		

	}
	
	
	
	private void findEdges(IntPoint point) {
		Queue<Quartet<Pair<IntPoint, IntPoint>, Set<Character>, Integer, Set<Character>>> queue = new ArrayDeque<Quartet<Pair<IntPoint,IntPoint>,Set<Character>,Integer,Set<Character>>>();
		Set<Pair<IntPoint, Set<Character>>> visited = new HashSet<Pair<IntPoint,Set<Character>>>();
		
		Quartet<Pair<IntPoint, IntPoint>, Set<Character>, Integer, Set<Character>> start = new Quartet<Pair<IntPoint, IntPoint>, Set<Character>, Integer, Set<Character>>(new Pair<IntPoint, IntPoint>(point, point), new HashSet<Character>(), 0, new HashSet<Character>());
		queue.add(start);
		visited = new HashSet<Pair<IntPoint,Set<Character>>>();
		
		char startChar = mazeChar(point);
		
		Quartet<Pair<IntPoint, IntPoint>, Set<Character>, Integer, Set<Character>> current;
		
		while(!queue.isEmpty()) {
			current = queue.remove();
			IntPoint startNode = current.getValue0().getValue0();
			IntPoint currentNode = current.getValue0().getValue1();
			Set<Character> chars = current.getValue1();
			Set<Character> keys = current.getValue3();
			int distance = current.getValue2();
			
			char currentChar = mazeChar(currentNode);
			
			
			if((Character.isLowerCase(currentChar) || currentChar == '@') && currentChar != startChar){
				if(currentChar != '@') {
					keys.add(currentChar);
				}
				KeyInfo k = keyinfos.get(startChar);
				k.destinations.put(currentChar, new Pair<Integer, Set<Character>>(distance, chars));
			}
				Pair<IntPoint, Set<Character>> cp= new Pair<IntPoint, Set<Character>>(currentNode, chars);
				visited.add(cp);
				for(IntPoint neighbour : nextPoints(currentNode, startNode)) {
					Set<Character> c = new HashSet<Character>();
					c.addAll(chars);
					Set<Character> k = new HashSet<Character>();
					k.addAll(keys);
					IntPoint node = new IntPoint(neighbour.getX(), neighbour.getY());
					Pair<IntPoint, Set<Character>> pair = new Pair<IntPoint, Set<Character>>(node, c);
					char character = mazeChar(node);
					if(!visited.contains(pair)) {
						if(Character.isUpperCase(character)) {
							c.add(Character.toLowerCase(character));
						}
						Quartet<Pair<IntPoint, IntPoint>, Set<Character>, Integer, Set<Character>> trip = new Quartet<Pair<IntPoint, IntPoint>, Set<Character>, Integer, Set<Character>>(new Pair<IntPoint, IntPoint>(currentNode, node), c, distance +1, k);
						queue.add(trip);
						visited.add(pair);
					}
				
				
				}
			
		}
		
	}
	
	private List<IntPoint> nextPoints(IntPoint currentPoint, IntPoint lastPoint){
		List<IntPoint> nextPoints = new ArrayList<IntPoint>();
		for(Point<Integer> p : currentPoint.allNeighbours()) {
			IntPoint point = (IntPoint) p;
			if(!p.equals(lastPoint) && mazeChar(point) != '#') {
				nextPoints.add(point);
			}
		}
		return nextPoints;
	}
	
	private char mazeChar(IntPoint point) {
		return maze[point.getY()][point.getX()];
	}
	
	private void updateMaze() {
		IntPoint originalStart = ats.get(0);
		ats.remove(0);
		int x = originalStart.getX();
		int y = originalStart.getY();
		maze[y][x] = '#';
		maze[y+1][x] = '#';
		maze[y-1][x] = '#';
		maze[y][x+1] = '#';
		maze[y][x-1] = '#';
		maze[y+1][x+1] = '@';
		ats.add(new IntPoint(x+1, y+1));
		maze[y-1][x+1] = '@';
		ats.add(new IntPoint(x+1, y-1));
		maze[y+1][x-1] = '@';
		ats.add(new IntPoint(x-1, y+1));
		maze[y-1][x-1] = '@';
		ats.add(new IntPoint(x-1, y-1));
	}
	
}
