package solver;

import java.util.*;

public final class SokoBanBoard {
    public final int width;
    public final int height;
    public final int N;              // width * height
    private final char[][] map;
    private final List<Coords> goals;
    private final Set<Coords> goalSet; // Used for

    public SokoBanBoard(int width, int height, char[][] map) {
        this.width = width;
        this.height = height;
        this.N = width * height;
        this.map = map.clone();
        this.goals = new ArrayList<>();
        this.goalSet = new HashSet<>();
        for (int i = 0; i < height; i++) { // Rows
            for (int j = 0; j < width; j++) { // Columns
                if(map[i][j] == '.') {
                    this.goals.add(new Coords(i, j));
                    this.goalSet.add(new Coords(i, j));
                }
            }
        }
    }
    public char getCell(int row, int col) {
        return map[row][col];
    }

    public List<Coords> getGoals() {
        return Collections.unmodifiableList(goals);
    }

    public boolean isGoalCell(Coords c) {
        return goalSet.contains(c);
    }
}
