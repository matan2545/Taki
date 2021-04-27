import java.util.ArrayList;
import java.util.Stack;
import javax.swing.JOptionPane;

public class Game {
	private Globals gvars = new Globals();
	
	private int currentPlayer; // Index of current player
	private int otherPlayer; // Index of current player
	private int isAI;
	
	private TakiCard.Color validColor; // The current valid color
	private TakiCard.Value validValue; // The current valid value

	private TakiDeck Kupa;
	private TakiCard topCard;
	
	private Stack<TakiCard> TrashStack;
	private ArrayList<TakiCard> player1Deck;
	private ArrayList<TakiCard> player2Deck;
	private ArrayList<TakiCard> addToPlayer;



	public Game(int isAI) {
		start();
		this.isAI = isAI;

	}

	public void start() {

		Kupa = new TakiDeck();
		TrashStack = new Stack<TakiCard>();
		addToPlayer = new ArrayList<TakiCard>();

		Kupa.reset();
		Kupa.shuffle();

		topCard = Kupa.takeCard();
		TrashStack.push(topCard);
		validColor = topCard.getColor();
		validValue = topCard.getValue();

		// Player 1 Deck
		player1Deck = new ArrayList<TakiCard>();
		player1Deck = Kupa.takeCard(8);
		for (int i = 0; i < player1Deck.size(); i++)
			System.out.println(player1Deck.get(i).toString() + " ");

		// Player 2 Deck
		player2Deck = new ArrayList<TakiCard>();
		player2Deck = Kupa.takeCard(8);
		for (int i = 0; i < player2Deck.size(); i++)
			System.out.println(player2Deck.get(i).toString() + " ");

		// First card
		TakiCard lastCard = Kupa.takeCard(); // STARTING CARD

		addToTrashStack(lastCard);
		validColor = lastCard.getColor();
		validValue = lastCard.getValue();

		// Checks valid first card
		if (validValue == TakiCard.Value.ChangeColor || validValue == TakiCard.Value.TakeTwo
				|| validValue == TakiCard.Value.SuperTaki || validValue == TakiCard.Value.Taki ||
				validValue == TakiCard.Value.Stop || validValue == TakiCard.Value.ChangeDirection) {
			start();
		}
	}

	public TakiCard takeCardsOrder() {
		TakiCard temp = addToPlayer.get(0);
		addToPlayer.remove(0);
		return temp;
	}

	public int getToAddSize() {
		return addToPlayer.size();
	}

	public ArrayList<TakiCard> getDeck(int pid) {
		if (pid == 1)
			return player1Deck;
		return player2Deck;
	}
	
	public void endTurn() {
		if (this.currentPlayer == 0)
			this.currentPlayer = 1;

		else
			this.currentPlayer = 0;
	}

	public void addToTrashStack(TakiCard card) {
		TrashStack.push(card);
		if (card.getColor() != TakiCard.Color.ChangeColor) {
			validColor = card.getColor();

		}
	}

	public TakiCard TakeACard() {
		return Kupa.takeCard();
	}

	public int getKupaSize() {
		return Kupa.getSize();
	}

	public int getThrashStackSize() {
		return TrashStack.size();
	}

	public TakiCard getLastCardStack() {
		// Function returns the Taki Card that is on the top of the stock pile
		if (!TrashStack.isEmpty())
			return TrashStack.peek();
		else {
			TrashStack.push(Kupa.takeCard());
			return TrashStack.peek();
		}

	}

	public boolean hasEmptyHand(int pid) {
		// Function gets a player ID
		// Function returns true or false if the player has an empty hand
		return getDeck(pid).isEmpty();
	}

	public int isGameOver() {
		// Function return true or false if one of the players has empty hand (won the
		// game), returns -1 if no one won the game
		if (hasEmptyHand(currentPlayer) == true)
			return currentPlayer;
		if (hasEmptyHand(otherPlayer) == true)
			return otherPlayer;
		return -1;
	}

	public int getCurrentPlayer() {
		// Function returns the player ID of the current player
		return this.currentPlayer;
	}

	public int getOtherPlayer(int i) {
		// Function gets an index
		// Function returns the player ID of the previous player that played
		return this.otherPlayer;
	}
	/*
	 * public int getPlayerHandSize(int pid) { // Function gets a player ID //
	 * Function returns the number of cards that the player has return
	 * getPlayerHand(pid).size(); }
	 */

	public TakiCard.Color getValidColor() {
		return this.validColor;
	}

	public TakiCard.Value getValidValue() {
		return this.validValue;
	}

	public boolean validCardPlay(TakiCard card) {
		return card.getColor() == getValidColor() || card.getValue() == getValidValue();
	}

	public void setCardColor(TakiCard.Color color) {
		validColor = color;
	}

	public boolean validTakiDrop(int index) {
		if (this.getDeck(this.getCurrentPlayer()).get(index).getColor() == this.getValidColor()) {
			this.getDeck(this.getCurrentPlayer()).remove(index);
			return true;
		} else
			return false;
	}

	public boolean take2Series(int index) {
		if (index != -1 && this.getDeck(this.getCurrentPlayer()).get(index).getValue() == TakiCard.Value.TakeTwo) {
			this.addToTrashStack(this.getDeck(this.getCurrentPlayer()).get(index));
			this.getDeck(this.getCurrentPlayer()).remove(index);
			addToPlayer.add(Kupa.takeCard());
			addToPlayer.add(Kupa.takeCard());

			return true;
		}
		return false;
	}

