package business;

import business.entities.User;
import persistence.UserDAO;
import persistence.UserDAOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserManager {
    private UserDAO userDAO;
    private String currentUser;

    // Defining constants of possible results
    public static final int WRONG_USER = -1;
    public static final int WRONG_PASSWORD = 0;
    public static final int USER_CORRECTLY_ADDED = 1;
    public static final int WRONG_USERNAME = 2;
    public static final int WRONG_EMAIL = 3;
    public static final int WRONG_CONFIRM_PASSWORD = 4;
    public static final int ERROR_CREATING_USER = 5;

    public UserManager(UserDAO userDAO) {
        this.userDAO = userDAO;
        this.currentUser = null;
    }

    /*public User getCurrentUser() {
        return currentUser;
    }*/

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

        } catch (UserDAOException ex) {
            ex.printStackTrace();
        }

        if (user != null) {
            // Correct User
            if (Crypt.decode(user.getPassword()).equals(passwordField)) {
                // Correct password and validation completed
                return USER_CORRECTLY_ADDED;
            } else {
                // Incorrect password
                return WRONG_PASSWORD;
            }
        } else {
            // Incorrect user
            return WRONG_USER;
        }
    }

    /**
     * Method that checks all the data provided in the text fields
     * @param username the username the user provided in the text field
     * @param email the email the user provided in the text field
     * @param password the password the user provided in the password field
     * @param confirmPassword the confirmPassword the user provided in the password field
     * @return an int indicating what information is wrong or if it's all correct
     */
    public int checkSignUpIdentification(String username, String email, char[] password, char[] confirmPassword) {
        ArrayList<User> users = null;
        try {
            users = userDAO.getAllUsers();

        } catch (UserDAOException ex) {
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

        // All the data introduced is valid, so we must save the user into the database
        User newUser = new User(username, email, String.valueOf(password));
        try {
            if (userDAO.createUser(newUser)) {
                return USER_CORRECTLY_ADDED;
            } else {
                return ERROR_CREATING_USER;
            }
        } catch (UserDAOException e) {
            return ERROR_CREATING_USER;
        }

    }

    /**
     * Method that checks if the email provided is a valid email
     * @param email the email provided by the user
     * @return true if the email is valid and false if it is not
     */
    private boolean checkValidEmail(String email) {
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(email);

        return mather.find();
    }

    /**
     * Method that checks if the password provided is a valid password
     * (Min 8 length, 1 UpperCase, 1 LowerCase and 1 number)
     * @param password the password introduced by the user
     * @return true if the password is valid and false if it is not
     */
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

    public void setCurrentUser(String user) {
        currentUser = user;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    /**
     * Method that deletes the current user from RAM
     */
    public void logOutUser(){
        this.currentUser = null;
    }
}