package presentation.views;

import business.entities.Genre;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class AddSongView extends JPanel {
    private JPanel centerPane;
    private PlaceholderTextField titleField;
    private PlaceholderTextField albumField;
    private PlaceholderTextField albumCover;
    private JComboBox<Genre> genreSelector;
    private JPanel authorPane;
    private JComboBox<String> authorSelector;
    private PlaceholderTextField authorField;
    private JButton addSongButton;
    private JLabel incorrectFieldLabel;
    private JButton addFileButton;

    public static final String COMBOBOX_AUTHOR = "COMBOBOX_AUTHOR";
    public static final String BTN_ADD_SONG = "BTN_ADD_SONG";
    public static final String BTN_SELECT_FILE = "SELECT FILE";

    private final String ADD_SONG_TITLE = "Add Song";
    private final String BUTTON_ADD_MSG = "Add";
    private final String SELECT_AUTHOR_ITEM = "Select author";
    private final String OTHER_ITEM = "Other";
    private final String BUTTON_SELECT_FILE = "Select file";
    private final String TITLE_FIELD_PH = "Title";
    private final String ALBUM_FIELD_PH = "Album";
    private final String ALBUM_COVER_PH = "Cover path";

    /**
     * Creates a new instance of AddSongView given a list of authors
     * @param authors: an ArrayList of String containing the authors
     */
    public AddSongView(ArrayList<String> authors) {
        this.setLayout(new BorderLayout());
        this.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);

        JPanel north = new JPanel();
        JPanel south = new JPanel();
        centerPane = new JPanel();
        titleField = new PlaceholderTextField();
        albumField = new PlaceholderTextField();
        albumCover = new PlaceholderTextField();
        genreSelector = new JComboBox<Genre>();
        authorPane = new JPanel();
        authorSelector = new JComboBox<String>();
        authorField = new PlaceholderTextField();
        addSongButton = new JButton(BUTTON_ADD_MSG);
        incorrectFieldLabel = new JLabel();
        addFileButton = new JButton(BUTTON_SELECT_FILE);

        north.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        north.setPreferredSize(new Dimension(
                this.getWidth(), ((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()) / 8
        ));

        south.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        south.setPreferredSize(new Dimension(
                this.getWidth(), ((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()) / 12
        ));
        incorrectFieldLabel.setForeground(new Color(220, 60, 25));
        incorrectFieldLabel.setVisible(false);
        south.add(incorrectFieldLabel);

        setCenter(authors);

        this.add(centerPane, BorderLayout.CENTER);
        this.add(south, BorderLayout.SOUTH);
        this.add(north, BorderLayout.NORTH);
    }

    /*
    * Configures the components in the center section of the view
    */
    private void setCenter(ArrayList<String> authors) {
        centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));
        centerPane.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);

        JLabel panelTitle = new JLabel(ADD_SONG_TITLE);
        panelTitle.setForeground(Color.WHITE);

        titleField.setText(null);
        albumField.setText(null);
        authorField.setText(null);
        albumCover.setText(null);

        // Genres JComboBox initialisation
        configureGenres();

        // Authors JCombobox initialisation
        configureAuthors(authors);

        // Add center components
        centerPane.add(panelTitle);
        centerPane.add(textField(TITLE_FIELD_PH, titleField));
        centerPane.add(textField(ALBUM_FIELD_PH, albumField));
        centerPane.add(genreSelector);
        centerPane.add(authorPane);
        centerPane.add(addFileButton);
        centerPane.add(textField(ALBUM_COVER_PH, albumCover));
        centerPane.add(addSongButton);
    }

    /*
    * Creates a JComboBox containing the genres in the system
    */
    private void configureGenres() {
        genreSelector = (JComboBox<Genre>) StyleApplier.applyStyle(genreSelector);

        for (Genre genre : Genre.values()) {
            genreSelector.addItem(genre);
        }

        genreSelector.setSelectedIndex(0);
    }

    /*
    * Creates a JComboBox from which to pick an author which adds a text field when option is "Other"
    */
    private void configureAuthors(ArrayList<String> authors) {
        // Authors JComboBox initialisation
        authorSelector = (JComboBox<String>) StyleApplier.applyStyle(authorSelector);
        authorSelector.addItem(SELECT_AUTHOR_ITEM);
        authorSelector.addItem(OTHER_ITEM);

        for (String author : authors) {
            authorSelector.addItem(author);
        }

        authorSelector.setSelectedIndex(0);

        // Author pane components
        authorPane.setLayout(new BoxLayout(authorPane, BoxLayout.Y_AXIS));
        authorPane.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        authorPane.add(authorSelector);
        authorField.setVisible(false);
        authorPane.add(textField("Author", authorField));
    }

    /**
     * A
     * @param controller
     */
    public void registerController(ActionListener controller) {
        authorSelector.addActionListener(controller);
        authorSelector.setActionCommand(COMBOBOX_AUTHOR);

        addSongButton.addActionListener(controller);
        addSongButton.setActionCommand(BTN_ADD_SONG);

        addFileButton.addActionListener(controller);
        addFileButton.setActionCommand(BTN_SELECT_FILE);
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
        return authorSelector.getItemAt(authorSelector.getSelectedIndex()).equals(OTHER_ITEM);
    }

    /**
     * Shows the author textField
     */
    public void showAuthorField() {
        authorField.setVisible(true);
        this.revalidate();
    }

    /**
     * Adds a new item in the author selector combobox
     * @param author: a String containing the name of the new author
     */
    public void addAuthor(String author) {
        authorSelector.addItem(author);
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
        albumCover.setText(null);

        titleField.setBackground(new Color(76, 76, 76));
        albumField.setBackground(new Color(76, 76, 76));
        authorField.setBackground(new Color(76, 76, 76));
        albumCover.setBackground(new Color(76, 76, 76));
        addFileButton.setForeground(Color.BLACK);

        this.revalidate();
    }

    /**
     * Highlights the title field and shows a message indicating the error
     * @param message: a String containing the error message
     */
    public void incorrectTitleField(String message) {
        incorrectFieldLabel.setText(message);
        incorrectFieldLabel.setVisible(true);
        titleField.setBackground(new Color(220, 60, 25));
    }

    /**
     * Highlights the album field and shows a message indicating the error
     * @param message: a String containing the error message
     */
    public void incorrectAlbumField(String message) {
        incorrectFieldLabel.setText(message);
        incorrectFieldLabel.setVisible(true);
        albumField.setBackground(new Color(220, 60, 25));
    }

    private final String NO_AUTHOR_SELECTED = "Please select an author";

    /**
     * Highlights the authors combobox and shows a message indicating the error
     */
    public void incorrectAuthorSelection() {
        incorrectFieldLabel.setText(NO_AUTHOR_SELECTED);
        incorrectFieldLabel.setVisible(true);
        authorSelector.setForeground(new Color(220, 60, 25));
    }

    /**
     * Highlights the author field and shows a message indicating the error
     * @param message: a String containing the error message
     */
    public void incorrectAuthorField(String message) {
        incorrectFieldLabel.setText(message);
        incorrectFieldLabel.setVisible(true);
        authorField.setBackground(new Color(220, 60, 25));
    }

    /**
     * Highlights the select file button and shows a message indicating the error
     * @param message: a String containing the error message
     */
    public void incorrectFile(String message) {
        incorrectFieldLabel.setText(message);
        incorrectFieldLabel.setVisible(true);
        addFileButton.setForeground(new Color(220, 60, 25));
    }

    /**
     * Restores the appearance of the title field
     */
    public void restoreTitleField() {
        titleField.setBackground(new Color(76, 76, 76));
    }

    /**
     * Restores the appearance of the album field
     */
    public void restoreAlbumField() {
        albumField.setBackground(new Color(76, 76, 76));
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
        addFileButton.setForeground(Color.BLACK);
    }

    /**
     * Restores the appearance of the author field
     */
    public void restoreAuthorField() {
        authorField.setBackground(new Color(76, 76, 76));
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
     * Path field text getter
     * @return a String containing the path of the song
     */
    public String getAlbumCover() {
        return albumCover.getText();
    }

    private final String ERROR_DIALOG_TITLE = "Error";
    /**
     * Opens an error dialog
     * @param message: a String containing the message to display in the dialog
     */
    public void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(null, message, ERROR_DIALOG_TITLE, JOptionPane.ERROR_MESSAGE);
    }

    // TODO: Try to generalize this function so that every PlaceholderTextField looks alike
    private Component textField(String placeHolder, PlaceholderTextField textField) {
        //Using own textField classes
        textField.setBorder(BorderFactory.createCompoundBorder(
                textField.getBorder(),
                BorderFactory.createEmptyBorder(5, 8, 5, 5)));
        textField.setColumns(28);
        textField.setBackground(new Color(76, 76, 76));
        textField.setForeground(Color.WHITE);
        textField.setPlaceholder(placeHolder);
        Font f = textField.getFont();
        textField.setFont(new Font(f.getName(), f.getStyle(), 12));
        JPanel auxPanel = new JPanel();
        auxPanel.setOpaque(false);
        auxPanel.add(textField);

        return auxPanel;
    }
}