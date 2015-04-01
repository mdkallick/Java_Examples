/* written by Mathias Dyssegaard Kallick
 * defines a class Display which displays the game
 */
 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
 
public class Display extends JPanel implements ActionListener{

	private int width;
	private int height;
	private Landscape scape;
	private ArrayList<JButton> fieldlist;
	private ArrayList<JButton> handlist1;
	private ArrayList<JButton> handlist2; // the three lists hold all of the buttons in
	//the panel, while the landscape holds the cards and all the other information. they
	//have to work in conjunction.
	private JPanel player1;
	private JPanel player2; 
	private JPanel field; //these three panels define the three different parts of the
	//projected window, which is in itself a panel.
	private JPanel turnpanel; //holds the help and the end turn buttons
	private Card currentCard;
	private int turn; // need to add a turn counter to dictate who can move
	private int currentx;
	private int currenty;
	
	public Display(int widthPROXY, int heightPROXY) {
		super (new GridLayout(4, 1));
		this.scape = new Landscape(heightPROXY, widthPROXY);
		this.width = widthPROXY;
		this.height = heightPROXY;
		this.turn = 1;
		this.currentx = 0;
		this.currenty = 0;
		this.fieldlist = new ArrayList<JButton>();
		this.handlist1 = new ArrayList<JButton>();
		this.handlist2 = new ArrayList<JButton>();
		this.player1 = new JPanel(new GridLayout(1, 0)); // zero means that there can be
		this.player2 = new JPanel(new GridLayout(1, 0)); // any number of columns
		this.field = new JPanel(new GridLayout(this.width, this.height));
		this.turnpanel = new JPanel(new GridLayout(1,1));
		JButton endturn = new JButton("End Turn");
		endturn.addActionListener(this);
		endturn.setActionCommand("End Turn");
		JButton help = new JButton("help");
		help.addActionListener(this);
		help.setActionCommand("HELP");
		this.turnpanel.add(endturn);
		this.turnpanel.add(help);
		this.currentCard = new Card(0,0,0,0,0);
		/* this loop sets up the field, which comes from the arraylist
		 * also adds all of the buttons to field list, so I can call them later.
		 */
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				JButton temp = new JButton("empty");
				temp.addActionListener(this);
				temp.setEnabled(false);
				temp.setActionCommand(Integer.toString(((i*this.width)+j)));
				this.fieldlist.add(temp);
				this.field.add(temp);
			}
		}
		/* this loop sets up the two players, such that they both have 5 cards and
		 * five buttons corresponding. This also adds to two different arraylists to
		 * be called from later. it also adds to the hands in the scape.
		 */
		for( int k = 0; k < 5; k++) {
			Card tempCard1 = this.scape.getDeck(1).drawCard();
			Card tempCard2 = this.scape.getDeck(2).drawCard();
			JButton temp1 = new JButton(tempCard1.toStringBrief());
			JButton temp2 = new JButton(tempCard2.toStringBrief());
			temp1.addActionListener(this);
			temp1.setEnabled(true);
			temp1.setActionCommand(Integer.toString(k) + "player1");
			// System.out.println(Integer.toString(k) + "player1");
			temp2.addActionListener(this);
			temp2.setEnabled(false);
			temp2.setActionCommand(Integer.toString(k) + "player2");
			this.player1.add(temp1);
			this.handlist1.add(temp1);
			this.scape.getHand(1).addCard(tempCard1);
			this.player2.add(temp2);
			this.handlist2.add(temp2);
			this.scape.getHand(2).addCard(tempCard2);
		}
		
		
		//this loop enables all of the cards on player one's side of the board
		for(int p = 0; p < 2; p++){
			for(int o = 0; o < this.width; o++) {
				this.fieldlist.get((p*this.width)+o).setEnabled(true);
			}
		}
		//adding all of the seperate panes to the mainpane.
		this.add(player1);
		this.add(field);
		this.add(player2);
		this.add(turnpanel);
	}
	/*this increments the turn counter, changes what buttons can be clicked based
	 * on the turns, and can add cards to the two hands at the beginning of each turn,
	 * but that seemed to encourage really bad bugs.
	 */
	public void incrementTurn() {
		this.turn++;
		this.disableAll();
		this.currentCard = null;
		if (this.turn % 2 != 0) {
			for(int i = 0; i < this.handlist2.size(); i++) {
				this.handlist2.get(i).setEnabled(false);
			}
			for(int j = 0; j < this.handlist1.size(); j++) {
				this.handlist1.get(j).setEnabled(true);
			}
			for(int p = 0; p < 2; p++){
				for(int o = 0; o < this.width; o++) {
					this.fieldlist.get((p*this.width)+o).setEnabled(true);
				}
			}
			// Card tempCard1 = this.scape.getDeck(1).drawCard();
			// JButton temp1 = new JButton(tempCard1.toStringBrief());
			// this.handlist1.add(temp1);
			// this.player1.add(temp1);
			// this.scape.getHand(1).addCard(tempCard1);
		}
		else {
			for(int k = 0; k < this.handlist1.size(); k++) {
				this.handlist1.get(k).setEnabled(false);
			}
			for(int l = 0; l < this.handlist2.size(); l++) {
				this.handlist2.get(l).setEnabled(true);
			}
			for(int n = (this.height - 2); n < this.height; n++){
				for(int m = 0; m < this.width; m++) {
					this.fieldlist.get((n*this.width)+m).setEnabled(true);
				}
			}
			// Card tempCard2 = this.scape.getDeck(2).drawCard();
			// JButton temp2 = new JButton(tempCard2.toStringBrief());
			// this.handlist1.add(temp2);
			// this.player2.add(temp2);
			// this.scape.getHand(2).addCard(tempCard2);
		}
	}
	/* this method quite simply disables all of the buttons in the field except for those
	 * which have cards in them.
	 */
	public void disableAll() {
		for(int i = 0; i < this.height; i++){
			for(int j = 0; j < this.width; j++) {
				if (this.scape.get(i,j) == null) {
					this.fieldlist.get((i*this.width)+j).setEnabled(false);
				}
				else if (this.scape.get(i,j).getPlayer() == 1 && this.turn % 2 != 0) {
					this.fieldlist.get((i*this.width)+j).setEnabled(true);
				}
				else if (this.scape.get(i,j).getPlayer() == 2 && this.turn % 2 == 0) {
					this.fieldlist.get((i*this.width)+j).setEnabled(true);
				}
				else {
					this.fieldlist.get((i*this.width)+j).setEnabled(false);
				}
				
			}
		}
	}
	
	public void enablePlayer(int player) {
		if (player == 1) {
			for(int p = 0; p < 2; p++){
				for(int o = 0; o < this.width; o++) {
					this.fieldlist.get((p*this.width)+o).setEnabled(true);
				}
			}
		}
		else {
			for(int n = (this.height - 2); n < this.height; n++){
				for(int m = 0; m < this.width; m++) {
					this.fieldlist.get((n*this.width)+m).setEnabled(true);
				}
			}
		}
	}
	
	/* this is the main method in my code. It defines everything that happens in the
	 * event of a button being pressed. It can check what the callsign of the button
	 * pressed was, and react to that.
	 */
	public void actionPerformed(ActionEvent e) {
		/* opens a new windown with all of the text below 
		 * when the help button is pressed.
		 */
		if ("HELP".equals(e.getActionCommand())) {
			JOptionPane.showMessageDialog(this,
				"This game is very simple, at the moment. \n"
				+ "Each card has an attack and a health variable\n"
				+ "These variables can be seen on the cards in the format: \n"
				+ "attack/health\n"
				+ "You are allowed to place one card, from your randomly \n"
				+ "selected hand of five cards, and place it.\n"
				+ "then, the opposing player does the same.\n"
				+ "on the next turn, you may move your card as much as you like.\n"
				+ "If you move onto the opponent's card, that card takes damage\n"
				+ "to it's health total equal to your attack.\n"
				+ "PRESS END TURN TO ADVANCE TURNS");
		}
	
		// System.out.println(e.getActionCommand());
		/* if one of the cards in either player's hand is pressed, this loop does what
		 * needs to be done. In this case it sets the current card such that it can
		 * later be placed somewhere on the field. also removes it from the hand and
		 * the player panel so that it is no longer displayed.
		 */ 
		for (int k = 0; k < this.handlist1.size(); k++) {
			if((Integer.toString(k) + "player1").equals(e.getActionCommand())) {
				this.currentCard = this.scape.getCard(1, k);
				this.scape.getHand(1).removeCard(k);
				this.handlist1.remove(k);
				this.player1.remove(k);
				this.disableAll();
				this.enablePlayer(1);
				System.out.println("Current Card Set");
			}
		// }
		// for (int l = 0; l < this.handlist2.size(); l++) {
			if((Integer.toString(k) + "player2").equals(e.getActionCommand())) {
				this.currentCard = this.scape.getCard(2, k);
				this.scape.getHand(2).removeCard(k);
				this.handlist2.remove(k);
				this.player2.remove(k);
				this.disableAll();
				this.enablePlayer(2);
				System.out.println("Current Card Set");
			}
			// if ((Integer.toString(k) + "player2").equals(e.getActionCommand()) || 
				// (Integer.toString(k) + "player1").equals(e.getActionCommand())) {
				// for (int i = 0; i < this.width; i++) {
					// for (int j = 0; j < this.height; j++) {
						// this.fieldlist.get((i*this.width)+j).setEnabled(true);
					// }
				// }
			// }
		}
		
		/* this for loop dictates everything that happens when something in the field 
		 * is pressed. it checks whether the spot is empty, and then it dictates what is
		 * to be done in the situation where there is an encounter between two cards.
		 * also disables and enables buttons.
		 */
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				if (Integer.toString(((i*width)+j)).equals(e.getActionCommand())) {
					if (this.currentCard == null) {
						this.currentCard = this.scape.get(i,j);
						this.currentx = i;
						this.currenty = j;
						this.scape.changeCard(null, i, j);
						this.disableAll();
						
						// for(int q = 0; q < this.width; q++) {
							// for(int r = 0; r < this.height; r++) {
								// this.fieldlist.get((q*this.width) + r).setEnabled(false);
							// }
						// }
						this.fieldlist.get((i*this.width) + j).setEnabled(false);
						this.fieldlist.get((i*this.width) + j + 1).setEnabled(true);
						this.fieldlist.get((i*this.width) +j - 1).setEnabled(true);
						this.fieldlist.get(((i + 1)*this.width) +j).setEnabled(true);
						this.fieldlist.get(((i - 1)*this.width) +j).setEnabled(true);
					}
					else if (this.currentCard != null && this.scape.get(i,j) != null) {
						if (this.currentCard.getAttack() >=
								this.scape.get(i,j).getHealth()) {
							this.scape.changeCard(this.currentCard, i, j);
							this.disableAll();
						}
						else {
							this.scape.get(i,j).changeHealth(
								this.scape.get(i,j).getHealth() - 
									this.currentCard.getAttack());
							this.scape.changeCard(this.currentCard, this.currentx,
																		this.currenty);
							this.disableAll();
						}
					}
					else {
						this.scape.changeCard(this.currentCard, i, j);
						this.currentCard = null;
						this.disableAll();
					}
				}
			}
		}
		//increments the turn when the end turn button is pressed.
		if ("End Turn".equals(e.getActionCommand())) {
			this.incrementTurn();
		}
		
		/* this loop changes the color and the text of the buttons in the field when the
		 * field changes (after each button press).
		 */
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				if (this.scape.get(i,j) != null) {
					this.fieldlist.get(((i*width) + j)).setText(
									this.scape.get(i,j).toStringBrief());
					if (this.scape.get(i,j).getPlayer() == 1) {
						this.fieldlist.get(((i*width) + j)).setBackground(Color.RED);
					}
					else if (this.scape.get(i,j).getPlayer() == 2) {
						this.fieldlist.get(((i*width) + j)).setBackground(Color.BLUE);
					}
				}
				else {
					this.fieldlist.get(((i*width) + j)).setText("empty");
					this.fieldlist.get(((i*width) + j)).setBackground(Color.WHITE);
				}
			}
		}
	}
	// for now, just assumes the game is not over.
	public boolean winConditions() {
		return false;
	}
	/*sets the main panel visible, sets it so it closes when you click the x, and 
	 *sets its boundaries to the resolution of my screen, so that it fills up my
	 *screen when it is opened.
	 *also happens to name the window.
	 */
	public void showDisplay() {
		JFrame frame = new JFrame("GameDisplay");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new Display(5,5));
		frame.pack();
		frame.setBounds(0,0/*(1920/2 - 250),(1080/2 - 250)*/, 1920, 1040);
		frame.setVisible(true);
	}
	
	public static void main(String args[]) {
		Display testdisp = new Display(5,5);
		testdisp.showDisplay();
	}
}