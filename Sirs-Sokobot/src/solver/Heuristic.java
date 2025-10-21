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
		int value = distancePlayerBox(state) + manhattanBoxGoal(state);
        //value += distancePlayerBox(state); //Search closest box + manhattan distance to a goal state
        // if (state.pushState)
        //value += manhattanBoxGoal(state); //When pushing the box compute total manhattan distance

		stateValues.put(state.hashCode(), value);
		return value;
	}

    /**
     * Compares manhattan distance of all boxes to the closest goal and takes the minimum
     * @param state the current state
     * @return the smallest minimum manhattan distance among all goals
     */
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

    /**
     * Gets minimum euclideanish distance from player to closest box and manhattan distance of that box
     * to the closest goal
     * @param state the current state
     * @return manhattan heuristic idk player -> box -> goal
     */
    private int distancePlayerBox(State state) {
        int value = 0;
        int manhattan = 0;
        boolean isGoal = false;

        //Gets the manhattan distance of player to closest box
        for (Coords box : state.boxes) {
            // Compare to positions of other boxes
            for (Coords goal : board.goals) {
                if (box.row == goal.row && box.col == goal.col) {
                    isGoal = true;
                    break;
                } else
                    manhattan = (int)Math.abs(goal.col - box.col)
                            + (int)Math.abs(goal.row - box.row);
            }

            if (isGoal)
                continue;

            int minimumDistance = 99999;
            if (boxValues.containsKey(box.hashCode())) {
                minimumDistance = boxValues.get(box.hashCode());
            } else {
                int distance =
                        (int) Math.sqrt((int)Math.abs(state.player.col - box.col)
                                + (int)Math.abs(state.player.row - box.row));

                if (distance < minimumDistance) {
                    minimumDistance = distance;
                }
            }

            //if (value == 0 || minimumDistance < value)
                value += minimumDistance;
        }
        //value = 0;

        return value + manhattan;
    }
}
