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
        HashSet<Coords> checked = new HashSet<>();
		
		// System.out.printf("Boxes: %s\n", boxes);
		for (Coords box : boxes) {
            if (board.goals.contains(box)) 
                continue;

			if (deadlocks.contains(box))
				return true;
			
			if (isFreezeDeadlock(board, box, checked, 0))
			{
				// System.out.printf("Frozen deadlock!!!\n%s\n\n", boxes);
				return true;
			}
        }

		// System.out.println();
		// System.out.println(checked);
        return false;
    }

	// What the fuck
	private boolean isFreezeDeadlock(SokoBanBoard board, Coords box, HashSet<Coords> checked, int i) {
		// if (i == 1) System.out.print("\t");
		// System.out.printf("Box: %s\n", box);
		
		if (checked.contains(box))
			return false;
		
		// Recursib
		char[] dirs = {'l', 'u', 'd', 'r'};

		// Check if this box can be pushed at all
		for (char d1 : dirs) {
			Coords space = NodeGenerator.applyMove(box, d1);

			// If this new space around box is empty
			// and it hasn't been checked already
			// (Checked boxes act as walls now)
			if (!board.walls.contains(space) && !checked.contains(space))
				// If this space can push the box from there
				// in like, the opposite way
				if (NodeGenerator.isActionValid(board, boxes, space, NodeGenerator.invertDirection(d1)))
					// System.out.printf("Box at %s can be pushed from %s!\n", box, space);
					// If it can be pushed, no deadlock!
					return false;
		}

		if (i == 1) return true;
		checked.add(box);
		// if (i == 1) System.out.print("\t");
		// System.out.printf("Box %s can't be pushed...\n", box);

		// Check for boxes
		for (char d2: dirs) {
			Coords space = NodeGenerator.applyMove(box, d2);
			
			// If this space has a box
			// and it hasn't been checked yet	
			if (boxes.contains(space) && !checked.contains(space))
				// Recursive call to see if that one's blocked
				if (isFreezeDeadlock(board, space, checked, 1))
				{
					// If yea, then let's goooooooo
					// if (i == 1) System.out.print("\t");
					// System.out.printf("\tFROZEN!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!\n", box);
					return true;
				}
		}

		// System.out.printf("\tNever mind, other boxes around it can still be pushed...\n", box);
		
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
