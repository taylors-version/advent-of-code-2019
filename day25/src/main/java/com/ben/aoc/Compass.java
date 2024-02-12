package com.ben.aoc;

public class Compass {

	public static String getOpposite(String direction) {
		switch(direction) {
		case "north":
			return "south";
		case "south":
			return "north";
		case "east":
			return "west";
		case "west":
			return "east";
		}
		return null;
	}
}
