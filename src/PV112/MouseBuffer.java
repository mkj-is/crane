/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PV112;

/**
 *
 * @author Kaspar
 */
public class MouseBuffer {
    
    private int startX = 0;
    private int startY = 0;
    private int diffX = 0;
    private int diffY = 0;
    private int lastX = 0;
    private int lastY = 0;
    
    public void press(int x, int y)
    {
        startX = x;
        startY = y;
        lastX = x;
        lastY = y;
        diffX = 0;
        diffY = 0;
        
    }
    
    public void move(int x, int y)
    {
        diffX = x - startX;
        diffY = y - startY;
        lastX = x;
        lastY = y;
    }
    
    public int[] getChange()
    {
        int[] loc = {diffX, diffY};
        startX = lastX;
        startY = lastY;
        diffX = 0;
        diffY = 0;
        return loc;
    }
    
    public void release()
    {
        startX = 0;
        startY = 0;
        lastX = 0;
        lastY = 0;
        diffX = 0;
        diffY = 0;
    }
    
}
