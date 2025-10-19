package solver;
import java.util.HashSet;

public class Squarelock {
    
    private char[][] map;
    private int x;
    private int y;
    private int i;
    private int outerLoopBound;
    private int innerLoopBound;
    private HashSet<Coords> squarelockSet;
    private boolean goalFlag;
    private int startFlag;
    private int endFlag;

    //has the mapdata as an attribute
    public Squarelock(char[][] m)
    {
        map = m;
        goalFlag = false;
        squarelockSet = new HashSet<>();
        x = 0;
        y = 0;
    }

    //checks the deadlocks in all 4 directions
    public void squarelockCheck()
    {
        belowCheck();
        aboveCheck();
        rightCheck();
        leftCheck();
    }

    //checks deadlocks that look like 
    /*
     *      ######
     *      #    #
     */
    //goes not count as deadlock if a goal exists
    public void belowCheck()
    {
        outerLoopBound = map.length - 1;

        for (y = 0; y < outerLoopBound; y++)
        {
            resetStartFlag();
            resetEndFlag();
            resetGoalFlag();

            innerLoopBound = map[y].length;
            for (x = 0; x < innerLoopBound; x++)
            {
                
                if(isWall(map[y][x]) && isWall(map[y + 1][x]) && startFlagValid())
                {
                    endFlag = x;
                    //System.out.println("\t" + x + " is end");
                }

                if(isWall(map[y][x]) && isWall(map[y + 1][x]) && !startFlagValid())
                {
                    startFlag = x;
                    //System.out.println("\t" + x + " is start");
                }

                if(!isWall(map[y][x]) && startFlagValid())
                {
                    resetStartFlag();
                    resetGoalFlag();
                    //System.out.println("\t" + x + " is where we RESET startFlag2");
                }

                if(isGoal(map[y + 1][x]) && startFlagValid())
                {
                    goalFlag = true;
                    //System.out.println("\t" + "Goal flag found at " + x);
                }
                    

                if (startFlagValid() && endFlagValid())
                {
                    if(!goalFlag)
                    {
                        for(i = startFlag + 1; i < endFlag; i++)
                        {
                            if(map[y + 1][i] == ' ')
                            {
                                this.squarelockSet.add(new Coords(y+1, i));
                            }
                        }
                    }
                    
                    startFlag = endFlag;
                    resetEndFlag();
                    resetGoalFlag();
                }
            }
            //System.out.println("\t" + "====NEXT ROW====");
        }
    }

    //checks deadlocks that look like 
    /*
     *      #    #
     *      ######
     */
    //goes not count as deadlock if a goal exists
    public void aboveCheck()
    {
        for (y = map.length - 1; y > 0; y--)
        {
            resetStartFlag();
            resetEndFlag();
            resetGoalFlag();

            innerLoopBound = map[y].length;
            for (x = 0; x < innerLoopBound; x++)
            {
                if(isWall(map[y][x]) && isWall(map[y - 1][x]) && startFlagValid())
                {
                    endFlag = x;
                    //System.out.println("\t" + x + " is end");
                }

                if(isWall(map[y][x]) && isWall(map[y - 1][x]) && !startFlagValid())
                {
                    startFlag = x;
                    //System.out.println("\t" + x + " is start");
                }

                if(!isWall(map[y][x]) && startFlagValid())
                {
                    resetStartFlag();
                    resetGoalFlag();
                    //System.out.println("\t" + x + " is where we RESET startFlag2");
                }

                if(isGoal(map[y - 1][x]) && startFlagValid())
                {
                    goalFlag = true;
                    //System.out.println("\t" + "Goal flag found at " + x);
                }
                    

                if (startFlagValid() && endFlagValid())
                {
                    if(!goalFlag)
                    {
                        for(i = startFlag + 1; i < endFlag; i++)
                        {
                            if(map[y - 1][i] == ' ')
                            {
                                this.squarelockSet.add(new Coords(y-1, i));
                            }
                        }
                    }
                    
                    startFlag = endFlag;
                    resetEndFlag();
                    resetGoalFlag();
                }
            }
            //System.out.println("\t" + "====NEXT ROW====");
        }
    }

