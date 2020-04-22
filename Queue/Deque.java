import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node firstNode, lastNode;
    private int nSize;

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    // construct an empty deque
    public Deque() {


    }

    // is the deque empty?
    public boolean isEmpty() {

        return (firstNode == null && lastNode == null);

    }

    // return the number of items on the deque
    public int size() {
        return nSize;
    }

    // add the item to the front
    public void addFirst(Item item) {

        if (item == null) throw new IllegalArgumentException("null argument exception");

        Node oldFirst = firstNode;

        firstNode = new Node();
        firstNode.item = item;
        firstNode.previous = null;

        if (lastNode == null) {

            lastNode = firstNode;

        } else {

            firstNode.next = oldFirst;
            oldFirst.previous = firstNode;
        }

        nSize++;
    }

    // add the item to the back
    public void addLast(Item item) {

        if (item == null) throw new IllegalArgumentException("null argument exception");

        Node oldLast = lastNode;
        lastNode = new Node();
        lastNode.item = item;
        lastNode.next = null;


        if (firstNode == null) {
            firstNode = lastNode;
            lastNode.previous = null;

        } else {
            oldLast.next = lastNode;
            lastNode.previous = oldLast;
        }

        nSize++;
    }

    // remove and return the item from the front
    public Item removeFirst() { // If I remove from Left, it is like a stack

        if (isEmpty()) throw new NoSuchElementException("The deque is empty.");
        Item item = firstNode.item;

        firstNode = firstNode.next;
        if (!(firstNode == null)) {
            firstNode.previous = null;

        }

        if (nSize == 1) {
            lastNode = null;
            firstNode = null;
        }

        nSize--;

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() { // If I remove from the right, it is like a queue

        if (isEmpty()) throw new NoSuchElementException("The deque is empty.");
        Item item = lastNode.item;

        lastNode = lastNode.previous;

        if (!(lastNode == null))
            lastNode.next = null;

        if (nSize == 1) {
            lastNode = null;
            firstNode = null;
        }

        nSize--;

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {

        return new DequeIterator();

    }

    private class DequeIterator implements Iterator<Item> {

        private Node current = firstNode;

        public boolean hasNext() {

            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("This operation is not supported");
        }

        public Item next() {

            if (current == null) throw new NoSuchElementException("There is no element");

            Item item = current.item;
            current = current.next;

            return item;

        }
    }

    // unit testing (required)
    public static void main(String[] args) {

        // int n = 5;
      /*  In in = new In(args[0]);
        int n = in.readInt();

        StdOut.println("n: " + n);

        Deque<Integer> queue = new Deque<>();

        for (int i = 0; i < n; i++) {
            int input = in.readInt();
            queue.addFirst(input);
            queue.addLast(input);

        }


        for (int myDeque : queue)

            StdOut.println(myDeque);


        queue.removeFirst();
        queue.removeLast();

        for (int myDeque : queue)

            StdOut.println("After removing first and last: " + myDeque);*/


    }

}
