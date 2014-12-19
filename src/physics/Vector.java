/*
 * Stores a magnitude and a direction.
 */

package physics;

/**
 *
 * @author Christopher Hittner
 */
public class Vector {
    private double magnitude, XZ, Y;
    
    public Vector(double magnitude, double angle_xz, double angle_y){
        this.magnitude = magnitude;
        XZ = angle_xz;
        Y = angle_y;
        fixValues();
    }
    
    public Vector(Vector v, double factor){
        this(v.magnitude * factor, v.XZ, v.Y);
    }
    
    public Vector(Coordinate from, Coordinate to){
        this(from.X(), from.Y(), from.Z(), to.X(), to.Y(), to.Z());
    }
    
    public Vector(double x1, double y1, double z1, double x2, double y2, double z2){
        double x = x2 - x1, y = y2 - y1, z = z2 - z1;
        double xz = Math.sqrt(Math.pow(x,2) + Math.pow(z,2));          
        magnitude = Math.sqrt(Math.pow(xz,2) + Math.pow(y,2));
        
        if(magnitude == 0){
            XZ = 0;
            Y = 0;
            return;
        }
        
        XZ = Math.acos(x/xz);
        if(z < 0)
            XZ *= -1;
        
        if(xz == 0){
            XZ = 0;
            Y = Math.PI/2.0 * Math.signum(y);
            return;
        }
        
        Y = Math.asin(y/magnitude);
        
        fixValues();
        
    }
    
    public double getMagnitude(){
        return magnitude;
    }
    public double getAngleXZ(){
        return XZ;
    }
    public double getAngleY(){
        return Y;
    }
    public double getMagnitudeX(){
        return getMagnitudeXZ() * Math.cos(XZ);
    }
    public double getMagnitudeY(){
        return magnitude * Math.sin(Y);
    }
    public double getMagnitudeZ(){
        return getMagnitudeXZ() * Math.sin(XZ);
    }
    public double getMagnitudeXZ(){
        return magnitude * Math.cos(Y);
    }
    
    private void fixValues(){
        if(magnitude < 0){
            XZ += Math.toRadians(180);
            Y *= -1;
            magnitude *= -1;
        }
        
        if(Math.abs(XZ) > Math.toRadians(180))
            XZ -= (Math.toRadians(360) * Math.signum(XZ));
        
    }
    
    public void addMagnitude(double amount){
        magnitude += amount;
        fixValues();
    }
    
    public void multiplyMagnitude(double amount){
        magnitude *= amount;
        fixValues();
    }
    
    public void addVectorToThis(Vector v){
        Vector result = new Vector(0,0,0,getMagnitudeX() + v.getMagnitudeX(), getMagnitudeY() + v.getMagnitudeY(), getMagnitudeZ() + v.getMagnitudeZ());
        magnitude = result.magnitude;
        XZ = result.XZ;
        Y = result.Y;
    }
    
    public String toString(boolean xyz_Parts){
        if(xyz_Parts){
            return new Coordinate(getMagnitudeX(), getMagnitudeY(), getMagnitudeZ()).toString();
        } else {
            return "Magnitude: " + magnitude + "    XZ: " + XZ + "    Y: " + Y;
        }
    }
    
    public Vector unitVector(){
        return new Vector(1, XZ, Y);
    }
    
    public static double dotProduct(Vector a, Vector b){
        return (a.getMagnitudeX() * b.getMagnitudeX() + a.getMagnitudeY() * b.getMagnitudeY() + a.getMagnitudeZ() * b.getMagnitudeZ());
    }
    
    public static double cosOfAngleBetween(Vector a, Vector b){
        return dotProduct(a,b)/(a.magnitude * b.magnitude);
    }
    
}
