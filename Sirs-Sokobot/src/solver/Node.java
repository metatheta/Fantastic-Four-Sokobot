package solver;

import java.util.Objects;

public class Node {
    public final Node parent;
    public final State state;
    public final int priorityValue; // heuristic / priority (A*, etc.)
    public final String move;       // direction enum (e.g. "u","r","d","l")

    public Node(State state, Node parent, int priorityValue, String move) {
        this.state = state;
        this.parent = parent;
        this.priorityValue = priorityValue;
        this.move = move;
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
