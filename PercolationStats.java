import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] thresholds;

    // perform independent trials on an n-by-n grid
    public PercolationStats(final int n, final int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        thresholds = new double[trials];

        for (int i = 0; i < trials; i++) {
            final Percolation p = new Percolation(n);
            while (!p.percolates()) {
                p.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));

            }

            final double openSites = (double) (p.numberOfOpenSites());
            thresholds[i] = openSites / Math.pow(n, 2);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (thresholds.length == 1)
            return Double.NaN;
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(thresholds.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(thresholds.length);
    }

    // test client (see below)
    public static void main(final String[] args) {
        final PercolationStats pStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
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