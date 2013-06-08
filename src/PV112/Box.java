/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PV112;

/**
 *
 * @author Kaspar
 */
public class Box implements Comparable {
    
    public int rotation;
    public int position;
    
    public Box()
    {
        rotation =(int)(Math.random() * 360.0);
        position = (int)(-64.0 - 60 + Math.random() * 90.0);
    }
    
    public Box(int rot, int pos)
    {
        rotation = rot;
        position = pos;
    }
    
    @Override
    public String toString() {
        return "box, r: " + rotation + ", p: " + position;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof Box)
        {
            Box box = (Box)o;
            if(Math.abs(rotation - box.rotation) < 5 && Math.abs(position - box.position) < 5)
            {
                return 0;
            }
            else
            {
                return 1;
            }
        }
        else
        {
            return -1;
        }
    }
    
    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 17 + rotation;
        hash = hash * 31 + position;
        return hash;
    }
    
}
