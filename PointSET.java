/*------------------
 * Implementation of brute force range search for 2D points
 -------------------*/
import java.util.TreeSet;
import java.util.Stack;
import edu.princeton.cs.algs4.*;

public class PointSET
{
    private TreeSet<Point2D> points;
    
    public PointSET()
    {
        points = new TreeSet<Point2D>();
    }
    
    public boolean isEmpty()
    {
        return points.isEmpty();
    }
    
    public int size()
    {
        return points.size();
    }
    
    public void insert(Point2D p)
    {
        points.add(p);
    }
    
    public boolean contains(Point2D p)
    {
        return points.contains(p);
    }
    
    public void draw()
    {
        for (Point2D p : points)
            p.draw();
    }
    
    public Iterable<Point2D> range(RectHV rect)
    {
        Stack<Point2D> inRect = new Stack<Point2D>();
        
        for (Point2D p : points)
        {
            if (rect.contains(p))
                inRect.push(p);
        }
        
        return inRect;
    }
    
    public Point2D nearest(Point2D p)
    {
        double dis = 20;
        double currDis = 0;
        Point2D nearest = null;
        
        for (Point2D currP : points)
        {
            currDis = p.distanceSquaredTo(currP);
            if (currDis < dis)
            {
                dis = currDis;
                nearest = currP;
            }
        }
        
        return nearest;
    }
    
    public static void main(String[] args)
    {
        PointSET test = new PointSET();
        Point2D p = new Point2D(1, 2);
        
        StdOut.println(test.nearest(p));
        /*
        String filename = args[0];
        In in = new In(filename);
        
        StdDraw.show(0);
        
        PointSET brute = new PointSET();
        while (!in.isEmpty())
        {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            brute.insert(p);
        }
        
        double x0 = 0.0, y0 = 0.0;      // initial endpoint of rectangle
        double x1 = 0.0, y1 = 0.0;      // current location of mouse
        boolean isDragging = false;     // is the user dragging a rectangle

        // draw the points
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        brute.draw();*/
/*
        while (true) 
        {
            StdDraw.show(40);

            // user starts to drag a rectangle
            if (StdDraw.mousePressed() && !isDragging) {
                x0 = StdDraw.mouseX();
                y0 = StdDraw.mouseY();
                isDragging = true;
                continue;
            }

            // user is dragging a rectangle
            else if (StdDraw.mousePressed() && isDragging) {
                x1 = StdDraw.mouseX();
                y1 = StdDraw.mouseY();
                continue;
            }

            // mouse no longer pressed
            else if (!StdDraw.mousePressed() && isDragging) {
                isDragging = false;
            }


            RectHV rect = new RectHV(Math.min(x0, x1), Math.min(y0, y1),
                                     Math.max(x0, x1), Math.max(y0, y1));
            // draw the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            brute.draw();

            // draw the rectangle
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius();
            rect.draw();

            // draw the range search results for brute-force data structure in red
            StdDraw.setPenRadius(.03);
            StdDraw.setPenColor(StdDraw.RED);
            for (Point2D p : brute.range(rect))
                p.draw();
            
            StdDraw.show(40);
        }
        *//*
        while (true) 
        {

            // the location (x, y) of the mouse
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            Point2D query = new Point2D(x, y);

            // draw all of the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            brute.draw();

            // draw in red the nearest neighbor (using brute-force algorithm)
            StdDraw.setPenRadius(.03);
            StdDraw.setPenColor(StdDraw.RED);
            brute.nearest(query).draw();
            StdDraw.setPenRadius(.02);

            StdDraw.show(0);
            StdDraw.show(40);
        }
        */
    }
}
