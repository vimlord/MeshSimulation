/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mesh.edge.properties;

import mesh.Mesh;
import mesh.edge.Edge;
import mesh.vertex.Vertex;
import physics.*;
import world.WorldManager;

/**
 * Makes an Edge unable to be stretched.
 * @warning This Property is unstable; don't use unless you know what you are doing!!!
 * @author Christopher Hittner
 */
public class EdgeStatic extends EdgeProperty{

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
        
        double totalMass = (owner.getMass() + target.getMass());
        
        //Computes center of mass
        Vector massFinder = new Vector(new Vector(new Coordinate(0,0,0),owner.getPosition()),owner.getMass());
        massFinder.addVectorToThis(new Vector(new Vector(new Coordinate(0,0,0),target.getPosition()),target.getMass()));
        massFinder.multiplyMagnitude(1.0/totalMass);
        Coordinate centerOfMass = new Coordinate(massFinder.getMagnitudeX(), massFinder.getMagnitudeY(), massFinder.getMagnitudeZ());
        
        //Computes the distances from the center of mass.
        double ownerDist = (totalMass) / (1 + owner.getMass() / target.getMass());
        double targDist = (totalMass) / (1 + target.getMass() / owner.getMass());
        
        //Computes a relative direction vector
        Vector direction = (new Vector(owner.getPosition(), target.getPosition())).unitVector();
        
        //Computes the new Coordinate for the owner
        Coordinate ownFinal = new Coordinate(centerOfMass.X(), centerOfMass.Y(), centerOfMass.Z());
        ownFinal.addVector(new Vector(direction, -ownerDist));
        
        //Computes the new Coordinate for the target
        Coordinate targFinal = new Coordinate(centerOfMass.X(), centerOfMass.Y(), centerOfMass.Z());
        targFinal.addVector(new Vector(direction, targDist));
        
        //Sets the new Coordinate
        owner.getPosition().addVector(new Vector(owner.getPosition(), ownFinal));
        target.getPosition().addVector(new Vector(target.getPosition(), targFinal));
        
    }

    @Override
    public boolean testConditions(Edge propOwner) {
        return true;
    }

}
