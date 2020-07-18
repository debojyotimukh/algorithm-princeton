import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    Point[] points;
    int nSeg;
    
    /**
     * finds all line segments containing 4 or more points
     * 
     * @param points
     */
    public FastCollinearPoints(Point[] points) {
        this.points=points;
        nSeg=0;
    }

    /**
     * Find the number of line segments
     * 
     * @return
     */
    public int numberOfSegments() {
        return nSeg;
    }

    /**
     * the line segments
     * 
     * @return
     */
    public LineSegment[] segments() {
        return null;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
    
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
    
        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}