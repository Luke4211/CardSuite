package CardSuite;

import javax.swing.ImageIcon;

public class Card {
	private int value;
	private String suit;
	private ImageIcon img;
	
	/**
	 * A playing card.
	 * 
	 * @param int val - the value of the card
	 * @param String suit - Diamonds, Clubs, Spades, Hearts
	 * @param ImageIcon img - image file for the card
	 */
	public Card(int val, String suit, ImageIcon img) {
		this.value = val;
		this.suit = suit;
		this.img = img;		
	}
	
	/**
	 * Gives info on the card.
	 * @return String. The value and suit of the card.
	 */
	public String getInfo() {
		String name;
		if(this.value == 1) {
			name = "Ace";
		}
		else if(this.value == 11) {
			name = "Jack";
		}
		else if(this.value == 12) {
			name = "Queen";
		}
		else if(this.value == 13) {
			name = "King";
		}
		else {
			name = value + "";
		}
		
		return (name + " of " + suit);
		
	}
	
	@Override
	public String toString() {
		String name;
		if(this.value == 1) {
			name = "Ace";
		}
		else if(this.value == 11) {
			name = "Jack";
		}
		else if(this.value == 12) {
			name = "Queen";
		}
		else if(this.value == 13) {
			name = "King";
		}
		else {
			name = value + "";
		}
		
		return (name + " of " + suit);
	}
	
	/**
	 * Getter for the card's value
	 * @return value as an int
	 */
	public int getVal() {
		return this.value;
	}
	
	/**
	 * Face cards in BlackJack are valued at 10
	 * @return value as an int, adjusted for BlackJack
	 */
	public int getBlackJackVal() {
		if(this.value == 1) {
			return 1;
		} else if(this.value < 10) {
			return this.value;
		} else {
			return 10;
		}
	}
	
	public void printCard() {
		String name;
		if(this.value == 1) {
			name = "Ace";
		}
		else if(this.value == 11) {
			name = "Jack";
		}
		else if(this.value == 12) {
			name = "Queen";
		}
		else if(this.value == 13) {
			name = "King";
		}
		else {
			name = value + "";
		}
		
		System.out.println(name + " of " + suit);
	}
	
	public String printCardGUI() {
		String name;
		if(this.value == 1) {
			name = "Ace";
		}
		else if(this.value == 11) {
			name = "Jack";
		}
		else if(this.value == 12) {
			name = "Queen";
		}
		else if(this.value == 13) {
			name = "King";
		}
		else {
			name = value + "";
		}
		
		return(name + " of " + suit);
	}
	
	public ImageIcon getImg() {
		return this.img;
	}

}
