package persistence;

/**
 * Public class for the generation of custom exceptions
 */
public class SongDAOException extends Exception {
    /**
     * Default constructor that relays a message given by params
     * @param message String containing the error message
     */
    public SongDAOException(String message) {
        super(message);
    }
}