import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private final ArrayList<LineSegment> lineSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null || Arrays.asList(points).contains(null)) throw new IllegalArgumentException();
        int n = points.length;
        Point[] copy = points.clone();
        lineSegments = new ArrayList<>();

        if (n == 1) return;

        for (int p = 0; p < n; p++) {
            Arrays.sort(copy);
            if (p != 0 && copy[p].compareTo(copy[p - 1]) == 0) throw new IllegalArgumentException();
            Point cacheP = points[p];
            Arrays.sort(copy, cacheP.slopeOrder());
            int q = -1;
            int r = -1;
            double slope1 = cacheP.slopeTo(copy[1]);
            double slope2 = Double.NEGATIVE_INFINITY;

            for (int i = 2; i <= n; i++) {
                if (i != n) slope2 = cacheP.slopeTo(copy[i]);
                if (slope2 == slope1) {
                    if (q == -1) {
                        q = i - 1;
                        r = i;
                    } else r++;
                } else {
                    if (r - q + 2 >= 4 && cacheP.compareTo(copy[q]) < 0)
                        lineSegments.add(new LineSegment(cacheP, copy[r]));
                    q = -1;
                    r = -1;
                }
                slope1 = slope2;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
            StdDraw.show();
            StdDraw.pause(10);
        }
        StdOut.println(collinear.numberOfSegments());
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[0]);
    }
}

