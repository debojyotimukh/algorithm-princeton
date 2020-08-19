import java.util.Arrays;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    private final int n;
    private final int[] tiles;
    private final int[] goal;
    private int manhattanCache = -1;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(final int[][] initial) {
        n = initial[0].length;
        tiles = flatten2DArray(initial);
        // create goal tile set
        goal = new int[n * n];
        for (int i = 0; i < n * n - 1; i++)
            goal[i] = i + 1;
        goal[n * n - 1] = 0;
    }

    // find 1D 0-based index from row,col
    private int findIndex(final int row, final int col) {
        return (row - 1) * n + (col - 1);
    }

    // convert a 2D array to 1D array
    private int[] flatten2DArray(final int[][] initial) {
        final int n = initial[0].length;
        final int[] array1D = new int[n * n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                array1D[n * i + j] = initial[i][j];

        return array1D;
    }

    private int[][] resolve2D(final int[] arr, final int rowSize) {
        final int[][] array2D = new int[rowSize][rowSize];
        for (int i = 0; i < rowSize; i++)
            for (int j = 0; j < rowSize; j++)
                array2D[i][j] = arr[n * i + j];
        return array2D;
    }

    // find 0-based indexes adjacent to an open site
    private int[] findAdjacentIndex(final int index) {
        final int row = index / n+1 ;
        final int col = index % n + 1;
        
        if (n == 1)
            return new int[] {};

        final int top = findIndex(row - 1, col);
        final int bottom = findIndex(row + 1, col);
        final int right = findIndex(row, col + 1);
        final int left = findIndex(row, col - 1);

        if (row == 1) { // top
            if (col == 1)// top-left
                return new int[] { right, bottom };
            if (col == n)// top-right
                return new int[] { left, bottom };
            return new int[] { right, left, bottom };
        }
        if (row == n) { // bottom
            if (col == 1)// bottom-left
                return new int[] { top, right };
            if (col == n)// bottom-right
                return new int[] { top, left };
            return new int[] { right, left, top };
        }
        if (col == 1)// left
            return new int[] { right, top, bottom };
        if (col == n)// right
            return new int[] { left, top, bottom };

        // Default (4)
        return new int[] { right, left, top, bottom };
    }

    // swap elements q[i], q[j]
    private void swapElements(final int[] q, final int i, final int j) {
        final int temp = q[i];
        q[i] = q[j];
        q[j] = temp;
    }

    // string representation of this board
    public String toString() {
        final StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[n * i + j] != 0)
                    s.append(String.format("%2d ", tiles[n * i + j]));
                else
                    s.append("   ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        if (!isGoal()) {
            for (int i = 0; i < tiles.length; i++) {
                if (tiles[i] == 0)
                    continue;
                if (tiles[i] != goal[i])
                    hamming++;
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        if (manhattanCache == -1) {
            int mancost = 0;
            for (int i = 0; i < tiles.length; ++i) {
                int v = tiles[i];
                if (v == 0)
                    continue;
                v = v - 1; // index in goal array
                final int goalRow = v % n;
                final int goalCol = v / n;

                final int currRow = i % n;
                final int currCol = i / n;

                final int cost = Math.abs(currRow - goalRow) + Math.abs(currCol - goalCol);
                mancost += cost;
            }
            manhattanCache = mancost;
        }
        return manhattanCache;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return Arrays.equals(tiles, goal);
    }

    // does this board equal y?
    public boolean equals(final Object y) {
        if (y == this)
            return true;
        if (y == null)
            return false;
        if (y.getClass() != this.getClass())
            return false;

        final Board that = (Board) y;
        return this.dimension() == that.dimension() && this.hamming() == that.hamming()
                && this.manhattan() == that.manhattan();
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int blankIndex = -1;
        for (int i = 0; i < tiles.length; i++)
            if (tiles[i] == 0) {
                blankIndex = i;
                break;
            }

        final int[] adj = findAdjacentIndex(blankIndex);
        final Stack<Board> neighbors = new Stack<>();
        for (final int index : adj) {
            final int[] copy = Arrays.copyOf(tiles, tiles.length);
            //StdOut.printf("index: %d", index);
            swapElements(copy, blankIndex,index);
            neighbors.push(new Board(resolve2D(copy, n)));
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return null;
    }

    // unit testing (not graded)
    public static void main(final String[] args) {
        final int[][] stateArr = new int[3][3];

        stateArr[0] = new int[] { 2, 1, 3 };
        stateArr[1] = new int[] { 5, 4, 0 };
        stateArr[2] = new int[] { 6, 7, 8 };

        final Board state = new Board(stateArr);
        stateArr[0] = new int[] { 1, 2, 3 };
        Board other = new Board(stateArr);
        StdOut.println(state.hamming());
        StdOut.println(state.manhattan());
        StdOut.print(state.toString());
        StdOut.println(state.equals(other));

        stateArr[0] = new int[] { 1, 2, 3 };
        stateArr[1] = new int[] { 4, 5, 6 };
        stateArr[2] = new int[] { 7, 8, 0 };
        other = new Board(stateArr);
        StdOut.println(other.isGoal());
        for (Board nBoard : other.neighbors())
            StdOut.println(nBoard.toString());

    }

}