package presentation.views;

import presentation.controllers.MainViewListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SignUpView extends JPanel {

    // Buttons connected with an Action Listener and their constants
    private JButton joinButton;
    private JButton goBackButton;
    public static final String BTN_JOIN = "BTN JOIN";
    public static final String BTN_GO_BACK = "BTN GO BACK";

    // Text fields of the username and email, and password fields
    private PlaceholderTextField usernameField;
    private PlaceholderTextField emailField;
    private PlaceholderPasswordField passwordField;
    private PlaceholderPasswordField confirmPasswordField;

    // Label of the possible incorrect input
    private JLabel incorrectInput;

    // Listener of the MainView
    MainViewListener listener;

    public SignUpView(MainViewListener listener) {
        this.listener = listener;
        usernameField = new PlaceholderTextField();
        emailField = new PlaceholderTextField();
        passwordField = new PlaceholderPasswordField();
        confirmPasswordField = new PlaceholderPasswordField();
        incorrectInput = new JLabel();

        this.setLayout(new BorderLayout());
        this.add(left(), BorderLayout.WEST);
        this.add(rightMargin(), BorderLayout.EAST);
        this.add(upMargin(), BorderLayout.NORTH);
        this.add(down(), BorderLayout.SOUTH);
        this.add(center(), BorderLayout.CENTER);
        this.setOpaque(false);
    }

    /**
     * Method to add the listener to the Login view buttons
     */
    public void registerController(ActionListener controller) {
        // Action listener for Join button
        joinButton.addActionListener(controller);
        joinButton.setActionCommand(BTN_JOIN);

        // Action listener for Go Back button
        goBackButton.addActionListener(controller);
        goBackButton.setActionCommand(BTN_GO_BACK);
    }

    private Component left() {
        JPanel left = new JPanel();
        left.add(goBackButton());
        left.add(goBackText());
        left.setOpaque(false);
        return left;
    }

    private Component goBackText() {
        JLabel goBackText = new JLabel("Log in");
        goBackText.setFont(new Font("arial", Font.BOLD, 16));
        goBackText.setForeground(Color.WHITE);

        JPanel panelAux = new JPanel();
        panelAux.setSize(goBackText.getWidth(), goBackText.getHeight());
        panelAux.setOpaque(false);
        panelAux.add(goBackText);

        return panelAux;
    }

    private Component goBackButton() {
        goBackButton = new JButton();
        goBackButton.setText("<");
        goBackButton.setBackground(Color.BLACK);
        goBackButton.setForeground(Color.WHITE);
        goBackButton.setFont(new Font("Berlin Sans FB Demi", Font.BOLD, 25));

        JPanel panelAuxGoBack = new JPanel();
        panelAuxGoBack.setOpaque(false);
        panelAuxGoBack.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
        panelAuxGoBack.add(goBackButton);

        return panelAuxGoBack;
    }

    private Component center() {
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.add(logoImage());
        center.add(borderLine());
        center.add(listener.textField("Email", emailField));
        center.add(listener.textField("Username", usernameField));
        center.add(listener.passwordField("Password", passwordField));
        center.add(listener.passwordField("Confirm Password", confirmPasswordField));
        center.add(joinButton());

        center.setOpaque(false);
        return center;
    }

    private Component joinButton() {
        joinButton = new JButton();
        joinButton.setText("JOIN PLATFORM ");
        joinButton.setBorder(BorderFactory.createEmptyBorder(4, 20, 4, 20));
        joinButton.setBackground(new Color(94, 182, 72));
        joinButton.setForeground(Color.WHITE);
        joinButton.setFont(new Font("arial", Font.BOLD, 10));

        JPanel panelAuxJoin = new JPanel();
        panelAuxJoin.setBorder(BorderFactory.createEmptyBorder(5, 25, 5, 25));
        panelAuxJoin.setOpaque(false);
        panelAuxJoin.add(joinButton);

        return panelAuxJoin;
    }

    private Component borderLine() {
        JLabel borderLine = new JLabel("___________________________________________________");
        borderLine.setFont(new Font("arial", Font.BOLD, 10));
        borderLine.setForeground(Color.GRAY);
        borderLine.setBorder(BorderFactory.createEmptyBorder(0, 4, 10, 4));

        JPanel panelAux = new JPanel();
        panelAux.setSize(borderLine.getWidth(), borderLine.getHeight());
        panelAux.setOpaque(false);
        panelAux.add(borderLine);

        return panelAux;
    }

    private Component logoImage() {
        JLabel logoImage = new JLabel();
        logoImage.setSize(176, 64);
        ImageIcon iconLogo = new ImageIcon("images/logo.jpeg");
        logoImage.setIcon(new ImageIcon(iconLogo.getImage().getScaledInstance(logoImage.getWidth(), logoImage.getHeight(), Image.SCALE_SMOOTH)));
        JPanel logoImagePanel = new JPanel();
        logoImagePanel.setOpaque(false);
        logoImagePanel.add(logoImage);

        return logoImagePanel;
    }

    private Component rightMargin() {
        JPanel downMargin = new JPanel();
        downMargin.setOpaque(false);
        downMargin.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 180));

        return downMargin;
    }

    /**
     * Metode that is in charge of the top margins of the window.
     * @return the container with the panel margin (without opacity)
     */
    public Container upMargin() {
        JPanel upMargin = new JPanel();
        upMargin.setOpaque(false);
        upMargin.setBorder(BorderFactory.createEmptyBorder(150, 0, 0, 0));

        return upMargin;
    }

    /**
     * Metode that is in charge of the bottom of the window.
     * @return the container with the panel (without opacity) and the incorrect input label
     */
    public Container down() {
        JPanel downMargin = new JPanel();
        downMargin.setOpaque(false);
        downMargin.setBorder(BorderFactory.createEmptyBorder(0, 0, 120, 0));
        downMargin.add(listener.wrongInputLabel(incorrectInput));
        return downMargin;
    }

    public String getUsernameField() {
        return usernameField.getText();
    }

    public String getEmailField() {
        return emailField.getText();
    }

    public char[] getPasswordField() {
        return passwordField.getPassword();
    }

    public char[] getConfirmPasswordField() {
        return confirmPasswordField.getPassword();
    }

    /**
     * Method that shows an error message when the username introduced is already inside the database
     */
    public void incorrectUsername() {
        incorrectInput.setText("This username is not valid. Try another one.");
        usernameField.setBackground(new Color(220, 60, 25));
        incorrectInput.setVisible(true);
    }

    /**
     * Method that shows an error message when the email introduced is already inside the database
     */
    public void incorrectEmail() {
        incorrectInput.setText("This email is not valid. Try another one.");
        emailField.setBackground(new Color(220, 60, 25));
        incorrectInput.setVisible(true);
    }

    /**
     * Method that shows an error message when the password introduced does not match the required standards
     */
    public void incorrectPassword() {
        incorrectInput.setText("The password must have minimum 8 characters and contain a capital letter," +
                " minuscule letter and a number. Try again.");
        passwordField.setBackground(new Color(220, 60, 25));
        incorrectInput.setVisible(true);
    }

    /**
     * Method that shows an error message when the confirming password introduced does not match with the password one
     */
    public void incorrectConfirmPassword() {
        incorrectInput.setText("The confirming password does not match with the password introduced. Try again.");
        confirmPasswordField.setBackground(new Color(220, 60, 25));
        incorrectInput.setVisible(true);
    }

    /**
     * Method called when all credentials are correct, to revert the possible previous incorrect messages
     */
    public void resetIncorrectInputs() {
        usernameField.setBackground(new Color(76, 76, 76));
        emailField.setBackground(new Color(76, 76, 76));
        passwordField.setBackground(new Color(76, 76, 76));
        confirmPasswordField.setBackground(new Color(76, 76, 76));
        incorrectInput.setVisible(false);
    }
}
