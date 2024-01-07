package com.ben.aoc;

import java.util.ArrayList;
import java.util.List;

import com.ben.aoc.dijkstra.Dijkstra;
import com.ben.aoc.dijkstra.Graph;
import com.ben.aoc.dijkstra.Node;

public class Day6 {
	
	public List<Orbit> orbits = new ArrayList<Orbit>();
	Graph graph;
	
	public Day6(String fileName) {
		List<String> lines = Util.readFile(getClass(), fileName);
		
		Orbit com = new Orbit("COM");
		orbits.add(com);
		for(String l : lines) {
			
			String[] split = l.split("\\)");
			Orbit o = new Orbit(split[1]);
			orbits.add(o);
			
		}
		
		for(String l : lines) {
			String[] split = l.split("\\)");
			Orbit orbitee = new Orbit(null);
			Orbit orbiter = new Orbit(null);
			for(Orbit o : orbits) {
				if(o.getName().equals(split[0])) {
					orbitee = o;
				}
				if(o.getName().equals(split[1])) {
					orbiter = o;
				}
			}
			orbiter.setParent(orbitee);
		}
		
		graph = new Graph();
		for(Orbit o : orbits) {
			graph.addNode(o);
		}
	}

	public long puzzle1() {
		long result = 0;
		
		for(Orbit n : orbits) {
			result += getOrbits(n);
		}

		return result;
	}


	public long puzzle2() {
		long result = 0;
		
		Orbit you = new Orbit(null, null);
		
		for(Orbit o : orbits) {
			if(o.getName().equals("YOU")) {
				you = o;
			}
		}
		
		Graph youGraph = Dijkstra.calculateShortestPath(graph, you);
		
		for(Node n : youGraph.getNodes()) {
			if(n.getName().equals("SAN")) {
				return n.getDistance() - 2;
			}
		}

		return result;
	}
	
	private int getOrbits(Orbit n) {
		if(n.getName().equals("COM")) {
			return 0;
		}else {
			Orbit parent = orbits.get(orbits.indexOf(n.parent));
			return 1+getOrbits(parent);
		}
		
	}
	
}
