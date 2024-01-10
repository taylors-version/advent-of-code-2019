package com.ben.aoc;

import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

public class AsteroidAngled {
	double angle;
	SortedMap<Double, IntPoint> asteroid; //K = distance from origin, V = actual point
	
	public AsteroidAngled(double angle) {
		if(angle < 0) {
			angle += (Math.PI*2);
		}
		this.angle = angle;
		asteroid = new TreeMap<Double, IntPoint>();
	}
	
	public void addAsteroid(double distance, IntPoint point) {
		asteroid.put(distance, point);
	}
	
	public IntPoint getNextPoint() {
		IntPoint point = asteroid.get(asteroid.firstKey());
		asteroid.remove(asteroid.firstKey());
		return point;
	}
	
	public boolean isMapEmpty() {
		return asteroid.isEmpty();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null) {
			return false;
		}
		if(this == o) {
			return true;
		}
		if(!(o instanceof AsteroidAngled)) {
			return false;
		}
		AsteroidAngled a = (AsteroidAngled) o;
		return (this.angle == a.angle);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(angle);
	}
	
	@Override
	public String toString() {
		return "" + angle;
	}
}
