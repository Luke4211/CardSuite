package CardSuite;
import java.awt.Image;
import java.awt.image.*;
import java.util.Scanner;

public class Card {
	private int value;
	private String suit;
	private Image image;
	
	public Card(int val, String suit) {
		this.value = val;
		this.suit = suit;
		//this.image = img;
		
	}
	
	public String getInfo() {
		String name;
		if(this.value == 1) {
			name = "Ace";
		}
		else if(this.value == 11) {
			name = "Jack";
		}
		else if(this.value == 12) {
			name = "Queen";
		}
		else if(this.value == 13) {
			name = "King";
		}
		else {
			name = value + "";
		}
		
		return (name + " of " + suit);
		
	}
	
	public int getVal() {
		return this.value;
	}
	
	public int getBlackJackVal() {
		if(this.value == 1) {
			return 1;
		} else if(this.value < 10) {
			return this.value;
		} else {
			return 10;
		}
	}
	
	public void printCard() {
		String name;
		if(this.value == 1) {
			name = "Ace";
		}
		else if(this.value == 11) {
			name = "Jack";
		}
		else if(this.value == 12) {
			name = "Queen";
		}
		else if(this.value == 13) {
			name = "King";
		}
		else {
			name = value + "";
		}
		
		System.out.println(name + " of " + suit);
	}
	
	private void loadImg() {
		
	}

}
