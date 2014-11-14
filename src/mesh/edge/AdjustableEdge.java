/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mesh.edge;

import java.util.ArrayList;
import mesh.edge.properties.EdgeProperty;

/**
 * An AdjustableEdge can have its length and spring constant changed.
 * @author Christopher Hittner
 */
public class AdjustableEdge extends Edge{

    public AdjustableEdge(long ownerID, long targID, double k, double dist, ArrayList<EdgeProperty> prop) {
        super(ownerID, targID, k, dist, prop);
    }
    
    public void incrementDistance(double amt){
        distance += amt; 
        distance = Math.max(distance, 0.0);
    }
    public void incrementConstant(double amt){ constant += amt; }

}
