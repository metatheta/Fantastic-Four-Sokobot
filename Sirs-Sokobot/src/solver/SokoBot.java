package solver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

public class SokoBot {
    public String solveSokobanPuzzle(int width, int height, char[][] mapData, char[][] itemsData) {
        // Initialize board
		SokoBanBoard board = new SokoBanBoard(mapData);
		Heuristic heuristic = new Heuristic(board);
		NodeGenerator nodeGenerator = new NodeGenerator(board);
		Squarelock squarelock = new Squarelock(mapData);
		squarelock.squarelockCheck();

        // Generate initial state and node
		State initialState = generateInitialState(board, itemsData);
		Node initialNode = new Node(initialState, null, heuristic);

		// Initialize frontier and explored set
		PriorityQueue<Node> frontier = new PriorityQueue<Node>(new NodeComparator());
		HashSet<State> explored = new HashSet<State>();
        frontier.add(initialNode);

        while (!frontier.isEmpty()) {
            Node currentNode = frontier.poll();
			explored.add(currentNode.state);

			if (isGoalState(currentNode.state, board)) {
				return generatePath(currentNode);
			}

			ArrayList<Node> nodes = nodeGenerator.generateNodes(currentNode, board, heuristic);
			for (Node node : nodes) {
				if (explored.contains(node.state))
					continue;
					
				if (node.state.isDeadlock(board, squarelock.getSquarelockSet()))
					continue;
				
				frontier.add(node);
			}
        }

		// Otherwise, give up ;(
		System.out.println("SHIT");
        return "";
    }

	private State generateInitialState(SokoBanBoard board, char[][] itemsData) {
		Coords player = null;
		HashSet<Coords> boxes = new HashSet<Coords>();
        
        for (int row = 0; row < itemsData.length; row++) {
            for (int col = 0; col < itemsData[row].length; col++) {
                if (itemsData[row][col] == '@')
					player = new Coords(row, col);

                if (itemsData[row][col] == '$')
					boxes.add(new Coords(row, col));
            }
        }

		return new State(player, boxes);
	}

	private String generatePath(Node start) {
		StringBuilder sb = new StringBuilder();
		int totalCost = 0;

		for (Node n = start; n.parent != null; n = n.parent) {
			// TODO: some wacko shit here
			
			// sb.append(n.lastAction);
			sb.append('d');
		}

		String result = sb.reverse().toString();
		System.out.println(result);
		System.out.printf("Total cost: %d\n", totalCost);
		return result;
	}

	private boolean isGoalState(State state, SokoBanBoard board) {
		return state.boxes.equals(board.goals); 
	}
}
