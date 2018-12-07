package CardSuite;

import java.util.Scanner;

public class Player {
	Scanner scnr = new Scanner(System.in);
	private Deck deck1;
	private Deck deck2;
	private Card doubleCard1;
	private Card doubleCard2;
	private int playerNum;
	private int numDecks;
	private int bet;
	private int splitBet;
	private int insurance;
	private long money;
	private boolean deck1Bust;
	private boolean deck2Bust;
	private boolean standing1;
	private boolean standing2;
	private boolean isDoubled1;
	private boolean isDoubled2;
	private boolean isSplit;
	private boolean insured;
	/*
	 * Basic constructor for Player class.
	 */
	public Player(int num, long money) {
		this.deck1 = new Deck(true);
		this.deck2 = new Deck(true);
		this.numDecks = 1;
		this.playerNum = num;
		this.money = money;
		this.deck1Bust = false;
		this.deck2Bust = false;
		this.standing1 = false;
		this.standing2 = false;
		this.isDoubled1 = false;
		this.isDoubled2 = false;
		this.isSplit = false;
		this.insured = true;
		this.bet = 0;
		this.splitBet = 0;
		this.insurance = 0;
	}
	
	/**
	 * Player constructor for testing purposes
	 * 
	 * @param num
	 * @param money
	 * @param deck1 true for empty
	 * @param deck2 true for empty
	 * @param numDecks 
	 * @param deck1Bust
	 * @param deck2Bust
	 * @param standing1
	 * @param standing2
	 * @param isDoubled1
	 * @param isDoubled2
	 * @param isSplit
	 * @param insured
	 */
	public Player(int num, long money, boolean deck1, boolean deck2, int numDecks,
			boolean deck1Bust, boolean deck2Bust, boolean standing1, boolean standing2,
			boolean isDoubled1, boolean isDoubled2, boolean isSplit, boolean insured) {
	this.deck1 = new Deck(deck1);
	this.deck2 = new Deck(deck2);
	this.numDecks = numDecks;
	this.playerNum = num;
	this.money = money;
	this.deck1Bust = deck1Bust;
	this.deck2Bust = deck2Bust;
	this.standing1 = standing1;
	this.standing2 = standing2;
	this.isDoubled1 = isDoubled1;
	this.isDoubled2 = isDoubled2;
	this.isSplit = isSplit;
	this.insured = insured;
	this.bet = 0;
	this.splitBet = 0;
	this.insurance = 0;
}
	
	/**
	 * Adds a card to the player's deck(s)
	 * @param deck int - 1 or 2. In case the player split their deck
	 * @param card Card - the card being added to the deck
	 */
	public void addCard(int deck, Card card) {
		if(deck == 1) {
			this.deck1.addCard(card);
		} else if(deck == 2) {
			this.deck2.addCard(card);
		}
	}
	
	/**
	 * Sets the player's deck to busted.
	 * @param deck int - 1 or 2. Which deck to bust, in case the player split their deck
	 */
	public void bustPlayer(int deck) {
		if(deck == 1) {
			deck1Bust = true;
		}
		
		if(deck == 2) {
			deck2Bust = true;
		}
	}
	
	/**
	 * resets the players values
	 */
	public void reset() {
		this.deck1Bust = false;
		this.deck2Bust = false;
		this.standing1 = false;
		this.standing2 = false;
		this.isDoubled1 = false;
		this.isDoubled2 = false;
		this.numDecks = 1;
		this.doubleCard1 = null;
		this.doubleCard2 = null;
		this.isSplit = false;
		this.insured = false;
		
	}
	
	/**
	 * 
	 * @return boolean value for if the deck is split
	 */
	public boolean checkSplit() {
		return this.isSplit;
	}
	
