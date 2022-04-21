package presentation.views;

import presentation.controllers.MainViewListener;
import presentation.views.components.PlaceholderPasswordField;
import presentation.views.components.PlaceholderTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static presentation.views.MainView.LOGO_IMAGE_PATH;

/**
 * Class that shows the LoginView and extends from JFrame since it's the first view
 */
public class LoginView extends JPanel {

    // Buttons connected with an Action Listener and their constants
    private JButton createAccountButton;
    private JButton loginButton;
    public static final String BTN_CREATE_ACCOUNT = "BTN CREATE ACCOUNT";
    public static final String BTN_LOG_IN = "BTN LOG IN";

    // Text fields of the username or email and password of the user
    private PlaceholderTextField userField;
    private PlaceholderPasswordField passwordField;

    // Label of the possible incorrect input
    private JLabel incorrectInput;

    // Interface of the MainView
    private MainViewListener listener;

    /**
     * Constructor method to set up the view
     */
    public LoginView(MainViewListener listener) {
        this.listener = listener;
        userField = new PlaceholderTextField();
        passwordField = new PlaceholderPasswordField();
        incorrectInput = new JLabel();

        this.setLayout(new BorderLayout());
        this.add(upMargin(), BorderLayout.NORTH);
        this.add(down(), BorderLayout.SOUTH);
        this.add(center(), BorderLayout.CENTER);
        this.setOpaque(false);

    }

    /**
     * Method to configure all the center components and containers of the Login view
     * @return the JPanel with all the center of the Login view
     */
    private Component center() {
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.add(logoImage());
        center.add(createAccountButton());
        center.add(listener.textField("Username or email", userField));
        center.add(listener.passwordField("Password", passwordField));
        center.add(loginButton());
        center.setOpaque(false);

        return center;
    }

    /**
     * Method to configure the image of the logo inside the center of the Login view
     * @return a JPanel with the logo image well configured
     */
    private Component logoImage() {
        JLabel logoImage = new JLabel();
        logoImage.setSize(263, 96);
        ImageIcon iconLogo = new ImageIcon(LOGO_IMAGE_PATH);
        logoImage.setIcon(new ImageIcon(iconLogo.getImage().getScaledInstance(logoImage.getWidth(), logoImage.getHeight(), Image.SCALE_SMOOTH)));
        JPanel logoImagePanel = new JPanel();
        logoImagePanel.setOpaque(false);
        logoImagePanel.add(logoImage);
        logoImagePanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

        return logoImagePanel;
    }

    /**
     * Method to configure the Create Account button inside the center of the Login view
     * @return a JPanel with the Create Account button well configured
     */
    public Container createAccountButton() {
        createAccountButton = new JButton();
        createAccountButton.setText("Create an account ");
        createAccountButton.setBorder(BorderFactory.createEmptyBorder(4, 90, 4, 90));
        createAccountButton.setBackground(new Color(131, 29, 233));
        createAccountButton.setForeground(Color.WHITE);
        createAccountButton.setFont(new Font("arial", Font.BOLD, 10));

        JPanel panelAuxStart = new JPanel();
        panelAuxStart.setBorder(BorderFactory.createEmptyBorder(10, 8, 10, 8));
        panelAuxStart.setOpaque(false);
        panelAuxStart.add(createAccountButton);

        return panelAuxStart;
    }

    /**
     * Method to configure the Login button inside the center of the Login view
     * @return a JPanel with the Login button well configured
     */
    public Container loginButton() {
        loginButton = new JButton();
        loginButton.setText("Log In ");
        loginButton.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        loginButton.setBackground(new Color(94, 182, 72));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("arial", Font.BOLD, 9));

        JPanel panelAuxLogin = new JPanel();
        panelAuxLogin.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        panelAuxLogin.setOpaque(false);
        panelAuxLogin.add(loginButton);

        return panelAuxLogin;
    }

    /**
     * Method to add the listener to the Login view buttons
     */
    public void registerController(ActionListener controller) {
        // Action listener for Create Account button
        createAccountButton.addActionListener(controller);
        createAccountButton.setActionCommand(BTN_CREATE_ACCOUNT);

        // Action listener for Log In button
        loginButton.addActionListener(controller);
        loginButton.setActionCommand(BTN_LOG_IN);
    }

    /**
     * Method that is in charge of the top margins of the window.
     * @return the container with the panel margin (without opacity)
     */
    public Container upMargin() {
        JPanel upMargin = new JPanel();
        upMargin.setOpaque(false);
        upMargin.setBorder(BorderFactory.createEmptyBorder(150, 0, 0, 0));

        return upMargin;
    }

    /**
     * Method that is in charge of the bottom of the window.
     * @return the container with the panel (without opacity) and the incorrect input label
     */
    public Container down() {
        JPanel downMargin = new JPanel();
        downMargin.setOpaque(false);
        downMargin.setBorder(BorderFactory.createEmptyBorder(0, 0, 120, 0));
        downMargin.add(listener.wrongInputLabel(incorrectInput));
        return downMargin;
    }

    /**
     * Gets the username/email introduced in the text field
     * @return the text introduced by the user in the username/email text field
     */
    public String getUserField() {
        return userField.getText();
    }

    /**
     * Gets the password introduced in the password field
     * @return the password introduced by the user in the password field
     */
    public String getPasswordField() {
        return String.valueOf(passwordField.getPassword());
    }

    /**
     * Method that shows an error message when the username or email introduced is incorrect
     */
    public void incorrectUser() {
        incorrectInput.setText("Username or email could not be found");
        passwordField.setBackground(new Color(76, 76, 76));
        userField.setBackground(new Color(220, 60, 25));
        incorrectInput.setVisible(true);
    }

    /**
     * Method that shows an error message when the password introduced is incorrect
     */
    public void incorrectPassword() {
        incorrectInput.setText("Wrong password. Try again.");
        userField.setBackground(new Color(76, 76, 76));
        passwordField.setBackground(new Color(220, 60, 25));
        incorrectInput.setVisible(true);
    }

    /**
     * Method called when all credentials are correct, to revert the possible previous incorrect messages
     */
    public void correctCredentials() {
        userField.setBackground(new Color(76, 76, 76));
        passwordField.setBackground(new Color(76, 76, 76));
        incorrectInput.setVisible(false);
    }

    /**
     * Method called when all credentials are correct
     * but the User could not be saved correctly into the databate
     */
    public void errorAddingUser() {
        incorrectInput.setText("ERROR: The User could not be added to the database.");
        incorrectInput.setVisible(true);
    }
}
