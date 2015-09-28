/*-------------------
 * Brute force algo to find 4 colinear points from an input file
 *-------------------*/
import java.util.Arrays;
    
public class Brute
{
    public static void main(String[] args)
    {
        
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);
        
        In in = new In(args[0]);
        int nOfPoints = in.readInt();
        Point[] points = new Point[nOfPoints];
        int xCoor;
        int yCoor;
        double slope1;
        double slope2;
        double slope3;

        
        for (int i = 0; i < nOfPoints; i++)
        {
            xCoor = in.readInt();
            yCoor = in.readInt();
            points[i] = new Point(xCoor, yCoor);
            points[i].draw();
            /*
            points[i].toString();
            StdOut.println();
            */
        }
        
        //StdOut.println();
        Arrays.sort(points);
        
        /*
        for (int i = 0; i < nOfPoints; i++)
        {
            points[i].toString();
            StdOut.println();
        }
        */
        
        for (int i = 0; i < nOfPoints; i++)
        {
            for (int j = i + 1; j < nOfPoints; j++)
            {
                for (int k = j + 1; k < nOfPoints; k++)
                {
                    slope1 = points[i].slopeTo(points[j]);
                    slope2 = points[j].slopeTo(points[k]);
                    
                    if (slope1 == slope2)
                    {
                        for (int h = k + 1; h < nOfPoints; h++)
                        {
                            slope3 = points[k].slopeTo(points[h]);
                            if (slope2 == slope3)
                            {
                                points[i].drawTo(points[h]);
                                StdOut.println(points[i].toString() + " -> " 
                                                   + points[j].toString() + " -> "
                                                   + points[k].toString() + " -> "
                                                   + points[h].toString());
                            }
                        }
                    }
                }
            }
        }
        
        
        StdDraw.show(0);
        StdDraw.setPenRadius();
    }
}