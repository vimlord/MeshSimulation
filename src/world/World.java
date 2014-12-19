/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package world;

import java.util.ArrayList;
import mesh.Mesh;
import mesh.edge.Edge;
import mesh.vertex.Vertex;
import physics.Constants;
import physics.Coordinate;
import physics.Vector;
import physics.functions.Equations;
import physics.functions.Plane;

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
        }
    }
    
    
    
    private ArrayList<Integer[]> buildTetrahedronSets(ArrayList<Vertex> vertices){
        //Retrieves every vertex from every mesh.
        for(Mesh m : meshes)
            vertices.addAll(m.getVertices());
        
        //Grabs every possible pairing
        ArrayList<Integer[]> indexSets = new ArrayList<>();
        for(int i = 0; i < vertices.size(); i++)
            for(int j = i+1; j < vertices.size(); j++)
                for(int k = j+1; k < vertices.size(); k++)
                    for(int l = k+1; l < vertices.size(); l++){
                        Integer[] add = {i,j,k,l};
                        indexSets.add(add);
                    }
        
        
        
        //Makes sure they are all connected
        for(int i = indexSets.size() - 1; i >= 0; i--)
            for(int j = 0; j < 4; j++){
                
                boolean[] found = {false, false, false, false};
                
                for(int k = 0; k < 4; k++){
                    if(k == j){
                        found[k] = true;
                        if(k < 3) k++;
                        else break;
                    }
                    
                    for(Edge e : vertices.get(indexSets.get(i)[j]).getEdges()){
                        if(e.getTarget() == vertices.get(indexSets.get(i)[k]).getID()){
                            found[k] = true;
                            break;
                        }
                    }
                }
                
                if(found[0] == false || found[1] == false || found[2] == false || found[3] == false){
                    indexSets.remove(i);
                    break;
                }
                
            }
        
        return indexSets;
    }
    
    public void executeCollisions(){
        //Retrieves every vertex from every mesh.
        ArrayList<Vertex> vertices = new ArrayList<>();
        
        //Grabs every possible pairing
        ArrayList<Integer[]> indexSets = buildTetrahedronSets(vertices);
        
        //Collisions if not the same shape
        for(int i = indexSets.size() - 1; i >= 0; i--){
            for(int j = i-1; j >= 0; j--){
                runCollision(indexSets.get(i), indexSets.get(j), vertices);
                runCollision(indexSets.get(j), indexSets.get(i), vertices);
            }
        }
        
        
    }
    
    private void runCollision(Integer[] a, Integer[] b, ArrayList<Vertex> vertices){
        
        //System.out.println(vertices.size());
        
        int[] shared = {-1,-1};
        for(int k = 0; k < 4; k++)
            for(int l = 0; l < 4; l++)
            if(a[k].equals(b[l])){
                if(shared[0] == -1 || shared[1] == -1)
                    shared[1] = a[k];
                else return;
                break;
            }
            //Execute collisions

        //ArrayList<Plane> planesA = new ArrayList<>();
        ArrayList<Plane> planesB = new ArrayList<>();
        for(int l = 0; l < 4; l++){
            
            //int[] indexesA = new int[3];
            int[] indexesB = new int[3];
            for(int m = 0; m < 3; m++)
                if(m < l){
                    //indexesA[m] = a[m];
                    indexesB[m] = b[m];
                }else{
                    //indexesA[m] = a[m+1];
                    indexesB[m] = b[m+1];
                }
            //planesA.add(new Plane(vertices.get(indexesA[0]).getPosition(), vertices.get(indexesA[1]).getPosition(), vertices.get(indexesA[2]).getPosition()));
            planesB.add(new Plane(vertices.get(indexesB[0]).getPosition(), vertices.get(indexesB[1]).getPosition(), vertices.get(indexesB[2]).getPosition()));

        }
        
        for(int k = 0; k < 4; k++){
            if(a[k] == shared[0] || a[k] == shared[1]){
                k++;
                if(k > 3)
                    return;
            }
            
            Vertex v;
            try{
                v = vertices.get(a[k]);
            } catch(Exception e){
                return;
            }
            
            int confirmations = 0;
            for(int l = 0; l < planesB.size(); l++){
                Plane p = planesB.get(l);
                double testVal = p.getValue(vertices.get(b[l]).getPosition()); //System.out.print(testVal + " ");
                double vertVal = p.getValue(v.getPosition());                  //System.out.println(vertVal);
                
                if(Math.signum(testVal) == Math.signum(vertVal) && Math.abs(testVal) >= Math.abs(vertVal) && testVal != 0)
                    confirmations++;
            }
            
            //System.out.println((confirmations == 4) + "\n");
            
            if(confirmations == 4){
                /*
                while((int)(a[0]/8) != (int)(b[0]/8)){
                    
                }
                */
                //Collision action
                double massSumA = (vertices.get(a[0]).getMass() + vertices.get(a[1]).getMass() + vertices.get(a[2]).getMass() + vertices.get(a[3]).getMass());
                double massSumB = (vertices.get(b[0]).getMass() + vertices.get(b[1]).getMass() + vertices.get(b[2]).getMass() + vertices.get(b[3]).getMass());

                double X = 0, Y = 0, Z = 0;

                Vector p1 = new Vector(0,0,0);

                Vector p2 = new Vector(0,0,0);

                for(int l = 0; l < 4; l++){
                    X = (vertices.get(b[l]).getMass()*vertices.get(b[l]).getPosition().X())/massSumB - (vertices.get(a[l]).getMass()*vertices.get(a[l]).getPosition().X())/massSumA;
                    Y = (vertices.get(b[l]).getMass()*vertices.get(b[l]).getPosition().Y())/massSumB - (vertices.get(a[l]).getMass()*vertices.get(a[l]).getPosition().Y())/massSumA;;
                    Z = (vertices.get(b[l]).getMass()*vertices.get(b[l]).getPosition().X())/massSumB - (vertices.get(a[l]).getMass()*vertices.get(a[l]).getPosition().Z())/massSumA;;
                    p1.addVectorToThis(new Vector(vertices.get(a[l]).getVelocity(),vertices.get(a[l]).getMass()));
                    p2.addVectorToThis(new Vector(vertices.get(b[l]).getVelocity(),vertices.get(b[l]).getMass()));
                }
                
                Vector deltaP1 = new Vector(p2, 1);
                deltaP1.addVectorToThis(new Vector(p1,-1));
                
                Vector relativePosition = new Vector(new Coordinate(0,0,0), new Coordinate(X,Y,Z));
                
                
                if(Vector.cosOfAngleBetween(deltaP1, relativePosition) < 0)
                    return;
                
                Vector deltaP2 = new Vector(deltaP1, -1);
                
                //System.out.println(deltaP1.getMagnitude() + "\n" + deltaP2.getMagnitude() + "\n");
                
                for(int index : a)
                    vertices.get(index).applyForce(new Vector(deltaP1, vertices.get(index).getMass()/massSumA));
                for(int index : b)
                    vertices.get(index).applyForce(new Vector(deltaP2, vertices.get(index).getMass()/massSumB));
                
                return;

            }

        }
    }
    
}
