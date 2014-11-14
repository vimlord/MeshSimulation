/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mesh.properties;

/**
 * A Property is a template that gives an Object traits that allow it to do different things.
 * @author Christopher Hittner
 */
public abstract class Property {
    
    /**
     *
     * @param propOwner The owner of this Property
     * @return Whether the property should be run
     */
    protected abstract boolean testConditions(Object propOwner);
    protected abstract void execute(Object propOwner);
    
    public final void run(Object propOwner){
        if(testConditions(propOwner))
            execute(propOwner);
    }
}
