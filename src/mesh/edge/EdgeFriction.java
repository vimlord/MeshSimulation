/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mesh.edge;

import mesh.Mesh;
import mesh.vertex.Vertex;
import physics.Vector;
import world.WorldManager;

/**
 *
 * @author WHS-D4B1W8
 */
public class EdgeFriction extends EdgeProperty{
    private final double mu;
    
    public EdgeFriction(double coefficient){
        mu = coefficient;
    }
    
    /**
     * Executes friction forces within the Edge
     * @param propOwner The owner of this Property
     */
    @Override
    protected void execute(Edge propOwner) {
        
        Vertex owner = null;
        Vertex target = null;
        for(Mesh m : WorldManager.getWorld().getMeshes()){
            for(Vertex v : m.getVertices()){
                if(v.getID() == propOwner.getOwner())
                    owner = v;
                else if(v.getID() == propOwner.getTarget())
                    target = v;
            }
        }
        
        if(owner == null || target == null)
            return;
        
        Vector relativeVelocity = (new Vector(target.getVelocity(),-1));
        relativeVelocity.addVectorToThis(owner.getVelocity());
        
        double distance = Math.abs((new Vector(owner.getPosition(),target.getPosition())).getMagnitude() - propOwner.getDistance());
        
        Vector force = new Vector(relativeVelocity, -mu * propOwner.getConstant() * (distance));
        
        //System.out.println(propOwner.getConstant() + " * " + distance + " * " + mu + " = " + force.getMagnitude());
        
        owner.applyForce(force);
        target.applyForce(new Vector(force, -1));
        
        
    }

    @Override
    public boolean testConditions(Edge propOwner) {
        return true;
    }
    
}
