// package solver;

// // import java.util.*;

// public class StateGenerator {
//     // HashSet<Coords> walls;
//     // HashSet<Coords> goals;

//     // public StateGenerator(HashSet<Coords> walls, HashSet<Coords> goals) {
//     //     this.walls = walls;
//     //     this.goals = goals;
//     // }

//     // public boolean isActionValid(Set<Coords> boxes, Coords target, Coords beyond) {
//     //     // can't step into wall
//     //     if (walls.contains(target))
//     //         return false;
//     //     // empty target -> can move
//     //     if (!boxes.contains(target))
//     //         return true;
//     //     // target has a box -> can push only if beyond is free (not wall, not box)
//     //     return !walls.contains(beyond) && !boxes.contains(beyond);
//     // }

//     // public ArrayList<String> generateActions(State state) {
//     //     ArrayList<String> actionList = new ArrayList<>();
//     //     int row = state.player.row;
//     //     int col = state.player.col;
//     //     Set<Coords> boxes = state.boxes;

//     //     // check the validity of moving in each direction.

//     //     // up
//     //     if (isActionValid(boxes, new Coords(row - 1, col), new Coords(row - 2, col)))
//     //         actionList.add("u");
//     //     // right
//     //     if (isActionValid(boxes, new Coords(row, col + 1), new Coords(row, col + 2)))
//     //         actionList.add("r");
//     //     // down
//     //     if (isActionValid(boxes, new Coords(row + 1, col), new Coords(row + 2, col)))
//     //         actionList.add("d");
//     //     // left
//     //     if (isActionValid(boxes, new Coords(row, col - 1), new Coords(row, col - 2)))
//     //         actionList.add("l");

//     //     return actionList;
//     // }

//     // // DOESN'T PERFORM ANY IN BOUNDS CHECKS (IDK how to make it yet ToT)
//     // // Assumes you used the generateActions method to get the direction used in the parameter
//     // public State applyAction(State state,String dir) {
//     //     int row = state.player.row;
//     //     int col = state.player.col;

//     //     int dr = 0, dc = 0;
//     //     switch (dir) {
//     //         case "u" -> dr = -1;
//     //         case "r" -> dc = 1;
//     //         case "d" -> dr = 1;
//     //         case "l" -> dc = -1;
//     //         default -> throw new IllegalArgumentException("Unknown direction: " + dir);
//     //     }

//     //     Coords target = new Coords(row + dr, col + dc);        // cell player would step into
//     //     Coords beyond = new Coords(row + 2 * dr, col + 2 * dc); // cell box would move into if pushed

//     //     // don't need this anymore since generateActions uses it, but for testing purposes you can uncomment
//     //     // if (!isActionValid(state.boxes, target, beyond)) return null;

//     //     // if there's a box at target -> push it to beyond
//     //     if (state.boxes.contains(target)) {
//     //         Set<Coords> newBoxes = new HashSet<>(state.boxes);
//     //         newBoxes.remove(target);
//     //         newBoxes.add(beyond);
//     //         // player ends up on target (old box position)
//     //         Coords newPlayer = new Coords(target.row, target.col);
//     //         return new State(newPlayer, newBoxes);
//     //     } else {
//     //         // simple walk into empty target (no box moved)
//     //         Coords newPlayer = new Coords(target.row, target.col);
//     //         return new State(newPlayer, state.boxes);
//     //     }
//     // }
// }
