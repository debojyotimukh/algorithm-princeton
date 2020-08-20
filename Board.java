import java.util.Arrays;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private final int n;
    private final int[] tiles;
    private int manhattanCache = -1;
    private int hammingCache = -1;
    private int blankIndex = -1;
    private int twinIndex1;
    private int twinIndex2;

    /**
     * create a board from an n-by-n array of tiles, where tiles[row][col] = tile at
     * (row, col)
     * 
     * @param initial 2D array
     */
    public Board(final int[][] initial) {
        assert initial!=null;
        n = initial[0].length;
        assert n >= 2 && n < 128;

        tiles = flatten2DArray(initial);

        // find position of blank tile
        for (int i = 0; i < tiles.length; i++)
            if (tiles[i] == 0) {
                blankIndex = i;
                break;
            }

        // find twin indeces
        twinIndex1 = StdRandom.uniform(0, n * n);
        if (twinIndex1 == blankIndex)
            twinIndex1 = StdRandom.uniform(0, n * n);
        twinIndex2 = StdRandom.uniform(0, n * n);
        if (twinIndex2 == blankIndex)
            twinIndex2 = StdRandom.uniform(0, n * n);
        if (twinIndex1 == twinIndex2)
            twinIndex1 = StdRandom.uniform(0, n * n);
    }

    /**
     * find 1D 0-based index from row,col
     * 
     * @param row
     * @param col
     * @return
     */
    private int findIndex(final int row, final int col) {
        assert row > 0 && col > 0;
        return (row - 1) * n + (col - 1);
    }

    /**
     * 
     * @param index 0-based index
     * @return Row number (1 based)
     */
    private int getRow(final int index) {
        return index / n + 1;
    }

    /**
     * 
     * @param index 0-based index
     * @return Column number (1 based)
     */
    private int getCol(final int index) {
        return index % n + 1;
    }

    /**
     * convert a 2D array to 1D array
     * 
     * @param initial
     * @return
     */
    private int[] flatten2DArray(final int[][] initial) {
        final int n = initial[0].length;
        final int[] array1D = new int[n * n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                array1D[n * i + j] = initial[i][j];

        return array1D;
    }

    /**
     * Build 2D array from 1D array
     * 
     * @param arr     1D array
     * @param rowSize Dimension of row/column
     * @return 2D array
     */
    private int[][] resolve2D(final int[] arr, final int rowSize) {
        final int[][] array2D = new int[rowSize][rowSize];
        for (int i = 0; i < rowSize; i++)
            for (int j = 0; j < rowSize; j++)
                array2D[i][j] = arr[rowSize * i + j];
        return array2D;
    }

    /**
     * Find adjacent indeces of the the blank tile
     * 
     * @param index index of blank tile
     * @return An integer array containing the adjacent indeces
     */
    private int[] findAdjacentIndex(final int index) {
        assert index >= 0 && index < n * n;
        final int row = getRow(index);
        final int col = getCol(index);
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

    /**
     * Swap elements of an array
     * 
     * @param q Array
     * @param i 1st index
     * @param j 2nd index
     */
    private void swap(final int[] q, final int i, final int j) {
        final int temp = q[i];
        q[i] = q[j];
        q[j] = temp;
    }

    // string representation of this board
    @Override
    public String toString() {
        final StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                s.append(String.format("%2d ", tiles[n * i + j]));

            s.append("\n");
        }
        return s.toString();
    }

    /**
     * find board dimension
     * 
     * @return board dimension
     */
    public int dimension() {
        return n;
    }

    /**
     * Computes number of tiles out of place
     * 
     * @return Hamming distance of the board
     */
    public int hamming() {
        if (hammingCache != -1)
            return hammingCache;
        int hamming = 0;
        if (!isGoal()) {
            for (int i = 0; i < tiles.length; i++) {
                if (tiles[i] == 0)
                    continue;
                if (tiles[i] != i + 1)
                    hamming++;
            }
        }
        hammingCache = hamming;
        return hammingCache;
    }

    /**
     * Computes sum of Manhattan distances between tiles and goal
     * 
     * @return Sum of Manhatten distances
     */
    public int manhattan() {
        if (manhattanCache != -1)
            return manhattanCache;
        int mancost = 0;
        for (int i = 0; i < tiles.length; ++i) {
            int v = tiles[i];
            if (v == 0)
                continue;
            v = v - 1; // index in goal array

            final int cost = Math.abs(getRow(i) - getRow(v)) + Math.abs(getCol(i) - getCol(v));
            mancost += cost;
        }
        manhattanCache = mancost;

        return manhattanCache;
    }

    /**
     * is this board the goal board?
     * 
     * @return true if goal board
     */
    public boolean isGoal() {
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == 0)
                continue;
            if (tiles[i] != i + 1)
                return false;
        }
        return true;
    }

    // does this board equal y?
    @Override
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

    /**
     * Possible board combination after moving the tiles. Depending on the location
     * of the blank square, a board can have 2, 3, or 4 neighbors.
     * 
     * @return An iterable containing the neighbors of the board.
     */
    public Iterable<Board> neighbors() {

        final int[] adj = findAdjacentIndex(blankIndex);
        final Stack<Board> neighbors = new Stack<>();
        for (final int index : adj) {
            final int[] copy = Arrays.copyOf(tiles, tiles.length);
            swap(copy, blankIndex, index);
            neighbors.push(new Board(resolve2D(copy, n)));
        }

        return neighbors;
    }

    /**
     * A board that is obtained by exchanging any pair of tiles. Unique for a given
     * board
     * 
     * @return Twin board
     */
    public Board twin() {
        final int[] copy = Arrays.copyOf(tiles, tiles.length);
        swap(copy, twinIndex1, twinIndex2);
        return new Board(resolve2D(copy, n));
    }

    // unit testing (not graded)
    public static void main(final String[] args) {
        final int[][] stateArr = new int[3][3];

        stateArr[0] = new int[] { 8, 1, 3 };
        stateArr[1] = new int[] { 4, 0, 2 };
        stateArr[2] = new int[] { 7, 6, 5 };

        final Board state = new Board(stateArr);
        stateArr[0] = new int[] { 1, 2, 3 };
        Board other = new Board(stateArr);
        StdOut.println(state.hamming());
        StdOut.println(state.manhattan());
        StdOut.print(state.toString());
        StdOut.println(state.equals(other));
        StdOut.println(state.isGoal());

        stateArr[0] = new int[] { 1, 2, 3 };
        stateArr[1] = new int[] { 4, 5, 6 };
        stateArr[2] = new int[] { 7, 8, 0 };
        other = new Board(stateArr);
        StdOut.println(other.isGoal());
        StdOut.println("NEIGHBORS");
        for (final Board nBoard : state.neighbors())
            StdOut.println(nBoard.toString());

        StdOut.println("TWINS");
        StdOut.println(state.twin().toString());
        StdOut.println(state.twin().toString());

    }

}