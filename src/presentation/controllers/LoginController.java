package presentation.controllers;

import business.UserManager;
import presentation.views.LoginView;
import presentation.views.MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements ActionListener {

    private LoginView loginView;
    private MainViewListener listener;
    private UserManager userManager;

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
                listener.changeView(MainView.CARD_SIGN_UP);
                break;
            case LoginView.BTN_LOG_IN:
                onLoginClick();
                break;
            default:
                System.err.println("Unknown action command " + e.getActionCommand());
        }
    }

    /**
     * Method that performs the actions when the user clicks the login button
     */
    private void onLoginClick() {
        int validationResult = userManager.checkUserAndPassword(loginView.getUserField(), loginView.getPasswordField());
        if (validationResult == UserManager.CORRECT_CHECKING) {
            // Correct credentials
            userManager.setCurrentUser(loginView.getUserField());
            loginView.correctCredentials();
            listener.changeView(MainView.CARD_PLAYER);

        } else {
            if (validationResult == UserManager.WRONG_PASSWORD) {
                // Correct Username but not correct password
                loginView.incorrectPassword();

            } else {
                // Incorrect Username or Email
                loginView.incorrectUser();
            }
        }
    }
}