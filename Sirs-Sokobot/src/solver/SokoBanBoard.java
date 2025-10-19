package solver;

import java.util.HashSet;

public class SokoBanBoard {
    public final HashSet<Coords> walls;
    public final HashSet<Coords> goals;

    public SokoBanBoard(char[][] mapData) {
        this.walls = new HashSet<Coords>();
		this.goals = new HashSet<Coords>();
        for (int row = 0; row < mapData.length; row++) {
            for (int col = 0; col < mapData[row].length; col++) {
                if (mapData[row][col] == '#')
                    this.walls.add(new Coords(row, col));

				if (mapData[row][col] == '.')
                    this.goals.add(new Coords(row, col));
            }
        }
    }
}
