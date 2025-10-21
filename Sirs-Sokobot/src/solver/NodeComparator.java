package solver;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {
	public int compare(Node node1, Node node2) {
		// GBFS
		if (node1.heuristic < node2.heuristic) {
			return -1;
		} else if (node1.heuristic > node2.heuristic) {
			return 1;
		} else {
			return 0;
		}

		// A*
		// if (node1.totalCost < node2.totalCost) {
		// 	return -1;
		// } else if (node1.totalCost > node2.totalCost) {
		// 	return 1;
		// } else {
		// 	return 0;
		// }
	}
}
