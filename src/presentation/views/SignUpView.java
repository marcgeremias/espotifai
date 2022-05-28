package presentation.views;

import presentation.views.components.*;
import presentation.views.components.TextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static presentation.views.MainView.LOGO_IMAGE_PATH;

/**
 * Public class for the sign-up view graphical interface
 */
public class SignUpView extends JPanel {

    private static final Color DEFAULT_LABEL_BG = new Color(76, 76, 76);
    private static final Color DEFAULT_ERROR_LABEL_BG = new Color(220, 60, 25);

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

    /**
     * Constructor method to set up the view
     */
    public SignUpView() {
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
     * Method to add the listener to the sign up view buttons
     */
    public void registerController(ActionListener controller) {
        // Action listener for Join button
        joinButton.addActionListener(controller);
        joinButton.setActionCommand(BTN_JOIN);

        // Action listener for Go Back button
        goBackButton.addActionListener(controller);
        goBackButton.setActionCommand(BTN_GO_BACK);
    }

    /*
     * Method that configures the left side of the view
     * @return a panel with all the components of the left side of the view
     */
    private Component left() {
        JPanel left = new JPanel();
        left.add(goBackButton());
        left.setOpaque(false);
        return left;
    }

    /*
     * Method that shows a button to indicate if the user wants to go back to the sign up view
     * @return the panel with the button of going back inside
     */
    private Component goBackButton() {
        goBackButton = new HoverButton(Color.LIGHT_GRAY, Color.BLACK, "< LOG IN");
        goBackButton.setBackground(Color.BLACK);
        goBackButton.setForeground(Color.WHITE);
        goBackButton.setOpaque(true);
        goBackButton.setFont(new Font("Apple Casual", Font.BOLD, 12));
        goBackButton.setBorderPainted(false);

        JPanel panelAuxGoBack = new JPanel();
        panelAuxGoBack.setOpaque(false);
        panelAuxGoBack.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
        panelAuxGoBack.add(goBackButton);

        return panelAuxGoBack;
    }

    /*
     * Method to configure all the center components and containers of the SignUp view
     * @return the JPanel with all the center of the SignUp view
     */
    private Component center() {
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.add(logoImage());
        center.add(borderLine());
        center.add(new TextField("Email", emailField));
        center.add(new TextField("Username", usernameField));
        center.add(new PasswordField("Password", passwordField));
        center.add(new PasswordField("Confirm Password", confirmPasswordField));
        center.add(joinButton());

        center.setOpaque(false);
        return center;
    }

    /*
     * Method that shows a button to join the platform and finish registering
     * @return the panel with the button of joining the program inside
     */
    private Component joinButton() {
        joinButton = new JButton();
        joinButton.setText("JOIN PLATFORM ");
        joinButton.setBorder(BorderFactory.createEmptyBorder(4, 20, 4, 20));
        joinButton.setBackground(new Color(94, 182, 72));
        joinButton.setOpaque(true);
        joinButton.setForeground(Color.WHITE);
        joinButton.setFont(new Font("arial", Font.BOLD, 10));

        JPanel panelAuxJoin = new JPanel();
        panelAuxJoin.setBorder(BorderFactory.createEmptyBorder(5, 25, 5, 25));
        panelAuxJoin.setOpaque(false);
        panelAuxJoin.add(joinButton);

        return panelAuxJoin;
    }

    /*
     * Method that shows a label representing a borderline after the logo
     * @return the panel with the borderline inside
     */
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

    /*
     * Method to configure the image of the logo inside the center of the sign up view
     * @return a JPanel with the logo image well configured
     */
    private Component logoImage() {
        JLabel logoImage = new JLabel();
        logoImage.setSize(176, 64);
        ImageIcon iconLogo = new ImageIcon(LOGO_IMAGE_PATH);
        logoImage.setIcon(new ImageIcon(iconLogo.getImage().getScaledInstance(logoImage.getWidth(), logoImage.getHeight(), Image.SCALE_SMOOTH)));
        JPanel logoImagePanel = new JPanel();
        logoImagePanel.setOpaque(false);
        logoImagePanel.add(logoImage);

        return logoImagePanel;
    }

    /*
     * Method that is in charge of the right margins of the window.
     * @return the container with the panel margin (without opacity)
     */
    private Component rightMargin() {
        JPanel downMargin = new JPanel();
        downMargin.setOpaque(false);
        downMargin.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 180));

