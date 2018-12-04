package JUnitTests;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import org.junit.jupiter.api.Test;

import CardSuite.Card;
import CardSuite.Deck;

class DeckTest {
	
	//Deck()
	
	@Test
	void testDeckEmpty() {
		Deck empty = new Deck(true);
		assertEquals(null, empty.getCard(0));
	}

	@Test
	void testDeckNotEmpty() {
		Deck d = new Deck(false);
		Card c = d.getCard(0);
		assertEquals("Ace of Diamonds", c.getInfo());
	}

	//top()
	
	@Test
	void testTop() {
		Deck d = new Deck(false);
		assertEquals("Ace of Diamonds", d.top().getInfo());
	}

	//size()

	@Test
	void testSizeFull() {
		Deck d = new Deck(false);
		assertEquals(52, d.size());
	}
	@Test
	void testSizeEmpty() {
		Deck d = new Deck(true);
		assertEquals(0, d.size());
	}

	//getCard()
	
	@Test
	void testGetCard() {
		Deck d = new Deck(false);
		Card c = d.getCard(0);
		assertEquals("Ace of Diamonds", c.getInfo());
	}

	@Test
	void testAddCard() {
		Deck d = new Deck(false);
		d.top(); //remove top card, and replace with something Ace of Clubs
		d.addCard(new Card(1,"Clubs", new ImageIcon(Deck.class.getResource("/resources/images/1 of Clubs.jpg")) ));
		assertEquals("Ace of Clubs", d.getCard(0).getInfo());
	}

	@Test
	void testAddDeck() {
		Deck d1 = new Deck(true);
		d1.addCard(new Card(1,"Clubs", new ImageIcon(Deck.class.getResource("/resources/images/1 of Clubs.jpg")) ));
		Deck d2 = new Deck(true);
		d2.addCard(new Card(1,"Hearts", new ImageIcon(Deck.class.getResource("/resources/images/1 of Hearts.jpg")) ));
		d1.addDeck(d2);
		assertEquals("Ace of Hearts", d1.getCard(1).getInfo());
	}

	@Test
	void testShuffle() {
		Deck d1 = new Deck(false);
		Deck d2 = new Deck(false);
		d2.shuffle();
		assertNotEquals(d2.getDeck().toString(), d1.getDeck().toString());
	}

	@Test
	void testCutDeck() {
		fail("Not yet implemented");
	}

	@Test
	void testRiffle() {
		fail("Not yet implemented");
	}

	@Test
	void testPrintDeck() {
		fail("Not yet implemented");
	}

	@Test
	void testPrintDeckGUI() {
		fail("Not yet implemented");
	}

	@Test
	void testResizeImage() {
		fail("Not yet implemented");
	}

}
