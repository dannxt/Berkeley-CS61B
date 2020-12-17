package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/* WeightedQuickUnionUF
   void union(int p, int q): unions two items;
   boolean connected(int p, int q): returns true if connected;
   int find(int p): returns set number of given item;
   int count(): return the number of sets;
*/

public class Percolation {
    // create N-by-N grid, with all sites initially blocked
    private int dimension;
    private int openSites;
    private int waterSite;
    private int bottomSites;
    private boolean percolates;
    private int[][] grid;
    private WeightedQuickUnionUF WF;
    private WeightedQuickUnionUF TF;

    public Percolation(int N) {
        dimension = N;
        openSites = 0;
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException("N should be larger than 0");
        }
        grid = new int[N][N];
        waterSite = N * N;
        bottomSites = N * N + 1;
        WF = new WeightedQuickUnionUF(N * N + 1);
        TF = new WeightedQuickUnionUF(N * N + 2);
    }

    // converts grid coordinates to an int representation
    private int xyTo1D(int r, int c) {
        return (r * dimension) + c;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row > dimension - 1 || row < 0 || col > dimension - 1 || col < 0) {
            throw new java.lang.IndexOutOfBoundsException("arguments cannot exceed grid dimension");
        }

        /*
         * percolation process
         */
        if (!isOpen(row, col)) {
            grid[row][col] = 1;
            openSites += 1;

            /* Top and bottom checks */
            if (dimension == 1) {
                fillSite(row, col);
                TF.union(xyTo1D(row, col), bottomSites);
            } else if (row == 0) {
                fillSite(row, col);
                checkBottomSite(row, col);
            } else if (row == dimension - 1) {
                TF.union(xyTo1D(row, col), bottomSites);
                checkTopSite(row, col);
            } else {
                checkBottomSite(row, col);
                checkTopSite(row, col);
            }

            /* Check/union left if not at first col */
            if (col != 0) {
                checkLeftSite(row, col);
            }

            /* Check/union right if not at last col */
            if (col != dimension - 1) {
                checkRightSite(row, col);
            }
        }
    }

    private void checkTopSite(int row, int col) {
        if (isOpen(row - 1, col)) {
            WF.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            TF.union(xyTo1D(row, col), xyTo1D(row - 1, col));
        }
    }

    private void checkLeftSite(int row, int col) {
        if (isOpen(row, col - 1)) {
            WF.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            TF.union(xyTo1D(row, col), xyTo1D(row, col - 1));
        }
    }

    private void checkRightSite(int row, int col) {
        if (isOpen(row, col + 1)) {
            WF.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            TF.union(xyTo1D(row, col), xyTo1D(row, col + 1));
        }
    }

    private void checkBottomSite(int row, int col) {
        if (isOpen(row + 1, col)) {
            WF.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            TF.union(xyTo1D(row, col), xyTo1D(row + 1, col));
        }
    }

    private void fillSite(int row, int col) {
        WF.union(xyTo1D(row, col), waterSite);
        TF.union(xyTo1D(row, col), waterSite);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row > dimension - 1 || row < 0 || col > dimension || col < 0) {
            throw new java.lang.IndexOutOfBoundsException("arguments cannot exceed grid dimension");
        }
        return grid[row][col] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row > dimension - 1 || row < 0 || col > dimension - 1 || col < 0) {
            throw new java.lang.IndexOutOfBoundsException("arguments cannot exceed grid dimension");
        }
        return WF.connected(xyTo1D(row, col), waterSite);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return TF.connected(bottomSites, waterSite);
    }

    // use for unit testing (not required, but keep this here for the autograder)
    public static void main(String[] args) {

    }
}