	/**
	 * If the players deck(s) are bust, the player is busted.
	 * @return boolean for if the player is busted.
	 */
	public boolean isBusted() {
		if(this.deck1Bust && this.deck2Bust) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param deck int - 1 or 2.
	 * @return boolean for if the selected deck is bust
	 */
	public boolean getBust(int deck) {
		if(deck == 1) {
			return this.deck1Bust;
		} else {
			return this.deck2Bust;
		}
	}
	
	/**
	 * getter for the player's number
	 * @return int
	 */
	public int getNum() {
		return this.playerNum;
	}
	
	public void showDouble() {
		if(this.isDoubled1) {
			this.deck1.addCard(doubleCard1);
		}
		if(this.isDoubled2) {
			this.deck2.addCard(doubleCard2);
		}
		
	}
	
	public void split(Card[] cards) {
		this.deck2 = new Deck(true);
		this.splitBet = this.bet;
		this.money -= this.bet;
		this.isSplit = true;
		this.numDecks++;
		this.deck2.addCard(this.deck1.top());
		this.deck1.addCard(cards[0]);
		this.deck2.addCard(cards[1]);
	}	
	
	public int getNumDecks() {
		return this.numDecks;
	}
	
	public void doubleDown(Card card, int deck) {
		if(deck == 1) {
			this.doubleCard1 = card;
			this.money -= bet;
			this.bet *= 2;
			this.standing1 = true;
			this.isDoubled1 = true;
		} else {
			this.doubleCard2 = card;
			this.money -= splitBet;
			this.splitBet *= 2;
			this.standing2 = true;
			this.isDoubled2 = true;
		}

	}
	
	public boolean promptToHit(int playerNum, int deckNum, Card dealerCard, Deck pDeck, int count) {
		String input = "";
		while(!input.equals("h") && !input.equals("s") ) {
			System.out.println("Player " + (playerNum) + ", would you like to hit or stand on deck " + (deckNum) + "? h/s ");
			input = this.scnr.next();
		}
		
		if(input.equals("h")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean promptToSplit(int playerNum, int deck, Card dealerCard, Deck pDeck, int count) {
		String input = "";
		while(!input.equals("y") && !input.equals("n") ) {
			System.out.println("Player " + playerNum + " would you like to split? y/n");
			input = this.scnr.next();
		}
		if(input.equals("y")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean promptToDouble(int playerNum, int deck, Card dealerCard, Deck pDeck, int count) {
		String input = "";
		while(!input.equals("y") && !input.equals("n")) {
			System.out.println("Player " + playerNum + ", would you like to double down? on deck " + deck + "? y/n");
			input = scnr.next();
		}
		
		if(input.equals("y")) {
			return true;
		} else {
			return false;
		}
	}
	
	public long promptToBet(int playerNum, long money, int count, long minBet) {
		long bet = -1;
		while(bet < 0 || bet%2 != 0 || bet%10 != 0 || bet > money || bet < minBet) {
			System.out.println("Player " + playerNum + ", please place your bet, even multiples of 10 only.");
			System.out.println("Minimum bet: " + minBet);
			bet = scnr.nextLong();
		}
		return bet;
	}
	
	public boolean promptAnother() {
		String another = "";
		
		while(!another.equals("n") && !another.equals("y")) {
			System.out.println("Player " + this.playerNum + ", would you like to play another hand? y/n");
			another = scnr.next();
		}
		
		if(another.equals("y")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean promptInsurance(int count) {
		String input = "";
		while(!input.equals("y") && !input.equals("n")) {
			System.out.println("Player " + this.playerNum + ", would you like to take insurance? y/n");
			input = scnr.next();

		}
		if(input.equals("y")) {
			return true;
		} else {
			return false;
		}
	}
	
	public void bustAlert() {
		String pause = "";
		while(pause.equals("")) {
			System.out.println("Player " + this.getNum() + " has busted. Hit any key then enter to continue...");
			pause = scnr.next();
		}
	}
	
	
	public void insure() {
		this.insured = true;
		this.insurance = this.bet/2;
		this.money -= this.insurance;
		
	}
	
	public int claimInsurance(boolean payOut) {
		if(payOut) {
			this.money += 3*this.insurance;
			this.insurance = 0;
			return 0;
		} else {
			int rtn = this.insurance;
			this.insurance = 0;
			return rtn;
		}
	}
	
	public int getInsurance() {
		return this.insurance;
	}
	
	
	public void addMoney(int money) {
		this.money += money;
	}
	
	public boolean getInsured() {
		return this.insured;
	}
	
	public boolean getStanding(int deck) {
		if(deck == 1) {
			return this.standing1;
		}
		
		return this.standing2;
	}
	
	public void setStand(boolean set, int deck) {
		if(deck == 1) {
			this.standing1 = set;
		}
		
		if(deck == 2) {
			this.standing2 = set;
		}
		
	}
	
	public void bet(long bet2) {
		this.bet += bet2;
		this.money -= this.bet;
	}
	
	public long getBet() {
		return this.bet + this.splitBet;
	}
	
	public long getMoney() {
		return this.money;
	}
	
	public void addWinnings(long l) {
		this.money += l;
	}
	
	
	public long claimBet(int index) {
		long rtn = 0;
		if(index == 1) {
			rtn = this.bet;
			this.bet = 0;
		} else if(index == 2) {
			rtn = this.splitBet;
			this.splitBet = 0;
		}
		return rtn;	
	}
	
	public void newDeck(int index) {
		if(index == 1) {
			this.deck1 = new Deck(true);
		} else if(index == 2) {
			this.deck2 = new Deck(true);
		}
	}
	
	/**
	 * Return the selected deck of this player
	 * @param deck int - 1 or 2
	 * @return Deck
	 */
	public Deck getDeck(int deck) {
		Deck rtn;
		if(deck == 1) {
			rtn = this.deck1;
			return rtn;
		} else if(deck == 2) {
			rtn = this.deck2;
			return rtn;
		} else {
			return null;
		}
	}
	

}
