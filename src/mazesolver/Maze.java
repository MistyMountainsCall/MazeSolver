package mazesolver;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Walker VanHouten
 */
public class Maze {
    
    private int rows, columns;
    private char[][] maze;
    
    public static final char WALLCHAR = '#';
    public static final char PATHCHAR = '.';
    public static final char ENDCHAR = 'F';
    public static final char ROUTECHAR = 'r';
    
    
    Maze(String fileName) throws FileNotFoundException, IOException {
        File mazeFile = new File(fileName);
        if(!mazeFile.exists()) {
            System.out.println("error: Specified mazefile does not exist.");
            System.exit(1);
        }
        
        String[] s = fileName.split("_");   //extract maze size from filename
        s = s[0].split("x");
        rows = Integer.parseInt(s[0]);
        columns = Integer.parseInt(s[1]);
        
        maze = new char[rows][columns];
        
        try (BufferedReader br = new BufferedReader(new FileReader(mazeFile))) {
            String line;
            int r = 0;
            while ((line = br.readLine()) != null) {
                String[] l = line.split(" ");
                for(int c = 0; c < l.length; c++) {
                    maze[r][c] = l[c].charAt(0);
                }
                r++;
            }
        }
        
        columns = maze.length;
        rows = maze[0].length;
        this.maze = maze;
    }
    
    String getChoices(Point pt) {
        String out = "";
        try {
            if(maze[pt.x+1][pt.y]==PATHCHAR) {    //north is path
                out += "n";
            }
        } catch(IndexOutOfBoundsException e) {
            wrongFormat(pt.x, pt.y);
        }
        try {
            if(maze[pt.x][pt.y+1]==PATHCHAR) {    //east is path
                out += "e";
            }
        } catch(IndexOutOfBoundsException e) {
            wrongFormat(pt.x, pt.y);
        }
        try {
            if(maze[pt.x-1][pt.y]==PATHCHAR) {    //south is path
                out += "s";
            }
        } catch(IndexOutOfBoundsException e) {
            wrongFormat(pt.x, pt.y);
        }
        try {
            if(maze[pt.x][pt.y-1]==PATHCHAR) {    //west is path
                out += "w";
            }
        } catch(IndexOutOfBoundsException e) {
            wrongFormat(pt.x, pt.y);
        }
        return out;     //string returned will contain valid directions
    }
    
    private void wrongFormat(int row, int col) {
        if(new Point(row, col)!=this.getStartCoords()) {
                System.out.println("error: Improperly formatted maze. (only path char conneted to outside of maze is the start)");
                System.exit(3);
            }
    }
    
    char getValue(int row, int col) {   //returns the character in the maze a specified coord
        return maze[row][col];
    }
    
    boolean isFinish(Point pt) {
        if(maze[pt.x][pt.y]==ENDCHAR) {
            return true;
        } else {
            return false;
        }
    }
    
    Point getStartCoords() {
        for(int c = 0; c < columns; c++) {  //search top row
            if(maze[0][c]==PATHCHAR) {
                return new Point(0, c);
            }
        }
        for(int c = 0; c < columns; c++) {
            if(maze[rows-1][c]==PATHCHAR) {
                return new Point(0, c);
            }
        }
        for(int r = 0; r < rows; r++) {
            if(maze[r][0]==PATHCHAR) {
                return new Point(r, 0);
            }
        }
        for(int r = 0; r < rows; r++) {
            if(maze[r][columns-1]==PATHCHAR) {
                return new Point(r, columns);
            }
        }
        System.out.println("error: No entrance on outside of maze.");
        System.exit(2);
        return new Point();
    }
    
    
    
    int getRows() {
        return rows;
    }
    int getCols() {
        return columns;
    }
    
    void overlaySolution(String route) {
        
    }
    
    void printMaze() {
        for(int r = 0; r < rows; r++) {
            System.out.print(maze[r][0]);
            for(int c = 1; c < columns; c++) {
                System.out.print(" " + maze[r][c]);
            }
            System.out.println("");
        }
    }
}
