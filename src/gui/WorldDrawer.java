/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui;

import gui.camera.Camera;
import java.awt.Graphics;
import java.awt.Graphics2D;
import mesh.Mesh;
import mesh.vertex.Vertex;
import mesh.edge.Edge;
import world.*;

/**
 *
 * @author WHS-D4B1W8
 */
public class WorldDrawer {
    
    public static void drawWorld(Graphics g, Camera c){
        drawWorld(g, c, WorldManager.getWorld());
    }
    
    public static void drawWorld(Graphics g, Camera c, World w){
        
        Graphics2D g2 = (Graphics2D) g;
        
        //For every Vertex in every Mesh
        for(Mesh m : w.getMeshes()) for(Vertex v : m.getVertices()){
            //Draws the Vertex
            c.drawVertex(g, v);
            //For every Edge
            for(Edge e : v.getEdges())
                //Draws the Edge
                c.drawEdge(g, v, e);
        }
        
    }
    
    
    
}
