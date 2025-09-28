package solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node {

    private Coords player;
    private Coords[] boxes;
    private char move;
    private Node parent;

    public Node(Coords p, Coords[] b, char m, Node pa)
    {
        player = p;
        boxes = Arrays.copyOf(b, b.length);
        Arrays.sort(boxes);
        move = m;
        parent = pa;
    }

    public Coords[] getBoxes()
    {
        return boxes;
    }

    public char getMove()
    {
        return move;
    }

    public Node getParent()
    {
        return parent;
    }

    public Coords getPlayer()
    {
        return player;
    }
    
    public List<Node> getChildren(char[][] mapData) 
    {
        
        List<Node> children = new ArrayList<>();
        
        // up, down, left, right
        int[][] directions = { 
                                {-1,0}, 
                                {1,0}, 
                                {0,-1}, 
                                {0,1} 
                             };
        char[] moves = { 'u', 'd', 'l', 'r' };

        int newPx;
        int newPy;
        int boxIndex;
        int newBx;
        int newBy;

        Coords[] newBoxes;

        for (int i = 0; i < 4; i++) 
        {
            newPx = player.getx() + directions[i][0];
            newPy = player.gety() + directions[i][1];

            //check if the player is out of the map
            if (newPx < 0 
                || newPx >= mapData.length 
                || newPy < 0 
                || newPy >= mapData[0].length) 
            {
                continue;
            }

            //check if the player would be walking into a wall
            if (mapData[newPx][newPy] == '#') 
            {
                continue;
            }
                

            //get the index of the box at the players new position
            boxIndex = indexOfBoxAt(newPx, newPy);

            //check if the player would be walking into a box
            if (boxIndex != -1) 
            {
                newBx = newPx + directions[i][0];
                newBy = newPy + directions[i][1];

                //check if the box is out of the map
                if (newBx < 0 
                    || newBx >= mapData.length 
                    || newBy < 0 
                    || newBy >= mapData[0].length) 
                {
                    continue;
                }

                //check if theres a box based on map data or the indexOfBoxAt function
                if (mapData[newBx][newBy] == '#' 
                    || indexOfBoxAt(newBx, newBy) != -1) 
                {
                    continue;
                }

                
                //checks if the wall is a corner
                if(wallIsCorner(mapData, newBy + directions[i][0], newBx + directions[i][1], moves[i]))
                {
                    continue;
                }

                if (wallHasTwoCorners(mapData, newBy + directions[i][0], newBx + directions[i][1], moves[i]))
                {
                    if (!wallHasGoalNear(mapData, newBy + directions[i][0], newBx + directions[i][1], moves[i]))
                    {
                        continue;
                    }
                }
                

                
                //copy the old boxes into a new one
                newBoxes = Arrays.copyOf(boxes, boxes.length);
                //replace the old box with its new coordinates
                newBoxes[boxIndex] = new Coords(newBx, newBy);


                Arrays.sort(newBoxes);
                //add a new child with the new player coords and new boxes
                children.add(new Node(new Coords(newPx, newPy), newBoxes, moves[i], this));
            } 
            else 
            {
                Arrays.sort(boxes);
                //if boxes aren't involved then just add the new state to children
                children.add(new Node(new Coords(newPx, newPy), Arrays.copyOf(boxes, boxes.length), moves[i], this));
            }
        }

        return children;
    }

    //check if there is a box at x and y, if there is then we return the index
    private int indexOfBoxAt(int x, int y) 
    {
        for (int i = 0; i < boxes.length; i++) 
        {
            if (boxes[i].getx() == x && boxes[i].gety() == y) 
            {
                return i;
            }
        }

        return -1;
    }

    //plug in the direction, the row and call of the wall that the box might hit, and the mapData
    public boolean wallHasGoalNear(char[][] mapData, int rowWall, int colWall, char direction)
    {
        int i;
        int lastRow = mapData.length - 1;
        int lastCol = mapData[0].length - 1;

        if (direction == 'u')
        {
            i = colWall - 1;
            rowWall++;

            //check the bottom row to the left for goal
            while(i > 0 && mapData[rowWall][i] != '#')
            {
                if (mapData[rowWall][i] == '.')
                {
                    return true;
                }
                i--;
            }

            i = colWall + 1;

            //check the bottom row to the right for goal
            while(i < lastCol && mapData[rowWall][i] != '#' )
            {
                if (mapData[rowWall][i] == '.')
                {
                    return true;
                }
                i++;
            }
        }
        else if (direction == 'd')
        {
            i = colWall - 1;
            rowWall--;

            //check the upper row to the left for goal
            while(i > 0 && mapData[rowWall][i] != '#')
            {
                if (mapData[rowWall][i] == '.')
                {
                    return true;
                }
                i--;
            }

            i = colWall + 1;

            //check the upper row to the right for goal
            while(i < lastCol && mapData[rowWall][i] != '#')
            {
                if (mapData[rowWall][i] == '.')
                {
                    return true;
                }
                i++;
            }
        }
        else if (direction == 'l')
        {
            i = rowWall - 1;
            colWall++;

            //check the right col upwards for goal
            while(i > 0 && mapData[i][colWall] != '#')
            {
                if (mapData[i][colWall] == '.')
                {
                    return true;
                }
                i--;
            }

            i = rowWall + 1;

            //check the right col to the downwards for goal
            while(i < lastRow && mapData[i][colWall] != '#')
            {
                if (mapData[i][colWall] == '.')
                {
                    return true;
                }
                i++;
            }
        }
        else if (direction == 'r')
        {
            i = rowWall - 1;
            colWall--;

            //check the left col upwards for goal
            while(i > 0 && mapData[i][colWall] != '#')
            {
                if (mapData[i][colWall] == '.')
                {
                    return true;
                }
                i--;
            }

            i = rowWall + 1;

            //check the left col to the downwards for goal
            while(i < lastRow && mapData[i][colWall] != '#')
            {
                if (mapData[i][colWall] == '.')
                {
                    return true;
                }
                i++;
            }
        }
        

        return false;
    }

    public boolean wallHasTwoCorners(char[][] mapData, int rowWall, int colWall, char direction)
    {
        int i;
        boolean plusCornerFlag = false;
        boolean minusCornerFlag = false;
        int lastRow = mapData.length - 1;
        int lastCol = mapData[0].length - 1;
        int extraRow;
        int extraCol;

        if (direction == 'u')
        {
            i = colWall - 1;
            extraRow = rowWall + 1;

            //check the bottom row to the left for corner
            while(i >= 0 && mapData[rowWall][i] == '#')
            {
                if (mapData[extraRow][i] == '#')
                {
                    minusCornerFlag = true;
                    break;
                }
                i--;
            }

            i = colWall + 1;

            //check the bottom row to the right for corner
            while(i <= lastCol && mapData[rowWall][i] == '#')
            {
                if (mapData[extraRow][i] == '#')
                {
                    plusCornerFlag = true;
                    break;
                }
                i++;
            }
        }
        else if (direction == 'd')
        {
            i = colWall - 1;
            extraRow = rowWall - 1;

            //check the upper row to the left for corner
            while(i >= 0 && mapData[rowWall][i] == '#')
            {
                if (mapData[extraRow][i] == '#')
                {
                    minusCornerFlag = true;
                    break;
                }
                i--;
            }

            i = colWall + 1;

            //check the upper row to the right for corner
            while(i <= lastCol && mapData[rowWall][i] == '#')
            {
                if (mapData[extraRow][i] == '#')
                {
                    plusCornerFlag = true;
                    break;
                }
                i++;
            }
        }
        else if (direction == 'l')
        {
            i = rowWall - 1;
            extraCol = colWall + 1;

            //check the right col upwards for corner
            while(i >= 0 && mapData[i][colWall] == '#')
            {
                if (mapData[i][extraCol] == '#')
                {
                    plusCornerFlag = true;
                    break;
                }
                i--;
            }

            i = rowWall + 1;

            //check the right col to the downwards for corner
            while(i <= lastRow && mapData[i][colWall] == '#')
            {
                if (mapData[i][extraCol] == '#')
                {
                    minusCornerFlag = true;
                    break;
                }
                i++;
            }
        }
        else if (direction == 'r')
        {
            i = rowWall - 1;
            extraCol = colWall - 1;

            //check the left col upwards for corner
            while(i >= 0 && mapData[i][colWall] == '#')
            {
                if (mapData[i][extraCol] == '#')
                {
                    plusCornerFlag = true;
                    break;
                }
                i--;
            }

            i = rowWall + 1;

            //check the left col to the downwards for corner
            while(i <= lastRow && mapData[i][colWall] == '#')
            {
                if (mapData[i][extraCol] == '#')
                {
                    minusCornerFlag = true;
                    break;
                }
                i++;
            }
        }
        

        return plusCornerFlag && minusCornerFlag;
    }

    public boolean wallIsCorner(char[][] mapData, int rowWall, int colWall, char direction)
    {
        if (direction == 'u')
        {
            rowWall++;

            //check the bottom row to the left for corner
            if (mapData[rowWall][colWall - 1] == '#')
            {
                return true;
            }

            //check the bottom row to the right for corner
            if (mapData[rowWall][colWall + 1] == '#')
            {
                return true;
            }
        }
        else if (direction == 'd')
        {
            rowWall--;

            //check the upper row to the left for corner
            if (mapData[rowWall][colWall - 1] == '#')
            {
                return true;
            }

            //check the upper row to the right for corner
            if (mapData[rowWall][colWall + 1] == '#')
            {
                return true;
            }
        }
        else if (direction == 'l')
        {
            colWall++;

            //check the right col upwards for corner
            if (mapData[rowWall - 1][colWall] == '#')
            {
                return true;
            }

            //check the right col to the downwards for corner
            if (mapData[rowWall + 1][colWall] == '#')
            {
                return true;
            }
        }
        else if (direction == 'r')
        {
            colWall--;

            //check the left col upwards for corner
            if (mapData[rowWall - 1][colWall] == '#')
            {
                return true;
            }

            //check the left col to the downwards for corner
            if (mapData[rowWall + 1][colWall] == '#')
            {
                return true;
            }
        }

        return false;
    }
    
    @Override
    public int hashCode() 
    {
        int result = player.hashCode();
        result = 31 * result + Arrays.hashCode(boxes);
        return result;
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof Node))
            return false;

        Node temp = (Node)o;

        return player.equals(temp.getPlayer()) && Arrays.equals(boxes, temp.getBoxes());
    }
}
