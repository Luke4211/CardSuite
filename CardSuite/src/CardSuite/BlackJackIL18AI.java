package CardSuite;
/*
 * Benchmark after one hundred million
 * hands simulated:
 * EV = +$1.56 (min bet = $100)
 * 
 */
public class BlackJackIL18AI extends BlackJackHiLoAI {
	private Illustrious18 il;
	public BlackJackIL18AI(int num, long money) {
		super(num, money);
		this.il = new Illustrious18();
	}
	
	@Override 
	public boolean promptToHit(int playerNum, int deck, Card dealerCard, Deck pDeck, int count) {
		il.setCount(count);
		String hit = il.getDecision(pDeck, dealerCard);
		if(hit.equals("dh") || hit.equals("rh") || hit.equals("h")) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean promptToSplit(int playerNum, int deck, Card dealerCard, Deck pDeck, int count) {
		il.setCount(count);
		String split = il.getDecision(pDeck, dealerCard);
		if(split.equals("p")) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean promptToDouble(int playerNum, int deck, Card dealerCard, Deck pDeck, int count) {
		il.setCount(count);
		String toDouble = il.getDecision(pDeck, dealerCard);
		if(toDouble.equals("dh") || toDouble.equals("ds")) {	
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean promptInsurance(int count) {
		if(count >= 3) {
			return true;
		} else {
			return false;
		}
	}

}
