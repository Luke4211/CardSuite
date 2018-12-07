package JUnitTests;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.ImageIcon;

import org.junit.jupiter.api.Test;

import CardSuite.Card;
import CardSuite.Deck;
import CardSuite.Player;

class PlayerTest {

	Player p = new Player(0,1000);
	Player t = new Player(1, 1000, true, true, 1, false, false, false, false, false, false, false, false);
	
	@Test
	void testAddCard() {
		Player p = new Player(0,1000);
		p.addCard(1, new Card(1,"Clubs", new ImageIcon(Deck.class.getResource("/resources/images/1 of Clubs.jpg"))));
		p.addCard(2, new Card(1,"Hearts", new ImageIcon(Deck.class.getResource("/resources/images/1 of Clubs.jpg"))));
		assertEquals("[Ace of Clubs]", p.getDeck(1).getDeck().toString());
		assertEquals("[Ace of Hearts]", p.getDeck(2).getDeck().toString());
	}

	@Test
	void testBustPlayer() {
		p.bustPlayer(1);
		p.bustPlayer(2);
		assertEquals(true,p.getBust(1));
		assertEquals(true,p.getBust(2));
	}

	@Test
	void testReset() {
		p.reset();
		assertEquals(false, p.getBust(1));
	}

	@Test
	void testIsBusted() {
		Player t = new Player(1, 1000, true, true, 2, true, true, false, false, false, false, false, false);
		assertEquals(true, t.isBusted());
		assertEquals(false, p.isBusted());
	}

	@Test
	void testSplit() {
		Player p = new Player(0, 1000);
		p.bet(100);
		p.addCard(1, new Card(1,"Clubs", new ImageIcon(Deck.class.getResource("/resources/images/1 of Clubs.jpg"))));
		p.addCard(1, new Card(1,"Clubs", new ImageIcon(Deck.class.getResource("/resources/images/1 of Clubs.jpg"))));
		Card[] cards = new Card[2];
		cards[0] = new Card(2,"Clubs", new ImageIcon(Deck.class.getResource("/resources/images/1 of Clubs.jpg")));
		cards[1] = new Card(2,"Hearts", new ImageIcon(Deck.class.getResource("/resources/images/1 of Clubs.jpg")));
		p.split(cards);
		assertEquals(2, p.getNumDecks());
		assertEquals("[2 of Clubs, Ace of Clubs]", p.getDeck(1).getDeck().toString());
		assertEquals("[2 of Hearts, Ace of Clubs]", p.getDeck(2).getDeck().toString());
	}

	@Test
	void testDoubleDown() {
		Player p = new Player(0, 1000);
		p.bet(100);
		p.addCard(1, new Card(1,"Clubs", new ImageIcon(Deck.class.getResource("/resources/images/1 of Clubs.jpg"))));
		p.addCard(1, new Card(1,"Clubs", new ImageIcon(Deck.class.getResource("/resources/images/1 of Clubs.jpg"))));
		Card[] cards = new Card[2];
		cards[0] = new Card(2,"Clubs", new ImageIcon(Deck.class.getResource("/resources/images/1 of Clubs.jpg")));
		cards[1] = new Card(2,"Hearts", new ImageIcon(Deck.class.getResource("/resources/images/1 of Clubs.jpg")));
		p.split(cards);
		p.doubleDown(p.getDeck(1).getCard(0), 1);
		p.doubleDown(p.getDeck(2).getCard(0), 2);
		assertEquals(400, p.getBet());
		assertEquals(600, p.getMoney());
	}


	@Test
	void testBustAlert() {
		//requires user input
	}

	@Test
	void testInsure() {
		Player p = new Player(0, 1000);
		p.bet(100);
		p.insure();
		assertEquals(850, p.getMoney());
	}

	@Test
	void testClaimInsurance() {
		Player p = new Player(0, 1000);
		p.bet(100);
		p.insure();
		assertEquals(50, p.getInsurance());
		assertEquals(true, p.getInsured());
		assertEquals(0, p.claimInsurance(true));
		assertEquals(1000, p.getMoney());
		Player p1 = new Player(0, 1000);
		p1.bet(100);
		p1.insure();
		assertEquals(50, p1.claimInsurance(false));
		assertEquals(850, p1.getMoney());
	}

	@Test
	void testGetInsurance() {
		fail("Not yet implemented");
	}

	@Test
	void testAddMoney() {
		Player p = new Player(1,1000);
		p.addMoney(100);
		assertEquals(1100, p.getMoney());
	}

	@Test
	void testSetStand() {
		Player p = new Player(0, 1000);
		p.setStand(true, 1);
		p.setStand(true, 2);
		assertEquals(true, p.getStanding(1));
		assertEquals(true, p.getStanding(2));
	}

	@Test
	void testAddWinnings() {
		Player p = new Player(1,1000);
		p.addWinnings(100);
		assertEquals(1100, p.getMoney());
	}

	@Test
	void testClaimBet() {
		fail("Not yet implemented");
	}

	@Test
	void testNewDeck() {
		fail("Not yet implemented");
	}

	@Test
	void testGetDeck() {
		fail("Not yet implemented");
	}

}
