import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] randQueue;
    private int length;
    private int capacity;
    private int first;
    private int last;

    // construct an empty randomized queue
    public RandomizedQueue() {
        randQueue = (Item[]) (new Object[0]);
        length = 0;
        capacity = 0;
        first = -1;
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
            randomizedQueue.enqueue(i+10);
            StdOut.printf("%d ", i+10);
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
        return length == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return length;
    }

    private void resize() {
        Item[] copy = (Item[]) (new Object[capacity]);
        int i;
        for (i = 0; i < length; i++) {
            copy[i] = randQueue[(i + first) % randQueue.length];
        }
        first = 0;
        last = i - 1;
        randQueue = copy;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (length + 1 >= capacity) {
            if (capacity == 0) capacity = 2;
            else capacity *= 2;
            resize();
        }
        last++;
        if (last == capacity) last = 0;
        randQueue[last] = item;
        length++;
        if (length == 1) first = last;
    }

    // remove and return a random item
    public Item dequeue() {
        if (length == 0) throw new NoSuchElementException();
        Item item;
        int index = (StdRandom.uniformInt(length) + first) % capacity;
        if (length == 1) {
            item = randQueue[first];
            randQueue[first] = null;
            first = -1;
            last = -1;
            length = 0;
            return item;
        }
        item = randQueue[index];
        randQueue[index] = randQueue[first];
        randQueue[first] = null;
        first++;
        if (first == capacity) first = 0;
        length--;
        if (length <= capacity/4) {
            capacity /= 2;
            resize();
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (length == 0) throw new NoSuchElementException();
        int index = (StdRandom.uniformInt(length) + first) % capacity;
        return randQueue[index];
    }

    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private final int[] shuffledIndices = StdRandom.permutation(length);
        private int i = 0;

        @Override
        public boolean hasNext() {
            return i < length;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return randQueue[(shuffledIndices[i++] + first) % capacity];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
