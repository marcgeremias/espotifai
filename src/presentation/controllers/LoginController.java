package presentation.controllers;

import business.UserManager;
import presentation.views.LoginView;
import presentation.views.MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements ActionListener {

    private LoginView loginView;
    private ChangeViewListener listener;
    private UserManager userManager;

    public LoginController(ChangeViewListener listener, LoginView loginView, UserManager userManager) {
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
                // Show player card if data correct
                int validationResult = userManager.checkUserAndPassword(loginView.getUserField(), loginView.getPasswordField());
                if (validationResult == 1) {
                    // Correct credentials
                    loginView.correctCredentials();
                    listener.changeView(MainView.CARD_PLAYER);

                } else {
                    if (validationResult == 0) {
                        // Correct Username but not correct password
                        loginView.incorrectPassword();

                    } else {
                        // Incorrect Username or Email
                        loginView.incorrectUser();
                    }
                }

                break;
            default:
                System.err.println("Unknown action command " + e.getActionCommand());
        }
    }
}