package presentation.views;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    //Card Layout which occupies the entire Frame
    private CardLayout cardManager;

    //Constants representing the view you want to show
    public static final String CARD_LOG_IN = "CARD LOG IN";
    public static final String CARD_SIGN_UP = "CARD SIGN UP";
    public static final String CARD_PLAYER = "CARD PLAYER";
    public static final String LOGO_IMAGE_PATH = "./res/images/logo.jpeg";

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
        //setResizable(false);
        setLocationRelativeTo(null);
    }

    /**
     * Method to change the view with the one chosen in the card
     * @param card the name put on the card layout that referes to a specific view
     */
    public void changeView(String card) {
        cardManager.show(getContentPane(), card);
    }

    /**
     * Start method to set the view visible
     */
    public void start() {
        setVisible(true);
    }

    /**
     * Method that is in charge of connecting the views we want with the CardLayout
     * @param loginView the Login View class (to put inside the card layout)
     * @param signUpView the SignUp View class (to put inside the card layout)
     * @param playerView the Player View class (to put inside the card layout)
     */
    public void initCardLayout(LoginView loginView, SignUpView signUpView, PlayerView playerView) {
        getContentPane().add(loginView, CARD_LOG_IN);
        getContentPane().add(signUpView, CARD_SIGN_UP);
        getContentPane().add(playerView, CARD_PLAYER);
    }
}