package business;

import business.entities.User;
import persistence.UserDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserManager {
    private UserDAO userDAO;

    // Defining constants of possible results
    public static final int WRONG_USER = -1;
    public static final int WRONG_PASSWORD = 0;
    public static final int CORRECT_CHECKING = 1;
    public static final int WRONG_USERNAME = 2;
    public static final int WRONG_EMAIL = 3;
    public static final int WRONG_CONFIRM_PASSWORD = 4;


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
                return CORRECT_CHECKING;
            } else {
                // Incorrect password
                return WRONG_PASSWORD;
            }
        } else {
            // Incorrect user
            return WRONG_USER;
        }
    }

    public int checkSignUpIdentification(String username, String email, char[] password, char[] confirmPassword) {
        ArrayList<User> users = null;
        try {
            users = userDAO.getAllUsers();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Check if the username field is not empty
        if (username.equals("")) {
            return WRONG_USERNAME;
        }

        boolean repeatedEmail = false;
        // Searching for possible repeated usernames or emails on the database
        for (int i = 0; i < (users != null ? users.size() : 0); i++) {
            if (username.equals(users.get(i).getName())) {
                return WRONG_USERNAME;
            }

            if (email.equals(users.get(i).getEmail())) {
                repeatedEmail = true;
            }
        }
        // If the username if correct but not the email, return email error
        if (repeatedEmail || !checkValidEmail(email)) {
            return WRONG_EMAIL;
        }

        if (!checkPasswordStandards(password)) {
            return WRONG_PASSWORD;
        }

        // We have to check if the confirmation password field coincide with the password field
        if (!Arrays.equals(confirmPassword, password)) {
            return WRONG_CONFIRM_PASSWORD;
        }

        return CORRECT_CHECKING;
    }


    private boolean checkValidEmail(String email) {
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(email);

        return mather.find();
    }

    private boolean checkPasswordStandards(char[] password) {
        boolean isUpperCase = false;
        boolean isLowerCase = false;
        boolean isDigit = false;

        // Now we must check the password format
        if (password.length < 8) {
            return false;
        }

        for (char c : password) {
            if (Character.isUpperCase(c)) {
                isUpperCase = true;
            } else {
                if (Character.isLowerCase(c)) {
                    isLowerCase = true;
                } else {
                    if (Character.isDigit(c)) {
                        isDigit = true;
                    }
                }
            }
        }

        return isDigit && isUpperCase && isLowerCase;
    }
}