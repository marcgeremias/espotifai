package business;

import business.entities.User;
import persistence.UserDAO;
import persistence.postgresql.UserSQL;

public class UserManager {
    private UserDAO userDAO;

    public UserManager(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

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
                return 0;
            }
        } else {
            return -1;
        }
    }
}