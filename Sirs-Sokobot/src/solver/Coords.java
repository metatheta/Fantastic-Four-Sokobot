package solver;

import java.util.Objects;

public class Coords {
    public final int row;
    public final int col;

    public Coords(int row, int col)
    {
        this.row = row;
        this.col = col;
    }

	@Override
	public boolean equals(Object o) {
		if (o.getClass() != this.getClass()) {
			return false;
		}

		Coords c = (Coords)o;
		return c.row == this.row && c.col == this.col;
	}

    @Override
    public int hashCode()
    {
        // return 31 * row + col;
		return Objects.hash(this.row, this.col);
    }
}
