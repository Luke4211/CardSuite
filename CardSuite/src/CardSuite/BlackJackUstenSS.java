package CardSuite;

public class BlackJackUstenSS {
       	/*                 		   Ace 2  3  4  5  6  7  8  9  10 */
	private final int[] countValues = {-2, 2, 2, 2, 3, 2, 1, 0, -1, -2};
	private int count;
	public BlackJackHiLo() {
		this.count = 0;
	}
	
	public void count(int card) {
		this.count += countValues[card-1];
	}
	
	public void reset() {
		this.count = 0;
	}
	
	public int getCount() {
		return this.count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
}
