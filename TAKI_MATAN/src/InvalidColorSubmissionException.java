
public class InvalidColorSubmissionException extends Exception{
	private TakiCard.Color expected;
	private TakiCard.Color valid;
	
	public InvalidColorSubmissionException(String message, TakiCard.Color valid, TakiCard.Color expected) {
		this.valid = valid;
		this.expected = expected;
	}

}
