import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
    private static final boolean BLOCKED = false;
    private static final boolean OPEN = true;
    private int gridSize;
    private WeightedQuickUnionUF grid;
    private boolean[] gridStatus;
    private int openSitesCount = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();

        gridSize = n;
        grid = new WeightedQuickUnionUF(gridSize * gridSize + 2);
        gridStatus = new boolean[gridSize * gridSize];
        for (int i = 0; i < gridSize; i++)
            for (int j = 0; j < gridSize; j++)
                gridStatus[gridSize * i + j] = BLOCKED;

        if (gridSize != 1) {
            int virtualTop = gridSize * gridSize;
            int virtualBottom = gridSize * gridSize + 1;
            for (int i = 0; i < gridSize; i++) {
                grid.union(virtualTop, findIndex(1, i + 1));
            }
            for (int i = 0; i < gridSize; i++) {
                grid.union(virtualBottom, findIndex(gridSize, i + 1));
            }
        }

    }

    // find 1D index from row,col
    private int findIndex(int row, int col) {
        return (row - 1) * gridSize + (col - 1);
    }

    private boolean isValid(int row, int col) {
        if (row < 1 || col < 1 || row > gridSize || col > gridSize)
            return false;
        return true;
    }

    // find indexes adjacent to an open site
    private int[] findAdjacentIndex(int row, int col) {
        if (!isValid(row, col))
            throw new IllegalArgumentException();
        if (gridSize == 1)
            return new int[] {};

        int top = findIndex(row - 1, col);
        int bottom = findIndex(row + 1, col);
        int right = findIndex(row, col + 1);
        int left = findIndex(row, col - 1);

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
    public void open(int row, int col) {
        if (isOpen(row, col))
            return;
        gridStatus[findIndex(row, col)] = OPEN;
        openSitesCount++;
        int[] adj = findAdjacentIndex(row, col);
        for (int i = 0; i < adj.length; i++) {
            if (gridStatus[adj[i]] == OPEN) {
                grid.union(findIndex(row, col), adj[i]);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!isValid(row, col))
            throw new IllegalArgumentException();
        if (gridStatus[findIndex(row, col)] == OPEN)
            return true;
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        // TODO: fix backwash problem
        if (!isValid(row, col))
            throw new IllegalArgumentException();
        if (!isOpen(row, col))
            return false;
        if (gridSize == 1)
            return true;
        int virtualTop = gridSize * gridSize;
        int current = findIndex(row, col);
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
        int virtualTop = gridSize * gridSize;
        int virtualBottom = gridSize * gridSize + 1;
        return grid.find(virtualBottom) == grid.find(virtualTop);
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
    public static void main(String[] args) {
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
        p.print();
        StdOut.println(p.percolates() ? "YES" : "NO");
        StdOut.println(p.numberOfOpenSites());
        StdOut.println(p.isFull(4, 4) ? "Y" : "N");

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