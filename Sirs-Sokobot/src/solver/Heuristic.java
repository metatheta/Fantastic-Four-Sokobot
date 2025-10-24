package solver;
import java.util.HashMap;
import java.lang.Math;

public class Heuristic {
	public final SokoBanBoard board;
    public static HashMap<Integer, Integer> manhattanGoal = new HashMap<Integer, Integer>();
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

        int playerBoxDistance;
        int goalBoxMin, playerBoxMin;
        int value = 0;
        for (Coords box : state.boxes) {
            playerBoxMin = 99999;

            //compute minimum manhattan distance from box to nearest goal
            if (boxValues.containsKey(box.hashCode())) {
                goalBoxMin = boxValues.get(box.hashCode());
            } else {
                goalBoxMin = manhattanGoal.get(box.hashCode());
                boxValues.put(box.hashCode(), goalBoxMin);
            }

            //Compute manhattan distance from player to current box
            playerBoxDistance = Math.abs(state.player.col - box.col)
                    + Math.abs(state.player.row - box.row);
            if (playerBoxDistance < playerBoxMin) { //update the minimum
                playerBoxMin = playerBoxDistance;
            }

            //add values
            value += goalBoxMin + playerBoxMin;
        }
		stateValues.put(state.hashCode(), value);
		return value;
	}

    /**
     * Precomputes the manhattan distance of all squares to the nearest goal
     * @param map the full map layout
     * @param board the board object containing the list of goals
     */
    public static void precomputeManhattanGoal(char[][] map, SokoBanBoard board) {
        int map_width = map.length;
        int map_length = map[0].length;
        int distance, minDistance;

        for (int row = 0; row < map_width; row ++)
        {
            for (int col = 0; col < map_length; col ++) {
                minDistance = 9999;
                for (Coords goal : board.goals) {
                    distance = Math.abs(goal.col - col) + Math.abs(goal.row - row);
                    if (distance < minDistance) {
                        minDistance = distance;
                    }
                }
                // SOMETHING GWEL CHANGED V
                manhattanGoal.put(new Coords(row, col).hashCode(), minDistance);
            }
        }
    }
}
