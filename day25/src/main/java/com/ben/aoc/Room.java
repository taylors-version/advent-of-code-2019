package com.ben.aoc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Room {
	
	private String name;
	private String description;
	private Map<String, Room> neighbours; //e.g. east, Bathroom
	private Set<String> availableDirections;
	
	public Room(String name, String description) {
		this.name = name;
		this.description = description;
		neighbours = new HashMap<String, Room>();
		availableDirections = new HashSet<String>();
	}
	
	public void addNeighbour(Room room, String direction) {
		neighbours.put(direction, room);
	}
	
	public void addDirection(String direction) {
		availableDirections.add(direction);
	}
	
	public Map<String, Room> getNeighbours(){
		return neighbours;
	}
	
	public Set<String> getAvailableDirections(){
		return availableDirections;
	}
	
	public String print() {
		return name + "\n" + description;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null) {
			return false;
		}
		if(this == o) {
			return true;
		}
		if(!(o instanceof Room)) {
			return false;
		}
		Room r = (Room) o;
		if(this.name.equals(r.name)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
