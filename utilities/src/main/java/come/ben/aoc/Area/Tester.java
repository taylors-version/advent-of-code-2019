package come.ben.aoc.Area;

import com.ben.aoc.IntPoint;
import com.ben.aoc.Util;

public class Tester {
	
	public static void main(String[] args) {
		
		char screen[][] = {{'1', '1', '1', '1', '1', '1', '1', '1'},
						{'1', '1', '1', '1', '1', '1', '0', '0'},
						{'1', '0', '0', '1', '1', '0', '1', '1'},
						{'1', '2', '2', '2', '2', '0', '1', '0'},
						{'1', '1', '1', '2', '2', '0', '1', '0'},
						{'1', '1', '1', '2', '2', '2', '2', '0'},
						{'1', '1', '1', '1', '1', '2', '1', '1'},
						{'1', '1', '1', '1', '1', '2', '2', '1'}};
		
		IntPoint point = new IntPoint(4, 4);
		
		char[][] filled = FloodFill.fill(screen, point, '3');
		
		Util.printMatrix(filled);
		}
		
	}

