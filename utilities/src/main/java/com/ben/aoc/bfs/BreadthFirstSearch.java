package com.ben.aoc.bfs;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

public class BreadthFirstSearch {
	public static <T> Optional<Node<T>> search(T value, Node<T> start){
		Queue<Node<T>> queue = new ArrayDeque<Node<T>>();
		queue.add(start);
		Set<Node<T>> alreadyVisited = new HashSet<Node<T>>();
		
		Node<T> currentNode;
		
		while(!queue.isEmpty()) {
			currentNode = queue.remove();
			
			if(currentNode.getValue().equals(value)) {
				return Optional.of(currentNode);
			} else {
				alreadyVisited.add(currentNode);
				queue.addAll(currentNode.getNeighbours());
				queue.removeAll(alreadyVisited);
			}
		}
		
		return Optional.empty();
	}
}
