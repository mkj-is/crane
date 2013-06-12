/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PV112;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Kaspar
 */
public class KeyBuffer {
    
    public Set<Integer> keys = new HashSet<Integer>();
    
    public void press(int key)
    {
        keys.add(new Integer(key));
    }
    
    public void release(int key)
    {
        keys.remove(new Integer(key));
    }
    
    public boolean isPressed(int key)
    {
        return keys.contains(new Integer(key));
    }
    
    
}
