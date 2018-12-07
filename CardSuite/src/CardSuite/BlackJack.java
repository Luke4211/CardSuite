package CardSuite;
import java.util.ArrayList;
import java.util.Scanner;
public class BlackJack {
	protected boolean run;
	protected long houseMoney;
	protected long playerMoney;
	protected int numPlayers;
	protected int handCount;
	protected int numHands;
	protected int minBet;
	protected int hiLo;
	protected Player[] players;
	protected Deck house;
	protected Deck discard;
	protected Scanner scnr;
	protected boolean isSim;
	
	
	/**
	 * BlackJack Constructor
	 */
	public BlackJack() {
		this.run = true;
		this.house = new Deck(false);
		this.discard = new Deck(true);
		this.houseMoney = 1000000;
		this.playerMoney = 5000;
		this.numHands = 100;
		this.minBet = 500;
		this.numPlayers = 1;
		this.handCount = 0;
		this.house.shuffle();
		this.players = new Player[this.numPlayers];
		this.hiLo = 0;
		this.addPlayers();
	}
	
	/**
	 * BlackJack Constructor
	 * @param dealerMoney
	 * @param playerMoney - starting money
	 * @param numPlayers
	 * @param numHands - total number of hands
	 * @param minBet
	 */
	public BlackJack(long dealerMoney, long playerMoney, int numPlayers, int numHands, int minBet) {
		this.run = true;
		this.house = new Deck(false);
		this.discard = new Deck(true);
		this.houseMoney = dealerMoney;
		this.playerMoney = playerMoney;
		this.numHands = numHands;
		this.minBet = minBet;
		this.scnr = new Scanner(System.in);
		this.numPlayers = numPlayers;
		this.handCount = 0;
		this.house.shuffle();
		this.players = new Player[this.numPlayers];
		this.hiLo = 0;
		this.addPlayers();		
	}
	
	public long getHouseMoney() {
		return this.houseMoney;
	}	
	
	public long getPlayerMoney() {
		return playerMoney;
	}

	public int getNumHands() {
		return numHands;
	}

	public int getMinBet() {
		return minBet;
	}
	
	
	/**
	 * Array of objects from this black jack game. Array cells respectively contain... 0-the house's deck,
	 * 1-the houses money, 2-player starting money, 3-discard deck, 4-number of players,
	 * 5-the number of hands for this game, 6-the array of players, 7-the hiLo int,
	 * 8-the minimum bet.
	 * @return Object[]
	 */
	public Object[] getInfo() {
		Object[] rtn = new Object[10];
		rtn[0] = this.house;
		rtn[1] = this.houseMoney;
		rtn[2] = this.playerMoney;
		rtn[3] = this.discard;
		rtn[4] = this.numPlayers;
		rtn[5] = this.handCount;
		rtn[6] = this.players;
		rtn[7] = this.hiLo;
		rtn[8] = this.minBet;
		return rtn;
	}
	
	public void setDeck(Deck deck) {
		this.house = deck;
	}
	
	
	/**
	 * The main loop for the game
	 */
	public void game() {
		while(this.run) {
			BlackJackHand hand = new BlackJackHand(this.getInfo()); 
			hand.hand();
			this.handCount++;
			this.setDeck(hand.getHouse());
			long money = hand.getMoney();
			this.houseMoney = money;
			this.hiLo = hand.getHiLo();
			this.discard = hand.getDiscard();
			this.players = hand.getPlayers();
			ArrayList<Player> tempPlayers = new ArrayList<Player>();
			for(int i = 0; i < this.numPlayers; i++) {
				boolean another = this.players[i].promptAnother();
				if(another) {
					tempPlayers.add(this.players[i]);
				}
			}
			this.numPlayers -= (this.players.length - tempPlayers.size());
			if(this.numPlayers > 0) {
				Player[] tempArry = new Player[this.numPlayers];
				int i = 0;
				for(Player player:tempPlayers) {
					tempArry[i] = player;
					i++;
				}
				this.players = tempArry;
			} else {
				this.run = false;
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
	public boolean reshuffle() {
		if(this.handCount > (6 - this.numPlayers)) {
			this.handCount = 0;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Method for testing purposes
	 */
	public void setHandCount(int handCount) {
		this.handCount = handCount;
	}
	
	/**
	 * Puts players in the game based on numPlayers
	 */
	protected void addPlayers() {
		for(int i = 0; i < this.numPlayers; i++) {
			Player newPlayer = new Player(i+1, this.playerMoney);
			this.players[i] = newPlayer;
		}
	}
}
