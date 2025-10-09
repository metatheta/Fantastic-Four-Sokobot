package solver;

import java.util.*;

public class SokoBanBoard {
    public final int width;
    public final int height;
    private final char[][] map;
    private final Set<Coords> wallSet;
    private final Set<Coords> goalSet; // Used for
    private final Set<Coords> deadlockSet;

    //non pring addition
   /*
    4 FUNCTIONS
    one horizontally checking below
    pseudocode:
      wallContinuousFlag && !goalFlag<-- place in while loop

      below first wall = susLeftFlag

      Subfunction:
        susLeftFlag
        susRightFlag
        if (youReach susRightFlag)
            place all coords in between in the deadlockSet
    
    one horizontally checking above
    pseudocode:
      wallContinuousFlag && !goalFlag<-- place in while loop

      below first wall = susLeftFlag

      Subfunction:
        susLeftFlag
        susRightFlag
        if (youReach susRightFlag)
            place all coords in between in the deadlockSet

    one vertically checking to the right
    pseudocode:
      wallContinuousFlag && !goalFlag<-- place in while loop

      Subfunction:
        susBottomFlag
        susToplag
        if (youReach susTopFlag)
            place all coords in between in the deadlockSet

    one vertically checking to the left
    pseudocode:
      wallContinuousFlag && !goalFlag<-- place in while loop

      Subfunction:
        susBottomFlag
        susToplag
        if (youReach susTopFlag)
            place all coords in between in the deadlockSet
     
    */

    public SokoBanBoard(int width, int height, char[][] map) {
        this.width = width;
        this.height = height;
        this.map = map.clone();

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

    //non pring addition
    public Set<Coords> deadlockSet() {
        return deadlockSet;
    }

    public boolean isWall(Coords c) {
        return wallSet.contains(c);
    }

    public boolean isGoalCell(Coords c) {
        return goalSet.contains(c);
    }

    //non pring addition
    public boolean isDeadlockCell(Coords c) {
        return deadlockSet.contains(c);
    }

}
