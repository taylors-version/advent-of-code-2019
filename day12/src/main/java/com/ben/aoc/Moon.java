package com.ben.aoc;

import java.util.Objects;

public class Moon {
	
	int xPos;
	int yPos;
	int zPos;
	int xVel;
	int yVel;
	int zVel;
	
	public Moon(int xPos, int yPos, int zPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.zPos = zPos;
	}
	
	public void move() {
		xPos+=xVel;
		yPos+=yVel;
		zPos+=zVel;
	}
	
	public long getPotential() {
		return Math.abs(xPos)+Math.abs(yPos)+Math.abs(zPos);
	}
	
	public long getKinetic() {
		return Math.abs(xVel)+Math.abs(yVel)+Math.abs(zVel);
	}
	
	public long getTotalEnergy() {
		return getPotential() * getKinetic();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if(this == o) {
			return true;
		}
		if(!(o instanceof Moon)) {
			return false;
		}
		Moon m = (Moon) o;
		
		if(xPos == m.xPos && xVel == m.xVel && yPos == m.yPos && yVel == m.yVel && zPos == m.zPos && zVel == m.zVel) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(xPos, xVel, yPos, yVel, zPos, zVel);
	}

}
