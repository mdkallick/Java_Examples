/* Written by Mathias Dyssegaard Kallick
 * 9-22-14
 * Defines a class Landscape which should hold all of the information
 * necessary for the 2d-array which will hold a game of life.
 * Written for CS231 Project 2
 */

import java.util.*;

public class Landscape {

	int agents;
	double height;
	double width;
	private int numrows;
	private int numcols;
	LinkedList<Cell> llist;
	
	
    public Landscape(int rows, int cols, int numagents, double heightin, double widthin) {
		this.llist = new LinkedList<Cell>();
		this.agents = numagents;
		this.numrows = rows;
		this.numcols = cols;
		this.height = heightin;
		this.width = widthin;

	}
	
	
	public Landscape(double heightin, double widthin) {
		llist = new LinkedList<Cell>();
		this.height = heightin;
		this.width = widthin;
		
	}
	
	/* method initialize();
	 * initializes the simulation with the given values.
	 */
	public void initialize() {
		Random gen = new Random();
		// generates builders
		for(int i=0;i<25;i++) {
			this.addAgent( new Builder( gen.nextDouble()*this.getWidth(), 
										gen.nextDouble()*this.getHeight(), 1));
		}
		for(int i=0;i<25;i++) {
			this.addAgent( new Builder( gen.nextDouble()*this.getWidth(), 
										gen.nextDouble()*this.getHeight(), 2));
		}		
		// generates trees
		for(int i=0;i<50;i++) {
			this.addAgent( new Tree( gen.nextDouble()*this.getWidth(), 
										gen.nextDouble()*this.getHeight()));
		}
		// (optional) generates barriers if desired.
		for(int i=0;i<0;i++) {
			this.addAgent( new Barrier( gen.nextDouble()*this.getWidth(), 
										gen.nextDouble()*this.getHeight(), 1));
		}
	}
	
    /* method addAgent();
     * simply adds a cell to the LinkedList that holds all of the cells.
     */
    public void addAgent(Cell a) {
		this.llist.add(a);
    }
	
	/* method removeAgent(Cell a);
	 * removes an Agent from the linkedlist llist.
	 */
	public void removeAgent(Cell a) {
		this.llist.remove(a);
	}
    /* method getAgents();
     * returns an ArrayList which contains all of the cells in the
     * LinkedList which holds cells. Also copies over those cells.
     */
    public ArrayList<Cell> getAgents() {
		ArrayList<Cell> alist = new ArrayList<Cell>();
		for(int i = 0; i < llist.size(); i++) {
			alist.add(llist.get(i));
		}
		return alist;
    }
    /* method toString();
     * it takes the information from the cells and creates a string
     * which is able to print out a 2-d configuration of pixels
     * which reflects the positions of the cells in the Landscape.
     * Notably, it takes a 1-d LinkedList and makes it 2-d by separating it
     * into columns and rows by adding a nextline every time it has gone through
     * a number of spaces equal to the number of columns.
     */
	 
    public String toString() {
		String str = "";
		ArrayList<String> stringlist = new ArrayList<String>();
		for(int p = 1; p <= (this.numcols * this.numrows); p++) {
			stringlist.add("-");
		}
		//Now that I have set up the list, I will change it so it reflects the cell position
		// System.out.println(this.getAgents().toString());
		for(int i = 0; i < this.getAgents().size(); i++) {
			for(int j = 0; j < this.numcols; j++) {
				for (int k = 0; k < this.numrows; k++) {
					if (this.getAgents().get(i).getCol() == j && this.getAgents().get(i).getRow() == k) {
						// System.out.println("problem in recognizing cell position");
						stringlist.set((j * numrows) + k, "#");
					}
				else {}
				}
			}
		}
		for (int e = 1; e <= stringlist.size(); e++) {
			if (e % numcols !=0) {
				str+=stringlist.get(e-1);
			}
			else if (e == 0) {
				str+=stringlist.get(e-1);
			}
			else if (e % numcols == 0) {
				str+=stringlist.get(e-1);
				str+="\n";
			}
		}
		return str;
    }

    /* method advance();
     * simply loops through a scrambled LinkedList of cells, and 
     * works through that list, calling updatestate to all the cells.
     */
    public void advance() {
		
		for(int i = 0; i < this.llist.size(); i++) {
			this.llist.get(i).updateState(this);
		}

    }
    /* method generate();
     * creates the Landscape, just loops through and adds all of the cells.
     * Is also able to generate the landscape differently given celltypes.
     */
    public void generate() {
	}
	/* method getRows();
     * returns the number of rows in the grid
     */
    public int getRows() {
		int rows0 = (int)Math.round(height);
		return rows0;
    }
    
	/* method getCols();
     * returns the number of columns in the grid
     */
    public int getCols() {
		int cols0 = (int)Math.round(width);
		return cols0;
    }
	/* method getHeight();
	 * returns the position of the cell in terms of height
	 */
	public double getHeight() {
		return height;
	}
	/* method getWidth();
	 * returns the position of the cell in terms of width
	 */
	public double getWidth() {
		return width;
	}
	
	public ArrayList<Cell> getWithinRadius( int radius, double x0, double y0 ) {
		ArrayList<Cell> alist = new ArrayList<Cell>();
		for (int i = 0; i < llist.size(); i++) {
			if ((x0-llist.get(i).getX())*(x0-llist.get(i).getX()) + 
				(y0-llist.get(i).getY())*(y0-llist.get(i).getY()) < radius*radius) {
				alist.add(llist.get(i));
			}
		}
		return alist;
	}
	
	/* method getBarriers();
	 * gets all of the barriers in the agents list.
	 */
	public int getBarriers() {
		int numBarriers = 0;
		for (int i = 0; i < llist.size(); i++) {
			if (llist.get(i).getType() == 3) {
				numBarriers++;
			}
		}
		return numBarriers;
	}
	
	/* method isObstacle() ;
	 * checks whether a range in the landscape is occupied by an obstacle.
	 */
	public boolean isObstacle(double xorigin, double yorigin, double xfinal,
												double yfinal, int faction) {
		for (int i = 0; i < llist.size(); i++) {
			if (llist.get(i).getX() <= xfinal && llist.get(i).getX() > xorigin
			&& llist.get(i).getY() <= yfinal && llist.get(i).getY() > yorigin 
			&& llist.get(i).getType() == 3 && llist.get(i).getFaction() == faction) {
				return true;
			}
		}
		return false;
	}

    public static void main(String argv[]) {

    }
}

