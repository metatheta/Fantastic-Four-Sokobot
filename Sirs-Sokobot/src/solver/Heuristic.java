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

    /**
     * Heuristic computation method. State is based on the sum of total manhattan distance
     * from box to nearest goal + total manhattan distance from player to a box
     * This also stores the values in a dedicated hashmap
     * @param state current state
     * @return heuristic value
     */
	public int calculateHeuristic(State state) {
		if (stateValues.containsKey(state.hashCode()))
			return stateValues.get(state.hashCode());

        int boxGoalDistance, playerBoxDistance;
        int goalBoxMin, playerBoxMin;
        int value = 0;
        for (Coords box : state.boxes) {
            playerBoxMin = goalBoxMin = 99999;

            //compute minimum manhattan distance from box to nearest goal
            if (boxValues.containsKey(box.hashCode())) {
                goalBoxMin = boxValues.get(box.hashCode());
            } else {
                //Go through each goal
                for (Coords goal : board.goals) {
                    boxGoalDistance = Math.abs(goal.col - box.col) + Math.abs(goal.row - box.row);
                    if (boxGoalDistance < goalBoxMin) {
                        goalBoxMin = boxGoalDistance;
                    }
                }
                boxValues.put(box.hashCode(), goalBoxMin);
            }

            //Compute manhattan distance from player to current box
            playerBoxDistance = Math.abs(state.player.col - box.col)
                    + Math.abs(state.player.row - box.row);
            if (playerBoxDistance < playerBoxMin) {
                playerBoxMin = playerBoxDistance;
            }

            //add values
            value += goalBoxMin + playerBoxMin;
        }
		stateValues.put(state.hashCode(), value);
		return value;
	}
}
