import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.SystemColor;

public class Game_GUI {
	private Color bgColor = new Color(60, 60, 60); // The background color of the game frame
	private int selectedCardIndex = -1; // The index of the selected buttons (the selected card)
	private boolean isTaki = false; // Indicates if a Taki move has started
	private boolean isTakeTwo = false; // Indicates if a Take-Two move has started

	private JFrame frmTaki; // The frame of the game
	private JPanel playerPanel; // The panel of player number one
	private JPanel oppPanel; // The panel of player number two
	private JButton LastCardButton; // Last card dropped button
	private JButton EndTaki; // A button to end a Taki move, appears only when isTaki is true.

	private ArrayList<JButton> buttonsList1; // The ArrayList of buttons(card) of player number one
	private ArrayList<JButton> buttonsList2; // The ArrayList of buttons(card) of player number two

	private Game game; // The game class

	// Function gets a type that indicated if the game is 1v1(0) or 1vPC(1)
	// The Function Launch the application.
	public static void main(String[] args, int type) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game_GUI window = new Game_GUI();
					window.frmTaki.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// The Function Creates the application.
	public Game_GUI() {
		buttonsList1 = new ArrayList<JButton>();
		buttonsList2 = new ArrayList<JButton>();
		playerPanel = new JPanel();

		game = new Game();

		initialize();

	}

