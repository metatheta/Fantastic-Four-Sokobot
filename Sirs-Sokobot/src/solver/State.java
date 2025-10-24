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

    public boolean isDeadlock(SokoBanBoard board, HashSet<Coords> deadlocks) {
        for (Coords box : boxes) {
            if (board.goals.contains(box)) 
                continue;

			if (deadlocks.contains(box))
				return true;
        }

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof State s))
            return false;

		return Objects.equals(this.boxes, s.boxes)
			&& Objects.equals(this.last, s.last);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boxes, last);
    }

	@Override
	public String toString() {
		return "Player: " + player.toString() + ", " + "Boxes: " + boxes.toString() + ", " + "Last: " + last;
	}
}
