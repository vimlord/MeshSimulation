/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.control;

import java.awt.event.KeyEvent;


/* @author Christopher Hittner


*/
public class Controller {
    
    boolean[] status;
    int[] keyCodes;
    private String name;
    
    /**
     * Creates a Controller with key codes
     * @param nm The name of the Controller
     * @param codes The array of codes.
     * @precondition No two entries in the array are the same.
     */
    public Controller(String nm, int[] codes){
        name = nm;
        keyCodes = codes;
        status = new boolean[codes.length];
        for(boolean b : status){
            b = false;
        }
    }
    
    /**
     * Sets a new code at an index, given it actually exists.
     * @param index
     * @param newCode
     */
    public void setCode(int index, int newCode){
        try{
            keyCodes[index] = newCode;
        } catch(Exception ex){
            
        }
    }
    
    /**
     * Sets whether or not a key is being pressed.
     * @param code The key code
     * @param value The boolean value
     */
    public void setState(int code, boolean value){
        for(int i  = 0; i < keyCodes.length; i++){
            if(code == keyCodes[i])
                status[i] = value;
        }
    }
    
    public boolean getState(int index){
        return status[index];
    }
    
    public int getNumerOfControls(){ return keyCodes.length; }
    
    public String getName(){
        return name;
    }
    
    public String toString(){
        String result = "Controller " + name + "\n";
        for(int i = 0; i < 11; i++){
            result += "Index " + i + ": " + "\n"
                    + "    Code: " + keyCodes[i] + "\n"
                    + "    Name: " + KeyEvent.getKeyText(keyCodes[i]) + "\n"
                    + "    Pressed: " + status[i] + "\n\n";
        }
        return result;
    }
    
    
    
}
