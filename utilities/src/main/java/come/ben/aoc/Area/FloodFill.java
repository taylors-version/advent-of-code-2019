package come.ben.aoc.Area;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import com.ben.aoc.IntPoint;

public class FloodFill {

	public static char[][] fill(char[][] data, IntPoint source, char value) {
		   
		int yMax = data.length;
		int xMax = data[0].length;
		
         Set<IntPoint> visited = new HashSet<IntPoint>();
         
         Queue<IntPoint> queue = new LinkedList<>();
   
         queue.add(source);
          
        visited.add(source);
   
        // Until queue is empty
        while (!queue.isEmpty()) 
        {
            // Extracting front pair
            IntPoint coord = queue.peek();
            int x1 = coord.getX();
            int y1 = coord.getY();
            char preValue = data[y1][x1];
            
            data[y1][x1] = value;
     
            // Popping front pair of queue
            queue.remove();
 
            // For Upside Point
            IntPoint upPoint = (IntPoint) coord.above();
            if (validCoord(upPoint, xMax, yMax) && !visited.contains(upPoint) && data[y1-1][x1] == preValue) {
                queue.add(upPoint);
                visited.add(upPoint);
            }
            
            // For Downside Point
            IntPoint downPoint = (IntPoint) coord.below();
            if (validCoord(downPoint, xMax, yMax) && !visited.contains(downPoint) && data[y1+1][x1] == preValue) {
                queue.add(downPoint);
                visited.add(downPoint);
            }
            
            // For Rightside Point
            IntPoint rightPoint = (IntPoint) coord.right();
            if (validCoord(rightPoint, xMax, yMax) && !visited.contains(rightPoint) && data[y1][x1 + 1] == preValue) {
                queue.add(rightPoint);
                visited.add(rightPoint);
            }
     
            // For Leftside Point
            IntPoint leftPoint = (IntPoint) coord.left();
            if (validCoord(leftPoint, xMax, yMax) && !visited.contains(leftPoint) && data[y1][x1 - 1] == preValue) {
                queue.add(leftPoint);
                visited.add(leftPoint);
            }
        }
        return data;
    }
	
	private static boolean validCoord(IntPoint coord, int xMax, int yMax) {
		int x = coord.getX();
		int y = coord.getY();
        if (x < 0 || y < 0 || x >= xMax || y >= yMax) {
            return false;
        }

        return true;
    }
	
}
