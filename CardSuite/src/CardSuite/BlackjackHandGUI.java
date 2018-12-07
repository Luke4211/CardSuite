package CardSuite;
import java.util.ArrayList;
import java.util.Scanner;
/*
 * A class which contains the logic for a
 * hand of blackjack
 */
public class BlackjackHandGUI {
	private boolean handOver;
	private boolean hiddenCard;
	private boolean dealerBust;
	protected boolean isSim;
	private boolean preCard;
	private long houseMoney;
	private int numPlayers;
	private int handCount;
	private int minBet;
	private int initialHiLo;
	private Player[] players;
	private BlackJackHiLo hiLoCounter;
	private Deck house;
	private Deck dealer;
	private Deck discard;
	private Card hideCard;
	private Card showCard;
	private Scanner scnr;
	
	/*
	 * Basic constructor, initializes instance variables
	 * to the values contained in the inputted array
	 */
	public BlackjackHandGUI(Object[] input) {
		this.house = (Deck)input[0];
		this.houseMoney = (long)input[1];
		this.discard = (Deck)input[3];
		this.numPlayers = (int)input[4];
		this.players = (Player[])input[6];
		this.dealer = new Deck(true);
		this.initialHiLo = (int)input[7];
		this.minBet = (int)input[8];
		this.handOver = false;
		this.hiddenCard = false;
		this.dealerBust = false;
		this.scnr = new Scanner(System.in);
		this.hiLoCounter = new BlackJackHiLo();
		this.preCard = true;
		this.hiLoCounter.setCount(this.initialHiLo);
		this.isSim = true;		
	}
	
	/*
	 * getHouse() returns the house deck.
	 */
	public Deck getHouse() {
		return this.house;
	}
	
	/*
	 * getDiscard() returns the discard deck
	 */
	public Deck getDiscard() {
		return this.discard;
	}
	
	/*
	 * getMoney() returns an array containing
	 * the player and house money amounts.
	 */
	public long getMoney() {
		
		return this.houseMoney;
	}	
	
	/*
	 * getPlayers() returns the array of 
	 * players in the game
	 */
	public Player[] getPlayers() {
		return this.players;
	}
	
	//added for GUI
	public Player getPlayers(int i) {
		return this.players[i];
	}
	
	public int getDealerCard() {
		return this.showCard.getVal();
	}
	public String printDealerDeck() {
		return this.dealer.printDeckGUI();
	}
	public Deck getDealerDeck() {
		return this.dealer;
	}
	
	public void setHandOver(boolean handOver) {
		this.handOver=handOver;
	}
	
	public boolean getHandOver() {
		return this.handOver;
	}
	
	public void initGUI() {
		Card topCard;
		this.dealHand();
		topCard = this.drawCard();
		this.hideCard = topCard;
		this.dealer.addCard(topCard);
		this.dealHand();
		topCard = this.drawCard();
		this.showCard = topCard;
		this.dealer.addCard(topCard);
		this.preCard = false;
		this.isSim=false;
		this.handOver=false;
	}
	
	public void hitUntil17GUI() {
		this.hiddenCard = true;
		int sum = handPts(this.dealer);
		if(this.soft17()) {
			this.hitDealer();
				
			sum = handPts(this.dealer);
		}
		while(sum<17) {
			this.hitDealer();				
			sum = handPts(this.dealer);
		}
		if(bust(this.dealer)) {
			this.dealerBust = true;
		}
		this.handOver = true;

	}
	//added for GUI
	
