/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mesh.vertex.properties;

import mesh.vertex.Vertex;

/**
 *
 * @author Christopher Hittner
 */
public class VertexStatic extends VertexProperty{

    @Override
    protected void execute(Vertex propOwner) {
        propOwner.getVelocity().multiplyMagnitude(0);
    }

    @Override
    public boolean testConditions(Vertex propOwner) { return true;}
    
}
