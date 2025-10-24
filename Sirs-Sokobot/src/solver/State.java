package solver;

import java.util.HashSet;
import java.util.Objects;

public class State {
    public static int stateCount = 0;
	public final Coords player;
    public final HashSet<Coords> boxes;
	public final char last;

    public State(Coords player, HashSet<Coords> boxes, char last) {
        this.player = player;
		this.boxes = new HashSet<Coords>(boxes);
		this.last = last;

        State.stateCount += 1;
    }

    public boolean isDeadlock(SokoBanBoard board, HashSet<Coords> squarelocks) {
        for (Coords box : boxes) {
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
        if (!(o instanceof State s))
            return false;

		// return Objects.equals(this.boxes, s.boxes);
		return Objects.equals(this.boxes, s.boxes)
			&& Objects.equals(this.last, s.last);
		// return Objects.equals(this.player, s.player)
			// && Objects.equals(this.boxes, s.boxes)
			// && Objects.equals(this.last, s.last);
    }

	// SOMETHING GWEL CHANGED V
    @Override
    public int hashCode() {
        // return Objects.hash(boxes);
        return Objects.hash(boxes, last);
        // return Objects.hash(player, boxes, last);
    }

	@Override
	public String toString() {
		return "Player: " + player.toString() + ", " + "Boxes: " + boxes.toString() + ", " + "Last: " + last;
	}
}
