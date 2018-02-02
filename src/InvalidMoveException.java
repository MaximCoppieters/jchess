
public class InvalidMoveException extends RuntimeException {
	
	String message;
	
	public InvalidMoveException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
