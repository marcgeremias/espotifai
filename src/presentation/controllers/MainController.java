package presentation.controllers;

import business.UserManager;
import presentation.views.LoginView;
import presentation.views.MainView;
import presentation.views.PlayerView;
import presentation.views.SignUpView;

public class MainController implements ChangeViewListener {

    private MainView mainView;
    private UserManager userManager;
    private LoginController loginController;

    public MainController(MainView mainView, UserManager userManager) {
        this.mainView = mainView;
        this.userManager = userManager;


        LoginView loginView = new LoginView();
        loginController = new LoginController(this, loginView, userManager);
        loginView.registerController(loginController);
        mainView.initCardLayout(loginView, new SignUpView(), new PlayerView());
        mainView.changeView(MainView.CARD_LOG_IN);
    }

    @Override
    public void changeView(String card) {
        mainView.changeView(card);
    }
}
