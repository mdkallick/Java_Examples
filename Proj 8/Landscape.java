/* written by Mathias Dyssegaard Kallick
 * defines a class Landscape which holds both of the hands in the game, the 2-d array
 * which is the landscape in which the cards are played, and the other relevant
 * information for this game. Also has a toString method for testing.
 */
 
public class Landscape {

	private Card[][] scapearray;
	private Hand player1;
	private Hand player2;
	private int width;
	private int height;
	private Deck deck1, deck2;
	
	public Landscape(int widthPROXY, int heightPROXY) {
		this.width = widthPROXY;
		this.height = heightPROXY;
		this.scapearray = new Card[width][height];
		this.player1 = new Hand();
		this.player2 = new Hand();
		this.deck1 = new Deck();
		this.deck2 = new Deck();
		this.deck1.setupDeck(1);
		this.deck2.setupDeck(2);
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				this.scapearray[i][j] = null;
			}
		}
	}
	//returns the deck of of the player i
	public Deck getDeck(int i) {
		if(i == 1) {return this.deck1;}
		else {return this.deck2;}
	}
	//returns the hand of the specified player.
	public Hand getHand(int player) {
		if (player == 1){return this.player1;}
		if (player == 2){return this.player2;}
		else {return null;}
	}
	//adds a given card to the hand of the specified player.
	//also checks if the player number entered is a player that exists
	//and returns an error message.
	public void addCard(int player, Card card) {
		if (player == 1) {
			this.player1.addCard(card);
		}
		else if (player == 2) {
			this.player2.addCard(card);
		}
		else {
			System.out.println("Wrong player # entered(add)");
		}
	}
	//changes a specified spot in the landscape to a given card.
	public void changeCard(Card card, int x, int y) {
		this.scapearray[x][y] = card;
	}
	//gets a card from the hand of a player.
	//checks the player number and returns an error message.
	public Card getCard(int player, int index) {
		if (player == 1) {
			return this.player1.getCard(index);
		}
		else if (player == 2) {
			return this.player2.getCard(index);
		}
		else {
			System.out.println("Wrong player # entered(get)");
		}
		return null;
	}
	//returns the card at position x,y in the landscape.
	public Card get(int x, int y) {
		return scapearray[x][y];
	}
	//adds a card to the array. mostly for testing.
	public void addToArray(Card card) {
		scapearray[card.getXpos()][card.getYpos()] = card;
	}
	//returns a string representation of the landscape for testing
	public String toString() {
		String str = "";
		for(int i = 0; i < this.width; i++) {
			str+="\n";
			for(int j = 0; j < this.height; j++) {
				if (scapearray[i][j] != null) {
					str+=" " + scapearray[i][j].toStringBrief() + " ";
				}
				else {
					str+=" ----------- ";
				}
			}
		}
		return str;
	}
	
	public static void main(String args[]) {
		Landscape scape = new Landscape(5,5);
		scape.addToArray(new Card(10,10,3,3,0));
		scape.addToArray(new Card(10,10,2,4,0));
		scape.addToArray(new Card(10,10,1,3,0));
		scape.addToArray(new Card(10,10,0,0,0));
		scape.addToArray(new Card(10,10,4,4,0));
		System.out.println(scape.toString());
	}
}