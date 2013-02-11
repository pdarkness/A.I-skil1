import java.lang.Iterable;
import java.lang.NullPointerException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: haukur
 * Date: 7.2.2013
 * Time: 22:00
 * To change this template use File | Settings | File Templates.
 */
public class State
{
    static final int NORTH = 0;
    static final int SOUTH = 1;
    static final int EAST = 2;
    static final int WEST = 3;

    private Location currentLocation;
    private Location startingPoint;
    private List<Location> dirtLocations;
    private List<Location> obstacleLocations;
    private int orientation;
    private Location size;
    private boolean turnedOn;

    public State()
    {
        currentLocation = new Location(0, 0);
        dirtLocations = new ArrayList<Location>();
        obstacleLocations = new ArrayList<Location>();
        orientation = 0;
        size = new Location(0, 0);
        turnedOn = false;
    }
    public State(Location current, int orientation)
    {
        dirtLocations = new ArrayList<Location>();
        obstacleLocations = new ArrayList<Location>();
        currentLocation = current;
        this.orientation = orientation;
        turnedOn = false;
    }
    class ManhattanComparator implements Comparator<Location>
    {
        public int compare(Location c1, Location c2)
        {
            int difX1 = Math.abs(currentLocation.getX()-c1.getX());
            int difY1 = Math.abs(currentLocation.getY()-c1.getY());
            int manhattan1 = difX1+difY1;
            int difX2 = Math.abs(currentLocation.getX()-c2.getX());
            int difY2 = Math.abs(currentLocation.getY()-c2.getY());
            int manhattan2 = difX2+difY2;
            if(manhattan1<manhattan2)
                return -1;
            if(manhattan1>manhattan2)
                return 1;
            return 0;
        }
    }
    public Location getCurrentLocation()
    {
        return currentLocation;
    }

    public Location getSize()
    {
        return size;
    }

    public boolean isTurnedOn()
    {
        return turnedOn;
    }

    public void setTurnedOn(boolean turnon)
    {
        this.turnedOn = turnon;
    }
    public int getOrientation()
    {
        return orientation;
    }

    public void setOrientation(int orientation)
    {
        this.orientation = orientation;
    }

