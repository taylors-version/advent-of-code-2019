package come.ben.aoc.Area;

import java.util.List;

import com.ben.aoc.IntPoint;
import com.ben.aoc.LongPoint;

public class Shoelace {
	
	public static long getShoeLaceAreaLongs(List<LongPoint> vertices) {
		long area = 0;
		
		for(int i = 0; i<vertices.size() -1; i++) {
			LongPoint vertice1 = vertices.get(i);
			LongPoint vertice2 = vertices.get(i+1);
			area += ((vertice1.getX() + vertice2.getX()) * (vertice1.getY() - vertice2.getY()));
		}
		
		LongPoint vertice1 = vertices.get(vertices.size()-1);
		LongPoint vertice2 = vertices.get(0);
		area += ((vertice1.getX() + vertice2.getX()) * (vertice1.getY() - vertice2.getY()));
		return (long)Math.abs(area / 2.0);
	}

	public static long getShoeLaceArea(List<IntPoint> vertices) {
		long area = 0;
		
		for(int i = 0; i<vertices.size() -1; i++) {
			IntPoint vertice1 = vertices.get(i);
			IntPoint vertice2 = vertices.get(i+1);
			area += ((vertice1.getX() + vertice2.getX()) * (vertice1.getY() - vertice2.getY()));
		}
		
		IntPoint vertice1 = vertices.get(vertices.size()-1);
		IntPoint vertice2 = vertices.get(0);
		area += ((vertice1.getX() + vertice2.getX()) * (vertice1.getY() - vertice2.getY()));
		return (long)Math.abs(area / 2.0);
	}
}
