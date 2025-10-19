package solver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

public class SokoBot {
    public String solveSokobanPuzzle(int width, int height, char[][] mapData, char[][] itemsData) {
        // Initialize board
		SokoBanBoard board = new SokoBanBoard(width, height, mapData);
		Heuristic heuristic = new Heuristic(board);
		NodeGenerator nodeGenerator = new NodeGenerator(board);
		Squarelock squarelock = new Squarelock(mapData);
		squarelock.squarelockCheck();

        // Generate initial state and node
		State initialState = generateInitialState(board, itemsData);
		Node initialNode = new Node(initialState, null, "", heuristic);

		// Initialize frontier and explored set
		PriorityQueue<Node> frontier = new PriorityQueue<Node>(new NodeComparator());
		HashSet<State> explored = new HashSet<State>();
        frontier.add(initialNode);

        while (!frontier.isEmpty()) {
            Node currentNode = frontier.poll();

			// If the state is actually the goal state
			if (currentNode.state.isGoal(board)) {
				// Generate string of moves
				StringBuilder sb = new StringBuilder();
				for (Node n = currentNode; n.parent != null; n = n.parent) {
					sb.append(n.move);
				}

				return sb.reverse().toString();
			}

			ArrayList<Node> nodes = nodeGenerator.generateNodes(currentNode, heuristic);
			for (Node node : nodes) {
				if (explored.contains(node.state)) {
					continue;
				}

				if (node.state.isDeadlock(board.getGoalSet(), board.getWallSet(),
					squarelock.getSquarelockSet())) {
					continue;
				}

				frontier.add(node);
			}

			explored.add(currentNode.state);
        }

		// Otherwise, give up ;(
		System.out.println("fuck");
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
