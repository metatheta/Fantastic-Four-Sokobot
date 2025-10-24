package solver;

import java.util.*;

public class NodeGenerator {
	private SokoBanBoard board;

	public NodeGenerator(SokoBanBoard board) {
		this.board = board;
	}
	
	public ArrayList<Node> generateNodes(Node node, SokoBanBoard board, Heuristic heuristic) {	
		ArrayList<Node> nodes = new ArrayList<Node>();
		
		// Look for all boxes available to the player, as well as
		// the spaces where these boxes COULD be pushed
		ArrayList<SearchedBox> search = floodFill(node.state, board);

		// Generate the possible states from all of the given
		// spaces for pushing
		ArrayList<State> states = generateStates(node.state, search);
		
		// Generate the nodes associated with these states
		for (State state : states) {

			if (state == null)
				continue;
			
            nodes.add(new Node(state, node, heuristic, node.totalCost + 1));
		}

		return nodes;
	}

	/**
	 * This function performs a flood-filling search to find
	 * all boxes available to the player, as well as the spaces
	 * next to those boxes where a push MAY be possible.
	 * 
	 * @param state Current state
	 * @param board Board and its static attributes
	 * @return List of SearchedBox objects
	 */
	private ArrayList<SearchedBox> floodFill(State state, SokoBanBoard board) {
		ArrayList<SearchedBox> searchedBoxes = new ArrayList<>();
		HashSet<Coords> boxes = new HashSet<>();
		
		// Initialize simple breadth-first search
		PriorityQueue<Coords> frontier = new PriorityQueue<>();
		HashSet<Coords> explored = new HashSet<>();
		frontier.add(state.player);

		char[] dirs = {'l', 'u', 'd', 'r'};

		while (!frontier.isEmpty()) {
			Coords ne, co = frontier.poll();
			explored.add(co);

			for (char dir : dirs) {
				ne = applyMove(co, dir);

				if (board.walls.contains(ne) || explored.contains(ne))
					continue;

				if (state.boxes.contains(ne)) {
					boxes.add(ne);
					continue;
				}

				frontier.add(ne);
			}
		}
		for (Coords box : boxes) {
			HashMap<Coords, Character> boxSpaces = new HashMap<>();
            Coords ne;
			for (char dir : dirs) {
				ne = applyMove(box, dir);
				if (board.walls.contains(ne) || boxes.contains(ne))
					continue;
				
				if (explored.contains(ne))
					boxSpaces.put(ne, invertDirection(dir));
			}
			SearchedBox newSearchedBox = new SearchedBox(box, boxSpaces);
			searchedBoxes.add(newSearchedBox);
		}

		return searchedBoxes;
	}

	/**
	 * This function generates the possible states from the earlier-found
	 * boxes and their possible pushes.
	 * 
	 * @param state Current state
	 * @param searchedBoxes Available boxes and push spaces
	 * @return List of states
	 */
	private ArrayList<State> generateStates(State state, ArrayList<SearchedBox> searchedBoxes) {
        Set<Coords> spaces;
        State newState;
		ArrayList<State> states = new ArrayList<State>();
		for (SearchedBox searchedBox : searchedBoxes) {
            spaces = searchedBox.spaces.keySet();
			for (Coords boxSpace : spaces) {
				if (board.walls.contains(boxSpace) || state.boxes.contains(boxSpace))
					continue;

				if (!isActionValid(this.board, state.boxes, searchedBox.box, searchedBox.spaces.get(boxSpace))) {
					continue;
				}
				
				newState = generateState(boxSpace, state.boxes, searchedBox.spaces.get(boxSpace));
				states.add(newState);
			}
		}
		return states;
	}

	/**
	 * This function generates the state.
	 * 
	 * Okay it looks esoteric and weird asf but look at it like this: we pass in
	 * where the player would be BEFORE making the action, alongside the
	 * boxes and the actual action. THEN it applies the action. You'll see
	 * 
	 * @param player Position of player before action
	 * @param boxes Positions of boxes
	 * @param action Action to be performed
	 * @return Brand new state
	 */
	private State generateState(Coords player, HashSet<Coords> boxes, char action) {
		// Apply action to player
		Coords newPlayer = applyMove(player.clone(), action);
		HashSet<Coords> newBoxes = new HashSet<Coords>(boxes);

		// If any of the boxes gets moved as a result
		// of the previous action being applied...
		if (newBoxes.contains(newPlayer)) {
			// Move the box!
			// At this point it is assumed that the movement
			// is always legal as it was vetted earlier
			// (...........Unless its wrong)
			newBoxes.remove(newPlayer);
			Coords newBox = applyMove(newPlayer.clone(), action);
			newBoxes.add(newBox);
		}

		if (newBoxes.size() != board.goals.size())
			return null;
		
		return new State(newPlayer, newBoxes, action);
	}

	public static boolean isActionValid(SokoBanBoard board, HashSet<Coords> boxes, Coords from, char action) {		
		Coords target = applyMove(from, action);

		if (board.walls.contains(target))
			return false;

		if (boxes.contains(target)) {
			Coords pushTarget = applyMove(target, action);

            return !board.walls.contains(pushTarget)
                    && !boxes.contains(pushTarget);
		}
		
		return true;
	}

	public static Coords applyMove(Coords start, char action) {
		Coords co = start.clone();
		
		int r = 0, c = 0;
		switch (action) {
			case 'u': r = -1; break;
			case 'r': c = 1; break;
			case 'l': c = -1; break;
			case 'd': r = 1; break;
		}

		co.row += r;
		co.col += c;
		return co;
	}

	public static char getDirection(Coords start, Coords end) {
		int rDiff = start.row - end.row;
		int cDiff = start.col - end.col;

        if (cDiff != 0)
        {
            if (cDiff < 0)
                return 'r';
            return 'l';
        }

        if (rDiff != 0)
        {
            if (rDiff < 0)
                return 'd';
            return 'u';
        }
		
		return ' ';
	}

	public static char invertDirection(char dir) {
        return switch (dir) {
            case 'u' -> 'd';
            case 'd' -> 'u';
            case 'l' -> 'r';
            case 'r' -> 'l';
            default -> ' ';
        };
	}
}