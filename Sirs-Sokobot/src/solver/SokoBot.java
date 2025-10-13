package solver;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.lang.StringBuilder;

public class SokoBot {

  public String solveSokobanPuzzle(int width, int height, char[][] mapData, char[][] itemsData) {
    
    int x, y;
    int numBoxes = 0;
    Coords[] goals;
    Coords[] boxes;
    Coords player = null;
    int boxIndex = 0;

    ArrayDeque<Node> frontier = new ArrayDeque<>();
    HashSet<Node> explored = new HashSet<>();
    List<Node> temp = new ArrayList<>();
    Node intermediate;

    //store the goals in an array
    
    String answer;

    StringBuilder sb = new StringBuilder();
    
    //get number of boxes
    for (x = 0; x < mapData.length; x++)
    {
      for (y = 0; y < mapData[x].length; y++)
      {
        if (mapData[x][y] == '.')
        {
          numBoxes++;
        }
      }
    }

    //make the goals and boxes
    goals = new Coords[numBoxes];
    boxes = new Coords[numBoxes];

    //assign the goals coordinates
    for (x = 0; x < mapData.length; x++)
    {
      for (y = 0; y < mapData[x].length && numBoxes > boxIndex; y++)
      {
        if (mapData[x][y] == '.')
        {
          goals[boxIndex] = new Coords(x, y);
          boxIndex++;
        }
      }
    }

    //sort the goals
    Arrays.sort(goals);

    //reset boxIndex
    boxIndex = 0;

    //assign the boxes and player coordinates
    for (x = 0; x < itemsData.length; x++)
    {
      for (y = 0; y < itemsData[x].length; y++)
      {
        if (itemsData[x][y] == '$')
        {
          boxes[boxIndex] = new Coords(x, y);
          boxIndex++;
        }

        if (itemsData[x][y] == '@')
        {
          player = new Coords(x, y);
        }
      }
    }

    //sort the boxes
    Arrays.sort(boxes);

    //make the starting node
    Node curr = new Node(player, boxes, '\0', null);

    frontier.add(curr);
    
    //continue looping while the frontier isnt empty
    while (!frontier.isEmpty())
    {
      //assign the front of the fronteir as this iteration's curr
      curr = frontier.poll();

      //check if its the goal
      if (Arrays.equals(goals, curr.getBoxes()))
      {
        break;
      }

      //if its not the goal we add it to explored
      explored.add(curr);

      //if its not the goal we get its kids
      temp = curr.getChildren(mapData);


      //we add the kids to the frontier
      for (x = 0; x < temp.size(); x++)
      {
        intermediate = temp.get(x);
        if (!explored.contains(intermediate))
        {
          frontier.addLast(intermediate);
          System.out.println(intermediate.toString());
        }
      }
    }

    sb = new StringBuilder();

    while(curr.getParent() != null)
    {
      sb.append(curr.getMove());
      curr = curr.getParent();
    }

    sb.reverse();
    answer = sb.toString();

    return answer;
  }
}
