package CardSuite;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JMenuBar;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Component;
import java.awt.Button;
import java.awt.Canvas;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import java.awt.Font;
import javax.swing.JLayeredPane;

public class GUI extends JPanel{


	//board
	private JFrame frame;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private BlackJack game;
//	private BlackJackHand hand;
	private BlackjackHandGUI hand;
	
	private int currentPlayer;
	
	private JButton btnStand;
	private JLabel lblHand;
	private JLabel lblHand_1;
	private JLabel lblHand_2;
	private JLabel lblHand_3;
	private JLabel lblDealerHand;
	private JLabel lblBalance;
	private JButton btnDouble;
	private JButton btnSplit;
	private JButton btnHit;
	private JButton btnBet;
	private JButton btnGetInsurance;
	private JButton btnSurrender;
	private JButton btnNextHand;
	private JLabel lblMaxBet;
	private JLabel lblCurrentBet;
	private JLayeredPane board;
	private JSlider slider;
	private JRadioButton rd1,rd2,rd3,rd4;
	
	
	//card slots
	private JLabel[] playerSlot;
	private JLabel[] splitSlot;// = new JLabel[10];
	private JLabel[] dealerSlot;// = new JLabel[10];
	//private MainMenu menu;
	
	private JLayeredPane mainMenu;
	private JTextField fieldNumPlayers;
	private JTextField fieldDealerMoney;
	private JTextField fieldPlayerMoney;
	private JTextField fieldMinBet;
	private Button start;
	
	
	//game info
	private JLabel lblNumPlayers;
	private JLabel lblDealerMoney;
	private JLabel lblPlayerMoney;
	private JLabel lblMinBet;
	private long dealerMoney;
	private long playerMoney;
	private int numPlayers;
	private int minBet;
	private int numHands=1000;
	private JLabel lblYourHand;
	private JLabel lblSplitHand;
	private JLabel lblDealerHand_1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
		
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(){	
		game = new BlackJack();
		//game = new BlackJack(dealerMoney, playerMoney, numPlayers, 10000, minBet);
		hand = new BlackjackHandGUI(game.getInfo());
//		hand = new BlackJackHand(game.getInfo());
		
		
		
		frame = new JFrame();
		frame.setBounds(100, 100, 872, 567);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);	
		
		board = new JLayeredPane();
		board.setOpaque(true);
		board.setBackground(new Color(34,150,34));
		board.setBounds(0, -31, 850, 511);

		
		mainMenu = new JLayeredPane();
		mainMenu.setBounds(0, 0, 850, 511);
		frame.getContentPane().add(mainMenu);
		
		//betting slider
		slider = new JSlider();
		slider.setToolTipText("");
		slider.setMajorTickSpacing(1000);
		slider.setBounds(32, 404, 213, 54);
		board.add(slider);		
		lblMinBet = new JLabel("Min Bet: ");
		lblMinBet.setBounds(32, 390, 89, 14);
		board.add(lblMinBet);		
		lblMaxBet = new JLabel("Max Bet: " + hand.getPlayers(currentPlayer).getMoney());
		lblMaxBet.setBounds(159, 390, 130, 14);
		board.add(lblMaxBet);
		
		
		ChangeListener listener = new ChangeListener()
        {
           public void stateChanged(ChangeEvent event)
           {
              // update text field when the slider value changes
              JSlider source = (JSlider) event.getSource();
              lblCurrentBet.setText("Current Bet: " + source.getValue());
           }
        };
        
