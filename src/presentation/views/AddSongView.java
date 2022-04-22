package presentation.views;

import business.entities.Genre;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddSongView extends JPanel {
    private JPanel centerPane;
    private PlaceholderTextField titleField;
    private PlaceholderTextField albumField;
    private PlaceholderTextField pathField;
    private JComboBox<Genre> genreSelector;
    private JPanel authorPane;
    private JComboBox<String> authorSelector;
    private PlaceholderTextField authorField;
    private JButton addSongButton;
    private JLabel incorrectFieldLabel;

    public static final String COMBOBOX_AUTHOR = "COMBOBOX_AUTHOR";
    public static final String BTN_ADD_SONG = "BTN_ADD_SONG";

    private final String ADD_SONG_TITLE = "Add Song";
    private final String BUTTON_ADD_MSG = "Add";
    private final String SELECT_AUTHOR_ITEM = "Select author";
    private final String OTHER_ITEM = "Other";

    public AddSongView(ArrayList<String> authors) {
        this.setLayout(new BorderLayout());
        this.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        this.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JLabel north = new JLabel("NORTH");
        centerPane = new JPanel();
        titleField = new PlaceholderTextField();
        albumField = new PlaceholderTextField();
        pathField = new PlaceholderTextField();
        genreSelector = new JComboBox<Genre>();
        authorPane = new JPanel();
        authorSelector = new JComboBox<String>();
        authorField = new PlaceholderTextField();
        addSongButton = new JButton(BUTTON_ADD_MSG);
        incorrectFieldLabel = new JLabel();

        north.setForeground(Color.WHITE);
        incorrectFieldLabel.setForeground(new Color(220, 60, 25));
        incorrectFieldLabel.setVisible(false);

        setContents(authors);

        this.add(north, BorderLayout.NORTH);
        this.add(centerPane, BorderLayout.CENTER);
        this.add(incorrectFieldLabel, BorderLayout.SOUTH);
    }

    public void setContents(ArrayList<String> authors) {
        centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));
        centerPane.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        // TODO: Resize layout to fit placeholder size
        centerPane.setBorder(BorderFactory.createEmptyBorder(10, 200, 0, 250));
        centerPane.setAlignmentX(Component.CENTER_ALIGNMENT); // TODO: Not working??

        JLabel panelTitle = new JLabel(ADD_SONG_TITLE);
        panelTitle.setForeground(Color.WHITE);

        titleField.setText(null);
        albumField.setText(null);
        authorField.setText(null);
        pathField.setText(null);

        // Genres JComboBox initialisation
        genreSelector = (JComboBox<Genre>) StyleApplier.applyStyle(genreSelector);

        for (Genre genre : Genre.values()) {
            genreSelector.addItem(genre);
        }

        genreSelector.setSelectedIndex(0);

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

        // Add center components
        centerPane.add(panelTitle);
        centerPane.add(textField("Title", titleField));
        centerPane.add(textField("Album", albumField));
        centerPane.add(genreSelector);
        centerPane.add(authorPane);
        centerPane.add(textField("Path", pathField));
        centerPane.add(addSongButton);
    }

    public void registerController(ActionListener controller) {
        authorSelector.addActionListener(controller);
        authorSelector.setActionCommand(COMBOBOX_AUTHOR);

        addSongButton.addActionListener(controller);
        addSongButton.setActionCommand(BTN_ADD_SONG);
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
        //authorPane.revalidate();
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
        //authorPane.revalidate();
        this.revalidate();
    }

    /**
     * Restores all components to the original state of the view
     */
    public void resetView() {
        restoreAuthorSelector();
        authorSelector.setSelectedIndex(0);
        genreSelector.setSelectedIndex(0);
        titleField.setText(null);
        albumField.setText(null);
        authorField.setText(null);
        pathField.setText(null);

        titleField.setBackground(new Color(76, 76, 76));
        albumField.setBackground(new Color(76, 76, 76));
        authorField.setBackground(new Color(76, 76, 76));
        pathField.setBackground(new Color(76, 76, 76));

        this.revalidate();
    }

    private final String NO_AUTHOR_SELECTED = "Please select an author";

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
     * Restores the appearance of the title field
     */
    public void restoreTitleField() {
        incorrectFieldLabel.setVisible(false);
        titleField.setBackground(new Color(76, 76, 76));
    }

    /**
     * Restores the appearance of the album field
     */
    public void restoreAlbumField() {
        incorrectFieldLabel.setVisible(false);
        albumField.setBackground(new Color(76, 76, 76));
    }

    /**
     * Restores the appearance of the authors combobox
     */
    public void restoreAuthorSelector() {
        incorrectFieldLabel.setVisible(false);
        authorSelector.setForeground(Color.GRAY);
    }

    /**
     * Restores the appearance of the author field
     */
    public void restoreAuthorField() {
        incorrectFieldLabel.setVisible(false);
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
    public String getPath() {
        return pathField.getText();
    }

    // TODO: Try to generalize this function so that every PlaceholderTextField looks alike
    public Component textField(String placeHolder, PlaceholderTextField textField) {
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