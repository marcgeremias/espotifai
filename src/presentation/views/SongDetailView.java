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

    // Image from the list of songs
    private JImagePanel playButton;
    public static final String BTN_PLAY_IMAGE = "BTN PLAY IMAGE";
    public static final String BTN_ADD_PLAYLIST = "BTN ADD PLAYLIST";

    public static final String LOGO_PLAY_PATH = "res/icons/play-button.png";

    private JTable table;
    private JPanel tableSong;
    private boolean notFirstTime;
    private JComboBox<String> playlistSelector;
    private JPanel playlistPane;
    private HoverButton addPlaylistButton;
    private JScrollPane lyricsScrollPane;

    /**
     * Constructor method to set up the view
     */
    public SongDetailView() {
        this.setLayout(new BorderLayout());
        tableSong = new JPanel(new GridLayout());
        tableSong.setBorder(new EmptyBorder(50, 50, 0, 50));
        this.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);

        this.add(tableSong, BorderLayout.NORTH);
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
    }


    /**
     * Method to configure all the center components and containers of the PlaylistDetail view
     * @return the JPanel with all the center of the PlaylistDetail view
     */
    private Component center() {
        playlistSelector = new JComboBox<String>();
        playlistPane = new JPanel();
        addPlaylistButton = new HoverButton(Color.DARK_GRAY, Color.BLACK, "ADD");
        lyricsScrollPane = new JScrollPane();

        //JPanel center config
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(true);
        center.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);

        //center.add(tableSong);
        center.add(addPanelLabel("LYRICS"));
        center.add(lyricsScrollPane);
        center.add(setPlayImage());
        center.add(addPanelLabel("ADD SONG INTO A PLAYLIST"));
        center.add(playlistPane);
        center.add(addPlaylistButton());

        return center;
    }

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

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 4, 20, 6));
        buttonPanel.add(addPlaylistButton);

        return buttonPanel;
    }

    private Component addPanelLabel(String message) {
        JLabel searchSong = new JLabel(message);
        searchSong.setForeground(Color.WHITE);
        searchSong.setFont(new Font("Apple Casual", Font.BOLD, 20));
        searchSong.setHorizontalAlignment(JLabel.CENTER);

        JPanel panelSearch = new JPanel();
        panelSearch.setBorder(BorderFactory.createEmptyBorder(40,15,0,15));
        panelSearch.setOpaque(false);
        panelSearch.add(searchSong);
        return panelSearch;
    }

    public void setSongLyrics(String lyrics) {
        JTextArea textArea = new JTextArea(lyrics, 10, 10);
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
        lyricsScrollPane.setPreferredSize(new Dimension(100, 100));

        revalidate();
        repaint();
    }

    /**
     * Method that sets the play image
     * @return JPanel with the button image
     */
    private Component setPlayImage(){
        JPanel playPanel = new JPanel();
        playPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        playPanel.setBorder(new EmptyBorder(2, 2, 20, 2));
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

        if (totalSecs >= 3600) {
            int hours = totalSecs / 3600;
            data[0][5] = String.format("%02d:%02d:%02d", hours, minutes, seconds);;
        } else {
            data[0][5] = String.format("%02d:%02d", minutes, seconds);;
        }

        // Inserting the column titles
        String[] column = {"Title","Genre","Album","Author", "User Uploaded", "Time"};

        if (notFirstTime) {
            // Resetting view
            table = null;
            tableSong = null;
            tableSong = new JPanel(new GridLayout());
            repaint();
            revalidate();
        }

        tableSong.setOpaque(true);
        tableSong.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        table = new JTable(data, column);

        table.getTableHeader().setReorderingAllowed(false);
        table.setOpaque(false);
        DefaultTableModel tableModel = new DefaultTableModel(data, column);
        //tableModel.fireTableDataChanged();
        table.repaint();
        table.setModel(tableModel);
        tableModel.fireTableDataChanged();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setDefaultEditor(Object.class, null);

        // Personalizing UI
        table.setFocusable(false);
        table.setRowSelectionAllowed(false);
        //table.setPreferredSize(new Dimension(70, 70));
        table.getTableHeader().setForeground(Color.BLACK);
        table.getTableHeader().setBackground(Color.WHITE);
        table.getTableHeader().setFont(new Font("arial", Font.BOLD, 15));
        table.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        table.setForeground(Color.WHITE);
        table.setFont(new Font("arial", Font.PLAIN, 15));
        table.getTableHeader().setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);

        table.setRowHeight(40);
        resizeColumnWidth(table);
        for (int i = 0; i < column.length; i++) {
            table.getColumnModel().getColumn(i).setResizable(false);
        }

        table.setShowGrid(false);
        table.setFillsViewportHeight(true);

        JScrollPane pane = new JScrollPane(table);
        pane.setPreferredSize(new Dimension(table.getWidth(), 62));
        pane.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        pane.setBorder(BorderFactory.createEmptyBorder());
        tableSong.add(pane);

        /*if (notFirstTime) {
            this.add(tableSong, BorderLayout.NORTH);
        }*/

        notFirstTime = true;

    }

    /**
     * Method that is in charge of the top margins of the window.
     * @return the container with the panel margin (without opacity)
     */
    public Container northMargin() {
        JPanel northMargin = new JPanel();
        northMargin.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));

        northMargin.setOpaque(false);
        return northMargin;
    }

    /**
     * Method that is in charge of the top margins of the window.
     * @return the container with the panel margin (without opacity)
     */
    public Container westMargin() {
        JPanel westMargin = new JPanel();
        westMargin.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 50));

        westMargin.setOpaque(false);
        return westMargin;
    }

    /**
     * Method that is in charge of the top margins of the window.
     * @return the container with the panel margin (without opacity)
     */
    public Container eastMargin() {
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
        //Se obtiene el modelo de la columna
        TableColumnModel columnModel = table.getColumnModel();
        //Se obtiene el total de las columnas
        for (int column = 0; column < table.getColumnCount(); column++) {
            //Establecemos un valor minimo para el ancho de la columna
            int width = 150; //Min Width
            //Obtenemos el numero de filas de la tabla
            for (int row = 0; row < table.getRowCount(); row++) {
                //Obtenemos el renderizador de la tabla
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                //Creamos un objeto para preparar el renderer
                Component comp = table.prepareRenderer(renderer, row, column);
                //Establecemos el width segun el valor maximo del ancho de la columna
                width = Math.max(comp.getPreferredSize().width + 1, width);

            }
            //Se establece una condicion para no sobrepasar el valor de 300
            //Esto es Opcional
            if (width > 300) {
                width = 300;
            }
            //Se establece el ancho de la columna
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    /**
     * Creates a JComboBox from which to pick a playlist to add in a song
     */
    public void showPlaylists(ArrayList<Playlist> allPlaylists) {
        // Authors JComboBox initialisation
        playlistSelector.setBackground(new Color(76, 76, 76));
        playlistSelector.setForeground(Color.GRAY);
        playlistSelector.addItem("Select Playlist");
        for (Playlist allPlaylist : allPlaylists) {
            playlistSelector.addItem(allPlaylist.getName());
        }

        playlistSelector.setSelectedIndex(0);

        // Author pane components
        playlistPane.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        playlistPane.add(playlistSelector);
    }

    public int getPlaylistIndexSelected() {
        return playlistSelector.getSelectedIndex() - 1;
    }

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

    public void lyricsError() {
        JOptionPane.showMessageDialog(this, "Error fetching the lyrics");
    }
}