        slider.addChangeListener(listener);
		
		
		//Text version
//		lblHand = new JLabel("Hand 1:");
//		lblHand.setFont(new Font("Tahoma", Font.PLAIN, 11));
//		lblHand.setBounds(32, 32, 437, 54);
//		board.add(lblHand);		
//		lblHand_1 = new JLabel("Hand 2:");
//		lblHand_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
//		lblHand_1.setBounds(32, 97, 437, 54);
//		board.add(lblHand_1);		
//		lblHand_2 = new JLabel("Hand 3:");
//		lblHand_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
//		lblHand_2.setBounds(32, 162, 437, 54);
//		board.add(lblHand_2);		
//		lblHand_3 = new JLabel("Hand 4:");
//		lblHand_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
//		lblHand_3.setBounds(32, 227, 437, 54);
//		board.add(lblHand_3);		
//		lblDealerHand = new JLabel("Dealer Hand:");
//		lblDealerHand.setFont(new Font("Tahoma", Font.PLAIN, 11));
//		lblDealerHand.setBounds(485, 142, 339, 54);
//		board.add(lblDealerHand);
		
				
		
		//select hand to operate on
		rd1 = new JRadioButton("1");
		rd1.setBounds(629, 417, 39, 23);
		board.add(rd1);
		buttonGroup.add(rd1);
		rd1.setSelected(true);
		rd2 = new JRadioButton("2");
		rd2.setBounds(670, 417, 39, 23);
		board.add(rd2);
		buttonGroup.add(rd2);
		rd2.setEnabled(false);
		rd3 = new JRadioButton("3");
		rd3.setBounds(711, 417, 39, 23);
		board.add(rd3);
		buttonGroup.add(rd3);
		rd3.setEnabled(false);
		rd4 = new JRadioButton("4");
		rd4.setBounds(752, 417, 39, 23);
		board.add(rd4);
		buttonGroup.add(rd4);
		rd4.setEnabled(false);
		
		//Player Money Balance Label
		lblBalance = new JLabel("Balance:" + hand.getPlayers(currentPlayer).getMoney());
		lblBalance.setBounds(32, 469, 99, 14);
		board.add(lblBalance);
		
		//Split Button
		//cant currently split more than once
		btnSplit = new JButton("Split");
		btnSplit.setEnabled(false);
		btnSplit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				Card[] cards = new Card[2];
				Card card = hand.drawCard();
				cards[0] = card;
				card = hand.drawCard();
				cards[1] = card;
				hand.getPlayers(currentPlayer).split(cards);
				
//				lblHand.setText(hand.getPlayers(currentPlayer).getDeck(1).printDeckGUI() +" Value:" +  hand.handPts(hand.getPlayers(currentPlayer).getDeck(1)));
//				lblHand_1.setText(hand.getPlayers(currentPlayer).getDeck(2).printDeckGUI() +" Value:" +  hand.handPts(hand.getPlayers(currentPlayer).getDeck(2)));
				drawPlayerHand();
				drawSplitHand();				
				btnSplit.setEnabled(false);
				
