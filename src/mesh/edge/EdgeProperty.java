/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mesh.edge;

import mesh.edge.Edge;
import mesh.properties.Property;

/**
 *
 * @author WHS-D4B1W8
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