	/*
	 * soft17() returns true if the dealer's
	 * hand is a soft 17. A soft 17 means that the dealer 
	 * has a hand whose value is 17 when he has one ace 
	 * valued at 10. If the dealer hits and goes over 21, the
	 * ace is reconsidered as low so he doesn't necessarily bust.
	 
	 */
	private boolean soft17() {
		Deck tempDeck = this.dealer;
		boolean firstAce = false;
		int sum = 0;
		for(Card card:tempDeck.getDeck()) {
			if(!firstAce && card.getVal() == 1) {
				firstAce = true;
				sum = 11;
			}
		}
		for(Card card:tempDeck.getDeck()) {
			sum += card.getBlackJackVal();
		}
		if(firstAce) {
			sum-=1;
			if(sum == 17) {
				return true;
			}
		}
		return false;
	}
	
	
	/*
	 * winner() determines who wins between a given
	 * player's deck and the dealers deck. Returns
	 * 0 if player wins, 1 if dealer wins, and 2 
	 * if there is a tie.
	 */
	public int winner(Deck player, Deck dealer) {
		int playerPts = handPts(player);
		int dealerPts = handPts(dealer);
		if(dealer.size() == 2 && dealerPts == 21) {
			if(player.size() == 2 && playerPts == 21) {
				return 2;
			} else {
				return 1;
			}
		} else if(player.size() == 2 && playerPts == 21) {
			return 0;
		}
		
		playerPts = 21 - playerPts;
		dealerPts = 21 - dealerPts;
		if(playerPts<0) {
			return 1;
		} else if(dealerPts<0) {
			return 0;
		}
		if(playerPts < dealerPts) {
			return 0;
		} else if(dealerPts < playerPts) {
			return 1;
		} else {
			return 2;
		}
	}
	
//	public int winner(Deck player, Deck dealer) {
//		int playerPts = handPts(player);
//		int dealerPts = handPts(dealer);
//		if(playerPts>dealerPts&&playerPts<=21) {
//			return 0;
//		}else if(dealerPts>playerPts&&dealerPts<=21) {
//			return 1;
//		}else {
//			return 2;
//		}
//	}
	/*
	 * bust() determines if the inputted deck's
	 * value exceeds 21, if so it returns true,
	 * otherwise returns false 
	 */
	public boolean bust(Deck hand) {
		if(handPts(hand) > 21) {
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * dealHand() deals one card to each
	 * deck in play (minus the dealer deck).
	 */
	private void dealHand() {
		for(int i = 0; i < this.numPlayers; i++) {
			int numDecks = this.players[i].getNumDecks();
			for(int j = 0; j < numDecks; j++) {
				Card topCard = this.drawCard();
				this.players[i].addCard(j+1, topCard);
			}			
		}
	}
	
	/*
	 * showDoubles() loops through each player and calls
	 * the showDouble() method, which adds the double
	 * card(s) to their main deck.
	 */
	private void showDoubles() {
		for(int i = 0; i < this.numPlayers; i++) {
			this.players[i].showDouble();
		}
	}
	
	/*
	 *  
	 *  hit() draws one card from the top of the
	 *  house deck and adds it into the passed
	 *  deck
	 */
	public void hit(int player, int deck) {
		Card card = this.drawCard();
		this.players[player].addCard(deck, card);		
	}
	
	/*
	 * hitDealer() draws one card from the house deck
	 * and adds it to the dealer's deck.
	 */
	private void hitDealer() {
		Card card = this.drawCard();
		this.dealer.addCard(card);
	}
	
	/*
	 * This method causes the dealer to repeatedly hit
	 * until his cards are at least 17 with no high aces.
	 */
	private void hitUntil17() {
		if(true) {
			this.hiddenCard = true;
			int sum = handPts(this.dealer);
			if(this.soft17()) {
				this.hitDealer();
				
				sum = handPts(this.dealer);
			}
			while(sum<17) {
				this.hitDealer();
				
				sum = handPts(this.dealer);
			}		
			if(bust(this.dealer)) {
				this.dealerBust = true;
			}
			if(!this.isSim) {
				for(int k = 0; k < this.numPlayers; k++) {
					for(int l = 0; l < this.players[k].getNumDecks(); l++) {
						if(this.winner(this.players[k].getDeck(l+1), this.dealer) == 0) {
						}
					}
				}
			
			
				String pause = "";
				while(pause.equals("")) {
					pause = scnr.next();
				}
			}
			this.handOver = true;
		}

	}

	/*
	 * canSplit() checks if the inputted deck
	 * can be split
	 */
	public boolean canSplit(Deck deck) {
		if(deck.size() == 2) {
			Card card1 = deck.getCard(0);
			Card card2 = deck.getCard(1);
			if(card1.getBlackJackVal() == card2.getBlackJackVal()) {
				return true;
			}
		}
		
		return false;
	}
	
	/*
	 * handPts() calculates the point value of a hand 
	 */
	public int handPts(Deck hand) {
		int sum = 0;
		boolean val10 = false;
		ArrayList<Card> aces = new ArrayList<Card>();
		
		for(int i = 0; i < hand.size(); i++) {
			int bjVal;
			try {
				bjVal = hand.getCard(i).getBlackJackVal();
			} catch(NullPointerException exception) {
				bjVal = 0;
			}
			if(bjVal == 1) {
				aces.add(hand.getCard(i));
			} else {
				sum += bjVal;
			}
		}
		for(int i = 0; i<aces.size(); i++) {
			if((sum+11) > 21){
				if(sum+1 > 21 && val10) {
					sum-=10;
					val10 = false;
				}
				sum++;
			} else {
				sum+= 11;
				val10 = true;
			}
		}
		return sum;
	}
	
	/**
	 * cleanUp() pays the pot out to the winner,-
	 * or redistributes it in case of a tie, and
	 * then updates the screen. It then adds all
	 * the decks together into the house deck.
	 */
	public void cleanUp() {
		for(int i = 0; i < this.numPlayers; i++) {			
			int numDecks = this.players[i].getNumDecks();
			for(int j = 0; j < numDecks; j++) {				
				int winner = this.winner(this.players[i].getDeck(j+1), this.dealer);
				if(winner == 0) {
					if(this.handPts(this.players[i].getDeck(j+1)) == 21 &&
						this.players[i].getDeck(j+1).size() == 2) {
						long bet = this.players[i].claimBet(j+1);
						int payOut = (int)(1.5*bet);
						this.houseMoney -= payOut;
						bet += payOut;
						this.players[i].addWinnings(bet);					
					} else {
						long bet = this.players[i].claimBet(j+1);
						this.players[i].addWinnings(2*bet);
						this.houseMoney -= bet;
					}
				} else if(winner == 1) {
					long bet = this.players[i].claimBet(j+1);
					this.houseMoney += bet;
				} else if(winner == 2) {
					long bet = this.players[i].claimBet(j+1);
					this.players[i].addWinnings(bet);
				}
				this.discard.addDeck(this.players[i].getDeck(j+1));
				this.players[i].newDeck(j+1);
				this.players[i].reset();	
			}
		}
		this.discard.addDeck(this.dealer);
		this.dealer = new Deck(true);
	}
	
	public Card drawCard() {
		Card topCard;
		try {
			topCard = this.house.top();
		} catch(IndexOutOfBoundsException exception) {
			Deck tempDeck = new Deck(true);
			tempDeck.addDeck(this.discard);
			this.house = tempDeck;
			this.discard = new Deck(true);
			topCard = this.house.top();
		}
		return topCard;
	}	
}
