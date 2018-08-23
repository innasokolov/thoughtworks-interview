package conference;

/**
 * Created by innasokolov on 8/25/16.
 */
public class TrackIsFullException extends Exception {
    public TrackIsFullException() { }
    public TrackIsFullException(String message) {
        super(message);
    }
    public TrackIsFullException(String message, Object ... args) {
        super(String.format(message, args));
    }
    public TrackIsFullException(String message, Throwable cause) {
        super(message, cause);
    }
}
