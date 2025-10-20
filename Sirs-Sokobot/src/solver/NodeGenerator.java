package solver;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;

/***********************************
 *                                 *
 *    I HAVE WRITTEN               *
 *           WHAT MAY BE           *
 *        THE WORST CODE EVER      *
 *                                 *
 ***********************************/

public class NodeGenerator {
	private SokoBanBoard board;

	public NodeGenerator(SokoBanBoard board) {
		this.board = board;
	}
	
	public ArrayList<Node> generateNodes(Node node, SokoBanBoard board, Heuristic heuristic) {	
		/*
			Generate possible nodes from a node
			1) Starting from the player position in a state, search for
			   a path towards nearby accessible boxes
				- Just a simple BFS/flood-fill could probably work here
				- Find the Coords of boxes with this search
				- Consider boxes as walls
				- Coords found in the BFS search are stored somewhere
			2) Find boxes that can be legally pushed by the player
				- A box can be legally pushed from a side if that side
				  existed in the BFS search (and if there's space of course)
			3) Generate the new states and nodes from the theoretical pushing
			   of these boxes
				- Deadlocks resulting from these pushes are also checked here
		*/
		
		ArrayList<Node> nodes = new ArrayList<Node>();

		// Search playerSearch = search(node.state, board);
		// ArrayList<State> states = generateStates(node.state, playerSearch);
		// for (State state : states) {
		// 	Node newNode = new Node(state, node, heuristic);
		// 	nodes.add(newNode);
		// }

		System.out.println(floodFill(node.state, board).toString());
		// Search search = floodFill(node.state, board);
		// ArrayList<State> states = generateStates(node.state, search);
		
		return nodes;
	}

	private Search floodFill(State state, SokoBanBoard board) {
		HashSet<Coords> boxSpaces = new HashSet<>();
		HashSet<Coords> boxes = new HashSet<>();
		
		ArrayDeque<Coords> frontier = new ArrayDeque<>();
		HashSet<Coords> explored = new HashSet<>();
		frontier.add(state.player);

		char[] dirs = {'l', 'u', 'd', 'r'};

		while (!frontier.isEmpty()) {
			Coords co = frontier.poll();
			explored.add(co);

			for (char dir : dirs) {
				Coords ne = applyMove(co, dir);

				if (board.walls.contains(ne))
					continue;

				if (explored.contains(ne))
					continue;

				if (state.boxes.contains(ne)) {
					boxes.add(ne);
					continue;
				}

				frontier.add(ne);
			}
		}

		for (Coords box : boxes) {
			for (char dir : dirs) {
				Coords ne = applyMove(box, dir);
				if (explored.contains(ne))
					boxSpaces.add(ne);
			}
		}

		return new Search(boxSpaces, boxes);
	}

	private Coords applyMove(Coords start, char action) {
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

	// private Search search(State state, SokoBanBoard board) {
	// 	HashSet<Coords> searched = new HashSet<Coords>();
	// 	HashSet<Coords> pushableBoxes = new HashSet<Coords>();

	// 	ArrayDeque<Coords> frontier = new ArrayDeque<Coords>();
	// 	HashSet<Coords> explored = new HashSet<Coords>();
	// 	frontier.add(state.player);
 
	// 	while (!frontier.isEmpty()) {
	// 		Coords cur = frontier.poll();
	// 		explored.add(cur);

	// 		Coords[] dirs = {
	// 			new Coords(cur.row - 1, cur.col), // Up
	// 			new Coords(cur.row, cur.col + 1), // Right
	// 			new Coords(cur.row, cur.col - 1), // Left
	// 			new Coords(cur.row + 1, cur.col)  // Down
	// 		};
			
	// 		for (Coords dir : dirs) {
	// 			if (!board.walls.contains(dir))
	// 				if (!state.boxes.contains(dir))
	// 					frontier.add(dir);
	// 				else
	// 					pushableBoxes.add(dir);
	// 		}
	// 	}
		
	// 	return new Search(searched, pushableBoxes);
	// }

	// private ArrayList<State> generateStates(State state, Search search) {
	// 	ArrayList<State> states = new ArrayList<State>();
	// 	for (Coords box : search.pushableBoxes) {
	// 		Coords up = box.clone();
	// 		applyMove(up, 'd');
	// 		if (search.searched.contains(up)
	// 			&& isActionValid(state, box, 'd')) {
	// 			State newState = generateState(state, up, 'd');
	// 			states.add(newState);
	// 		}
			
	// 		Coords right = box.clone();
	// 		applyMove(right, 'l');
	// 		if (search.searched.contains(right)
	// 			&& isActionValid(state, box, 'l')) {
	// 			State newState = generateState(state, right, 'l');
	// 			states.add(newState);
	// 		}

	// 		Coords left = box.clone();
	// 		applyMove(left, 'r');
	// 		if (search.searched.contains(left)
	// 			&& isActionValid(state, box, 'r')) {
	// 			State newState = generateState(state, left, 'r');
	// 			states.add(newState);
	// 		}

	// 		Coords down = box.clone();
	// 		applyMove(down, 'u');
	// 		if (search.searched.contains(down)
	// 			&& isActionValid(state, box, 'u')) {
	// 			State newState = generateState(state, down, 'u');
	// 			states.add(newState);
	// 		}

	// 	}
	// 	return states;
	// }

	// private boolean isActionValid(State state, Coords target, char action) {
	// 	if (board.walls.contains(target)) {
	// 		return false;
	// 	}

	// 	if (state.boxes.contains(target)) {
	// 		Coords pushTarget = target.clone();
	// 		applyMove(pushTarget, action);

    //         return !board.walls.contains(pushTarget)
    //                 && !state.boxes.contains(pushTarget);
	// 	}
		
	// 	return true;
	// }

	// private void applyMove(Coords coords, char action) {
	// 	int r = 0, c = 0;
	// 	switch (action) {
	// 		case 'u': r = -1; break;
	// 		case 'r': c = 1; break;
	// 		case 'l': c = -1; break;
	// 		case 'd': r = 1; break;
	// 	}

	// 	coords.row += r;
	// 	coords.col += c;
	// }

	// private State generateState(State state, Coords player, char action) {
	// 	Coords newPlayer = player.clone();
	// 	applyMove(newPlayer, action);
		
	// 	HashSet<Coords> newBoxes = new HashSet<Coords>(state.boxes);
	// 	if (newBoxes.contains(newPlayer)) {
	// 		newBoxes.remove(newPlayer);
	// 		Coords newBox = newPlayer.clone();
	// 		applyMove(newBox, action);
	// 		newBoxes.add(newBox);
	// 	}
		
	// 	return new State(newPlayer, newBoxes);
	// }
}