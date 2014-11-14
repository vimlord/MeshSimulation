/*
 * Equations contains exactly what the name suggests: equations. The list can be
 * modified as needed.
 */

package physics.functions;

import mesh.vertex.Vertex;
import physics.*;

/**
 *
 * @author Christopher Hittner
 */
public class Equations implements Constants{
    
    /**
     *
     * @param affected The object being pulled
     * @param pulling The object doing the pulling
     * @return The force vector
     */
    public static Vector gravitationalForce(Vertex affected, Vertex pulling){
        double distance = (new Vector(affected.getPosition(), pulling.getPosition())).getMagnitude();
        double Gmm = G * affected.getMass() * pulling.getMass();
        double r2 = Math.pow(distance, 2);
        
        Vector v = (new Vector(affected.getPosition(), pulling.getPosition())).unitVector();
        v.multiplyMagnitude(Gmm/r2);
        
        return v;
    }
    
    /**
     *
     * @param affected The object being pulled
     * @param pulling The object doing the pulling
     * @return The force vector
     */
    public static Vector electromagneticForce(Vertex affected, Vertex pulling){
        double distance = (new Vector(affected.getPosition(), pulling.getPosition())).getMagnitude();
        double Kqq = -K * affected.getCharge() * pulling.getCharge();
        double r2 = Math.pow(distance, 2);
        
        //Error check that avoids a potential divide by zero error
        if(r2 == 0)
            return new Vector(0,0,0);
        
        Vector v = (new Vector(affected.getPosition(), pulling.getPosition())).unitVector();
        v.multiplyMagnitude(Kqq/r2);
        
        return v;
    }
    
}
