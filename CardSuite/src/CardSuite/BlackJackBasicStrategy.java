package CardSuite;

import java.util.ArrayList;
/*
 * A class which calculates the mathematically
 * best choice for each hand and dealer top card.
 * Written by Lucas Gorski
 */
public class BlackJackBasicStrategy {
	/*
	 * These decision tables give the mathematically
	 * best move depending on the player's hand and
	 * dealer's top card. Each row represents
	 * the player's hand, and columns represent the
	 * dealer's card. Tables created based off information from
	 * https://wizardofodds.com/games/blackjack/strategy/1-deck/
	 * ********************************************
	 *                    Key                     *
	 * h = hit                                    *                   
	 * s = stand                                  *
	 * dh = double if allowed, hit otherwise      *
	 * ds = double if allowed, stand otherwise    *
	 * p = split                                  *
	 * rh = surrender if allowed, otherwise hit   *
	 * rs = surrender if allowed, otherwise stand *
	 * ********************************************
	 */
	
	private final String[][] hardDecisionTable = {
	/* Hands:                         2     3     4     5     6     7     8     9     10    A  */
	/*4-7 */			{"h",  "h",  "h",  "h",  "h",  "h",  "h",  "h",  "h",  "h"  },
	/* 8  */			{"h",  "h",  "h",  "dh", "dh", "h",  "h",  "h",  "h",  "h"  },
	/* 9  */			{"dh", "dh", "dh", "dh", "dh", "h",  "h",  "h",  "h",  "h"  },
	/* 10 */			{"dh", "dh", "dh", "dh", "dh", "dh", "dh", "dh", "h",  "h"  },
	/* 11 */			{"dh", "dh", "dh", "dh", "dh", "dh", "dh", "dh", "dh", "dh" },
	/* 12 */			{"h",  "h",  "s",  "s",  "s",   "h",  "h",  "h",  "h",  "h" },
	/* 13 */			{"s",  "s",  "s",  "s",  "s",   "h",  "h",  "h",  "h",  "h" },
	/* 14 */			{"s",  "s",  "s",  "s",  "s",   "h",  "h",  "h",  "h",  "h" },
	/* 15 */			{"s",  "s",  "s",  "s",  "s",   "h",  "h",  "h",  "h",  "rh"},
	/* 16 */			{"s",  "s",  "s",  "s",  "s",   "h",  "h",  "h",  "rh", "rh"},
	/* 17 */			{"s",  "s",  "s",  "s",  "s",   "s",  "s",  "s",  "s",  "rs"},
	/* 18+*/			{"s",  "s",  "s",  "s",  "s",   "s",  "s",  "s",  "s",  "s" },	
	};
	/*
	 * 
	 */
	
	private final String[][] softDecisionTable = {
	/* Hands:                         2     3     4     5     6     7     8     9     10    A  */
	/* 13 */			{"h" , "h" , "dh", "dh", "dh", "h",  "h",  "h",  "h",  "h" },
	/* 14 */			{"h" , "h" , "dh", "dh", "dh", "h",  "h",  "h",  "h",  "h" },
	/* 15 */			{"h" , "h" , "dh", "dh", "dh", "h",  "h",  "h",  "h",  "h" },
	/* 16 */			{"h" , "h" , "dh", "dh", "dh", "h",  "h",  "h",  "h",  "h" },
	/* 17 */			{"dh", "dh", "dh", "dh", "dh", "h",  "h",  "h",  "h",  "h" },
	/* 18 */			{"s",  "ds", "ds", "ds", "ds", "s",  "s",  "h",  "h",  "h" },
	/* 19 */			{"s",  "s",  "s",  "s",  "ds", "s",  "s",  "s",  "s",  "s" },
	/* 20+*/			{"s",  "s",  "s",  "s",  "s",  "s",  "s",  "s",  "s",  "s" },
	};
	
