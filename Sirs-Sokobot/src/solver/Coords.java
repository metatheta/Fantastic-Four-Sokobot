package solver;

public class Coords implements Comparable<Coords>{
    
    private int x;
    private int y;
    
    public Coords(int x, int y)
    {
        setx(x);
        sety(y);
    }

    public void setx(int i)
    {
        x = i;
    }

    public void sety(int i)
    {
        y = i;
    }

    public int getx()
    {
        return x;
    }

    public int gety()
    {
        return y;
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof Coords))
            return false;

        Coords c = (Coords)o;

        return c.getx() == x && c.gety() == y;
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
