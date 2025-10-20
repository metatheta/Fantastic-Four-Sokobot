package solver;

import java.util.Objects;

public class Node {
    public final State state;
	public final Node parent;
	public final int heuristic;

    public Node(State state, Node parent, Heuristic heuristic) {
        this.state = state;
        this.parent = parent;
		this.heuristic = heuristic.calculateHeuristic(state);
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
