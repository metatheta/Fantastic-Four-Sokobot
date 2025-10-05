package solver;

public class Coords implements Comparable<Coords>{

    public final int row;
    public final int col;

    public Coords(int row, int col)
    {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null)
            return false;
        if (o == this)
            return true;
        if (this.getClass() != o.getClass())
            return false;
        Coords c = (Coords) o;
        if(this.hashCode() == c.hashCode())
            return true;
        return c.row == row && c.col == col;
    }

     @Override
    public int compareTo(Coords other)
    {
        if (this.row != other.row)
            return this.row - other.row;
        return this.col - other.col;
    }

    @Override
    public int hashCode()
    {
        return 31 * row + col;
    }

}
