package JUnitTests;

import javax.swing.ImageIcon;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import CardSuite.Card;
import CardSuite.Deck;

class CardTest {
	
	ImageIcon img = new ImageIcon(Deck.class.getResource("/resources/images/13 of Hearts.jpg"));
	
	Card c1 = new Card(1,"Hearts", new ImageIcon(Deck.class.getResource("/resources/images/1 of Hearts.jpg")));
	Card c2 = new Card(2,"Hearts", new ImageIcon(Deck.class.getResource("/resources/images/2 of Hearts.jpg")));
	Card c10 = new Card(10,"Hearts", new ImageIcon(Deck.class.getResource("/resources/images/10 of Hearts.jpg")));
	Card cj = new Card(11,"Hearts", new ImageIcon(Deck.class.getResource("/resources/images/11 of Hearts.jpg")));
	Card cq = new Card(12,"Hearts", new ImageIcon(Deck.class.getResource("/resources/images/12 of Hearts.jpg")));
	Card ck = new Card(13,"Hearts", new ImageIcon(Deck.class.getResource("/resources/images/13 of Hearts.jpg")));
	
	//getInfo()
	
	@Test
	void testGetInfoC1() {
		assertEquals("Ace of Hearts",c1.getInfo());
	}
	@Test
	void testGetInfoC2() {
		assertEquals("2 of Hearts",c2.getInfo());
	}
	@Test
	void testGetInfoC10() {
		assertEquals("10 of Hearts",c10.getInfo());	}
	@Test
	void testGetInfoCJ() {
		assertEquals("Jack of Hearts",cj.getInfo());
	}
	@Test
	void testGetInfoCQ() {
		assertEquals("Queen of Hearts",cq.getInfo());
	}
	@Test
	void testGetInfoCK() {
		assertEquals("King of Hearts",ck.getInfo());
	}
	
	//printCardGUI()
	@Test
	void testPrintCardGUIC1() {
		assertEquals("Ace of Hearts",c1.printCardGUI());
	}
	@Test
	void testPrintCardGUIC2() {
		assertEquals("2 of Hearts",c2.printCardGUI());
	}
	@Test
	void testPrintCardGUIC10() {
		assertEquals("10 of Hearts",c10.printCardGUI());	}
	@Test
	void testPrintCardGUICJ() {
		assertEquals("Jack of Hearts",cj.printCardGUI());
	}
	@Test
	void testPrintCardGUICQ() {
		assertEquals("Queen of Hearts",cq.printCardGUI());
	}
	@Test
	void testPrintCardGUICK() {
		assertEquals("King of Hearts",ck.printCardGUI());
	}

	//toString()
	
	@Test
	void testToStringC1() {
		assertEquals("Ace of Hearts",c1.toString());
	}
	@Test
	void testToStringC2() {
		assertEquals("2 of Hearts",c2.toString());
	}
	@Test
	void testToStringC10() {
		assertEquals("10 of Hearts",c10.toString());	}
	@Test
	void testToStringCJ() {
		assertEquals("Jack of Hearts",cj.toString());
	}
	@Test
	void testToStringCQ() {
		assertEquals("Queen of Hearts",cq.toString());
	}
	@Test
	void testToStringCK() {
		assertEquals("King of Hearts",ck.toString());
	}

	//printCard()
	@Test
	void testPrintCardC1() {
		c1.printCard();
	}
	@Test
	void testPrintCardC2() {
		c2.printCard();

	}
	@Test
	void testPrintCardC10() {
		c10.printCard();

	}
	@Test
	void testPrintCardCJ() {
		cj.printCard();

	}
	@Test
	void testPrintCardCQ() {
		cq.printCard();

	}
	@Test
	void testPrintCardCK() {
		ck.printCard();

	}
	
	//getVal()
	
	@Test
	void testGetVal() {
		assertEquals(11,cj.getVal());
	}

	//getBlackJackVal()
	
	@Test
	void testGetBlackJackValC1() {
		assertEquals(1,c1.getBlackJackVal());
	}
	
	@Test
	void testGetBlackJackValC2() {
		assertEquals(2,c2.getBlackJackVal());
	}
	
	@Test
	void testGetBlackJackValC10() {
		assertEquals(10,c10.getBlackJackVal());
	}
	
	@Test
	void testGetBlackJackValCJ() {
		assertEquals(10,cj.getBlackJackVal());
	}
	
	@Test
	void testGetBlackJackValCQ() {
		assertEquals(10,cq.getBlackJackVal());
	}
	
	@Test
	void testGetBlackJackValCK() {
		assertEquals(10,ck.getBlackJackVal());
	}
	
	//getImg()
	@Test
	void testGetImgCK() {
		assertEquals(img.getImage(),ck.getImg().getImage());
	}
}
