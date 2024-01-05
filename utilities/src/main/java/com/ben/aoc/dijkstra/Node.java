package com.ben.aoc.dijkstra;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Node {
	protected String name;
	protected List<Node> shortestPath = new LinkedList<>();
	protected Integer distance = Integer.MAX_VALUE;
	
	Map<Node, Integer> adjacentNodes = new HashMap<>();
	
	public void addDestination(Node destination, int distance) {
		adjacentNodes.put(destination, distance);
	}
	
	public Node(String name) {
		this.name = name;
	}
	
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public int getDistance(){
		return distance;
	}
	
	public Map<Node, Integer> getAdjacentNodes(){
		return adjacentNodes;
	}
	
	public List<Node> getShortestPath(){
		return shortestPath;
	}
	
	public void setShortestPath(List<Node> shortestPath) {
		this.shortestPath = shortestPath;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append(": ");
		for (Node n : shortestPath) {
			sb.append(n.getName());
			sb.append(", ");
		}
		sb.delete(sb.length()-2, sb.length());
		sb.append(" (" + distance + ")");
		return sb.toString();
	}
}
