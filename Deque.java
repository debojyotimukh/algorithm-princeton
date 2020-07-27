import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private int count;

    private class Node {        
        Item value;
        Node prev;
        Node next;
        
        Node(final Item value) {
            this.value = value;
        }

    }

    private Node front;
    private Node rear;

    // construct an empty deque
    public Deque() {
        count = 0;
        front = null;
        rear = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return count == 0;
    }

    // return the number of items on the deque
    public int size() {
        return count;
    }

    // add the item to the front
    public void addFirst(final Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        final Node newnode = new Node(item);
        if (front == null) {
            front = newnode;
            rear = front;
        } else {
            newnode.next = front;
            front.prev = newnode;
            front = newnode;
        }
        count++;
    }

    // add the item to the back
    public void addLast(final Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        final Node newnode = new Node(item);
        if (rear == null) {
            rear = newnode;
            front = rear;
        } else {
            newnode.prev = rear;
            rear.next = newnode;
            rear = newnode;
        }
        count++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (count == 0)
            throw new NoSuchElementException();

        Item temp = front.value;
        front = front.next;
        if (front == null)
            rear = null;
        else
            front.prev = null;

        count--;
        return temp;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (count == 0)
            throw new NoSuchElementException();

        Item temp = rear.value;
        rear = rear.prev;
        if (rear == null)
            front = null;
        else
            rear.next = null;

       
        count--;
        return temp;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new LinkedListIterator(front);
    }

    private class LinkedListIterator implements Iterator<Item> {
        Node currNode;

        public LinkedListIterator(final Node firsNode) {
            currNode = firsNode;
        }

        @Override
        public boolean hasNext() {
            return currNode != null;
        }

        @Override
        public Item next() {
            if (currNode == null)
                throw new NoSuchElementException();
            final Item currValue = currNode.value;
            currNode = currNode.next;
            return currValue;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    // unit testing (required)
    public static void main(final String[] args) {
        Deque<Integer> dq = new Deque<>();
        dq.addFirst(12);
        dq.addLast(13);
        dq.addFirst(11);
        dq.addLast(14);
        dq.addFirst(10);
        dq.addLast(15);

        dq.removeFirst();
        dq.removeLast();

        Iterator<Integer> it = dq.iterator();
        while (it.hasNext())
            StdOut.println(it.next());
    }

}