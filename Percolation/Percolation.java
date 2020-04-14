import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {


    private final int n;
    private final WeightedQuickUnionUF perlocation;
    private final int topVirtaulNode;
    private final int bottomVirtaulNode;
    private boolean[][] openSites;
    private int numbberOfOpenSites = 0;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        if (n == 0 || n < 0) throw new IllegalArgumentException("The n should be more than zero.");
        this.n = n;
        this.perlocation = new WeightedQuickUnionUF(this.n * this.n + 2); // 2 extra node are represented top virtual node (element n*n) and bottom virtual node (element n*n + 1)

        this.openSites = new boolean[this.n][this.n]; // this array shows if the correspondent element is connected or not


        this.topVirtaulNode = this.perlocation.find(this.n * this.n);

        this.bottomVirtaulNode = this.perlocation.find(this.n * this.n + 1);


    }

    private int objectIndex(int row, int col) {
        validate(row, col);
        row = row - 1;
        col = col - 1;
        return row * this.n + col;
    }

    
    private boolean getOpenStateArray(int row, int col) {
        return this.openSites[row - 1][col - 1];
    }

    private void setOpenStateArray(int row, int col) {
        this.openSites[row - 1][col - 1] = true;
    }


    private void validate(int row, int col) {
        if (row < 1 || row > this.n || col < 1 || col > this.n)
            throw new IllegalArgumentException("You should enter a number between 1 and " + (this.n));
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        validate(row, col);

        if (!isOpen(row, col)) {
            setOpenStateArray(row, col);
            this.numbberOfOpenSites += 1;

            if (row == 1 && !this.perlocation.connected(this.topVirtaulNode, objectIndex(row, col)))
                this.perlocation.union(this.topVirtaulNode, objectIndex(row, col));
            if (row == this.n && !this.perlocation.connected(this.bottomVirtaulNode, objectIndex(row, col)))
                this.perlocation.union(this.bottomVirtaulNode, objectIndex(row, col));
        }


        int mySite = objectIndex(row, col);

        int rightSite;
        int leftSite;
        int upperSight;
        int bottomSite;

        if (row > 1) {
            if (getOpenStateArray(row - 1, col)) {
                upperSight = objectIndex(row - 1, col);
                this.perlocation.union(mySite, upperSight); // upper neighbor
            }
        }
        if (row < this.n) {
            if (getOpenStateArray(row + 1, col)) {
                bottomSite = objectIndex(row + 1, col);
                this.perlocation.union(mySite, bottomSite); // lower neighbor

            }
        }
        if (col > 1) {
            if (getOpenStateArray(row, col - 1)) {
                leftSite = objectIndex(row, col - 1);
                this.perlocation.union(mySite, leftSite); // left neighbor
            }
        }
        if (col < this.n) {
            if (getOpenStateArray(row, col + 1)) {
                rightSite = objectIndex(row, col + 1);
                this.perlocation.union(mySite, rightSite); // right neighbor
            }
        }



       /* for (int j = 1; j < this.n + 1; j++) {
            if (isFull(this.n, j)) this.perlocation.union(this.bottomVirtaulNode, findIndex(this.n, j));
        }*/


    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        validate(row, col);

        return getOpenStateArray(row, col);
    }


    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

        validate(row, col);

        if (isOpen(row, col)) {
            int mySite = objectIndex(row, col);
            return (this.perlocation.connected(mySite, this.topVirtaulNode)); // it checks if the site is connected to an open site in the top row
        }
        return false;
    }

   /* private void backWashEvitation() {
        for (int j = 1; j < this.n + 1; j++) {
            if (isFull(this.n, j)) this.perlocation.union(this.bottomVirtaulNode, findIndex(this.n, j));
        }
    }*/

    // returns the number of open sites
    public int numberOfOpenSites() {

        return this.numbberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {

        // backWashEvitation();

        return (this.perlocation.connected(this.topVirtaulNode, this.bottomVirtaulNode)); // it checks if bottom row is connected to the upper row

    }

    // test client (optional)
    public static void main(String[] args) {
       /* In in = new In(args[0]);
        int n = in.readInt();
        Percolation myPerTest = new Percolation(n);
        myPerTest.open(2, 3);*/
    }
}

