import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int length;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        length = 0;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> integerDeque = new Deque<>();
        StdOut.printf("Empty: %b \nAdding 10 elements...\n", integerDeque.isEmpty());
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) integerDeque.addFirst(i);
            else integerDeque.addLast(i);
            StdOut.printf("%d ", i);
        }
        StdOut.printf("\nEmpty: %b \n", integerDeque.isEmpty());
        StdOut.printf("Size: %d \nfirst to last: \n", integerDeque.size());
        for (int i : integerDeque) {
            StdOut.printf("%d ", i);
        }
        StdOut.println("\ndeleting 5 elements...");
        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0) StdOut.printf("%d ", integerDeque.removeFirst());
            else StdOut.printf("%d ", integerDeque.removeLast());
        }
        StdOut.println("\nAdding 6 elements...");
        for (int i = 0; i < 6; i++) {
            if (i % 3 == 0) integerDeque.addFirst(i);
            else integerDeque.addLast(i);
            StdOut.printf("%d ", i);
        }
        StdOut.printf("\nEmpty: %b \n", integerDeque.isEmpty());
        StdOut.printf("Size: %d \nfirst to last: \n", integerDeque.size());
        for (int i : integerDeque) {
            StdOut.printf("%d ", i);
        }
        StdOut.println("\ndeleting all elements...");
        for (int i = 0; i < 11; i++) {
            if (i % 2 == 0) StdOut.printf("%d ", integerDeque.removeFirst());
            else StdOut.printf("%d ", integerDeque.removeLast());
        }
        StdOut.printf("\nEmpty: %b \n", integerDeque.isEmpty());
    }

    // is the deque empty?
    public boolean isEmpty() {
        return length == 0;
    }

    // return the number of items on the deque
    public int size() {
        return length;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.prev = null;
        if (length == 0) last = first;
        else oldFirst.prev = first;
        length++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        if (length == 0) first = last;
        else oldLast.next = last;
        length++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (length == 0) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        first = first.next;
        if (first != null) first.prev = null;
        else last = null;
        length--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (length == 0) {
            throw new NoSuchElementException();
        }

        Item item = last.item;
        last = last.prev;
        if (last != null) last.next = null;
        else first = null;
        length--;
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    private class DequeIterator implements Iterator<Item> {

        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
