/* Written by Mathias Dyssegaard Kallick
 * 9-22-14
 * Defines a class which holds all the information necessary for a cell
 * in Conway's Game of Life
 * Written for CS231 Project 2
 */

import java.util.*;
import java.math.*;
import java.awt.Graphics;

public abstract class Cell {

    protected double ypos = 0.0;
    protected double xpos = 0.0;

    public Cell(double x0, double y0) {
	ypos = y0;
	xpos = x0;
    }
    	
	public abstract void updateState(Landscape scape);
	
	public abstract int getFaction();
	
	public abstract void draw(Graphics g, int x, int y, int scale);
	
	public abstract int getType();
	
	/* method getX() : returns the value of xpos, 
	 * which represents the horizontal position of the cell
	 */
    public double getX() {
		return xpos;
    }
	/* method getCol() : rounds x to the nearest int */ 
    public int getCol() {
	int X = (int)Math.round(this.getX());
		return X;
    }
    /* method getY() : returns the value of ypos,
	 * which is the vertical position of the cell
	 */
    public double getY() {
	return ypos;
    }
    /* method getRow() : rounds y to the nearest int
	 */
    public int getRow() {
		int Y = (int)Math.round(this.getY());
		return Y;
    }
    /* method toString() : just returns a "."
	 * so Landscape can reference that.
	 */
    public String toString() {
	String s = ".";
	return s;
    }
    
    /* method setPosition();
     * This method sets the position of the cell in the landscape
     * Should set an x position and a y position
     */ 
    public void setPosition(double row, double col) {
	ypos = row;
	xpos = col;
    }

    public static void main(String argv[]) {
	}
}
