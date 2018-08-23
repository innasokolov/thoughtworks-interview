package conference;

/**
 * Created by innasokolov on 8/25/16.
 */
public class InvalidInputException extends RuntimeException {
    public InvalidInputException() { }
    public InvalidInputException(String message) {
        super(message);
    }
    public InvalidInputException(String message, Object ... args) {
        super(String.format(message, args));
    }
    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
