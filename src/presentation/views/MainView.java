package presentation.views;

import presentation.controllers.SingUpController;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    //Card Layout which occupies the entire Frame
    private CardLayout cardManager;

    //Constants representing the view you want to show
    public static final String CARD_LOG_IN = "CARD LOG IN";
    public static final String CARD_SIGN_UP = "CARD SIGN UP";
    public static final String CARD_PLAYER = "CARD PLAYER";

    public MainView() {
        cardManager = new CardLayout();
        this.setLayout(cardManager);

        configureWindow();
    }

    /**
     * Method to configure the window panel of the view
     */
    private void configureWindow() {
        setTitle("Espotifai");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        setSize(1280, 720);
        setLocationRelativeTo(null);
    }

    public void changeView(String card) {
        cardManager.show(getContentPane(), card);
    }

    /**
     * Start method to set the view visible
     */
    public void start() {
        setVisible(true);
    }
    //TODO: passar totes les vistes per inicialitzar-les
    public void initCardLayout(LoginView loginView, SignUpView signUpView, PlayerView playerView) {
        getContentPane().add(loginView, CARD_LOG_IN);
        getContentPane().add(signUpView, CARD_SIGN_UP);
        getContentPane().add(playerView, CARD_PLAYER);
    }
}
