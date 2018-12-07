package JUnitTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import CardSuite.BlackJack;
import CardSuite.Deck;

class BlackJackTest {
	
	BlackJack b = new BlackJack();
	BlackJack b2 = new BlackJack(123456, 1234, 1, 1, 100);
	
	@Test
	void testGetInfo() {
		BlackJack b2 = new BlackJack(123456, 1234, 1, 1, 100);
		Object[] o = b2.getInfo();
		assertEquals((long)123456, o[1]); //house money
		assertEquals(b2.getHouseMoney(), o[1]);
		assertEquals((long)1234, o[2]); //player money
		assertEquals(b2.getPlayerMoney(), o[2]);
		assertEquals(1, o[4]); //numplayers
		assertEquals(0, o[5]); //the current hand we are on
		assertEquals(100, o[8]); //min bet
		assertEquals(b2.getMinBet(), o[8]);
		assertEquals(1, b2.getNumHands());
		
		// test setDeck() while we are at it
		Deck d = new Deck(false);
		b2.setDeck(d);
		o = b2.getInfo();
		assertEquals(d, o[0]);
	}

	@Test
	void testReshuffle() {
		BlackJack b = new BlackJack();
		assertEquals(false, b.reshuffle());
		b.setHandCount(100);
		assertEquals(true, b.reshuffle());
	}

}