	// The Function initialize the contents of the frame.
	private void initialize() {
		// Main frame
		frmTaki = new JFrame();
		frmTaki.setIconImage(Toolkit.getDefaultToolkit().getImage("Photos\\TAKI_logo.png")); // Frame icon image
		frmTaki.setTitle("TAKI BY MATAN ANTEBI"); // Frame title
		frmTaki.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTaki.getContentPane().setLayout(null);
		frmTaki.setResizable(false);
		frmTaki.setSize(1280, 720); // Frame size
		frmTaki.setLocationRelativeTo(null); // Frame opening location

		// Last card button
		LastCardButton = new JButton("");
		LastCardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Check valid card drop
				takeLastCard();
			}
		});
		// EndTaki temp button
		EndTaki = new JButton("");
		EndTaki.setBorderPainted(false);
		EndTaki.setIcon(new ImageIcon("Photos\\END_TURN.png")); // Button image
		EndTaki.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Disable Taki action when pressed, and hiding the button
				isTaki = false; // End Taki action
				EndTaki.setVisible(false); // hide End Taki button
				int endTurnOnFinish = game.dropCardByCard(game.getLastCardStack());

				ChecksEndCondition(endTurnOnFinish); // Checks end turn conditions

				selectedCardIndex = -1; // Resets selected card index

			}
		});

		// End Taki button define
		EndTaki.setBounds(1052, 306, 191, 77);
		EndTaki.setVisible(false);
		frmTaki.getContentPane().add(EndTaki);
		LastCardButton.setBounds(625, 272, 108, 154);
		frmTaki.getContentPane().add(LastCardButton);

		// Deck button define
		JButton KupaButton = new JButton("");
		KupaButton.setIcon(new ImageIcon("Photos\\Cards\\Back.png"));
		KupaButton.setBounds(204, 272, 108, 154);
		frmTaki.getContentPane().add(KupaButton);
		KupaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Take From Kupa
				takeFromKupa();
				System.out.println(
						"cards in kupa = " + game.getKupaSize() + "\ncards in thrash = " + game.getThrashStackSize());

			}
		});

		// Player's panel
		playerPanel = new JPanel();
		playerPanel.setBackground(bgColor);
		playerPanel.setBounds(29, 496, 1214, 154);
		playerPanel.setLayout(new GridLayout(1, 14, 5, 0));

		// Opponent's panel
		oppPanel = new JPanel();
		oppPanel.setBackground(bgColor);
		oppPanel.setBounds(29, 33, 1214, 154);
		oppPanel.setLayout(new GridLayout(1, 14, 5, 0));

		// Print decks
		showScreen();

		// Background
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("Photos\\TAKIBG.png"));
		lblNewLabel.setBounds(-10, 0, 1274, 681);
		frmTaki.getContentPane().add(lblNewLabel);
	}

	private JPanel getCurrentPlayerPanel() {
		// Function returns current player's panel
		if (game.getCurrentPlayer() == 0)
			return playerPanel;
		return oppPanel;
	}

	private ArrayList<JButton> getCurrentPlayerButtonList() {
		// Function returns current player's buttons list (cards list)
		if (game.getCurrentPlayer() == 0)
			return buttonsList1;
		return buttonsList2;
	}

	private void showScreen() {
		// Function prints all player's GUI panel
		addButtons(buttonsList1, playerPanel, 8); // Add 8 starting cards
		printDeck(game.getDeck(0), buttonsList1); // Prints player1's deck
		frmTaki.getContentPane().add(playerPanel); // Add player1's panel to the frame

		addButtons(buttonsList2, oppPanel, 8); // Add 8 starting cards
		printDeck(game.getDeck(1), buttonsList2); // Prints player2's deck
		frmTaki.getContentPane().add(oppPanel); // Add player2's panel to the frame

		printTopCard(game.getLastCardStack()); // Prints last card buttons
	}

	private void printDeck(ArrayList<TakiCard> deck, ArrayList<JButton> btList) {
		// Function gets a deck and an ArrayList of buttons
		// Function adds icons to the buttons by the deck
		for (int i = 0; i < deck.size(); i++)
			btList.get(i).setIcon(getCardImage(deck.get(i)));
	}

	private void addButtons(ArrayList<JButton> btList, JPanel my_panel, int n) {
		// Function gets an ArrayList of buttons, a panel and an integer
		// Function add -n- buttons to the list and locate them on the panel
		for (int i = 0; i < n; i++) {
			btList.add(new JButton(""));
			my_panel.add(btList.get(i));
			btList.get(i).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					// Creates actionListener for each button
					if (my_panel == getCurrentPlayerPanel()) {
						int j = btList.indexOf(event.getSource());
						System.out.println("button " + j);
						selectedCardIndex = j; // Update button's selected index
					}

				}
			});
		}
	}

	private void takeFromKupa() {
		// The function takes a card from the main deck if possible
		game.checkIfEmptyKupa(); // Makes sure the main deck is not empty
		if (isTakeTwo == true) { // Take 2 Action finish
			int tempLimit = game.getToAddSize();
			for (int i = 0; i < tempLimit; i++) {
				System.out.println("to add size: " + game.getToAddSize());
				TakiCard takenCard = game.takeCardsOrder();
				System.out.println("new to add size: " + game.getToAddSize());

				System.out.println("Taking a specialorders card: " + takenCard.toString());
				game.getDeck(game.getCurrentPlayer()).add(takenCard);
				System.out.println("finished to add size: " + game.getToAddSize());
			}
			isTakeTwo = false; // Resets take two action
		} else { // Normal move: only one card
			TakiCard takenCard = game.TakeACard();
			System.out.println("Taking a card: " + takenCard.toString());
			game.getDeck(game.getCurrentPlayer()).add(takenCard);
		}
		updateScreen(); // Updates player's buttons
		game.endTurn(); // End current player's turn

	}

	private void updateScreen() {
		// Function update current players button list
		getCurrentPlayerButtonList().clear(); /// Clear player's button list
		getCurrentPlayerPanel().removeAll(); // Clears player's panel
		getCurrentPlayerPanel().repaint(); // Updates the screen

		int newSize = 8;
		System.out.println("new size: " + game.getDeck(game.getCurrentPlayer()).size());
		if (game.getDeck(game.getCurrentPlayer()).size() > 8) // GridLayout options
			newSize = game.getDeck(game.getCurrentPlayer()).size();
		addButtons(getCurrentPlayerButtonList(), getCurrentPlayerPanel(), newSize);
		printDeck(game.getDeck(game.getCurrentPlayer()), getCurrentPlayerButtonList());
		for (int i = game.getDeck(game.getCurrentPlayer()).size(); i < getCurrentPlayerButtonList().size(); i++) {
			getCurrentPlayerButtonList().get(i).setVisible(false);
		}
		printTopCard(game.getLastCardStack()); // Prints new top card

		if (game.isGameOver() == 0) // Checks end game
		{
			System.out.println("Player 1 won");
			JOptionPane.showMessageDialog(frmTaki, "Player 1 Won !");
			frmTaki.dispose();
		} else if (game.isGameOver() == 1) {
			System.out.println("Player 2 won");
			JOptionPane.showMessageDialog(frmTaki, "Player 2 Won !");
			frmTaki.dispose();
		}
	}

	private void takeLastCard() {
		// Function drops selected card if possible
		if (selectedCardIndex != -1) // Checks if the player chose a card
			if (isTakeTwo == true) // Checks if Take 2 action is enabled
				if (game.take2Series(selectedCardIndex) == true) {
					updateScreen(); // Updates player's buttons
					game.finishTurn(true);
					return;
				} else
					return;

		if (selectedCardIndex != -1 && isTaki == true) // Checks if Taki action is enabled
			if (game.getDeck(game.getCurrentPlayer()).get(selectedCardIndex).getColor() != game.getValidColor()) // Not
																													// a
																													// regular
																													// taki
																													// type

				if (game.getDeck(game.getCurrentPlayer()).get(selectedCardIndex)
						.getColor() == TakiCard.Color.ChangeColor // Stops a Taki process by change color
						|| game.getDeck(game.getCurrentPlayer()).get(selectedCardIndex).getValue() == game
								.getValidValue()) { // Stop a Taki process by valid value
					System.out.println("sharondagon");
					isTaki = false; // Stops Taki action
					System.out.println("I FINISH TAKI");
					EndTaki.setVisible(false); // Hiding EndTaki button

				}
		if (isTaki == true && selectedCardIndex != -1) { // A Regular Taki color
			game.startTaki(selectedCardIndex);
			selectedCardIndex = -1; // Resets selected index
		}
		int endTurnOnFinish = game.dropCard(selectedCardIndex); // Checks players card

		updateScreen(); // Updates player's buttons

		ChecksEndCondition(endTurnOnFinish); // Checks end turn conditions

		selectedCardIndex = -1; // Resets selected index
	}

	private void ChecksEndCondition(int endTurnOnFinish) {
		// Function gets an integer
		// Function end current player's turn if he finished his turn, and activates
		// special actions if needed

		if (endTurnOnFinish == 1) // Valid regular move: end current player's turn
			game.finishTurn(true); // Ends current player's turn

		else if (endTurnOnFinish == 0) // Invalid regular move: d
			game.finishTurn(false); // Does not end current player's turns current player's turn

		else if (endTurnOnFinish == 2) { // Valid taki action move
			isTaki = true; // Starts taki action
			EndTaki.setVisible(true); // Shows End Taki button

		} else if (endTurnOnFinish == 3) { // Valid Plus 2 action move
			isTakeTwo = true; // Starts Take 2 action
			game.finishTurn(true); // Ends current player's turn
		}

	}

	private ImageIcon getCardImage(TakiCard card) {
		// Function gets a Taki Card
		// Function returns a matched image in an ImageIcon variable
		ImageIcon cardImg = new ImageIcon("Photos\\Cards\\" + card.toString() + ".png");
		Image image = cardImg.getImage();
		Image newimg = image.getScaledInstance(145, 160, java.awt.Image.SCALE_SMOOTH);
		cardImg = new ImageIcon(newimg);
		return cardImg;
	}

	private void printTopCard(TakiCard card) {
		// Function gets a Taki Card
		// Function displays the card on top card button
		LastCardButton.setIcon(getCardImage(card)); // Updates top card's icon

		// Creates buttons's stroke by the current valid color
		if (game.getValidColor() == TakiCard.Color.Red)
			LastCardButton.setBorder(BorderFactory.createLineBorder(Color.red, 5));
		else if (game.getValidColor() == TakiCard.Color.Yellow)
			LastCardButton.setBorder(BorderFactory.createLineBorder(Color.yellow, 5));
		else if (game.getValidColor() == TakiCard.Color.Green)
			LastCardButton.setBorder(BorderFactory.createLineBorder(Color.green, 5));
		else if (game.getValidColor() == TakiCard.Color.Blue)
			LastCardButton.setBorder(BorderFactory.createLineBorder(Color.blue, 5));
		else
			LastCardButton.setBorder(BorderFactory.createLineBorder(Color.gray, 5));
	}
}
