package presentation.views;

import presentation.views.components.HoverButton;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Public class for the UserProfile View graphic interface
 */
public class UserProfileView extends JPanel {
    public static final String DELETE_ACCOUNT = "deleteButton";
    public static final String BACK_BUTTON = "backButton";

    //JLabel for the titles and descriptions
    private JLabel title;
    private JLabel username;

    //Custom JButton (HoverButton) for the messages
    private HoverButton backButton;
    private HoverButton deleteHoverButton;
    private HoverButton editSettingsButton;
    private HoverButton paymentMethods;
    private HoverButton manageSuscriptions;

    /**
     * Constructor method to set up the view
     */
    public UserProfileView() {
        this.setLayout(new BorderLayout()); // Setting of a Border Layout which structures the whole menu
        this.setBackground(Color.DARK_GRAY);
        this.add(upMargin(), BorderLayout.NORTH);
        this.add(downMargin(), BorderLayout.SOUTH);
        this.add(backButton(), BorderLayout.EAST);
        this.add(center(), BorderLayout.CENTER);
    }

    /**
     * Updates the name of the user to display
     * @param username a String containing the username
     */
    public void setUsername(String username){
        this.username.setText(username);
        repaint();
    }

    /*
     * Method that creates a margin in the top of the window
     * @return A container with the panel of the top margin (without opacity)
     */
    private Container upMargin() {
        JPanel upMargin = new JPanel();
        upMargin.setOpaque(false);
        upMargin.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

        return upMargin;
    }

