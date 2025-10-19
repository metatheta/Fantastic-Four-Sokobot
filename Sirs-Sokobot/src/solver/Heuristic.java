package solver;

import java.util.HashMap;

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

		int value = 0;

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

		stateValues.put(state.hashCode(), value);
		return value;
	}
}
