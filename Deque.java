import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private Item[] dq;
    private int sz;
    private int front;
    private int rear;
    private int count;

    // construct an empty deque
    public Deque() {
        sz = 4;
        dq = (Item[]) new Object[sz];
        front = -1;
        rear = 0;
        count = 0;
    }

    // resize circular array
    private void resize() {
        sz = dq.length * 2;
        final Item[] aux = (Item[]) new Object[sz];
        int newIndex = 0;
        int oldIndex = front;

        boolean rearReached = false;

        while (!rearReached) {
            rearReached = oldIndex % (dq.length + 1) == rear;
            aux[newIndex] = dq[oldIndex % dq.length];

            newIndex++;
            oldIndex++;
        }
        front = 0;
        rear = dq.length - 1;
        dq = aux;

    }

    // is deque full?
    private boolean isFull() {
        return ((front == 0 && rear == sz - 1) || front == rear + 1);
    }

    // is the deque empty?
    public boolean isEmpty() {
        return front == -1;
    }

    // return the number of items on the deque
    public int size() {
        return count;
    }

    // add the item to the front
    public void addFirst(final Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (isFull())
            resize();
        if (isEmpty()) {
            front = 0;
            rear = 0;
        } else if (front == 0)
            front = sz - 1;
        else
            front--;

        dq[front] = item;
        count++;
    }

    // add the item to the back
    public void addLast(final Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (isFull())
            resize();
        if (isEmpty()) {
            front = 0;
            rear = 0;
        } // else if (rear == sz -1)
          // rear = 0;
        else
            rear++;

        dq[rear] = item;
        count++;

    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        final Item d = dq[front];
        dq[front] = null;
        if (front == rear) {
            front = -1;
            rear = 0;
        } else if (front == sz - 1)
            front = 0;
        else
            front++;

        count--;
        return d;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        final Item d = dq[rear];
        dq[rear] = null;
        if (front == rear) {
            front = -1;
            rear = 0;
        } else if (rear == 0)
            rear = sz - 1;
        else
            rear--;

        count--;
        return d;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new CircularArrayIterator(front, rear);
    }

    private class CircularArrayIterator implements Iterator<Item> {
        int f = -1;
        int r = 0;

        CircularArrayIterator(final int front, final int rear) {
            f = front;
            r = rear;
        }

        @Override
        public boolean hasNext() {
            return f % (dq.length + 1) != r;
        }

        @Override
        public Item next() {
            final Item d = dq[f % dq.length];
            if (d == null)
                throw new NoSuchElementException();
            ++f;
            return d;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(final String[] args) {
        final Deque<Integer> q = new Deque<>();

        q.addFirst(12);
        q.removeLast();
        q.addFirst(11);
        q.removeFirst();

        q.addFirst(8);
        q.addLast(45);
        q.addLast(5);
        q.addFirst(8);
        q.addFirst(1);
        q.addLast(2);
        q.addFirst(3);
        q.addFirst(4);
        q.addLast(6);
        q.addFirst(7);
        q.addLast(9);

        Iterator<Integer> it = q.iterator();
        while (it.hasNext())
            StdOut.println(it.next());

        StdOut.println("After removing");

        q.removeFirst();
        q.removeLast();
        q.removeLast();
        q.removeFirst();
        q.removeLast();

        it = q.iterator();
        while (it.hasNext())
            StdOut.println(it.next());

    }

}