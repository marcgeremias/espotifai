package presentation.controllers;

import business.UserManager;
import presentation.views.MainView;
import presentation.views.SignUpView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Public controller that controls the creation of a new {@link business.entities.User}
 */
public class SignUpController implements ActionListener {
    private SignUpView signUpView;
    private MainViewListener listener;
    private UserManager userManager;

    /**
     * Creates an instance of SignUpController
     * @param listener an instance of MainViewListener
     * @param signUpView an instance of SignUpView
     * @param userManager an instance of UserManager
     */
    public SignUpController(MainViewListener listener, SignUpView signUpView, UserManager userManager) {
        this.listener = listener;
        this.signUpView = signUpView;
        this.userManager = userManager;
    }

    /**
     * Method of the interface ActionListener that does all the appropriate actions when a button is pressed
     * @param e the action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case SignUpView.BTN_GO_BACK:
                // We change to the login card and clear the info
                signUpView.clearAllInfo();
                listener.changeView(MainView.CARD_LOG_IN);
                break;
            case SignUpView.BTN_JOIN:
                onJoinClick();
                break;
            default:
                System.err.println("Unknown action command " + e.getActionCommand());
        }
    }

    /*
     * Method that performs the actions when the user clicks the join button
     */
    private void onJoinClick() {
        // We reset the possible previous wrong introduced inputs
        signUpView.resetIncorrectInputs();

        // We check all the fields of the view and get a result
        int validationResult = userManager.checkSignUpIdentification(signUpView.getUsernameField(),
                signUpView.getEmailField(), signUpView.getPasswordField(), signUpView.getConfirmPasswordField());

        if (validationResult == UserManager.WRONG_USERNAME) {
            // Show wrong username error
            signUpView.incorrectUsername();
        } else {

            if (validationResult == UserManager.WRONG_EMAIL) {
                // Show wrong email error
                signUpView.incorrectEmail();
            } else {

                if (validationResult == UserManager.WRONG_PASSWORD) {
                    // Show wrong password error
                    signUpView.incorrectPassword();
                } else {

                    if (validationResult == UserManager.WRONG_CONFIRM_PASSWORD) {
                        // Show wrong confirmation password error
                        signUpView.incorrectConfirmPassword();
                    } else {
                        // Validation completed, show playerView
                        userManager.setCurrentUser(signUpView.getUsernameField());
                        signUpView.resetIncorrectInputs();
                        signUpView.clearFields();
                        listener.changeView(MainView.CARD_PLAYER);
                    }
                }
            }
        }
    }
}