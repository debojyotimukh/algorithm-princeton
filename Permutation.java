import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(final String[] args) {
        final int k = Integer.parseInt(args[0]);
        if (k == 0)
            return;
        final RandomizedQueue<String> rq = new RandomizedQueue<>();
        // implement reservoir sampling for bonus point
        int count = 0;
        while (!StdIn.isEmpty()) {
            if (count > k)
                rq.dequeue();
            rq.enqueue(StdIn.readString());

            count++;
        }

        for (int i = 0; i < k; i++)
            StdOut.println(rq.dequeue());
    }

}