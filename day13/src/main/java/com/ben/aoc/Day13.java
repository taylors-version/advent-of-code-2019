package com.ben.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day13 {
	
	String[] instructions;
	Map<IntPoint, Integer> game;
	
	public Day13(String fileName) {
		List<String> lines = Util.readFile(getClass(), fileName);
		instructions = lines.get(0).split(",");
		
	}

	public long puzzle1() {
		String[] ints = Arrays.copyOf(instructions, instructions.length);
		game = new HashMap<IntPoint, Integer>();
		
		Computer robot = new Computer(ints);
		robot.intCode();
		List<Long> output = robot.getOutputs();
		for(int i = 0; i<output.size()-2; i+=3) {
			long x = output.get(i);
			long y = output.get(i+1);
			long tile = output.get(i+2);
			IntPoint point = new IntPoint((int)x, (int)y);
			game.put(point, (int)tile);
		}
		
		long result = 0;
		for (Integer tile : game.values()) {
			if(tile.equals(2)) {
				result ++;
			}
		}
		
		return result;
	}
	
	public long puzzle2() {
		String[] ints = Arrays.copyOf(instructions, instructions.length);
		ints[0] = "2";
		game = new HashMap<IntPoint, Integer>();
		List<Long> inputs = new ArrayList<Long>();
		
		Computer robot = new Computer(ints);
		boolean finished = false;
		long blockCount = Long.MAX_VALUE;
		long result = 0;
		
		while(!finished || blockCount != 0) {
			finished = robot.intCode(inputs);
			List<Long> output = robot.getOutputs();
			IntPoint paddle = new IntPoint(0, 0);
			IntPoint ball = new IntPoint(0, 0);
			for(int i = 0; i<output.size()-2; i+=3) {
				long x = output.get(i);
				long y = output.get(i+1);
				long tile = output.get(i+2);
				IntPoint point = new IntPoint((int)x, (int)y);
				game.put(point, (int)tile);
				if(tile == 3 && x != -1) {
					paddle = point;
				}else if(tile == 4 && x != -1) {
					ball = point;
				}else if(x == -1) {
					result = tile;
				}
			}
			long paddleMove = paddle.getX() > ball.getX() ? -1 : paddle.getX() < ball.getX() ? 1 : 0;
			inputs.add(paddleMove);
			
			long blocks = 0;
			for (Integer tile : game.values()) {
				if(tile.equals(2)) {
					blocks ++;
				}
			}
			blockCount = blocks;
		}
		return result;
	}
}