	private final String[][] splitDecisionTable = {
	/* Hands:                         2     3     4     5     6     7     8     9     10    A  */
	/*2's*/   			{"p",  "p",  "p",  "p",  "p",  "p",  "h",  "h",  "h",  "h" },
	/*3's*/				{"p",  "p",  "p",  "p",  "p",  "p",  "p",  "h",  "h",  "h" },
	/*4's*/				{"h",  "h",  "p",  "p",  "p",  "h",  "h",  "h",  "h",  "h" },
	/*6's*/				{"p",  "p",  "p",  "p",  "p",  "p",  "h",  "h",  "h",  "h" },
	/*7's*/				{"p",  "p",  "p",  "p",  "p",  "p",  "p",  "h",  "rs", "rh"},
	/*8's*/				{"p",  "p",  "p",  "p",  "p",  "p",  "p",  "p",  "p",  "p" },
	/*9's*/				{"p",  "p",  "p",  "p",  "p",  "s",  "h",  "h",  "s",  "p" },
	/*A's*/				{"p",  "p",  "p",  "p",  "p",  "p",  "p",  "p",  "p",  "p" },
	};
	
	public BlackJackBasicStrategy() {
		
	}
	
	/*
	 * getDecision() returns the optimal decision 
	 * based on the player's hand and dealer's top
	 * card
	 */
	public String getDecision(Deck deck, Card card) {
		int pts = this.handPts(deck);
		int row;
		int col = this.getCol(card);
		boolean split = this.isSplit(deck);
		boolean soft = this.isSoft(deck);	
		Card top = deck.getCard(0);
		
		if(split && top.getBlackJackVal() != 10) {
			row = this.getSplitRow(top);
			return this.splitDecisionTable[row][col];
			
		} else if(soft) {
			row = this.getSoftRow(pts);
			return this.softDecisionTable[row][col];
		} else {
			
			row = this.getHardRow(pts);
			return this.hardDecisionTable[row][col];
		}
		
	}
	/*
	 * handPts() calculates the point value of the passed hand
	 */
	public int handPts(Deck hand) {
		int sum = 0;
		boolean val10 = false;
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
	 * isSoft(deck) returns true if the hand contains
	 * and ace valued and 10, and false otherwise.
	 */
	protected boolean isSoft(Deck deck) {
		int pts = this.handPts(deck);
		int sum = 0;		
		for(int i = 0; i <deck.size(); i++) {
			sum+=deck.getCard(i).getBlackJackVal();
		}
		if(sum != pts) {
			return true;
		} else {
			return false;
		}
	}
	/*
	 * isSplit(deck) returns true if the hand contains
	 * only 2 cards which have the same value, and 
	 * false otherwise.
	 */
	protected boolean isSplit(Deck deck) {
		if(deck.size() == 2) {
			Card first = deck.getCard(0);
			Card sec = deck.getCard(1);
			if(first.getVal() == sec.getVal() 
					&& first.getVal() != 10
					&& first.getVal() != 5 ) {
				return true;
			}
		}
		
		return false;
	}
	/*
	 * getHardRow(int) returns the row 
	 * number for the decision table for
	 * a hard hand (no ace valued at 10)
	 */
	protected int getHardRow(int pts) {
		if(pts >= 4 && pts <= 7) {
			return 0;
		} else if(pts >= 18) {
			return 11;
		}
		return (pts - 7);
	}
	/*
	 * getSoftRow(int) returns the row
	 * number for the decision table for
	 * a soft hand
	 */
	protected int getSoftRow(int pts) {
		if(pts >= 20) {
			return 7;
		}
		
		return (pts - 13);
	}
	/*
	 * getSplitRow(card) returns the row
	 * number for the decision table for
	 * a split hand.
	 */
	protected int getSplitRow(Card card) {
		int cardVal = card.getBlackJackVal();
		if(cardVal >= 2 && cardVal <= 4 ) {
			return (cardVal - 2);
		} else if(cardVal >= 6 && cardVal <= 9) {
			return (cardVal - 3);
		} 
		return 7;
	}
	
	/*
	 * getCol(Card) returns the column
	 * number for the given card.
	 */
	protected int getCol(Card card) {
		int val = card.getBlackJackVal();
		if(val == 1) {
			return 9;
		}
		
		return (val - 2);
	}

}
