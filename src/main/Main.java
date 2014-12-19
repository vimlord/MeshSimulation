/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import gui.GUI;
import gui.control.Controller;
import gui.control.ArmController;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import mesh.Mesh;
import mesh.edge.AdjustableEdge;
import mesh.edge.Edge;
import mesh.edge.properties.*;
import mesh.vertex.Vertex;
import mesh.vertex.properties.*;
import physics.Coordinate;
import physics.Vector;
import world.World;
import world.WorldManager;

/**
 *
 * @author Christopher Hittner
 */
public class Main {
    
    public static void main(String[] args){
        //GUI.initialize();
        
        GUI.getGUI().setController(buildArmController("Test"));
        
        WorldManager.setWorld(buildCollisionDemo());
        
        //WorldManager.getWorld().addMesh(buildArm(new Coordinate(25,0,0),10,20000,20000));
        
        
        WorldManager.startSimulation();
        
        while(true){
            GUI.getGUI().redraw();
            
        }
    }
    
    
    
    
    public static Mesh buildSpring(double mass, double length, double k, double equilibDist, Coordinate co){
        //Creates Vertices
        ArrayList<Vertex> vertices = new ArrayList<>();
        for(int i = 0; i < 2; i++){
            vertices.add(new Vertex(new Coordinate(co.X() ,co.Y() + i * length * (-0.5 + i), co.Z()), new ArrayList<Edge>(), mass, 0));
        
        }
        
        vertices.get(0).getEdges().add(new Edge(vertices.get(0).getID(), vertices.get(1).getID(), k, equilibDist, null));
        
        return new Mesh(vertices);
        
    }
    
    public static Mesh buildCube(double mass, double side, double constant, double distFactor, Coordinate co){
        //Creates Vertices
        ArrayList<Vertex> vertices = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            vertices.add(new Vertex(new Coordinate(co.X() + side * (i%2) ,co.Y() + side * (i%4 - i%2)/2, co.Z() + side * (i%8 - i%4)/4), new ArrayList<Edge>(), mass, 0));
        
        }
        
        //vertices.get(0).applyForce(new Vector(20, Math.toRadians(190), Math.toRadians(-7)));
        
        ArrayList<EdgeProperty> prop = new ArrayList<>();
        //prop.add(new EdgeFriction(0.001));
        
