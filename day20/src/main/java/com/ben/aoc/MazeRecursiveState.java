package com.ben.aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;

import com.ben.aoc.dijkstra.Node;
import com.ben.aoc.dijkstra.State;

public class MazeRecursiveState extends State {
	
	final String name;
	final int cost;
	final int layer;
	final boolean isInner;
	
	public MazeRecursiveState(String name, int cost, int layer, boolean isInner) {
		this.name = name;
		this.cost = cost;
		this.layer = layer;
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
			String name = w.getName();
			boolean layer0Safe = w.isInner || name.equals("ZZ");
			boolean layer1Safe = !name.equals("AA") && !name.equals("ZZ");
			if((layer == 0 && layer0Safe) || (layer != 0 && layer1Safe)) {
				int newLayer = layer;
				if(w.isInner) {
					newLayer++;
				}else {
					newLayer--;
				}
				
				MazeRecursiveState state = new MazeRecursiveState(w.getName(), e.getValue(), newLayer, !w.isInner);
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
		if(!(o instanceof MazeRecursiveState)) {
			return false;
		}
		MazeRecursiveState m = (MazeRecursiveState) o;
		if(m.name.equals(this.name) && (m.isInner == this.isInner) && this.layer == m.layer) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name, isInner, layer);
	}

}
