import edu.princeton.cs.algs4.StdOut;

public class test {

    public static void main(String[] args) {


        int no = Integer.parseInt(args[0]); // Test type


        Deque<Integer> deque = new Deque<>();

        switch (no) {
            case 1:
                StdOut.println("deque.isEmpty(): " + deque.isEmpty());
                StdOut.println("deque.isEmpty(): " + deque.isEmpty());
                StdOut.println("deque.isEmpty(): " + deque.isEmpty());
                deque.addFirst(4);
                deque.addFirst(5);
                deque.removeFirst();
                deque.addFirst(7);
                deque.removeFirst();
                deque.removeFirst();
                break;

            case 2:
                StdOut.println("deque.isEmpty(): " + deque.isEmpty());
                deque.addFirst(2);
                StdOut.println("deque.isEmpty(): " + deque.isEmpty());
                deque.removeLast();
                break;

            case 3:
                deque.addFirst(1);
                deque.addFirst(2);
                deque.removeLast();
                StdOut.println("deque.isEmpty(): " + deque.isEmpty());
                StdOut.println("deque.isEmpty(): " + deque.isEmpty());
                deque.removeLast();
                break;

            case 4:
                deque.isEmpty();
                deque.addLast(2);
                deque.removeLast();
                break;
            case 5:
                StdOut.println("deque.isEmpty(): " + deque.isEmpty());
                StdOut.println("deque.isEmpty(): " + deque.isEmpty());
                StdOut.println("deque.isEmpty(): " + deque.isEmpty());
                StdOut.println("deque.isEmpty(): " + deque.isEmpty());
                deque.addLast(5);
                deque.removeFirst();
                break;
            case 6:
                deque.addFirst(1);
                deque.addLast(2);
                deque.removeFirst();
                deque.removeFirst();
                break;

        }


    }


}
