import java.util.ArrayList;

public class AI_Player {
	private int bestIndex; // The chosen index
	private int score; // The score for the AI
	private boolean isTake2; // If take2 is on
	boolean isTaki; // If taki is on
	
	private ArrayList<TakiCard> myDeck; // AI's deck
	private TakiCard.Color validColor; // Valid color
	private TakiCard.Value validValue; // Valid value
	private TakiCard.Color bestColor; // The chosen color (for SuperTaki / ChangeColor)


	public AI_Player(ArrayList<TakiCard> deck, TakiCard.Value validValue, TakiCard.Color validColor, boolean isTake2,
			boolean isTaki) {
		// Builder
		this.myDeck = deck;
		this.validColor = validColor;
		this.validValue = validValue;
		this.isTake2 = isTake2;
		this.isTaki = isTaki;
	}

	public int howMuchByColor(TakiCard.Color color) {
		// Function gets a color
		// Function returns how much cards from this color
		int sumSameColor = 0;
		for (int i = 0; i < myDeck.size(); i++) {
			if (myDeck.get(i).getColor() == color)
				sumSameColor++;
		}
		return sumSameColor;
	}

	public TakiCard.Color findMaxColorInDeck() {
		// Function returns how much cards from this color
		int maxSum = howMuchByColor(TakiCard.Color.Red);
		TakiCard.Color maxColor = TakiCard.Color.Red;

		if (howMuchByColor(TakiCard.Color.Green) > maxSum)
			maxColor = TakiCard.Color.Green;

		if (howMuchByColor(TakiCard.Color.Blue) > maxSum)
			maxColor = TakiCard.Color.Blue;

		if (howMuchByColor(TakiCard.Color.Yellow) > maxSum)
			maxColor = TakiCard.Color.Yellow;

		return maxColor;
	}

	public int findCardindex(TakiCard.Value value) {
		// Function gets a value
		// Function a card with the same value or -1
		for (int i = 0; i < myDeck.size(); i++) {
			if (myDeck.get(i).getValue() == value)
				return i;
		}
		return -1;
	}

	public boolean isOnlyOneColorLeft(TakiCard.Color colorToCheck) {
		// Function gets a color
		// Function returns true or false if there is only one card left in this color
		int sum = 0;
		for (int i = 0; i < myDeck.size(); i++) {
			if (myDeck.get(i).getColor() == colorToCheck)
				sum++;
		}
		if (sum == myDeck.size())
			return true;
		return false;

	}

	public int findNotSameColorButSameValueIndex() {
		// Function returns the index of the same value card
		int maxInThisColor = 0;
		int returnIndex = -1;
		for (int i = 0; i < myDeck.size(); i++) {
			if (myDeck.get(i).getValue() == validValue && myDeck.get(i).getColor() != validColor) {
				if (howMuchByColor(myDeck.get(i).getColor()) > maxInThisColor) {
					maxInThisColor = howMuchByColor(myDeck.get(i).getColor());
					returnIndex = i;
				}
			}
		}
		return returnIndex;
	}

	public int moreTurnCardIndexSameColor() {
		// Function returns the index of a Stop/ChangeDirection/Plus card
		for (int i = 0; i < myDeck.size(); i++) {
			if ((myDeck.get(i).getValue() == TakiCard.Value.Stop
					|| myDeck.get(i).getValue() == TakiCard.Value.ChangeDirection
					|| myDeck.get(i).getValue() == TakiCard.Value.Plus) && myDeck.get(i).getColor() == validColor) {
				return i;
			}

		}
		return -1;
	}

	public int TakiIndexSameColor() {
		// Function returns the index of Taki card
		for (int i = 0; i < myDeck.size(); i++) {
			if (myDeck.get(i).getValue() == TakiCard.Value.Taki && myDeck.get(i).getColor() == validColor) {
				return i;
			}

		}
		return -1;
	}

	public int getTake2IndexSameColor() {
		// Function returns the index of TakeTwo card
		for (int i = 0; i < myDeck.size(); i++) {
			if (myDeck.get(i).getValue() == TakiCard.Value.TakeTwo && myDeck.get(i).getColor() == validColor) {
				return i;
			}

		}
		return -1;
	}

	public int getRegValidCardIndexSameColor() {
		// Function returns same color card index
		for (int i = 0; i < myDeck.size(); i++) {
			if (myDeck.get(i).getColor() == validColor)
				return i;
		}
		return -1;
	}

	public int findTake2Index() {
		// Function returns TakeTwo card index
		for (int i = 0; i < myDeck.size(); i++) {
			if (myDeck.get(i).getValue() == validValue)
				return i;
		}
		return -1;

	}

	public int ChooseBestIndex() {
		// Function returns the best index for the player
		//// update my deck
		score = 0;
		bestColor = null;

		int sumValidColors = howMuchByColor(validColor);
		// MORE THAN 1 VALID CARD BY COLOR
		if (isTake2 == true) {
			return findTake2Index();
		}
		if (validValue == TakiCard.Value.TakeTwo)
			if (findTake2Index() != -1)
				return findTake2Index();
		if (sumValidColors > 0 && isOnlyOneColorLeft(validColor) && TakiIndexSameColor() != -1) // 1 color left
			return TakiIndexSameColor();
		if (sumValidColors > 1) // There are more than 1 same color cards
		{
			if (getTake2IndexSameColor() != -1 && score < 5) // if there is take 2
			{
				score = 5;
				bestIndex = getTake2IndexSameColor();
			}
			if (moreTurnCardIndexSameColor() != -1 && getTake2IndexSameColor() == -1 && score < 10) // Stop / change
																									// direction / plus
																									// and no take 2
			{
				score = 10;
				bestIndex = moreTurnCardIndexSameColor();
			}
			if (moreTurnCardIndexSameColor() != -1 && getTake2IndexSameColor() != -1 && score < 15) // Stop / change
																									// direction / plus
																									// and yes take 2
			{
				score = 15;
				bestIndex = moreTurnCardIndexSameColor();
			}
			if (TakiIndexSameColor() != -1 && score < 20) // there is a taki card
			{
				score = 20;
				bestIndex = TakiIndexSameColor();
			}
			if (score == 0) // no good option
			{
				score = 1;
				bestIndex = getRegValidCardIndexSameColor();
			}
		}

		// JUST 1 VALID CARD BY COLOR
		else if (sumValidColors == 1) {
			score = 30;
			bestIndex = getRegValidCardIndexSameColor(); // free all valid colors
		}
		// NO VALID BY COLOR OPTIONS
		else if (sumValidColors == 0) {
			if (findNotSameColorButSameValueIndex() != -1 && score < 10) {
				score = 15;
				bestIndex = findNotSameColorButSameValueIndex();
			}
			if (howMuchByColor(TakiCard.Color.ChangeColor) > 0) // none color card
			{
				this.bestColor = findMaxColorInDeck();
				if (findCardindex(TakiCard.Value.ChangeColor) != -1 && score < 10) {
					score = 10;
					bestIndex = findCardindex(TakiCard.Value.ChangeColor);
				}
				if (findCardindex(TakiCard.Value.SuperTaki) != -1 && score < 20) {
					score = 20;
					bestIndex = findCardindex(TakiCard.Value.SuperTaki);
				}
			}
		}

		if (score == 0 && isTaki == false)
			return -1;
		if (score == 0 && isTaki == true)
			return -2; // end my taki

		return bestIndex;
	}

	public TakiCard.Color getBestColor() {
		// Function returns the chosen color
		return this.bestColor;
	}

}
