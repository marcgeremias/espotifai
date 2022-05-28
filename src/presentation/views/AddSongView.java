package presentation.views;

import business.entities.Genre;
import presentation.views.components.HoverButton;
import presentation.views.components.PlaceholderTextField;
import presentation.views.components.TextField;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

/**
 * Public class for the Add song view graphical interface
 */
public class AddSongView extends JPanel {
    private PlaceholderTextField titleField;
    private PlaceholderTextField albumField;
    private JComboBox<Genre> genreSelector;
    private JPanel authorPane;
    private JComboBox<String> authorSelector;
    private PlaceholderTextField authorField;
    private HoverButton addSongButton;
    private JLabel incorrectFieldLabel;
    private HoverButton addFileButton;
    private HoverButton addImageButton;


    public static final String COMBOBOX_AUTHOR = "COMBOBOX_AUTHOR";
    public static final String BTN_ADD_SONG = "BTN_ADD_SONG";
    public static final String BTN_SELECT_FILE = "SELECT FILE";
    public static final String BTN_SELECT_IMAGE = "SELECT IMAGE";

    private static final String ERROR_DIALOG_TITLE = "Error";
    private static final String ADD_SONG_TITLE = "Add Song";
    private static final String BUTTON_ADD_MSG = "Add";
    private final String SELECT_AUTHOR_ITEM = "Select author";
    private final String OTHER_ITEM = "Other";
    private final String BUTTON_SELECT_FILE = "Select file";
    private final String BUTTON_ADD_IMAGE = "Add image";
    private final String TITLE_FIELD_PH = "Title";
    private final String ALBUM_FIELD_PH = "Album";
    private final String ALBUM_COVER_PH = "Cover path";

    private static final Color DEFAULT_ERROR_LABEL_BG = new Color(220, 60, 25);
    private static final Color DEFAULT_LABEL_BG = new Color(76, 76, 76);

    /**
     * Creates a new instance of AddSongView given a list of authors
     */
    public AddSongView() {
        this.setLayout(new BorderLayout());
        this.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);

        JPanel east = new JPanel();

        titleField = new PlaceholderTextField();
        albumField = new PlaceholderTextField();
        genreSelector = new JComboBox<Genre>();
        authorPane = new JPanel();
        authorSelector = new JComboBox<String>();
        authorField = new PlaceholderTextField();
        addSongButton = new HoverButton(new Color(131, 29, 233), Color.BLACK, BUTTON_ADD_MSG);
        incorrectFieldLabel = new JLabel();
        addFileButton = new HoverButton(Color.DARK_GRAY, Color.BLACK, BUTTON_SELECT_FILE);
        addImageButton = new HoverButton(Color.DARK_GRAY, Color.BLACK, BUTTON_ADD_IMAGE);

