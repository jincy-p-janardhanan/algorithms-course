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
        if (points == null || Arrays.asList(points).contains(null)) throw new IllegalArgumentException();
        Point[] copy = points.clone(); // requirement: should not mutate constructor argument
        Arrays.sort(copy);
        int n = points.length;
        lineSegments = new ArrayList<>();

        boolean[] lineExists = new boolean[n * n];
        for (int p = 0; p < n; p++) {
            if (p != 0 && copy[p].compareTo(copy[p - 1]) == 0) throw new IllegalArgumentException();

            for (int q = 0; q < n; q++) {
                if (p == q) continue;
                if (lineExists[idx(p, q, n)]) continue;
                double slopePQ = copy[p].slopeTo(copy[q]);

                for (int r = 0; r < n; r++) {
                    if (p == r || q == r) continue;
                    if (lineExists[idx(p, r, n)] || lineExists[idx(q, r, n)]) continue;
                    double slopePR = copy[p].slopeTo(copy[r]);
                    if (slopePQ != slopePR) continue;

                    for (int s = 0; s < n; s++) {
                        if (p == s || q == s || r == s) continue;
                        if (lineExists[idx(p, s, n)] || lineExists[idx(q, s, n)] || lineExists[idx(r, s, n)]) continue;
                        double slopePS = copy[p].slopeTo(copy[s]);
                        if (slopePQ != slopePS) continue;
                        Point[] temp = {copy[p], copy[q], copy[r], copy[s]};
                        int[] indices = {p, q, r, s};

                        for (int i = 0; i < 4; i++) {
                            for (int j = i + 1; j < 4; j++) {
                                int cmp = temp[i].compareTo(temp[j]);
                                if (cmp > 0) {
                                    Point swap = temp[i];
                                    temp[i] = temp[j];
                                    temp[j] = swap;
                                    int idxSwap = indices[i];
                                    indices[i] = indices[j];
                                    indices[j] = idxSwap;
                                }
                            }
                        }

                        lineSegments.add(new LineSegment(temp[0], temp[3]));
                        for (int i = 0; i < 4; i++) {
                            int p1 = indices[i];
                            for (int j = i + 1; j < 4; j++) {
                                int p2 = indices[j];
                                lineExists[idx(p1, p2, n)] = true;
                                lineExists[idx(p2, p1, n)] = true;
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
        StdOut.println(collinear.numberOfSegments());
    }

    private int idx(int p, int q, int n) {
        return n * p + q;
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