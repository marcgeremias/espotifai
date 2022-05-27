package presentation.views;

import presentation.views.components.HoverButton;
import presentation.views.components.PlaceholderTextField;
import presentation.views.components.TextField;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class CreatePlaylistView extends JPanel{

    // The button to create playlist
    public static final String BTN_CREATE_PLAYLIST = "BTN_CREATE_PLAYLIST";

    // Attributes
    private HoverButton createButton;
    private PlaceholderTextField nameField;


    public CreatePlaylistView() {
        this.setLayout(new BorderLayout());
        this.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);

        this.add(labelsPane("CREATE PLAYLIST", 30), BorderLayout.NORTH);
        this.add(center(), BorderLayout.CENTER);
        this.add(southMargin(), BorderLayout.SOUTH);

        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setOpaque(true);
    }

    /**
     * Method to add the listener to the add playlist button
     */
    public void registerController(ActionListener controller) {
        createButton.setActionCommand(BTN_CREATE_PLAYLIST);
        createButton.addActionListener(controller);
    }

    /*
     * Method to configure all the center components and containers of the Create Playlist view
     * @return the JPanel with all the center of the Create Playlist view
     */
    private Component center() {
        JPanel center = new JPanel();
        nameField = new PlaceholderTextField();

        createButton = new HoverButton(Color.LIGHT_GRAY, Color.DARK_GRAY, "ADD");
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.add(labelsPane("Name", 18));
        center.add(new TextField("Name", nameField));
        center.add(createButton());
        center.setOpaque(false);

        return center;
    }

    /*
     * Method that creates a JPanel with a button to create playlist inside
     */
    private Component createButton() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        createButton.setBackground(Color.BLACK);
        createButton.setForeground(Color.LIGHT_GRAY);
        createButton.setFont(new Font("Apple Casual", Font.BOLD, 10));

        //Border Settings
        createButton.setBorderPainted(true);
        createButton.setBorder(new LineBorder((Color.LIGHT_GRAY)));
        createButton.setPreferredSize(new Dimension(100,25));

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 80, 0));
        buttonPanel.add(createButton);

        return buttonPanel;
    }

    /*
     * Method that creates a JPanel with a personalized message and Font size
     */
    private Component labelsPane(String message, int size) {
        JLabel searchSong = new JLabel(message);
        searchSong.setForeground(Color.WHITE);
        searchSong.setFont(new Font("Apple Casual", Font.BOLD, size));
        searchSong.setHorizontalAlignment(JLabel.CENTER);

        JPanel panelSearch = new JPanel();
        panelSearch.setBorder(BorderFactory.createEmptyBorder(40,15,0,15));
        panelSearch.setOpaque(false);
        panelSearch.add(searchSong);
        return panelSearch;
    }

    /**
     * Method that gets the text inside the text field
     * @return the text inside the text field
     */
    public String getNameField() {
        return nameField.getText();
    }

    /*
     * Method that makes the south margin to compact all the other components on top
     */
    private Component southMargin() {
        JPanel southMargin = new JPanel();
        southMargin.setOpaque(false);
        southMargin.setBorder(BorderFactory.createEmptyBorder(0, 0, 150, 0));

        return southMargin;
    }

    /**
     * Method that shows an error when the created playlist could not correctly add
     * @param message the message with the error to display
     */
    public void createPlaylistError(String message) {
        JOptionPane.showMessageDialog(this,message);
    }
}
