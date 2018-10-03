package CardSuite;

public class BlackJackHiLo { /*		   Ace 2  3  4  5  6  7  8  9  10 */
	private final int[] countValues = {-1, 1, 1, 1, 1, 1, 0, 0, 0, -1};
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
