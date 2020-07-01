import java.lang.Object;
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
        front = 0;
        rear = -1;
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
            rearReached = oldIndex % dq.length == rear;
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
        return dq.length == sz;
    }

    // is the deque empty?
    public boolean isEmpty() {
        if (front > rear)
            return true;
        return false;
    }

    // return the number of items on the deque
    public int size() {
        return count;
    }

    // add the item to the front
    public void addFirst(final Item item) {
        //TODO: not working for more than one
        if (item == null)
            throw new IllegalArgumentException();
        //if (isFull())
            //resize();
        if (isEmpty())
            dq[++rear] = item;
        else if (front != 0)
            dq[--front] = item;

        count++;
    }

    // add the item to the back
    public void addLast(final Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        //if (isFull())
            //resize();
        rear = (rear + 1) % sz;
        dq[rear] = item;
        count++;

    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        final Item d = dq[front];
        front = (front + 1) % sz;
        count--;
        return d;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        final Item d = dq[rear];
        rear--;
        count--;
        if (isEmpty()) {
            front = 0;
            rear = -1;
        }
        return d;
    }

    private void display(){
        for(int i=front;i<=rear;i++)
            StdOut.println(dq[i]);
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new CircularArrayIterator(front, rear);
    }

    private class CircularArrayIterator implements Iterator<Item> {
        int f = 0;
        int r = 0;

        CircularArrayIterator(final int front, final int rear) {
            f = front;
            r = rear;
        }

        @Override
        public boolean hasNext() {
            return f > r;
        }

        @Override
        public Item next() {
            return dq[f++];
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
        q.addFirst(11);
        q.addFirst(8);
        q.addLast(45);
        q.addLast(5);
        
        /*final Iterator<Integer> it = q.iterator();
        while (!it.hasNext())
            StdOut.println(it.next());*/
        q.display();
        q.removeFirst();
        q.removeLast();
        q.display();

    }

}