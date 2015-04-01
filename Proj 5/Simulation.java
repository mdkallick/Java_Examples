/* Written by Mathias Dyssegaard Kallick
 * 10-6-2014
 * Defines a class Simulation, which runs through LandscapeDisplay.class and saves
 * the printed out simulation to a series of images.
 */
 
 import java.util.Random;
 import java.util.Scanner;
 
 public class Simulation {
 
	LandscapeDisplay ldisp;
	Landscape scape;
	private int category;
	private Scanner scanner;
 
	/* Simulation Constructor;
	 * generates all of the different types of cells. Also determines how many of each
	 * type of cell there will be, as well as how big the landscape is.
	 */
	public Simulation() {
	    this.scape = new Landscape(100, 150);
		this.scape.initialize();
		ldisp = new LandscapeDisplay(scape, 4);
	}
	
	/* method getScape();
	 * returns landscape: for testing purposes.
	 */
	public Landscape getScape() {
		return this.scape;
	}
	
	/* method saveImages();
	 * This method loops through all the steps of the simulation and 
	 * saves the images using the saveImage method of LandscapeDisplay.
	 */
	public void saveImages() {
		for(int i = 0; i < 50; i++) {
			this.scape.advance();
			ldisp.update();
			ldisp.saveImage("image" + Integer.toString(i) + ".png");
		}
		System.out.println("done");
	}

	
	public static void main(String args[]) {
		Simulation newsim = new Simulation();
		newsim.saveImages();
		int builders1 = 0;
		int builders2 = 0;
		int trees = 0;
		int barriers1 = 0;
		int barriers2 = 0;
 		for ( Cell cell : newsim.getScape().getAgents()) {
			if (cell.getType() == 1 && cell.getFaction() == 1) {
				builders1++;
			}
			else if (cell.getType() == 1 && cell.getFaction() == 2) {
				builders2++;
			}
			else if (cell.getType() == 2) {
				trees++;
			}
			else if (cell.getType() == 3 && cell.getFaction() == 1) {
				barriers1++;
			}
			else if (cell.getType() == 3 && cell.getFaction() == 2) {
				barriers2++;
			}
		}
		System.out.println("there are : " + builders1 + " of faction 1 builders");
		System.out.println("there are : " + builders2 + " of faction 2 builders");
		System.out.println("there are : " + trees + " trees");
		System.out.println("there are : " + barriers1 + " of faction 1 barriers");
		System.out.println("there are : " + barriers2 + " of faction 2 barriers");
		if (barriers1 > barriers2) {
			System.out.println("red Wins!");
		}
		else if (barriers2 > barriers1) {
			System.out.println("blue Wins!");
		}
	}
}