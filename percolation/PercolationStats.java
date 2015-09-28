/*--------------------
 * Used to calculate the statistics of percolation
 *--------------------*/
import edu.princeton.cs.algs4.*;

public class PercolationStats {
    private double[] result;
    private int T;
    public PercolationStats(int N, int T)
    {
        if (N <= 0 || T <= 0)
            throw new java.lang.IllegalArgumentException("Size and Numbers of simulations must be larger than 0!");
        
        this.T = T;
        this.result = new double[T];
        int i = 0;
        int j = 0;
        
        int counter = 0;
        while (counter < T)
        {
            Percolation currSim = new Percolation(N);
            double nOfOpens = 0;
            while (!currSim.percolates())
            {
                do
                {
                    i = StdRandom.uniform(N) + 1;
                    j = StdRandom.uniform(N) + 1;
                }
                while (currSim.isOpen(i, j));
                currSim.open(i, j);
                nOfOpens++;
            }
            this.result[counter] = nOfOpens / N / N;
            counter++;
        }
        
    }
    public double mean() { return StdStats.mean(this.result); }
    public double stddev() { return StdStats.stddev(this.result); }
    public double confidenceLo()
    {
        return this.mean() - 1.96 * this.stddev() / Math.sqrt(this.T);
    }
    public double confidenceHi()
    {
        return this.mean() + 1.96 * this.stddev() / Math.sqrt(this.T);
    }
    
    public static void main(String[] args)
    {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats test = new PercolationStats(N, T);
        StdOut.printf("mean                    = %f\n", test.mean());
        StdOut.printf("stddev                  = %f\n", test.stddev());
        StdOut.printf("95%% confidence interval = %f, %f\n", 
                      test.confidenceLo(), test.confidenceHi());
    }
}
