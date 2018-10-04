package CardSuite;
import java.util.ArrayList;
import java.util.Scanner;
/*
 * A class which contains the logic for a
 * hand of blackjack
 */
public class BlackJackHand {
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
	public BlackJackHand(Object[] input) {
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
		this.isSim = false;
		
		
		
	}
	
	/*
	 * The hand() method contains main loop of each hand. After dealing the 
	 * initial cards and taking bets, the player can hit, stand, 
	 * split* or double down* until he either busts or stands. If he busts, he 
	 * loses the hand. If he stands, the dealer must hit until their hand value is 
	 * at least 17. Once the hand value equals or is between 17 and 21 inclusive,
	 * the points are tallied and the closest to 21 wins. If the dealer busts
	 * while hitting they automatically lose.
	 */
	public void hand() {
		initalize();
		if(this.showCard.getBlackJackVal() == 1) {
			this.offerInsurance();
		}
		if(!this.handOver) {
			if(!this.isSim) {
				
			}
			if(this.natural(this.dealer)) {
				this.hiddenCard = false;
				for(int i = 0; i < this.numPlayers; i++) {
					this.players[i].bustPlayer(1);
					this.handOver = true;
				}
			} else {
				for(int i = 0; i < this.numPlayers; i++) {
					this.updateHiLo();
					boolean surrender = this.players[i].promptSurrender(i, this.showCard, this.players[i].getDeck(1), this.getHiLo());
					if(surrender) {
						this.houseMoney += this.players[i].surrender();
					} else {
						for(int j = 0; j < this.players[i].getNumDecks(); j++) {
							boolean isBusted = this.bust(players[i].getDeck(j+1));
							boolean isStanding = this.players[i].getStanding(j+1);
							if(this.canSplit(this.players[i].getDeck(1)) && !this.players[i].checkSplit()) {
								this.updateHiLo();
								boolean split = this.players[i].promptToSplit(i+1, j+1, this.dealer.getCard(0), this.players[i].getDeck(j+1), this.getHiLo());
								if(split) {
									Card[] cards = new Card[2];
									Card card = this.drawCard();
									cards[0] = card;
									card = this.drawCard();
									cards[1] = card;
									this.players[i].split(cards);
									this.updateHiLo();
									this.printState();
									
								}
							}
							Boolean isDoubled = false;
							Deck temp = this.players[i].getDeck(j+1);
							this.updateHiLo();
							boolean toDouble = this.players[i].promptToDouble(i+1, j+1, this.dealer.getCard(0), temp, this.getHiLo());
							if(toDouble) {
								Card card = this.drawCard();
								this.players[i].doubleDown(card, j+1);
								isDoubled = true;
							}
							while(!isBusted && !isStanding && !isDoubled) {
								temp = this.players[i].getDeck(j+1);
								this.updateHiLo();
								boolean playerHit = this.players[i].promptToHit(i+1, j+1, this.dealer.getCard(0), temp, this.getHiLo());
								if(playerHit) {
									this.hit(i, j+1);
									if(this.bust(players[i].getDeck(j+1))) {
										this.players[i].bustPlayer(j+1);
										this.printState();	
										this.players[i].bustAlert();
									}						
									this.printState();						
								} else {
									this.players[i].setStand(true, j+1);
								}
								isBusted = this.bust(players[i].getDeck(j+1));
								isStanding = this.players[i].getStanding(j+1);
							}
						}
					}
				}
			}
		}

		this.showDoubles();
		this.hitUntil17();
		this.cleanUp();	
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
	
	/*
	 * initialize() shuffles the deck, then deals the
	 * initial cards to the table and collects the bet
	 * placed by the player.
	 */
	private void initalize() {
		Card topCard;
		Scanner scnr = new Scanner(System.in);
		String input = new String("");
		this.printState();
		this.takeBets();
		this.dealHand();
		topCard = this.drawCard();
		this.hideCard = topCard;
		this.dealer.addCard(topCard);
		this.dealHand();
		topCard = this.drawCard();
		this.showCard = topCard;
		this.dealer.addCard(topCard);
		this.preCard = false;
		this.updateHiLo();	
		this.printState();
		
	}
	
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
	
	private boolean natural(Deck hand) {
		if(hand.size() == 2) {
			if(this.handPts(hand) == 21) {
				return true;
			}
		}
		
		return false;
	}
	
	
	private void hiLo(Card card) {
		try {
			this.hiLoCounter.count(card.getBlackJackVal());
		} catch(NullPointerException exception) {
			
		}
	}
	
	
	/*
	 * winner() determines who wins between a given
	 * player's deck and the dealers deck. Returns
	 * 0 if player wins, 1 if dealer wins, and 2 
	 * if there is a tie.
	 */
	private int winner(Deck player, Deck dealer) {
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
		if(this.bust(player)) {
			return 1;
		} else if(this.dealerBust) {
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
	
	public int getHiLo() {
		return this.hiLoCounter.getCount();
	}
	
	/*
	 * bust() determines if the inputted deck's
	 * value exceeds 21, if so it returns true,
	 * otherwise returns false 
	 */
	private boolean bust(Deck hand) {
		if(handPts(hand) > 21) {
			return true;
		} else {
			return false;
		}
	}
	
	private void offerInsurance() {
		this.updateHiLo();
		boolean noInsure = true;
		boolean dealerWins = true;
		for(int i = 0; i < this.numPlayers; i++) {
			boolean insure = this.players[i].promptInsurance(this.getHiLo());
			if(insure) {
				this.players[i].insure();
				noInsure = false;
		
			}
		}
		if(!noInsure) {
			if(this.hideCard.getBlackJackVal() == 10) {
				dealerWins = false;
				this.hiddenCard = true;
				this.handOver = true;
				
			}
			for(int i = 0; i < this.numPlayers; i++) {
				if(this.players[i].getInsured()) {
					int insure = this.players[i].getInsurance();
					int add = this.players[i].claimInsurance(!dealerWins);
					if(add == 0) {
						this.houseMoney -= 2*insure;
					} else {
						this.houseMoney += add;
					}
				}
			}
		}
		this.updateHiLo();
		
		this.printState();
		
		if(!this.isSim) {
			System.out.println("Insurance has been settled. press any key");
			String pause = this.scnr.nextLine();
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
		this.updateHiLo();
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
	private void hit(int player, int deck) {
		Card card = this.drawCard();
		this.players[player].addCard(deck, card);
		this.updateHiLo();
		
	}
	
	/*
	 * hitDealer() draws one card from the house deck
	 * and adds it to the dealer's deck.
	 */
	private void hitDealer() {
		Card card = this.drawCard();
		this.dealer.addCard(card);
		this.updateHiLo();
	}
	
	/*
	 * This method causes the dealer to repeatedly hit
	 * until his cards are at least 17 with no high aces.
	 */
	private void hitUntil17() {
		if(this.allFinished()) {
			this.hiddenCard = true;
			int sum = handPts(this.dealer);
			if(this.soft17()) {
				this.hitDealer();
				this.printState();
				
				sum = handPts(this.dealer);
			}
			while(sum<17) {
				this.hitDealer();
				this.printState();
				
				sum = handPts(this.dealer);
			}		
			printState();
			if(bust(this.dealer)) {
				this.dealerBust = true;
			}
			if(!this.isSim) {
				System.out.println("" + this.isSim);
				System.out.println("Winning players (if any): ");
				for(int k = 0; k < this.numPlayers; k++) {
					for(int l = 0; l < this.players[k].getNumDecks(); l++) {
						if(this.winner(this.players[k].getDeck(l+1), this.dealer) == 0 && !this.players[k].getSurrender()) {
							System.out.println("Player " + (k+1) + " Deck " + (l+1));
						}
					}
				}
			
			
				String pause = "";
				while(pause.equals("")) {
					System.out.println("Hit any key then enter to continue...");
					pause = scnr.next();
				}
			}
			this.handOver = true;
			this.updateHiLo();
		}

	}
	
	private void updateHiLo() {
		this.hiLoCounter.setCount(0);
		for(int i = 0; i < this.numPlayers; i++) {
			for(int j = 0; j < this.players[i].getNumDecks(); j++) {
				ArrayList<Card> cards = this.players[i].getDeck(j+1).getDeck();
				for(Card card: cards) {
					this.hiLo(card);
				}
			}
		}
		ArrayList<Card> disCards = this.discard.getDeck();
		for(Card card: disCards) {
			this.hiLo(card);
		}
		if(!this.hiddenCard && !this.preCard) {
			this.hiLo(this.showCard);
			
		} else {
			ArrayList<Card> dealer = this.dealer.getDeck();
			for(Card card: dealer) {
				this.hiLo(card);
			}
		}
		
	}
	/*
	 * takeBet() takes a bet from each player
	 * in the game.
	 */
	private void takeBets() {
		
		//add in split and double down functionality here
		Scanner scnr = new Scanner(System.in);
		String input = new String();
		for(int i = 0; i < this.numPlayers; i++) {
			this.updateHiLo();
			int count = this.hiLoCounter.getCount();
			long bet = 0;
			bet = this.players[i].promptToBet(i+1, this.players[i].getMoney(), count, minBet);
			this.players[i].bet(bet);		
			
		}
		
	}
	
	/*
	 * canSplit() checks if the inputted deck
	 * can be split
	 */
	private boolean canSplit(Deck deck) {
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
	private int handPts(Deck hand) {
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
	
	/*
	 * flushScreen() prints multiple blank lines 
	 * in order to clear the screen. For textUI only
	 */
	private void flushScreen() {
		for(int i = 0; i <30; i++)
		{
		    System.out.println("");
		}
	}
	
	/*
	 * cleanUp() pays the pot out to the winner,-
	 * or redistributes it in case of a tie, and
	 * then updates the screen. It then adds all
	 * the decks together into the house deck.
	 */
	private void cleanUp() {
		this.printState();
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
	
	/*
	 * allFinished() returns true if every player
	 * is either sitting or standing. Returns false
	 * otherwise.
	 */
	private boolean allFinished() {
		boolean allFin = true;
		
		for(int i = 0; i < numPlayers; i++) {
			for(int j = 0; j < this.players[i].getNumDecks(); j++) {
				if(!this.players[i].getStanding(j+1) && !this.players[i].getBust(j+1)) {
					allFin = false;
				}
			}
		}
		
		return allFin;
	}
	
	private Card drawCard() {
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
		this.updateHiLo();
		return topCard;
	}
	
	/*
	 * printPlayerInfo() prints the basic information 
	 * of each player in game.
	 */
	private void printPlayerInfo() {
		for(int i = 0; i < this.numPlayers; i++) {
			System.out.println("*****************************************************");
			System.out.println("Player " + (this.players[i].getNum()));
			System.out.println("Money: $" + this.players[i].getMoney());
			System.out.println("Bet: $" + this.players[i].getBet());
			System.out.println("Insurance: $" + this.players[i].getInsurance());
			for(int j = 0; j < this.players[i].getNumDecks(); j++) {
				System.out.println("Deck: " + (j+1) + " Size of deck: " + this.players[i].getDeck(j+1).size());
				if(this.players[i].getDeck(j+1).size() == 0) {
					System.out.println("You currently have no cards.");
				} else {
					System.out.println("Your cards, points: " + this.handPts(this.players[i].getDeck(j+1)));
					this.players[i].getDeck(j+1).printDeck();
				}
			}
			System.out.println("*****************************************************");
		}
	}
	
	/*
	 * printState() prints a primitive textUI for the game
	 */
	private void printState() {
		if(this.isSim) {
			return;
		}
		this.updateHiLo();
		flushScreen();
		System.out.println("*****************************************************");
		System.out.println("House money: $" + this.houseMoney);
		System.out.println("Deck Count: " + this.hiLoCounter.getCount());
		this.printPlayerInfo();
		System.out.println("*****************************************************");
		Card dealerShow;
		if(this.dealer.size() == 2 && this.hiddenCard == false) {
			dealerShow = this.dealer.getCard(0);
			System.out.println("Dealer's top card: " + dealerShow.getInfo());

		}
		if(this.hiddenCard) {
			System.out.println("Dealers cards, points: " + this.handPts(this.dealer));
			int size = this.dealer.size();
			for(int i = 0; i < size; i++) {
				Card card = this.dealer.getCard(i);
				System.out.println(card.getInfo());
			}
			
		}
		
		for(int i = 0; i<4; i++) {
			System.out.println("");
		}
	}
}