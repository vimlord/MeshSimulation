/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mesh.edge;

import mesh.edge.properties.EdgeProperty;
import java.util.ArrayList;

/**
 * An Edge keeps two Vertices at a specified distance.
 * Edges act like springs in the program.
 * @author Christopher Hittner
 */
public class Edge {
    private long owner, target;
    protected double constant, distance;
    private final ArrayList<EdgeProperty> properties;
    
    /**
     *
     * @param ownerID The ID of the owner
     * @param targID The ID of the targeted Vertex.
     * @param k The spring constant.
     * @param dist The default distance.
     * @param prop The properties
     */
    public Edge(long ownerID, long targID, double k, double dist, ArrayList<EdgeProperty> prop){
        owner = ownerID;
        target = targID;
        constant = k;
        distance = dist;
        if(prop == null)
            properties = new ArrayList<>();
        else
            this.properties = prop;
        
    }
    
    public long getOwner(){ return owner; }
    public long getTarget(){ return target; }
    public double getConstant(){ return constant; }
    public double getDistance(){ return distance; }
    
    public void addProperty(EdgeProperty p){ properties.add(p); }
    
    public void setDistance(double val){ distance = val;}
    
    /**
     * Safely executes the EdgeProperties
     */
    public void executeProperties(){
        for(EdgeProperty ep : properties)
            ep.run(this);
    }
    
}
