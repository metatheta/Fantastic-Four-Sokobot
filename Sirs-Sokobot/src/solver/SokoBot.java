package solver;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

public class SokoBot {
    public String solveSokobanPuzzle(int width, int height, char[][] mapData, char[][] itemsData) {
        // Initialize board
		SokoBanBoard board = new SokoBanBoard(mapData);
		Heuristic heuristic = new Heuristic(board);
        Heuristic.precomputeManhattanGoal(mapData, board);
		NodeGenerator nodeGenerator = new NodeGenerator(board);
		Deadlocks deadlocks = new Deadlocks(mapData);
		// System.out.println(deadlocks.toString());

        // Generate initial state and node
		State initialState = generateInitialState(board, itemsData);
		Node initialNode = new Node(initialState, null, heuristic, 0);

		// Initialize frontier and explored set
        PriorityQueue<Node> frontier = new PriorityQueue<Node>(initialNode);
		HashSet<State> explored = new HashSet<State>();

        frontier.add(initialNode);
        while (!frontier.isEmpty()) {
            Node currentNode = frontier.poll();
			explored.add(currentNode.state);

			if (isGoalState(currentNode.state, board)) {
				System.out.printf("States: %d\n", State.stateCount);
				return generateGoalPath(currentNode, board);
			}

			ArrayList<Node> nodes = nodeGenerator.generateNodes(currentNode, board, heuristic);
			for (Node node : nodes) {
				if (explored.contains(node.state) || node.state.isDeadlock(board, deadlocks.deadlocks))
					continue;
				
				frontier.add(node);
			}
        }

		// Otherwise, give up ;(
		System.out.println("I will kill myself");
        return "";
    }

	/**
	 * This function generates the initial state
	 * of the board on program start.
	 * 
	 * @param board Board
	 * @param itemsData Items
	 * @return The initial state what else is it going to return bro
	 */
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

		return new State(player, boxes, ' ');
	}

	/**
	 * This function backtracks through the nodes,
	 * beginning from the goal node until the initial node,
	 * and reconstructs the path that could be taken from each node.
	 * The idea is to search for the path using another search
	 * algorithm, this time another dirty BFS implementation,
	 * then store it somewhere. The strings are combined together
	 * then reversed to theoretically form the full path towards
	 * the goal node.
	 * 
	 * @param goal Goal, acting as start position
	 * @param board Board
	 * @return Full string
	 */
	private String generateGoalPath(Node goal, SokoBanBoard board) {
		StringBuilder sb = new StringBuilder();

		for (Node n = goal; n.parent != null; n = n.parent) {			
			String path = searchPath(n.state, n.parent.state, board);
			sb.append(path);
		}

		String result = sb.reverse().toString();
		System.out.println("Herbie's Path -> " + result);
        System.out.println("Herbie Counted: " + result.length() + " moves");
		return result;
	}

	/**
	 * This function searches for the path from a given set of coordinates
	 * to another set of coordinates, keeping in mind the walls and the
	 * boxes, which act as walls for our purposes.
	 * Afterwards, the path is reconstructed by backtracking from the found
	 * coordinates to the end. Each player move is found by comparing two coordinates
	 * and finding their difference. An external function is used for this.
	 * 
	 * @param from Start coordinates
	 * @param to Ending coordinates to find
	 * @param board Board
	 * @return path string
	 */
	private String searchPath(State from, State to, SokoBanBoard board) {
		Coords start = NodeGenerator.applyMove(from.player, NodeGenerator.invertDirection(from.last));
		
		ArrayDeque<SimpleImmutableEntry<Coords,Coords>> frontier = new ArrayDeque<>();
		ArrayDeque<SimpleImmutableEntry<Coords,Coords>> explored = new ArrayDeque<>();
		HashSet<Coords> exploredSet = new HashSet<>(); 
		frontier.add(new SimpleImmutableEntry<>(start, null));

        char[] dirs = {'l', 'u', 'd', 'r'};

        // Breadth-first search to find the path
		while (!frontier.isEmpty()) {
			SimpleImmutableEntry<Coords,Coords> co = frontier.poll();
			explored.push(co);
			exploredSet.add(co.getKey());

			// If the path is found, stop
			// The goal coordinates should be at the top of the
			// explored stack
			if (co.getKey().equals(to.player)) {
				break;
			}

			for (char dir : dirs) {
				Coords ne = NodeGenerator.applyMove(co.getKey(), dir);

				if (board.walls.contains(ne))
					continue;

				if (exploredSet.contains(ne))
					continue;

				if (to.boxes.contains(ne)) 
					continue;

				frontier.add(new SimpleImmutableEntry<>(ne, co.getKey()));
			}
		}

		// Reconstruct the path by going through the
		// explored stack of coordinates
		// Similar to how sir does it on Excel
		ArrayDeque<SimpleImmutableEntry<Coords,Coords>> path = new ArrayDeque<>();
		path.push(explored.pop());
		while (!explored.isEmpty()) {
			SimpleImmutableEntry<Coords,Coords> ex = explored.pop();

			if (ex.getValue() == null) {
				path.push(ex);
				break;
			}

			if (ex.getKey().equals(path.getFirst().getValue())) {
				path.push(ex);
			}
		}

		StringBuilder sb = new StringBuilder();
		sb.append(from.last);
		while (!path.isEmpty()) {
			SimpleImmutableEntry<Coords,Coords> co = path.pollFirst();

			if (co.getValue() == null) {
				continue;
			}

			char direction = NodeGenerator.getDirection(co.getKey(), co.getValue());
			sb.append(direction);
		}

		return sb.toString();
	}

	private boolean isGoalState(State state, SokoBanBoard board) {
		// System.out.printf("%s\n", state.boxes);
		// System.out.printf("%s\n", board.goals);
		return state.boxes.equals(board.goals); 
	}
}
