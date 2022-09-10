import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int length;

    // construct an empty randomized queue
    public RandomizedQueue() {
        first = null;
        last = null;
        length = 0;
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();
        StdOut.printf("Empty: %b \nAdding 10 elements...\n", randomizedQueue.isEmpty());
        for (int i = 0; i < 10; i++) {
            randomizedQueue.enqueue(i);
            StdOut.printf("%d ", i);
        }
        StdOut.printf("\nEmpty: %b \nSample element: %d\n", randomizedQueue.isEmpty(), randomizedQueue.sample());
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
        return length == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return length;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (length == 0) first = last;
        else oldLast.next = last;
        length++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (length == 0) throw new NoSuchElementException();
        Item item;
        int index;
        Node prevNode;
        if (length == 1) {
            item = first.item;
            first = null;
            last = null;
        } else {
            index = StdRandom.uniformInt(length);
            if (index == 0) {
                item = first.item;
                first = first.next;
            } else {
                prevNode = getNodeAtIndex(index - 1);
                item = prevNode.next.item;
                prevNode.next = prevNode.next.next;
                if (index == length - 1) last = prevNode;
            }
        }
        length--;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (length == 0) throw new NoSuchElementException();
        int index = StdRandom.uniformInt(length);
        return getNodeAtIndex(index).item;
    }

    private Node getNodeAtIndex(int index) {
        if (index == 0) return first;
        Node current = first;
        int count = 0;
        while (count < index - 1) {
            current = current.next;
            count++;
        }
        return (current.next);
    }

    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class Node {
        Item item;
        Node next;
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
            return getNodeAtIndex(shuffledIndices[i++]).item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
