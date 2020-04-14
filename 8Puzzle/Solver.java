import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    // find a solution to the initial board (using the A* algorithm)

    private boolean isSolvable;
    // private final ArrayList<Board> solutionBoardList = new ArrayList<>();
    private int minMoves;
    private final Stack<SearchNode> solutionStack = new Stack<>();
    private final Stack<Board> finalSolution = new Stack<>();
    private SearchNode searchNode;

    public Solver(Board initial) {


        // Board initialBoard = initial;

        if (initial == null) {
            throw new IllegalArgumentException("Null Point is not accepted");
        }

        //SearchNode searchNode;

        Board initialTween = initial.twin();

        searchNode = new SearchNode(initial, null, 0);

        SearchNode searchNodeTween;

        searchNodeTween = new SearchNode(initialTween, null, 0);

        MinPQ<SearchNode> pq = new MinPQ<>(); // MinPQ data type for the priority queue of the initial board
        pq.insert(searchNode);

        MinPQ<SearchNode> pqTween = new MinPQ<>(); // MinPQ data type for the priority queue of the tween board of the initial one
        pqTween.insert(searchNodeTween);

        searchNode = pq.delMin();
        searchNodeTween = pqTween.delMin();


        while (true) {

            Board curBoard = searchNode.getBoard();
            Board tweenCurBoard = searchNodeTween.getBoard();


            // this.solutionBoardList.add(curBoard);

            // this.solutionStack.push(curBoard);

            this.solutionStack.push(searchNode);

            if (curBoard.isGoal()) {
                this.isSolvable = true;
                this.minMoves = searchNode.moves;
                solutionBoardStack();
                break;
            }

            if (tweenCurBoard.isGoal()) //if the tween is resolvable, the main one is not resolvable
            {
                this.isSolvable = false;
                this.minMoves = -1;
                break;
            }

            for (Board searchNodeNeighbor : curBoard.neighbors()) {
                if (!searchNodeNeighbor.equals(searchNode.previousNode)) {
                    pq.insert(new SearchNode(searchNodeNeighbor, curBoard, searchNode.moves + 1)); // adding the neighbors to the pq
                }
            }
            searchNode = pq.delMin();

            for (Board searchNodeNeighborTeen : tweenCurBoard.neighbors()) {
                if (!searchNodeNeighborTeen.equals(searchNodeTween.previousNode)) {
                    pqTween.insert(new SearchNode(searchNodeNeighborTeen, tweenCurBoard, searchNodeTween.moves + 1)); // adding the neighbors to the pq tween
                }
            }
            searchNodeTween = pqTween.delMin();
        }
    }

    /*
    * How do I reconstruct the solution once I've dequeued the goal search node?
    * Since each search node records the previous search node to get there,
     * you can chase the pointers all the way back to the initial search node (and consider them in reverse order).

You just need to start from the first dequeued solved board,
 push it onto the stack, then do so for its parent board, and so on until you reach the root.
  Then solution() should return a stack which when you pop it, you get the root board first.
    *
    *
    * */

    private Stack<Board> solutionBoardStack() {


        SearchNode cur;
        SearchNode temp;

        cur = this.solutionStack.pop(); // Adding the last item of stack to the final solution, goal board
        this.finalSolution.push(cur.getBoard()); // add the previous item;

        while (!this.solutionStack.isEmpty()) {


            temp = this.solutionStack.pop();

            if (temp.getBoard() == cur.previousNode) {


                this.finalSolution.push(temp.getBoard());
                cur = temp;
            }


        }

        //finalSolution.push(cur.getBoard()); // initial board

        return this.finalSolution;
    }

    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final Board previousNode;
        private final int moves; // It is g(n) in the A* algorithm, the exact cast from the starting point until the current node
        private final int priority;

        SearchNode(Board board, Board previousNode, int moves) //Constructor of Search node
        {
            this.board = board;
            this.previousNode = previousNode;
            this.moves = moves;
            this.priority = moves + board.manhattan();
        }

        public int compareTo(SearchNode that) // comparator based on the priority
        {
            if (this.priority < that.priority) return -1;
            if (this.priority > that.priority) return +1;
            return 0;
        }

        public Board getBoard() {
            return board;
        }

    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return this.isSolvable;
    }

    // min number of moves to solve initial board
    public int moves() {
        return minMoves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {

        if (!isSolvable) {
            return null;
        }


        return this.finalSolution;
        // return this.solutionBoardList;
    }

    // test client
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output

        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println("solution 1 " + board);
            for (Board board : solver.solution())
                StdOut.println("solution 2 " + board);


        }

    }
}