	public boolean take2SeriesByCard(TakiCard card) {
		if (card.getValue() == TakiCard.Value.TakeTwo) {
			addToPlayer.add(Kupa.takeCard());
			addToPlayer.add(Kupa.takeCard());

			return true;
		}
		return false;
	}

	public int dropCard(int selectedCardIndex) {

		boolean endTurnOnFinish = true;
		if (selectedCardIndex == -1)
			return 0;
		if (this.submitPlayerCard(this.getDeck(this.getCurrentPlayer()).get(selectedCardIndex)) == false) {
			return 0;
		}

		if (this.getDeck(this.getCurrentPlayer()).get(selectedCardIndex).getValue() == TakiCard.Value.TakeTwo) {
			take2Series(selectedCardIndex);
			return 3;
		}
		//
		if (this.getDeck(this.getCurrentPlayer()).get(selectedCardIndex).getValue() == TakiCard.Value.Stop
				|| this.getDeck(this.getCurrentPlayer()).get(selectedCardIndex)
						.getValue() == TakiCard.Value.ChangeDirection
				|| this.getDeck(this.getCurrentPlayer()).get(selectedCardIndex).getValue() == TakiCard.Value.Plus)
			endTurnOnFinish = false;

		if (this.getDeck(this.getCurrentPlayer()).get(selectedCardIndex).getValue() == TakiCard.Value.SuperTaki
				|| this.getDeck(this.getCurrentPlayer()).get(selectedCardIndex).getValue() == TakiCard.Value.Taki) {
			this.addToTrashStack(this.getDeck(this.getCurrentPlayer()).get(selectedCardIndex));
			this.getDeck(this.getCurrentPlayer()).remove(selectedCardIndex);
			// this.removeAllColors();
			return 2;
		} else {
			this.addToTrashStack(this.getDeck(this.getCurrentPlayer()).get(selectedCardIndex));
			this.getDeck(this.getCurrentPlayer()).remove(selectedCardIndex);
		}
		if (endTurnOnFinish == true)
			return 1;
		else if (endTurnOnFinish == false)
			return 0;
		return 2;

	}

	public void checkIfEmptyKupa() {
		if (this.Kupa.isEmpty()) {
			Kupa.recharge(TrashStack);
			TrashStack.clear();
		}
	}

	public int dropCardByCard(TakiCard card) {
		boolean endTurnOnFinish = true;

		if (this.submitPlayerCard(card) == false) {
			return 0; // invalid card
		}

		if (card.getValue() == TakiCard.Value.TakeTwo) {
			take2SeriesByCard(card);
			return 3; // take 2
		}
		//
		if (card.getValue() == TakiCard.Value.Stop || card.getValue() == TakiCard.Value.ChangeDirection
				|| card.getValue() == TakiCard.Value.Plus)
			endTurnOnFinish = false; // another turn

		if (card.getValue() == TakiCard.Value.SuperTaki || card.getValue() == TakiCard.Value.Taki) {
			// this.removeAllColors();
			return 2;
		}
		if (endTurnOnFinish == true)
			return 1; // valid move
		else if (endTurnOnFinish == false)
			return 0;
		return 2;

	}

	public void finishTurn(boolean endTurnOnFinish) {
		if (endTurnOnFinish == true)
			this.endTurn();
	}

	public TakiCard.Color getColorByString(String color) {
		if (color.equals("Red"))
			return TakiCard.Color.Red;
		else if (color.equals("Blue"))
			return TakiCard.Color.Blue;
		if (color.equals("Green"))
			return TakiCard.Color.Green;
		return TakiCard.Color.Yellow;
	}

	public void startTaki(int index) {
		if (index == -1)
			return;
		if (this.getDeck(this.getCurrentPlayer()).get(index).getColor() == this.getValidColor()) {
			this.addToTrashStack(this.getDeck(this.getCurrentPlayer()).get(index));
			this.getDeck(this.getCurrentPlayer()).remove(index);
		}
		this.validValue = this.getLastCardStack().getValue();
	}

	public boolean submitPlayerCard(TakiCard card) {
		if (validCardPlay(card)) {
			validColor = card.getColor();
			validValue = card.getValue();
			if (card.getValue() == TakiCard.Value.ChangeDirection || card.getValue() == TakiCard.Value.Stop
					|| card.getValue() == TakiCard.Value.Plus) {
				return true;
			}
			return true;
		}

		else if (card.getColor() == TakiCard.Color.ChangeColor) {
			{
				if (getCurrentPlayer() == 1 && isAI == 1)
				{
					this.validColor = gvars.getAIchosenColor();
					this.validValue = null;
					System.out.println("valid " + validColor);
				}
				else
				{
				String[] options = { "Red", "Blue", "Green", "Yellow" };
				try {
					Object searchType = JOptionPane.showInputDialog(null, null, "Choose a color ",
							JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
					this.validColor = getColorByString(searchType.toString());
				} catch (NullPointerException e) {
					return false;
				}
				this.validValue = null;
				System.out.println("valid " + validColor);
				}
			}
			return true;
		}

		return false;
	}

}
