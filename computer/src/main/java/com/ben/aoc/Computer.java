package com.ben.aoc;

public class Computer {
	
	private int[] ints;
	
	public Computer(int[] ints) {
		this.ints = ints;
	}
	
	public int[] intCode() {
		boolean found99 = false;
		int intsructionPointer = 0;
		
		while(!found99) {
			int opCode = ints[intsructionPointer];
			switch (opCode) {
			case 1:
				add(intsructionPointer);
				break;
			case 2:
				multiply(intsructionPointer);
				break;
			case 99:
				found99 = true;
				break;
			}
			intsructionPointer+=4;
		}
		
		return ints;
	}
	
	private void add(int position) {
		int positionA = ints[position+1];
		int positionB = ints[position+2];
		int storeLocation = ints[position+3];
		ints[storeLocation] = ints[positionA] + ints[positionB];
	}
	
	private void multiply(int position) {
		int positionA = ints[position+1];
		int positionB = ints[position+2];
		int storeLocation = ints[position+3];
		ints[storeLocation] = ints[positionA] * ints[positionB];
	}
	
}
