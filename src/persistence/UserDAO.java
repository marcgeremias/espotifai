package persistence;

import business.entities.User;

import java.util.ArrayList;

/**
 * Public interface that manages {@link User} persistence in the system.
 * @see User
 */
public interface UserDAO {

    /**
     * This method will create a new {@link User} in the system storage given the user passed in parameters.
     * @param user new user to persist in the system
     * @return true (1) if a new user has been added to the system or (2) false if the user's nickname already
     * exists.
     * @throws UserDAOException if there is an error storing the data.
     */
    boolean createUser(User user) throws UserDAOException;

    /**
     * This method will query the system with the given String parameter (userID).
     * @param userID String containing the unique identifier of the user in the system.
     * @return instance of {@link User} (1) if the identifier matches a user in the system or <b>null</b> if the
     * identifier doesn't match any user.
     * @throws UserDAOException if there is an error storing the data.
     */
    User getUserByID(String userID) throws UserDAOException;

    /**
     * This method will query all users stored in the system.
     * @return (1) List of {@link User} stored in the system or (2) <b>null</b> if there are no users in the system.
     * @throws UserDAOException if there is an error storing the data.
     */
    ArrayList<User> getAllUsers() throws UserDAOException;

    /**
     * This method will update the values of a user stored in the storage system.
     * @param user instance of {@link User} containing the new values to persist.
     * @return true (1) if the updated has been executed correctly or (2) false if the user updated doesn't match
     * with any user stored in the system.
     * @throws UserDAOException if there is an error storing the data.
     */
    boolean updateUser(User user) throws UserDAOException;

    /**
     * This method will delete a user from the storage system given its unique identifier.
     * @param userID unique identifier of the user we want to delete.
     * @return true (1) if the user exists and the query was successful or false (2) if the unique identifier didn't
     * match with any user stored in the system.
     * @throws UserDAOException if there is an error storing the data.
     */
    boolean deleteUser(String userID) throws UserDAOException;

    /**
     * This method will check if a user exists in the system given its unique identifier or its email account.
     * @param key String containing either the nickname or the email of the user.
     * @return Instance of {@link User} (1) if the key matches any user in the system or <b>null</b> (2) if the
     * key doesn't match with any value in the storage system.
     * @throws UserDAOException if there is an error storing the data.
     */
    User validateUser(String key) throws UserDAOException;

}
