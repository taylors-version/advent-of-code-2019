package com.ben.aoc.dijkstra;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class State implements Comparable<State> {

	public abstract int getCost();
	public abstract List<State> next();
	public abstract String toString();

	private int distance = Integer.MAX_VALUE;
	private List<State> shortestPath = new LinkedList<State>();

	public abstract boolean isFinished();

	public String getHash() {
		return Integer.toString(Objects.hash(toString()));
	}

	@Override
	public int compareTo(State o) {
		return getHash().compareTo(o.getHash());
	}
	
	public int getDistance() {
		return distance;
	}
	
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public List<State> getShortestPath(){
		return shortestPath;
	}
	
	public void setShortestPath(List<State> shortestPath) {
		this.shortestPath = shortestPath;
	}

}
