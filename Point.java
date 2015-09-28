/*------------------
 * An implementation of 
 *------------------*/
import java.util.Comparator;

public class Point implements Comparable<Point> 
{
    public final Comparator<Point> SLOPE_ORDER = new BySlope();
    
    private final int x;
    private final int y;
    
    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    private class BySlope implements Comparator<Point>
    {
        public int compare(Point v, Point w)
        {
            double slopeToV = Point.this.slopeTo(v);
            double slopeToW = Point.this.slopeTo(w);
            
            if (slopeToV < slopeToW)
                return -1;
            
            if (slopeToV > slopeToW)
                return 1;
            
            return 0;
        }
    }
   
    public void draw()
    {
        StdDraw.point(x, y);
    }
    
    public void drawTo(Point that)
    {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
    
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }
    
    public int compareTo(Point that)
    {
        if (y < that.y)
            return -1;
        
        if (y == that.y)
        {
            if (x < that.x)
                return -1;
            
            if (x > that.x)
                return 1;
            
            return 0;
        }
        
        return 1;
    }
    
    public double slopeTo(Point that)
    {
        if (y == that.y)
        {
            if (x == that.x)
                return Double.NEGATIVE_INFINITY;
            else
                return 0.0 / 1.0;
        }
        
        if (x == that.x)
            return Double.POSITIVE_INFINITY;
        
        return (that.y - y) / (double) (that.x - x);
    }
    
    public static void main(String[] args)
    {
        /*
        Point origin = new Point(0, 0);
        Point test1 = new Point(0, -3);
        Point test2 = new Point(0, 8);
        StdOut.println(origin.compareTo(test1));
        StdOut.println(origin.compareTo(test2));
        StdOut.println(test1.compareTo(test2));
        StdOut.println(origin.SLOPE_ORDER.compare(test1, test2));
        */
    }
}