/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mesh.edge.properties;

import mesh.edge.Edge;
import mesh.edge.Edge;
import mesh.properties.Property;

/**
 * Edge Properties are used to modify Edges.
 * @author Christopher Hittner
 */
public abstract class EdgeProperty extends Property {

    @Override
    public final boolean testConditions(Object propOwner) {
        return testConditions((Edge) propOwner);
    }
    
    @Override
    protected final void execute(Object propOwner) {
        execute((Edge) propOwner);
    }
    
    protected abstract void execute(Edge propOwner);
    public abstract boolean testConditions(Edge propOwner);
    
}
