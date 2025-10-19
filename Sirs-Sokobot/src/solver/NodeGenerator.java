package solver;

import java.util.ArrayList;
import java.util.HashSet;

public class NodeGenerator {
	private SokoBanBoard board;

	public NodeGenerator(SokoBanBoard board) {
		this.board = board;
	}
	
	public ArrayList<Node> generateNodes(Node node, Heuristic heuristic) {
		ArrayList<Node> nodes = new ArrayList<Node>();
		
		ArrayList<Character> actions = generateActions(node.state);
		for (char action : actions) {
			State newState = generateState(node.state, action);
			Node newNode = new Node(newState, node, Character.toString(action), heuristic);
			nodes.add(newNode);
		}
		
		return nodes;
	}

	private ArrayList<Character> generateActions(State state) {
		ArrayList<Character> actions = new ArrayList<Character>();
		Coords target = state.player.clone();

		char[] choices = {'u', 'r', 'l', 'd'};
		for (char choice : choices) {
			target.row = state.player.row;
			target.col = state.player.col;
			applyMove(target, choice);

			if (isActionValid(state, target, choice)) {
				actions.add(choice);
			}
		}

		return actions;
	}

	private boolean isActionValid(State state, Coords target, char action) {
		if (board.getWallSet().contains(target)) {
			return false;
		}

		if (state.boxes.contains(target)) {
			Coords pushTarget = target.clone();
			applyMove(pushTarget, action);

			// if (board.getWallSet().contains(pushTarget)
			// 	|| state.boxes.contains(pushTarget)
			// 	|| board.deadlocks.contains(pushTarget)) {
			if (board.getWallSet().contains(pushTarget)
				|| state.boxes.contains(pushTarget)) {
				return false;
			} else {
				return true;
			}
		}
		
		return true;
	}

	private void applyMove(Coords coords, char action) {
		int r = 0, c = 0;
		switch (action) {
			case 'u': r = -1; break;
			case 'r': c = 1; break;
			case 'l': c = -1; break;
			case 'd': r = 1; break;
		}

		coords.row += r;
		coords.col += c;
	}

	private State generateState(State state, char action) {
		// Move player
		Coords player = state.player.clone();
		applyMove(player, action);

		// Move box, if applicable
		HashSet<Coords> boxes = new HashSet<Coords>(state.boxes);
		if (boxes.contains(player)) {
			boxes.remove(player);
			Coords newBox = player.clone();
			applyMove(newBox, action);
			boxes.add(newBox);
		}
		
		return new State(player, boxes);
	}
}