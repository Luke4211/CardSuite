package CardSuite;
import java.io.IOException;
import java.util.ArrayList;

public class main {
	
	
	public static void main(String[] args) {
		BlackJack game = new BlackJack(250000000,250000, 2);
		BlackJackBasicStrategy bs = new BlackJackBasicStrategy();
		Card dealerCard = new Card(6, "d");
		Deck deck = new Deck(true);
		Card card = new Card(4, "d");
		deck.addCard(card);
		card = new Card(4, "d");
		deck.addCard(card);
		
		System.out.println(bs.getDecision(deck, dealerCard));
		//System.out.println("pts " + bs.handPts(deck));
		//System.out.println(isSoft(deck) + "");
	}
	

	private static boolean isSoft(Deck deck) {
		BlackJackBasicStrategy bs2 = new BlackJackBasicStrategy();
		int pts = bs2.handPts(deck);
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
}