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
	protected int ustenSS;
	protected int countStyle;
	protected Player[] players;
	protected Deck house;
	protected Deck discard;
	protected Scanner scnr;
	protected boolean isSim;
	
	
	public BlackJack(long dealerMoney, long playerMoney, int numPlayers, int numHands, int minBet, int countStyle) {
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
		this.ustenSS = -2;
		this.countStyle = countStyle;		
	}
	
	
	
	public Object[] getInfo() {
		Object[] rtn = new Object[11];
		rtn[0] = this.house;
		rtn[1] = this.houseMoney;
		rtn[2] = this.playerMoney;
		rtn[3] = this.discard;
		rtn[4] = this.numPlayers;
		rtn[5] = this.handCount;
		rtn[6] = this.players;
		rtn[7] = this.hiLo;
		rtn[8] = this.minBet;
		rtn[9] = this.countStyle;
		rtn[10] = this.ustenSS;
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
	
	protected void addPlayers() {
		for(int i = 0; i < this.numPlayers; i++) {
			Player newPlayer = new Player(i+1, this.playerMoney);
			this.players[i] = newPlayer;
		}
	}
}
