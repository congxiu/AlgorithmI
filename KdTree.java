/*----------------
 * An implementation of 2d Tree
 -----------------*/
import java.util.Stack;
import edu.princeton.cs.algs4.*;

public class KdTree
{
    private Node root;
    private int size;
    
    private static class Node
    {
        private double key;
        private Node left;
        private Node right;
        private RectHV rect;
        private Point2D point;
        
        public Node(double x, double y, double xMin, double yMin, 
                    double xMax, double yMax, boolean compareX)
        {
            this.point = new Point2D(x, y);
            this.left = null;
            this.right = null;
            
            if (compareX)
            {
                this.key = x;
                this.rect = new RectHV(x, yMin, x, yMax);
            }
            else
            {
                this.key = y;
                this.rect = new RectHV(xMin, y, xMax, y);
            }
        }
        
        public boolean lessThan(Point2D that, boolean compareX)
        {
            if (compareX)
                return that.x() >= key;
            else
                return that.y() >= key;
        }
    }
    
    public KdTree()
    {
        root = null;
        size = 0;
    }
    
    public boolean isEmpty()
    {
        return size == 0;
    }
    
    public int size()
    {
        return size;
    }
    
    private Node put(Node node, Point2D p, double xMin, double yMin, 
                     double xMax, double yMax, boolean compareX)
    {
        if (node == null)
        {
            size += 1;
            return new Node(p.x(), p.y(), xMin, yMin, xMax, yMax,
                            compareX);
        }
        
        if (node.point.equals(p))
            return node;
        
        if (compareX)
        {
            if (node.lessThan(p, compareX))
                node.right = put(node.right, p, node.key, 
                                 yMin, xMax, yMax, !compareX);
            else
                node.left = put(node.left, p, xMin, yMin, node.key, 
                                yMax, !compareX);
        }
        else
        {
            if (node.lessThan(p, compareX))
                node.right = put(node.right, p, xMin, 
                                 node.key, xMax, yMax, !compareX);
            else
                node.left = put(node.left, p, xMin, yMin, xMax, 
                                node.key, !compareX);
        }
        
        return node;
    }
    
    public void insert(Point2D p)
    {
        root = put(root, p, 0.0, 0.0, 1.0, 1.0, true);
    }
    
    private boolean search(Node node, Point2D p, boolean compareX)
    {
        if (node == null)
            return false;
        
        if (node.point.equals(p))
            return true;
        
        if (node.lessThan(p, compareX))
            return search(node.right, p, !compareX);
        else
            return search(node.left, p, !compareX);
    }
    
    public boolean contains(Point2D p)
    {
        return search(root, p, true);
    }
    
    private void drawPointLine(Node node, boolean compareX)
    {
        if (node == null)
            return;
        
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        node.point.draw();
        
        if (compareX)
            StdDraw.setPenColor(StdDraw.RED);
        else
            StdDraw.setPenColor(StdDraw.BLUE);
        
        StdDraw.setPenRadius();
        node.rect.draw();
        
        drawPointLine(node.left, !compareX);
        drawPointLine(node.right, !compareX);
    }
    
    public void draw()
    {
        drawPointLine(root, true);
    }
    
    private void nodeInRect(Node node, RectHV rect, Stack<Point2D> inRect,
                            boolean compareX)
    {
        if (node == null)
            return;
        
        if (rect.intersects(node.rect))
        {
            if (rect.contains(node.point))
                inRect.push(node.point);
            nodeInRect(node.left, rect, inRect, !compareX);
            nodeInRect(node.right, rect, inRect, !compareX);
        }
        else
        {
            if (compareX)
            {
                if (rect.xmax() < node.key)
                    nodeInRect(node.left, rect, inRect, !compareX);
                else
                    nodeInRect(node.right, rect, inRect, !compareX);
            }
            else
            {
                if (rect.ymax() < node.key)
                    nodeInRect(node.left, rect, inRect, !compareX);
                else
                    nodeInRect(node.right, rect, inRect, !compareX);
            }
        }
    }
    
    public Iterable<Point2D> range(RectHV rect)
    {
        Stack<Point2D> inRect = new Stack<Point2D>();
        nodeInRect(root, rect, inRect, true);
        
        return inRect;
    }
    
    private Point2D searchNearest(Node node, Point2D p, boolean compareX,
                                  double currDis, Point2D currNearest)
    {
        if (node == null)
            return currNearest;
        
        double dis = p.distanceSquaredTo(node.point);
        if (dis < currDis)
        {
            currDis = dis;
            currNearest = node.point;
        }
        
        if (node.lessThan(p, compareX))
        {
            currNearest = searchNearest(node.right, p, !compareX, 
                                        currDis, currNearest);
            currDis = currNearest.distanceSquaredTo(p);
            
            if (node.rect.distanceSquaredTo(p) < currDis)
                currNearest = searchNearest(node.left, p, !compareX, 
                                        currDis, currNearest);
        }
        else
        {
            currNearest = searchNearest(node.left, p, !compareX, 
                                        currDis, currNearest);
            currDis = currNearest.distanceSquaredTo(p);
            
            if (node.rect.distanceSquaredTo(p) < currDis)
                currNearest = searchNearest(node.right, p, !compareX, 
                                        currDis, currNearest);
        }
        
        return currNearest;
    }
    
    public Point2D nearest(Point2D p)
    {
        Point2D nearestNeighbor = searchNearest(root, p, true, 2.0, null);
        
        return nearestNeighbor;
    }
    
    public static void main(String[] args)
    {
        /*
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        StdDraw.show(0);
        KdTree kdtree = new KdTree();
        while (true) {
            if (StdDraw.mousePressed()) 
            {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                //System.out.printf("%8.6f %8.6f\n", x, y);
                Point2D p = new Point2D(x, y);
                if (rect.contains(p)) 
                {
                    StdOut.printf("%8.6f %8.6f\n", x, y);
                    kdtree.insert(p);
                    StdOut.println(kdtree.size());
                    StdDraw.clear();
                    kdtree.draw();
                }
            }
            StdDraw.show(50);
        }
        */
        
        
    }
}
