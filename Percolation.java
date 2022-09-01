import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    private final byte[] statusBits;
    private final WeightedQuickUnionUF locations;
    private boolean percolated;
    private int count;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n must be greater than 0.");

        statusBits = new byte[n * n];
        locations = new WeightedQuickUnionUF(n * n);
        this.n = n;
        count = 0;
        percolated = false;
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
            if (testGrid.isOpen(r, c)) continue;
            testGrid.open(r, c);

        }
        percolationThreshold = (double) testGrid.numberOfOpenSites() / (double) (n * n);
        StdOut.printf("Open sites %d " + "\nPercolation threshold = %f\n", testGrid.numberOfOpenSites(), percolationThreshold);
    }

    private int idx(int row, int col) {
        return n * row + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        checkArgsValid(row, col);

        int current, newRoot, adjacentRoot;
        int[] adjacent = new int[4];
        byte status;

        row -= 1;       // back to 0-base index
        col -= 1;
        current = idx(row, col);
        if (statusBits[current] != 0) return;
        if (row == 0) adjacent[0] = -1;
        else adjacent[0] = idx(row - 1, col);   // Adjacent top
        if (row == n - 1) adjacent[1] = -1;
        else adjacent[1] = idx(row + 1, col);   // Adjacent bottom
        if (col == 0) adjacent[2] = -1;
        else adjacent[2] = idx(row, col - 1);    // Adjacent left
        if (col == n - 1) adjacent[3] = -1;
        else adjacent[3] = idx(row, col + 1);    // Adjacent right
        status = 4;           // site opened, 4 in binary = 100
        count++;

        if (n == 1) {
            statusBits[0] = 7;
            percolated = true;
            return;
        }
        if (row == 0) status = 6;      // top connected, 6 in binary = 110

        if (row == n - 1) status = 5;       // bottom connected, 5 in binary = 101

        for (int i = 0; i < 4; i++) {
            if (isValid(adjacent[i])) {
                adjacentRoot = locations.find(adjacent[i]);
                if (statusBits[adjacentRoot] != 0) {
                    status = (byte) (status | statusBits[adjacentRoot]);
                    locations.union(current, adjacent[i]);
                }
            }
        }

        newRoot = locations.find(current);
        statusBits[current] = status;

        /* Note: statusbit[current] is not always reliable
         *       and can only be used for checking open/closed
         *       because we're NOT updating all elements in the
         *       same connected component here. So, to get actual
         *       status of an element, we have to check status of its root.
         */

        statusBits[newRoot] = status;

        if (status == 7)     // 7 in binary = 111, => site open, top & bottom connected
            percolated = true;
    }

    private void checkArgsValid(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) throw new IllegalArgumentException();
    }

    private boolean isValid(int index) {
        return (index >= 0 && index < n * n);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkArgsValid(row, col);
        row -= 1;
        col -= 1;

        return (statusBits[(idx(row, col))]) != 0;    // 0 => closed site
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkArgsValid(row, col);
        row -= 1;
        col -= 1;
        int i = idx(row, col);

        // 1 = (001) in binary, full site => any of {6 = (110), 7 = (111)}
        return (statusBits[locations.find(i)] | 1) == 7;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolated;
    }
}
