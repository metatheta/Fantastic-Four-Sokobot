import java.util.ArrayList;
import java.util.List;

public class Node
{
    Glass g1;
    Glass g2;
    Glass g3;

    public final Node parent;

    public Node(int x, int y, int z, Node p)
    {
        g1 = new Glass(x, 8);
        g2 = new Glass(y, 5);
        g3 = new Glass(z, 3);

        parent = p;
    }

    public boolean isGoal()
    {
        if (g1.contents == 4 && g2.contents == 4 && g3.contents == 0)
            return true;
        else
            return false;
    }

    @Override
    public String toString()
    {
        return g1.contents + " " + g2.contents + " " + g3.contents;
    }

    @Override
    public boolean equals(Object n)
    {
        Node temp = (Node) n;
    
        if (this.g1.contents == temp.g1.contents
            && this.g2.contents == temp.g2.contents
            && this.g3.contents == temp.g3.contents)
            return true;
        else
            return false;
    }

    private Node pour(Glass src, Glass dst) 
    {
        int amount = Math.min(src.contents, dst.limit - dst.contents);

        // copy all glasses
        Glass newG1 = new Glass(g1.contents, g1.limit);
        Glass newG2 = new Glass(g2.contents, g2.limit);
        Glass newG3 = new Glass(g3.contents, g3.limit);

        // figure out which glasses we are pouring between
        if (src == g1 && dst == g2) {
            newG1.contents -= amount;
            newG2.contents += amount;
        } else if (src == g1 && dst == g3) {
            newG1.contents -= amount;
            newG3.contents += amount;
        } else if (src == g2 && dst == g1) {
            newG2.contents -= amount;
            newG1.contents += amount;
        } else if (src == g2 && dst == g3) {
            newG2.contents -= amount;
            newG3.contents += amount;
        } else if (src == g3 && dst == g1) {
            newG3.contents -= amount;
            newG1.contents += amount;
        } else if (src == g3 && dst == g2) {
            newG3.contents -= amount;
            newG2.contents += amount;
        }

        return new Node(newG1.contents, newG2.contents, newG3.contents, this);
    }

    public List<Node> getChildren() 
    {
        List<Node> children = new ArrayList<>();

        // Try all 6 pours
        if (g1.contents > 0 && g2.contents < g2.limit)
            children.add(pour(g1, g2));
        if (g1.contents > 0 && g3.contents < g3.limit)
            children.add(pour(g1, g3));

        if (g2.contents > 0 && g1.contents < g1.limit)
            children.add(pour(g2, g1));
        if (g2.contents > 0 && g3.contents < g3.limit)
            children.add(pour(g2, g3));

        if (g3.contents > 0 && g1.contents < g1.limit)
            children.add(pour(g3, g1));
        if (g3.contents > 0 && g2.contents < g2.limit)
            children.add(pour(g3, g2));

        return children;
    }
    
}