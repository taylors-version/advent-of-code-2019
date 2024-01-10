package com.ben.aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ben.aoc.maths.Maths;


public class Day12 {
	private static final String REG_EX_X = "x=(-?)\\d+";
	private static final String REG_EX_Y = "y=(-?)\\d+";
	private static final String REG_EX_Z = "z=(-?)\\d+";
	
	List<Moon> moons1 = new ArrayList<Moon>();
	List<Moon> moons2 = new ArrayList<Moon>();
	List<Moon> originalMoons;
	
	public Day12(String fileName) {
		List<String> lines = Util.readFile(getClass(), fileName);
		moons1 = new ArrayList<Moon>();
		originalMoons = new ArrayList<Moon>();
		for(int i = 0; i<lines.size(); i++) {
			String line = lines.get(i);
			
			Matcher xMatcher = Pattern.compile(REG_EX_X).matcher(line);
			Matcher yMatcher = Pattern.compile(REG_EX_Y).matcher(line);
			Matcher zMatcher = Pattern.compile(REG_EX_Z).matcher(line);
			
			xMatcher.find();
			yMatcher.find();
			zMatcher.find();
			
			int x = Integer.parseInt(xMatcher.group().substring(2));
			int y = Integer.parseInt(yMatcher.group().substring(2));
			int z = Integer.parseInt(zMatcher.group().substring(2));
			
			moons1.add(new Moon(x, y, z));
			moons2.add(new Moon(x, y, z));
			originalMoons.add(new Moon(x, y, z));
			
		}
		
	}

	public long puzzle1() {
		long result = 0;
		for(int i = 0; i<1000; i++) {
			for(Moon moon : moons1) {
				for(Moon m : moons1) {
					moon.xVel += moon.xPos > m.xPos ? -1 : moon.xPos < m.xPos ? 1 : 0;
					moon.yVel += moon.yPos > m.yPos ? -1 : moon.yPos < m.yPos ? 1 : 0;
					moon.zVel += moon.zPos > m.zPos ? -1 : moon.zPos < m.zPos ? 1 : 0;
				}
			}
			for(Moon moon : moons1) {
				moon.move();
			}
		}
		for(Moon moon : moons1) {
			result += moon.getTotalEnergy();
		}
		
		return result;
	}


	public long puzzle2() {
		long loopx = 0L;
		long loopy = 0L;
		long loopz = 0L;
		
		int count = 0;
		
		while (loopx == 0 || loopy == 0 || loopz == 0) {
			for(Moon moon : moons2) {
				for(Moon m : moons2) {
					moon.xVel += moon.xPos > m.xPos ? -1 : moon.xPos < m.xPos ? 1 : 0;
					moon.yVel += moon.yPos > m.yPos ? -1 : moon.yPos < m.yPos ? 1 : 0;
					moon.zVel += moon.zPos > m.zPos ? -1 : moon.zPos < m.zPos ? 1 : 0;
				}
			}
			for(Moon moon : moons2) {
				moon.move();
			}
			count++;
			boolean xLoop = true;
			boolean yLoop = true;
			boolean zLoop = true;
			for(int i = 0; i<moons2.size(); i++) {
				Moon m = moons2.get(i);
				Moon o = originalMoons.get(i);
				if(m.xPos != o.xPos || m.xVel != o.xVel) {
					xLoop = false;
				}
				if(m.yPos != o.yPos || m.yVel != o.yVel) {
					yLoop = false;
				}
				if(m.zPos != o.zPos || m.zVel != o.zVel) {
					zLoop = false;
				}
			}
			if(xLoop && loopx == 0) {
				loopx = count;
			}
			if(yLoop && loopy == 0) {
				loopy = count;
			}
			if(zLoop && loopz == 0) {
				loopz = count;
			}
		}
		
		Long lcmXY = Maths.lcm(loopx, loopy);
		return Maths.lcm(lcmXY, loopz);
	}
	
}
