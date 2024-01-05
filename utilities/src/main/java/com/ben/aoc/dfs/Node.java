package com.ben.aoc.dfs;

import java.util.HashSet;
import java.util.Set;

public class Node<T> {
	private T value;
	private Set<Node<T>> neighbours;
	
	public Node(T value) {
		this.value = value;
		this.neighbours = new HashSet<Node<T>>();
	}
	
	public void connect(Node<T> node) {
		if (this == node) throw new IllegalArgumentException("Can't connect to iself");
		this.neighbours.add(node);
		node.neighbours.add(this);
	}
	
	public T getValue() {
		return value;
	}
	
	public Set<Node<T>> getNeighbours(){
		return neighbours;
	}
}
