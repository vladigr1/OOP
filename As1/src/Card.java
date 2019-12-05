
public class Card {

	private int num, suit;
	public Card(int num, int suit) {
		this.num = num;
		this.suit = suit;
	}
	
	public int getNum() {
		return this.num;
	}
	
	public int getSuit() {
		return this.suit;
	}
	
	public String toString() {
		String ret= String.valueOf(num);		//adding a number and suit to ret
		switch(suit) {
		case 0:
			ret += "C";
			break;
		case 1:
			ret += "D";
			break;
		case 2:
			ret += "H";
			break;
		case 3:
			ret += "S";
			break;
		}
		
		return ret;
	}
	
	public int compareTo(Card other) {
		if (this.num > other.num )
			return 1;
		if (this.num < other.num )
			return -1;
		if(this.suit > other.suit)
			return 1;
		if(this.suit < other.suit)
			return -1;
		return 0;
	}
}

