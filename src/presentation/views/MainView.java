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

    /**
     * Method that configures our custom textField with the class PlaceholderTextField
     * @param placeHolder the placeholder you want to put in the text field
     * @return the JPanel with the custom text field configured
     */
    public Component textField(String placeHolder, PlaceholderTextField textField) {
        //Using own textField classes
        textField.setBorder(BorderFactory.createCompoundBorder(
                textField.getBorder(),
                BorderFactory.createEmptyBorder(5, 8, 5, 5)));
        textField.setColumns(28);
        textField.setBackground(new Color(76, 76, 76));
        textField.setForeground(Color.WHITE);
        textField.setPlaceholder(placeHolder);
        textField.setOpaque(true);
        Font f = textField.getFont();
        textField.setFont(new Font(f.getName(), f.getStyle(), 12));
        JPanel auxPanel = new JPanel();
        auxPanel.setOpaque(false);
        auxPanel.add(textField);

        return auxPanel;
    }

    /**
     * Method that configures our custom PasswordField with the class PlaceholderPasswordField
     * @param placeHolder the placeholder you want to put in the password field
     * @return the JPanel with the custom password field configured
     */
    public Component passwordField(String placeHolder, PlaceholderPasswordField passField) {
        //Using own Field classes
        passField.setBorder(BorderFactory.createCompoundBorder(
                passField.getBorder(),
                BorderFactory.createEmptyBorder(5, 8, 5, 5)));
        passField.setColumns(28);
        passField.setBackground(new Color(76, 76, 76));
        passField.setForeground(Color.WHITE);
        passField.setPlaceholder(placeHolder);
        passField.setOpaque(true);
        passField.setEchoChar('*');
        Font f = passField.getFont();
        passField.setFont(new Font(f.getName(), f.getStyle(), 12));
        JPanel auxPanel = new JPanel();
        auxPanel.setOpaque(false);
        auxPanel.add(passField);

        return auxPanel;
    }

    /**
     * Method that shows a message when a wrong input is set
     * @param inputMessage the attribute with the input message to put
     * @return the input label message hided in a JPanel
     */
    public Component wrongInputLabel(JLabel inputMessage) {
        inputMessage.setFont(new Font("Cascadia Code ExtraLight", Font.PLAIN, 13));
        inputMessage.setForeground(Color.RED);
        inputMessage.setVisible(false);
        return inputMessage;
    }
}
