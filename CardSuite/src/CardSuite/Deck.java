package CardSuite;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.*;

import javax.swing.ImageIcon;

/**
 * Deck Class: A simple class which provides all of the functionality of
 * 			   a normal deck of cards.
 * Written by: Lucas Gorski
 * Public methods:
 * Deck(boolean empty)
 * top()
 * addCard()
 * shuffle()
 * getCard()
 * getDeck()
 * addDeck()
 * size()
 * cut()
 * riffle()
 * printDeck()
 */
public class Deck {
	private ArrayList<Card> deck;
	
	/**card dimension x*/
	private int x=70;
	/**card dimension y*/
	private int y=110;
	
	/**
	 * Constructor which generates a full deck if empty is false, 
	 * if empty is true then the deck will be left empty.
	 */
	public Deck(boolean empty) {
		this.deck = new ArrayList<Card>(52);
		if(!empty) {
			this.genDeck();
		}
	}
	
	/**
	 * The top() method removes the top card from the deck
	 * and returns it
	 */
	public Card top() {
		return this.deck.remove(0);
	}
	
	/**
	 * The size() method returns the number of
	 * cards in the deck
	 */
	public int size() {
		return this.deck.size();
	}
	
	/**
	 * This method returns the card at a given index
	 * without removing it from the deck.
	 */
	public Card getCard(int index) {
		if(index < this.deck.size()) {
			return this.deck.get(index);
		}
		else
			return null;
	}
	
	/**
	 * The addCard method inserts a card to the top
	 * of a deck.
	 */
	public void addCard(Card card) {
		this.deck.add(0, card);
	}
	
	/**
	 * Returns the deck in the form of an
	 * arrayList
	 */
	public ArrayList<Card> getDeck() {
		return this.deck;
	}
	
	/**
	 * Adds another deck onto the current deck
	 */
	public void addDeck(Deck pdeck) {
		this.deck.addAll(pdeck.getDeck());
	}

	/**
	 * The shuffle() method shuffles the deck by first cutting it
	 * 1-9 times, and then riffling it 1-7 times.
	 */
	public void shuffle() {
		Random r = new Random();
		int numCuts = 1 + r.nextInt(10);
		int numRiffs = 1 + r.nextInt(8);
		cutDeck(numCuts);
		riffle(numRiffs);
	}
	
	/**
	 * The cutDeck() method cuts the deck by randomly determining a cut position,
	 * and then reversing the order of the two resulting decks
	 * 
	 * @param pNumCuts int - how many times to cut the deck 
	 */
	public void cutDeck(int pNumCuts) {
		Random r = new Random();
		int numCuts = pNumCuts;
		for(int i = 1; i < numCuts; i++ ) { 
			int cutIndex = r.nextInt(52);
			Card[] tempArry = new Card[52];
			this.deck.toArray(tempArry);
			Card[] firstHalf = java.util.Arrays.copyOfRange(tempArry, 0, cutIndex);
			Card[] secHalf = java.util.Arrays.copyOfRange(tempArry, cutIndex, 52);
			ArrayList<Card> finalArry = new ArrayList<Card>();
			finalArry.addAll(Arrays.asList(secHalf));
			finalArry.addAll(Arrays.asList(firstHalf));
			this.deck = finalArry;
		}
	}
	
	/**
	 * The riffle() method riffles the deck. First, it splits the deck into
	 * two fairly even piles, and then produces a new deck by alternately stacking
	 * cards from each pile. 
	 * 
	 * @param numRiffs int - how many times to riffle the deck 
	 */
	public void riffle(int numRiffs) {
		for(int m = 0; m<numRiffs; m++) {
			Random r = new Random();
			int cutTolerance = r.nextInt(9) - 4;
			int cutIndex = 26 + cutTolerance;
			Card[] tempArry = new Card[52];
			this.deck.toArray(tempArry);
			Card[] firstHalf = java.util.Arrays.copyOfRange(tempArry, 0, cutIndex);
			Card[] secHalf = java.util.Arrays.copyOfRange(tempArry, cutIndex, 52);
			ArrayList<Card> finalArry = new ArrayList<Card>();
			int f = 0;
			int s = 0;
			boolean maxedFirstDeck = false;
			boolean maxedSecDeck = false;
			for(int i = 0; i < 53; i++) {
				if(i%2 == 0 || maxedSecDeck) {
					if(f<firstHalf.length) {
						finalArry.add(firstHalf[f]);
						f++;
					} else {
						maxedFirstDeck = true;
					}
				} 
				if(i%2!=0 || maxedFirstDeck){
					if(s<secHalf.length) {
						finalArry.add(secHalf[s]);
						s++;
					} else {
						maxedSecDeck = true;
					}
				}
			}
			this.deck = finalArry;
		}
	}
	
	/**
	 * The printDeck() method prints the deck out in order.
	 */
	public void printDeck() {
		for(Card card:this.deck) {
			card.printCard();
		}
	}
	
	/**
	 * Returns the deck as a string.
	 * @return String
	 */
	public String printDeckGUI() {
		String n="";
		for(Card card:this.deck) {
			n += " " + card.printCardGUI();
		}
		return n;
	}
	
	
	/**
	 * resize an image to new x y dimensions
	 * @param x
	 * @param y
	 * @param imagePath
	 * @return
	 */
	public ImageIcon resizeImage(int x, int y, String imagePath) {
		ImageIcon imageIcon = new ImageIcon(Deck.class.getResource(imagePath)); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(x, y,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);  // transform it back
		return imageIcon;
	}
	
	
	/**
	 * The genDeck() method generates a deck of 52 cards,
	 * 13 of each suit.
	 */
	private void genDeck() {
		ImageIcon img=null;
		for(int i = 1; i<14; i++) {
			try {
				img = resizeImage(x,y,"/resources/images/" + i +" of "+ "Diamonds.jpg");
			}catch(Exception e) {
				System.out.println("error loading " + i + " of " + "Diamonds.jpeg");
			}
			Card tempCard = new Card(i, "Diamonds", resizeImage(x,y,"/resources/images/" + i +" of "+ "Diamonds.jpg"));
			this.deck.add(tempCard);
		}
		
		for(int i = 1; i<14; i++) {
			try {
				img = resizeImage(x,y,"/resources/images/" + i +" of "+ "Clubs.jpg");
			}catch(Exception e) {
				System.out.println("error loading " + i + " of " + "Clubs.jpeg");
			}
			Card tempCard = new Card(i, "Clubs", img);
			this.deck.add(tempCard);
		}
		
		for(int i = 1; i<14; i++) {
			try {
				img = resizeImage(x,y,"/resources/images/" + i +" of "+ "Hearts.jpg");
			}catch(Exception e) {
				System.out.println("error loading " + i + " of " + "Hearts.jpeg");
			}
			Card tempCard = new Card(i, "Hearts", img);
			this.deck.add(tempCard);
		}
		
		for(int i = 1; i<14; i++) {
			try {
				img = resizeImage(x,y,"/resources/images/" + i +" of "+ "Spades.jpg");
			}catch(Exception e) {
				System.out.println("error loading " + i + " of " + "Spades.jpeg");
			}
			Card tempCard = new Card(i, "Spades", img);
			this.deck.add(tempCard);
		}
		
	}
}
