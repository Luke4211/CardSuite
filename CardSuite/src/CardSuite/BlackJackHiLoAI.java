package CardSuite;
/*
 * Benchmark after one hundred million
 * hands simulated:
 * EV = +$1.30 ($100 min bet)
 * 
 */
public class BlackJackHiLoAI extends BlackJackBasicAI {
	public BlackJackHiLoAI(int num, long money) {
		super(num, money);
	}
	
	@Override
	public long promptToBet(int playerNum, long money, int count, long minBet) {
		if(count > 2) {
			return ((count - 1)*(minBet));
		} else {
			return minBet;
		}
	}

}
