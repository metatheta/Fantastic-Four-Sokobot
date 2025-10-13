package solver;

import java.util.*;

public class SokoBot {

    public String solveSokobanPuzzle(int width, int height, char[][] mapData, char[][] itemsData) {
        SokoBanBoard board = new SokoBanBoard(width, height, mapData);

        HashSet<Coords> boxes = new HashSet<>();
        Coords player = null;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < itemsData[i].length; j++) {
                if (itemsData[i][j] == '@') player = new Coords(i, j);
                if (itemsData[i][j] == '$') boxes.add(new Coords(i, j));
            }
        }

        State start = new State(player, boxes);
        StateGenerator generator = new StateGenerator(new HashSet<>(board.getWallSet()), new HashSet<>(board.getGoalSet()));

        //start of bfs stuffs
        Node root = new Node(start, null, 0, ""); // priority value = 0 cuz its bfs; root.move = ""

        if (start.isGoal(board)) return "";

        Set<State> explored = new HashSet<>();
        Set<State> inFrontier = new HashSet<>();
        ArrayDeque<Node> frontier = new ArrayDeque<>();

        frontier.add(root);
        inFrontier.add(root.state);

        while (!frontier.isEmpty()) {
            Node node = frontier.poll();
            inFrontier.remove(node.state);
            explored.add(node.state);

            List<String> actions = generator.generateActions(node.state);
            for (String action : actions) {
                State childState = generator.applyAction(node.state, action);
                if (childState == null) continue; // invalid move

                if (explored.contains(childState) || inFrontier.contains(childState)) {
                    continue; // duplicate
                }

                // insert deadlock test here
                // if (deadlock test bla bla bla)
                if(childState.isCornerDeadlock(new HashSet<>(board.getGoalSet()), new HashSet<>(board.getWallSet())))
                  continue;

                if(node.wentBack(childState))
                  continue;

                Node child = new Node(childState, node, 0, action);

                // check goal before enqueuing
                if (childState.isGoal(board)) {
                    // build move-string
                    StringBuilder sb = new StringBuilder();
                    for (Node n = child; n.parent != null; n = n.parent) sb.append(n.move);
                    return sb.reverse().toString();
                }

                // enqueue and mark in-frontier to prevent duplicates
                frontier.add(child);
                inFrontier.add(child.state);
            }
        }

        return "lrlrlrlrudududud";
    }


}
