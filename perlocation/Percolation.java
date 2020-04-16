import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {


    private final int n;
    private final WeightedQuickUnionUF perlocation;
    private byte[] siteStates;
    private int numberOfOpenSites = 0;
    private boolean perlocateFlag;
    private final byte open;
    private final byte top;
    private final byte bottom;
    private final byte closed;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        if (n == 0 || n < 0) throw new IllegalArgumentException("The n should be more than zero.");
        this.n = n;

        this.perlocation = new WeightedQuickUnionUF(this.n * this.n);

        this.siteStates = new byte[this.n * this.n];

        this.open = 1;
        this.top = 2;
        this.bottom = 4;
        this.closed = 0;

    }

    private int objectIndex(int row, int col) {
        validate(row, col);
        row = row - 1;
        col = col - 1;
        return row * this.n + col;
    }


    private void validate(int row, int col) {
        if (row < 1 || row > this.n || col < 1 || col > this.n)
            throw new IllegalArgumentException("You should enter a number between 1 and " + (this.n));
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        validate(row, col);
        byte currentState;
        final byte topBottom = 6;  // top | bottom, it is considered for this.n = 2
        final byte topOpenBottom = 7; // open | top | bottom

        int mySite = objectIndex(row, col);

        if (siteStates[mySite] == closed) {

            siteStates[mySite] = open;
            this.numberOfOpenSites += 1;

            if (row == 1) {
                siteStates[mySite] = top;
            }
            if (row == this.n) {
                siteStates[mySite] = bottom;
            }

        }

        currentState = siteStates[mySite];

        if (row > 1) {
            if (!(siteStates[mySite - this.n] == closed)) {

                byte upState = siteStates[this.perlocation.find(mySite - this.n)];
                currentState = (byte) (upState | currentState);
                this.perlocation.union(mySite, mySite - this.n); // upper neighbor
            }
        }
        if (row < this.n) {
            if (!(siteStates[mySite + this.n] == closed)) {

                byte bottomState = siteStates[this.perlocation.find(mySite + this.n)];
                currentState = (byte) (bottomState | currentState);
                this.perlocation.union(mySite, mySite + this.n); // lower neighbor
            }
        }
        if (col > 1) {
            if (!(siteStates[mySite - 1] == closed)) {

                byte leftState = siteStates[this.perlocation.find(mySite - 1)];
                currentState = (byte) (leftState | currentState);
                this.perlocation.union(mySite, mySite - 1); // left neighbor
            }
        }
        if (col < this.n) {
            if (!(siteStates[mySite + 1] == closed)) {

                byte rightState = siteStates[this.perlocation.find(mySite + 1)];
                currentState = (byte) (rightState | currentState);
                this.perlocation.union(mySite, mySite + 1); // right neighbor
            }
        }

        int mySiteRoot = this.perlocation.find(mySite);
        siteStates[mySite] = currentState;
        siteStates[mySiteRoot] = currentState;

        if ((currentState == topBottom) || (currentState == topOpenBottom)) { // 7 = open | top | bottom , 6 = top | bottom
            //  (currentState == 8) ||
            perlocateFlag = true;
        }

        // Corner Case
        if (siteStates.length == 1 && currentState == bottom) {
            perlocateFlag = true;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        validate(row, col);

        byte openState = (byte) (siteStates[(objectIndex(row, col))] & open);
        byte topState = (byte) (siteStates[(objectIndex(row, col))] & top);
        byte bottomState = (byte) (siteStates[(objectIndex(row, col))] & bottom);

        return (openState != 0 || topState != 0 || bottomState != 0);

    }


    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

        validate(row, col);
        byte fullState;

        if (siteStates.length == 1) // Corner Case
        {
            fullState = (byte) (siteStates[this.perlocation.find(objectIndex(row, col))] & bottom);
        } else {

            fullState = (byte) (siteStates[this.perlocation.find(objectIndex(row, col))] & top);
        }

        return (fullState != 0);

    }


    // returns the number of open sites
    public int numberOfOpenSites() {

        return this.numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {

        return perlocateFlag;

    }

    // test client (optional)
    public static void main(String[] args) {
       /* In in = new In(args[0]);
        int n = in.readInt();
        Percolation myPerTest = new Percolation(n);
        myPerTest.open(2, 3);*/
    }
}

