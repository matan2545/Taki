
public class InvalidValueSubmissionException extends Exception{
	private TakiCard.Value expected;
	private TakiCard.Value valid;
	
	public InvalidValueSubmissionException(String message, TakiCard.Value valid, TakiCard.Value expected) {
		this.valid = valid;
		this.expected = expected;
	}

}
