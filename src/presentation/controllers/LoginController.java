package presentation.controllers;

import business.UserManager;
import presentation.views.LoginView;
import presentation.views.MainView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Public class for the log in controller
 */
public class LoginController implements ActionListener, KeyEventDispatcher {

    private LoginView loginView;
    private MainViewListener listener;
    private UserManager userManager;

    private long enterKeyTimeElapsed;

    /**
     * Public constructor for LoginController instance
     * @param listener main listener to report change view commands
     * @param loginView managed view
     * @param userManager manager instance to perform logic
     */
    public LoginController(MainViewListener listener, LoginView loginView, UserManager userManager) {
        this.listener = listener;
        this.loginView = loginView;
        this.userManager = userManager;
    }

    /**
     * Method of the interface ActionListener that does all the appropriate actions when a button is pressed
     * @param e the action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case LoginView.BTN_CREATE_ACCOUNT:
                // We change to the create account view and clear the login info
                listener.changeView(MainView.CARD_SIGN_UP);
                loginView.clearAllInfo();
                break;
            case LoginView.BTN_LOG_IN:
                onLoginClick();
                break;
            default:
                System.err.println("Unknown action command " + e.getActionCommand());
        }
    }

    /*
     * Method that performs the actions when the user clicks the login button
     */
    private void onLoginClick() {
        // We check the user and password of the text fields and get a result
        int validationResult = userManager.checkUserAndPassword(loginView.getUserField(), loginView.getPasswordField());

        if (validationResult == UserManager.USER_CORRECTLY_ADDED) {
            // Correct credentials
            userManager.setCurrentUser(loginView.getUserField());
            loginView.correctCredentials();
            loginView.clearFields();
            listener.changeView(MainView.CARD_PLAYER);

        } else {
            if (validationResult == UserManager.WRONG_PASSWORD) {
                // Correct Username but not correct password
                loginView.incorrectPassword();

            } else {
                if (validationResult == UserManager.WRONG_USER) {
                    // Incorrect Username or Email
                    loginView.incorrectUser();

                } else {
                    loginView.errorAddingUser();
                }
            }
        }
    }

    /**
     * Method that manages key press to log in with enter key
     * @param e event
     * @return false
     */
    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_ENTER && enterKeyTimeElapsed == 0){
            onLoginClick();
            enterKeyTimeElapsed = System.currentTimeMillis();
        } else if (System.currentTimeMillis() - enterKeyTimeElapsed >= 1000){
            enterKeyTimeElapsed = 0;
        }
        return false;
    }
}