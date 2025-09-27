import java.util.ArrayDeque;
import java.util.List;


public class Driver {
    public static void main(String[] args)
    {
        ArrayDeque<Node> frontier = new ArrayDeque<>();
        ArrayDeque<Node> explored = new ArrayDeque<>();
        
        frontier.addFirst(new Node(8, 0, 0, null));
        explored.add(frontier.peek());

        Node curr = explored.peek();
        List<Node> generated;
        
        while (!curr.isGoal()) 
        {
            //add to fronteir
            generated = curr.getChildren();
            for (int x = 0; x < generated.size(); x++)
            {
                frontier.addLast(generated.get(x));
            }

            //adjust frontier
            frontier.removeFirst();

            //add front of frontier to explored
            explored.addLast(frontier.peek());

            //update value of curr
            curr = frontier.peek();
        }

        System.out.print("This is the goal: ");

        while (curr.parent != null)
        {
            System.out.println(curr.toString());
            curr = curr.parent;
        }

        System.out.println(curr.toString());
        System.out.println("This is the starting point");
    }
    

}
