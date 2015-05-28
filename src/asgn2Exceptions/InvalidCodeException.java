package asgn2Exceptions;


/**
 * Exception thrown when an attempt is made to construct an
 * invalid container code.
 * 
 * @author CAB302 Yunkai Zhu
 * @version 1.0
 */
@SuppressWarnings("serial") // We're not interested in binary i/o here
public class InvalidCodeException extends CargoException {

	/**
	 * Constructs a new InvalidCodeException object.
	 * 
	 * @param message an informative message describing the problem encountered
	 */
	public InvalidCodeException(String message) {
		super("InvalidCodeException: " + message);
	}

}
