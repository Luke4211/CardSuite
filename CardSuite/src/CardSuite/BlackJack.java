package CardSuite;
import java.util.ArrayList;
import java.util.Scanner;
public class BlackJack {
	private boolean run;
	private int houseMoney;
	private int playerMoney;
	private int numPlayers;
	private int handCount;
	private Player[] players;
	private Deck house;
	private Deck discard;
	private Scanner scnr;
	
	public BlackJack(int dealerMoney, int playerMoney, int numPlayers) {
		this.run = true;
		this.house = new Deck(false);
		this.discard = new Deck(true);
		this.houseMoney = dealerMoney;
		this.playerMoney = playerMoney;
		this.scnr = new Scanner(System.in);
		this.numPlayers = numPlayers;
		this.handCount = 0;
		this.house.shuffle();
		this.players = new Player[this.numPlayers];
		for(int i = 0; i < this.numPlayers; i++) {
			Player newPlayer = new Player(i+1, this.playerMoney);
			this.players[i] = newPlayer;
		}
		game();
	}
	
	public Object[] getInfo() {
		Object[] rtn = new Object[8];
		rtn[0] = this.house;
		rtn[1] = this.houseMoney;
		rtn[2] = this.playerMoney;
		rtn[3] = this.discard;
		rtn[4] = this.numPlayers;
		rtn[5] = this.handCount;
		rtn[6] = this.players;
		return rtn;
	}
	
	public void setDeck(Deck deck) {
		this.house = deck;
	}
	
	
	
	public void game() {
		while(this.run) {
			
			BlackJackHand hand = new BlackJackHand(this.getInfo()); 
		
			hand.hand();
			this.handCount++;
			System.out.println("Would you like to play another hand? y/n Count: " + this.handCount);
			String another = this.scnr.next();
			if(another.equals("n")) {
				this.run = false;
				System.out.println("Thanks for playing!");
			} else {
				this.setDeck(hand.getHouse());
				int money = hand.getMoney();
				this.houseMoney = money;
				this.discard = hand.getDiscard();
				this.players = hand.getPlayers();
			}

			
			if(this.reshuffle()) {
				this.house.addDeck(discard);
				this.house.shuffle();
				this.discard = new Deck(true);
			}
			
			
		}
	}
	
	/*
	 * This method returns true if it's time
	 * to reshuffle the deck, and false if otherwise
	 * The formula for the number of hands on a deck
	 * equals 6 - the number of players.
	 */
	private boolean reshuffle() {
		//this.house.printDeck();
		if(this.handCount > 6 - this.numPlayers) {
			return true;
		} else {
			return false;
		}
	}
}
