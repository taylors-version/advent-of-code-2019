package com.ben.aoc;

import java.util.ArrayList;
import java.util.List;

/**
 * Point class for integers in cartesian coordinates
 */
public class CartesianIntPoint extends Point<Integer> {

	public CartesianIntPoint(Integer x, Integer y) {
		super(x, y);
	}

	@Override
	public Point<Integer> above() {
		return new CartesianIntPoint(getX(), getY()+1);
	}

	@Override
	public Point<Integer> below() {
		return new CartesianIntPoint(getX(), getY()-1);
	}

	@Override
	public Point<Integer> right() {
		return new CartesianIntPoint(getX()+1, getY());
	}

	@Override
	public Point<Integer> left() {
		return new CartesianIntPoint(getX()-1, getY());
	}
	
	public List<Point<Integer>> allNeighbours(){
		List<Point<Integer>> neighbours = new ArrayList<Point<Integer>>();
		
		neighbours.add(above());
		neighbours.add(below());
		neighbours.add(right());
		neighbours.add(left());
		
		return neighbours;
	}
	
	public int manhattanDistance(CartesianIntPoint point) {
		return Math.abs(this.getX() - point.getX()) + Math.abs(this.getY() - point.getY());
	}

}
