/**
 * @author eldad and vlad
 * this class represent a card that have a number and a suit 
 * it can get the num and the suit and compare two cards 
 */

public class Card {
	private int num;
	private int suit;
	
	// Constructor 
	public Card(int num, int suit) {
		
		this.num = num;
		this.suit = suit;
	}
	
	//getter
	public int getNum() {
		return this.num;
	}
	//getter
	public int getSuit() {
		return this.suit;
	}

	// printing format for a card object 
	public String toString() {
		String str = "" + this.getNum();
		//the switch is to determine the char of the type
		switch (this.getSuit()) {
		case 0:
			str += "C";
			break;
		case 1:
			str += "D";
			break;
		case 2:
			str += "H";
			break;
		case 3:
			str += "S";
			break;
		}
		return str;
	}

	public int compareTo(Card other) {
		//the compare method works as follows:
		// # first the value of the card determing the result 
		// # then the suit 
		// returns positive if higher , zero if equal and negative if smaller 
		int numDif = this.getNum() - other.getNum();
		return numDif == 0 ? this.getSuit() - other.getSuit() : numDif;
	}

}