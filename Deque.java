import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item value;
        Node next;
        Node prev;

    }

    private Node front;
    private Node rear;
    private int count;

    Deque() {
        front = null;
        rear = front;
        count = 0;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    public void addFirst(final Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        final Node current = new Node();
        current.value = item;
        current.prev = null;
        if (count == 1)
            current.next = null;
        else
            current.next = front;
        front = current;
        if (count == 1)
            rear = front;
        count++;
    }

    public void addLast(final Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        final Node current = new Node();
        current.value = item;
        if (count == 1)
            current.prev = null;
        else
            current.prev = rear;
        current.next = null;
        rear = current;
        if (count == 1)
            front = rear;
        count++;
    }

    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        final Item temp = front.value;
        if (count == 1) {
            front = null;
            rear = front;
            count--;
            return temp;
        }
        final Node current = front.next;
        current.prev = null;
        front = current;
        count--;

        return temp;
    }

    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        final Item temp = rear.value;
        if (count == 1) {
            rear = null;
            front = rear;
            count--;
            return temp;
        }
        final Node current = rear.prev;
        current.next = null;
        rear = current;
        count--;

        return temp;
    }

    @Override
    public Iterator<Item> iterator() {
        return new LinkedListIterator(front);
    }

    private class LinkedListIterator implements Iterator<Item> {
        Node current;

        LinkedListIterator(final Node first) {
            current = first;
        }

        @Override
        public boolean hasNext() {
            return current.next != null;
        }

        @Override
        public Item next() {
            if (current.next == null)
                throw new NoSuchElementException();
            current = current.next;
            return current.value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    public static void main(final String[] args) {
        final Deque<Integer> q = new Deque<>();

        q.addFirst(12);
        //q.removeLast();
        q.addFirst(11);
        //q.removeFirst();

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

        /*q.removeFirst();
        q.removeLast();
        q.removeLast();
        q.removeFirst();
        q.removeLast();*/

        it = q.iterator();
        while (it.hasNext())
            StdOut.println(it.next());

    }

}