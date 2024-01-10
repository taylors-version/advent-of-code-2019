package com.ben.aoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.numbers.complex.Complex;


public class Day10 {
	public Set<IntPoint> asteroids = new HashSet<IntPoint>();
	private IntPoint station;
	private List<AsteroidAngled> asteroidsFromStation;
	
	
	public Day10(String fileName) {
		List<String> lines = Util.readFile(getClass(), fileName);
		for(int i = 0; i<lines.size(); i++) {
			String line = lines.get(i);
			for(int j = 0; j<line.length(); j++) {
				if(line.charAt(j) == '#') {
					IntPoint point = new IntPoint(j, i);
					asteroids.add(point);
				}
			}
		}
		
	}

	public long puzzle1() {
		long result = 0;
		for(IntPoint point : asteroids) {
			List<AsteroidAngled> others = new ArrayList<AsteroidAngled>();
			for(IntPoint seen : asteroids) {
				if(!seen.equals(point)) {
					Complex complex = toComplex(point, seen);
					AsteroidAngled asteroid = new AsteroidAngled(complex.arg());
					if(others.contains(asteroid)) {
						asteroid = others.get(others.indexOf(asteroid));
						asteroid.addAsteroid(complex.abs(), seen);
					}else {
						asteroid.addAsteroid(complex.abs(), seen);
						others.add(asteroid);
					}
				}
			}
			if(others.size() > result) {
				result = others.size();
				station = point;
				asteroidsFromStation = others;
			}
		}
		return result;
	}


	public long puzzle2() {		
		Collections.sort(asteroidsFromStation, new Comparator<AsteroidAngled>() {
		     public int compare(AsteroidAngled a, AsteroidAngled b) {
		    	 return Double.compare(a.angle, b.angle);
		     }
		});
		int i = 0;
		int count = 0;
		while(asteroidsFromStation.size()>0) {
			count++;
			AsteroidAngled asteroid = asteroidsFromStation.get(i%asteroidsFromStation.size());
			IntPoint point = asteroid.getNextPoint();
			if(asteroid.isMapEmpty()) {
				asteroidsFromStation.remove(i%asteroidsFromStation.size());
			}else {
				i++;
			}
			
			if(count == 200) {
				return 100*point.getX() + point.getY();
			}
		}
		return 0;
	}
	
	private Complex toComplex(IntPoint origin, IntPoint seen){
		long xDiff = seen.getX() - origin.getX();
		long yDiff = seen.getY() - origin.getY();
		Complex complex = Complex.ofCartesian(-yDiff, xDiff);

		return complex;
	}
	
}
