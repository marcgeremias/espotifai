package business;

import business.entities.User;
import persistence.UserDAO;

public class UserManager {
    private UserDAO userDAO;

    public UserManager(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Method that checks from the database the username or email and the password
     * of the Strings introduced in the textFields in the Login View
     * @param userField the username or email to check
     * @param passwordField the password to check
     * @return
     */
    public int checkUserAndPassword(String userField, String passwordField) {
        User user = null;
        try {
            user = userDAO.validateUser(userField);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (user != null) {
            // Correct User
            if (user.getPassword().equals(passwordField) /*Desencriptar!!??*/) {
                // Correct password and validation completed
                return 1;
            } else {
                // Incorrect password
                return 0;
            }
        } else {
            // Incorrect user
            return -1;
        }
    }
}