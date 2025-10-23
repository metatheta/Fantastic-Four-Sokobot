package solver;

import java.util.HashSet;
import java.util.Random;

public class Zob {
    public static long[][] hashSource = new long[1][1];
    private int row;
    private int col;
    private Random rand;


    public Zob(int r, int c)
    {
        hashSource = new long[r][c];
        row = r;
        col = c;
        rand = new Random(4040);
        generateHashSourceArray();
    }

    private void generateHashSourceArray()
    {
        int y, x;

        for(y = 0; y < row; y++)
        {
            for(x = 0; x < col; x++)
            {
                hashSource[y][x] = rand.nextLong();
            }
        }
    }

    public long initialHash(HashSet<Coords> boxes)
    {
        long result = 0;

        for(Coords box : boxes)
        {
            result = result ^ hashSource[box.row][box.col];
        }

        return result;
    }
}
