package persistence;

/**
 * Public class that extends from Exception to create custom exceptions
 */
public class PlaylistDAOException extends Exception {
    /**
     * Default constructor that relays a message given by params
     * @param message String containing the error message
     */
    public PlaylistDAOException(String message) {
        super(message);
    }
}