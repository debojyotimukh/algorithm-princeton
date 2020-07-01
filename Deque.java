import java.lang.Object;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Item[] dq;
    private int sz;
    private int front;
    private int rear;

    // construct an empty deque
    public Deque() {
        dq = (Item[]) new Object[10];
        sz = 0;
        front=0;
        rear=-1;
    }

    // resize circular array
    private void resize() {
        Item[] aux = (Item[]) new Object[dq.length * 2];
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

    // is the deque empty?
    public boolean isEmpty() {
        if(front>rear)
            return true;
        return false;
    }

    // return the number of items on the deque
    public int size() {
        return sz;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        return null;

    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        return null;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return null;
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}