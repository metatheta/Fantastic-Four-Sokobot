package solver;

import java.util.HashSet;
import java.util.Objects;

public class State {
    public static int state_count = 0;

    public final Coords player;
    public final HashSet<Coords> boxes;

    //TODO: LOTS OF THESE ARE NOW COMPLETELY USELESS
    public final int goalCount;
    public final Boolean pushState;
    public final State previousState;

    public State(State prev, Coords player, HashSet<Coords> boxes, int goalCount, Boolean pushState) {
        this.previousState = prev;
        this.player = player;
        this.boxes = new HashSet<>(boxes);
        this.goalCount = goalCount;
        this.pushState = pushState;
        State.state_count += 1;
    }

    public boolean isDeadlock(SokoBanBoard board, HashSet<Coords> squarelocks)
    {
        for (Coords box : boxes)
        {
            if (board.goals.contains(box)) 
                continue;

			if (squarelocks.contains(box))
				return true;

			// Top-left
			if (board.walls.contains(new Coords(box.row-1, box.col))
				&& board.walls.contains(new Coords(box.row, box.col+1))) {
				return true;
			}

			// Top-right
			if (board.walls.contains(new Coords(box.row-1, box.col))
				&& board.walls.contains(new Coords(box.row, box.col-1))) {
				return true;
			}

			// Bottom-left
			if (board.walls.contains(new Coords(box.row+1, box.col))
				&& board.walls.contains(new Coords(box.row, box.col-1))) {
				return true;
			}

			// Bottom-right
			if (board.walls.contains(new Coords(box.row+1, box.col))
				&& board.walls.contains(new Coords(box.row, box.col+1))) {
				return true;
			}
        }

        return false;
    }

    @Override
    public boolean equals(Object o) {
        //data type checking
        if (!(o instanceof State s))
            return false;

        //checks equality for state
		/*return Objects.equals(this.player, s.player)
        	&& Objects.equals(this.boxes, s.boxes);*/
        return Objects.equals(this.boxes, s.boxes) && Objects.equals(this.player, s.player)
                && Objects.equals(this.goalCount, s.goalCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, boxes);
    }
}
