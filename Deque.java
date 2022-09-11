import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Item[] deque;
    private int length;
    private int capacity;
    private int first;
    private int last;

    // construct an empty deque
    public Deque() {
        deque = (Item[]) (new Object[0]);
        length = 0;
        capacity = 0;
        first = -1;
        last = -1;
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

    private void resize() {
        Item[] copy = (Item[]) (new Object[capacity]);
        int count = 0;
        int i = first;
        int j = capacity - length + 1;
        if (copy.length < deque.length) {
            j--;
            length++;
        }
        first = j;
        while (count < length - 1) {
            copy[j++] = deque[i++];
            if (i == deque.length) i = 0;
            if (j == copy.length) j = 0;
            count++;
        }
        if (copy.length < deque.length) length--;
        last = j - 1;
        if (last == -1) last = capacity - 1;
        deque = copy;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        length++;
        if (length >= capacity) {
            if (capacity == 0) capacity = 2;
            else capacity *= 2;
            resize();
        }
        if (first == 0 && length != capacity) {
            if (length == 3) {
                deque[capacity - 1] = deque[last];
                last = capacity - 1;
            }
            deque[last - 1] = deque[first];
            first = last - 1;
        }
        deque[--first] = item;
        if (length == 1) last = first;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        length++;
        if (length >= capacity) {
            if (capacity == 0) capacity = 2;
            else capacity *= 2;
            resize();
        }
        last++;
        if (last == capacity) last = 0;
        deque[last] = item;
        if (length == 1) first = last;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (length == 0) {
            throw new NoSuchElementException();
        }
        Item item = deque[first];
        deque[first++] = null;
        if (first == capacity) first = 0;
        length--;
        if (length == capacity / 4) {
            capacity /= 2;
            resize();
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (length == 0) {
            throw new NoSuchElementException();
        }

        Item item = deque[last];
        deque[last--] = null;
        if (last == -1) last = capacity - 1;
        length--;
        if (length == capacity / 4) {
            capacity /= 2;
            resize();
        }
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        private int current = first;

        @Override
        public boolean hasNext() {
            if (length == 0) return false;
            return deque[current] != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = deque[current];
            current = current + 1;
            if (current == capacity) current = 0;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
