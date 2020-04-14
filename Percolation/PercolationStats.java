import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {


    private final double[] fractions;
    private static double confidence_95 = 1.96;
    private final double stdDev;
    private final double meanValue;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        if (n <= 0) throw new IllegalArgumentException("The n should be more than zero.");
        if (trials <= 0) throw new IllegalArgumentException("The trials should be more than zero.");

        fractions = new double[trials];


        for (int i = 0; i < trials; i++) {

            Percolation perlocation = new Percolation(n);
            // int openSites = 0;

            while (!perlocation.percolates()) {
                int randomRow = StdRandom.uniform(n) + 1;
                int randomCol = StdRandom.uniform(n) + 1;
                if (!perlocation.isOpen(randomRow, randomCol)) {
                    perlocation.open(randomRow, randomCol);
                    // openSites++;
                }
            }

            this.fractions[i] = perlocation.numberOfOpenSites() * 1.0 / (n * n);
        }

        this.meanValue = StdStats.mean(this.fractions);
        this.stdDev = StdStats.stddev(this.fractions);

    }

    // sample mean of percolation threshold
    public double mean() {
        return meanValue;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return this.stdDev;
    }


    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.meanValue - confidence_95 * this.stdDev / Math.sqrt(fractions.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.meanValue + confidence_95 * this.stdDev / Math.sqrt(fractions.length);
    }

    // test client (see below)
    public static void main(String[] args) {


        int n = StdIn.readInt();
        int trials = StdIn.readInt();
        PercolationStats perStats = new PercolationStats(n, trials);
        StdOut.printf("mean                    = %.8f\n", perStats.mean());
        StdOut.printf("stddev                  = %.8f\n", perStats.stddev());
        StdOut.printf("95%% confidence interval = [%.16f, %.16f]", perStats.confidenceLo(), perStats.confidenceHi());


    }
}
