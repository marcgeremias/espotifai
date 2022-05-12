package persistence.postgresql;

import business.Crypt;
import business.entities.User;
import persistence.UserDAOException;
import persistence.UserDAO;
import persistence.config.DBConstants;
import persistence.config.DBConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Public class that implements {@link UserDAO} interface with SQL persistence in the database configured on start-up.
 * @see User
 * @see UserDAO
 * @see DBConfig
 * @see DBConstants
 */
public class UserSQL implements UserDAO {

    private static final String ENCRYPTION_TYPE = "bf";

    /**
     * Public method to create a new user in the database system. The function recieves a instance of {@link User}
     * and it inserts into the database the values inside.
     * @param user instance of new {@link User} to save to database
     * @return true if query was successful or false if nickname is already taken
     * @throws UserDAOException if the query fails to execute correctly (i.e. the nickname or mail are already in use)
     */
    @Override
    public boolean createUser(User user) throws UserDAOException {
        try {
            Connection c = DBConfig.getInstance().openConnection();

        String createUserSQL = "INSERT INTO "+ DBConstants.TABLE_USER +"("+ DBConstants.COL_ID_NICKNAME
                +", "+ DBConstants.USER_COL_MAIL +", "+ DBConstants.USER_COL_PASSWORD
                +") VALUES (?, ?, ?)";
        PreparedStatement createUserSTMT = c.prepareStatement(createUserSQL);
        createUserSTMT.setString(1, user.getName());
        createUserSTMT.setString(2, user.getEmail());
        createUserSTMT.setString(3, Crypt.encode(user.getPassword()));
        int count = createUserSTMT.executeUpdate();

            c.close();
            return count > 0;
        } catch (SQLException e) {
            throw new UserDAOException(e.getMessage());
        }
    }

    /**
     * Gets the user matching the given identifier in the params. Note that the identifier is a String and
     * it corresponds to a unique value in the database.
     * @param userID String identifier of the user we want to retrieve
     * @return instance of {@link User} matching the given identifier
     * @throws UserDAOException if the query fails to execute correctly
     */
    @Override
    public User getUserByID(String userID) throws UserDAOException {
        try {
            Connection c = DBConfig.getInstance().openConnection();

            String selectUserSQL = "SELECT * FROM " + DBConstants.TABLE_USER + " WHERE " + DBConstants.COL_ID_NICKNAME + " = ?";
            PreparedStatement selectUserSTMT = c.prepareStatement(selectUserSQL);
            selectUserSTMT.setString(1, userID);
            ResultSet rs = selectUserSTMT.executeQuery();
        /*
        |   Col 1       |   Col 2   |   Col 3   |
        | id_nickname   |   mail    | password  |
         */
            User user = null;
            if (rs.next()) {
                user = new User(rs.getString(1), rs.getString(2), rs.getString(3));
            }
            c.close();
            return user;
        } catch (SQLException e) {
            throw new UserDAOException(e.getMessage());
        }
    }

    /**
     * Gets all users saved on the database.
     * @return List of {@link User}
     * @throws UserDAOException if query fails to execute correctly
     */
    @Override
    public ArrayList<User> getAllUsers() throws UserDAOException {
        try {
            Connection c = DBConfig.getInstance().openConnection();
            ArrayList<User> users = new ArrayList<>();

            String selectUsersSQL = "SELECT * FROM " + DBConstants.TABLE_USER;
            PreparedStatement selectUsersSTMT = c.prepareStatement(selectUsersSQL);
            ResultSet rs = selectUsersSTMT.executeQuery();
         /*
        |   Col 1       |   Col 2   |   Col 3   |
        | id_nickname   |   mail    | password  |
         */
            while (rs.next()) {
                User user = new User(rs.getString(1), rs.getString(2), rs.getString(3));
                users.add(user);
            }

            c.close();
            return users;
        } catch (SQLException e) {
            throw new UserDAOException(e.getMessage());
        }
    }

    /**
     * Given a new instance of a {@link User}, it updates the values in the database corresponding to the identifier of
     * the instance with the new values. <b>Note</b> that ALL fields of the user table will be afected so it's important
     * to make sure that values that you do not wish to change are passed correctly as well.
     * @param user instance of {@link User} with the new values to update.
     * @return true if the query was successful or false if nickname is incorrect
     * @throws UserDAOException if the query fails to execute correctly or the database connection failed.
     */
    @Override
    public boolean updateUser(User user) throws UserDAOException {
        try {
            Connection c = DBConfig.getInstance().openConnection();

            String updateUserSQL = "UPDATE " + DBConstants.TABLE_USER + " SET " + DBConstants.COL_ID_NICKNAME + " = ?," +
                    DBConstants.USER_COL_MAIL + " = ?, " + DBConstants.USER_COL_PASSWORD + " = crypt(?, gen_salt(?)) " +
                    "WHERE " + DBConstants.COL_ID_NICKNAME + " = ?";
            PreparedStatement updateUserSTMT = c.prepareStatement(updateUserSQL);
            updateUserSTMT.setString(1, user.getName());
            updateUserSTMT.setString(2, user.getEmail());
            updateUserSTMT.setString(3, user.getPassword());
            updateUserSTMT.setString(4, ENCRYPTION_TYPE);
            updateUserSTMT.setString(5, user.getName());
            int count = updateUserSTMT.executeUpdate();

            c.close();
            return count > 0;
        } catch (SQLException e) {
            throw new UserDAOException(e.getMessage());
        }
    }

    /**
     * This method will delete all relations in the database of the given the identifier of the user.
     * @param userID user unique identifier as a String
     * @return true if the userID exists, false if it doesn't
     * @throws UserDAOException if the query fails to execute correctly or database connection can't be opened.
     */
    @Override
    public boolean deleteUser(String userID) throws UserDAOException {
        try {
            Connection c = DBConfig.getInstance().openConnection();

            String deleteUserSQL = "DELETE FROM " + DBConstants.TABLE_USER + " WHERE " + DBConstants.COL_ID_NICKNAME + " = ?";
            PreparedStatement deleteUserSTMT = c.prepareStatement(deleteUserSQL);
            deleteUserSTMT.setString(1, userID);
            int count = deleteUserSTMT.executeUpdate();

            c.close();
            return count > 0;
        } catch (SQLException e) {
            throw new UserDAOException(e.getMessage());
        }
    }

    /**
     * This method will recieve a String containing either a mail address or a nickname. Either case it will query
     * the database and if a match exists (not distinguishing if mail matched or nickname matched) will return true,
     * else it will return false.
     * @param key String containing mail or nickname (we don't distinguish)
     * @return instance of user found or null pointer
     * @throws UserDAOException if the query fails to execute correctly or the database connection can't be opened.
     */
    @Override
    public User validateUser(String key) throws UserDAOException {
        try {
            Connection c = DBConfig.getInstance().openConnection();

            String validateUserSQL = "SELECT * FROM " + DBConstants.TABLE_USER + " WHERE " + DBConstants.COL_ID_NICKNAME + " = ? " +
                    "OR " + DBConstants.USER_COL_MAIL + " = ?";
            PreparedStatement validateUserSTMT = c.prepareStatement(validateUserSQL);
            validateUserSTMT.setString(1, key);
            validateUserSTMT.setString(2, key);
            ResultSet rs = validateUserSTMT.executeQuery();

            User user = null;
            if (rs.next()) {
                user = new User(rs.getString(1), rs.getString(2), rs.getString(3));
            }

            c.close();
            return user;
        } catch (SQLException e) {
            throw new UserDAOException(e.getMessage());
        }
    }
}