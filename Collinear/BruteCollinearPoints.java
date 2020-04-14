import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class BruteCollinearPoints {


    private final LineSegment[] segmentsArray;


    public BruteCollinearPoints(Point[] points) // finds all line segments containing 4 points
    {

        List<LineSegment> segmentList = new ArrayList<>(); // It restore the start and end points of a line segment

        Point[] sortedPoints = points.clone();
        checkException(sortedPoints);

        Arrays.sort(sortedPoints);

        for (int i = 0; i < sortedPoints.length - 3; i++) {

            for (int j = i + 1; j < sortedPoints.length - 2; j++) {

                double slopeOne = sortedPoints[i].slopeTo(sortedPoints[j]);


                for (int k = j + 1; k < sortedPoints.length - 1; k++) {

                    double slopeTwo = sortedPoints[j].slopeTo(sortedPoints[k]);


                    int compareFirstSlopes = Double.compare(slopeOne, slopeTwo);


                    if (compareFirstSlopes == 0) {


                        int z = k + 1;

                        while (z < sortedPoints.length) {


                            double slopeThree = sortedPoints[k].slopeTo(sortedPoints[z++]);


                            int compareSecondSlopes = Double.compare(slopeTwo, slopeThree);

                            if (compareSecondSlopes == 0) {


                                Point[] tempPointList = new Point[4];
                                tempPointList[0] = sortedPoints[i];
                                tempPointList[1] = sortedPoints[j];
                                tempPointList[2] = sortedPoints[k];
                                tempPointList[3] = sortedPoints[z - 1];
                                Arrays.sort(tempPointList);
                                segmentList.add(new LineSegment(tempPointList[0], tempPointList[3]));

                            }
                        }
                    }


                }
            }

        }

        segmentsArray = segmentList.toArray(new LineSegment[segmentList.size()]);

    }

    public int numberOfSegments()   // the number of line segments
    {
        return segmentsArray.length;
    }

    public LineSegment[] segments() // the line segments
    {

        return Arrays.copyOf(segmentsArray, segmentsArray.length);


    }


    private void checkException(Point[] points) {

        for (int i = 0; i < points.length; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("Double point found");
            }
            if (points[i] == null) {
                throw new IllegalArgumentException("Null Point is not accepted");
            }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }


}