        //Creates Edges
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(i!=j){
                    Edge e = new Edge(vertices.get(i).getID(), vertices.get(j).getID(), constant,(new Vector(vertices.get(i).getPosition(),vertices.get(j).getPosition())).getMagnitude() * (distFactor),prop);
                    vertices.get(i).getEdges().add(e);
                }
            }
        }
        
        //Builds and sends the Mesh
        return new Mesh(vertices);
    }
    
    
    public static Mesh buildArm(Coordinate anchor, double pointMasses, double boneStrength, double muscleStrength){
        ArrayList<Vertex> vertices = new ArrayList<>();
        
        //Default Properties for Edges
        ArrayList<EdgeProperty> prop = new ArrayList<>();
        prop.add(new EdgeFriction(0.0001));
        
        for(int i = 0; i < 3; i++){
            vertices.add(new Vertex(new Coordinate(anchor.X(), anchor.Y(), anchor.Z() + (10 * i)), null, pointMasses, 0));
            if(i > 0)
                vertices.get(i).getEdges().add(new Edge(vertices.get(i).getID(), vertices.get(i-1).getID(), boneStrength, 10, prop));
        }
        
        for(int i = 0; i < 12; i++){
            vertices.add(new Vertex(new Coordinate(anchor.X() + Math.cos(i*Math.PI/2), anchor.Y() + Math.sin(i*Math.PI/2), anchor.Z() + (10 * (int)(i/4.0))), null, pointMasses, 0));
            vertices.get(i+3).getEdges().add(new Edge(vertices.get(i+3).getID(), vertices.get((int)(i/4.0)).getID(), boneStrength, 1, prop));
            if((int)(i/4) == 0)
                vertices.get(i+3).getProperties().add(new VertexStatic());
            if((i%4) > 1)
                vertices.get(i+3).getEdges().add(new Edge(vertices.get(i+3).getID(), vertices.get(i+1).getID(), boneStrength, 2, prop));
        }
        
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 4; j++){
                if(i > 0)
                    vertices.get(i).getEdges().add(new Edge(vertices.get(i).getID(), vertices.get((int)(-1 + 4*i + j)).getID(), boneStrength, Math.sqrt(101), prop));
                if(i < 2)
                    vertices.get(i).getEdges().add(new Edge(vertices.get(i).getID(), vertices.get((int)(7 + 4*i + j)).getID(), boneStrength, Math.sqrt(101), prop));
            }
        }
        
        
        for(int i = 3; i <= 10; i++){
            int[] indexes = {i+3,i+4,i+5};
            for(int j : indexes){
                if(j > 10 + 4*(int)((i-3)/4))
                    j-=4;
                else if(j < 7 + 4*(int)((i-3)/4))
                    j+=4;
                vertices.get(i).getEdges().add(new AdjustableEdge(vertices.get(i).getID(), vertices.get(j).getID(), boneStrength, new Vector(vertices.get(i).getPosition(),vertices.get(j).getPosition()).getMagnitude(), prop));
            }
        }
        
        for(int i = 0; i <= 3; i++){
            vertices.get(11+i).getEdges().add(new Edge(vertices.get(11+i).getID(), vertices.get(11 + (i+1)%4).getID(), boneStrength, Math.sqrt(2), prop));
            vertices.get(7+i).getEdges().add(new AdjustableEdge(vertices.get(7+i).getID(), vertices.get(7 + (i+1)%4).getID(), boneStrength, Math.sqrt(2), prop));
        }
            
        
        return new Mesh(vertices);
        
    }
    
    
    /**
     * Creates an arm.
     * @param anchor The anchor point.
     * @param pointMasses
     * @param boneStrength  The spring constant of the bones.
     * @param muscleStrength The spring constant of the muscles.
     * @return The arm.
     * 
     * Vertices 1-4 have an AdjustableEdge at index 0. This is the upper arm muscle.
     *  1-0 = Right
     *  2-0 = Left
     *  3-0 = Up
     *  4-0 = Down
     *  7-2 and 8-2 and 9-3 and 10-2 = Clockwise
     *  7-3 and 8-1 and 9-2 and 10-1 = Counterclockwise
     *  9-4 = Contract
     *  10-1 = Extend
     * Vertices 7-8 have two AdjustableEdges that will control the "elbow" rotation at indexes 4 and 5
     * Vertices 9-10 have AdjustableEdges that control elevation. 9 has it at 2, 10 has it at 1
     * 
     */
    public static Mesh buildArm_V1(Coordinate anchor, double pointMasses, double boneStrength, double muscleStrength){
        ArrayList<Vertex> vertices = new ArrayList<>();
        
        //Default Properties for Edges
        ArrayList<EdgeProperty> prop = new ArrayList<>();
        prop.add(new EdgeFriction(0.0001));
        
        //////////////////////
        //Arm Base/Mount
        //////////////////////
        vertices.add(new Vertex(anchor, null, pointMasses, 0)); //0
        vertices.add(new Vertex(new Coordinate(anchor.X() + 1, anchor.Y(), anchor.Z()), null, pointMasses, 0)); //1
        vertices.add(new Vertex(new Coordinate(anchor.X() - 1, anchor.Y(), anchor.Z()), null, pointMasses, 0)); //2
        vertices.add(new Vertex(new Coordinate(anchor.X(), anchor.Y() + 1, anchor.Z()), null, pointMasses, 0)); //3
        vertices.add(new Vertex(new Coordinate(anchor.X(), anchor.Y() - 1, anchor.Z()), null, pointMasses, 0)); //4
        
        for(int i = 0; i < 5; i++)
            vertices.get(i).getProperties().add(new VertexStatic());
        
        //////////////////////
        //The arm itself
        //////////////////////
        vertices.add(new Vertex(new Coordinate(anchor.X(), anchor.Y(), anchor.Z() + 10), null, pointMasses, 0)); //5
        vertices.get(5).getEdges().add(new Edge(vertices.get(5).getID(), vertices.get(0).getID(), boneStrength, 10, prop));
        vertices.add(new Vertex(new Coordinate(anchor.X(), anchor.Y(), anchor.Z() + 20), null, pointMasses, 0)); //6
        vertices.get(5).getEdges().add(new Edge(vertices.get(5).getID(), vertices.get(6).getID(), boneStrength, 10, prop));
        //////////////////////
        
        //////////////////////
        //Mid-arm Joint
        //////////////////////
        vertices.add(new Vertex(new Coordinate(anchor.X() + 1, anchor.Y(), anchor.Z() + 10), null, pointMasses, 0)); //7
        vertices.add(new Vertex(new Coordinate(anchor.X() - 1, anchor.Y(), anchor.Z() + 10), null, pointMasses, 0)); //8
        vertices.add(new Vertex(new Coordinate(anchor.X(), anchor.Y() + 1, anchor.Z() + 10), null, pointMasses, 0)); //9
        vertices.add(new Vertex(new Coordinate(anchor.X(), anchor.Y() - 1, anchor.Z() + 10), null, pointMasses, 0)); //10
        for(int i = 7; i < 11; i++)
            vertices.get(i).getEdges().add(new Edge(vertices.get(i).getID(), vertices.get(5).getID(), boneStrength, 1, prop));
        vertices.get(7).getEdges().add(new Edge(vertices.get(7).getID(), vertices.get(8).getID(), boneStrength, 2, prop));
        vertices.get(9).getEdges().add(new Edge(vertices.get(9).getID(), vertices.get(10).getID(), boneStrength, 2, prop));
        //////////////////////
        
        //////////////////////
        //Elbow Rotation
        //////////////////////
        
        vertices.get(7).getEdges().add(new AdjustableEdge(vertices.get(7).getID(), vertices.get(3).getID(), boneStrength, Math.sqrt(102), prop));
        vertices.get(7).getEdges().add(new AdjustableEdge(vertices.get(7).getID(), vertices.get(4).getID(), boneStrength, Math.sqrt(102), prop));
        
        vertices.get(8).getEdges().add(new AdjustableEdge(vertices.get(8).getID(), vertices.get(3).getID(), boneStrength, Math.sqrt(102), prop));
        vertices.get(8).getEdges().add(new AdjustableEdge(vertices.get(8).getID(), vertices.get(4).getID(), boneStrength, Math.sqrt(102), prop));
        
        vertices.get(9).getEdges().add(new AdjustableEdge(vertices.get(9).getID(), vertices.get(1).getID(), boneStrength, Math.sqrt(102), prop));
        vertices.get(9).getEdges().add(new AdjustableEdge(vertices.get(9).getID(), vertices.get(2).getID(), boneStrength, Math.sqrt(102), prop));
        
        vertices.get(10).getEdges().add(new AdjustableEdge(vertices.get(10).getID(), vertices.get(1).getID(), boneStrength, Math.sqrt(102), prop));
        vertices.get(10).getEdges().add(new AdjustableEdge(vertices.get(10).getID(), vertices.get(2).getID(), boneStrength, Math.sqrt(102), prop));
        
        
        //////////////////////
        //Inter-Sectional Joint Connection Improvements
        //////////////////////
        for(int i = 7; i < 11; i++){
            if(i > 8)
                vertices.get(i).getEdges().add(new AdjustableEdge(vertices.get(i).getID(), vertices.get(6).getID(), muscleStrength, Math.sqrt(101), prop));
            else {
                vertices.get(i).getEdges().add(new Edge(vertices.get(i).getID(), vertices.get(6).getID(), boneStrength, Math.sqrt(101), prop));
                vertices.get(i).getEdges().add(new Edge(vertices.get(i).getID(), vertices.get(i - 6).getID(), boneStrength, 10, prop));
            }
            vertices.get(i).getEdges().add(new Edge(vertices.get(i).getID(), vertices.get(0).getID(), boneStrength, Math.sqrt(101), prop));
        }
        
        //////////////////////
        //Upper Arm Muscle
        /////////////////////
        for(int i = 1; i < 5; i++){
            //Muscle
            vertices.get(i).getEdges().add(new AdjustableEdge(vertices.get(i).getID(), vertices.get(5).getID(), muscleStrength, Math.sqrt(101), prop));
            
            //Connections at joints
            vertices.get(i).getEdges().add(new Edge(vertices.get(i).getID(), vertices.get(0).getID(), 100, 1, prop));
            
        }
        
        
        vertices.get(7).getEdges().add(new Edge(vertices.get(7).getID(), vertices.get(9).getID(), 10000, Math.sqrt(2), prop));
        vertices.get(7).getEdges().add(new Edge(vertices.get(7).getID(), vertices.get(10).getID(), 10000, Math.sqrt(2), prop));
        
        vertices.get(8).getEdges().add(new Edge(vertices.get(8).getID(), vertices.get(9).getID(), 10000, Math.sqrt(2), prop));
        vertices.get(8).getEdges().add(new Edge(vertices.get(8).getID(), vertices.get(10).getID(), 10000, Math.sqrt(2), prop));
        
        
        
        /*
        for(int i = 0; i < vertices.size(); i++)
            for(int j = 0; j < vertices.get(i).getEdges().size(); j++)
                if(vertices.get(i).getEdges().get(j) instanceof AdjustableEdge)
                    System.out.println("Index " + (i) + "\nSub-index " + (j) + "\n");
        */
        
        
        //Flexes upper arm upward
        if(false){
            ((AdjustableEdge) (vertices.get(3).getEdges().get(0))).incrementDistance(-1);
            ((AdjustableEdge) (vertices.get(4).getEdges().get(0))).incrementDistance(1);
        }
        
        //Rotates Elbow
        if(false){
            double dist = 1;
            ((AdjustableEdge) (vertices.get(7).getEdges().get(2))).incrementDistance(-dist);
            ((AdjustableEdge) (vertices.get(8).getEdges().get(2))).incrementDistance(-dist);
            ((AdjustableEdge) (vertices.get(9).getEdges().get(3))).incrementDistance(-dist);
            ((AdjustableEdge) (vertices.get(10).getEdges().get(2))).incrementDistance(-dist);
            ((AdjustableEdge) (vertices.get(7).getEdges().get(3))).incrementDistance(dist);
            ((AdjustableEdge) (vertices.get(8).getEdges().get(1))).incrementDistance(dist);
            ((AdjustableEdge) (vertices.get(9).getEdges().get(2))).incrementDistance(dist);
            ((AdjustableEdge) (vertices.get(10).getEdges().get(3))).incrementDistance(dist);
        }
        //Flexes lower arm upward
        if(false){
            ((AdjustableEdge) (vertices.get(9).getEdges().get(4))).incrementDistance(-1);
            ((AdjustableEdge) (vertices.get(10).getEdges().get(3))).incrementDistance(1);
        }
        
        return new Mesh(vertices);
        
        
    }
    
    
    public static Controller buildArmController(String name){
        int[] indexes = {
            KeyEvent.VK_UP, 
            KeyEvent.VK_DOWN, 
            KeyEvent.VK_LEFT, 
            KeyEvent.VK_RIGHT, 
            KeyEvent.VK_W, 
            KeyEvent.VK_A, 
            KeyEvent.VK_S, 
            KeyEvent.VK_D,
            KeyEvent.VK_T, 
            KeyEvent.VK_G, 
            KeyEvent.VK_F, 
            KeyEvent.VK_H,
            KeyEvent.VK_I, 
            KeyEvent.VK_K, 
            KeyEvent.VK_J, 
            KeyEvent.VK_L,
        };
        
        return new ArmController(name, indexes);
    }
    
    public static Controller build2DController(String name){
        int[] indexes = {KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D};
        
        return new Controller(name, indexes);
    }
    
    /**
     * Builds a World with two cubes in it.
     * @return The requested World
     */
    public static World buildCubeDemo(){
        
        World w = new World();
        
        w.addMesh(buildCube(10, 10, 10, 1, new Coordinate(40,3,6)));
        w.addMesh(buildCube(10, 10, 10, 1.25, new Coordinate(40,-3,-16)));
        
        return w;
        
    }
    
    /**
     * Builds a World with two colliding cubes in it.
     * @return The requested World
     */
    public static World buildCollisionDemo(){
        
        World w = new World();
        
        w.addMesh(buildCube(10, 10, 10, 1, new Coordinate(40,0,11)));
        w.addMesh(buildCube(10, 15, 10, 1, new Coordinate(37.5,-2.5,-21)));
        w.getMesh(1).applyForce(new Vector(500.0,Math.PI/2.0,0.0));
        w.getMesh(0).applyForce(new Vector(-500.0,Math.PI/2.0,0.0));
        
        return w;
        
    }
    
    /**
     * Builds a World with two springs and a reference cube.
     * @return The requested World
     */
    public static World buildSpringDemo(){
        
        World w = new World();
        
        w.addMesh(buildSpring(1,5, 1, 20, new Coordinate(40,0,10)));
        w.addMesh(buildSpring(1,5, 1, 10, new Coordinate(40,0,-10)));
        w.addMesh(buildCube(10, 10, 10, 1, new Coordinate(40,-5,-5)));
        
        return w;
    }
    
}
