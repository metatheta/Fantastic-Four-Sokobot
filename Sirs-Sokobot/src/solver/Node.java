package solver;

import java.util.Objects;

public class Node {
    public final State state;
	public final Node parent;
    public final String move;
	public final int heuristicValue;

    public Node(State state, Node parent, String move, Heuristic heuristic) {
        this.state = state;
        this.parent = parent;
        this.move = move;

		this.heuristicValue = heuristic.calculateHeuristic(state);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node other)) return false;
        // Two nodes are equal if their states are equal (not identity)
        return Objects.equals(this.state, other.state);
    }

    @Override
    public int hashCode() {
        // Only the state determines equality, so only it contributes to hashCode.
        return Objects.hashCode(state);
    }
}
