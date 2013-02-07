import java.lang.Iterable;
import java.lang.NullPointerException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: haukur
 * Date: 7.2.2013
 * Time: 22:00
 * To change this template use File | Settings | File Templates.
 */
public class State {

    private Location currentLocation;
    private List<Location> dirtLocations;
    private List<Location> obstacleLocations;
    private int orientation;
    private Location size;
    public State()
    {
        currentLocation = new Location(0,0);
        dirtLocations = new ArrayList<Location>();
        obstacleLocations = new ArrayList<Location>();
        orientation = 0;
        size = new Location(0,0);
    }
    public State(Location current, short orientation)
    {
        dirtLocations = new ArrayList<Location>();
        obstacleLocations = new ArrayList<Location>();
        currentLocation = current;
        this.orientation = orientation;
    }
    public Location getCurrentLocation() {
        return currentLocation;
    }

    public Location getSize() {
        return size;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public void setSize(Location size) {
        this.size = size;
    }
    void setCurrentLocation(Location l) {
         this.currentLocation=l;
    }
    void addDirtLocation(Location l)
    {
        dirtLocations.add(l);
    }
    void addObstacleLocation(Location l)
    {
        obstacleLocations.add(l);
    }
    public List<Location> getDirtLocations() {
        return dirtLocations;
    }
    public List<Location> getObstacleLocations() {
        return obstacleLocations;
    }

    public void debug() {
        System.out.println("DIRT:");
        for(Location l : this.getDirtLocations() )
        {
            System.out.println(l.getX() + " " + l.getY());
        }
        System.out.println("OBSTACLES:");
        for(Location l : this.getObstacleLocations() )
        {
            System.out.println(l.getX() + " " + l.getY());
        }
        System.out.println("ORIENTATION:");
        System.out.println(this.getOrientation());
        System.out.println("LOCATION:");
        System.out.println(this.getCurrentLocation().getX() + " " + this.getCurrentLocation().getY());
        System.out.println("SIZE:");
        System.out.println(this.getSize());
    }
}
