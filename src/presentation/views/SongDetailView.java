package presentation.views;

import business.entities.Playlist;
import business.entities.Song;
import presentation.views.components.HoverButton;
import presentation.views.components.JImagePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * Class that shows the SongDetail view and extend from JFrame
 */
public class SongDetailView extends JPanel {

    private static final String[] column = {"Title","Genre","Album","Author", "Uploaded By", "Time"};

    // Image from the list of songs
    private JImagePanel playButton;
    public static final String BTN_PLAY_IMAGE = "BTN PLAY IMAGE";
    public static final String BTN_ADD_PLAYLIST = "BTN ADD PLAYLIST";
    public static final String LOGO_PLAY_PATH = "res/icons/play-button.png";
    public static final String BTN_DELETE_SONG = "BTN DELETE SONG";

    private static final String FETCHING_LYRICS_PLACEHOLDER = "Fetching the lyrics... This might take a while!";

    private JTable table;
    private JPanel tableSong;
    private JComboBox<String> playlistSelector;
    private JPanel playlistPane;
    private HoverButton addPlaylistButton;
    private JScrollPane lyricsScrollPane;
    private DefaultTableModel tableModel;
    private JTextArea textArea;
    private HoverButton deleteSongButton;

    /**
     * Constructor method to set up the view
     */
    public SongDetailView() {
        this.setLayout(new BorderLayout());
        this.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        this.add(north(), BorderLayout.NORTH);
        this.add(westMargin(), BorderLayout.WEST);
        this.add(eastMargin(), BorderLayout.EAST);
        this.add(center(), BorderLayout.CENTER);
    }

    /**
     * Method to add the listener to the playlist buttons
     */
    public void registerController(ActionListener controller) {
        playButton.setActionCommand(BTN_PLAY_IMAGE);
        playButton.addActionListener(controller);

        addPlaylistButton.setActionCommand(BTN_ADD_PLAYLIST);
        addPlaylistButton.addActionListener(controller);

        deleteSongButton.setActionCommand(BTN_DELETE_SONG);
        deleteSongButton.addActionListener(controller);
    }

    /*
     * Method that configures all the north side of the view
     */
    private Component north(){
        // We create all the attributes
        playlistSelector = new JComboBox<String>();
        playlistPane = new JPanel();
        addPlaylistButton = new HoverButton(Color.DARK_GRAY, Color.BLACK, "ADD");
        tableSong = new JPanel(new GridLayout());
        table = new JTable();
        tableModel = new DefaultTableModel(null, column);
        tableSong.setBorder(new EmptyBorder(50, 50, 0, 50));

        tableSong.setOpaque(true);
        tableSong.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        table.setModel(tableModel);

        table.getTableHeader().setReorderingAllowed(false);
        table.setOpaque(false);
        table.setModel(tableModel);
        tableModel.fireTableDataChanged();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setDefaultEditor(Object.class, null);

        // Personalizing UI
        table.setFocusable(false);
        table.setRowSelectionAllowed(false);
        table.getTableHeader().setFont(new Font("arial", Font.BOLD, 15));
        table.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        table.setForeground(Color.WHITE);
        table.setFont(new Font("arial", Font.PLAIN, 15));
        table.getTableHeader().setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);

        table.setRowHeight(40);
        resizeColumnWidth(table);

        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFillsViewportHeight(true);
        table.setBorder(null);

        JScrollPane pane = new JScrollPane(table);
        pane.setPreferredSize(new Dimension(table.getWidth(), 62));
        pane.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        pane.setBorder(BorderFactory.createEmptyBorder());
        tableSong.add(pane);

