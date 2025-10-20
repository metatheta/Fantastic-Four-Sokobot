package solver;

import java.util.Arrays;
import java.util.HashSet;

public class Search {
	public final HashSet<Coords> boxSpaces;
	public final HashSet<Coords> boxes;

	public Search(HashSet<Coords> boxSpaces, HashSet<Coords> boxes) {
		this.boxSpaces = boxSpaces;
		this.boxes = boxes;
	}

	@Override
	public String toString() {
		return Arrays.toString(boxSpaces.toArray()) + '\n' + Arrays.toString(boxes.toArray());
	}
}