    public void setSize(Location size)
    {
        this.size = size;
    }
    void setCurrentLocation(Location l)
    {
        this.currentLocation = l;
    }
    void addDirtLocation(Location l)
    {
        dirtLocations.add(l);
        Collections.sort(dirtLocations, new ManhattanComparator() );
    }
    void addObstacleLocation(Location l)
    {
        obstacleLocations.add(l);
    }
    public List<Location> getDirtLocations()
    {
        return dirtLocations;
    }
    public List<Location> getObstacleLocations()
    {
        return obstacleLocations;
    }
    public boolean atDirt() {
        for(Location l : dirtLocations)
            if(l.getX() == currentLocation.getX() && l.getY() == currentLocation.getY())
            {
                return true;
            }
        return false;
    }
    public void debug()
    {
        System.out.println("DIRT:");
        for(Location l : this.getDirtLocations() )
            System.out.println(l.getX() + " " + l.getY());
        System.out.println("OBSTACLES:");
        for(Location l : this.getObstacleLocations() )
            System.out.println(l.getX() + " " + l.getY());
        System.out.println("ORIENTATION:");
        System.out.println(this.getOrientation());
        System.out.println("LOCATION:");
        System.out.println(this.getCurrentLocation().getX() + " " + this.getCurrentLocation().getY());
        System.out.println("SIZE:");
        System.out.println(this.getSize().getX() + " " + this.getSize().getY());
        System.out.println("ON:");
        System.out.println(this.turnedOn);
        System.out.println("Legalmoves:");
        for(String move : legalMoves())
        {
            System.out.println(move);
        }
    }
    public List<String> legalMoves()
    {
        Stack<String> legalMoves = new Stack<String>();
        for(Location l : dirtLocations)
            if(l.getX() == currentLocation.getX() && l.getY() == currentLocation.getY())
            {
                legalMoves.push("SUCK");
                return legalMoves;
            }
        boolean blocked = false;
        boolean blocked_back = false;
        boolean blocked_left = false;
        boolean blocked_right = false;
        if(orientation == NORTH)
        {
            for(Location l : obstacleLocations)
            {
                if(l.getX() == currentLocation.getX() && l.getY() == (currentLocation.getY() + 1) )
                    blocked = true;
                if( l.getX()== currentLocation.getX() && l.getY() == (currentLocation.getY()-1) )
                    blocked_back=true;
                if(l.getY() == currentLocation.getY() && l.getX() == (currentLocation.getX()-1) )
                    blocked_left = true;
                if(l.getY() == currentLocation.getY() && l.getX() == (currentLocation.getX()+1) )
                    blocked_right = true;
            }
            if(currentLocation.getX() == size.getX())
                blocked_right = true;
            if(currentLocation.getX() == 1)
                blocked_left=true;
            if(currentLocation.getY() == 1)
                blocked_back = true;
            if(size.getY() <= currentLocation.getY())
                blocked = true;
            if(!blocked)
                legalMoves.push("GO");
        }

        else if(orientation == SOUTH)
        {
            for(Location l : obstacleLocations)
            {
                if(l.getX() == currentLocation.getX() && l.getY() == (currentLocation.getY() - 1) )
                    blocked = true;
                if(l.getX() == currentLocation.getX() && l.getY() == (currentLocation.getY() + 1))
                    blocked_back = true;
                if(l.getY()==currentLocation.getY() && l.getX()== currentLocation.getX()+1)
                    blocked_left = true;
                if(l.getY() == currentLocation.getY() && l.getX() == currentLocation.getX()-1)
                    blocked_right = true;
            }
            if(currentLocation.getX()==1)
                blocked_right = true;
            if(currentLocation.getX()==size.getX())
                blocked_left=true;
            if(currentLocation.getY() == size.getY())
                blocked_back=true;
            if(1 >= currentLocation.getY())
                blocked = true;
            if(!blocked)
                legalMoves.push("GO");
        }
        else if(orientation == EAST)
        {
            for(Location l : obstacleLocations)
            {
                if(l.getY() == currentLocation.getY() && l.getX() == (currentLocation.getX() + 1) )
                    blocked = true;
                if(l.getY() == currentLocation.getY() && l.getX() == (currentLocation.getX() -1) )
                    blocked_back = true;
                if(l.getX()==currentLocation.getX() && l.getY() == currentLocation.getY()+1)
                    blocked_left=true;
                if(l.getX() == currentLocation.getX() && l.getY() == currentLocation.getY()-1)
                    blocked_right = true;
            }
            if(currentLocation.getY() == 1)
                blocked_right = true;
            if(currentLocation.getY() == size.getY())
                blocked_left=true;
            if(currentLocation.getX() == 1)
                blocked_back = true;
            if(size.getX() <= currentLocation.getX())
                blocked = true;
            if(!blocked)
                legalMoves.push("GO");
        }
        else if(orientation == WEST)
        {
            for(Location l : obstacleLocations)
            {
                if(l.getY() == currentLocation.getY() && l.getX() == (currentLocation.getX() - 1) )
                    blocked = true;
                if(l.getY() == currentLocation.getY() && l.getX() == (currentLocation.getX() + 1))
                    blocked_back=true;
                if(l.getX() == currentLocation.getX() && l.getY() == (currentLocation.getY()-1))
                    blocked_left=true;
                if(l.getX() == currentLocation.getX() && l.getY() == (currentLocation.getY()+1))
                    blocked_right = true;
            }
            if(currentLocation.getY()==size.getY())
                blocked_right = true;
            if(currentLocation.getY()==1)
                blocked_left=true;
            if(currentLocation.getX() == size.getX())
                blocked_back=true;
            if(1 >= currentLocation.getX())
                blocked = true;
            if(!blocked)
                legalMoves.push("GO");
        }
        if(! (blocked_left && blocked_back && blocked_right) )
        {
        if( !(blocked_back && blocked_left) )
        {
            if(!(blocked && blocked_left) )
              legalMoves.push("TURN_LEFT");
        }
        if(!(blocked_back && blocked_right) )
          {
            if(!(blocked && blocked_right) )
                legalMoves.push("TURN_RIGHT");
          }
        }
        if(blocked && blocked_right  && blocked_left)
            legalMoves.push("TURN_LEFT");
        return legalMoves;
    }


