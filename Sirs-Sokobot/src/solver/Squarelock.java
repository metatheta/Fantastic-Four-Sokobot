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
    public void belowCheck() //updated
    {
        outerLoopBound = map.length - 1;

        for (y = 0; y < outerLoopBound; y++)
        {
            startFlag = -1; //resetStartFlag
            endFlag = -1; //resetEndFlag
            goalFlag = false; /*resetGoalFlag*/

            innerLoopBound = map[y].length;
            for (x = 0; x < innerLoopBound; x++)
            {
                if((map[y][x] == '#') && (map[y + 1][x] == '#'))
                {
                    if (startFlag > -1 /*startFlagValid*/)
                    {
                        endFlag = x;
                        //System.out.println("\t" + x + " is end");
                    } else
                    {
                        startFlag = x;
                        //System.out.println("\t" + x + " is start");
                    }
                }

                if (startFlag > -1 /*startFlagValid*/)
                {
                    if (!(map[y][x] == '#'))
                    {
                        startFlag = -1; //resetStartFlag
                        goalFlag = false; /*resetGoalFlag*/
                        //System.out.println("\t" + x + " is where we RESET startFlag2");
                    }

                    if ((map[y + 1][x] == '.'))
                    {
                        goalFlag = true;
                        //System.out.println("\t" + "Goal flag found at " + x);
                    }

                    if (endFlag > -1 /*endFlagValid*/)
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
                        endFlag = -1; //resetEndFlag
                        goalFlag = false; /*resetGoalFlag*/
                    }
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
    public void aboveCheck() //updated
    {
        for (y = map.length - 1; y > 0; y--)
        {
            startFlag = -1; //resetStartFlag
            endFlag = -1; //resetEndFlag
            goalFlag = false; /*resetGoalFlag*/

            innerLoopBound = map[y].length;
            for (x = 0; x < innerLoopBound; x++)
            {
                if ((map[y][x] == '#') && (map[y - 1][x] == '#'))
                {
                    if (startFlag > -1 /*startFlagValid*/)
                        endFlag = x;
                        //System.out.println("\t" + x + " is end");
                    else
                        startFlag = x;
                        //System.out.println("\t" + x + " is start");
                }

                if (startFlag > -1 /*startFlagValid*/)
                {
                    if (!(map[y][x] == '#'))
                    {
                        startFlag = -1; //resetStartFlag
                        goalFlag = false; /*resetGoalFlag*/
                        //System.out.println("\t" + x + " is where we RESET startFlag2");
                    }

                    if ((map[y - 1][x] == '.'))
                    {
                        goalFlag = true;
                        //System.out.println("\t" + "Goal flag found at " + x);
                    }

                    if (endFlag > -1 /*endFlagValid*/)
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
                        endFlag = -1; //resetEndFlag
                        goalFlag = false; /*resetGoalFlag*/
                    }

                }
            }
            //System.out.println("\t" + "====NEXT ROW====");
        }
    }

    //checks deadlocks that look like 
    /*
     *      ##
     *       #
     *       #
     *       #
     *      ##
     */
    //goes not count as deadlock if a goal exists
    public void rightCheck() //updated
    {
        outerLoopBound = map[0].length - 1;
        innerLoopBound = map.length;

        for (x = 0; x < outerLoopBound; x++)
        {
            startFlag = -1; //resetStartFlag
            endFlag = -1; //resetEndFlag
            goalFlag = false; /*resetGoalFlag*/

            for (y = 0; y < innerLoopBound; y++)
            {
                if ((map[y][x] == '#') && (map[y][x + 1] == '#'))
                {
                    if (startFlag > -1 /*startFlagValid*/)
                        endFlag = y;
                        //System.out.println(y + " is end");
                    else
                        startFlag = y;
                        //System.out.println(y + " is start");
                }

                if (startFlag > -1 /*startFlagValid*/)
                {
                    if (!(map[y][x] == '#'))
                    {
                        startFlag = -1; //resetStartFlag
                        goalFlag = false; /*resetGoalFlag*/
                        //System.out.println(y + " is where we RESET startFlag2");
                    }

                    if ((map[y][x + 1] == '.'))
                    {
                        goalFlag = true;
                        //System.out.println("Goal flag found at " + y);
                    }

                    if (endFlag > -1 /*endFlagValid*/)
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
                        endFlag = -1; //resetEndFlag
                        goalFlag = false; /*resetGoalFlag*/
                    }

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
    public void leftCheck() //updated
    {
        innerLoopBound = map.length;

        for (x =  map[0].length - 1; x > 0; x--)
        {
            startFlag = -1; //resetStartFlag
            endFlag = -1; //resetEndFlag
            goalFlag = false; /*resetGoalFlag*/

            
            for (y = 0; y < innerLoopBound; y++)
            {
                // System.out.println(y);
                if ((map[y][x] == '#') && (map[y][x - 1] == '#'))
                {
                    if (startFlag > -1 /*startFlagValid*/)
                        endFlag = y;
                        //System.out.println(y + " is end");
                    else
                        startFlag = y;
                        //System.out.println(y + " is start");
                }

                if (startFlag > -1 /*startFlagValid*/)
                {
                    if (!(map[y][x] == '#'))
                    {
                        startFlag = -1; //resetStartFlag
                        goalFlag = false; /*resetGoalFlag*/
                        //System.out.println(y + " is where we RESET startFlag2");
                    }

                    if ((map[y][x - 1] == '.'))
                    {
                        goalFlag = true;
                        //System.out.println("Goal flag found at " + y);
                    }

                    if (endFlag > -1 /*endFlagValid*/)
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
                        endFlag = -1; //resetEndFlag
                        goalFlag = false; /*resetGoalFlag*/
                    }
                }
            }
            //System.out.println("====NEXT COL====");
        }
    }

    //important getter
    public HashSet<Coords> getSquarelockSet()
    {
        return squarelockSet; //getSquarelockSet
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
