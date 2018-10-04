package CardSuite;
/*
 * This primitive "AI" plays according to 
 * single deck blackjack strategy.
 * Benchmark after simulating one hundred million rounds 
 * of blackjack: EV = -0.054, that is, for every 
 * $100 bet this AI will lose on average $0.54
 * EV updated on 10/02/2018
 */
public class BlackJackBasicAI extends Player {
	BlackJackBasicStrategy bs;
	public BlackJackBasicAI(int num, long money) {
		super(num, money);
		bs = new BlackJackBasicStrategy();
	}
	/*
	 * (non-Javadoc)
	 * @see CardSuite.Player#promptToHit(int, int, CardSuite.Card, CardSuite.Deck)
	 * Checks decision table and returns true if the table indicates to hit
	 * and false otherwise.
	 */
	@Override 
	public boolean promptToHit(int playerNum, int deck, Card dealerCard, Deck pDeck, int count) {
		String hit = bs.getDecision(pDeck, dealerCard);
		if(hit.equals("dh") || hit.equals("rh") || hit.equals("h")) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean promptToSplit(int playerNum, int deck, Card dealerCard, Deck pDeck, int count) {
		String split = bs.getDecision(pDeck, dealerCard);
		if(split.equals("p")) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean promptToDouble(int playerNum, int deck, Card dealerCard, Deck pDeck, int count) {
		String toDouble = bs.getDecision(pDeck, dealerCard);
		if(toDouble.equals("dh") || toDouble.equals("ds")) {	
			return true;
		} else {
			
			return false;
		}
	}
	
	@Override
	public void bustAlert() {
		/*
		 * This function just negates the pause
		 *  after busting so no user interaction
		 *  is required.
		 */
	}
	
	@Override
	public long promptToBet(int playerNum, long money, int count, long minBet) {
		return minBet;
	}
	
	@Override
	public boolean promptInsurance(int count) {
		return false;
	}
	
}


