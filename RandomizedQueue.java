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

    private void swap(Item a, Item b) {
        Item d = a;
        a = b;
        b = d;
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
    public void enqueue(Item item) {
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
        for (int i = count; i > 0; i--) {
            int j = StdRandom.uniform(0, i);
            swap(q[j], q[i]);
        }
        return q[--count];
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();
        Item d = q[StdRandom.uniform(count)];
        return d;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator(count);
    }

    private class ArrayIterator implements Iterator<Item> {
        int i = 0;
        int count = 0;

        ArrayIterator(int count) {
            this.count = count;
        }

        @Override
        public boolean hasNext() {
            return i < count;
        }

        @Override
        public Item next() {
            Item d = q[i++];
            if (d == null)
                throw new NoSuchElementException();
            return d;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();

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
        Iterator<Integer> it = rq.iterator();
        while (it.hasNext())
            StdOut.println(it.next());
    }

}