package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private double[] results;
    private int T;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.T = T;
        results = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation percolation = pf.make(N);
            while (!percolation.percolates()) {
                percolation.open(StdRandom.uniform(0, N), StdRandom.uniform(0, N));
            }
            double threshold = percolation.numberOfOpenSites() / (double) (N * N);
            results[i] = threshold;
        }
    }

    public double mean() {
        return StdStats.mean(results);
    }

    public double stddev() {
        return StdStats.stddev(results);
    }

    public double confidenceLow() {
        return mean() - (1.96 * stddev() / Math.sqrt(T));
    }

    public double confidenceHigh() {
        return mean() + (1.96 * stddev() / Math.sqrt(T));
    }
}
