package com.ben.aoc;

/**
 * Point class for long matrices
 * Note in the below matrix the highlighted point([1][0]) is 0,1 (y=1, x=0)
 * [ ][ ]
 * [x][0]
 */
public class LongPoint extends Point<Long> {

	public LongPoint(Long x, Long y) {
		super(x, y);
	}

	@Override
	public Point<Long> above() {
		return new LongPoint(getX(), getY()-1);
	}

	@Override
	public Point<Long> below() {
		return new LongPoint(getX(), getY()+1);
	}

	@Override
	public Point<Long> right() {
		return new LongPoint(getX()+1, getY());
	}

	@Override
	public Point<Long> left() {
		return new LongPoint(getX()-1, getY());
	}

}