    /*
     * Method that creates a margin in the bottom of the window
     * @return A container with the panel of the bottom margin (without opacity)
     */
    private Container downMargin() {
        JPanel downMargin = new JPanel();
        downMargin.setOpaque(false);
        downMargin.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));

        return downMargin;
    }

    /*
     * Method to configure all the center components and containers of the User Profile view
     * @return the JPanel with all the center of the User Profile view
     */
    private Component center() {
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        // Addition of the image into the panel
        center.add(userImage());
        // Addition of the username label
        center.add(setUsernameLabel());
        // Addition of the title of the menu
        center.add(titleMenu());
        // Addition of the "edit settings" option of the menu
        center.add(createEditSettingsOption());
        // Addition of the "payment methods" option of the menu
        center.add(createPaymentMethods());
        // Addition of the "manage suscriptions" option of the menu
        center.add(createManageSubscriptions());
        // Addition of the "delete account" option
        center.add(createDeleteAccountButton());

        center.setOpaque(false);

        return center;
    }

    /*
     * Method that sets the user's name in the User Profile View
     * @return A panel containing the user's name
     */
    private Component setUsernameLabel(){
        username = new JLabel();
        username.setForeground(Color.WHITE);
        username.setFont(new Font("Apple Casual", Font.BOLD, 25));
        username.setHorizontalAlignment(JLabel.CENTER);

        JPanel panelUsername = new JPanel();
        panelUsername.setBorder(BorderFactory.createEmptyBorder(5,15,5,15));
        panelUsername.setOpaque(false);
        panelUsername.add(username);

        return panelUsername;
    }

    /*
     * Method that sets the title of the menu inside the User Profile view
     * @return A panel containing the title of the section of the view
     */
    private Component titleMenu() {
        title = new JLabel("Select the area that you want to modify: ");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Apple Casual", Font.BOLD, 15));
        title.setHorizontalAlignment(JLabel.CENTER);

        JPanel panelTitleSettings = new JPanel();
        panelTitleSettings.setBorder(BorderFactory.createEmptyBorder(10,15,5,15));
        panelTitleSettings.setOpaque(false);
        panelTitleSettings.add(title);

        return panelTitleSettings;
    }

    /*
     * Method that displays the image of the user in the User Profile view
     * @return A panel containing the image specified
     */
    private Component userImage() {
        // Image settings
        JLabel userImage = new JLabel();
        userImage.setSize(120, 120);
        ImageIcon iconLogo = new ImageIcon("images/anonimus.jpeg");
        userImage.setIcon(new ImageIcon(iconLogo.getImage().getScaledInstance(userImage.getWidth(), userImage.getHeight(), Image.SCALE_SMOOTH)));
        // Creation of an extra panel to locate the image
        JPanel userImagePanel = new JPanel();
        userImagePanel.setOpaque(false);
        userImagePanel.add(userImage);
        userImagePanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

        return userImagePanel;
    }

    /*
     * Method that sets the rollback button to skip the User Profile view
     * @return A panel containing the button to rollback
     */
    private Component backButton() {
        backButton = new HoverButton(Color.LIGHT_GRAY, Color.DARK_GRAY, "X");
        backButton.setBackground(Color.DARK_GRAY);
        backButton.setForeground(Color.WHITE);
        // Letter Settings
        backButton.setFont(new Font("Apple Casual", Font.BOLD, 15));
        // Border Settings
        backButton.setBorderPainted(true);
        backButton.setBorder(null);
        backButton.setPreferredSize(new Dimension(40,40));

        JPanel panelBackButton = new JPanel();
        panelBackButton.setBorder(BorderFactory.createEmptyBorder(0,0,0,100));
        panelBackButton.setOpaque(false);

        panelBackButton.add(backButton);

        return panelBackButton;
    }

    /*
     * Method that displays a button to Delete the account of the user in the User Profile view
     * @return A panel with the current button specified
     */
    private Component createDeleteAccountButton() {
        deleteHoverButton = new HoverButton(Color.RED, Color.DARK_GRAY, "Delete");
        deleteHoverButton.setBackground(Color.DARK_GRAY);
        deleteHoverButton.setForeground(Color.WHITE);
        // Letter Settings
        deleteHoverButton.setFont(new Font("Apple Casual", Font.BOLD, 15));
        // Border Settings
        deleteHoverButton.setBorderPainted(true);
        deleteHoverButton.setBorder(new LineBorder((Color.WHITE)));
        deleteHoverButton.setPreferredSize(new Dimension(200,40));

        // Panel Settings
        JPanel panelDeleteAccount = new JPanel();
        panelDeleteAccount.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        panelDeleteAccount.setOpaque(false);

        panelDeleteAccount.add(deleteHoverButton);

        return panelDeleteAccount;
    }

    /*
     * Method that creates Account Settings option in the User Profile view
     * @return A panel with the option to manage the user's account settings
     */
    private Component createEditSettingsOption() {
        editSettingsButton = new HoverButton(Color.LIGHT_GRAY, Color.DARK_GRAY, "Account Settings                                >");
        editSettingsButton.setBackground(Color.DARK_GRAY);
        editSettingsButton.setForeground(Color.WHITE);
        // Letter Settings
        editSettingsButton.setFont(new Font("Apple Casual", Font.BOLD, 15));
        // Border Settings
        editSettingsButton.setBorderPainted(true);
        editSettingsButton.setBorder(new LineBorder(Color.WHITE));
        editSettingsButton.setPreferredSize(new Dimension(400,60));

        // Panel Settings
        JPanel panelEditSettings = new JPanel();
        panelEditSettings.setBorder(BorderFactory.createEmptyBorder(5,15,5,15));
        panelEditSettings.setOpaque(false);

        panelEditSettings.add(editSettingsButton);

        return panelEditSettings;
    }

    /*
     * Method that creates Paymeny Methods option in the User Profile view
     * @return A panel with the option to manage the user's payment methods
     */
    private Component createPaymentMethods() {
        paymentMethods = new HoverButton(Color.LIGHT_GRAY, Color.DARK_GRAY, "Payment Methods                                >");
        paymentMethods.setBackground(Color.DARK_GRAY);
        paymentMethods.setForeground(Color.WHITE);
        // Letter Settings
        paymentMethods.setFont(new Font("Apple Casual", Font.BOLD, 15));
        // Border Settings
        paymentMethods.setBorderPainted(true);
        paymentMethods.setBorder(new LineBorder(Color.WHITE));
        paymentMethods.setPreferredSize(new Dimension(400,60));

        // Panel Settings
        JPanel panelPaymentMethods = new JPanel();
        panelPaymentMethods.setBorder(BorderFactory.createEmptyBorder(5,15,5,15));
        panelPaymentMethods.setOpaque(false);

        panelPaymentMethods.add(paymentMethods);
        return panelPaymentMethods;
    }

    /*
     * Method that creates Manage Susbcriptions option in the User Profile view
     * @return A panel with the option to manage the user's subscriptions
     */
    private Component createManageSubscriptions() {
        manageSuscriptions = new HoverButton(Color.LIGHT_GRAY, Color.DARK_GRAY, "Manage Subscriptions                                >");
        manageSuscriptions.setBackground(Color.DARK_GRAY);
        manageSuscriptions.setForeground(Color.WHITE);
        // Letter Settings
        manageSuscriptions.setFont(new Font("Apple Casual", Font.BOLD, 15));
        // Border Settings
        manageSuscriptions.setBorderPainted(true);
        manageSuscriptions.setBorder(new LineBorder(Color.WHITE));
        manageSuscriptions.setPreferredSize(new Dimension(400,60));

        // Panel Settings
        JPanel panelManageSuscriptions = new JPanel();
        panelManageSuscriptions.setBorder(BorderFactory.createEmptyBorder(5,15,5,15));
        panelManageSuscriptions.setOpaque(false);

        panelManageSuscriptions.add(manageSuscriptions);

        return panelManageSuscriptions;
    }

    /**
     * Function which manages all the actions releted with the diferent buttons of the User Profile view
     * @param listener Controls the diferent buttons and coordinates their actions
     */
    public void registerController (ActionListener listener){
        // Listener for the log-out button
        deleteHoverButton.setActionCommand(DELETE_ACCOUNT);
        deleteHoverButton.addActionListener(listener);

        // Listener for the back button
        backButton.setActionCommand(BACK_BUTTON);
        backButton.addActionListener(listener);
    }

    /**
     * Method that creates a confirmation panel when the user clicks the Delete button
     */
    public int showConfirmationPanel() {
        Object[] options = {"Yes, please", "No, thanks"};
        return JOptionPane.showOptionDialog(null,
                "Are you sure you want to delete the account",
                "Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
    }

    /**
     * Opens an error dialog
     * @param message a String containing the error message
     */
    public void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}