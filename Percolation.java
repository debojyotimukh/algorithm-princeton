import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private static int BLOCKED=0;
    private static int OPEN=1;
    private static int N;
    private WeightedQuickUnionUF grid;
    private int[] gridStatus;
    private int openSitesCount=0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        if(n<=0)
            throw new IllegalArgumentException();
        else{
            N=n;
            grid=new WeightedQuickUnionUF(n*n+2);
            gridStatus=new int[n*n];
            for(int i=0;i<n;i++)
                for(int j=0;j<n;j++)
                    gridStatus[N*i+j]=BLOCKED;
        }
 
    }

    //find 1D index from row,col
    private int findIndex(int row,int col){
        return (row-1)*N+(col-1);
    }

    private boolean isValid(int row,int col){
        if(row<1||col<1||row>N||col>N)
            return false;
        return true;
    }

    //find indexes adjacent to an open site
    private int[] findAdjacentIndex(int row,int col){
        //TODO: merge if statements
        if(!isValid(row, col))
            throw new IllegalArgumentException();

        int top=findIndex(row-1, col);
        int bottom=findIndex(row+1, col);
        int right=findIndex(row, col+1);
        int left=findIndex(row, col-1);

        //Corner case (2)
        if(row==1&&col==1)//top-left
            return new int[] {right,bottom};        
        if(row==1&&col==N)//top-right
            return new int[] {left,bottom};        
        if(row==N&&col==1)//bottom-left
            return new int[] {top,right};        
        if(row==N&&col==N)//bottom-right
            return new int[] {top,left};

        //Side cases (3)
        if(row==1)//top
            return new int[] {right,left,bottom};
        if(row==N)//bottom
            return new int[] {right,left,top};
        if(col==1)//left
            return new int[] {right,top,bottom};
        if(col==N)//right
            return new int[] {left,top,bottom};

        //Default (4)
        return new int[] {right,left,top,bottom};
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        if(!isOpen(row, col)){
            gridStatus[findIndex(row,col)]=OPEN;
            if(openSitesCount==0) openSitesCount++;
            int[] adj=findAdjacentIndex(row, col);
            for(int i=0;i<adj.length;i++){                
                if(gridStatus[adj[i]]==OPEN){
                    grid.union(findIndex(row, col),adj[i]);
                    openSitesCount++;
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        if(!isValid(row, col))
            throw new IllegalArgumentException();
        if(gridStatus[findIndex(row,col)]==OPEN)
            return true;
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        if(!isValid(row, col))
            throw new IllegalArgumentException();
        if(gridStatus[findIndex(row,col)]==BLOCKED)
            return true;
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return openSitesCount;
    }

    // does the system percolate?
    public boolean percolates(){
        int virtualTop=N*N;
        int virtualBottom=N*N+1;
        for(int i=0;i<N;i++){
            grid.union(virtualTop, findIndex(1, i+1));
        }
        for(int i=0;i<N;i++){
            grid.union(virtualBottom, findIndex(N, i+1));
        }
        return grid.find(virtualBottom)==grid.find(virtualTop);
    }

    //print the grid
    private void print(){
        //TODO: use only StdOut
        for(int i=0;i<N;i++){
            System.out.println(" ");
            for(int j=0;j<N;j++)
                    System.out.print(gridStatus[N*i+j]+" ");            
        }
        System.out.println("");                
    }

    // test client (optional)
    public static void main(String[] args){
        Percolation p=new Percolation(20);
        
        p.open(1, 1);
        p.open(2, 2);
        p.open(1, 2);
        p.open(1, 3);
        //p.open(1, 4);
        p.open(3, 2);
        p.open(4, 2);
        p.print();
        System.out.println(p.percolates()?"YES":"NO");
        System.out.println(p.numberOfOpenSites());
        
    }
}