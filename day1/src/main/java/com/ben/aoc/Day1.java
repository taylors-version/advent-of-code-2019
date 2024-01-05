package com.ben.aoc;

import java.util.List;

public class Day1 {
	
	List<String> lines;
	
	public Day1(String fileName) {
		lines = Util.readFile(getClass(), fileName);
	}

	public long puzzle1() {
		long result = 0;
		
		for(String l : lines) {
			result += moduleFuel(Long.parseLong(l));
		}
		
		return result;
	}
	
	public long puzzle2() {
		long result = 0;
		
		for(String l : lines) {
			result += completeFuel(Long.parseLong(l));
		}
		
		return result;
	}
	
	public long moduleFuel(long mass) {
		long divided = mass/3;
		return divided - 2;
	}
	
	public long completeFuel(long mass) {
		long fuel = 0;
		
		while(mass>0) {
			mass = moduleFuel(mass);
			if(mass >0) {
				fuel+=mass;
			}
		}
		
		return fuel;
	}
	
}
