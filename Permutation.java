import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        if (k == 0) return;
        int count = 0;
        while (!StdIn.isEmpty()) {
            count++;
            String s = StdIn.readString();
            if (count <= k) randomizedQueue.enqueue(s);
            else {
                if (StdRandom.uniformInt(count) < k) {
                    randomizedQueue.dequeue();
                    randomizedQueue.enqueue(s);
                }
            }
        }
        for (String s: randomizedQueue) {
            StdOut.println(s);
        }
    }
}
