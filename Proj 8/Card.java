/* written by Mathias Dyssegaard Kallick
 * defines a class Card which holds all the information that a card needs to hold:
 * damage, health, position, and which player controls it.
 */
 
public class Card {

	private int attack;
	private int health;
	private int xPos;
	private int yPos;
	private int player;

	public Card(int atk, int hp, int xPosition, int yPosition, int playerNum) {
		this.attack = atk;
		this.health = hp;
		this.xPos = xPosition;
		this.yPos = yPosition;
		this.player = playerNum;
	}
	//returns the player controlling the card.
	public int getPlayer() {
		return this.player;
	}
	//returns the attack.
	public int getAttack() {
		return this.attack;
	}
	//changes the attack given a new attack value.
	public void changeAttack(int newattack) {
		this.attack = newattack;
	}
	//returns the health of the card.
	public int getHealth() {
		return this.health;
	}
	//changes the health of the card given a new health value.
	public void changeHealth(int newhealth) {
		this.health = newhealth;
	}
	//returns the x position of the card in the landscape.
	public int getXpos() {
		return this.xPos;
	}
	//changes the x position of the card given a new x position.
	public void updateX(int newX) {
		this.xPos = newX;
	}
	//returns the y position of the card in the landscape.
	public int getYpos() {
		return this.yPos;
	}
	//changes the y position of the card given a new y position.
	public void updateY(int newY) {
		this.yPos = newY;
	}
	//returns a lengthy string of the values relevant to this card:mostly for testing.
	public String toString() {
		String str = "health: "; str+=this.health; str+=" attack: ";str+=this.attack;
		str+=" X: "; str+=this.xPos; str+=" Y: "; str+=this.yPos;
		return str;
	}
	//returns a short string for displaying the attack and health of the card.
	public String toStringBrief() {
		String str = this.attack + "/" + this.health; // + " : " + this.xPos;
		//str+= "/" + this.yPos;
		return str;
	}
	
	public static void main(String args[]) {
	}
}