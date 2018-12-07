package CardSuite;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Image;
import java.awt.Button;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Color;

public class MainMenu {

	private JFrame frame;
	private JLayeredPane mainMenu;
	private JTextField fieldNumPlayers;
	private JTextField fieldDealerMoney;
	private JTextField fieldPlayerMoney;
	private JTextField fieldMinBet;
	private JLabel lblNewLabel;
	private JLayeredPane background;


	/**
	 * Create the application.
	 */
	public MainMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 872, 567);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		mainMenu = new JLayeredPane();
		mainMenu.setBounds(0, 0, 850, 517);
		frame.getContentPane().add(mainMenu);
		
		
		ImageIcon wallpaper = resizeImage(1920, 1080,"/resources/images/test.png");
		
		background = new JLayeredPane();
		background.setBounds(0, 0, 850, 517);
		//background.setOpaque(true);
		lblNewLabel = new JLabel();
		lblNewLabel.setBounds(0, 0, 850, 517);
		lblNewLabel.setIcon(wallpaper);
		//background.add(lblNewLabel);
		frame.getContentPane().add(background);

		
		Button start = new Button("Start Game");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		start.setFont(new Font("Dialog", Font.PLAIN, 27));
		start.setBounds(255, 382, 330, 71);
		mainMenu.add(start);
		
		fieldNumPlayers = new JTextField();
		fieldNumPlayers.setBounds(515, 99, 146, 26);
		mainMenu.add(fieldNumPlayers);
		fieldNumPlayers.setColumns(10);
		
		fieldDealerMoney = new JTextField();
		fieldDealerMoney.setColumns(10);
		fieldDealerMoney.setBounds(515, 141, 146, 26);
		mainMenu.add(fieldDealerMoney);
		
		fieldPlayerMoney = new JTextField();
		fieldPlayerMoney.setColumns(10);
		fieldPlayerMoney.setBounds(515, 186, 146, 26);
		mainMenu.add(fieldPlayerMoney);
		
		fieldMinBet = new JTextField();
		fieldMinBet.setColumns(10);
		fieldMinBet.setBounds(515, 228, 146, 26);
		mainMenu.add(fieldMinBet);
		
		
		JLabel lblNumPlayers = new JLabel("Enter Number of Players:");
		lblNumPlayers.setForeground(new Color(255, 255, 255));
		lblNumPlayers.setBounds(308, 101, 207, 23);
		mainMenu.add(lblNumPlayers);
		
		JLabel lblDealerMoney = new JLabel("Enter Dealer Money:");
		lblDealerMoney.setForeground(new Color(255, 255, 255));
		lblDealerMoney.setBounds(308, 144, 207, 23);
		mainMenu.add(lblDealerMoney);
		
		JLabel lblPlayerMoney = new JLabel("Enter Player Money:");
		lblPlayerMoney.setForeground(new Color(255, 255, 255));
		lblPlayerMoney.setBounds(308, 189, 207, 23);
		mainMenu.add(lblPlayerMoney);
		
		JLabel lblMinBet = new JLabel("Enter the Minimum Bet:");
		lblMinBet.setForeground(new Color(255, 255, 255));
		lblMinBet.setBounds(308, 231, 207, 23);
		mainMenu.add(lblMinBet);
		mainMenu.add(lblNewLabel);

	}
	
	public ImageIcon resizeImage(int x, int y, String imagePath) {
		ImageIcon imageIcon = new ImageIcon(MainMenu.class.getResource(imagePath)); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(x, y,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);  // transform it back
		return imageIcon;
	}
	
	public JLayeredPane draw(){
		return this.mainMenu;
	}
}
