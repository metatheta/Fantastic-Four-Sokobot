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
		NodeGenerator nodeGenerator = new NodeGenerator(board);
		Squarelock squarelock = new Squarelock(mapData);
		squarelock.squarelockCheck();
		// System.out.println(squarelock.toString());

        // Generate initial state and node
		State initialState = generateInitialState(board, itemsData);
		Node initialNode = new Node(initialState, null, heuristic, 0);

		// Initialize frontier and explored set
		PriorityQueue<Node> frontier = new PriorityQueue<Node>(new NodeComparator());
		HashSet<State> explored = new HashSet<State>();
        frontier.add(initialNode);

        while (!frontier.isEmpty()) {
            Node currentNode = frontier.poll();
			explored.add(currentNode.state);

			// System.out.println("[CURRENT NODE]");
			// System.out.println(currentNode);

			if (isGoalState(currentNode.state, board)) {
				// System.out.println("\n========================");
				// System.out.println("GENERATING GOAL PATH NOW");
				// System.out.println("========================\n");

				return generateGoalPath(currentNode, board);
			}

			ArrayList<Node> nodes = nodeGenerator.generateNodes(currentNode, board, heuristic);
			// System.out.println("-");
			// System.out.println(nodes);
			// System.out.println();
			for (Node node : nodes) {
				if (explored.contains(node.state))
					continue;
					
				if (node.state.isDeadlock(board, squarelock.getSquarelockSet()))
					continue;
				
				frontier.add(node);
			}
        }

		// Otherwise, give up ;(
		System.out.println("FUCKING FUCK FUCK !!!!!!!! :(");
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

		return new State(player, boxes, ' ');
	}

	/**
	 * This function backtracks through the nodes,
	 * beginning from the goal node until the initial node,
	 * and reconstructs the path that could be taken from each node.
	 * 
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
		// int totalCost = 0;

		for (Node n = goal; n.parent != null; n = n.parent) {
			// Coords from = n.state.player;
			// Coords to = n.parent.state.player;
			
			// String path = searchPath(from, to, n.parent.state, n.state.last, board);
			String path = searchPath(n.state, n.parent.state, board);
			// System.out.printf("FROM: %s\n", n.state);
			// System.out.printf("TO: %s\n", n.parent.state);
			// System.out.printf("GIVEN: %s\n", n.parent.state);
			// System.out.printf("SUB-PATH -> %s\n\n", path);
			sb.append(path);
		}

		String result = sb.reverse().toString();
		System.out.println("FINAL -> " + result);
        System.out.println("Total moves: " + result.length());
		return result;
	}

	/**
	 * This function searches for the path from a given set of coordinates
	 * to another set of coordinates, keeping in mind the walls and the
	 * boxes, which act as walls for our purposes.
	 * 
	 * Afterwards, the path is reconstructed by backtracking from the found
	 * coordinates to the end. Each player move is found by comparing two coordinates
	 * and finding their difference. An external function is used for this.
	 * 
	 * @param from Start coordinates
	 * @param to Ending coordinates to find
	 * @param board Board
	 * @return path string
	 */
	// private String searchPath(Coords start, Coords end, State state, char ababa, SokoBanBoard board) {
	private String searchPath(State from, State to, SokoBanBoard board) {
		Coords start = NodeGenerator.applyMove(from.player, NodeGenerator.invertDirection(from.last));
		
		ArrayDeque<SimpleImmutableEntry<Coords,Coords>> frontier = new ArrayDeque<>();
		ArrayDeque<SimpleImmutableEntry<Coords,Coords>> explored = new ArrayDeque<>();
		HashSet<Coords> exploredSet = new HashSet<>(); 
		frontier.add(new SimpleImmutableEntry<>(start, null));

        char[] dirs = {'l', 'u', 'd', 'r'};
        /*
        char[] dirs = {'l', 'u', 'r', 'd'};
        char[] dirs = {'l', 'd', 'u', 'r'};
        char[] dirs = {'l', 'd', 'r', 'u'};
        char[] dirs = {'l', 'r', 'u', 'd'};
        char[] dirs = {'l', 'r', 'd', 'u'};
        char[] dirs = {'u', 'l', 'd', 'r'};
        char[] dirs = {'u', 'l', 'r', 'd'};
        char[] dirs = {'u', 'd', 'l', 'r'};
        char[] dirs = {'u', 'd', 'r', 'l'};
        char[] dirs = {'u', 'r', 'l', 'd'};
        char[] dirs = {'u', 'r', 'd', 'l'};
        char[] dirs = {'d', 'l', 'u', 'r'};
        char[] dirs = {'d', 'l', 'r', 'u'};
        char[] dirs = {'d', 'u', 'l', 'r'};
        char[] dirs = {'d', 'u', 'r', 'l'};
        char[] dirs = {'d', 'r', 'l', 'u'};
        char[] dirs = {'d', 'r', 'u', 'l'};
        char[] dirs = {'r', 'l', 'u', 'd'};
        char[] dirs = {'r', 'l', 'd', 'u'};
        char[] dirs = {'r', 'u', 'l', 'd'};
        char[] dirs = {'r', 'u', 'd', 'l'};
        char[] dirs = {'r', 'd', 'l', 'u'};
        char[] dirs = {'r', 'd', 'u', 'l'};
         */


        // Breadth-first search to find the path
		// System.out.printf("SEARCHING FROM %s TO %s\n", start, to.player);
		while (!frontier.isEmpty()) {
			SimpleImmutableEntry<Coords,Coords> co = frontier.poll();
			explored.push(co);
			// System.out.printf("PUSHING: %s FROM %s\n", co.getKey(), co.getValue());
			exploredSet.add(co.getKey());

			// If the path is found, stop
			// The goal coordinates should be at the top of the
			// explored stack
			if (co.getKey().equals(to.player)) {
				// System.out.printf("-> FOUND!\n");
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

		// System.out.printf("EXPLORED: %s\n\n", explored);

		// Reconstruct the path by going through the
		// explored stack of coordinates
		// Similar to how sir does it on Excel
		// StringBuilder sb = new StringBuilder();
		// SimpleImmutableEntry<Coords,Coords> last = explored.pop();
		ArrayDeque<SimpleImmutableEntry<Coords,Coords>> path = new ArrayDeque<>();
		path.push(explored.pop());
		// sb.append(from.last);
		// sb.append(NodeGenerator.getDirection(last.getKey(), last.getValue()));
		// System.out.printf("SEARCHING FROM %s to %s\n", start, to.player);
		// System.out.printf("GOAL: %s FROM %s\n", path.getFirst().getKey(), path.getFirst().getValue());
		while (!explored.isEmpty()) {
			SimpleImmutableEntry<Coords,Coords> ex = explored.pop();
			// System.out.printf("CHECKING EXPLORED: %s FROM %s\n", 
				// ex.getKey(), ex.getValue());

			if (ex.getValue() == null) {
				path.push(ex);
				break;
			}

			if (ex.getKey().equals(path.getFirst().getValue())) {
				// last = ex;
				path.push(ex);
				// The direction needed to get from one set of coordinates
				// to another is found from this function
				// sb.append(NodeGenerator.getDirection(ex.getKey(), path.getLast().getValue()));
				// System.out.printf("\t> FOUND\n");
			}
		}

		StringBuilder sb = new StringBuilder();
		// System.out.printf("%s\n\n", path);
		sb.append(from.last);
		while (!path.isEmpty()) {
			SimpleImmutableEntry<Coords,Coords> co = path.pollFirst();
			// System.out.println(co);

			if (co.getValue() == null) {
				continue;
			}

			char direction = NodeGenerator.getDirection(co.getKey(), co.getValue());
			// sb.append(NodeGenerator.invertDirection(direction));
			sb.append(direction);
		}
		// System.out.printf("%s\n\n", path);

		// sb.append(NodeGenerator.getDirection(last.getKey(), last.getValue()));
		
		// frontier = null;
		// explored = null;
		// exploredSet = null;
		// System.gc();
		return sb.toString();
	}

	private boolean isGoalState(State state, SokoBanBoard board) {
		// System.out.printf("%s\n", state.boxes);
		// System.out.printf("%s\n", board.goals);
		return state.boxes.equals(board.goals); 
	}
}
