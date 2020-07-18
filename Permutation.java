import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
//import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(final String[] args) {
        final int k = Integer.parseInt(args[0]);
        if (k == 0)
            return;
        final RandomizedQueue<String> rq = new RandomizedQueue<>();
        // implement reservoir sampling (Algorithm R)
        /*
         * int i = 1; while (!StdIn.isEmpty()) { final String in = StdIn.readString();
         * if (i <= k) rq.enqueue(in); else if (StdRandom.uniform(1, i + 1) <= k) {
         * rq.dequeue(); rq.enqueue(in); }
         * 
         * i++; }
         */
        while (!StdIn.isEmpty())
            rq.enqueue(StdIn.readString());

        for (int c = 0; c < k; c++)
            StdOut.println(rq.dequeue());
    }

}