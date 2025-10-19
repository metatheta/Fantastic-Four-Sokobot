package solver;
import java.util.HashMap;
import java.lang.Math;

public class Heuristic {
	public final SokoBanBoard board;
	private HashMap<Integer, Integer> stateValues;
	private HashMap<Integer, Integer> boxValues;
	
	public Heuristic(SokoBanBoard board) {
		this.board = board;
		this.stateValues = new HashMap<Integer, Integer>(); 
		this.boxValues = new HashMap<Integer, Integer>(); 
	}

	public int calculateHeuristic(State state) {
		if (stateValues.containsKey(state.hashCode()))
			return stateValues.get(state.hashCode());

        //Compute heuristic value
		int value = 0;
        value += manhattanBoxGoal(state);

        /*
        if (false && state.pushState) {
            value += manhattanBoxGoal(state) - state.goalCount; //smarter but slower
        } else if (false) {
            value += manhattanPlayerBox(state); //faster but dumber
        }*/

		stateValues.put(state.hashCode(), value);
		return value;
	}

    //This is too slow
    private int manhattanBoxGoal(State state) {
        int value = 0;

        //Gets the manhattan distance of box to goal
        for (Coords box : state.boxes) {
            // Compare to positions of other boxes

            int minimumDistance = 99999;

            if (boxValues.containsKey(box.hashCode())) {
                minimumDistance = boxValues.get(box.hashCode());
            } else {
                for (Coords goal : board.goals) {
                    // Compute Manhattan distance

                    int distance =
                            (int)Math.abs(goal.col - box.col)
                                    + (int)Math.abs(goal.row - box.row);

                    if (distance < minimumDistance) {
                        minimumDistance = distance;
                    }
                }
                boxValues.put(box.hashCode(), minimumDistance);
            }

            value += minimumDistance;
        }

        return value;
    }

    private int distancePlayerBox(State state) {
        int value = 0;

        //Gets the manhattan distance of player to closest box
        for (Coords box : state.boxes) {
            // Compare to positions of other boxes

            int minimumDistance = 99999;

            if (boxValues.containsKey(box.hashCode())) {
                minimumDistance = boxValues.get(box.hashCode());
            } else {
                Coords goal = state.player;
                int distance =
                        (int) Math.sqrt((int)Math.abs(goal.col - box.col)
                                + (int)Math.abs(goal.row - box.row));

                if (distance < minimumDistance) {
                    minimumDistance = distance;
                }
            }

            if (minimumDistance < value || value == 0)
                value = minimumDistance;
        }

        return value;
    }
}
