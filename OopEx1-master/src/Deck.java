
public class Deck {

	/**
	 * this class represent a deck of cards with multiple ways to combine/ create a
	 * deck of cards , the user can : # take a card # get the number of cards in the
	 * deck # print the cards in the deck # and sort the deck by number then suit
	 * 
	 */

	private Card[] cards; // array of cards
	private int size; // the size of the deck
	private static final int CARD_TYPES = 4; // card types

	public Deck(int num) {
		/**
		 * build a deck by combining each card from number 0-number-1 with every suit
		 */
		this.size = num * CARD_TYPES;
		cards = new Card[this.size];
		for (int i = 0; i < num; i++)
			for (int j = 0; j < CARD_TYPES; j++)
				cards[CARD_TYPES * i + j] = new Card(i, j);
	}

	public Deck(Deck from, int num) {
		/**
		 * take the num amount of cards from the deck from and puts them in out current
		 * deck
		 */
		this.size = from.getNumCards() > num ? num : from.getNumCards();
		this.cards = new Card[this.size];
		for (int i = 0; i < num; i++)
			cards[i] = from.takeOne();
	}

	public Deck(Deck first, Deck second) {
		/**
		 * takes the cards from two decks each time from each deck till they are both
		 * empty
		 */
		boolean flag = true;
		int i;
		this.size = first.getNumCards() + second.getNumCards();
		this.cards = new Card[this.size];
		// iterates the decks until one of them is empty
		for (i = 0; i < this.size && first.getNumCards() != 0 && second.getNumCards() != 0; i++) {
			this.cards[i] = i % 2 == 0 ? first.takeOne() : second.takeOne();
			flag = !flag;
		}
		// in the case that the decks are not of the same size
		// adds the rest to the deck
		while (first.getNumCards() != 0)
			this.cards[i++] = first.takeOne();
		while (second.getNumCards() != 0)
			this.cards[i++] = second.takeOne();
	}

	public int getNumCards() {
		// gets the number of cards
		return this.size;
	}

	public Card takeOne() {
		// take a card from the deck
		return cards[--size];
	}

	public String toString() {
		// the string fromat to print in the case we want to print
		// the deck
		String str = "[";
		if (this.size == 0) // in the case of empty deck
			return str + "]";
		for (int i = 0; i < this.getNumCards() - 1; i++)
			str += this.cards[i].toString() + ", ";
		return str + this.cards[this.size - 1].toString() + "]";
	}

	private void swap(int i, int j) {
		// swap two cards in the deck with indexes i and j
		Card tmp = this.cards[i];
		this.cards[i] = this.cards[j];
		this.cards[j] = tmp;
	}

	public void sort() {
		// sort the deck with bubble sort
		for (int i = 0; i < this.size; i++)
			for (int j = 0; j < this.size - i - 1; j++)
				if (this.cards[j].compareTo(this.cards[j + 1]) > 0)
					swap(j, j + 1);
	}
}
