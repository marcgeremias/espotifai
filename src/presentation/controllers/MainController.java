package presentation.controllers;

import business.UserManager;
import presentation.views.*;

import javax.swing.*;
import java.awt.*;

public class MainController implements MainViewListener {

    private MainView mainView;
    private UserManager userManager;
    private LoginController loginController;
    private SingUpController signUpController;

    public MainController(MainView mainView, UserManager userManager) {
        this.mainView = mainView;
        this.userManager = userManager;
    }

    public void run() {
        // Defining login view and controller
        LoginView loginView = new LoginView(this);
        loginController = new LoginController(this, loginView, userManager);
        loginView.registerController(loginController);

        // Defining signup view and controller
        SignUpView signUpView = new SignUpView(this);
        signUpController = new SingUpController(this, signUpView, userManager);
        signUpView.registerController(signUpController);

        // Defining views in the card layout of the JFrame MainView
        mainView.initCardLayout(loginView, signUpView, new PlayerView());
        mainView.changeView(MainView.CARD_LOG_IN);
    }

    @Override
    public void changeView(String card) {
        mainView.changeView(card);
    }


    @Override
    public Component passwordField(String placeHolder, PlaceholderPasswordField passField) {
        return mainView.passwordField(placeHolder, passField);
    }

    @Override
    public Component textField(String placeHolder, PlaceholderTextField textField) {
        return mainView.textField(placeHolder, textField);
    }

    @Override
    public Component wrongInputLabel(JLabel inputMessage) {
        return mainView.wrongInputLabel(inputMessage);
    }
}
