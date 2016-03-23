package mazesolver;

import java.awt.Point;

/**
 *
 * @author Walker VanHouten
 */
public class Branch {

    public static final char HARD_CODED_WEST = 'w';
    
    private String pathTo, choices;
    private Branch parent;
    private Point coords;
    
    private boolean root = false;
    //bottom of the tree
    Branch(Maze m, Point startLocation) {
        root = true;
        System.out.println("things " + startLocation.y);
        if(!m.isFinish(startLocation)) {
            System.out.println("woah");
        }
        execute(m, this, getNextLocation(HARD_CODED_WEST, startLocation), "e");
        System.out.println("There is no exit or this code is broken.");
    }
    
    Branch(Maze m, String directions, Branch parent, Point coords, String choices) {
        this.parent = parent;
        pathTo = directions;
        this.coords = coords;
        this.choices = choices;
        
        
        while(this.choices.length()>1) {
            String newDirs = "" + this.choices.charAt(0);
            //remove this branch from choices
            this.choices = "" + this.choices.subSequence(1, this.choices.length()-1);
            execute(m, this, getNextLocation(this.choices.charAt(0), coords), newDirs);
        }
        //remove this branch as is it is no longer consequential (pass the cursor this branches parent instead of itself)
        execute(m, this.parent, this.coords, this.parent.pathTo+choices.charAt(0));
    }
    
    private static String eliminateFromPath(String choices, char from) {
        int l = choices.length();
        char c = getOpposite(from);
        String out = "";
        for(int i = 0; i < l; i++) {
            if(choices.charAt(i)!=c) {
                out += choices.charAt(i);
            }
        }
        return out;
    }
    
    private static char getOpposite(char dir) {
        switch(dir) {
            case 'n':
                return 's';
            case 'e':
                return 'w';
            case 's':
                return 'n';
            case 'w':
                return 'e';
        }
        System.out.println("coding syntax error");
        System.exit(5);
        return 'b';
    }
    
    Point getNextLocation(char directionGoingIn, Point oldPt) {
        //get next location from the direction provided
        switch(directionGoingIn) {
            case 'n':
                oldPt.x += 1;
                break;
            case 'e':
                oldPt.y += 1;
                break;
            case 's':
                oldPt.x -= 1;
                break;
            case 'w':
                oldPt.y -= 1;
                break;
        }
        return oldPt;   //return the updated location
    }
    
    void execute(Maze m, Branch parent, Point location, String directions) {
        System.out.println("derp " + location.x + "  " + location.y);
        if(m.isFinish(location)) {
            //get the full correct path
            String fullPath = "";
            while(!parent.isRoot()) {
                fullPath += parent.getPath();
                parent = parent.parent;
            }
            //overlay the path to the character array maze to be printed out
            System.out.println("directions to exit: " + fullPath + "\n\n");
            m.overlaySolution(fullPath);
            m.printMaze();
            System.exit(0);
        } else {
            //find paths to take
            char from = directions.charAt(directions.length()-1);
            String choices = eliminateFromPath(m.getChoices(location), from);
            if(choices.length()==0) {
                //dead end. Do nothing right now the parent branch will execute its next choice
                System.out.println("Picking up cursor.");
            } else if(choices.length()==1) {
                //move in the only avalible direction until an intersection is found
                //add the next move to the direction list
                directions += choices.charAt(0);
                execute(m, parent, getNextLocation(choices.charAt(0), location), directions);
            } else {
                //intersection found
                new Branch(m, directions, parent, location, choices);
            }
        }
    }
    
    private boolean isRoot() {
        return root;
    }
    
    private String getPath() {
        return pathTo;
    }
}
