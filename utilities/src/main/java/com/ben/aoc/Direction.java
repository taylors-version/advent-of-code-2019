package com.ben.aoc;

import java.util.Objects;

public class Direction {
	
	enum Dir{
		UP,
		RIGHT,
		DOWN,
		LEFT
	}
	
	private Dir direction;
	
	public Direction(Dir direction) {
		this.direction = direction;
	}
	
	public Direction(char d) {
		switch (d) {
		case 'u':
		case 'U':
			direction = Dir.UP;
			break;
		case 'r':
		case 'R':
			direction = Dir.RIGHT;
			break;
		case 'd':
		case 'D':
			direction = Dir.DOWN;
			break;
		case 'l':
		case 'L':
			direction = Dir.LEFT;
			break;
		}
	}
	
	public Dir getDirection() {
		return direction;
	}
	
	public Direction rotateClockwise() {
		switch (direction) {
		case UP:
			return new Direction(Dir.RIGHT);
		case RIGHT:
			return new Direction(Dir.DOWN);
		case DOWN:
			return new Direction(Dir.LEFT);
		case LEFT:
			return new Direction(Dir.UP);
		}
		return null;
	}
	
	public Direction rotateAntiClockwise() {
		switch (direction) {
		case UP:
			return new Direction(Dir.LEFT);
		case RIGHT:
			return new Direction(Dir.UP);
		case DOWN:
			return new Direction(Dir.RIGHT);
		case LEFT:
			return new Direction(Dir.DOWN);
		}
		return null;
	}
	
	@Override
	public String toString() {
		return direction.name();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null) {
			return false;
		}
		if(this == o) {
			return true;
		}
		if(!(o instanceof Direction)) {
			return false;
		}
		Direction d = (Direction) o;
		
		return (this.direction.equals(d.direction));
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(direction);
	}

}
