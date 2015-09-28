/*-------------------------
 *This is a implementation of the percolation API
 *It should be used to find out the threshold value of
 *percolation. 
 *-------------------------*/
import edu.princeton.cs.algs4.*;

public class Percolation {
    private int N;
    private boolean[][] grid;
    private WeightedQuickUnionUF underlying;
    private WeightedQuickUnionUF testFull;
    
    public Percolation(int N)
    {
        if (N <= 0)
            throw new 
            java.lang.IllegalArgumentException("Size must be larger than 0!");
        this.N = N;
        this.grid = new boolean[N][N];
        this.underlying = new WeightedQuickUnionUF(N * N + 2);
        this.testFull = new WeightedQuickUnionUF(N * N + 1);
    }
    
    private void checkIndex(int i, int j)
    {
        if (i <= 0 || j <= 0 || i > this.N || j > this.N)
            throw new java.lang.IndexOutOfBoundsException();
    }
    
/*    
    public void printGrid()
    {
        for (int i = 0; i <= this.N-1; i++)
        {
            for (int j = 0; j <= this.N-1; j++)
                StdOut.printf("%b ", this.grid[i][j]);
            StdOut.println();
        }   
    }
*/    
    public void open(int i, int j)
    {
        this.checkIndex(i, j);
        if (this.isOpen(i, j))
            return;
        
        this.grid[i-1][j-1] = true;
        
        if (i == 1)
        {
            this.underlying.union(0, j);
            this.testFull.union(0, j);
        }
        else if (this.isOpen(i - 1, j))
        {
            this.underlying.union((i-1) * this.N + j, (i-2) * this.N + j);
            this.testFull.union((i-1) * this.N + j, (i-2) * this.N + j);
        }
        
        if (i == this.N)
            this.underlying.union(this.N * this.N + 1, (i-1) * this.N + j);
        else if (this.isOpen(i + 1, j))
        {
            this.underlying.union((i-1) * this.N + j, (i) * this.N + j);
            this.testFull.union((i-1) * this.N + j, (i) * this.N + j);
        }
        
        if (j > 1)
        {
            if (this.isOpen(i, j - 1))
            {
                this.underlying.union((i-1) * this.N + j, (i-1) * this.N +j - 1);
                this.testFull.union((i-1) * this.N + j, (i-1) * this.N +j - 1);
            }
        }
        
        if (j < this.N)
        {
            if (this.isOpen(i, j + 1))
            {
                this.underlying.union((i-1) * this.N + j, (i-1) * this.N +j + 1);
                this.testFull.union((i-1) * this.N + j, (i-1) * this.N +j + 1);
            }
        }
    }
    
    public boolean isOpen(int i, int j)
    {
        this.checkIndex(i, j);
        return this.grid[i-1][j-1];
    }
    
    public boolean isFull(int i, int j)
    {
        this.checkIndex(i, j);
        return 
            this.testFull.connected(0, (i-1) * this.N + j);
    }
    
    public boolean percolates()
    {
        return 
            this.underlying.connected(0, this.N * this.N + 1);
    }
    
    public static void main(String[] args)
    {
/*
        Percolation test = new Percolation(3);
        test.printGrid();
        test.open(1, 2);
        test.open(2, 2);
        test.open(2, 3);
        test.open(3, 1);
        StdOut.println(test.percolates());
        test.printGrid();
*/
        return;
    }
}
