package com.ben.aoc;

import java.util.Objects;
import java.util.Set;

public class Edge {
	
	IntPoint start;
	IntPoint destination;
	int distance;
	char destinationKey;
	Set<Character> blockingGates;
	Set<Character> keysOnRoute;
	public Edge(IntPoint start, IntPoint destination, int distance, char destinationKey, Set<Character> blockingGates, Set<Character> keysOnRoute) {
		this.start = start;
		this.destination = destination;
		this.distance = distance;
		this.destinationKey = destinationKey;
		this.blockingGates = blockingGates;
		this.keysOnRoute = keysOnRoute;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(distance, destinationKey, blockingGates);
	}
	@Override
	public boolean equals(Object o) {
		if(o == null) {
			return false;
		}
		if(this == o) {
			return true;
		}
		if(!(o instanceof Edge)) {
			return false;
		}
		Edge p = (Edge) o;
		if(p.distance == this.distance && p.destinationKey == this.destinationKey && p.blockingGates.equals(this.blockingGates)) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(Day18.maze[start.getY()][start.getX()]).append(" ");
		sb.append(start).append(" " + distance + " ").append(destination).append(": ").append(destinationKey).append(" ");
		for(Character c : keysOnRoute) {
			sb.append(c);
		}
		sb.append(" ");
		for(Character c : blockingGates) {
			sb.append(Character.toUpperCase(c));
		}
		
		return sb.toString();
	}
	
	public String ben() {
		StringBuilder sb = new StringBuilder();
		sb.append(Day18.maze[start.getY()][start.getX()]).append(" ").append(destinationKey).append(" ").append(distance).append(" : ");
		
		for(Character c : blockingGates) {
			sb.append(Character.toUpperCase(c));
		}
		
		return sb.toString();
	}
	
}
