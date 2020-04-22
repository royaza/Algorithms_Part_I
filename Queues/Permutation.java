import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {


        int no = Integer.parseInt(args[0]);


        RandomizedQueue<String> randomString = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            randomString.enqueue(StdIn.readString());
        }


        if (no <= randomString.size()) {
            for (int i = 0; i < no; i++) {


                StdOut.println(randomString.dequeue());
            }

        } else {
            throw new IllegalArgumentException("Enter a number less than or equal to " + randomString.size());
        }


    }
}
