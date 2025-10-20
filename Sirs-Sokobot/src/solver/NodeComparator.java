package solver;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {
    //Heuristic comparison
	public int compare(Node node1, Node node2) {
		if (node1.heuristic < node2.heuristic) {
			return -1;
		} else if (node1.heuristic > node2.heuristic) {
			return 1;
		} else {
			return 0;
		}
	}
}
