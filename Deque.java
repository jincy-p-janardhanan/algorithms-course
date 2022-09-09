import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private final ArrayList<Item> deque;
    private int first;
    private int last;

    // construct an empty deque
    public Deque() {
        deque = new ArrayList<>();
        first = last = -1;
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
        return deque.size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return deque.size();
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (deque.size() == 0) first = last = 0;
        deque.add(first, item);
        last = (last + 1) % deque.size();
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (deque.size() == 0) first = 0;
        last = (last + 1) % (deque.size() + 1);
        deque.add(last, item);
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (deque.size() == 0) {
            throw new NoSuchElementException();
        }

        Item item = deque.get(first);
        deque.remove(first);
        if (deque.size() != 0) last = (last - 1) % deque.size();
        else first = last = -1;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (deque.size() == 0) {
            throw new NoSuchElementException();
        }

        Item item = deque.get(last);
        deque.remove(last);
        if (deque.size() != 0) last = (last - 1) % deque.size();
        else first = last = -1;
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        private int current = first, count = 0;

        @Override
        public boolean hasNext() {
            return count != deque.size();
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = deque.get(current);
            current = (current + 1) % deque.size();
            count++;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