        east.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        east.setPreferredSize(new Dimension(
                ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()) / 6, this.getHeight()
        ));

        this.add(east, BorderLayout.EAST);
        this.add(center(), BorderLayout.CENTER);
    }

    /*
    * Configures the components in the center section of the view
    */
    private Component center() {
        JPanel panel = new JPanel();
        JLabel viewTitle = new JLabel(ADD_SONG_TITLE);
        JPanel north = new JPanel();
        JPanel south = new JPanel();
        JPanel center = new JPanel();

        panel.setLayout(new BorderLayout());

        viewTitle.setForeground(Color.WHITE);
        viewTitle.setFont(new Font("Apple Casual", Font.BOLD, 30));
        viewTitle.setHorizontalAlignment(JLabel.CENTER);

        north.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        north.setPreferredSize(new Dimension(
                this.getWidth(), ((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()) / 8
        ));
        north.setBorder(BorderFactory.createEmptyBorder(55,15,25,15));
        north.add(viewTitle);

        south.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        south.setPreferredSize(new Dimension(
                this.getWidth(), ((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()) / 12
        ));
        incorrectFieldLabel.setForeground(DEFAULT_ERROR_LABEL_BG);
        incorrectFieldLabel.setVisible(false);
        south.add(incorrectFieldLabel);

        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(BorderFactory.createEmptyBorder(25, 120, 20, 120));
        center.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);

        titleField.setText(null);
        albumField.setText(null);
        authorField.setText(null);
        //albumCover.setText(null);
        addFileButton.setAlignmentX(CENTER_ALIGNMENT);
        addImageButton.setAlignmentX(CENTER_ALIGNMENT);
        addSongButton.setAlignmentX(CENTER_ALIGNMENT);

        // Genres JComboBox initialisation
        configureGenres();

        // Authors JCombobox initialisation
        configureAuthors();

        // Add center components
        center.add(new TextField(TITLE_FIELD_PH, titleField));
        center.add(new TextField(ALBUM_FIELD_PH, albumField));
        center.add(genreSelector);
        center.add(authorPane);
        center.add(addFileButton());
        center.add(addImageButton());
        center.add(addSongButton());

        panel.add(north, BorderLayout.NORTH);
        panel.add(south, BorderLayout.SOUTH);
        panel.add(center, BorderLayout.CENTER);

        return panel;
    }

    /*
    * Creates a JComboBox containing the genres in the system
    */
    private void configureGenres() {
        genreSelector.setBackground(DEFAULT_LABEL_BG);
        genreSelector.setForeground(Color.GRAY);
        genreSelector.setBorder(BorderFactory.createEmptyBorder(0, 130, 0, 130));

        for (Genre genre : Genre.values()) {
            genreSelector.addItem(genre);
        }

        genreSelector.setSelectedIndex(0);
    }

    /*
    * Creates a JComboBox from which to pick an author which adds a text field when option is "Other"
    */
    private void configureAuthors() {
        // Authors JComboBox initialisation
        authorSelector.setBackground(DEFAULT_LABEL_BG);
        authorSelector.setForeground(Color.GRAY);
        authorSelector.setBorder(BorderFactory.createEmptyBorder(0, 130, 0, 130));
        authorSelector.addItem(SELECT_AUTHOR_ITEM);
        authorSelector.addItem(OTHER_ITEM);

        authorSelector.setSelectedIndex(0);

        // Author pane components
        authorPane.setLayout(new BoxLayout(authorPane, BoxLayout.Y_AXIS));
        authorPane.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        authorPane.add(authorSelector);
        authorField.setVisible(false);
        authorPane.add(new TextField("Author", authorField));
    }

    private Component addFileButton() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        addFileButton.setBackground(Color.BLACK);
        addFileButton.setForeground(Color.LIGHT_GRAY);
        addFileButton.setFont(new Font("Apple Casual", Font.BOLD, 10));
        //Border Settings
        addFileButton.setBorderPainted(true);
        addFileButton.setBorder(new LineBorder((Color.LIGHT_GRAY)));
        addFileButton.setPreferredSize(new Dimension(100,25));

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
        buttonPanel.add(addFileButton);

        return buttonPanel;
    }

    private Component addImageButton() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        addImageButton.setBackground(Color.BLACK);
        addImageButton.setForeground(Color.LIGHT_GRAY);
        addImageButton.setFont(new Font("Apple Casual", Font.BOLD, 10));
        //Border Settings
        addImageButton.setBorderPainted(true);
        addImageButton.setBorder(new LineBorder((Color.LIGHT_GRAY)));
        addImageButton.setPreferredSize(new Dimension(100,25));

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(1, 0, 6, 0));
        buttonPanel.add(addImageButton);

        return buttonPanel;
    }

    private Component addSongButton() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        addSongButton.setBackground(Color.BLACK);
        addSongButton.setForeground(Color.LIGHT_GRAY);
        addSongButton.setFont(new Font("Apple Casual", Font.BOLD, 10));
        //Border Settings
        addSongButton.setBorderPainted(true);
        addSongButton.setBorder(new LineBorder((Color.LIGHT_GRAY)));
        addSongButton.setPreferredSize(new Dimension(100,25));

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(6, 0, 2, 0));
        buttonPanel.add(addSongButton);

        return buttonPanel;
    }

    /**
     * Adds the listeners of the AddSongView's components
     * @param controller an instance of ActionListener
     */
    public void registerController(ActionListener controller) {
        authorSelector.addActionListener(controller);
        authorSelector.setActionCommand(COMBOBOX_AUTHOR);

        addSongButton.addActionListener(controller);
        addSongButton.setActionCommand(BTN_ADD_SONG);

        addFileButton.addActionListener(controller);
        addFileButton.setActionCommand(BTN_SELECT_FILE);

        addImageButton.addActionListener(controller);
        addImageButton.setActionCommand(BTN_SELECT_IMAGE);
    }

    private final FileNameExtensionFilter MP3_FILTER = new FileNameExtensionFilter("MP3 file (.mp3)", "mp3");

    /**
     * Opens a JFileChooser dialog to select a file
     * @return the selected file or null if no file selected
     */
    public File selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(MP3_FILTER);

        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }

        return null;
    }

    private final FileNameExtensionFilter JPG_FILTER = new FileNameExtensionFilter("JPG file (.jpg)", "jpg");

    /**
     * Opens a JFileChooser dialog to select an image
     * @return the selected file or null if no file selected
     */
    public File selectImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(JPG_FILTER);

        int returnValue = fileChooser.showOpenDialog(this);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }

        return null;
    }

    /**
     * Public method that updated the authors list in the combo box selector
     * @param authors list of authors as Strings
     */
    public void setAuthors(ArrayList<String> authors){
        authorSelector.removeAllItems();
        authorSelector.addItem(SELECT_AUTHOR_ITEM);
        authorSelector.addItem(OTHER_ITEM);

        if (authors != null) {
            for (String author : authors) {
                authorSelector.addItem(author);
            }
        }

        authorSelector.setSelectedIndex(0);
        revalidate();
    }

    /**
     * Checks whether an author has been selected from available ones
     * @return a boolean indicating whether an author has been selected
     */
    public boolean authorSelected() {
        return !authorSelector.getItemAt(authorSelector.getSelectedIndex()).equals(SELECT_AUTHOR_ITEM)
                && !authorSelector.getItemAt(authorSelector.getSelectedIndex()).equals(OTHER_ITEM);
    }
    /**
     * Checks whether the user wants to create a new author
     * @return a boolean indicating whether the user selected the option that creates a new author
     */
    public boolean newAuthorSelected() {
        String item = authorSelector.getItemAt(authorSelector.getSelectedIndex());
        if (item != null) {
            return item.equals(OTHER_ITEM);
        } else {
            return false;
        }
    }

    /**
     * Shows the author textField
     */
    public void showAuthorField() {
        authorField.setVisible(true);
        this.revalidate();
    }


    /**
     * Hides the author textField
     */
    public void hideAuthorField() {
        authorField.setVisible(false);
        this.revalidate();
    }

    /**
     * Restores all components to the original state of the view
     */
    public void resetView() {
        restoreAuthorSelector();
        incorrectFieldLabel.setVisible(false);
        authorSelector.setSelectedIndex(0);
        genreSelector.setSelectedIndex(0);
        titleField.setText(null);
        albumField.setText(null);
        authorField.setText(null);

        titleField.setBackground(DEFAULT_LABEL_BG);
        albumField.setBackground(DEFAULT_LABEL_BG);
        authorField.setBackground(DEFAULT_LABEL_BG);
        addFileButton.setForeground(Color.LIGHT_GRAY);

        this.revalidate();
    }

    /**
     * Highlights the title field and shows a message indicating the error
     * @param message: a String containing the error message
     */
    public void incorrectTitleField(String message) {
        incorrectFieldLabel.setText(message);
        incorrectFieldLabel.setVisible(true);
        titleField.setBackground(DEFAULT_ERROR_LABEL_BG);
    }

    /**
     * Highlights the album field and shows a message indicating the error
     * @param message: a String containing the error message
     */
    public void incorrectAlbumField(String message) {
        incorrectFieldLabel.setText(message);
        incorrectFieldLabel.setVisible(true);
        albumField.setBackground(DEFAULT_ERROR_LABEL_BG);
    }

    private final String NO_AUTHOR_SELECTED = "Please select an author";

    /**
     * Highlights the authors combobox and shows a message indicating the error
     */
    public void incorrectAuthorSelection() {
        incorrectFieldLabel.setText(NO_AUTHOR_SELECTED);
        incorrectFieldLabel.setVisible(true);
        authorSelector.setForeground(DEFAULT_ERROR_LABEL_BG);
    }

    /**
     * Highlights the author field and shows a message indicating the error
     * @param message: a String containing the error message
     */
    public void incorrectAuthorField(String message) {
        incorrectFieldLabel.setText(message);
        incorrectFieldLabel.setVisible(true);
        authorField.setBackground(DEFAULT_ERROR_LABEL_BG);
    }

    /**
     * Highlights the select file button and shows a message indicating the error
     * @param message: a String containing the error message
     */
    public void incorrectFile(String message) {
        incorrectFieldLabel.setText(message);
        incorrectFieldLabel.setVisible(true);
        addFileButton.setForeground(DEFAULT_ERROR_LABEL_BG);
    }

    /**
     * Restores the appearance of the title field
     */
    public void restoreTitleField() {
        titleField.setBackground(DEFAULT_LABEL_BG);
    }

    /**
     * Restores the appearance of the album field
     */
    public void restoreAlbumField() {
        albumField.setBackground(DEFAULT_LABEL_BG);
    }

    /**
     * Restores the appearance of the authors combobox
     */
    public void restoreAuthorSelector() {
        authorSelector.setForeground(Color.GRAY);
    }

    /**
     * Restores the appearance of the file selector button
     */
    public void restoreFileButton() {
        addFileButton.setForeground(Color.LIGHT_GRAY);
    }

    /**
     * Restores the appearance of the author field
     */
    public void restoreAuthorField() {
        authorField.setBackground(DEFAULT_LABEL_BG);
    }

    /**
     * Title field text getter
     * @return a String containing the title of the song
     */
    public String getTitle() {
        return titleField.getText();
    }

    /**
     * Album field text getter
     * @return a String containing the album of the song
     */
    public String getAlbum() {
        return albumField.getText();
    }

    /**
     * Genre selection getter
     * @return an item in the {@link Genre} enumeration
     */
    public Genre getGenre() {
        return genreSelector.getItemAt(genreSelector.getSelectedIndex());
    }

    /**
     * Author selection getter
     * @return a String containing the author of the song
     */
    public String getAuthorItem() {
        return authorSelector.getItemAt(authorSelector.getSelectedIndex());
    }

    /**
     * Author field text getter
     * @return a String containing the author of the song
     */
    public String getAuthorField() {
        return authorField.getText();
    }

    /**
     * Opens an error dialog
     * @param message: a String containing the message to display in the dialog
     */
    public void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(null, message, ERROR_DIALOG_TITLE, JOptionPane.ERROR_MESSAGE);
    }
}