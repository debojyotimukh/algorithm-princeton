import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
    private static final boolean BLOCKED = false;
    private static final boolean OPEN = true;
    private final int gridSize;
    private final WeightedQuickUnionUF grid;
    private final boolean[] gridStatus;
    private int openSitesCount = 0;

    private int openBottomCount = 0;
    private int[] openBottomCols;
    private boolean isPercolated = false;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(final int n) {
        if (n <= 0)
            throw new IllegalArgumentException();

        gridSize = n;
        grid = new WeightedQuickUnionUF(gridSize * gridSize + 2);
        gridStatus = new boolean[gridSize * gridSize];
        openBottomCols = new int[gridSize];

        for (int i = 0; i < gridSize; i++)
            for (int j = 0; j < gridSize; j++)
                gridStatus[gridSize * i + j] = BLOCKED;

        if (gridSize != 1) {
            final int virtualTop = gridSize * gridSize;
            for (int i = 0; i < gridSize; i++) {
                grid.union(virtualTop, findIndex(1, i + 1));
            }
        }

    }

    // find 1D 0-based index from row,col
    private int findIndex(final int row, final int col) {
        return (row - 1) * gridSize + (col - 1);
    }

    // is the row,col inside the specified grid ?
    private boolean isValid(final int row, final int col) {
        if (row < 1 || col < 1 || row > gridSize || col > gridSize)
            return false;
        return true;
    }

    // find 0-based indexes adjacent to an open site
    private int[] findAdjacentIndex(final int row, final int col) {
        if (!isValid(row, col))
            throw new IllegalArgumentException();
        if (gridSize == 1)
            return new int[] {};

        final int top = findIndex(row - 1, col);
        final int bottom = findIndex(row + 1, col);
        final int right = findIndex(row, col + 1);
        final int left = findIndex(row, col - 1);

        if (row == 1) { // top
            if (col == 1)// top-left
                return new int[] { right, bottom };
            if (col == gridSize)// top-right
                return new int[] { left, bottom };
            return new int[] { right, left, bottom };
        }
        if (row == gridSize) { // bottom
            if (col == 1)// bottom-left
                return new int[] { top, right };
            if (col == gridSize)// bottom-right
                return new int[] { top, left };
            return new int[] { right, left, top };
        }
        if (col == 1)// left
            return new int[] { right, top, bottom };
        if (col == gridSize)// right
            return new int[] { left, top, bottom };

        // Default (4)
        return new int[] { right, left, top, bottom };
    }

    // opens the site (row, col) if it is not open already
    public void open(final int row, final int col) {
        if (isOpen(row, col))
            return;

        gridStatus[findIndex(row, col)] = OPEN;
        openSitesCount++;

        if (row == gridSize)
            openBottomCols[openBottomCount++] = col;

        final int[] adj = findAdjacentIndex(row, col);
        for (int i = 0; i < adj.length; i++) {
            if (gridStatus[adj[i]] == OPEN) {
                grid.union(findIndex(row, col), adj[i]);
            }
        }
        if (isPercolated || openSitesCount < gridSize)
            return;

        if (!isFull(row, col))
            return;

        for (int i = 0; i < openBottomCount; i++) {
            if (isFull(gridSize, openBottomCols[i])) {
                isPercolated = true;
                break;
            }
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(final int row, final int col) {
        if (!isValid(row, col))
            throw new IllegalArgumentException();
        if (gridStatus[findIndex(row, col)] == OPEN)
            return true;
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(final int row, final int col) {
        if (!isValid(row, col))
            throw new IllegalArgumentException();
        if (!isOpen(row, col))
            return false;
        if (gridSize == 1)
            return true;
        final int virtualTop = gridSize * gridSize;
        final int current = findIndex(row, col);
        return grid.find(virtualTop) == grid.find(current);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        if (gridSize == 1 && isOpen(1, 1))
            return true;
        return isPercolated;
    }

    // print the grid
    private void print() {
        for (int i = 0; i < gridSize; i++) {
            StdOut.println(" ");
            for (int j = 0; j < gridSize; j++)
                StdOut.print(gridStatus[gridSize * i + j] ? "1 " : "0 ");
        }
        StdOut.println("");
    }

    // test client (optional)
    public static void main(final String[] args) {
        Percolation p = new Percolation(5);

        p.open(1, 1);
        p.open(2, 2);
        p.open(1, 2);
        p.open(1, 3);
        p.open(1, 4);
        p.open(3, 2);
        p.open(4, 2);

        p.open(1, 1);
        p.print();
        StdOut.println(p.percolates() ? "YES" : "NO");
        StdOut.println(p.numberOfOpenSites());
        StdOut.println(p.isFull(3, 2) ? "Y" : "N");
        StdOut.println(p.isFull(3, 3) ? "Y" : "N");
        p.open(5, 2);
        p.open(2, 1);
        p.open(4, 4);
        p.open(5, 4);
        p.print();
        StdOut.println(p.percolates() ? "YES" : "NO");
        StdOut.println(p.numberOfOpenSites());
        StdOut.println(p.isFull(4, 4) ? "Y" : "N"); // backwash

        p = new Percolation(1);
        p.print();
        StdOut.println(p.percolates() ? "YES" : "NO");
        StdOut.println(p.numberOfOpenSites());
        StdOut.println(p.isFull(1, 1) ? "Y" : "N");
        p.open(1, 1);
        p.print();
        StdOut.println(p.percolates() ? "YES" : "NO");
        StdOut.println(p.numberOfOpenSites());
        StdOut.println(p.isFull(1, 1) ? "Y" : "N");

        p = new Percolation(2);
        p.print();
        StdOut.println(p.percolates() ? "YES" : "NO");
        StdOut.println(p.numberOfOpenSites());
        StdOut.println(p.isFull(1, 1) ? "Y" : "N");
        p.open(1, 1);
        p.open(2, 2);
        p.print();
        StdOut.println(p.percolates() ? "YES" : "NO");
        StdOut.println(p.numberOfOpenSites());
        StdOut.println(p.isFull(1, 1) ? "Y" : "N");
        p.open(1, 2);
        p.print();
        StdOut.println(p.percolates() ? "YES" : "NO");
        StdOut.println(p.numberOfOpenSites());
        StdOut.println(p.isFull(1, 1) ? "Y" : "N");
    }
}