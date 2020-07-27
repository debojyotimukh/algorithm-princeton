import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    Point[] points;
    int nSeg;
    Stack<Point> beginStack=new Stack<>();
    Stack<Point> endStack=new Stack<>();

    /**
     * finds all line segments containing 4 points
     * 
     * @param points
     */
    public BruteCollinearPoints(Point[] points) {
        this.points = points;
        nSeg = 0;
    }

    /**
     * Finds the number of line segments
     * 
     * @return
     */
    public int numberOfSegments() {
        return nSeg;
    }

    /**
     * Check if three points are collinear
     * @param p1
     * @param p2
     * @param p3
     * @return
     */
    private boolean isCollinearTriplet(Point p1,Point p2,Point p3){
        return p1.slopeTo(p2)==p2.slopeTo(p3)&&p2.slopeTo(p3)==p3.slopeTo(p1);
    }

    /**
     * Resize the segments array
     * @param segments
     * @param size
     * @return
     */
    private LineSegment[] filterNull(LineSegment[] segments,int size){
        LineSegment[] aux=new LineSegment[size];
        for(int i=0;i<size;i++)
            aux[i]=segments[i];
        segments=aux;
        return aux;
    }

    private LineSegment[] findMaximalSegments(){
        if(endStack.size()==1){
            final LineSegment ls=new LineSegment(beginStack.pop(), endStack.pop());
            return new LineSegment[] {ls};
        }
        LineSegment[] segments=new LineSegment[nSeg];
        int count=0;
        Point b=beginStack.peek();
        while(endStack.size()>1){            
            Point e1=endStack.pop();
            Point e2=endStack.peek();
            if(isCollinearTriplet(b, e1, e2)){
                endStack.pop();
            }
            else {
                b=beginStack.pop();
            segments[count++]=new LineSegment(b, e1);
            }
        }
        nSeg=count;
        return segments;

    }
    /**
     * the line segments
     * 
     * @return
     */
    public LineSegment[] segments() {
        int n=points.length;
        if(n<4)
            return null;

        LineSegment[] segments=new LineSegment[20];
        Arrays.sort(points);
        for (int i = 0; i < n; i++) 
            for (int j = i+1; j < n; j++) 
                for (int k = j+1; k < n; k++) 
                    if(isCollinearTriplet(points[i], points[j], points[k])){
                        for(int c=k+1;c<n;c++){
                            if(isCollinearTriplet(points[i], points[k], points[c])){
                                Point[] collPoints={points[i],points[j],points[k],points[c]};
                                Arrays.sort(collPoints);
                                /*StdOut.println();
                                for(Point pt:collPoints)
                                    StdOut.print(pt.toString()+"--");
                                segments[nSeg++]=new LineSegment(collPoints[0], collPoints[3]);*/
                                beginStack.push(collPoints[0]);
                                endStack.push(collPoints[3]);
                                nSeg++;
                                break;
                            }
                        }
                    }
        segments=findMaximalSegments();
        return filterNull(segments,nSeg);
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
        /*StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();*/

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            //segment.draw();
        }
        //StdDraw.show();
    }
}