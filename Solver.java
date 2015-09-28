/*-------------
 * An implementation of A* algo solver for 8 puzzle problem
 *-------------*/
import java.util.LinkedList;

public class Solver
{
    private boolean solvable;
    private int moves;
    private LinkedList<Board> solutions = new LinkedList<Board>();
        
    private class Node implements Comparable<Node>
    {
        private Board currBoard;
        private int moves;
        private Node prev;
        
        public Node(Board currBoard, int moves, Node prev)
        {
            this.currBoard = currBoard;
            this.moves = moves;
            this.prev = prev;
        }
        
        public int compareTo(Node that)
        {
            if (this.moves + this.currBoard.manhattan()
                    > that.moves + that.currBoard.manhattan())
                return 1;
            
            if (this.moves + this.currBoard.manhattan()
                    < that.moves + that.currBoard.manhattan())
                return -1;
            
            return 0;
        }
    }
    
    public Solver(Board initial)
    {
        int oriMoves = 0;
        int twinMoves = 0;
        boolean oriGoal = false;
        boolean twinGoal = false;
        Node currNode = new Node(initial, 0, null);
        Node currTwinsNode = new Node(initial.twin(), 0, null);
        Board prevBoard;
        Board prevTwinsBoard;
        MinPQ<Node> pq = new MinPQ<Node>();
        MinPQ<Node> pqTwins = new MinPQ<Node>();
        pq.insert(currNode);
        pqTwins.insert(currTwinsNode);
        
        while ((!oriGoal) && (!twinGoal))
        {
            currNode = pq.delMin();
            currTwinsNode = pqTwins.delMin();
            prevBoard = currNode.prev == null ? null : currNode.prev.currBoard;
            prevTwinsBoard = 
                currTwinsNode.prev == null ? null : currTwinsNode.prev.currBoard;
            oriMoves = currNode.moves + 1;
            twinMoves = currTwinsNode.moves + 1;
            oriGoal = currNode.currBoard.isGoal();
            twinGoal = currTwinsNode.currBoard.isGoal();
            
            for (Board board: currNode.currBoard.neighbors())
            {
                if (!board.equals(prevBoard))
                    pq.insert(new Node(board, oriMoves, currNode));
            }
            
            for (Board board: currTwinsNode.currBoard.neighbors())
            {
                if (!board.equals(prevTwinsBoard))
                    pqTwins.insert(new Node(board, twinMoves, currTwinsNode));
            }
        }
               
        if (oriGoal)
        {
            solvable = true;
            moves = oriMoves - 1;
            solutions.add(currNode.currBoard);
            while (currNode.prev != null)
            {
                solutions.addFirst(currNode.prev.currBoard);
                currNode = currNode.prev;
            }
        }
        else
        {
            solvable = false;
            moves = -1;
            solutions = null;
        }
    }
    
    public boolean isSolvable()
    {
        return solvable;
    }
    
    public int moves()
    {
        return moves;
    }
    
    public Iterable<Board> solution()
    {
        return solutions;
    }
    
    public static void main(String[] args)
    {
        
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else 
        {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}