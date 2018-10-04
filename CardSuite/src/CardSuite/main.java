package CardSuite;
import java.io.IOException;
import java.util.ArrayList;

public class main {
	public static void main(String[] args) {
		//BlackJack game = new BlackJack(250000000,250000, 1, 0, 100);
		long houseMoney = 10000000000L;
		BlackJackSimulator game = new BlackJackSimulator(houseMoney, 1000000000L, 3, 1000000000, 100, 4);
		game.game();
		
	}
}