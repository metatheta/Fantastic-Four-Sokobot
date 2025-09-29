package solver;

import java.util.*;

public final class State {
    public final Coords player;
    public final Set<Coords> boxes;

    public State(Coords player, Collection<Coords> boxes) {
        this.player = player;
        this.boxes = new HashSet<>(boxes);
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
