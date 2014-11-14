/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mesh.vertex.properties;

import mesh.properties.Property;
import mesh.vertex.Vertex;

/**
 *
 * @author Christopher Hittner
 */
public abstract class VertexProperty extends Property{
    
    @Override
    public final boolean testConditions(Object propOwner) {
        return testConditions((Vertex) propOwner);
    }
    
    @Override
    protected final void execute(Object propOwner) {
        execute((Vertex) propOwner);
    }
    
    protected abstract void execute(Vertex propOwner);
    public abstract boolean testConditions(Vertex propOwner);
}
