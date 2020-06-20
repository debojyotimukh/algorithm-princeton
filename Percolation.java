import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static int BLOCKED=0;
    private static int OPEN=1;
    private static int N;
    WeightedQuickUnionUF grid;
    private int openSitesCount=0;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        if(n<=0)
            throw new IllegalArgumentException();
        else{
            N=n;
            grid=new WeightedQuickUnionUF(n*n);
        }
 
    }

    private boolean isValid(int row,int col){
        if(row<1||col<1||row>=N||col>=N)
            return false;
        return true;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        if(!isOpen(row, col)){

        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        if(!isValid(row, col))
            throw new IllegalArgumentException();
        else{
            
        }
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        if(grid[row-1][col-1]==0)
            return true;
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return openSitesCount;
    }

    // does the system percolate?
    public boolean percolates(){}

    // test client (optional)
    public static void main(String[] args){

    }
}