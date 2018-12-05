package JUnitTests;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import org.junit.jupiter.api.Test;

import CardSuite.Card;
import CardSuite.Deck;

class DeckTest {
	
	//genDeck()
	
	@Test
	void testGenDeck() {
		Deck d = new Deck(false); //calls genDeck
		System.out.println("genDeck()\n" + d.getDeck().toString() + "\ngenDeck()");
		assertEquals(d.size(), 52);
		//assertEquals(d.getDeck().toString(),);
	}
	
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
		Deck d1 = new Deck(false);
		Deck d2 = new Deck(false);
		d2.cutDeck(7);
		assertNotEquals(d2.getDeck().toString(), d1.getDeck().toString());
	}

	@Test
	void testRiffle() {
		Deck d1 = new Deck(false);
		Deck d2 = new Deck(false);
		d2.cutDeck(7);
		assertNotEquals(d2.getDeck().toString(), d1.getDeck().toString());
	}

	@Test
	void testPrintDeck() {
		Deck d1 = new Deck(false);
		System.out.println("printDeck()\n");
		d1.printDeck();
		System.out.println("printDeck()\n");
	}

	@Test
	void testPrintDeckGUI() {
		Deck d1 = new Deck(true);
		d1.addCard(new Card(1,"Clubs", new ImageIcon(Deck.class.getResource("/resources/images/1 of Clubs.jpg")) ));
		d1.addCard(new Card(2,"Diamonds", new ImageIcon(Deck.class.getResource("/resources/images/2 of Diamonds.jpg")) ));
		d1.addCard(new Card(13,"Spades", new ImageIcon(Deck.class.getResource("/resources/images/13 of Spades.jpg")) ));
		d1.addCard(new Card(10,"Hearts", new ImageIcon(Deck.class.getResource("/resources/images/10 of Hearts.jpg")) ));
		assertEquals(" 10 of Hearts King of Spades 2 of Diamonds Ace of Clubs", d1.printDeckGUI());
	}

	@Test
	void testResizeImage() {
		//This is a GUI method.
	}

}
