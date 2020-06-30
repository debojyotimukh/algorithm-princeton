import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int sz=0;

    // construct an empty randomized queue
    public RandomizedQueue(){

    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return false;
    }

    // return the number of items on the randomized queue
    public int size(){
        return sz;
    }

    // add the item
    public void enqueue(Item item){
        if(item==null) throw new IllegalArgumentException();
    }

    // remove and return a random item
    public Item dequeue(){
        if(isEmpty()) throw new NoSuchElementException();
        return null;
    }

    // return a random item (but do not remove it)
    public Item sample(){
        if(isEmpty()) throw new NoSuchElementException();
        return null;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return null;
    }

    // unit testing (required)
    public static void main(String[] args){

    }

}