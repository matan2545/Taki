
public class Globals {
	public static TakiCard.Color AIchosenColor;
	
	public void setAIchosenColor(TakiCard.Color c)
	{
		// Function gets a color
		// Function set the global variable to the chosen color
		Globals.AIchosenColor = c;
	}
	public TakiCard.Color getAIchosenColor()
	{
		// Function returns the global variable
		return Globals.AIchosenColor;
	}
}
