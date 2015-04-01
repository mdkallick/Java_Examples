/* written by Mathias Dyssegaard Kallick
 * defines a class Hand which holds all of the cards in a player's hand
 * it does this in a dynamic arraylist which I implemented.
 */
 
import java.util.ArrayList;
 
public class Hand {

	private ArrayList<Card> cards;

	public Hand() {
		this.cards = new ArrayList<Card>();
	}
	//returns the number of cards in the player's hand.
	public int getNumCards() {
		return this.cards.size();
	}
	//returns the card at the given index in the hand.
	public Card getCard(int index) {
		return this.cards.get(index);
	}
	//adds a given card to the hand.
	public void addCard(Card card) {
		this.cards.add(card);
	}
	//removes a card at a given index. does not return it.
	public void removeCard(int index) {
		this.cards.remove(index);
	}
	//returns a string of the strings of all the cards in the hand. for testing.
	public String toString() {
		String str = "";
		for(int i = 0; i < cards.size();i++) {
			str+=this.cards.get(i).toString();
		}
		return str;
	}
	
	public static void main(String args[]) {
	}
}