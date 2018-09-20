package CardSuite;

public class BlackJackBasicAI extends Player {
	BlackJackBasicStrategy bs;
	public BlackJackBasicAI(int num, int money) {
		super(num, money);
		bs = new BlackJackBasicStrategy();
		
	}
	
	@Override 
	public boolean promptToHit(int playerNum, int deck, Card dealerCard) {
		String hit = bs.getDecision(super.getDeck(deck), dealerCard);
		
		if(hit.equals("dh") || hit.equals("rh") || hit.equals("h")) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean promptToSplit(int playerNum, int deck, Card dealerCard) {
		String split = bs.getDecision(super.getDeck(deck), dealerCard);
		
		if(split.equals("p")) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean promptToDouble(int playerNum, int deck, Card dealerCard) {
		String toDouble = bs.getDecision(super.getDeck(deck), dealerCard);
		
		if(toDouble.equals("dh") || toDouble.equals("ds")) {
			return true;
		} else {
			return false;
		}
	}
	
}


