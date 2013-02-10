import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * Creator: Knútur Óli Magnússon & Haukur Rósinkranz
 * Date: 5.2.2013
 * Time: 21:34
 */
public class Agent187 implements Agent
{
    private Set<State> states = new HashSet<State>();
    static final int NORTH = 0;
    static final int SOUTH = 1;
    static final int EAST = 2;
    static final int WEST = 3;
    private Collection<String> percepts;
    private State startingState;
    StateDFS dfs;


    int h(State N)
    {
        int points = 0;
        int curX = N.getCurrentLocation().getX();
        int curY = N.getCurrentLocation().getY();
        int maxlen = 0;
        int lengthSum = 99999;
        Location target;
        for(Location l : N.getDirtLocations())
        {
            int difX = Math.abs(curX-l.getX());
            int difY = Math.abs(curY-l.getY());
            int sum = difX+difY;
            if(sum<lengthSum)
            {
                lengthSum = sum;
                target=l;
            }
        }

        return lengthSum;
    }
    @Override
    public void init(Collection<String> percepts)
    {
        startingState = new State();
        Pattern perceptNamePattern = Pattern.compile("\\(\\s*([^\\s]+).*");
        for (String percept: percepts)
            {
                Matcher perceptNameMatcher = perceptNamePattern.matcher(percept);
                if (perceptNameMatcher.matches())
                    {
                        String perceptName = perceptNameMatcher.group(1);
                        if (perceptName.equals("HOME"))
                            {
                                Matcher m = Pattern.compile("\\(\\s*HOME\\s+([0-9]+)\\s+([0-9]+)\\s*\\)").matcher(percept);
                                if (m.matches())
                                    {
                                        int curX = Integer.parseInt(m.group(1));
                                        int curY  = Integer.parseInt(m.group(2));
                                        Location currentLocation = new Location(curX, curY);
                                        startingState.setCurrentLocation(currentLocation);
                                        startingState.setStartingPoint(currentLocation);
                                    }
                            }
                        else if (perceptName.equals("ORIENTATION"))
                            {
                                String orientation = percept.split(" ")[1].replaceAll("\\)", "");
                                int orientationIndex = NORTH;
                                if(orientation == "SOUTH")
                                        orientationIndex =  SOUTH;
                                else if(orientation == "EAST")
                                        orientationIndex =  EAST;
                                else if(orientation == "WEST")
                                        orientationIndex =  WEST;
                                startingState.setOrientation(orientationIndex);
                            }
                        else if (perceptName.equals("AT"))
                            {
                                String type = percept.split(" ")[1];
                                int x = Integer.parseInt(percept.split(" ")[2]);
                                int y = Integer.parseInt(percept.split(" ")[3].replaceAll("\\)", ""));
                                Location location = new Location(x, y);
                                if(type.equals("OBSTACLE"))
                                        startingState.addObstacleLocation(location);
                                else if(type.equals("DIRT"))
                                        startingState.addDirtLocation(location);
                            }
                        else if (perceptName.equals("SIZE"))
                            {
                                int x = Integer.parseInt(percept.split(" ")[1]);
                                int y = Integer.parseInt(percept.split(" ")[2].replaceAll("\\)", ""));
                                Location location = new Location(x, y);
                                startingState.setSize(location);
                            }
                        else
                                System.out.println("other percept:" + percept);
                    }
                else
                        System.err.println("strange percept that does not match pattern: " + percept);
            }
        for(String m : startingState.ResultingState("TURN_RIGHT").ResultingState("GO").legalMoves())
            {
                System.out.println("Legal: " + m);
            }
        //startingState.ResultingState("GO").debug();
        dfs = new StateDFS(startingState);
    }

    @Override
    public String nextAction(Collection<String> percepts)
    {
        Random random = new Random();
        System.out.print("perceiving:");
        for(String percept: percepts)
            {
                System.out.print("'" + percept + "', ");
            }
        System.out.println("");
        String[] actions = { "TURN_ON", "TURN_OFF", "TURN_RIGHT", "TURN_LEFT", "GO", "SUCK" };
        return dfs.nextMove();
    }

}
