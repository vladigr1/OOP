
public class Deck {
	
	private Card[] cards;
	
	public Deck(int num) {
		cards = new Card[num * 4];
		for (int i = 0; i < num; i++) {
			cards[4*i]= new Card(i,0);
			cards[4*i+1]= new Card(i,1);
			cards[4*i+2]= new Card(i,2);
			cards[4*i+3]= new Card(i,3);
		}
	}
	
	public Deck(Deck from, int num) {
		int curNum = num;
		if(from.cards.length < num) curNum = from.cards.length;
		cards = new Card[curNum];
		for (int i = 0; i < curNum; i++) {
			cards[i] = from.cards[from.cards.length - 1 - i];
		}
		int newSizeFrome = from.cards.length - curNum;
		Card [] newFrom = new Card[newSizeFrome];
		for (int i = 0; i < newFrom.length; i++) {
			newFrom[i] = from.cards[i];
		}
		from.cards = newFrom;
	}
	
	public Deck(Deck first, Deck second) {
		cards = new Card[first.cards.length + second.cards.length];		//in case the decks length is deiffrent
		int minIndex = first.cards.length < second.cards.length ? first.cards.length : second.cards.length;
		Card[] maxCards = first.cards.length > second.cards.length ? first.cards : second.cards;
		int i = 0,indexAfterMin;
		for (; i < minIndex; i++) {
			cards[2*i] = first.cards[first.cards.length - 1 - i];
			cards[2*i+1] = second.cards[second.cards.length - 1- i];
		}
		indexAfterMin = 2*i;
		for (; i < maxCards.length; i++) {
			cards[indexAfterMin] = maxCards[maxCards.length -1 -i];
			indexAfterMin++;
		}
		first.cards = new Card[0];
		second.cards = new Card[0];
	}
	
	public int getNumCards() {
		return cards.length;
	}
	
	public Card takeOne() {
		Card lastCard = cards[cards.length-1];
		for (int i = 0; i < cards.length-1; i++) {				// move all card index by one 
			cards[i+1] = cards[i]; 
		}
		cards[0]=lastCard;
		return lastCard;
	}
	
	public String toString() {
		String cur = "[";
		for(Card card: cards) {
			cur += card;
			if(card != cards[cards.length-1]) cur += ", ";		//adding to everyone comma except last 
		}
		cur += "]";
		return cur;
	}
	
	public void sort() {										//insert sort
		for (int i = 1; i < cards.length; i++) {
			Card temp = cards[i];
			int j = i-1;
			while( j > -1 && temp.compareTo(cards[j]) == -1) {
			cards[j+1] = cards[j];
			--j;
			}
			cards[j+1] = temp;
		}
	}		
}
