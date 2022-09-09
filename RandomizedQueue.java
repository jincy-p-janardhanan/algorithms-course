import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private final ArrayList<Item> randQueue;
    private int last;

    // construct an empty randomized queue
    public RandomizedQueue() {
        randQueue = new ArrayList<>();
        last = -1;
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();
        StdOut.printf("Empty: %b \nAdding 10 elements...\n", randomizedQueue.isEmpty());
        for (int i = 0; i < 10; i++) {
            randomizedQueue.enqueue(i);
            StdOut.printf("%d ", i);
        }
        StdOut.printf("\nEmpty: %b \nSample element: %d\n",
                randomizedQueue.isEmpty(), randomizedQueue.sample());
        StdOut.printf("Size: %d \nRandomized queue:\n", randomizedQueue.size());
        for (int i : randomizedQueue) {
            StdOut.printf("%d ", i);
        }
        StdOut.println("\ndeleting 5 elements...");
        for (int i = 0; i < 5; i++) {
            StdOut.printf("%d ", randomizedQueue.dequeue());
        }
        StdOut.println("\nAdding 6 elements...");
        for (int i = 0; i < 6; i++) {
            randomizedQueue.enqueue(i);
            StdOut.printf("%d ", i);
        }
        StdOut.printf("\nEmpty: %b \n", randomizedQueue.isEmpty());
        StdOut.printf("Size: %d \nRandomized queue: \n", randomizedQueue.size());
        for (int i : randomizedQueue) {
            StdOut.printf("%d ", i);
        }
        StdOut.println("\ndeleting all elements...");
        for (int i = 0; i < 11; i++) {
            StdOut.printf("%d ", randomizedQueue.dequeue());
        }
        StdOut.printf("\nEmpty: %b \n", randomizedQueue.isEmpty());
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return randQueue.size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return randQueue.size();
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        last = (last + 1) % (randQueue.size() + 1);
        randQueue.add(last, item);
    }

    // remove and return a random item
    public Item dequeue() {
        if (randQueue.size() == 0) throw new NoSuchElementException();
        int index = StdRandom.uniformInt(randQueue.size());
        Item item = randQueue.get(index);
        randQueue.remove(index);
        if (randQueue.size() != 0) last = (last - 1) % randQueue.size();
        else last = -1;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (randQueue.size() == 0) throw new NoSuchElementException();
        int index = StdRandom.uniformInt(randQueue.size());
        return randQueue.get(index);
    }

    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int count = 0;
        private final boolean[] visited = new boolean[randQueue.size()];

        @Override
        public boolean hasNext() {
            return count != randQueue.size();
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            int index;
            do {
                index = StdRandom.uniformInt(randQueue.size());
            } while (visited[index]);
            visited[index] = true;
            count++;
            return randQueue.get(index);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
