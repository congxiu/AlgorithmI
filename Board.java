/*----------------------
 * An implementation for the 8 Puzzle.
 *----------------------*/
import java.util.Stack;

public class Board
{
    private int[][] blocks;
    private int dimension;
    
    public Board(int[][] blocks)
    {
        dimension = blocks.length;
        this.blocks = new int[dimension][dimension];
        
        for (int i = 0; i < dimension; i++)
        {
            for (int j = 0; j < dimension; j++)
                this.blocks[i][j] = blocks[i][j];
        }
    }
    
    public int dimension()
    {
        return dimension;
    }
    
    public int hamming()
    {
        int counter = 1;
        int dis = 0;
        
        for (int i = 0; i < dimension; i++)
        {
            for (int j = 0; j < dimension; j++)
            {
                if (counter != blocks[i][j] && counter < dimension * dimension)
                    dis++;
                
                counter++;
            }
        }
        
        return dis;
    }
    
    public int manhattan()
    {
        int dis = 0;
        int currNumber = 0;
        int iCorrect;
        int jCorrect;
        
        for (int i = 0; i < dimension; i++)
        {
            for (int j = 0; j < dimension; j++)
            {
                currNumber = blocks[i][j];
                if (currNumber != 0)
                {
                    iCorrect
                        = (int) Math.ceil(currNumber / (double) dimension) - 1;
                    jCorrect = (currNumber - 1) % dimension;
                    dis += Math.abs(i - iCorrect) + Math.abs(j - jCorrect);
                }
            }
        }
        
        return dis;
    }
    
    public boolean isGoal()
    {
        return hamming() == 0;
    }
    
    private void exch(int i, int j, int i2, int j2)
    {
        int temp = blocks[i][j];
        blocks[i][j] = blocks[i2][j2];
        blocks[i2][j2] = temp;
    }
    
    private Board makeNewBoard(int i, int j, int i2, int j2)
    {
        exch(i, j, i2, j2);
        Board newBoard = new Board(blocks);
        exch(i, j, i2, j2);
        
        return newBoard;
    }
    
    public Board twin()
    {   
        if (dimension == 1)
            return new Board(blocks);
        
        Board toBeReturned;
        
        if (blocks[0][0] != 0 && blocks[0][1] != 0)
            toBeReturned = makeNewBoard(0, 0, 0, 1);
        else
            toBeReturned = makeNewBoard(1, 0, 1, 1);
        
        return toBeReturned;
    }
    
    public boolean equals(Object y)
    {
        if (y == this) return true;
        
        if (y == null) return false;
        
        if (y.getClass() != this.getClass()) return false;
        
        Board that = (Board) y;
        
        if (that.dimension != this.dimension) return false;
        
        for (int i = 0; i < dimension; i++)
        {
            for (int j = 0; j < dimension; j++)
            {
                if (this.blocks[i][j] != that.blocks[i][j])
                    return false;
            }
        }
        
        return true;
    }
    
    public Iterable<Board> neighbors()
    {
        Stack<Board> toBeReturned = new Stack<Board>();
        int xZero = 0;
        int yZero = 0;
        
        for (int i = 0; i < dimension; i++)
        {
            for (int j = 0; j < dimension; j++)
            {
                if (blocks[i][j] == 0)
                {
                    xZero = i;
                    yZero = j;
                }
            }
        }
        
        if (xZero < dimension - 1)
            toBeReturned.push(makeNewBoard(xZero, yZero, xZero + 1, yZero));
        
        if (xZero > 0)
            toBeReturned.push(makeNewBoard(xZero, yZero, xZero - 1, yZero));
        
        if (yZero > 0)
            toBeReturned.push(makeNewBoard(xZero, yZero, xZero, yZero - 1));
        
        if (yZero < dimension - 1)
            toBeReturned.push(makeNewBoard(xZero, yZero, xZero, yZero + 1));
            
        return toBeReturned;
    }
    
    public String toString()
    {
        String toBeReturned = dimension + "\n";
        for (int i = 0; i < dimension; i++)
        {
            for (int j = 0; j < dimension; j++)
                toBeReturned += (" " + blocks[i][j]);
            
            toBeReturned += " \n";
        }
        return toBeReturned;
    }
    
    public static void main(String[] args)
    {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board test = new Board(blocks);
        
        StdOut.println(test.toString());
        StdOut.println(test.isGoal());
        StdOut.println(test.hamming());
        StdOut.println(test.manhattan());
        StdOut.println(test.twin().toString());
        
        for (Board board : test.neighbors())
            StdOut.println(board.toString());
    }
}