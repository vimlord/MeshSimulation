/*
 * Stores a coordinate/
 */

package physics;

/**
 *
 * @author Christopher Hittner
 */
public class Coordinate {
    
    private double x, y, z;
    
    /**
     *
     * @param X The x-coord
     * @param Y The y-coord
     * @param Z The z-coord
     */
    public Coordinate(double X, double Y, double Z){
        x = X;
        y = Y;
        z = Z;
    }
    
    /**
     * Increments the coordinate by the value of the vector.
     * @param v
     */
    public void addVector(Vector v){
       x += v.getMagnitudeX();
       y += v.getMagnitudeY();
       z += v.getMagnitudeZ();
    }
    
    public double X(){ return x; }
    public double Y(){ return y; }
    public double Z(){ return z; }
    
    public String toString(){
        return "(" + x + "," + y + "," + z + ")";
    }
    
    public static double relativeDistance(Coordinate a, Coordinate b){
        return Math.sqrt(Math.pow(a.X() - b.X(), 2) + Math.pow(a.Y() - b.Y(), 2) + Math.pow(a.Z() - b.Z(), 2));
    }
}
