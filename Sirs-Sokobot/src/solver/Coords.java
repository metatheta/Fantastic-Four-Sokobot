package solver;

public class Coords implements Comparable<Coords>{
    
    public final int x;
    public final int y;
    
    public Coords(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof Coords c))
            return false;
        return c.x == x && c.y == y;
    }

     @Override
    public int compareTo(Coords other) 
    {
        if (this.x != other.x) 
            return this.x - other.x;
        return this.y - other.y;                        
    }

    @Override
    public int hashCode() 
    {
        return 31 * x + y;
    }

}