				if(hand.getPlayers(currentPlayer).getNumDecks()==2) {
					rd2.setEnabled(true);
				}else if(hand.getPlayers(currentPlayer).getNumDecks()==3) {
					rd3.setEnabled(true);
				}else if(hand.getPlayers(currentPlayer).getNumDecks()==4) {
					rd4.setEnabled(true);
				}
					
			}
		});
		btnSplit.setBounds(516, 453, 99, 43);
		board.add(btnSplit);
		
		//Double Down Button
		btnDouble = new JButton("Double");
		btnDouble.setEnabled(false);
		btnDouble.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Card card = hand.drawCard();
				if(rd1.isSelected()) {					
					doubleDown(card, 1);
//					lblHand.setText(hand.getPlayers(currentPlayer).getDeck(1).printDeckGUI() +" Value:" +  hand.handPts(hand.getPlayers(currentPlayer).getDeck(1)));
				}else if(rd2.isSelected()){
					doubleDown(card, 2);
//					lblHand_1.setText(hand.getPlayers(currentPlayer).getDeck(2).printDeckGUI() +" Value:" +  hand.handPts(hand.getPlayers(currentPlayer).getDeck(2)));
				}else if(rd3.isSelected()) {
					doubleDown(card, 3);
//					lblHand_2.setText(hand.getPlayers(currentPlayer).getDeck(3).printDeckGUI() +" Value:" +  hand.handPts(hand.getPlayers(currentPlayer).getDeck(3)));
				}else if(rd4.isSelected()) {
					doubleDown(card, 4);
//					lblHand_3.setText(hand.getPlayers(currentPlayer).getDeck(4).printDeckGUI() +" Value:" +  hand.handPts(hand.getPlayers(currentPlayer).getDeck(4)));
				}else {
					JOptionPane.showMessageDialog(frame,
						    "Choose a deck to double down.");
				}
				end();
			}
		});
		btnDouble.setBounds(421, 453, 89, 42);
		board.add(btnDouble);
		
		//Hit Button
		btnHit = new JButton("Hit");
		btnHit.setEnabled(false);
		btnHit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rd1.isSelected()) {
					hand.hit(currentPlayer, 1);
					drawPlayerHand();
//					lblHand.setText(hand.getPlayers(currentPlayer).getDeck(1).printDeckGUI() +" Value:" +  hand.handPts(hand.getPlayers(currentPlayer).getDeck(1)));
				}else if(rd2.isSelected()){
					hand.hit(currentPlayer, 2);
					drawSplitHand();
//					lblHand_1.setText(hand.getPlayers(currentPlayer).getDeck(2).printDeckGUI() +" Value:" +  hand.handPts(hand.getPlayers(currentPlayer).getDeck(2)));
				}else if(rd3.isSelected()) {
					hand.hit(currentPlayer, 3);
//					lblHand_2.setText(hand.getPlayers(currentPlayer).getDeck(3).printDeckGUI() +" Value:" +  hand.handPts(hand.getPlayers(currentPlayer).getDeck(3)));
				}else if(rd4.isSelected()){
					hand.hit(currentPlayer, 4);
//					lblHand_3.setText(hand.getPlayers(currentPlayer).getDeck(4).printDeckGUI() +" Value:" +  hand.handPts(hand.getPlayers(currentPlayer).getDeck(4)));
				}else {
					JOptionPane.showMessageDialog(frame,
						    "Choose a deck to hit.");
				}
				if(hand.bust(hand.getPlayers(currentPlayer).getDeck(1)) || hand.bust(hand.getPlayers(currentPlayer).getDeck(2))){
					JOptionPane.showMessageDialog(frame, "Deck 1 busted.");
					end();
				}
			}
		});
		btnHit.setBounds(733, 453, 99, 43);
		board.add(btnHit);
		
		//Stand Button
		btnStand = new JButton("Stand");
		btnStand.setEnabled(false);
		btnStand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				end();
			}
		});
		btnStand.setBounds(625, 453, 99, 43);
		board.add(btnStand);
		
		//betting
		btnBet = new JButton("Bet");
		btnBet.setEnabled(false);
		btnBet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(hand.getPlayers(currentPlayer).getMoney()<game.getMinBet()) {
					JOptionPane.showMessageDialog(frame,"You're out of money.");
				}else {					
				hand.getPlayers(currentPlayer).bet(slider.getValue());
				lblBalance.setText("Balance:" + hand.getPlayers(currentPlayer).getMoney());
				btnBet.setEnabled(false);
				
				//begin the next hand
				hand.initGUI();
//				hand.getPlayers(currentPlayer).getDeck(1).printDeck();
				drawPlayerHand();
				drawDealerHand();
				
				
//				lblHand.setText(hand.getPlayers(currentPlayer).getDeck(1).printDeckGUI() +" Value:" +  hand.handPts(hand.getPlayers(currentPlayer).getDeck(1)));
//				lblDealerHand.setText("Dealer Top Card: " + hand.printDealerCard());
				
				if(hand.canSplit(hand.getPlayers(currentPlayer).getDeck(1)))
					btnSplit.setEnabled(true);
				else
					btnSplit.setEnabled(false);
				btnStand.setEnabled(true);
				btnHit.setEnabled(true);
				btnDouble.setEnabled(true);
				rd1.setEnabled(true);
				}
			}
		});
		btnBet.setBounds(93, 356, 89, 23);
		board.add(btnBet);
		
		/*Get insurance button*/
		btnGetInsurance = new JButton("Get Insurance");
		btnGetInsurance.setEnabled(false);
		btnGetInsurance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//add functionality
			}
		});
		btnGetInsurance.setBounds(281, 449, 130, 23);
		board.add(btnGetInsurance);
		
		
		/*Surrender button */
		btnSurrender = new JButton("Surrender");
		btnSurrender.setEnabled(false);
		btnSurrender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//add functionality;
			}
		});
		btnSurrender.setBounds(281, 473, 130, 23);
		board.add(btnSurrender);
		
		//Next Hand Button
		btnNextHand = new JButton("Next Hand");
		btnNextHand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								
				btnBet.setEnabled(true);
				slider.setMinimum(minBet);
				slider.setMaximum((int)(hand.getPlayers(currentPlayer).getMoney()));
				
				resetLabels();
				resetCardSlots();
				btnNextHand.setEnabled(false);
			}
		});
		btnNextHand.setBounds(303, 404, 193, 36);
		board.add(btnNextHand);
		
		JLabel lblChooseHand = new JLabel("Choose Hand:");
		lblChooseHand.setBounds(534, 421, 89, 14);
		board.add(lblChooseHand);
		
		lblCurrentBet = new JLabel("Current bet: " + hand.getPlayers(currentPlayer).getBet());
		lblCurrentBet.setBounds(141, 469, 130, 14);
		board.add(lblCurrentBet);	
		
		lblYourHand = new JLabel("Player " + (currentPlayer + 1) + " | Value: ");		
		lblYourHand.setBounds(240, 348, 218, 14);
		board.add(lblYourHand);		
		lblSplitHand = new JLabel("");
		lblSplitHand.setBounds(440, 348, 2, 14);
		board.add(lblSplitHand);
		

		lblDealerHand_1 = new JLabel("Dealer Hand | Value: ");
		lblDealerHand_1.setBounds(534, 165, 190, 14);
		board.add(lblDealerHand_1);

		
		
		/********MENU ITEMS***********/
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnGame = new JMenu("Game");
		menuBar.add(mnGame);
		
		JMenuItem mntmNewGame = new JMenuItem("Restart Game");
		mnGame.add(mntmNewGame);
		mntmNewGame.setEnabled(false);
		mntmNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newGame();
			}
		});
		
		JMenuItem mntmMainMenu = new JMenuItem("Main Menu");
		mnGame.add(mntmMainMenu);
		mntmMainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.remove(board);
				frame.getContentPane().add(mainMenu);				
				frame.validate();
	            frame.repaint();
	            mntmNewGame.setEnabled(false);
			}
		});	
		
		
		/*********DRAW MAIN MENU**************/		
		
		start = new Button("Start Game");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				numPlayers = Integer.parseInt(fieldNumPlayers.getText());
				dealerMoney = Integer.parseInt(fieldDealerMoney.getText());
				playerMoney = Integer.parseInt(fieldPlayerMoney.getText());
				minBet = Integer.parseInt(fieldMinBet.getText());
				newGame();
				mntmNewGame.setEnabled(true);
			}
		});
		start.setFont(new Font("Dialog", Font.PLAIN, 27));
		start.setBounds(255, 382, 330, 71);
		mainMenu.add(start);
		
		fieldNumPlayers = new JTextField("1");
		fieldNumPlayers.setBounds(515, 99, 146, 26);
		mainMenu.add(fieldNumPlayers);
		fieldNumPlayers.setColumns(10);
		
		fieldDealerMoney = new JTextField("1000000");
		fieldDealerMoney.setColumns(10);
		fieldDealerMoney.setBounds(515, 141, 146, 26);
		mainMenu.add(fieldDealerMoney);
		
		fieldPlayerMoney = new JTextField("5000");
		fieldPlayerMoney.setColumns(10);
		fieldPlayerMoney.setBounds(515, 186, 146, 26);
		mainMenu.add(fieldPlayerMoney);
		
		fieldMinBet = new JTextField("500");
		fieldMinBet.setColumns(10);
		fieldMinBet.setBounds(515, 228, 146, 26);
		mainMenu.add(fieldMinBet);
		
		
		lblNumPlayers = new JLabel("Enter Number of Players:");
		lblNumPlayers.setBounds(308, 101, 207, 23);
		mainMenu.add(lblNumPlayers);
		
		lblDealerMoney = new JLabel("Enter Dealer Money:");
		lblDealerMoney.setBounds(308, 144, 207, 23);
		mainMenu.add(lblDealerMoney);
		
		lblPlayerMoney = new JLabel("Enter Player Money:");
		lblPlayerMoney.setBounds(308, 189, 207, 23);
		mainMenu.add(lblPlayerMoney);
		
		lblMinBet = new JLabel("Enter the Minimum Bet:");
		lblMinBet.setBounds(308, 231, 207, 23);
		mainMenu.add(lblMinBet);
		
		createCardSlots();
	}
	
	private void doubleDown(Card card, int deck){
		hand.getPlayers(currentPlayer).doubleDown(card, deck);
		hand.getPlayers(currentPlayer).showDouble();
	}
	
	
	
	private void createCardSlots(){
		playerSlot=new JLabel[10];
		dealerSlot=new JLabel[10];
		splitSlot=new JLabel[10];
		int x=0;		
        for (int i=0;i<10;i++){        	
        	playerSlot[i]=new JLabel();
        	playerSlot[i].setBounds(250+x,230,70,110);        	
        	dealerSlot[i]=new JLabel();
        	dealerSlot[i].setBounds(550+x,50,70,110);        	
        	splitSlot[i]=new JLabel();
        	splitSlot[i].setBounds(450+x,230,70,110);
        	
        	board.add(playerSlot[i]);
        	board.moveToFront(playerSlot[i]);        	
        	board.add(dealerSlot[i]);
        	board.moveToFront(dealerSlot[i]);        	
        	board.add(splitSlot[i]);
        	board.moveToFront(splitSlot[i]);
        	x+=25;
        }
    }
	
	private void resetCardSlots() {
		for(int i=0;i<playerSlot.length;i++) {
			playerSlot[i].setIcon(null);			
			playerSlot[i].revalidate();
			
			dealerSlot[i].setIcon(null);
			dealerSlot[i].revalidate();	
			
			splitSlot[i].setIcon(null);
			splitSlot[i].revalidate();
		}
	}
	
    private void drawPlayerHand(){
    	for(int i=0;i<hand.getPlayers(currentPlayer).getDeck(1).size();i++){
    		playerSlot[i].setIcon(hand.getPlayers(currentPlayer).getDeck(1).getCard(i).getImg());
    		lblYourHand.setText("Player " + (currentPlayer + 1) + "Hand 1 | Value: " + hand.handPts(hand.getPlayers(currentPlayer).getDeck(1)));
    	}
    }
    
    private void drawSplitHand(){
    	for(int i=0;i<hand.getPlayers(currentPlayer).getDeck(2).size();i++){
    		splitSlot[i].setIcon(hand.getPlayers(currentPlayer).getDeck(2).getCard(i).getImg());
    		lblSplitHand.setText("Player " + (currentPlayer + 1) + "Hand 2 | Value: " + hand.handPts(hand.getPlayers(currentPlayer).getDeck(2)));
    	}
    }
    
    private void drawDealerHand() {
    	if(hand.getHandOver()==false) {
    		if(hand.getDealerCard()==1) {
    			lblDealerHand_1.setText("Dealer Hand | Value: 1 or 11");
    		}else if(hand.getDealerCard()>=10){
    			lblDealerHand_1.setText("Dealer Hand | Value: 10");
    		}else {
    			lblDealerHand_1.setText("Dealer Hand | Value: " + hand.getDealerCard());
    		}
    		dealerSlot[0].setIcon(hand.getDealerDeck().getCard(0).getImg());
    		dealerSlot[1].setIcon(new ImageIcon(GUI.class.getResource("/resources/images/purple_back.jpg")));
    	}else {
    		for(int i=0;i<hand.getDealerDeck().size();i++){
    			dealerSlot[i].setIcon(hand.getDealerDeck().getCard(i).getImg());
    			lblDealerHand_1.setText("Dealer Hand | Value: " + hand.handPts(hand.getDealerDeck()));
    		}
    	}
    }
    
