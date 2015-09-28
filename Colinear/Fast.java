/*-----------------------
 * Revised fast algo to find group of 4 colinear points
 *-----------------------*/
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;

public class Fast
{   
    private static void printAndDraw(ArrayList<Point> points, Point currPoint, 
                                     int st, int ed)
    {
        Point[] colPoints = new Point[ed - st + 1];
        int counter = 0;
        
        for (int i = st; i < ed; i++)
            colPoints[counter++] = points.get(i);
        
        colPoints[counter] = currPoint;
        Arrays.sort(colPoints);
        
        if (currPoint == colPoints[0])
        {
            colPoints[0].drawTo(colPoints[counter]);
            
            for (int i = 0; i < counter; i++)
                StdOut.print(colPoints[i].toString() + " -> ");
            
            StdOut.println(colPoints[counter].toString());
        }
    }
    
    public static void main(String[] args)
    {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);
        
        In in = new In(args[0]);
        int nOfPoints = in.readInt();
        Point[] points = new Point[nOfPoints];
        ArrayList<Point> pointsCopy = new ArrayList<Point>(nOfPoints);
        
        Point currPoint;
        double currSlope;
        int xCoor;
        int yCoor;
        int counter;
        int currSize;
        
        for (int i = 0; i < nOfPoints; i++)
        {
            xCoor = in.readInt();
            yCoor = in.readInt();
            points[i] = new Point(xCoor, yCoor);
            points[i].draw();
            pointsCopy.add(points[i]);
        }
        
        Arrays.sort(points);
        
        for (int i = 0; i < nOfPoints; i++)
        {
            counter = 1;
            currPoint = points[i];
            Collections.sort(pointsCopy, currPoint.SLOPE_ORDER);
            currSlope = currPoint.slopeTo(pointsCopy.get(0));
            currSize = pointsCopy.size();
            
            for (int j = 1; j < currSize; j++)
            {
                if (currSlope == currPoint.slopeTo(pointsCopy.get(j)))
                {
                    counter++;
                }
                else
                {
                    if (counter >= 3)
                        printAndDraw(pointsCopy, currPoint, j - counter, j);
                    
                    currSlope = currPoint.slopeTo(pointsCopy.get(j));
                    counter = 1;
                }
            }
            
            if (counter >= 3)
                printAndDraw(pointsCopy, currPoint, currSize - counter,
                             currSize);
        }
        
        StdDraw.show(0);
        StdDraw.setPenRadius();
    }
}