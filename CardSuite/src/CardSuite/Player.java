package CardSuite;

public class Player {
	private Deck deck1;
	private Deck deck2;
	private Card doubleCard1;
	private Card doubleCard2;
	private int playerNum;
	private int numDecks;
	private int bet;
	private int splitBet;
	private int insurance;
	private int money;
	private boolean deck1Bust;
	private boolean deck2Bust;
	private boolean standing1;
	private boolean standing2;
	private boolean isDoubled;
	private boolean isSplit;
	/*
	 * Basic constructor for Player class.
	 */
	public Player(int num, int money) {
		this.deck1 = new Deck(true);
		this.deck2 = new Deck(true);
		this.numDecks = 1;
		this.playerNum = num;
		this.money = money;
		this.deck1Bust = false;
		this.deck2Bust = false;
		this.standing1 = false;
		this.standing2 = false;
		this.isDoubled = false;
		this.isSplit = false;
		this.bet = 0;
		this.splitBet = 0;
		this.insurance = 0;
		
	}
	
	public void addCard(int deck, Card card) {
		if(deck == 1) {
			this.deck1.addCard(card);
		} else if(deck == 2) {
			this.deck2.addCard(card);
		}
	}
	
	public void bustPlayer(int deck) {
		if(deck == 1) {
			deck1Bust = true;
		}
		
		if(deck == 2) {
			deck2Bust = true;
		}
	}
	
	public void reset() {
		this.deck1Bust = false;
		this.deck2Bust = false;
		this.standing1 = false;
		this.standing2 = false;
		this.isDoubled = false;
		this.numDecks = 1;
		this.insurance = 0;
		this.doubleCard1 = null;
		this.doubleCard2 = null;
		this.isSplit = false;
		
	}
	
	public boolean checkSplit() {
		return this.isSplit;
	}
	
	public boolean isBusted() {
		if(this.deck1Bust && this.deck2Bust) {
			return true;
		}
		
		return false;
	}
	
	public boolean getBust(int deck) {
		if(deck == 1) {
			return this.deck1Bust;
		} else {
			return this.deck2Bust;
		}
	}
	
	public int getNum() {
		return this.playerNum;
	}
	
	public void showDouble() {
		if(this.isDoubled) {
			this.deck1.addCard(doubleCard1);
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
		this.deck2.printDeck();
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
		} else {
			this.doubleCard2 = card;
			this.money -= splitBet;
			this.splitBet *= 2;
			this.standing2 = true;
		}
		this.isDoubled = true;
		
	
		
		
		
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
	
	public void bet(int amount) {
		this.bet = amount;
		this.money -= this.bet;
	}
	
	public int getBet() {
		return this.bet + this.splitBet;
	}
	
	public int getMoney() {
		return this.money;
	}
	
	public void addWinnings(int amount) {
		this.money += amount;
	}
	
	public int claimBet(int index) {
		int rtn = 0;
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
	
	public Deck getDeck(int deck) {
		if(deck == 1) {
			return this.deck1;
		} else if(deck == 2) {
			return this.deck2;
		} else {
			return null;
		}
	}
}
