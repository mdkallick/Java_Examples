 /* Written by Mathias Dyssegaard Kallick
 * 10-18-2014
 * Defines a class Builder, which extends cell and represents the builders in my
 * simulation. It takes the landscape and finds the nearest tree, and then directs
 * the builder to it. Once the builder gets wood, it moves randomly for 8 turns, 
 * after which a obstacle is spawned within a radius of 2 of the builder.
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
 
 public class Builder extends Cell {
	
	private int life;
	private boolean hasWood;
	private int counter;
	private int type;
	private boolean check;
	private Tree currentTree;
	private int faction;
	
	/* Constructor Builder();
	 * sets the builders life to 6, tells him he doesn't have wood,
	 * and sets his location.
	 */
	public Builder(double x0, double y0, int fact) {
		super(x0, y0);
		xpos = x0;
		ypos = y0;
		life = 10;
		hasWood = false;
		counter = 0;
		type = 1;
		check = false;
		currentTree = new Tree(1000000, 1000000);
		faction = fact;
		
	}
	
	
	/* method move();
	 * moves the builder a random distance in a random direction.
	 * the random distance is between -5 and 5 in both the x and y directions.
	 * written because I wanted to make moving a cell be an easier piece of code.
	 * Also, doesn't move if there is a barrier in the way.
	 * However, it moves in the x and y directions separately, so it can move in just
	 * one direction.
	 */
	public void move(Landscape scape) {
		Random rand = new Random();
		double randdoubx = (rand.nextDouble()-.5) * 10;
		double randdouby = (rand.nextDouble()-.5) * 10;
		if (scape.isObstacle(this.xpos, this.ypos, this.xpos + randdoubx,
									this.ypos, this.faction) == false) {
			this.xpos = xpos + randdoubx;
		}
		else { /* do not move in the x direction that turn */ }
		if (scape.isObstacle(this.xpos, this.ypos, this.xpos,
								this.ypos + randdouby, this.faction) == false) {
			this.ypos = ypos + randdouby;
		}
		else { /* do not move in the y direction that turn */ }
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
	 * dictates the behaviour of the builder based on whether or not he has wood,
	 * and his surroundings.
	 */
	public void updateState(Landscape scape) {
		Random rand = new Random();
		double randdoubx = 0.0;
		double randdouby = 0.0;
		if (this.life > 7 && this.hasWood == true && rand.nextInt(100) == 1) {
			scape.addAgent( new Builder( rand.nextDouble()*scape.getWidth(), 
										rand.nextDouble()*scape.getHeight(), faction));
			//I decided to spawn the new builder at a random spot because I don't want clumps of builders.
		}
		if (this.life  == 0){
			scape.removeAgent(this);
			return;
		}
		if (hasWood == true) {
			counter++;
			if (counter == 8) {
				if (faction == 1) {
					scape.addAgent (  new Barrier( 
						((rand.nextDouble()-.5) * 2) + this.xpos, 
						((rand.nextDouble()-.5) * 2) + this.ypos, 1));
				}
				else if (faction == 2) {
					scape.addAgent (  new Barrier( 
						((rand.nextDouble()-.5) * 2) + this.xpos, 
						((rand.nextDouble()-.5) * 2) + this.ypos, 2));
				}
				counter = 0;
				check = false;
			}
			else {
				this.move(scape);
			}
		}
		else {
			life--;
			int i = 0;
			
			while (check == false) {
				i++;
				ArrayList<Cell> templist = scape.getWithinRadius(i, xpos, ypos);

				for (int j = 0; j < templist.size(); j++) {
					if (templist.get(j).getType() == 2) {
						currentTree = ((Tree)templist.get(j));
						check = true;
						j = templist.size();
					}
				}
			}
			
			
			if (currentTree.getX() == this.xpos && currentTree.getY() == this.ypos) {
				hasWood = true;
				currentTree.updateWood();
			}
			if (currentTree.getX() < 1000000.0 && currentTree.getY() < 1000000.0) { 
				if (scape.isObstacle(xpos, ypos, this.xpos + 5, ypos, this.faction) == false
					&& this.xpos != currentTree.getX()) {
					if (this.xpos - currentTree.getX() >= 5) {
						this.xpos = this.xpos - 5;
					}
					else if (this.xpos - currentTree.getX() <= -5) {
						this.xpos = this.xpos + 5;
					}
					else {
						this.xpos = currentTree.getX();
					}
				}
				else {
					/* do not move in the x direction that turn */					
				}
				if (scape.isObstacle(xpos, ypos, xpos, this.ypos + 5, this.faction) == false) {
					if (this.ypos - currentTree.getY() >= 5) {
						this.ypos = this.ypos - 5;
					}
					else if (this.ypos - currentTree.getY() <= -5) {
						this.ypos = this.ypos + 5;
					}
					else {
						this.ypos = currentTree.getY();
					}
				}
				else { 
					/* do not move in the y direction that turn */ 
				}
			}			
		}
	}

	/* method draw();
	 * draws a builder with a given color (blue).
	 */
	public void draw(Graphics g, int x0, int y0, int scale) {
		int x = x0 + (int)(this.getX() * scale);
		int y = y0 + (int)(this.getY() * scale);
	/*
	 *code copied from : http://docs.oracle.com/javase/tutorial/2d/images/loadimage.html
	 * it imports an image which I got from a google search for warcraft 3 builder.
	 */
		BufferedImage img = null;
		if (faction == 1) {
			try {
				img = ImageIO.read(new File("builderpicture1.jpg"));
			} 
			catch (IOException e) {
			}
		}
		else if (faction == 2) {
			try {
				img = ImageIO.read(new File("builderpicture2.jpg"));
			} 
			catch (IOException e) {
			}
		}
		
 
		g.setColor(new Color(0.0f, 0.0f, 1.0f));
		g.drawImage(img, ((int)this.xpos) * scale, ((int)this.ypos) * scale, null);
		
		return;
	}
	/* method toString();
	 * returns a string of the values of the x and y location of the cell.
	 * Mainly for testing.
	 */
	public String toString() {
		String str = String.valueOf(xpos) + ";" + String.valueOf(ypos);
		return str;
	}
	
	public static void main(String arg[]) {
	}
}