package presentation.controllers;

import business.UserManager;
import presentation.views.MainView;
import presentation.views.SignUpView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SingUpController implements ActionListener {
    private SignUpView signUpView;
    private MainViewListener listener;
    private UserManager userManager;

    public SingUpController(MainViewListener listener, SignUpView signUpView, UserManager userManager) {
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
                listener.changeView(MainView.CARD_LOG_IN);
                break;
            case SignUpView.BTN_JOIN:
                onJoinClick();

                break;
            default:
                System.err.println("Unknown action command " + e.getActionCommand());
        }
    }

    private void onJoinClick() {
        // We reset the possible previous wrong introduced inputs
        signUpView.resetIncorrectInputs();

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
                        signUpView.resetIncorrectInputs();
                        listener.changeView(MainView.CARD_PLAYER);
                    }
                }
            }
        }
    }
}