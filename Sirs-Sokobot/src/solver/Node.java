package solver;

import java.util.Objects;

public class Node {
    public final State state;
	public final Node parent;
    public final String move;
	public final int heuristicValue;

    public Node(State state, Node parent, String move, Heuristic heuristic) {
        this.state = state; //contains the state
        this.parent = parent;
        this.move = move;
		this.heuristicValue = heuristic.calculateHeuristic(state);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Node n))
			return false;
		
        return Objects.equals(this.state, n.state);
    }

    @Override
    public int hashCode() {
        return this.state.hashCode();
    }
}
