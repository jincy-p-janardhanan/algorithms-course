import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n, virtualTop, virtualBottom;
    private final boolean[][] grid;
    private final WeightedQuickUnionUF locations;
    private int count;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n must be greater than 0.");

        virtualTop = 0;
        virtualBottom = n * n + 1;
        grid = new boolean[n][n];
        locations = new WeightedQuickUnionUF(virtualBottom + 1);
        this.n = n;
        count = 0;
    }

    private int idx(int row, int col) {
        return n * row + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        int current, top, bottom, left, right, currentValue;

        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException();

        if (isOpen(row, col))
            return;

        row -= 1;       // back to 0-base index
        col -= 1;
        current = idx(row, col);
        currentValue = locations.find(current);
        top = idx(row - 1, col);    // Adjacent top
        bottom = idx(row + 1, col);   // Adjacent bottom
        left = idx(row, col - 1);     // Adjacent left
        right = idx(row, col + 1);    // Adjacent right
        this.grid[row][col] = true;
        count++;

        if (row != 0) {
            if (grid[row - 1][col])
                if (currentValue != locations.find(top)) {
                    locations.union(current, top);
                }
        } else {
            if (currentValue != virtualTop) {
                locations.union(virtualTop, current);
            }
        }

        if (row < n - 1) {
            if (grid[row + 1][col])
                if (currentValue != locations.find(bottom)) {
                    locations.union(current, bottom);
                }
        } else {
            if (currentValue != virtualBottom) {
                locations.union(virtualBottom, current);
            }
        }

        if (col != 0)
            if (grid[row][col - 1])
                if (currentValue != locations.find(left)) {
                    locations.union(current, left);
                }

        if (col < n - 1)
            if (grid[row][col + 1])
                if (currentValue != locations.find(right)) {
                    locations.union(current, right);
                }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException();

        row -= 1;
        col -= 1;
        return grid[row][col];
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n)
            throw new IllegalArgumentException();

        if (n == 1)
            return grid[0][0];

        row -= 1;
        col -= 1;
        if (grid[row][col]) {
            return locations.find(virtualTop) == locations.find(idx(row, col));
        }
        return false;
    }

    // does the system percolate?
    public boolean percolates() {
        if (n == 1)
            return grid[0][0];
        return (locations.find(virtualTop) == locations.find(virtualBottom));
    }

    // test client (optional)
    public static void main(String[] args) {

        // Monte - Carlo Simulation
        int n = 5;
        double percolationThreshold;
        Percolation testGrid = new Percolation(n);
        int r, c;
        while (!testGrid.percolates()) {
            r = StdRandom.uniformInt(1, n + 1);
            c = StdRandom.uniformInt(1, n + 1);
            if (testGrid.isOpen(r, c))
                continue;
            testGrid.open(r, c);
        }
        percolationThreshold = (double) testGrid.numberOfOpenSites() / (double) (n * n);
        StdOut.printf("Open sites %d " +
                        "\nPercolation threshold = %f",
                testGrid.numberOfOpenSites(), percolationThreshold);
    }
}
