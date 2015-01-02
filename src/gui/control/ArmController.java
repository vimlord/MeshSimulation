/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.control;

import gui.GUI;
import mesh.edge.AdjustableEdge;
import physics.Vector;
import world.WorldManager;

/**
 *
 * @author Christopher Hittner
 */
public class ArmController extends Controller{

    public ArmController(String nm, int[] codes) {
        super(nm, codes);
    }
    
    public void execute(){
        if(getState(0))
            GUI.getGUI().getCamera().getPosition().addVector(new Vector(2.5 * Math.pow(10, -2), GUI.getGUI().getCamera().getXZ(), GUI.getGUI().getCamera().getY()));
        if(getState(1))
            GUI.getGUI().getCamera().getPosition().addVector(new Vector(2.5 * Math.pow(10, -2), GUI.getGUI().getCamera().getXZ() + Math.PI, -GUI.getGUI().getCamera().getY()));
        if(getState(2))
            GUI.getGUI().getCamera().getPosition().addVector(new Vector(2.5 * Math.pow(10, -2), GUI.getGUI().getCamera().getXZ() + Math.PI/2.0, 0));
        if(getState(3))
            GUI.getGUI().getCamera().getPosition().addVector(new Vector(2.5 * Math.pow(10, -2), GUI.getGUI().getCamera().getXZ() - Math.PI/2.0, 0));

        if(getState(4))
            GUI.getGUI().getCamera().setDirection(new Vector(1, GUI.getGUI().getCamera().getXZ(), GUI.getGUI().getCamera().getY() + 5 * Math.pow(10, -3)));
        if(getState(5))
            GUI.getGUI().getCamera().setDirection(new Vector(1, GUI.getGUI().getCamera().getXZ() + 5 * Math.pow(10, -3), GUI.getGUI().getCamera().getY()));
        if(getState(6))
            GUI.getGUI().getCamera().setDirection(new Vector(1, GUI.getGUI().getCamera().getXZ(), GUI.getGUI().getCamera().getY() - 5 * Math.pow(10, -3)));
        if(getState(7))
            GUI.getGUI().getCamera().setDirection(new Vector(1, GUI.getGUI().getCamera().getXZ() - 5 * Math.pow(10, -3), GUI.getGUI().getCamera().getY()));
        
        
        if(true){
            if(getState(8)){
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(4).getAdjustableEdges().get(0).incrementDistance(-.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(4).getAdjustableEdges().get(1).incrementDistance(-.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(4).getAdjustableEdges().get(2).incrementDistance(-.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(6).getAdjustableEdges().get(0).incrementDistance(.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(6).getAdjustableEdges().get(1).incrementDistance(.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(6).getAdjustableEdges().get(2).incrementDistance(.001);
            }
            if(getState(9)){
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(4).getAdjustableEdges().get(0).incrementDistance(.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(4).getAdjustableEdges().get(1).incrementDistance(.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(4).getAdjustableEdges().get(2).incrementDistance(.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(6).getAdjustableEdges().get(0).incrementDistance(-.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(6).getAdjustableEdges().get(1).incrementDistance(-.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(6).getAdjustableEdges().get(2).incrementDistance(-.001);
            }
            if(getState(10)){
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(3).getAdjustableEdges().get(0).incrementDistance(.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(3).getAdjustableEdges().get(1).incrementDistance(.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(3).getAdjustableEdges().get(2).incrementDistance(.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(5).getAdjustableEdges().get(0).incrementDistance(-.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(5).getAdjustableEdges().get(1).incrementDistance(-.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(5).getAdjustableEdges().get(2).incrementDistance(-.001);
            }
            if(getState(11)){
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(3).getAdjustableEdges().get(0).incrementDistance(-.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(3).getAdjustableEdges().get(1).incrementDistance(-.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(3).getAdjustableEdges().get(2).incrementDistance(-.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(5).getAdjustableEdges().get(0).incrementDistance(.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(5).getAdjustableEdges().get(1).incrementDistance(.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(5).getAdjustableEdges().get(2).incrementDistance(.001);
            }
            
            //For 12 and 13, it should match 8 and 9.
            
            if(getState(12)){
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(8).getAdjustableEdges().get(0).incrementDistance(-.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(8).getAdjustableEdges().get(1).incrementDistance(-.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(8).getAdjustableEdges().get(2).incrementDistance(-.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(10).getAdjustableEdges().get(0).incrementDistance(.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(10).getAdjustableEdges().get(1).incrementDistance(.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(10).getAdjustableEdges().get(2).incrementDistance(.001);
            }
            if(getState(13)){
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(8).getAdjustableEdges().get(0).incrementDistance(.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(8).getAdjustableEdges().get(1).incrementDistance(.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(8).getAdjustableEdges().get(2).incrementDistance(.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(10).getAdjustableEdges().get(0).incrementDistance(-.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(10).getAdjustableEdges().get(1).incrementDistance(-.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(10).getAdjustableEdges().get(2).incrementDistance(-.001);
            }
            if(getState(14)){
                
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(7).getAdjustableEdges().get(0).incrementDistance(-.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(7).getAdjustableEdges().get(2).incrementDistance(.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(8).getAdjustableEdges().get(0).incrementDistance(-.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(8).getAdjustableEdges().get(2).incrementDistance(.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(9).getAdjustableEdges().get(0).incrementDistance(-.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(9).getAdjustableEdges().get(2).incrementDistance(.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(10).getAdjustableEdges().get(0).incrementDistance(-.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(10).getAdjustableEdges().get(2).incrementDistance(.001);
                
                
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(7).getAdjustableEdges().get(2).incrementDistance(.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(8).getAdjustableEdges().get(2).incrementDistance(-.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(9).getAdjustableEdges().get(2).incrementDistance(.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(10).getAdjustableEdges().get(2).incrementDistance(-.001);
            }
            if(getState(15)){
                
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(7).getAdjustableEdges().get(0).incrementDistance(.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(7).getAdjustableEdges().get(2).incrementDistance(-.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(8).getAdjustableEdges().get(0).incrementDistance(.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(8).getAdjustableEdges().get(2).incrementDistance(-.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(9).getAdjustableEdges().get(0).incrementDistance(.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(9).getAdjustableEdges().get(2).incrementDistance(-.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(10).getAdjustableEdges().get(0).incrementDistance(.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(10).getAdjustableEdges().get(2).incrementDistance(-.001);
                
                
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(7).getAdjustableEdges().get(2).incrementDistance(-.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(8).getAdjustableEdges().get(2).incrementDistance(.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(9).getAdjustableEdges().get(2).incrementDistance(-.001);
                WorldManager.getWorld().getMeshes().get(0).getVertices().get(10).getAdjustableEdges().get(2).incrementDistance(.001);
            }
        }
    }
    
}
