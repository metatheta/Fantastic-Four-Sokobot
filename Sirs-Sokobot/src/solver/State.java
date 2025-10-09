package solver;

import java.util.*;

public class State {
    public final Coords player;
    public final Set<Coords> boxes;

    public State(Coords player, Collection<Coords> boxes) {
        this.player = player;
        this.boxes = new HashSet<>(boxes);
    }

    //non pring addition
    //note, if deadlockSet fails, uncomment the multi line thing inside
    public boolean isCornerDeadlock(HashSet<Coords> goals, Set<Coords> walls)
    {
        int row;
        int col;

        for (Coords box : boxes)
        {
            row = box.row;
            col = box.col;

            //box is in a top left corner
            if(isIn(walls, row - 1, col) && isIn(walls, row, col - 1))
                return true;

            //box is in a top right corner
            if(isIn(walls, row - 1, col) && isIn(walls, row, col + 1))
                return true;

            //box is in a bottom left corner
            if(isIn(walls, row + 1, col) && isIn(walls, row, col - 1))
                return true;

            //box is in a bottom right corner
            if(isIn(walls, row + 1, col) && isIn(walls, row, col + 1))
                return true;

            /*
            //box has 3 walls above it and 1 wall on each side
            if(isIn(walls, row - 1, col) && isIn(walls, row - 1, col - 1)
                && isIs(walls, row - 1, col + 1) && isIn(walls, row, col - 2)
                && isIn(walls, row, col + 2) && (!isIn(goals, row, col - 1) && !isIn(walls, row, col + 1)))
                return true;

            //box has 3 walls below it and 1 wall on each side
            if(isIn(walls, row + 1, col) && isIn(walls, row + 1, col - 1)
                && isIs(walls, row + 1, col + 1) && isIn(walls, row, col - 2)
                && isIn(walls, row, col + 2) && (!isIn(goals, row, col - 1) && !isIn(walls, row, col + 1)))
                return true;

            //box has 3 walls to the left of it and 1 wall on the top and another on the bottom
            if(isIn(walls, row, col - 1) && isIn(walls, row - 1, col - 1)
                && isIs(walls, row + 1, col - 1) && isIn(walls, row - 2, col)
                && isIn(walls, row + 2, col) && (!isIn(goals, row - 1, col) && !isIn(walls, row + 1, col)))
                return true;

            //box has 3 walls to the right of it and 1 wall on the top and another on the bottom
            if(isIn(walls, row, col + 1) && isIn(walls, row - 1, col + 1)
                && isIs(walls, row + 1, col + 1) && isIn(walls, row - 2, col)
                && isIn(walls, row + 2, col) && (!isIn(goals, row - 1, col) && !isIn(walls, row + 1, col)))
                return true;
            */
        }

        return false;
    }

    /* comment this if the 4 deadlock set maker functions dont work */
    //non pring addition
    public boolean isDeadlock(HashSet<Coords> deadlocks)
    {
        int row;
        int col;

        for(Coords box : boxes)
        {
            row = box.row;
            col = box.col;

            if(isIn(deadlocks, row, col))
                return true;
        }

        return false;
    }

    //checks if a coordinate is in a set of coordinates
    public boolean isIn(HashSet<Coords> collection, int row, int col)
    {
        if (collection.contains(new Coords(row, col)))
        {
            return true;
        }

        return false;
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof State other))
            return false;
        return Objects.equals(this.player, other.player)
                && Objects.equals(this.boxes, other.boxes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, boxes);
    }

    public boolean isGoal(SokoBanBoard board) {
        for(Coords box : boxes) {
            if(!board.isGoalCell(box)) {
                return false;
            }
        }
        return true;
    }
}
