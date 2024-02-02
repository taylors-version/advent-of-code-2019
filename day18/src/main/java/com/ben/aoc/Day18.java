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
import org.javatuples.Triplet;


public class Day18 {
	static char[][] maze;
	List<IntPoint> nodes = new ArrayList<IntPoint>();
	Set<Character> keys = new HashSet<Character>();
	List<Character> ats;
	Map<Character, KeyInfo> keyinfos = new HashMap<Character, KeyInfo>();
	IntPoint startPos;
	
	Map<Pair<Set<Character>, List<Character>>, Integer> cache;
	
	List<Character> optimalPath = new ArrayList<Character>();
	
	int total = 0;
	
	public Day18(String fileName) {
		List<String> lines = Util.readFile(getClass(), fileName);
		
		maze = new char[lines.size()][];
		ats = new ArrayList<Character>();
		for(int i = 0; i<lines.size(); i++) {
			maze[i] = lines.get(i).toCharArray();
			for(Character c : lines.get(i).toCharArray()) {
				if(Character.isLowerCase(c)) {
					nodes.add(new IntPoint(lines.get(i).indexOf(c), i));
					keyinfos.put(c, new KeyInfo(c));
				}
				if(c == '@') {
					ats.add('@');
					startPos = new IntPoint(lines.get(i).indexOf('@'), i);
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

		keys = new HashSet<Character>();
		keys.add('@');
		cache = new HashMap<Pair<Set<Character>,List<Character>>, Integer>();
		
		int shortestPath = getShortestPath(ats);
		return shortestPath;
	}


	public long puzzle2() {
		updateMaze();

		for(IntPoint p : nodes) {
			findEdges(p);
		}
		
		keys = new HashSet<Character>();
		keys.add('0');
		keys.add('1');
		keys.add('2');
		keys.add('3');
		cache = new HashMap<Pair<Set<Character>,List<Character>>, Integer>();
		optimalPath = new ArrayList<Character>();
		total = 0;
		int shortestPath = getShortestPath(ats); 
		return shortestPath;
		
	}
	
	private int getShortestPath(List<Character> startChars) {
		Pair<Set<Character>, List<Character>> p = new Pair<Set<Character>, List<Character>>(keys, startChars);
		if(cache.containsKey(p)) {
			return cache.get(p);
		}
		total++;
		int minPath = Integer.MAX_VALUE;
		
		List<Triplet<List<Character>, Integer, Character>> subTasks = new ArrayList<Triplet<List<Character>,Integer, Character>>();
		
		
		for(int i = 0; i<startChars.size(); i++) {
			char startChar = startChars.get(i);

			KeyInfo keyInfo = keyinfos.get(startChar);
			
			for(Entry<Character, Pair<Integer, Set<Character>>> e : keyInfo.destinations.entrySet()) {
				if(!keys.contains(e.getKey()) && keys.containsAll(e.getValue().getValue1())) {
					List<Character> chars = new ArrayList<Character>();
					chars.addAll(startChars);
					chars.set(i, e.getKey());
					subTasks.add(new Triplet<List<Character>, Integer, Character>(chars, e.getValue().getValue0(), e.getKey()));
				}
			}
			
		}
		
		for(Triplet<List<Character>, Integer, Character> entry : subTasks) {
			keys.add(entry.getValue2());
			int subPath = getShortestPath(entry.getValue0());
			int path = entry.getValue1() + subPath;
			minPath = Math.min(minPath, path);
			keys.remove(entry.getValue2());
		}
		
		int result = minPath<Integer.MAX_VALUE ? minPath : 0;
		
		Set<Character> k = new HashSet<Character>();
		k.addAll(keys);
		List<Character> c = new ArrayList<Character>();
		c.addAll(startChars);
		p = new Pair<Set<Character>, List<Character>>(k, c);
		cache.put(p, result);
		
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
			
			
			if((Character.isLowerCase(currentChar) || currentChar == '@' || Character.isDigit(currentChar)) && currentChar != startChar){
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
		IntPoint originalStart = startPos;
		nodes.remove(startPos);
		ats.remove(0);
		int x = originalStart.getX();
		int y = originalStart.getY();
		maze[y][x] = '#';
		maze[y+1][x] = '#';
		maze[y-1][x] = '#';
		maze[y][x+1] = '#';
		maze[y][x-1] = '#';
		maze[y-1][x-1] = '0';
		nodes.add(new IntPoint(x-1, y-1));
		ats.add('0');
		maze[y-1][x+1] = '1';
		nodes.add(new IntPoint(x+1, y-1));
		ats.add('1');
		maze[y+1][x-1] = '2';
		nodes.add(new IntPoint(x-1, y+1));
		ats.add('2');
		maze[y+1][x+1] = '3';
		nodes.add(new IntPoint(x+1, y+1));
		ats.add('3');
		
		keyinfos.remove('@');
		keyinfos.put('0', new KeyInfo('0'));
		keyinfos.put('1', new KeyInfo('1'));
		keyinfos.put('2', new KeyInfo('2'));
		keyinfos.put('3', new KeyInfo('3'));
		
		for(Entry<Character, KeyInfo> e : keyinfos.entrySet()) {
			e.getValue().destinations = new HashMap<Character, Pair<Integer,Set<Character>>>();
		}
	}
	
}
