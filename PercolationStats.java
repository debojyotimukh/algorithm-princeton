import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] thresholds;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        thresholds = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                p.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));

            }

            double openSites = (double) (p.numberOfOpenSites());
            thresholds[i] = openSites / Math.pow(n, 2);

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
        return mean() - CONFIDENCE_95 * s / Math.sqrt(thresholds.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double s = StdStats.stddev(thresholds);
        return mean() + CONFIDENCE_95 * s / Math.sqrt(thresholds.length);
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