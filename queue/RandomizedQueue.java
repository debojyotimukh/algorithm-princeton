package queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int sz;
    private Item[] q;
    private int count;

    // construct an empty randomized queue
    public RandomizedQueue() {
        sz = 4;
        q = (Item[]) new Object[sz];
        count = 0;
    }

    // resize array
    private void resize() {
        sz = q.length * 2;
        final Item[] aux = (Item[]) new Object[sz];
        for (int i = 0; i < count; i++)
            aux[i] = q[i];
        q = aux;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        if (count == 0)
            return true;
        return false;
    }

    // return the number of items on the randomized queue
    public int size() {
        return count;
    }

    // add the item
    public void enqueue(final Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        if (count == sz)
            resize();
        q[count++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();
        --count;
        // Fisher-Yates shuffle
        for (int i = count; i > 0; i--) {
            final int j = StdRandom.uniform(0, i);
            final Item d = q[j];
            q[j] = q[i];
            q[i] = d;
        }
        final Item d = q[count];
        q[count]=null;
        return d;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();
        final Item d = q[StdRandom.uniform(count)];
        return d;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator(count);
    }

    private class ArrayIterator implements Iterator<Item> {
        int current;
        int count;
        int[] indexArr;

        ArrayIterator(final int count) {
            current = 0;
            this.count = count;
            indexArr = new int[count];
            for (int i = 0; i < count; i++)
                indexArr[i] = i;
            StdRandom.shuffle(indexArr);
        }

        @Override
        public boolean hasNext() {
            return current < count;
        }

        @Override
        public Item next() {
            if (current >= count)
                throw new NoSuchElementException();
            return q[indexArr[current++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(final String[] args) {
        final RandomizedQueue<Integer> rq = new RandomizedQueue<>();

        rq.enqueue(5);
        rq.enqueue(8);
        rq.enqueue(67);
        rq.enqueue(65);
        rq.enqueue(78);
        rq.enqueue(2);
        StdOut.println("show");
        StdOut.println(rq.sample());
        rq.dequeue();
        rq.dequeue();
        StdOut.println("show");
        rq.dequeue();
        rq.enqueue(112);
        final Iterator<Integer> it = rq.iterator();
        while (it.hasNext())
            StdOut.println(it.next());

        // StdOut.println(it.next());

        final int n = 5;
        final RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);
        for (final int a : queue) {
            for (final int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }
    }

}