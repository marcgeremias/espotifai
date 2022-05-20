package presentation.views;

import presentation.views.components.HoverButton;
import presentation.views.components.JImagePanel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class SideMenuView extends JPanel {

    private String pathHomeNormal = "res/icons/home.png";
    private String pathHomeHover = "res/icons/home_hover.png";
    private String pathSearchNormal = "res/icons/search.png";
    private String pathSearchHover = "res/icons/search_hover.png";
    private String pathLibraryNormal = "res/icons/library.png";
    private String pathLibraryHover = "res/icons/library_hover.png";
    private String addMusicNormal = "res/icons/add.png";
    private String addMusicHover = "res/icons/add_hover.png";
    private String createPlaylistNormal = "res/icons/create_playlist.png";
    private String createPlaylistHover = "res/icons/create_playlist_hover.png";

    public static final String HOME_BUTTON = "home_button_presses";
    public static final String SEARCH_BUTTON = "search_button_presses";
    public static final String LIBRARY_BUTTON = "library_button_presses";
    public static final String ADD_MUSIC_BUTTON = "add_music_button_presses";
    public static final String CREATE_PLAYLIST_BUTTON = "create_playlist_button_presses";
    public static final String LOGOUT = "logout_button_pressed";
    public static final String SETTINGS = "settings_button_pressed";

    //Main section
    private JImagePanel homeJButton;
    private JImagePanel searchJButton;
    private JImagePanel libraryJButton;

    //Secondary section
    private JImagePanel addMusicJButton;
    private JImagePanel createPlaylistButton;

    //Last section
    private HoverButton logOutJbutton;
    private HoverButton settingsButton;

    /**
     * Constructor method to set up the view
     */
    public SideMenuView() {
        configurePanel();
    }

    /**
     * Method that configures the settings of the side panel
     */
    private void configurePanel() {
        this.setLayout(new BorderLayout()); // Setting of a Border Layout which structures the whole menu
        this.add(bufferMenu(), BorderLayout.CENTER);
    }

    /**
     * Method that creates a buffer panel that structures the elements of the side panel of the view
     * @return A panel containing all the elements of the side menu
     */
    private Component bufferMenu(){
        JPanel actualPanel = new JPanel(new BorderLayout());
        actualPanel.setBackground(Color.BLACK);

        actualPanel.add(mainSection(), BorderLayout.NORTH);
        actualPanel.add(center(), BorderLayout.CENTER);
        actualPanel.add(logOutSection(), BorderLayout.SOUTH);

        actualPanel.setOpaque(false);
        return actualPanel;
    }

    /**
     * Method that specifies all the elements that are located in the center of the panel
     * @return A panel with the components of the center of the panel
     */
    private Component center(){
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(Color.BLACK);

        center.add(middleSection());
        center.add(playlistSection());

        center.setOpaque(false);
        return center;
    }

    /**
     * Method that structures the three options of the top of the side menu
     * @return A panel containing the top section elements of the side menu
     */
    private Component mainSection() {
        JPanel home = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        home.setBackground(Color.BLACK);

        homeJButton = new JImagePanel(pathHomeNormal, pathHomeHover, null);
        homeJButton.setPreferredSize(new Dimension(120,30));
        homeJButton.setOpaque(false);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.insets = new Insets(10,0,8,0);
        home.add (homeJButton, constraints);

        searchJButton = new JImagePanel(pathSearchNormal, pathSearchHover, null);
        searchJButton.setPreferredSize(new Dimension(95, 25));
        searchJButton.setOpaque(false);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        home.add (searchJButton, constraints);

        libraryJButton = new JImagePanel(pathLibraryNormal, pathLibraryHover, null);
        libraryJButton.setPreferredSize(new Dimension(100,25));
        libraryJButton.setOpaque(false);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        home.add (libraryJButton, constraints);

        home.setOpaque(true);
        home.setBorder(BorderFactory.createEmptyBorder(10, 4, 6, 6));

        return home;
    }

    /**
     * Method that structures the middle section of the side menu
     * @return A panel containing the top section elements of the side menu
     */
    private Component middleSection() {
        JPanel additionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        additionPanel.setBackground(Color.BLACK);

        addMusicJButton = new JImagePanel(addMusicNormal, addMusicHover, null);
        addMusicJButton.setPreferredSize(new Dimension(120,35));
        addMusicJButton.setOpaque(false);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        additionPanel.add (addMusicJButton, constraints);

        createPlaylistButton = new JImagePanel(createPlaylistNormal, createPlaylistHover, null);
        createPlaylistButton.setPreferredSize(new Dimension(140,35));
        createPlaylistButton.setOpaque(false);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.insets = new Insets(8,20,8,0);
        additionPanel.add (createPlaylistButton, constraints);

        additionPanel.setOpaque(true);
        additionPanel.setBorder(BorderFactory.createEmptyBorder(20, 4, 6, 6));

        return additionPanel;
    }

    private Component playlistSection() {
        JPanel playlistJPanel = new JPanel();
        playlistJPanel.setLayout(new BoxLayout(playlistJPanel, BoxLayout.Y_AXIS));
        playlistJPanel.setBackground(Color.BLACK);
        playlistJPanel.setOpaque(true);

        for (int i = 0; i < 12; i++) {
            Label label = new Label("ULAAA");
            label.setForeground(Color.BLACK);
            playlistJPanel.add(label);
        }

        return playlistJPanel;
    }

    /**
     * Method that structures the bottom section of the side menu
     * @return A panel containing the botttom section elements of the side menu
     */
    private Component logOutSection() {
        JPanel logOutPanel = new JPanel();
        logOutPanel.setBackground(Color.BLACK);
        logOutPanel.setOpaque(true);

        logOutJbutton = new HoverButton(Color.RED, Color.BLACK, "Log Out");
        logOutJbutton.setBackground(Color.BLACK);
        logOutJbutton.setForeground(Color.LIGHT_GRAY);
        logOutJbutton.setFont(new Font("Apple Casual", Font.BOLD, 10));
        // Border Settings
        logOutJbutton.setBorderPainted(true);
        logOutJbutton.setBorder(new LineBorder((Color.LIGHT_GRAY)));
        logOutJbutton.setPreferredSize(new Dimension(100,25));

        settingsButton = new HoverButton(Color.DARK_GRAY, Color.BLACK, "Settings");
        settingsButton.setBackground(Color.BLACK);
        settingsButton.setForeground(Color.LIGHT_GRAY);
        settingsButton.setFont(new Font("Apple Casual", Font.BOLD, 10));
        //Border Settings
        settingsButton.setBorderPainted(true);
        settingsButton.setBorder(new LineBorder((Color.LIGHT_GRAY)));
        settingsButton.setPreferredSize(new Dimension(100,25));

        logOutPanel.setBorder(BorderFactory.createEmptyBorder(20, 4, 20, 6));
        logOutPanel.add(settingsButton);
        logOutPanel.add(logOutJbutton);

        return logOutPanel;
    }

    /**
     * Method to add the listener to the SideMenu view buttons
     */
    public void registerController (ActionListener listener){
        homeJButton.setActionCommand(HOME_BUTTON);
        homeJButton.addActionListener(listener);

        searchJButton.setActionCommand(SEARCH_BUTTON);
        searchJButton.addActionListener(listener);

        libraryJButton.setActionCommand(LIBRARY_BUTTON);
        libraryJButton.addActionListener(listener);

        addMusicJButton.setActionCommand(ADD_MUSIC_BUTTON);
        addMusicJButton.addActionListener(listener);

        createPlaylistButton.setActionCommand(CREATE_PLAYLIST_BUTTON);
        createPlaylistButton.addActionListener(listener);

        logOutJbutton.setActionCommand(LOGOUT);
        logOutJbutton.addActionListener(listener);

        settingsButton.setActionCommand(SETTINGS);
        settingsButton.addActionListener(listener);
    }

}