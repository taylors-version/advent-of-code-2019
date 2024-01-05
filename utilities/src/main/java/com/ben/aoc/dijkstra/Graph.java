package com.ben.aoc.dijkstra;

import java.util.HashSet;
import java.util.Set;

public class Graph {
	private Set<Node> nodes = new HashSet<>();
	
	public void addNode(Node nodeA) {
		nodes.add(nodeA);
	}
	
	public Set<Node> getNodes(){
		return nodes;
	}
}
