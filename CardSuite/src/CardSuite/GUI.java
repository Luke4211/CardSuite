package CardSuite;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

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
import java.awt.Dimension;
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
	
	private Dimension screenSize;
	private int width;
	private int height;
	private int simType = 1;
	
	private int currentPlayer;
	
	private JButton btnStand;
//	private JLabel lblHand;
//	private JLabel lblHand_1;
//	private JLabel lblHand_2;
//	private JLabel lblHand_3;
//	private JLabel lblDealerHand;
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
	private JRadioButton rd1,rd2;
	
	
	//card slots
	private JLabel[] playerOneSlot;
	private JLabel[] playerTwoSlot;
	private JLabel[] playerThreeSlot;
	private JLabel[] playerFourSlot;
	private JLabel[] splitSlotOne;
	private JLabel[] splitSlotTwo;
	private JLabel[] splitSlotThree;
	private JLabel[] splitSlotFour;// = new JLabel[10];
	private JLabel[] dealerSlot;// = new JLabel[10];
	
	private JLayeredPane mainMenu;
	private JTextField fieldNumPlayers;
	private JTextField fieldDealerMoney;
	private JTextField fieldPlayerMoney;
	private JTextField fieldMinBet;
	private JTextField fieldNumHands;
	private JTextField fieldSimulation;
	private Button start;
	private Button simulation;
	
	
	//game info
	private JLabel lblNumPlayers;
	private JLabel lblDealerMoney;
	private JLabel lblPlayerMoney;
	private JLabel lblMinBet;
	private JLabel lblNumHands;
	private JLabel lblSimulation;
	private long dealerMoney;
	private long playerMoney;
	private long playerStartMoney = 0;
	private int numPlayers;
	private int minBet;
	private int numHands=1000;
	private JLabel lblHandOne;
	private JLabel lblHandTwo;
	private JLabel lblHandThree;
	private JLabel lblHandFour;
	private JLabel lblSplitHandOne;
	private JLabel lblSplitHandTwo;
	private JLabel lblSplitHandThree;
	private JLabel lblSplitHandFour;
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
//		game = new BlackJack(dealerMoney, playerMoney, numPlayers, 10000, minBet);
		hand = new BlackjackHandGUI(game.getInfo());
