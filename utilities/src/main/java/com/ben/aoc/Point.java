package com.ben.aoc;

import java.util.Objects;

import org.javatuples.Pair;

public abstract class Point<T extends Number> {
	
	private Pair<T, T> xy;
	
	public abstract Point<T> above();
	public abstract Point<T> below();
	public abstract Point<T> right();
	public abstract Point<T> left();
	
	public Point(Pair<T, T> xy) {
		this.xy = xy;
	}
	
	public Point(T x, T y){
		Pair<T, T> point = new Pair<T, T>(x, y);
		this.xy = point;
	}
	
	public T getX() {
		return xy.getValue0();
	}
	
	public T getY() {
		return xy.getValue1();
	}
	
	public void setX(T x) {
		xy.setAt0(x);
	}
	
	public void setY(T y) {
		xy.setAt1(y);
	}
	
	public Point<T> getByDirection(Direction direction){
		switch(direction.getDirection()) {
		case UP:
			return above();
		case RIGHT:
			return right();
		case DOWN:
			return below();
		case LEFT:
			return left();
		}
		return null;
	}
	
	public String toString() {
		return "(" + xy.getValue0() + ", " + xy.getValue1() + ")";
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null) {
			return false;
		}
		if(this == o) {
			return true;
		}
		if(!(o instanceof Point)) {
			return false;
		}
		Point p = (Point) o;
		if(this.getX().equals(p.getX()) && this.getY().equals(p.getY())) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.getX(), this.getY());
	}

}
