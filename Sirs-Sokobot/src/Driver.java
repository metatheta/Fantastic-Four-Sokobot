import solver.Coords;
import solver.SokoBanBoard;
import solver.State;
import solver.StateGenerator;

import java.nio.file.*;
import java.io.IOException;
import java.util.*;

/**
 * Driver that reads a ragged sokoban level file (lines may have different lengths),
 * and splits it into a ragged map layer and a ragged item layer.
 *
 * Usage:
 *   java solver.Driver path/to/level.txt
 */
public class Driver {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java solver.Driver <level-file>");
            System.exit(1);
        }

        Path path = Paths.get(args[0]);
        List<String> lines;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            System.err.println("Failed to read file: " + e.getMessage());
            return;
        }

        if (lines.isEmpty()) {
            System.err.println("Empty level file.");
            return;
        }

        // build ragged char[][] arrays (preserve each line's length)
        int rows = lines.size();
        char[][] mapData = new char[rows][];
        char[][] itemData = new char[rows][];
        int maxWidth = 0;

        for (int r = 0; r < rows; r++) {
            String line = lines.get(r);
            char[] chars = line.toCharArray();
            mapData[r] = new char[chars.length];
            itemData[r] = new char[chars.length];

            for (int c = 0; c < chars.length; c++) {
                char ch = chars[c];
                // default values
                char m = ' ';
                char it = ' ';

                switch (ch) {
                    case '#': m = '#'; it = ' '; break;   // wall
                    case '.': m = '.'; it = ' '; break;   // goal
                    case '@': m = ' '; it = '@'; break;   // player
                    case '$': m = ' '; it = '$'; break;   // box
                    case '+': m = '.'; it = '+'; break;   // player on goal
                    case '*': m = '.'; it = '*'; break;   // box on goal
                    default:
                        // treat any other char (space, etc.) as floor / no item
                        m = ' ';
                        it = ' ';
                        break;
                }

                mapData[r][c] = m;
                itemData[r][c] = it;
            }

            if (chars.length > maxWidth) maxWidth = chars.length;
        }

        // Basic validation: count player(s), boxes and goals
        int playerCount = 0;
        int boxCount = 0;
        int goalCount = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < mapData[r].length; c++) {
                if (mapData[r][c] == '.') goalCount++;
            }
            for (int c = 0; c < itemData[r].length; c++) {
                if (itemData[r][c] == '@' || itemData[r][c] == '+') playerCount++;
                if (itemData[r][c] == '$' || itemData[r][c] == '*') boxCount++;
            }
        }

        System.out.printf("Read %d rows, max width = %d%n", rows, maxWidth);
        System.out.printf("players=%d boxes=%d goals=%d%n", playerCount, boxCount, goalCount);
        if (playerCount != 1) System.out.println("Warning: expected exactly 1 player (@ or +).");
        if (boxCount != goalCount) System.out.println("Warning: #boxes != #goals (may be unsolvable).");

        System.out.println("\nMap layer (walls '#', goals '.'):");
        printRagged(mapData);

        System.out.println("\nItem layer (player '@' player-on-goal '+', box '$' box-on-goal '*'):");
        printRagged(itemData);

        System.out.println("\nCombined view (map + items):");
        printCombined(mapData, itemData);

        // You can now pass these arrays to your SokoBanBoard and create initial State:
        SokoBanBoard board = new SokoBanBoard(maxWidth, rows, mapData);
        HashSet<Coords> boxes = new HashSet<>();
        Coords player = null;
        for (int i = 0; i < rows; i++) { // Rows
            for (int j = 0; j < itemData[i].length; j++) { // Columns
                if (itemData[i][j] == '@') {
                    player = new Coords(i, j);
                }
                if (itemData[i][j] == '$') {
                    boxes.add(new Coords(i, j));
                }
            }
        }
        State start = new State(player, boxes);
        StateGenerator generator = new StateGenerator(new HashSet<>(board.getWallSet()), new HashSet<>(board.getGoalSet()));
        ArrayList<String> actionsPossible = generator.generateActions(start);
        System.out.println(actionsPossible);
    }

    private static void printRagged(char[][] grid) {
        for (int r = 0; r < grid.length; r++) {
            if (grid[r] == null) {
                System.out.println();
            } else {
                System.out.println(new String(grid[r]));
            }
        }
    }

    private static void printCombined(char[][] mapData, char[][] itemData) {
        int rows = Math.max(mapData.length, itemData.length);
        for (int r = 0; r < rows; r++) {
            int mapLen = (r < mapData.length && mapData[r] != null) ? mapData[r].length : 0;
            int itemLen = (r < itemData.length && itemData[r] != null) ? itemData[r].length : 0;
            int len = Math.max(mapLen, itemLen);
            StringBuilder sb = new StringBuilder(len);
            for (int c = 0; c < len; c++) {
                char m = (c < mapLen) ? mapData[r][c] : ' ';
                char it = (c < itemLen) ? itemData[r][c] : ' ';
                char out;
                if (it == '@' || it == '+') out = (m == '.') ? '+' : '@';
                else if (it == '$' || it == '*') out = (m == '.') ? '*' : '$';
                else out = m;
                sb.append(out);
            }
            System.out.println(sb.toString());
        }
    }
}
