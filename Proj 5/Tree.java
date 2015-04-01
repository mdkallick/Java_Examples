/* Written by Mathias Dyssegaard Kallick
 * 10-18-2014
 * Defines a class Tree, which spawns wood for Builders to pick up. If a Tree runs out
 * of wood, it dies. A Tree spawns wood every 4 turns. Also, a Tree is a barrier which
 * builders cannot move through.
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
 
 public class Tree extends Cell {
	
	private double xloc;
	private double yloc;
	private int woodAmount;
	private int type;
	private int i;
	private boolean checkForTrees;
	
	public Tree(double x0, double y0) {
		super(x0, y0);
		xloc = x0;
		yloc = y0;
		woodAmount = 3;
		type = 2;
		i = 0;
		checkForTrees = false;

	}
	
	/* method getType();
	 * simply returns the type of cell, which is set in the constructor.
	 */
	public int getType() {
		return type;
	}
	
	/* method getFaction();
	 * does nothing, but I wanted to make it part of the Cell superclass.
	 */
	public int getFaction() {
		return 3;
	}
	
	/* method getX();
	 * returns the x position of the tree.
	 */
	public double getX() {
		return this.xloc;
	}
	
	/* method getY();
	 * returns the y position of the tree.
	 */
	public double getY() {
		return this.yloc;
	}
	
	/* method updateWood();
	 * updates the amount of wood the tree has. Builder should call this when
	 * taking wood from the tree.
	 */
	 public void updateWood() {
		woodAmount--;
	}
	
	/* method updateState();
	 * Also kills the tree if it has no wood.
	 */
	public void updateState(Landscape scape) {
		i++;
		for ( Cell cell : scape.getWithinRadius(5, xloc, yloc)) {
			if (cell.getType() == 2) {
				checkForTrees = true;
			}
		}
		Random gen = new Random();
		if (this.woodAmount >=6 && checkForTrees == true && gen.nextInt(100) == 1) {
			scape.addAgent( new Tree( gen.nextDouble()*scape.getWidth(), 
										gen.nextDouble()*scape.getHeight()));
		//I wanted the trees to not clump, like the builders.
		}
		if ( this.woodAmount == 0 ) {
			scape.removeAgent(this);
		}
		if ( i == 10 ) {
			this.woodAmount++;
			i = 0;
		}
	}
	
	/* method draw();
	 * draws a cell with a given color(green).
	 */
	public void draw(Graphics g, int x0, int y0, int scale) {
		int x = x0 + (int)(this.getX() * scale);
		int y = y0 + (int)(this.getY() * scale);
 
 	/*
	 *code copied from : http://docs.oracle.com/javase/tutorial/2d/images/loadimage.html
	 * it imports an image which I got from a google search for warcraft 3 pine tree.
	 */
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("treepicture.jpg"));
		} 
		catch (IOException e) {
		}
 
		g.setColor(new Color(0.0f, 1.0f, 0.0f));
		// g.fillRect(x, y, scale, scale);
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