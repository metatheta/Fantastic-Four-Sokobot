package solver;

import java.util.HashMap;

/**
 * This class describes a box and the spaces surrounding
 * it where a push COULD be performed. These spaces have not
 * been validated yet.
 */
public class SearchedBox {
	/** Coordinates of the box */
	public final Coords box;
	/** HashMap mapping a space to the push move that COULD be performed */
	public final HashMap<Coords, Character> spaces;

	public SearchedBox(Coords box, HashMap<Coords, Character> spaces) {
		this.box = box;
		this.spaces = spaces;
	}
}
