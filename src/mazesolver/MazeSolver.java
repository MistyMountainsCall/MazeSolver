package mazesolver;

import java.awt.Point;
import java.io.IOException;

/**
 *
 * @author Walker VanHouten
 * 
 * This program does not use the right hand rule to route find.
 * This is to make it much better as a base for future maze solving programs by
 * giving it some tools that can be used for loop detection.
 * The program "lifts up" the cursor and moves it back to the last intersection.
 * I will try to add in something to make the cursor backtrack for the purposes of this
 * assignment.
 */
public class MazeSolver {

    /**
     * @param args the command line arguments
     * filename of maze file
     */
    public static void main(String[] args) throws IOException {
//        Maze m = new Maze(args[1]);
        Maze m = new Maze("12x12_maze.txt");    //filename format is important (ROWNUMxCOLNUM_whatever you want to call the rest)
        Point startCoords = m.getStartCoords();
        System.out.println("Start row = " + startCoords.x);
        System.out.println("Start col = " + startCoords.y);
        m.printMaze();
        System.out.println("\n");
        startCoords.x = 0;
        startCoords.y = 3;
        new Branch(m, startCoords);
        
    }
}
