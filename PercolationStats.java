import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static int N;
    private static int T;
    private double[] thresholds;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        N = n;
        T = trials;
        thresholds = new double[T];

        StdRandom.setSeed(30104+32*N-10*T);
        for (int i = 0; i < T; i++) {
            Percolation p = new Percolation(N);
            for (int j = 0; j < N * N; j++) {
                if (p.percolates())
                    break;
                p.open(StdRandom.uniform(1,N+1) , StdRandom.uniform(1,N+1));

            }
            // StdOut.println(p.numberOfOpenSites());
            Double openSites = new Double(p.numberOfOpenSites());
            thresholds[i] = openSites / Math.pow(N, 2);
            // StdOut.println(thresholds[i]);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.var(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double s = StdStats.stddev(thresholds);
        return mean() - 1.96 * s / Math.sqrt(T);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double s = StdStats.stddev(thresholds);
        return mean() + 1.96 * s / Math.sqrt(T);
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats pStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        StdOut.print("mean                   = ");
        StdOut.println(pStats.mean());
        StdOut.print("stddev                 = ");
        StdOut.println(pStats.stddev());
        StdOut.print("95% confidence interval= [");
        StdOut.print(pStats.confidenceLo());
        StdOut.print(",");
        StdOut.print(pStats.confidenceHi());
        StdOut.println("]");

    }

}