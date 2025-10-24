package solver;

import java.util.Comparator;
import java.util.Objects;

public class Node implements Comparator<Node> {
    public final State state;
	public final Node parent;
	public final int heuristic;
	public final int totalCost;

    public Node(State state, Node parent, Heuristic heuristic, int cost) {
        this.state = state;
        this.parent = parent;
		this.heuristic = heuristic.calculateHeuristic(state);
		this.totalCost = this.heuristic + cost;
    }

    @Override
    public int compare(Node node1, Node node2) {
		return Integer.compare(node1.heuristic, node2.heuristic);
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

	@Override
	public String toString() {
		return "State #" + hashCode() + ": {" + state + "}, Heuristic: " + heuristic;
	}
}