        return downMargin;
    }

    /*
     * Method that is in charge of the top margins of the window.
     * @return the container with the panel margin (without opacity)
     */
    private Container upMargin() {
        JPanel upMargin = new JPanel();
        upMargin.setOpaque(false);
        upMargin.setBorder(BorderFactory.createEmptyBorder(150, 0, 0, 0));

        return upMargin;
    }

    /*
     * Method that is in charge of the bottom of the window.
     * @return the container with the panel (without opacity) and the incorrect input label
     */
    private Container down() {
        JPanel downMargin = new JPanel();
        downMargin.setOpaque(false);
        downMargin.setBorder(BorderFactory.createEmptyBorder(0, 0, 120, 0));
        downMargin.add(WrongInputLabel.wrongInputLabel(incorrectInput));
        return downMargin;
    }

    /**
     * Gets the username introduced in the text field
     * @return the text introduced by the user in the username text field
     */
    public String getUsernameField() {
        return usernameField.getText();
    }

    /**
     * Gets the email introduced in the text field
     * @return the text introduced by the user in the email text field
     */
    public String getEmailField() {
        return emailField.getText();
    }

    /**
     * Gets the password introduced in the text field
     * @return the text introduced by the user in the password field
     */
    public char[] getPasswordField() {
        return passwordField.getPassword();
    }

    /**
     * Gets the confirmPassword introduced in the text field
     * @return the text introduced by the user in the confirmPassword of the password field
     */
    public char[] getConfirmPasswordField() {
        return confirmPasswordField.getPassword();
    }

    /**
     * Method that shows an error message when the username introduced is already inside the database
     */
    public void incorrectUsername() {
        incorrectInput.setText("This username is not valid. Try another one.");
        usernameField.setBackground(DEFAULT_ERROR_LABEL_BG);
        incorrectInput.setVisible(true);
    }

    /**
     * Method that shows an error message when the email introduced is already inside the database
     */
    public void incorrectEmail() {
        incorrectInput.setText("This email is not valid. Try another one.");
        emailField.setBackground(DEFAULT_ERROR_LABEL_BG);
        incorrectInput.setVisible(true);
    }

    /**
     * Method that shows an error message when the password introduced does not match the required standards
     */
    public void incorrectPassword() {
        incorrectInput.setText("The password must have minimum 8 characters and contain a capital letter," +
                " minuscule letter and a number. Try again.");
        passwordField.setBackground(DEFAULT_ERROR_LABEL_BG);
        incorrectInput.setVisible(true);
    }

    /**
     * Method that shows an error message when the confirming password introduced does not match with the password one
     */
    public void incorrectConfirmPassword() {
        incorrectInput.setText("The confirming password does not match with the password introduced. Try again.");
        confirmPasswordField.setBackground(DEFAULT_ERROR_LABEL_BG);
        incorrectInput.setVisible(true);
    }

    /**
     * Method called when all credentials are correct, to revert the possible previous incorrect messages
     */
    public void resetIncorrectInputs() {
        usernameField.setBackground(DEFAULT_LABEL_BG);
        emailField.setBackground(DEFAULT_LABEL_BG);
        passwordField.setBackground(DEFAULT_LABEL_BG);
        confirmPasswordField.setBackground(DEFAULT_LABEL_BG);
        incorrectInput.setVisible(false);
    }

    /**
     * Method that clears all the fields of the text fields
     */
    public void clearFields() {
        usernameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        incorrectInput.setText("");
        incorrectInput.setVisible(false);
    }

    /**
     * Method that clears all the information in the view (fields + reset errors)
     */
    public void clearAllInfo(){
        clearFields();
        resetIncorrectInputs();
        incorrectInput.setVisible(false);
    }
}
