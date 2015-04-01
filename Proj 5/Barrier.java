/* Written by Mathias Dyssegaard Kallick
 * 10-18-2014
 * Defines a class Barrier, which is spawned by builders. It is an obstacle that cannot
 * be moved through by builders. It also cannot exist in the same space as another
 * Obstacle or a Tree, which is also an Obstacle.
 */
 
 import java.util.*;
 import java.awt.Graphics;
 import java.awt.Color;
 import java.applet.Applet;
 import java.awt.*;
 import java.awt.image.*;
 import java.io.*;
 import java.net.URL;
 import javax.imageio.*;
 
 public class Barrier extends Cell {
	
	private double xloc;
	private double yloc;
	private int type;
	private int faction;
	
	public Barrier(double x0, double y0, int fact) {
		super(x0, y0);
		xloc = x0;
		yloc = y0;
		type = 3;
		faction = fact;
	}
	
	/* method getType();
	 * simply returns the type of cell, which is set in the constructor.
	 */
	public int getType() {
		return type;
	}
	
	/* method getFaction();
	 * simply returns the faction of the cell, which is a parameter in the constructor.
	 */
	public int getFaction() {
		return this.faction;
	}
	
	/* method updateState();
	 * updates the positions of the cell based 
	 * on it's neighbors, and changes their lifevalue based on it's neighbors.
	 */
	public void updateState(Landscape scape) {
		if (scape.getBarriers() > 500) {
			scape.removeAgent(this);
		}
	}

	/* method draw();
	 * draws a cell with a given color (red).
	 */
	public void draw(Graphics g, int x0, int y0, int scale) {
		int x = x0 + (int)(this.getX() * scale);
		int y = y0 + (int)(this.getY() * scale);
 
	/*
	 *code copied from : http://docs.oracle.com/javase/tutorial/2d/images/loadimage.html
	 * it imports an image which I got from a google search for warcraft 3 crate.
	 */
		BufferedImage img = null;
		if (faction == 1) {
			try {
				img = ImageIO.read(new File("barrierimage1.png"));
			} 
			catch (IOException e) {
			}
		}
		else if (faction == 2) {
			try {
				img = ImageIO.read(new File("barrierimage2.png"));
			} 
			catch (IOException e) {
			}
		} 
		g.setColor(new Color(1.0f, 0.0f, 0.0f));
		g.drawImage(img, ((int)this.xpos) * scale, ((int)this.ypos) * scale, null);
		
		return;
	}
	/* method toString();
	 * returns a string of the values of the x and y location of the cell.
	 * Mainly for testing.
	 */
	public String toString() {
		String str = String.valueOf(xloc) + ";" + String.valueOf(yloc);
		return str;
	}
	
	public static void main(String arg[]) {
	}
}