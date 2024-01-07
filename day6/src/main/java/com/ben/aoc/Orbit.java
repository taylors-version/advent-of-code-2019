package com.ben.aoc;

import java.util.Objects;

import com.ben.aoc.dijkstra.Node;

public class Orbit extends Node{
	protected Orbit parent;
	
	public Orbit(String name) {
		super(name);
	}
	
	public Orbit(String name, Orbit parent) {
		super(name);
		if(parent != null) {
			setParent(parent);
		}
	}
	
	public void setParent(Orbit parent) {
		this.parent = parent;
		this.addDestination(parent, 1);
		parent.addDestination(this, 1);
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null) {
			return false;
		}
		if(this == o) {
			return true;
		}
		if(!(o instanceof Orbit)) {
			return false;
		}
		Orbit n = (Orbit) o;
		return n.name.equals(name);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
	
	@Override
	public String toString() {
		String parentName = "";
		if(parent != null) {
			parentName = parent.name;
		}
		return parentName + ")" + name;
	}
}
