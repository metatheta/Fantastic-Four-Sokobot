package solver;

import java.util.*;

public class SokoBanBoard {
    public final int width;
    public final int height;
    private final char[][] map;
    private final Set<Coords> wallSet;
    private final Set<Coords> goalSet; // Used for

    public SokoBanBoard(int width, int height, char[][] map) {
        this.width = width;
        this.height = height;
        this.map = map;

        this.goalSet = new HashSet<>();
        this.wallSet = new HashSet<>();
        for (int i = 0; i < height; i++) { // Rows
            for (int j = 0; j < width; j++) { // Columns
                if(map[i][j] == '.') {
                    this.goalSet.add(new Coords(i, j));
                }
                if(map[i][j] == '#') {
                    this.wallSet.add(new Coords(i, j));
                }
            }
        }
    }

    public char[][] getMap() {
        return map;
    }

    public Set<Coords> getWallSet() {
        return wallSet;
    }

    public Set<Coords> getGoalSet() {
        return goalSet;
    }

    public boolean isWall(Coords c) {
        return wallSet.contains(c);
    }

    public boolean isGoalCell(Coords c) {
        return goalSet.contains(c);
    }


}
