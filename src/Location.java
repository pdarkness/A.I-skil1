/**
 * Created with IntelliJ IDEA.
 * User: haukur
 * Date: 7.2.2013
 * Time: 22:07
 * To change this template use File | Settings | File Templates.
 */
public class Location
{
    private int x;
    private int y;
    public Location(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }
    @Override
    public boolean equals(Object obj) {

        Location l = (Location) obj;
        if(this.x != l.x)
            return false;
        if(this.y != l.y)
            return false;
        return true;

    }
}
