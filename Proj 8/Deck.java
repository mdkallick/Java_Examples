/* written by Mathias Dyssegaard Kallick
 * defines a class Deck which holds all the cards in a deck.
 * Decks are seperated based on player.
 * Uses a stack to store cards.
 */
 
import java.util.LinkedList; 
import java.util.Random;
 
public class Deck {

	private LinkedList<Card> deckLL;
	
	public Deck() {
		this.deckLL = new LinkedList<Card>();
	}
	//sets up the deck with 52 randomly generated cards, which belong to a given player.
	public void setupDeck(int playerNum) {
		Random rand = new Random();
		for(int i = 0; i < 52; i++) {
			this.deckLL.add(new Card(rand.nextInt(10), rand.nextInt(10),0,0,playerNum));
		}
	}
	//draws a card from the top of the deck.
	//the deck is ordered so that it is FIFO
	public Card drawCard() {
		return this.deckLL.removeFirst();
	}
	//adds a card to the bottom of the deck
	public void addCard(Card card) {
		this.deckLL.add(card);
	}
}