/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package world;

import java.util.ArrayList;
import mesh.Mesh;
import mesh.vertex.Vertex;
import mesh.edge.Edge;
import physics.Constants;
import physics.Vector;
import physics.functions.Equations;

/**
 *
 * @author Christopher Hittner
 */
public class World implements Constants{
    
    private ArrayList<Mesh> meshes = new ArrayList<>();
    private long availableVertexID = Long.MIN_VALUE, availableMeshID = Long.MIN_VALUE;
    
    
    public void addMesh(Mesh m){ meshes.add(m); }
    
    /**
     * Grabs an ID for a new Vertex.
     * @return
     */
    public long claimVertexID(){
        long result = availableVertexID;
        availableVertexID++;
        return result;
    }
    
    /**
     * Grabs an ID for a new Mesh.
     * @return
     */
    public long claimMeshID(){
        long result = availableMeshID;
        availableMeshID++;
        return result;
    }
    
    /**
     * Enacts the forces due to the Edges that Vertices have
     * @param factor The time over which the force occurs
     */
    public void executeEdges(double factor){
        //Executes Edge Properties
        for(int i = 0; i < meshes.size(); i++) for(Vertex a : meshes.get(i).getVertices()) for(int e = a.getEdges().size() - 1; e >= 0; e--){
            Edge edge = a.getEdges().get(e);
            edge.executeProperties();
        }
        
        //This tests for every Edge in every Vertex in every Mesh
        for(int i = 0; i < meshes.size(); i++) for(Vertex a : meshes.get(i).getVertices()) for(Edge e : a.getEdges())
            //For every Vertex in every Mesh
            for(int j = 0; j < meshes.size(); j++) for(Vertex b : meshes.get(j).getVertices())
                    if(b.getID() == e.getTarget()){
                        
                        //Force vector initially based on position
                        Vector force = new Vector(a.getPosition(),b.getPosition());
                        //Then, the default displacement is subtracted
                        force.addMagnitude(-1 * e.getDistance());
                        //After that, the vector is multiplied by the factor and the spring constant
                        force.multiplyMagnitude(factor * e.getConstant());
                        a.applyForce(force);
                        b.applyForce(new Vector(force,-1));
                        
                        /*
                        if(force.getMagnitude() != 0)
                            System.out.println((a.getID() - Long.MIN_VALUE) + "\n" + (b.getID() - Long.MIN_VALUE) + "\n" + force.toString(false) + "\n");
                        */
                    }
    }
    
    /**
     * Executes the four fundamental interactions (only two implemented): 
     * Gravity
     * Electromagnetic
     * Strong (Not implemented)
     * Weak (Not implemented)
     * @param factor
     */
    public void executeFundamentalInteractions(double factor){
        executeGravity(factor);
        executeElectromagneticForce(factor);
    }
    
    /**
     * Applies gravity between all Vertices.
     * @param factor The factor
     */
    public void executeGravity(double factor){
        //Gravity for every mesh
        for(int i = 0; i < meshes.size(); i++){
            for(int j = 0; j < meshes.size(); j++){
                for(Vertex a : meshes.get(i).getVertices()){
                    for(Vertex b : meshes.get(j).getVertices()){
                        //Error check to make sure a isn't b. If it was, a divide by zero error would occur.
                        if(!a.equals(b)){
                            Vector force = Equations.gravitationalForce(a, b);
                            force.multiplyMagnitude(factor);
                            a.applyForce(force);
                            b.applyForce(new Vector(force, -1));
                        }
                    }
                }
            }
        }
        
    }
    
    /**
     * Applied magnetic attractions and repulsions between all Vertices.
     * @param factor The factor
     */
    public void executeElectromagneticForce(double factor){
        for(int i = 0; i < meshes.size(); i++){
            for(int j = i+1; j < meshes.size(); j++){
                for(Vertex a : meshes.get(i).getVertices())
                    for(Vertex b : meshes.get(j).getVertices()){
                        //Error check to make sure a isn't b. If it was, a divide by zero error would occur.
                        if(!a.equals(b)){
                            Vector force = Equations.gravitationalForce(a, b);
                            force.multiplyMagnitude(factor);
                            a.applyForce(force);
                            b.applyForce(new Vector(force, -1));
                        }
                        
                    }
            }
        }
        
    }
    
    public int getMeshListSize(){ return meshes.size(); }
    
    /**
     * Grabs the mesh at the requested index in the ArrayList.
     * @param index
     * @return The Mesh at the given index
     */
    public Mesh getMesh(int index){ return meshes.get(index); }
    
    /**
     * Grabs the Mesh with the requested ID.
     * @param ID
     * @return The Mesh matching the ID, or null
     */
    public Mesh getMesh(long ID){
        for(Mesh m : meshes){
            if(m.getMeshID() == ID)
                return m;
        }
        return null;
    }

    public ArrayList<Mesh> getMeshes() {
        return meshes;
    }

    public void executeMovement(double time) {
        for(Mesh m : getMeshes()) for(Vertex v : m.getVertices()){
            v.executeProperties();
            Vector velocity = new Vector(v.getVelocity(),time);
            v.getPosition().addVector(velocity);
            //System.out.println("Index " + (v.getID() + Long.MIN_VALUE) + ": " + v.getVelocity().toString(true));
        }
    }
    
}
