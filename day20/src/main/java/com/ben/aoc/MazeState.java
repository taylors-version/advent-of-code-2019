package com.ben.aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;

import com.ben.aoc.dijkstra.Node;
import com.ben.aoc.dijkstra.State;

public class MazeState extends State {
	
	final String name;
	final int cost;
	final boolean isInner;
	
	public MazeState(String name, int cost,  boolean isInner) {
		this.name = name;
		this.cost = cost;
		this.isInner = isInner;
	}

	@Override
	public int getCost() {
		return cost;
	}

	@Override
	public List<State> next() {
		List<State> next = new ArrayList<State>();
		
		String suffix = isInner ? "i" : "o";
		String searchName = name + suffix;
		
		Warp node = Day20.warps.get(searchName);

		for(Entry<Node, Integer> e : node.getAdjacentNodes().entrySet()) {
			Warp w = (Warp) e.getKey();
			if(!w.getName().equals("AA")) {
				
				MazeState state = new MazeState(w.getName(), e.getValue(), !w.isInner);
				next.add(state);
			}
		}
		
		return next;
	}

	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public int compareTo(State o) {
		Integer thisHash = hashCode();
		Integer oHash = o.hashCode();
		return thisHash.compareTo(oHash);
	}

	@Override
	public boolean isFinished() {
		return name.equals("ZZ");
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null) {
			return false;
		}
		if(this == o) {
			return true;
		}
		if(!(o instanceof MazeState)) {
			return false;
		}
		MazeState m = (MazeState) o;
		if(m.name.equals(this.name) && (m.isInner == this.isInner) && m.getDistance() == this.getDistance()) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name, isInner, getDistance());
	}

}
