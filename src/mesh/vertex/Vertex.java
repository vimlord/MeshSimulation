/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mesh.vertex;

import java.util.ArrayList;
import mesh.edge.Edge;
import mesh.vertex.properties.VertexProperty;
import physics.*;
import world.WorldManager;

/**
 *
 * @author Christopher Hittner
 */
public class Vertex {
    private long ID;
    private Coordinate position;
    private Vector velocity = new Vector(0,0,0);
    private double mass, electricCharge;
    private final ArrayList<Edge> edges;
    private final ArrayList<VertexProperty> properties = new ArrayList<>();
    
    /**
     *
     * @param c The Coordinate of the Vertex
     * @param edges The connection references.
     * @param kilograms The mass of the Vertex.
     * @param coulombs The electric charge, in Coulombs. Not guaranteed to be used.
     */
    public Vertex(Coordinate c , ArrayList<Edge> edges , double kilograms , double coulombs){
        if(c != null)
            position = c;
        else
            position = new Coordinate(0,0,0);
        
        if(edges != null)
            this.edges = edges;
        else
            this.edges = new ArrayList<>();
        
        mass = kilograms;
        electricCharge = coulombs;
        
        ID = WorldManager.getWorld().claimVertexID();
    }
    
    
    public void applyForce(Vector force){
        Vector acc = new Vector(force, 1.0/mass);
        velocity.addVectorToThis(acc);
    }
    
    public double getMass(){ return mass; }
    public double getCharge(){ return electricCharge; }
    public ArrayList<Edge> getEdges() { return edges; }
    public Coordinate getPosition(){ return position; }
    public Vector getVelocity(){ return velocity; }
    public long getID(){ return ID; }
    public ArrayList<VertexProperty> getProperties() { return properties;}
    
    /**
     * Safely executes the VertexProperties
     */
    public void executeProperties(){
        for(VertexProperty vp : properties)
            vp.run(this);
    }
}
