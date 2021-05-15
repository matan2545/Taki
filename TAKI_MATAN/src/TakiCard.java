
public class TakiCard {
	enum Color {
		Red, Blue, Green, Yellow, ChangeColor;

		private static final Color[] colors = Color.values();

		public static Color getColor(int i) {
			return Color.colors[i];
		}
	}

	enum Value {
		One, Two, Three, Four, Five, Six, Seven, Eight, Nine, TakeTwo, Stop, Plus, Taki, ChangeDirection, SuperTaki,
		ChangeColor;

		private static final Value[] values = Value.values();

		public static Value getValue(int i) {
			return Value.values[i];
		}
	}

	private final Color color;
	private final Value value;

	public TakiCard(final Color color, final Value value) {
		// Builder
		this.color = color;
		this.value = value;
	}

	public Color getColor() {
		// Function returns the card color
		return this.color;
	}

	public Value getValue() {
		// Function returns the card value
		return this.value;
	}

	public String toString() {
		// Function prints the card
		return color + "_" + value;
	}
}
