package solver;

import java.util.*;

public class SokoBot {
    public String solveSokobanPuzzle(int width, int height, char[][] mapData, char[][] itemsData) {
        // Initialize board and heuristic
		SokoBanBoard board = new SokoBanBoard(width, height, mapData);
		Heuristic heuristic = new Heuristic(board);

        // Generate initial state and nodes
		State initialState = generateInitialState(board, itemsData);
		if (initialState.isGoal(board)) {
			return "";
		}
		Node initialNode = new Node(initialState, null, "", heuristic);

		// Generate state generator
        StateGenerator generator = new StateGenerator(
			new HashSet<>(board.getWallSet()), 
			new HashSet<>(board.getGoalSet())
		);

		PriorityQueue<Node> frontier = new PriorityQueue<Node>(new NodeComparator());
		HashSet<State> frontierStates = new HashSet<State>();
		HashSet<Node> explored = new HashSet<Node>();

        frontier.add(initialNode);
		frontierStates.add(initialState);

        while (!frontier.isEmpty()) {
            Node currentNode = frontier.poll();
			frontierStates.remove(currentNode.state);
            explored.add(currentNode);

            List<String> actions = generator.generateActions(currentNode.state);
			for (String action : actions) {
                State newState = generator.applyAction(currentNode.state, action);
                Node newNode = new Node(newState, currentNode, action, heuristic);

				// If no valid state could be found
				if (newState == null) {
					continue;
				}

				// If the state was already explored
				// If the state already exists in the frontier
                if (explored.contains(newNode) || frontierStates.contains(newState)) {
                    continue;
                }

                // If the state results in a deadlock for a box
                if (newState.isCornerDeadlock(board.getGoalSet(), board.getWallSet()))
                	continue;

				// If the state results in a backtrack
                if (newNode.wentBack(newState))
                	continue;

                // If the state is actually the goal state
                if (newState.isGoal(board)) {
                    // Generate string of moves
					StringBuilder sb = new StringBuilder();
                    for (Node n = newNode; n.parent != null; n = n.parent) {
						sb.append(n.move);
					}

                    return sb.reverse().toString();
                }

                frontier.add(newNode);
				frontierStates.add(newState);
            }
        }

		// Otherwise, give up ;(
        return "";
    }

	State generateInitialState(SokoBanBoard board, char[][] itemsData) {
		HashSet<Coords> boxes = new HashSet<>();
        Coords player = null;
        for (int i = 0; i < board.height; i++) {
            for (int j = 0; j < itemsData[i].length; j++) {
                if (itemsData[i][j] == '@') player = new Coords(i, j);
                if (itemsData[i][j] == '$') boxes.add(new Coords(i, j));
            }
        }
		
		return new State(player, boxes);
	}
}
