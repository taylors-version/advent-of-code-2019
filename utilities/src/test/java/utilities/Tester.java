package utilities;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.ben.aoc.IntPoint;
import com.ben.aoc.Util;
import com.ben.aoc.maths.Maths;

import come.ben.aoc.Area.FloodFill;

public class Tester 
{
	
	@Test
    public void testMatrixTranspose()
    {
		char[][] chars = {{'a', 'b', 'c'}, {'d', 'e', 'f'}};
		char[][] transposedChars = {{'a', 'd'}, {'b', 'e'}, {'c', 'f'}};
				
    	assertTrue(Arrays.deepEquals(transposedChars, Util.transposeMatrix(chars)));
    }
	
	@Test
    public void testLargeMatrixTranspose()
    {
		int x = 10000;
		int y = 10000;
		
		long[][] longMatrix = new long[x][y];
		long[][] transposed = new long[y][x];
		
		for (int i =0; i<x; i++) {
			for (int j = 0; j<y; j++) {
				longMatrix[i][j] = (2*i)+j;
				transposed[j][i] = (2*i)+j;
			}
		}
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        //System.out.println("Before transpose: " + timestamp);
		long[][] testedMatrix = Util.transposeMatrix(longMatrix);
		timestamp = new Timestamp(System.currentTimeMillis());
        //System.out.println("After transpose: " + timestamp);
				
    	assertTrue(Arrays.deepEquals(transposed, testedMatrix));
    }

	@Test
	public void testPointMoving() {
		IntPoint origin = new IntPoint(0, 0);
		IntPoint below = new IntPoint(0,1);
		
		assertEquals(below, origin.below());
		
	}
	
	@Test
	public void testFloodFill() {
		char screen[][] = {{'1', '1', '1', '1', '1', '1', '1', '1'},
				{'1', '1', '1', '1', '1', '1', '0', '0'},
				{'1', '0', '0', '1', '1', '0', '1', '1'},
				{'1', '2', '2', '2', '2', '0', '1', '0'},
				{'1', '1', '1', '2', '2', '0', '1', '0'},
				{'1', '1', '1', '2', '2', '2', '2', '0'},
				{'1', '1', '1', '1', '1', '2', '1', '1'},
				{'1', '1', '1', '1', '1', '2', '2', '1'}};
		IntPoint point = new IntPoint(4, 4);
		char filledScreen[][] = {{'1', '1', '1', '1', '1', '1', '1', '1'},
				{'1', '1', '1', '1', '1', '1', '0', '0'},
				{'1', '0', '0', '1', '1', '0', '1', '1'},
				{'1', '3', '3', '3', '3', '0', '1', '0'},
				{'1', '1', '1', '3', '3', '0', '1', '0'},
				{'1', '1', '1', '3', '3', '3', '3', '0'},
				{'1', '1', '1', '1', '1', '3', '1', '1'},
				{'1', '1', '1', '1', '1', '3', '3', '1'}};
		assertTrue(Arrays.deepEquals(filledScreen, FloodFill.fill(screen, point, '3')));
	}
	
	@Test
	
	public void testLCM() {
		List<Long> numbers = new ArrayList<Long>();
		numbers.add(3911L);
		numbers.add(3917L);
		numbers.add(3929L);
		numbers.add(3793L);
		assertEquals(228300182686739L, Maths.lcm(numbers));
	}
	
}
