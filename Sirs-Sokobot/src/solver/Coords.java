package solver;

public class Coords {
    public int row;
    public int col;

    public Coords(int row, int col)
    {
        this.row = row;
        this.col = col;
    }

	@Override
	public Coords clone() {
		return new Coords(this.row, this.col);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Coords c)) {
			return false;
		}

		return c.row == this.row && c.col == this.col;
	}

    @Override
    public int hashCode() {
        return 31 * row + col;
		// return Objects.hash(this.row, this.col);
    }

	@Override
	public String toString() {
		return "(" + row + ", " + col + ")";
	}
}
