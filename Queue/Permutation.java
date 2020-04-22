import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {


    public static void main(String[] args) {


        int no = Integer.parseInt(args[0]);
        int k = 0;


        RandomizedQueue<String> randomString = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {

            String string = StdIn.readString();
            k++;
            if (k < no + 1) {
                randomString.enqueue(string);
            } else {
                if (StdRandom.uniform() < (double) no / k) {
                    randomString.dequeue();
                    randomString.enqueue(string);
                }
            }


        }

        for (String s : randomString)
            StdOut.println(s);

        // StdOut.println("Size: " + randomString.size());

        /* if (no <= randomString.size()) {
            for (int i = 0; i < no; i++) {


                StdOut.println(randomString.dequeue());


            }

        } else {
            throw new IllegalArgumentException("Enter a number less than or equal to " + randomString.size());
        }*/


    }
}
