import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private final ArrayList<LineSegment> lineSegments;

    // finds all line segments containing 4 points
    // The input will be a set of more than 4 points,
    // and you need to check all possible subsets of 4 points
    // to see if they are collinear.
    public BruteCollinearPoints(Point[] points) {
        lineSegments = new ArrayList<>();
        int n = points.length;
        boolean[] lineExists = new boolean[n * n];
        for (int p = 0; p < n; p++) {
            for (int q = 0; q < n; q++) {
                if (p == q) continue;
                for (int r = 0; r < n; r++) {
                    if (p == r || q == r) continue;
                    for (int s = 0; s < n; s++) {
                        if (p == s || q == s || r == s) continue;
                        Point[] temp = {points[p], points[q], points[r], points[s]};
                        int[] indices = {p, q, r, s};
                        boolean checkLine = false;
                        for (int p1 : indices) {
                            for (int p2 : indices) {
                                if (p1 == p2) continue;
                                if (lineExists[n * p1 + p2]) {
                                    checkLine = true;
                                    break;
                                }
                            }
                            if (checkLine) break;
                        }
                        if (checkLine) continue;
                        Arrays.sort(temp);
                        if (temp[0].slopeTo(temp[1]) == temp[0].slopeTo(temp[2]) && temp[0].slopeTo(temp[1]) == temp[0].slopeTo(temp[3])) {
                            lineSegments.add(new LineSegment(temp[0], temp[3]));
                            for (int p1 : indices) {
                                for (int p2 : indices) {
                                    if (p1 == p2) continue;
                                    lineExists[n * p1 + p2] = true;
                                }
                            }
                        }
                    }
                }
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
            StdDraw.show();
            StdDraw.pause(100);
        }

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