import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;


class SortByX implements Comparator<Point>
{
    // Used for sorting in ascending order of
    // roll number
    public int compare(Point a, Point b)
    {
        return (int) (Math.ceil(a.x - b.x));
    }
}

class SortByY implements Comparator<Point>
{
    // Used for sorting in ascending order of
    // roll number
    public int compare(Point a, Point b)
    {
        return (int) (Math.ceil(a.y - b.y));
    }
}

public class ClosestPairTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Initialize and take inputs
		ArrayList<Point> points = new ArrayList<Point>();
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter the number of points");
		int noOfPoints = scan.nextInt();
		
		float xCoord, yCoord;
		
		for(int i=0;i<noOfPoints;i++){
			System.out.println("Enter space seperated x y values of point number " + (i+1));
			xCoord = scan.nextFloat();
			yCoord = scan.nextFloat();
			points.add(new Point(xCoord,yCoord));
		}
		
		System.out.println("Closest points are " + FindClosestPoint(points) + " units far away");
		
	}

	  
	//distance between points 
	static float Dist(Point p1, Point p2) 
	{ 
		//System.out.println("distances: " + (float) Math.sqrt((p1.x - p2.x)*(p1.x - p2.x) + (p1.y - p2.y)*(p1.y - p2.y)));
	    return (float) Math.sqrt((p1.x - p2.x)*(p1.x - p2.x) + (p1.y - p2.y)*(p1.y - p2.y)); 
	}
	
	static float BruteForce(ArrayList<Point> points){
		float MIN_DIST = 9999999999.9f;
		for(int i=0;i<points.size();i++){
			for(int j=i+1;j<points.size();j++){
				if(Dist(points.get(i),points.get(j)) < MIN_DIST){
					MIN_DIST = Dist(points.get(i),points.get(j));
				}
			}
		}
		return MIN_DIST;
	}
	 
	// Note that this method seems to be a O(n^2) method, but it's a O(n) 
	// method as the inner loop runs at most 7 times 
	static float stripClosest(ArrayList<Point> strip, float d) 
	{ 
	    float min = d;  // Initialize the minimum distance as d 
	  
	    //This loop runs at most 7 times 
	    for (int i = 0; i < strip.size(); ++i) 
	        for (int j = i+1; j < strip.size() && (strip.get(j).y - strip.get(i).y) < min; ++j) 
	            if (Dist(strip.get(i),strip.get(j)) < min) 
	                min = Dist(strip.get(i), strip.get(j)); 
	  
	    return min; 
	} 
	
	static float FindSmallestDistance(ArrayList<Point> xPoints,ArrayList<Point> yPoints){
		if(xPoints.size()<=3){
			return BruteForce(xPoints); 
		}
		
		int midPointXIndex = xPoints.size()/2;
		Point midPointX = xPoints.get(midPointXIndex);
		
		ArrayList<Point> xSortedPointsL;
		ArrayList<Point> xSortedPointsR;
		xSortedPointsL = new ArrayList<Point>(xPoints.subList(0, midPointXIndex));
		xSortedPointsR = new ArrayList<Point>(xPoints.subList(midPointXIndex+1,xPoints.size()-1));
		
		ArrayList<Point> ySortedPointsL = new ArrayList<Point>();
		ArrayList<Point> ySortedPointsR = new ArrayList<Point>();
		Point curPoint;
		for(int i = 0;i<yPoints.size();i++){
			curPoint = yPoints.get(i);
			if(curPoint.x< midPointX.x)
				ySortedPointsL.add(curPoint);
			else
				ySortedPointsR.add(curPoint);
		}
		
		float distLeft = FindSmallestDistance(xSortedPointsL,ySortedPointsL);
		float distRight = FindSmallestDistance(xSortedPointsR,ySortedPointsR);
		
		float smallerDist = Math.min(distLeft, distRight);
		
		ArrayList<Point> strip = new ArrayList<Point>();
		
	    for (int i = 0; i < xPoints.size(); i++){ 
	        if (Math.abs(yPoints.get(i).x - midPointX.x) < smallerDist) 
	            strip.add(yPoints.get(i));
	    }
	    return Math.min(smallerDist, stripClosest(strip, smallerDist) ); 
	}

	
	static float FindClosestPoint(ArrayList<Point> inputPoints){
		ArrayList<Point> xSortedPoints = new ArrayList<Point>(inputPoints);
		Collections.sort(xSortedPoints, new SortByX());
		ArrayList<Point> ySortedPoints = new ArrayList<Point>(inputPoints);
		Collections.sort(ySortedPoints, new SortByY());
		float minDistance = FindSmallestDistance(xSortedPoints,ySortedPoints); 
		return minDistance;
	}

}
