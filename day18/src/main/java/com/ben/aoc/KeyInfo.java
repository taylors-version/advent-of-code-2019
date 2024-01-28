package com.ben.aoc;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.javatuples.Pair;

public class KeyInfo {
	char key;
	public Map<Character, Pair<Integer, Set<Character>>> destinations;
	
	public KeyInfo(char key) {
		this.key = key;
		destinations = new HashMap<Character, Pair<Integer,Set<Character>>>();
	}
}
