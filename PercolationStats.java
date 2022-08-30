import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final int trials;
    private final double[] percolationThreshold;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        Percolation grid;
        int r, c;

        percolationThreshold = new double[trials];
        this.trials = trials;

        double totalNumberOfSites = (double) n * n;
        for (int i = 0; i < trials; i++) {
            grid = new Percolation(n);
            while (!grid.percolates()) {
                r = StdRandom.uniformInt(1, n + 1);
                c = StdRandom.uniformInt(1, n + 1);
                if (grid.isOpen(r, c))
                    continue;
                grid.open(r, c);
            }
            percolationThreshold[i] = (double) grid.numberOfOpenSites() / totalNumberOfSites;
        }
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]), trials = Integer.parseInt(args[1]);
        PercolationStats testPercolation = new PercolationStats(n, trials);
        StdOut.printf("mean\t\t\t\t\t= %f\n", testPercolation.mean());
        StdOut.printf("stddev\t\t\t\t\t= %f\n", testPercolation.stddev());
        StdOut.printf("95%s confidence interval\t= [%f, %f]\n", "%", testPercolation.confidenceLo(), testPercolation.confidenceHi());
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percolationThreshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percolationThreshold);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(trials));
    }
}