//		hand = new BlackJackHand(game.getInfo());
		
		//get the resolution of the user's monitor
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) screenSize.getWidth();
		height = (int) screenSize.getHeight();
		
		
		
		frame = new JFrame();
		frame.setBounds(100, 100, width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setUndecorated(true);
		frame.getContentPane().setLayout(null);	
		
		board = new JLayeredPane();
		board.setOpaque(true);
		board.setBackground(new Color(34,150,34));
		board.setBounds(0, -31, width, height);
		//frame.getContentPane().add(board);

		
		mainMenu = new JLayeredPane();
		mainMenu.setBounds(0, 0, width, height);
		frame.getContentPane().add(mainMenu);
		
		//betting slider
		slider = new JSlider();
		slider.setToolTipText("");
		slider.setMajorTickSpacing(1000);
		slider.setBounds(32, height - 170, 213, 54);
		board.add(slider);		
		lblMinBet = new JLabel("Min Bet: ");
		lblMinBet.setBounds(32, height - 220, 89, 14);
		board.add(lblMinBet);		
		lblMaxBet = new JLabel("Max Bet: " + hand.getPlayers(currentPlayer).getMoney());
		lblMaxBet.setBounds(159, height - 220, 130, 14);
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
				
		
		//select hand to operate on
		rd1 = new JRadioButton("1");
		rd1.setBounds(629, height - 150, 39, 23);
		board.add(rd1);
		buttonGroup.add(rd1);
		rd1.setSelected(true);
		rd2 = new JRadioButton("2");
		rd2.setBounds(670, height - 150, 39, 23);
		board.add(rd2);
		buttonGroup.add(rd2);
		rd2.setEnabled(false);
//		rd3 = new JRadioButton("3");
//		rd3.setBounds(711, height - 150, 39, 23);
//		board.add(rd3);
//		buttonGroup.add(rd3);
//		rd3.setEnabled(false);
//		rd4 = new JRadioButton("4");
//		rd4.setBounds(752, height - 150, 39, 23);
//		board.add(rd4);
//		buttonGroup.add(rd4);
//		rd4.setEnabled(false);
		
		//Player Money Balance Labels
		lblBalance = new JLabel("Balance:" + hand.getPlayers(currentPlayer).getMoney());
		lblBalance.setBounds(32, 50, 99, 14);
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
				drawPlayerHand(currentPlayer+1);
				drawSplitHand(currentPlayer+1);				
				btnSplit.setEnabled(false);
				
				if(hand.getPlayers(currentPlayer).getNumDecks()==2) {
					rd2.setEnabled(true);
				}else if(hand.getPlayers(currentPlayer).getNumDecks()==3) {}
//					rd3.setEnabled(true);
//				}else if(hand.getPlayers(currentPlayer).getNumDecks()==4) {
//					rd4.setEnabled(true);
//				}
//					
			}
		});
		btnSplit.setBounds(516, height - 100, 99, 43);
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
//				}else if(rd3.isSelected()) {
//					doubleDown(card, 3);
////					lblHand_2.setText(hand.getPlayers(currentPlayer).getDeck(3).printDeckGUI() +" Value:" +  hand.handPts(hand.getPlayers(currentPlayer).getDeck(3)));
//				}else if(rd4.isSelected()) {
//					doubleDown(card, 4);
////					lblHand_3.setText(hand.getPlayers(currentPlayer).getDeck(4).printDeckGUI() +" Value:" +  hand.handPts(hand.getPlayers(currentPlayer).getDeck(4)));
				}else {
					JOptionPane.showMessageDialog(frame,
						    "Choose a deck to double down.");
				}
				end();
			}
		});
		btnDouble.setBounds(421, height - 100, 89, 42);
		board.add(btnDouble);
		
		//Hit Button
		btnHit = new JButton("Hit");
		btnHit.setEnabled(false);
		btnHit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rd1.isSelected()) {
					hand.hit(currentPlayer, 1);
					drawPlayerHand(currentPlayer+1);
					drawSplitHand(currentPlayer+1);
//					lblHand.setText(hand.getPlayers(currentPlayer).getDeck(1).printDeckGUI() +" Value:" +  hand.handPts(hand.getPlayers(currentPlayer).getDeck(1)));
				}else if(rd2.isSelected()){
					hand.hit(currentPlayer, 2);
					drawPlayerHand(currentPlayer+1);
					drawSplitHand(currentPlayer+1);
//					lblHand_1.setText(hand.getPlayers(currentPlayer).getDeck(2).printDeckGUI() +" Value:" +  hand.handPts(hand.getPlayers(currentPlayer).getDeck(2)));
				}else {
					JOptionPane.showMessageDialog(frame,
						    "Choose a deck to hit.");
				}
				if(hand.bust(hand.getPlayers(currentPlayer).getDeck(1)) || hand.bust(hand.getPlayers(currentPlayer).getDeck(2))){
					JOptionPane.showMessageDialog(frame, "You busted.");
					end();
				}
			}
		});
		btnHit.setBounds(733, height - 100, 99, 43);
		board.add(btnHit);
		
		//Stand Button
		btnStand = new JButton("Stand");
		btnStand.setEnabled(false);
		btnStand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				end();
			}
		});
		btnStand.setBounds(625, height - 100, 99, 43);
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
				placeBets();				
				if(hand.canSplit(hand.getPlayers(currentPlayer).getDeck(1)))
					btnSplit.setEnabled(false);
				else
					btnSplit.setEnabled(false);				
				rd1.setEnabled(true);
				}
			}
		});
		btnBet.setBounds(93, height - 200, 89, 23);
		board.add(btnBet);
		
		/*Get insurance button*/
		btnGetInsurance = new JButton("Get Insurance");
		btnGetInsurance.setEnabled(false);
		btnGetInsurance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//add functionality
			}
		});
		btnGetInsurance.setBounds(281, height - 100, 130, 23);
		board.add(btnGetInsurance);
		
		
		/*Surrender button */
		btnSurrender = new JButton("Surrender");
		btnSurrender.setEnabled(false);
		btnSurrender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//add functionality;
			}
		});
		btnSurrender.setBounds(281, height - 70, 130, 23);
		board.add(btnSurrender);
		
		//Next Hand Button
		btnNextHand = new JButton("Next Hand");
		btnNextHand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								
				btnBet.setEnabled(true);
				slider.setMinimum(minBet);
				slider.setMaximum((int)(hand.getPlayers(currentPlayer).getMoney()));				
				nextHand();			
				btnNextHand.setEnabled(false);
			}
		});
		btnNextHand.setBounds(width/2, height - 100, 193, 36);
		board.add(btnNextHand);
		
		JLabel lblChooseHand = new JLabel("Choose Hand:");
		lblChooseHand.setBounds(534, height - 150, 89, 14);
		board.add(lblChooseHand);
		
		lblCurrentBet = new JLabel("Current bet: " + hand.getPlayers(currentPlayer).getBet());
		lblCurrentBet.setBounds(101, height - 240, 130, 14);
		board.add(lblCurrentBet);	
		
		lblHandOne = new JLabel("Player 1 Hand 1 | Value: ");		
		lblHandOne.setBounds(240, height - 300, 218, 14);
		board.add(lblHandOne);		
		lblHandTwo = new JLabel("Player 2 Hand 1 | Value: ");		
		lblHandTwo.setBounds(640, height - 300, 218, 14);
		board.add(lblHandTwo);	
		lblHandThree = new JLabel("Player 3 Hand 1 | Value: ");		
		lblHandThree.setBounds(1040, height - 300, 218, 14);
		board.add(lblHandThree);	
		lblHandFour = new JLabel("Player 4 Hand 1 | Value: ");		
		lblHandFour.setBounds(1440, height - 300, 218, 14);
		board.add(lblHandFour);
		
		lblSplitHandOne = new JLabel("");
		lblSplitHandOne.setBounds(440, height - 300, 2, 14);
		board.add(lblSplitHandOne);
		lblSplitHandTwo = new JLabel("");
		lblSplitHandTwo.setBounds(840, height - 300, 2, 14);
		board.add(lblSplitHandTwo);
		lblSplitHandThree = new JLabel("");
		lblSplitHandThree.setBounds(1240, height - 300, 2, 14);
		board.add(lblSplitHandThree);
		lblSplitHandFour = new JLabel("");
		lblSplitHandFour.setBounds(1640, height - 300, 2, 14);
		board.add(lblSplitHandFour);

		

		lblDealerHand_1 = new JLabel("Dealer Hand | Value: ");
		lblDealerHand_1.setBounds(width/2 - 35, height/2 - 100, 190, 14);
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
		
		JMenuItem mntmExitGame = new JMenuItem("Exit Game");
		mnGame.add(mntmExitGame);
		mntmExitGame.setEnabled(true);
		mntmExitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
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
		start.setBounds(width/2 - 380, height/2, 330, 71);
		mainMenu.add(start);
		
		
		simulation = new Button("Start Simulation");
		simulation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				numHands = Integer.parseInt(fieldNumHands.getText());
				simType = Integer.parseInt(fieldSimulation.getText());
				BlackJackSimulator simulation = new BlackJackSimulator(10000000000L, 1000000000L, 3, numHands, 100, simType);
				playerStartMoney = simulation.getPlayerMoney();
				simulation.game();
				
				//dispaly results
				JOptionPane.showMessageDialog(frame,"                   Results:\n\n"+
											"Player Starting Money: $" + playerStartMoney + 
											"\nPlayer Ending Money: $"  + simulation.getPlayerMoney() +
											"\n\nNumber of Hands: " + numHands +
											"\n\nElapsed Time(seconds): " + simulation.getTimeEnd());
				
			}
		});
		simulation.setFont(new Font("Dialog", Font.PLAIN, 27));
		simulation.setBounds(width/2, height/2, 330, 71);
		mainMenu.add(simulation);
		
		
		fieldSimulation = new JTextField("1");
		fieldSimulation.setColumns(10);
		fieldSimulation.setBounds(width/2+100, height/3 + 70, 146, 26);
		mainMenu.add(fieldSimulation);
		
		lblSimulation = new JLabel("Enter the Type of Simulation: 1 for Basic | 2 for HiLo | 3 for Illustrious 18");
		lblSimulation.setBounds(width/2 , height/3 + 40, 400, 26);
		mainMenu.add(lblSimulation);
		
		fieldNumHands = new JTextField("1000");
		fieldNumHands.setColumns(10);
		fieldNumHands.setBounds(width/2+100, height/3 + 130, 146, 26);
		mainMenu.add(fieldNumHands);
		
		fieldNumPlayers = new JTextField("1");
		fieldNumPlayers.setBounds(width/2-200, height/3, 146, 26);
		mainMenu.add(fieldNumPlayers);
		fieldNumPlayers.setColumns(10);
		
		fieldDealerMoney = new JTextField("1000000");
		fieldDealerMoney.setColumns(10);
		fieldDealerMoney.setBounds(width/2 - 200, height/3 + 40, 146, 26);
		mainMenu.add(fieldDealerMoney);
		
		fieldPlayerMoney = new JTextField("5000");
		fieldPlayerMoney.setColumns(10);
		fieldPlayerMoney.setBounds(width/2 - 200, height/3 + 80, 146, 26);
		mainMenu.add(fieldPlayerMoney);
		
		fieldMinBet = new JTextField("500");
		fieldMinBet.setColumns(10);
		fieldMinBet.setBounds(width/2 - 200, height/3 + 120, 146, 26);
		mainMenu.add(fieldMinBet);
		
		lblNumHands = new JLabel("Enter Number of Hands to Simulate");
		lblNumHands.setBounds(width/2 + 70, height/3 + 100, 207, 23);
		mainMenu.add(lblNumHands);
		
		
		lblNumPlayers = new JLabel("Enter Number of Players(up to 4):");
		lblNumPlayers.setBounds(width/2 - 400, height/3, 207, 23);
		mainMenu.add(lblNumPlayers);
		
		lblDealerMoney = new JLabel("Enter Dealer Money:");
		lblDealerMoney.setBounds(width/2 - 400, height/3 + 40, 207, 23);
		mainMenu.add(lblDealerMoney);
		
		lblPlayerMoney = new JLabel("Enter Player Money:");
		lblPlayerMoney.setBounds(width/2 - 400, height/3 + 80, 207, 23);
		mainMenu.add(lblPlayerMoney);
		
		lblMinBet = new JLabel("Enter the Minimum Bet:");
		lblMinBet.setBounds(width/2 - 400, height/3 + 120, 207, 23);
		mainMenu.add(lblMinBet);
		
		createCardSlots();
	}
	
	private void doubleDown(Card card, int deck){
		hand.getPlayers(currentPlayer).doubleDown(card, deck);
		hand.getPlayers(currentPlayer).showDouble();
	}
	
	
	//createCardSlots() creates the card slots for the card images to be drawn to
	private void createCardSlots(){
		playerOneSlot=new JLabel[10];
		playerTwoSlot=new JLabel[10];
		playerThreeSlot=new JLabel[10];
		playerFourSlot=new JLabel[10];
		dealerSlot=new JLabel[10];
		splitSlotOne=new JLabel[10];
		splitSlotTwo=new JLabel[10];
		splitSlotThree=new JLabel[10];
		splitSlotFour=new JLabel[10];
		int x=0;		
        for (int i=0;i<10;i++){        	
        	playerOneSlot[i]=new JLabel();
        	playerOneSlot[i].setBounds(250+x, height - 420,70,110);
        	playerTwoSlot[i]=new JLabel();
        	playerTwoSlot[i].setBounds(650+x, height - 420,70,110);  
        	playerThreeSlot[i]=new JLabel();
        	playerThreeSlot[i].setBounds(1050+x, height - 420,70,110);  
        	playerFourSlot[i]=new JLabel();
        	playerFourSlot[i].setBounds(1450+x, height - 420,70,110);  
        	
        	dealerSlot[i]=new JLabel();
        	dealerSlot[i].setBounds(width/2 - 35+x,height/2 - 220,70,110);
        	
        	splitSlotOne[i]=new JLabel();
        	splitSlotOne[i].setBounds(450+x, height - 420,70,110);
        	splitSlotTwo[i]=new JLabel();
        	splitSlotTwo[i].setBounds(850+x, height - 420,70,110);
        	splitSlotThree[i]=new JLabel();
        	splitSlotThree[i].setBounds(1250+x, height - 420,70,110);
        	splitSlotFour[i]=new JLabel();
        	splitSlotFour[i].setBounds(1650+x, height - 420,70,110);
        	
        	board.add(playerOneSlot[i]);
        	board.moveToFront(playerOneSlot[i]);
        	board.add(playerTwoSlot[i]);
        	board.moveToFront(playerTwoSlot[i]); 
        	board.add(playerThreeSlot[i]);
        	board.moveToFront(playerThreeSlot[i]); 
        	board.add(playerFourSlot[i]);
        	board.moveToFront(playerFourSlot[i]);
        	
        	board.add(dealerSlot[i]);
        	board.moveToFront(dealerSlot[i]);
        	
        	board.add(splitSlotOne[i]);
        	board.moveToFront(splitSlotOne[i]);
        	board.add(splitSlotTwo[i]);
        	board.moveToFront(splitSlotTwo[i]);
        	board.add(splitSlotThree[i]);
        	board.moveToFront(splitSlotThree[i]);
        	board.add(splitSlotFour[i]);
        	board.moveToFront(splitSlotFour[i]);
        	x+=25;
        }
    }
	
	
	//resetPlayerSlots resets all player's hands to null
	private void resetPlayerSlots() {
			for(int i=0;i<10;i++) {			
				playerOneSlot[i].setIcon(null);			
				playerOneSlot[i].revalidate();			
				playerTwoSlot[i].setIcon(null);			
				playerTwoSlot[i].revalidate();			
				playerThreeSlot[i].setIcon(null);			
				playerThreeSlot[i].revalidate();			
				playerFourSlot[i].setIcon(null);			
				playerFourSlot[i].revalidate();			
				splitSlotOne[i].setIcon(null);
				splitSlotOne[i].revalidate();
				splitSlotTwo[i].setIcon(null);
				splitSlotTwo[i].revalidate();
				splitSlotThree[i].setIcon(null);
				splitSlotThree[i].revalidate();
				splitSlotFour[i].setIcon(null);
				splitSlotFour[i].revalidate();
			
		}
	}
	
	//resetCardSlots() resets the dealer's hand to null
	private void resetCardSlots() {
		for(int i=0;i<10;i++) {
			dealerSlot[i].setIcon(null);
			dealerSlot[i].revalidate();
		}
	}
	
	
	//drawAllHands() draws all player hands to the board
	private void drawAllHands() {
		for(int i = 0;i<this.numPlayers;i++) {
			drawPlayerHand(i+1);
		}
	}
	
	
	//drawPlayerHand() draws a specific players hand to the board
    private void drawPlayerHand(int player){
    	switch(player) {
    	case 1:
    		for(int i=0;i<hand.getPlayers(0).getDeck(1).size();i++){
        		playerOneSlot[i].setIcon(hand.getPlayers(0).getDeck(1).getCard(i).getImg());
        		lblHandOne.setText("Player 1 Hand 1 | Value: " + hand.handPts(hand.getPlayers(0).getDeck(1)));
        	}
    		break;
    	case 2:
    		for(int i=0;i<hand.getPlayers(1).getDeck(1).size();i++){
        		playerTwoSlot[i].setIcon(hand.getPlayers(1).getDeck(1).getCard(i).getImg());
        		lblHandTwo.setText("Player 2 Hand 1 | Value: " + hand.handPts(hand.getPlayers(1).getDeck(1)));
        	}
    		break;
    	case 3:
    		for(int i=0;i<hand.getPlayers(2).getDeck(1).size();i++){
        		playerThreeSlot[i].setIcon(hand.getPlayers(2).getDeck(1).getCard(i).getImg());
        		lblHandThree.setText("Player 3 Hand 1 | Value: " + hand.handPts(hand.getPlayers(2).getDeck(1)));
        	}
    		break;
    	case 4:
    		for(int i=0;i<hand.getPlayers(3).getDeck(1).size();i++){
        		playerFourSlot[i].setIcon(hand.getPlayers(3).getDeck(1).getCard(i).getImg());
        		lblHandFour.setText("Player 4 Hand 1 | Value: " + hand.handPts(hand.getPlayers(3).getDeck(1)));
        	}
    		break;
    	}
    	
    }
    
    //drawSplitHand() draws the specified player's second hand to the board when they choose to split
    private void drawSplitHand(int player){
    	switch(player) {
    		case 1:
    			for(int i=0;i<hand.getPlayers(currentPlayer).getDeck(2).size();i++){
    				splitSlotOne[i].setIcon(hand.getPlayers(currentPlayer).getDeck(2).getCard(i).getImg());
    				lblSplitHandOne.setText("player 1 Hand 2 | Value: " + hand.handPts(hand.getPlayers(currentPlayer).getDeck(2)));
    			}
    			break;
    		case 2:
    			for(int i=0;i<hand.getPlayers(currentPlayer).getDeck(2).size();i++){
    	    		splitSlotTwo[i].setIcon(hand.getPlayers(currentPlayer).getDeck(2).getCard(i).getImg());
    	    		lblSplitHandOne.setText("player 2 Hand 2 | Value: " + hand.handPts(hand.getPlayers(currentPlayer).getDeck(2)));
    	    	}
    			break;
    		case 3:
    			for(int i=0;i<hand.getPlayers(currentPlayer).getDeck(2).size();i++){
    	    		splitSlotThree[i].setIcon(hand.getPlayers(currentPlayer).getDeck(2).getCard(i).getImg());
    	    		lblSplitHandOne.setText("player 3 Hand 2 | Value: " + hand.handPts(hand.getPlayers(currentPlayer).getDeck(2)));
    	    	}
    			break;
    		case 4:
    			for(int i=0;i<hand.getPlayers(currentPlayer).getDeck(2).size();i++){
    	    		splitSlotFour[i].setIcon(hand.getPlayers(currentPlayer).getDeck(2).getCard(i).getImg());
    	    		lblSplitHandOne.setText("player 4 Hand 2 | Value: " + hand.handPts(hand.getPlayers(currentPlayer).getDeck(2)));
    	    	}
    			break;
    	}
    	
    }
    
    //drawDealerHand() draws the dealer's hand to the board
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
	
	
	//resetButtons() disables all buttons except the next hand button
	private void resetButtons() {		
		btnSurrender.setEnabled(false);
		btnGetInsurance.setEnabled(false);
		btnHit.setEnabled(false);
		btnDouble.setEnabled(false);
		btnSplit.setEnabled(false);
		btnBet.setEnabled(false);
		btnStand.setEnabled(false);
		btnNextHand.setEnabled(true);
	}
	
	
	//resetLabels() resets all labels to default
	private void resetLabels() {
		lblBalance.setText("Balance:" + hand.getPlayers(currentPlayer).getMoney());
		lblMinBet.setText("Min Bet: " + minBet);
		lblMaxBet.setText("Max Bet: " + hand.getPlayers(currentPlayer).getMoney());
		lblHandOne.setText("Player 1 | Value: ");
		lblHandTwo.setText("Player 2 | Value: ");
		lblHandThree.setText("Player 3 | Value: ");
		lblHandFour.setText("Player 4 | Value: ");
		lblSplitHandOne.setText(null);
		lblSplitHandTwo.setText(null);
		lblSplitHandThree.setText(null);
		lblSplitHandFour.setText(null);
		lblDealerHand_1.setText("Dealer Hand | Value: ");
		resetCardSlots();
		rd1.setSelected(true);
		hand.setHandOver(false);		
	}
	
	//nextHand() resets the board and initializes the next hand
	private void nextHand() {
		resetLabels();
		resetButtons();
		resetCardSlots();
		resetPlayerSlots();
		btnBet.setEnabled(true);
		hand.initGUI();
		JOptionPane.showMessageDialog(frame,"Player " + (currentPlayer + 1) + ", place your bet.");
	}
	
	//placeBets() prompts the players to place their bets and 
	//draws each hand to the board when all players have placed their bets
	private void placeBets() {
		if(currentPlayer+1<this.numPlayers) {
			btnBet.setEnabled(true);
			currentPlayer++;
			JOptionPane.showMessageDialog(frame,"Player " + (currentPlayer + 1) + ", place your bet.");
			
		}else {
			currentPlayer=0;
			drawAllHands();
			drawDealerHand();
			btnStand.setEnabled(true);
			btnHit.setEnabled(true);
			btnDouble.setEnabled(false);
			JOptionPane.showMessageDialog(frame,"Player " + (currentPlayer + 1) + "'s turn.");
		}
	}
	
	//end() prompts each player to play their turn and when all players have played their turn,
	//displays the final board state and pays out each player
	private void end() {			
		if(currentPlayer>=this.numPlayers-1){
			hand.hitUntil17GUI();
			drawPlayerHand(currentPlayer+1);
			drawDealerHand();
			currentPlayer = 0;
			for(int i = 0; i<this.numPlayers;i++) {
				if(hand.winner(hand.getPlayers(currentPlayer).getDeck(1),hand.getDealerDeck())==0) {
					JOptionPane.showMessageDialog(frame,"Player " + (currentPlayer+1) + " won! Payout is: "+hand.getPlayers(currentPlayer).getBet()*2);					
				}else if(hand.winner(hand.getPlayers(currentPlayer).getDeck(1),hand.getDealerDeck())==1) {
					JOptionPane.showMessageDialog(frame,"Player " + (currentPlayer+1) + " lost.");
				}else if(hand.winner(hand.getPlayers(currentPlayer).getDeck(1),hand.getDealerDeck())==2) {
					JOptionPane.showMessageDialog(frame,"Player " + (currentPlayer+1) + " tied.");
				}
				currentPlayer++;
			}
			
			hand.cleanUp();
			resetButtons();
			btnNextHand.setEnabled(true);
			currentPlayer = 0;
		}else {
			lblBalance.setText("Balance:" + hand.getPlayers(currentPlayer).getMoney());
			lblMaxBet.setText("Max Bet: " + hand.getPlayers(currentPlayer).getMoney());	
			currentPlayer++;
			JOptionPane.showMessageDialog(frame,"Player " + (currentPlayer + 1) + "'s turn.");
		}

	}
}





