package com.ben.aoc;

import java.util.Arrays;
import java.util.List;

public class Day8 {
	private static final int pixels_wide = 25;
	private static final int pixels_tall = 6;
	
	private String input;
	String[][] finalImage;
	int[][] digitCount;
	int numberOfLayers;
	
	public Day8(String fileName) {
		List<String> lines = Util.readFile(getClass(), fileName);
		input = lines.get(0);
		
		int numberOfPixels = input.length();
		int pixelsPerLayer = pixels_wide*pixels_tall;
		numberOfLayers = numberOfPixels/pixelsPerLayer;
		
		finalImage = new String[pixels_tall][pixels_wide];
		for(String[] row : finalImage) {
			Arrays.fill(row, "2");
		}
		
		digitCount = new int[numberOfLayers][3]; //0's 1's 2's per layer
		int layerId = -1;
		
		for(int i = 0; i<numberOfPixels; i++) {
			if(i % pixelsPerLayer == 0) {
				layerId++;
			}
			char c = input.charAt(i);
			switch (c) {
			case '0':
				digitCount[layerId][0]++;
				break;
			case '1':
				digitCount[layerId][1]++;
				break;
			case '2':
				digitCount[layerId][2]++;
				break;
			}
			
			int pixelNumber = i%pixelsPerLayer;
			int columnIndex = pixelNumber%pixels_wide;
			int rowIndex = pixelNumber/pixels_wide;
			if(finalImage[rowIndex][columnIndex].equals("2")) {
				
				finalImage[rowIndex][columnIndex] = String.valueOf(c);
			}
		}
	}

	public long puzzle1() {
		int minLayer = -1;
		int zeroCount = Integer.MAX_VALUE;
		for(int i = 0; i<numberOfLayers; i++) {
			if(digitCount[i][0] < zeroCount) {
				zeroCount = digitCount[i][0];
				minLayer = i;
			}
		}
		
		return digitCount[minLayer][1] * digitCount[minLayer][2];
	}
	
	public String puzzle2() {
		String result = "\n";
		for(int i = 0; i<finalImage.length; i++) {
			for(int j = 0; j<finalImage[i].length; j++) {
				String value = finalImage[i][j];
				String emoji = value.equals("0") ? "⬛️" : "⬜";
				result = result + emoji;
			}
			result = result + "\n";
		}
		
		return result;
	}	
}
