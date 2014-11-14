/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.camera;

import gui.GUI;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import mesh.Mesh;
import mesh.edge.AdjustableEdge;
import mesh.edge.Edge;
import mesh.vertex.Vertex;
import physics.Coordinate;
import physics.Vector;
import world.WorldManager;

/**
 *
 * @author Christopher Hittner
 */
public class Camera {
    
    private Vector direction;
    private Coordinate position;
    private double axialRot = 0;
    
    public Camera(Coordinate loc, double XZ, double Y){
        direction = new Vector(1,XZ,Y);
        position = loc;
    }
    
    /**
     * Returns a polar coordinate set.
     * Index 0 is the angle difference in radians
     * Index 1 is the rotation difference in radians
     * @param point
     * @return A polar coordinate
     */
    public double[] getPolarCoordinate(Coordinate point){
        double[] result = new double[2];
        
        //Generates the distance from the center
        double distance = (new Vector(position,point)).getMagnitude();
        Coordinate focus1 = new Coordinate(position.X(), position.Y(), position.Z());
        focus1.addVector(new Vector(direction.unitVector(), -distance/2.0));
        double focalLength = (new Vector(focus1,point)).getMagnitude();        
        
        /*
        System.out.println("T = " + point);
        System.out.println("C = " + position);
        System.out.println("F = " + focus1);
        
        System.out.println("d =  " + distance);
        System.out.println("f =  " + focalLength);
        */
        
        result[0] = -Math.acos(Math.pow(focalLength,2)/Math.pow(distance,2)-1.25);
        
        if(result[0] == 0){
            result[1] = 0;
            return result;
        }
        
        //double expectedOffset = Math.sqrt(Math.pow(focalLength, 2) - Math.pow((distance/2.0 + distance*Math.cos(result[0])),2));
        double expectedOffset = distance * Math.sin(result[0]);
        //System.out.println(distance + "\n" + (result[0] * 180/Math.PI) + "\n" + (distance * Math.sin(result[0])) + "\n");
        
        Coordinate focus2 = new Coordinate(position.X(), position.Y(), position.Z());
        focus2.addVector(new Vector(direction.unitVector(), distance*Math.cos(result[0])));
        
        Coordinate X = new Coordinate(focus2.X(),focus2.Y(),focus2.Z());
        X.addVector(new Vector(expectedOffset, (new Vector(direction, 1)).getAngleXZ() + Math.toRadians(90), 0));
        
        Coordinate Y = new Coordinate(focus2.X(),focus2.Y(),focus2.Z());
        Y.addVector(new Vector(expectedOffset, (new Vector(direction, 1)).getAngleXZ(), (new Vector(direction, 1)).getAngleY() + Math.toRadians(90)));
        
        double x = (new Vector(X,point)).getMagnitude(),
               y = (new Vector(Y,point)).getMagnitude();
        
        double cos = 1 - (Math.pow(x,2)/(2*Math.pow(expectedOffset,2)));
        double sin = 1 - (Math.pow(y, 2)/(2*Math.pow(expectedOffset,2)));
        
        result[1] = Math.acos(Math.min(Math.max(-1.0, cos),1.0));
        result[1] *= Math.signum(Math.asin(Math.min(Math.max(-1.0, sin),1.0)));
        result[1] -= axialRot;
        
        /*
        System.out.println("arcsin(" + (1 - (Math.pow(y,2)/(2*Math.pow(expectedOffset,2)))) + ") = " + Math.asin(1 - (Math.pow(y,2)/(2*Math.pow(expectedOffset,2)))));
        System.out.println("arccos(" + (1 - (Math.pow(x,2)/(2*Math.pow(expectedOffset,2)))) + ") = " + Math.acos(1 - (Math.pow(x,2)/(2*Math.pow(expectedOffset,2)))));
        System.out.println("Result: (" + result[0] + "," + result[1] + ")\n");
        */
        
        return result;
    }
    
    /**
     * Returns the XY coordinate where a Coordinate should be drawn
     * @param c
     * @return
     */
    public int[] getPlanarCoordinate(Coordinate c){
        double[] polarCoord = getPolarCoordinate(c);
        
        int[] result = new int[2];
        
        GUI gui = GUI.getGUI();
        result[0] = (int) (gui.getCenterX() - gui.getPixelsPerRadian() * polarCoord[0] * Math.cos(polarCoord[1]));
        result[1] = (int) (gui.getCenterY() - gui.getPixelsPerRadian() * polarCoord[0] * Math.sin(polarCoord[1]));
        
        return result;
    }
    
    public void drawCoordinate(Graphics g, Coordinate c){
        Graphics2D g2 = (Graphics2D) g;
        
        int[] coord = getPlanarCoordinate(c);
        
        //System.out.println(coord[0] + "," + coord[1]);
        
        g2.drawOval(coord[0] - 2, coord[1] - 2, 4, 4);
    }
    
    public void drawVertex(Graphics g, Vertex v){
        //System.out.println("Vertex " + (v.getID() - Long.MIN_VALUE) + ":");
        drawCoordinate(g,v.getPosition());
    }
    
    public void drawEdge(Graphics g, Vertex v, Edge e){
        Coordinate destination = null;
        for(Mesh m : WorldManager.getWorld().getMeshes()){
            for(Vertex ver : m.getVertices())
                if(ver.getID() == e.getTarget()){
                    destination = ver.getPosition();
                    break;
                }
        }
        
        //If the Vertex doesn't exist, there's nothing to draw.
        if(destination == null)
            return;
        
        int[] start = getPlanarCoordinate(v.getPosition());
        int[] finish = getPlanarCoordinate(destination);
        
        Graphics2D g2 = (Graphics2D) g;
        
        //Adjustable Edges will be red because they are a lot like muscle.
        if(e instanceof AdjustableEdge)
            g2.setColor(Color.RED);
        
        //This stops the Camera from drawing unnecessary Edges (because it will draw stuff that shouldn't be there)
        if(Math.sqrt(Math.pow(start[0] - finish[0], 2) + Math.pow(start[1] - finish[1], 2)) < Math.PI * GUI.getGUI().getPixelsPerRadian())
            g2.drawLine(start[0],start[1],finish[0],finish[1]);
        
        g2.setColor(Color.BLACK);
        
    }
    
    public void setDirection(Vector dir){
        direction = dir.unitVector();
    }
    
    public void setPosition(Coordinate c){
        position = c;
    }
    
    public void setAxialRot(double val){
        axialRot = val;
    }
    
    
    
    public double getXZ(){ return direction.getAngleXZ(); }
    public double getY(){ return direction.getAngleY(); }
    public double getAxialRot(){ return axialRot; }
    public Coordinate getPosition(){ return position; }
    public Vector getDirection(){ return direction; }
    
    
}
