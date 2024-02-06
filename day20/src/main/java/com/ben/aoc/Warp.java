package com.ben.aoc;

import com.ben.aoc.dijkstra.Node;

public class Warp extends Node {
	
	final boolean isInner;
	
	public Warp(String name, boolean isInner) {
		super(name);
		this.isInner = isInner;
	}
	
	public boolean isInner() {
		return isInner;
	}

}
