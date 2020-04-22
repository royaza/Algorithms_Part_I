import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int N;
    private Item[] s;


    // construct an empty randomized queue
    public RandomizedQueue() {

        final int capacity = 10;

        s = (Item[]) new Object[capacity];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {

        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {

        if (item == null) throw new IllegalArgumentException("null argument exception");

        if (N == s.length) resizeArray(2 * s.length);
        s[N++] = item;


    }

    // remove and return a random item
    public Item dequeue() {

        if (isEmpty()) throw new NoSuchElementException("The deque is empty.");

        if (N > 0 && N == s.length / 4) resizeArray(s.length / 2);


        int randomIndex = StdRandom.uniform(N); // a random number between 0 and N

        Item item;

        if (randomIndex == N - 1) {
            item = s[--N];
            s[N] = null;
        } else {
            item = s[randomIndex];
            s[randomIndex] = s[--N];
            s[N] = null;

        }

        return item;

    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("The deque is empty.");

        int randomIndex = StdRandom.uniform(N); // a random number between 0 and N

        return s[randomIndex];

    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {


        Item[] shuffleArray = shuffleArray();
        private int i = shuffleArray.length;


        @Override
        public boolean hasNext() {
            return i > 0;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("This operation is not supported");
        }

        @Override
        public Item next() {

            if (shuffleArray == null || i == 0) throw new NoSuchElementException("There is no element");

            return shuffleArray[--i];
        }
    }

    private Item[] shuffleArray() {

        Item[] copy = (Item[]) new Object[N];

        for (int i = 0; i < N; i++)
            copy[i] = s[i];

        StdRandom.shuffle(copy);

        return copy;
    }

    private void resizeArray(int value) {

        Item[] copy = (Item[]) new Object[value];

        for (int i = 0; i < N; i++)
            copy[i] = s[i];

        s = copy;

    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}
