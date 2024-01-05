package com.ben.aoc.dfs;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;

public class DepthFirstSearch {
	public static <T> Optional<Node<T>> search(T value, Node<T> start){
		Set<Node<T>> visited = new HashSet<Node<T>>();
		Stack<Node<T>> stack = new Stack<Node<T>>();
		
		stack.push(start);
		while(!stack.isEmpty()) {
			Node<T> current = stack.pop();
			if(!visited.contains(current)) {
				visited.add(current);
				if(current.getValue().equals(value)) {
					return Optional.of(current);
				} else {
					for(Node<T> dest : start.getNeighbours()) {
						if(!visited.contains(dest)) {
							stack.push(dest);
						}
					}
				}
			}
		}
		
		return Optional.empty();
	}
	 
}
