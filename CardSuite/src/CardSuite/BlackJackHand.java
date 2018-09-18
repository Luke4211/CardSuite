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
	private int houseMoney;
	private int numPlayers;
	private int handCount;
	private Player[] players;
	private Deck house;
	private Deck dealer;
	private Deck discard;
	private Scanner scnr;
	
	/*
	 * Basic constructor, initializes instance variables
	 * to the values contained in the inputted array
	 */
	public BlackJackHand(Object[] input) {
		this.house = (Deck)input[0];
		this.houseMoney = (int)input[1];
		this.discard = (Deck)input[3];
		this.numPlayers = (int)input[4];
		this.handCount = (int)input[5];
		this.players = (Player[])input[6];
		this.dealer = new Deck(true);
		this.handOver = false;
		this.hiddenCard = false;
		this.dealerBust = false;
		this.scnr = new Scanner(System.in);
		
		
		
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
		while(!this.handOver) {
			if(this.allFinished()) {
				this.handOver = true;
			}
			for(int i = 0; i<this.numPlayers; i++) {
				int deckNum = this.players[i].getNumDecks();
				for(int j = 0; j<deckNum; j++) {
					boolean isBusted = this.bust(players[i].getDeck(j+1));
					boolean isStanding = this.players[i].getStanding(j+1);
					
					if(!isBusted && !isStanding) {
						String input = "";
						while(!input.equals("h") && !input.equals("s") ) {
							System.out.print("Player " + (i+1) + ", would you like to hit or stand on deck " + (j+1) + "? h/s ");
							input = this.scnr.next();
						}
						if(input.equals("h")) {
							this.hit(i, j+1);
							if(this.bust(players[i].getDeck(j+1))) {
								this.players[i].bustPlayer(j+1);
								if(this.allFinished()) {
									this.handOver = true;
								}
								this.printState();
								String pause = "";
								while(pause.equals("")) {
									System.out.println("You've busted! Hit any key then enter to continue...");
									pause = scnr.next();
								}
								
							}
							this.printState();
						} else if(input.equals("s")) {
							this.players[i].setStand(true, j+1);
						}
					}
				}
			}
			
			this.showDoubles();
			this.hitUntil17();
		}
		
		this.cleanUp();
		this.printState();
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
	public int getMoney() {
		
		return this.houseMoney;
	}
	
	/*
	 * getHandCount() returns the current number of hands
	 * that have been played on the deck
	 */
	public int getHandCount() {
		return this.handCount;
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
		
		Scanner scnr = new Scanner(System.in);
		String input = new String("");
		this.printState();
		this.takeBets();
		Card topCard;
		this.dealHand();
		topCard = this.house.top();
		this.dealer.addCard(topCard);
		this.dealHand();
		topCard = this.house.top();
		this.dealer.addCard(topCard);
			
		
		
		this.printState();
		
		for(int i = 0; i < this.numPlayers; i++) {
			if(this.canSplit(players[i].getDeck(1))) {
				String split;
				System.out.println("Would you like to split? y/n");
				split = scnr.nextLine();
				while(!split.equals("y") && !split.equals("n")) {
					System.out.println("Would you like to split? y/n");
					split = scnr.nextLine();
				}
				if(split.equals("y")) {
					Card[] cards = new Card[2];
					cards[0] = this.house.top();
					cards[1] = this.house.top();
				
					this.players[i].split(cards);
					this.printState();
				}
			}
			for(int j = 0; j < this.players[i].getNumDecks(); j++) {
				input = "";
				while(!input.equals("y") && !input.equals("n")) {
					System.out.println("Players " + (i+1) + ", would you like to double down? y/n");
					input = scnr.nextLine();
					
				}
				
				if(input.equals("y")) {
					Card card = this.house.top();
					this.players[i].doubleDown(card, j+1);
					
				}
				
			}
		}
		
		
		
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
	/*
	 * winner() determines who wins between a given
	 * player's deck and the dealers deck. Returns
	 * 0 if player wins, 1 if dealer wins, and 2 
	 * if there is a tie.
	 */
	private int winner(Deck player, Deck dealer) {
		int playerPts = handPts(player);
		int dealerPts = handPts(dealer);
		
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
	
	/*
	 * dealHand() deals one card to each
	 * deck in play (minus the dealer deck).
	 */
	private void dealHand() {
		Card topCard;
		for(int i = 0; i < this.numPlayers; i++) {
			int numDecks = this.players[i].getNumDecks();
			for(int j = 0; j < numDecks; j++) {
				topCard = this.house.top();
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
	private void hit(int player, int deck) {
		this.players[player].addCard(deck, this.house.top());
		
	}
	
	/*
	 * hitDealer() draws one card from the house deck
	 * and adds it to the dealer's deck.
	 */
	private void hitDealer() {
		this.dealer.addCard(this.house.top());
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
			
			System.out.println("Winning players (if any): ");
			for(int k = 0; k < this.numPlayers; k++) {
				for(int l = 0; l < this.players[k].getNumDecks(); l++) {
					if(this.winner(this.players[k].getDeck(l+1), this.dealer) == 0) {
						System.out.println("Player " + (k+1) + " Deck " + (l+1));
					}
				}
			}
			
			String pause = "";
			while(pause.equals("")) {
				System.out.println("Hit any key then enter to continue...");
				pause = scnr.next();
			}
			this.handOver = true;
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
			int bet = -1;
			while(bet < 0 || bet%2 != 0 || bet%10 != 0 || bet > this.players[i].getMoney()) {
				System.out.println("Player " + this.players[i].getNum() + ", please place your bet, even multiples of 10 only.");
				bet = scnr.nextInt();
			}
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
			if(card1.getVal() == card2.getVal()) {
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
		ArrayList<Card> aces = new ArrayList<Card>();
		
		for(int i = 0; i < hand.size(); i++) {
			if(hand.getCard(i).getBlackJackVal() == 1) {
				aces.add(hand.getCard(i));
			} else {
				sum += hand.getCard(i).getBlackJackVal();
			}
		}

		for(int i = 0; i<aces.size(); i++) {
			if((sum+11) > 21){
					sum++;
			} else {
				sum+= 11;
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
		for(int i = 0; i < this.numPlayers; i++) {
			int numDecks = this.players[i].getNumDecks();
			for(int j = 0; j < numDecks; j++) {
				
				int winner = this.winner(this.players[i].getDeck(j+1), this.dealer);
				this.discard.addDeck(this.players[i].getDeck(j+1));
				this.players[i].newDeck(j+1);
				if(winner == 0) {
					if(this.handPts(this.players[i].getDeck(j+1)) == 21) {
						int bet = this.players[i].claimBet(j+1);
						this.houseMoney -= bet;
						bet = (int)(1.5*bet) + bet;
						this.players[i].addWinnings(bet);
										
					} else {
						int bet = this.players[i].claimBet(j+1);
						this.players[i].addWinnings(2*bet);
						this.houseMoney -= bet;
					}
				} else if(winner == 1) {
					int bet = this.players[i].claimBet(j+1);
					this.houseMoney += bet;
				} else if(winner == 2) {
					int bet = this.players[i].claimBet(j+1);
					this.players[i].addWinnings(bet);
				}
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
	
	/*
	 * printPlayerInfo() prints the basic information 
	 * of each player in game.
	 */
	private void printPlayerInfo() {
		for(int i = 0; i < this.numPlayers; i++) {
			System.out.println("*****************************************************");
			System.out.println("Player " + (i+1));
			System.out.println("Money: $" + this.players[i].getMoney());
			System.out.println("Bet: $" + this.players[i].getBet());
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
		flushScreen();
		System.out.println("*****************************************************");
		System.out.println("House money: $" + this.houseMoney);
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
