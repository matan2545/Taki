import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class TakiDeck {

	// * 2 each number = 18*4 = 72
	// * 2 stop = 2
	// * 2 change direction = 2
	// * 2 taki each color = 8
	// * 2 super taki = 2
	// * 2 plus each color = 8
	// * 4 change color = 4

	private ArrayList<TakiCard> cards;

	public TakiDeck() {
		cards = new ArrayList<TakiCard>();
	}

	public void reset() {
		TakiCard.Color[] colors = TakiCard.Color.values();

		for (int i = 0; i < colors.length - 1; i++) {
			TakiCard.Color color = colors[i]; // insert colors values except ChangeD
			for (int j = 0; j < 9; j++) // insert 2 numbers
			{
				cards.add(new TakiCard(color, TakiCard.Value.getValue(j)));
				cards.add(new TakiCard(color, TakiCard.Value.getValue(j)));
			}

			TakiCard.Value[] values = new TakiCard.Value[] { TakiCard.Value.TakeTwo, TakiCard.Value.Stop,
					TakiCard.Value.Plus, TakiCard.Value.Taki, TakiCard.Value.ChangeDirection };
			for (TakiCard.Value value : values) // insert 2 take2, skips, stops, taki , change direction for each color
			{
				cards.add(new TakiCard(color, value));
				cards.add(new TakiCard(color, value));
			}
		}

		TakiCard.Value[] values = new TakiCard.Value[] { TakiCard.Value.SuperTaki, TakiCard.Value.ChangeColor };
		for (TakiCard.Value value : values) {
			for (int i = 0; i < 4; i++) {
				cards.add(new TakiCard(TakiCard.Color.ChangeColor, value));

			}
		}
		System.out.println(cards.size());

	}

	public void recharge(Stack<TakiCard> stk)
	{
		while (!stk.isEmpty())
		{
			cards.add(stk.pop());
		}
	}
	public boolean isEmpty() {
		return cards.size() == 0;
	}

	public void shuffle() {

		for (int i = 0; i < 4; i++)
			Collections.shuffle(cards);

	}

	public int getSize() {
		return this.cards.size();
	}
	
	public void printCards()
	{
		for (int i = 0; i< this.getSize(); i++)
		{
			System.out.println(this.cards.get(i));
		}
	}

	public TakiCard takeCard() throws IllegalArgumentException {
		if (isEmpty())
			throw new IllegalArgumentException("Cannot take a card, deck is empty");
		TakiCard temp = cards.get(cards.size() - 1);
		cards.remove(cards.size() - 1);
		return temp;
	}

	public ArrayList<TakiCard> takeCard(int n) // take multiple cards
	{
		ArrayList<TakiCard> ret = new ArrayList<TakiCard>();
		for (int i = 0; i < n; i++) {
			ret.add(cards.get(cards.size() - 1));
			cards.remove(cards.size() - 1);
		}
		return ret;
	}
}
