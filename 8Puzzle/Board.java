import edu.princeton.cs.algs4.Stack;

import java.util.Arrays;


public class Board {

    private final int[][] myTiles;
    private final int n;

    // Create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)

    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.myTiles = deepCopy(tiles); // tiles.clone();
    }

    private int[][] goalBoard() {

        int[][] goalBord = new int[this.n][this.n];

        int num = 1;

        outerLoop:
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if ((i == this.n - 1) && (j == this.n - 1)) {
                    goalBord[this.n - 1][this.n - 1] = 0;
                    break outerLoop;
                }
                goalBord[i][j] = num++;
            }
        }

        return goalBord;
    }

    // string representation of this board

    public String toString() {

        StringBuilder stringBoard = new StringBuilder(n);

        stringBoard.append(n).append("\n");

        for (int i = 0; i < n; i++) {

            for (int j = 0; j < n; j++) {

                if (j == n - 1) {

                    stringBoard.append(String.valueOf(myTiles[i][j]));

                } else {

                    stringBoard.append(String.valueOf(myTiles[i][j])).append(" ");
                }
            }

            stringBoard.append("\n");
        }
        return stringBoard.toString();
    }

    // board dimension n

    public int dimension() {

        return myTiles.length;
    }


    // Hamming and Manhattan distances. To measure how close a board is to the goal board, we define two notions of distance.
    // The Hamming distance between a board and the goal board is the number of tiles in the wrong position.

    // number of tiles out of place

    public int hamming() {

        int[][] myGoalBoard = goalBoard();
        int numWrongPosition = 0;

        outerLoop:
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (myGoalBoard[i][j] == 0) break outerLoop;
                if (this.myTiles[i][j] != myGoalBoard[i][j]) {
                    numWrongPosition += 1;
                }
            }
        }

        return numWrongPosition;
    }

    // sum of Manhattan distances between tiles and goal

    // The Manhattan distance between a board and the goal board is the sum of the Manhattan distances
    // (sum of the vertical and horizontal distance) from the tiles to their goal positions.

    public int manhattan() {

        int[][] myGoalBoard = goalBoard();
        int manhattanDistance = 0;

        outerLoop:
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (myGoalBoard[i][j] == 0) break outerLoop;

                if (!(myTiles[i][j] == myGoalBoard[i][j])) {

                    int[] index = findIndex(myGoalBoard[i][j], myTiles);

                    manhattanDistance += Math.abs(index[0] - i) + Math.abs(index[1] - j);
                }
            }
        }

        return manhattanDistance;
    }

    private int[] findIndex(int goalValue, int[][] mytiles) {

        int[] tileIndexes = new int[2];

        outerLoop:
        for (int i = 0; i < mytiles.length; i++) {
            for (int j = 0; j < mytiles.length; j++) {
                {
                    if (mytiles[i][j] == goalValue) {

                        tileIndexes[0] = i;
                        tileIndexes[1] = j;

                        break outerLoop;
                    }
                }
            }
        }
        return tileIndexes;
    }

    // is this board the goal board?

    public boolean isGoal() {
        return equals(new Board(goalBoard()));
    }


    // does this board equal y?

    public boolean equals(Object y) {


        if (!(y instanceof Board)) return false; // !(y instanceof Board) (!(y.getClass() == this.getClass()))

        Board board = (Board) y;

        return Arrays.deepEquals(board.myTiles, this.myTiles);
    }


    private int[][] deepCopy(int[][] myArray) {
        int[][] copyArray = new int[myArray.length][myArray.length];
        for (int x = 0; x < myArray.length; x++) {
            for (int y = 0; y < myArray.length; y++) {
                {
                    copyArray[x][y] = myArray[x][y];
                }
            }
        }
        return copyArray;
    }


    // all neighboring boards
    // Neighboring boards.  The neighbors() method returns an iterable containing the neighbors of the board.
    // Depending on the location of the blank square, a board can have 2, 3, or 4 neighbors.

    public Iterable<Board> neighbors() {

        // ArrayList<Board> boardList = new ArrayList<>();
        Stack<Board> boardStack = new Stack<>();

        int tempValue;

        outerLoop:
        for (int i = 0; i < this.myTiles.length; i++) {
            for (int j = 0; j < this.myTiles.length; j++) {

                if (this.myTiles[i][j] == 0) {

                    if (i == 0 && j == 0) { // if the empty block is on the up-left corner
                        int[][] neighbor1 = deepCopy(this.myTiles);
                        tempValue = neighbor1[i + 1][j];
                        neighbor1[i][j] = tempValue;
                        neighbor1[i + 1][j] = 0;
                        boardStack.push(new Board(neighbor1));
                        // boardList.add(new Board(neighbor1)); // add the first neighbor - move the below row tile to the empty block on top


                        int[][] neighbor2;
                        neighbor2 = deepCopy(this.myTiles);
                        tempValue = neighbor2[i][j + 1];
                        neighbor2[i][j] = tempValue;
                        neighbor2[i][j + 1] = 0;
                        boardStack.push(new Board(neighbor2));
                        // boardList.add(new Board(neighbor2)); // add the second neighbor - move the left column tile to the empty block on the right

                        break outerLoop;
                    }

                    if (i == 0 && j == this.myTiles.length - 1) { // if the empty block is on the up-right corner
                        int[][] neighbor1 = deepCopy(this.myTiles);
                        tempValue = neighbor1[i + 1][j];
                        neighbor1[i][j] = tempValue;
                        neighbor1[i + 1][j] = 0;
                        boardStack.push(new Board(neighbor1));
                        // boardList.add(new Board(neighbor1)); // add the first neighbor - move the below row tile to the empty block on top


                        int[][] neighbor2 = deepCopy(this.myTiles);
                        tempValue = neighbor2[i][j - 1];
                        neighbor2[i][j] = tempValue;
                        neighbor2[i][j - 1] = 0;
                        boardStack.push(new Board(neighbor2));
                        // boardList.add(new Board(neighbor2)); // add the second neighbor - move the right column tile to the empty block on the left

                        break outerLoop;
                    }

                    if (i == this.myTiles.length - 1 && j == 0) { // if the empty block is on the left-down corner
                        int[][] neighbor1 = deepCopy(this.myTiles);
                        tempValue = neighbor1[i - 1][j];
                        neighbor1[i][j] = tempValue;
                        neighbor1[i - 1][j] = 0;
                        boardStack.push(new Board(neighbor1));
                        // boardList.add(new Board(neighbor1)); // add the first neighbor - move the up row tile to the empty block on down


                        int[][] neighbor2 = deepCopy(this.myTiles);
                        tempValue = neighbor2[i][j + 1];
                        neighbor2[i][j] = tempValue;
                        neighbor2[i][j + 1] = 0;
                        boardStack.push(new Board(neighbor2));
                        // boardList.add(new Board(neighbor2)); // add the second neighbor - move the right column tile to the empty block on the left

                        break outerLoop;
                    }

                    if (i == this.myTiles.length - 1 && j == this.myTiles.length - 1) { // if the empty block is on the right-down corner
                        int[][] neighbor1 = deepCopy(this.myTiles);
                        tempValue = neighbor1[i - 1][j];
                        neighbor1[i][j] = tempValue;
                        neighbor1[i - 1][j] = 0;
                        boardStack.push(new Board(neighbor1));
                        // boardList.add(new Board(neighbor1)); // add the first neighbor - move the up row tile to the empty block on down


                        int[][] neighbor2 = deepCopy(this.myTiles);
                        tempValue = neighbor2[i][j - 1];
                        neighbor2[i][j] = tempValue;
                        neighbor2[i][j - 1] = 0;
                        boardStack.push(new Board(neighbor2));
                        // boardList.add(new Board(neighbor2)); // add the second neighbor - move the left column tile to the empty block on the right

                        break outerLoop;
                    }

                    if (i == 0) { // the first row tiles (there is no i-1)
                        int[][] neighbor1 = deepCopy(this.myTiles);
                        tempValue = neighbor1[i + 1][j];
                        neighbor1[i][j] = tempValue;
                        neighbor1[i + 1][j] = 0;
                        boardStack.push(new Board(neighbor1));
                        // boardList.add(new Board(neighbor1)); // add the  first neighbor- move the down row tile to up


                        int[][] neighbor2 = deepCopy(this.myTiles);
                        tempValue = neighbor2[i][j - 1];
                        neighbor2[i][j] = tempValue;
                        neighbor2[i][j - 1] = 0;
                        boardStack.push(new Board(neighbor2));
                        // boardList.add(new Board(neighbor2)); // add the second neighbor - move the right column tile to the left


                        int[][] neighbor3 = deepCopy(this.myTiles);
                        tempValue = neighbor3[i][j + 1];
                        neighbor3[i][j] = tempValue;
                        neighbor3[i][j + 1] = 0;
                        boardStack.push(new Board(neighbor3));
                        // boardList.add(new Board(neighbor3)); // add the third neighbor - move the left column tile to the right


                        break outerLoop;
                    }

                    if (i == this.myTiles.length - 1) //the last row (there is no i+1)
                    {
                        int[][] neighbor1 = deepCopy(this.myTiles);
                        tempValue = neighbor1[i - 1][j];
                        neighbor1[i][j] = tempValue;
                        neighbor1[i - 1][j] = 0;
                        boardStack.push(new Board(neighbor1));
                        // boardList.add(new Board(neighbor1)); // add the  first neighbor- move the up row tile to down


                        int[][] neighbor2 = deepCopy(this.myTiles);
                        tempValue = neighbor2[i][j - 1];
                        neighbor2[i][j] = tempValue;
                        neighbor2[i][j - 1] = 0;
                        boardStack.push(new Board(neighbor2));
                        // boardList.add(new Board(neighbor2)); // add the second neighbor - move the right column tile to the left

                        int[][] neighbor3 = deepCopy(this.myTiles);
                        tempValue = neighbor3[i][j + 1];
                        neighbor3[i][j] = tempValue;
                        neighbor3[i][j + 1] = 0;
                        boardStack.push(new Board(neighbor3));
                        // boardList.add(new Board(neighbor3)); // add the third neighbor - move the left column tile to the right


                        break outerLoop;
                    }
                    if (j == 0) // the first column (there is no j-1)
                    {
                        int[][] neighbor1 = deepCopy(this.myTiles);
                        tempValue = neighbor1[i][j + 1];
                        neighbor1[i][j] = tempValue;
                        neighbor1[i][j + 1] = 0;
                        boardStack.push(new Board(neighbor1));
                        // boardList.add(new Board(neighbor1)); // add the  first neighbor- move the right column tile to left


                        int[][] neighbor2 = deepCopy(this.myTiles);
                        tempValue = neighbor2[i - 1][j];
                        neighbor2[i][j] = tempValue;
                        neighbor2[i - 1][j] = 0;
                        boardStack.push(new Board(neighbor2));
                        // boardList.add(new Board(neighbor2)); // add the second neighbor - move the up row tile to the down


                        int[][] neighbor3 = deepCopy(this.myTiles);
                        tempValue = neighbor3[i + 1][j];
                        neighbor3[i][j] = tempValue;
                        neighbor3[i + 1][j] = 0;
                        boardStack.push(new Board(neighbor3));
                        // boardList.add(new Board(neighbor3)); // add the third neighbor - move the down row tile to the up


                        break outerLoop;
                    }

                    if (j == this.myTiles.length - 1)  // the last column (there is no j+11)
                    {
                        int[][] neighbor1 = deepCopy(this.myTiles);
                        tempValue = neighbor1[i][j - 1];
                        neighbor1[i][j] = tempValue;
                        neighbor1[i][j - 1] = 0;
                        boardStack.push(new Board(neighbor1));
                        // boardList.add(new Board(neighbor1)); // add the  first neighbor- move the left column tile to right

                        int[][] neighbor2 = deepCopy(this.myTiles);
                        tempValue = neighbor2[i - 1][j];
                        neighbor2[i][j] = tempValue;
                        neighbor2[i - 1][j] = 0;
                        boardStack.push(new Board(neighbor2));
                        // boardList.add(new Board(neighbor2)); // add the second neighbor - move the down up tile to the down

                        int[][] neighbor3 = deepCopy(this.myTiles);
                        tempValue = neighbor3[i + 1][j];
                        neighbor3[i][j] = tempValue;
                        neighbor3[i + 1][j] = 0;
                        boardStack.push(new Board(neighbor3));
                        // boardList.add(new Board(neighbor3)); // add the third neighbor - move the down row tile to the up

                        break outerLoop;

                    } else // the tile is in the middle and it has for neighbors
                    {
                        int[][] neighbor1 = deepCopy(this.myTiles);
                        tempValue = neighbor1[i][j + 1];
                        neighbor1[i][j] = tempValue;
                        neighbor1[i][j + 1] = 0;
                        boardStack.push(new Board(neighbor1));
                        // boardList.add(new Board(neighbor1)); // add the  first neighbor- move the right column tile to left

                        int[][] neighbor2 = deepCopy(this.myTiles);
                        tempValue = neighbor2[i][j - 1];
                        neighbor2[i][j] = tempValue;
                        neighbor2[i][j - 1] = 0;
                        boardStack.push(new Board(neighbor2));
                        // boardList.add(new Board(neighbor2)); // add the  second neighbor- move the left column tile to right

                        int[][] neighbor3 = deepCopy(this.myTiles);
                        tempValue = neighbor3[i - 1][j];
                        neighbor3[i][j] = tempValue;
                        neighbor3[i - 1][j] = 0;
                        boardStack.push(new Board(neighbor3));
                        // boardList.add(new Board(neighbor3)); // add third neighbor - move the down up tile to the down

                        int[][] neighbor4 = deepCopy(this.myTiles);
                        tempValue = neighbor4[i + 1][j];
                        neighbor4[i][j] = tempValue;
                        neighbor4[i + 1][j] = 0;
                        boardStack.push(new Board(neighbor4));
                        // boardList.add(new Board(neighbor4)); // add the forth neighbor - move the down row tile to the up


                        break outerLoop;
                    }
                }
            }
        }

        return boardStack;
        // return boardList;
    }

    /* private int randomGenerator(int n) {
        Random rand = new Random();
        return rand.nextInt(n);
    } */

    // a board that is obtained by exchanging any pair of tiles
    // You will use it to determine whether a puzzle is solvable: exactly one of a board and its twin are solvable.
    // A twin is obtained by swapping any pair of tiles (the blank square is not a tile).

    public Board twin() {

        // int x1, y1, x2, y2;
        int[] indexX = new int[2];
        int[] indexY = new int[2];
        int[][] twin = deepCopy(this.myTiles);
        int k = 0;
        int p = 0;

        outerLoop:
        for (int i = 0; i < this.myTiles.length; i++) {
            for (int j = 0; j < this.myTiles.length; j++) {
                if (!(twin[i][j] == 0)) {
                    indexX[k++] = i;
                    indexY[p++] = j;
                    if (k == 2) break outerLoop;
                }
            }
        }

       /*
       do {
            x1 = randomGenerator(this.myTiles.length);
            y1 = randomGenerator(this.myTiles.length);

        } while (twin[x1][y1] == 0);

        do {
            x2 = randomGenerator(this.myTiles.length);
            y2 = randomGenerator(this.myTiles.length);

        } while (twin[x2][y2] == 0 || twin[x2][y2] == twin[x1][y1]);

        int temp = twin[x1][y1];
        twin[x1][y1] = twin[x2][y2];
        twin[x2][y2] = temp;
        */

        int temp = twin[indexX[0]][indexY[0]];
        twin[indexX[0]][indexY[0]] = twin[indexX[1]][indexY[1]];
        twin[indexX[1]][indexY[1]] = temp;
        return new Board(twin);
    }


    /*
     * Unit tests (not graded)
     */

    // unit testing (not graded)
    /* public static void main(String[] args) {

    } */
}