    public State ResultingState(String move)
    {
        State resultingState = new State(currentLocation, orientation);
        resultingState.setStartingPoint(startingPoint);
        boolean suck = move.equals("SUCK");
        for(Location l : obstacleLocations)
            resultingState.addObstacleLocation(l);
        resultingState.setSize(size);
        resultingState.setTurnedOn(turnedOn);
        for(Location l : dirtLocations)
        {
            if(!(suck && l.getX() == currentLocation.getX() && l.getY() == currentLocation.getY()))
                resultingState.addDirtLocation(l);
        }
        if(suck) return resultingState;

        if(move.equals("TURN_ON") )
        {
            resultingState.turnedOn = true;
            return resultingState;
        }
        else if(move.equals("TURN_OFF") )
        {
            resultingState.turnedOn=false;
            return resultingState;
        }
        else if(move.equals("TURN_RIGHT") )
        {
            switch (orientation)
            {
            case NORTH:
                resultingState.setOrientation(EAST);
                break;
            case SOUTH:
                resultingState.setOrientation(WEST);
                break;
            case EAST:
                resultingState.setOrientation(SOUTH);
                break;
            case WEST:
                resultingState.setOrientation(NORTH);
                break;
            }
            return resultingState;
        }

        else if(move.equals("TURN_LEFT") )
        {
            switch (orientation)
            {
            case NORTH:
                resultingState.setOrientation(WEST);
                break;
            case SOUTH:
                resultingState.setOrientation(EAST);
                break;
            case EAST:
                resultingState.setOrientation(NORTH);
                break;
            case WEST:
                resultingState.setOrientation(SOUTH);
                break;
            }
            return resultingState;
        }
        else if(move.equals("GO")  )
        {
            if(orientation == NORTH)
            {
                if( size.getY() > currentLocation.getY() )
                {
                    boolean blocked = false;
                    for(Location l : obstacleLocations)
                        if(l.getX() == currentLocation.getX() && l.getY() == (currentLocation.getY() + 1) )
                        {
                            blocked = true;
                            break;
                        }
                    if(!blocked)
                        resultingState.setCurrentLocation(
                            new Location(currentLocation.getX(), currentLocation.getY() + 1)
                        );
                }
            }
            else if(orientation == SOUTH)
            {
                if( 1 < currentLocation.getY() )
                {
                    boolean blocked = false;
                    for(Location l : obstacleLocations)
                        if(l.getX() == currentLocation.getX() && l.getY() == (currentLocation.getY() - 1) )
                        {
                            blocked = true;
                            break;
                        }
                    if(!blocked)
                        resultingState.setCurrentLocation(
                            new Location(currentLocation.getX(), currentLocation.getY() - 1)
                        );
                }
            }
            else if(orientation == EAST)
            {
                if( size.getX() > currentLocation.getX() )
                {
                    boolean blocked = false;
                    for(Location l : obstacleLocations)
                        if(l.getY() == currentLocation.getY() && l.getX() == (currentLocation.getX() + 1) )
                        {
                            blocked = true;
                        }
                    if(!blocked)
                        resultingState.setCurrentLocation(
                            new Location(currentLocation.getX() + 1, currentLocation.getY())
                        );
                }
            }
            else if(orientation == WEST)
            {
                if( 1 < currentLocation.getX() )
                {
                    boolean blocked = false;
                    for(Location l : obstacleLocations)
                        if(l.getY() == currentLocation.getY() && l.getX() == (currentLocation.getX() - 1) )
                        {
                            blocked = true;
                            break;
                        }
                    if(!blocked)
                        resultingState.setCurrentLocation(
                            new Location(currentLocation.getX() - 1, currentLocation.getY())
                        );
                }
            }
        }
        return resultingState;
    }

    boolean successor() {

        return dirtLocations.isEmpty() && currentLocation.equals(startingPoint);
    }


    @Override
    public boolean equals(Object obj) {
        State s = (State) obj;
        if(this.orientation != s.orientation)
            return false;
        if(!this.currentLocation.equals(s.currentLocation))
            return false;
        if(this.getDirtLocations().size() != s.getDirtLocations().size())
            return false;
        for(int i=0; i<this.getDirtLocations().size(); i++)
        {
            if( !this.getDirtLocations().get(i).equals(s.getDirtLocations().get(i)))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int calc = (orientation*size.getX()+1)*(size.getY()+1)*(currentLocation.getX()+1)*(currentLocation.getY()+1);
        int calc2 = (dirtLocations.size()+1) * (obstacleLocations.size()+1);
        if(turnedOn)
            calc+=1337;
        return calc*calc2*(currentLocation.getX()+currentLocation.getY());
    }

    public Location getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(Location startingPoint) {
        this.startingPoint = startingPoint;
    }
}
