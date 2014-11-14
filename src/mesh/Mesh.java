/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mesh;

import java.util.ArrayList;
import mesh.vertex.Vertex;
import physics.Coordinate;
import physics.Vector;
import world.WorldManager;

/**
 *
 * @author Christopher Hittner
 */
public class Mesh {
    private long ID;
    private ArrayList<Vertex> vertices;
    
    /**
     * Creates a Mesh
     * @param v The list of Vertexes.
     */
    public Mesh(ArrayList<Vertex> v){
        
        if(v != null)
            vertices = v;
        else
            vertices = new ArrayList<>();
        
        ID = WorldManager.getWorld().claimMeshID();
    }
    
    public void applyForce(Vector force){
        
        double mass = getMass();
        
        for(Vertex v : vertices){
            double portion = v.getMass()/mass;
            v.applyForce(new Vector(force, portion));
        }
        
    }
    
    public double getMass(){
        double mass = 0;
        for(Vertex v : vertices)
            mass += v.getMass();
        
        return mass;
    }
    
    public Coordinate centerOfMass(){
        double x = 0, y = 0, z = 0;
        
        for(Vertex v : vertices){
            Coordinate coord = v.getPosition();
            x += coord.X();
            y += coord.Y();
            z += coord.Z();
        }
        
        return new Coordinate(x/vertices.size(), y/vertices.size(), z/vertices.size());
        
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }
    
    public long getMeshID() { return ID; }
    
}