//	lblNewLabel_2 = new JLabel("New label");
//	lblNewLabel_2.setIcon(new ImageIcon(GUI.class.getResource("/resources/images/purple_back.jpg")));
//	lblNewLabel_2.setBounds(550, 50, 70, 110);
//	board.add(lblNewLabel_2);
    
	
	//newGame() starts a new game with the same game settings
	private void newGame() {
		//game = new BlackJack();
		game = new BlackJack(dealerMoney, playerMoney, numPlayers, numHands, minBet);
		hand = new BlackjackHandGUI(game.getInfo());
//		hand = new BlackJackHand(game.getInfo());
		currentPlayer=0;
		frame.remove(mainMenu);
		frame.getContentPane().add(board);
		frame.validate();
        frame.repaint();
		resetButtons();		
		resetLabels();		
	}
	
	
	
	private void resetButtons() {
		btnNextHand.setEnabled(true);
		btnSurrender.setEnabled(false);
		btnGetInsurance.setEnabled(false);
		btnHit.setEnabled(false);
		btnDouble.setEnabled(false);
		btnSplit.setEnabled(false);
		btnBet.setEnabled(false);
		btnStand.setEnabled(false);
	}
	private void resetLabels() {
//		lblHand.setText("Hand 1: ");
//		lblHand_1.setText("Hand 2: ");
//		lblHand_2.setText("Hand 3: ");
//		lblHand_3.setText("Hand 4: ");
//		lblDealerHand.setText("Dealer top card: ");
		lblBalance.setText("Balance:" + hand.getPlayers(currentPlayer).getMoney());
		lblMinBet.setText("Min Bet: " + minBet);
		lblMaxBet.setText("Max Bet: " + hand.getPlayers(currentPlayer).getMoney());
		lblYourHand.setText("Player " + (currentPlayer + 1) + " | Value: ");
		lblYourHand.setText("");
		lblDealerHand_1.setText("Dealer Hand | Value: ");
		resetCardSlots();
		rd1.setSelected(true);
		hand.setHandOver(false);		
	}
	
	private void end() {
		hand.hitUntil17GUI();//player is winning when dealer's original hand is >= 17 and < 21 and dealer hand is > player hand
		drawPlayerHand();
		drawDealerHand();
		System.out.println(hand.handPts(hand.getDealerDeck()));
		if(hand.winner(hand.getPlayers(currentPlayer).getDeck(1),hand.getDealerDeck())==0) {
			JOptionPane.showMessageDialog(frame,"You won! Payout is: "+hand.getPlayers(currentPlayer).getBet()*2);					
		}else if(hand.winner(hand.getPlayers(currentPlayer).getDeck(1),hand.getDealerDeck())==1) {
			JOptionPane.showMessageDialog(frame,"You lost");
		}else if(hand.winner(hand.getPlayers(currentPlayer).getDeck(1),hand.getDealerDeck())==2) {
			JOptionPane.showMessageDialog(frame,"You tied");
		}
//		lblDealerHand.setText("Dealer Top Card: " + hand.handPts(hand.getDealerDeck()) + ":  " + hand.printDealerDeck());
		hand.cleanUp(); 				
		lblBalance.setText("Balance:" + hand.getPlayers(currentPlayer).getMoney());
		lblMaxBet.setText("Max Bet: " + hand.getPlayers(currentPlayer).getMoney());
		resetButtons();
		currentPlayer++;
		if(currentPlayer>this.numPlayers-1){
			currentPlayer = 0;
		}

	}
}





