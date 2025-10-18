package solver;

import java.util.HashMap;

public class Heuristic {
	public final SokoBanBoard board;
	private HashMap<State, Integer> values;
	
	public Heuristic(SokoBanBoard board) {
		this.board = board;
		this.values = new HashMap<State, Integer>(); 
	}

	public int calculateHeuristic(State state) {
		if (values.containsKey(state)) {
			return values.get(state);
		}

		int value = 0;

		for (Coords goalCoords : board.getGoalSet()) {
			// Compare to positions of other boxes

			int minimumDistance = 99999;

			for (Coords boxCoords : state.boxes) {
				// Compute Manhattan distance
				
				int distance = 
					(int)Math.abs(goalCoords.col - boxCoords.col)
					+ (int)Math.abs(goalCoords.row - boxCoords.row);
				
				if (distance < minimumDistance) {
					minimumDistance = distance;
				}
			}

			value += minimumDistance;
		}

		values.put(state, value);
		return value;
	}
}
