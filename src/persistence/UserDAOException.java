package persistence;

/**
 * Public class for the generation of custom exceptions
 */
public class UserDAOException extends Exception {
    /**
     * Default constructor for the classe that relays a message given by params
     * @param message String containing the error message
     */
    public UserDAOException(String message) {
        super(message);
    }
}