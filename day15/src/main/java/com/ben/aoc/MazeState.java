package com.ben.aoc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ben.aoc.dijkstra.State;

public class MazeState extends State {
	final CartesianIntPoint location;
	static Set<CartesianIntPoint> visited = new HashSet<CartesianIntPoint>();

	public MazeState(CartesianIntPoint location) {
		this.location = location;
		visited.add(location);
	}
	
	@Override
	public int getCost() {
		return 1;
	}

	@Override
	public List<State> next() {
		List<State> neighbours = new ArrayList<State>();
		for(Point<Integer> point : location.allNeighbours()) {
			CartesianIntPoint p = (CartesianIntPoint)point;
			if(Day15.maze.containsKey(p) && !visited.contains(p)) {
				if(Day15.maze.get(p) != 1) {
					neighbours.add(new MazeState(p));
				}
			}
		}
		return neighbours;
	}

	@Override
	public String toString() {
		return location.toString();
	}

	@Override
	public boolean isFinished() {
		if (Day15.maze.get(location) == 3) {
			return true;
		}
		return false;
	}
	
	public static void reset() {
		visited = new HashSet<CartesianIntPoint>();
	}

}