    //checks deadlocks that look like 
    /*
     *      ##
     *      #
     *      #
     *      #
     *      ##
     */
    //goes not count as deadlock if a goal exists
    public void rightCheck()
    {
        outerLoopBound = map[0].length - 1;
        innerLoopBound = map.length;

        for (x = 0; x < outerLoopBound; x++)
        {
            resetStartFlag();
            resetEndFlag();
            resetGoalFlag();

            
            for (y = 0; y < innerLoopBound; y++)
            {
                // System.out.println(y);
                if(isWall(map[y][x]) && isWall(map[y][x + 1]) && startFlagValid())
                {
                    endFlag = y;
                    //System.out.println(y + " is end");
                }

                if(isWall(map[y][x]) && isWall(map[y][x + 1]) && !startFlagValid())
                {
                    startFlag = y;
                    //System.out.println(y + " is start");
                }

                if(!isWall(map[y][x]) && startFlagValid())
                {
                    resetStartFlag();
                    resetGoalFlag();
                    //System.out.println(y + " is where we RESET startFlag2");
                }

                if(isGoal(map[y][x + 1]) && startFlagValid())
                {
                    goalFlag = true;
                    //System.out.println("Goal flag found at " + y);
                }
                    

                if (startFlagValid() && endFlagValid())
                {
                    if(!goalFlag)
                    {
                        for(i = startFlag + 1; i < endFlag; i++)
                        {
                            if(map[i][x + 1] == ' ')
                            {
                                this.squarelockSet.add(new Coords(i, x+1));
                            }
                        }
                    }
                    
                    startFlag = endFlag;
                    resetEndFlag();
                    resetGoalFlag();
                }
            }
            //System.out.println("====NEXT COL====");
        }
    }

    //checks deadlocks that look like 
    /*
     *      ##
     *      #
     *      #
     *      #
     *      ##
     */
    //goes not count as deadlock if a goal exists
    public void leftCheck()
    {
        innerLoopBound = map.length;

        for (x =  map[0].length - 1; x > 0; x--)
        {
            resetStartFlag();
            resetEndFlag();
            resetGoalFlag();

            
            for (y = 0; y < innerLoopBound; y++)
            {
                // System.out.println(y);
                if(isWall(map[y][x]) && isWall(map[y][x - 1]) && startFlagValid())
                {
                    endFlag = y;
                    //System.out.println(y + " is end");
                }

                if(isWall(map[y][x]) && isWall(map[y][x - 1]) && !startFlagValid())
                {
                    startFlag = y;
                    //System.out.println(y + " is start");
                }

                if(!isWall(map[y][x]) && startFlagValid())
                {
                    resetStartFlag();
                    resetGoalFlag();
                    //System.out.println(y + " is where we RESET startFlag2");
                }

                if(isGoal(map[y][x - 1]) && startFlagValid())
                {
                    goalFlag = true;
                    //System.out.println("Goal flag found at " + y);
                }
                    

                if (startFlagValid() && endFlagValid())
                {
                    if(!goalFlag)
                    {
                        for(i = startFlag + 1; i < endFlag; i++)
                        {
                            if(map[i][x - 1] == ' ')
                            {
                                this.squarelockSet.add(new Coords(i, x-1));
                            }
                        }
                    }
                    
                    startFlag = endFlag;
                    resetEndFlag();
                    resetGoalFlag();
                }
            }
            //System.out.println("====NEXT COL====");
        }
    }

    public HashSet<Coords> getSquarelockSet()
    {
        return squarelockSet;
    }

    private boolean isWall(char w)
    {
        return w == '#';
    }

    private void resetStartFlag()
    {
        startFlag = -1;
    }

    private void resetEndFlag()
    {
        endFlag = -1;
    }

    private void resetGoalFlag()
    {
        goalFlag = false;
    }

    private boolean startFlagValid()
    {
        return startFlag > -1;
    }

    private boolean endFlagValid()
    {
        return endFlag > -1;
    }

    private boolean isGoal(char g)
    {
        return g == '.';
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for (int row = 0; row < map.length; row++) 
        {
            for (int col = 0; col < map[i].length; col++) {
				if (squarelockSet.contains(new Coords(row, col)))
					sb.append('X');
				else
					sb.append(map[row][col]);
			}
            sb.append('\n');
        }

        return sb.toString();
    }
}