        JPanel north = new JPanel();
        north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
        north.setOpaque(true);
        north.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);

        JPanel accionablesPane = new JPanel();
        accionablesPane.setOpaque(false);
        accionablesPane.setLayout(new BoxLayout(accionablesPane, BoxLayout.X_AXIS));
        accionablesPane.add(setPlayImage());

        JPanel addSongToPlaylistPane = new JPanel();
        addSongToPlaylistPane.setOpaque(false);
        addSongToPlaylistPane.add(addPanelLabel("ADD TO PLAYLIST"));
        addSongToPlaylistPane.add(playlistPane);
        addSongToPlaylistPane.add(addPlaylistButton());

        accionablesPane.add(addSongToPlaylistPane);
        north.add(tableSong);
        north.add(accionablesPane);

        return north;
    }

    /*
     * Method to configure all the center components and containers of the SongDetail view
     * @return the JPanel with all the center of the SongtDetail view
     */
    private Component center() {
        lyricsScrollPane = new JScrollPane();
        deleteSongButton = new HoverButton(Color.DARK_GRAY, Color.BLACK, "DELETE");

        //JPanel center config
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(true);
        center.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);

        textArea = new JTextArea(FETCHING_LYRICS_PLACEHOLDER, 10, 10);
        textArea.setEditable(false);
        textArea.setForeground(Color.WHITE);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        textArea.setFont(new Font("Apple Casual", Font.BOLD, 12));
        textArea.setHighlighter(null);
        textArea.setWrapStyleWord(true);

        lyricsScrollPane.setViewportView(textArea);
        lyricsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanePersonalized(lyricsScrollPane);

        JLabel searchSong = new JLabel("LYRICS");
        searchSong.setForeground(Color.WHITE);
        searchSong.setFont(new Font("Apple Casual", Font.BOLD, 16));
        searchSong.setHorizontalAlignment(JLabel.CENTER);

        JPanel panelSearch = new JPanel();
        panelSearch.setOpaque(false);
        panelSearch.add(searchSong);

        center.add(lyricsScrollPane);
        center.add(deleteSongButton());

        return center;
    }

    /*
     * Method that adds the playlist button
     * @return a panel with the playlist button inside
     */
    private Component addPlaylistButton() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        addPlaylistButton.setBackground(Color.BLACK);
        addPlaylistButton.setForeground(Color.LIGHT_GRAY);
        addPlaylistButton.setFont(new Font("Apple Casual", Font.BOLD, 10));

        //Border Settings
        addPlaylistButton.setBorderPainted(true);
        addPlaylistButton.setBorder(new LineBorder((Color.LIGHT_GRAY)));
        addPlaylistButton.setPreferredSize(new Dimension(100,25));

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        buttonPanel.add(addPlaylistButton);

        return buttonPanel;
    }

    private Component deleteSongButton() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        deleteSongButton.setBackground(Color.BLACK);
        deleteSongButton.setForeground(Color.LIGHT_GRAY);
        deleteSongButton.setFont(new Font("Apple Casual", Font.BOLD, 10));
        //Border Settings
        deleteSongButton.setBorderPainted(true);
        deleteSongButton.setBorder(new LineBorder((Color.LIGHT_GRAY)));
        deleteSongButton.setPreferredSize(new Dimension(100,25));

        deleteSongButton.setVisible(false);

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        buttonPanel.add(deleteSongButton);

        return buttonPanel;
    }

    /*
     * Method that adds the label of the title of the view
     */
    private Component addPanelLabel(String message) {
        JLabel searchSong = new JLabel(message);
        searchSong.setForeground(Color.WHITE);
        searchSong.setFont(new Font("Apple Casual", Font.BOLD, 16));
        searchSong.setHorizontalAlignment(JLabel.CENTER);

        JPanel panelSearch = new JPanel();
        panelSearch.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panelSearch.setOpaque(false);
        panelSearch.add(searchSong);
        return panelSearch;
    }

    /**
     * Method that set the lyrics of the song
     * @param lyrics the lyrics of the song
     */
    public void setSongLyrics(String lyrics) {
        textArea.setText(lyrics);
        revalidate();
        repaint();
    }

    /*
     * Method that sets the play image
     * @return JPanel with the button image
     */
    private Component setPlayImage(){
        JPanel playPanel = new JPanel();
        playPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        playPanel.setBorder(BorderFactory.createEmptyBorder(10, 4, 20, 6));
        playButton = new JImagePanel(LOGO_PLAY_PATH, null, null);
        playButton.setPreferredSize(new Dimension(40, 40));
        playPanel.add(playButton);
        playButton.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        playPanel.setOpaque(false);
        return playPanel;
    }


    /**
     * Method that fills the JTable with the songs in the system and personalize the JTable
     * @param currentSong an arraylist of songs that are currently in the system
     */
    //TODO: change encapsulation
    public void fillTable(Song currentSong) {
        // Inserting the data to each column
        String[][] data = new String[1][6];
        data[0][0] = currentSong.getTitle();
        data[0][1] = String.valueOf(currentSong.getGenre());
        data[0][2] = currentSong.getAlbum();
        data[0][3] = currentSong.getAuthor();
        data[0][4] = currentSong.getUser();

        int totalSecs = currentSong.getDuration();
        int minutes = (totalSecs % 3600) / 60;
        int seconds = totalSecs % 60;

        data[0][5] = String.format("%02d:%02d", minutes, seconds);;

        tableModel.setDataVector(data, column);
        tableModel.fireTableDataChanged();

        // Update the text inside the lyrics textarea
        textArea.setText(FETCHING_LYRICS_PLACEHOLDER);

        revalidate();
        repaint();
    }

    /*
     * Method that is in charge of the top margins of the window.
     * @return the container with the panel margin (without opacity)
     */
    public Container northMargin() {
        JPanel northMargin = new JPanel();
        northMargin.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));

        northMargin.setOpaque(false);
        return northMargin;
    }

    /*
     * Method that is in charge of the top margins of the window.
     * @return the container with the panel margin (without opacity)
     */
    private Container westMargin() {
        JPanel westMargin = new JPanel();
        westMargin.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 50));

        westMargin.setOpaque(false);
        return westMargin;
    }

    /*
     * Method that is in charge of the top margins of the window.
     * @return the container with the panel margin (without opacity)
     */
    private Container eastMargin() {
        JPanel eastMargin = new JPanel();
        eastMargin.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));

        eastMargin.setOpaque(false);
        return eastMargin;
    }

    /*
     * Method extracted from:
     * https://es.stackoverflow.com/questions/345550/c%C3%B3mo-autoajustar-el-ancho-de-una-columna-de-un-jtable-al-contenido-que-hay-en-e
     * @param table the JTable we are resizing
     */
    private void resizeColumnWidth(JTable table) {
        TableColumnModel columnModel = table.getColumnModel();

        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 150;
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }

            if (width > 300) {
                width = 300;
            }
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    /**
     * Creates a JComboBox from which to pick a playlist to add in a song
     */
    public void showPlaylists(ArrayList<Playlist> allPlaylists) {
        // Authors JComboBox initialisation
        playlistSelector.removeAllItems();
        playlistSelector.setBackground(new Color(76, 76, 76));
        playlistSelector.setForeground(Color.GRAY);
        playlistSelector.addItem("Select Playlist");
        for (Playlist allPlaylist : allPlaylists) {
            playlistSelector.addItem(allPlaylist.getName());
        }

        playlistSelector.setSelectedIndex(0);

        // Author pane components
        playlistPane.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        playlistPane.setBorder(BorderFactory.createEmptyBorder(20, 4, 20, 6));
        playlistPane.add(playlistSelector);
    }

    /**
     * Method that gets the playlist index selected
     * @return the playlist index selected -1 because the first index is not valid
     */
    public int getPlaylistIndexSelected() {
        return playlistSelector.getSelectedIndex() - 1;
    }

    /*
     * Method that personalize the scroll pane bar
     */
    private void scrollPanePersonalized(JScrollPane scrollPane) {
        scrollPane.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.DARK_GRAY;
            }
        });
        scrollPane.getVerticalScrollBar().setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        scrollPane.getHorizontalScrollBar().setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
    }

    /**
     * Method that shows an error message dialog when the lyrics didn't fetch correctly
     * @param message the message of the error we are going to display
     */
    public void lyricsError(String message) {
        textArea.setText("Error loading lyrics");
        JOptionPane.showMessageDialog(this,message);
    }

    /**
     * Activates the button to delete a song
     */
    public void enableDeleteSongButton() {
        deleteSongButton.setVisible(true);
    }

    /**
     * Deactivates the button to delete a song
     */
    public void disableDeleteSongButton() {
        deleteSongButton.setVisible(false);
    }

    private final String DELETE_SONG_DIALOG_TITLE = "Delete song";

    /**
     * Opens a dialog to confirm song deletion
     * @param message a String containing the message of the dialog
     * @return an int representing the selected option
     */
    public int confirmSongDeletion(String message) {
        return JOptionPane.showConfirmDialog(null, message, DELETE_SONG_DIALOG_TITLE, JOptionPane.YES_NO_OPTION);
    }

    /**
     * Opens an error dialog
     * @param message a String containing the error message
     */
    public void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
