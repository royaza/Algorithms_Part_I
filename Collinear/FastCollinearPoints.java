import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class FastCollinearPoints {


    private final LineSegment[] segmentsArray;


    public FastCollinearPoints(Point[] points)  // finds all line segments containing 4 or more points
    {

        checkNull(points);
        List<LineSegment> segmentList = new ArrayList<>(); // It restore the start and end points of a line segment

        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        CheckException(sortedPoints);


        for (int i = 0; i < sortedPoints.length; i++) {


            Point[] slopeArray = sortedPoints.clone();

            Point p = sortedPoints[i]; //pivot point
            Arrays.sort(slopeArray, p.slopeOrder());


            int n = slopeArray.length;
            int x = 1;

            while (x < n) {

                LinkedList<Point> sameSlopeList = new LinkedList<>(); // For storing the points which have the same slope with the pivot
                final double tempSlope = p.slopeTo(slopeArray[x]);

                do {
                    sameSlopeList.add(slopeArray[x++]);


                } while (x < n && Double.compare(tempSlope, p.slopeTo(slopeArray[x])) == 0);

                int sampleListSize = sameSlopeList.size();

                if (sampleListSize >= 3 && p.compareTo(sameSlopeList.peek()) < 0) { // Check for double points


                    segmentList.add(new LineSegment(p, sameSlopeList.removeLast()));

                }
            }


        }
        segmentsArray = segmentList.toArray(new LineSegment[segmentList.size()]);

    }


    public int numberOfSegments() // the number of line segments
    {
        return segmentsArray.length;
    }

    public LineSegment[] segments()  // the line segments
    {

        return Arrays.copyOf(segmentsArray, segmentsArray.length);

    }

    private void CheckException(Point[] points) {

        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("Double point found");
            }

        }
    }

    private void checkNull(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Null array is not accepted");
        }
        for (Point point : points) {
            if (point == null) throw new IllegalArgumentException("Null Point is not accepted");
        }
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

            if (points[i] == null) {
                throw new IllegalArgumentException("Null Point is not accepted");
            }
        }


        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("Null Point is not accepted");
            }
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
