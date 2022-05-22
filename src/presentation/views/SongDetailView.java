package presentation.views;

import business.entities.Playlist;
import business.entities.Song;
import presentation.views.components.JImagePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
    private JImagePanel listImage;
    private JImagePanel playButton;
    public static final String BTN_PLAY_IMAGE = "BTN PLAY IMAGE";
    public static final String BTN_ADD_PLAYLIST = "BTN ADD PLAYLIST";

    public static final String LOGO_IMAGE_PATH = "res/images/nyan_cat.png";
    public static final String LOGO_PLAY_PATH = "res/icons/play-button-green.png";

    public static final String PLAYLIST_TYPE = "SONG";
    public static final String ALBUM_TYPE = "ALBUM";
    private static final String LIST_NAME = "SONG";

    private JTable table;
    private JPanel tableSongs;
    private boolean notFirstTime;
    private JComboBox<String> playlistSelector;
    private JPanel playlistPane;
    private JButton addPlaylistButton;

    /**
     * Constructor method to set up the view
     */
    public SongDetailView() {
        this.setLayout(new BorderLayout());

        this.add(northMargin(), BorderLayout.NORTH);
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

       //addPlaylistButton.setActionCommand(BTN_ADD_PLAYLIST);
        //addPlaylistButton.addActionListener(controller);
    }


    /**
     * Method to configure all the center components and containers of the PlaylistDetail view
     * @return the JPanel with all the center of the PlaylistDetail view
     */
    private Component center() {
        tableSongs = new JPanel(new GridLayout());
        playlistSelector = new JComboBox<String>();
        playlistPane = new JPanel();
        //JPanel sencer
        JPanel center = new JPanel();
        center.setBorder(new EmptyBorder(20, 1, 1, 1));
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        //Part superior
        center.add(upUI());

        //Part inferior
        center.add(tableSongs, BorderLayout.SOUTH);
        center.add(playlistPane, BorderLayout.SOUTH);

        return center;
    }


    /**
     * Method to set the top JPanel
     * @return the JPanel on the top
     */
    private Component upUI() {
        JPanel upUI = new JPanel();
        // Margin with song list
        upUI.setPreferredSize(new Dimension(160, 160));
        //upUI.setPreferredSize(new Dimension(320, 320));
        //Part esquerre
        upUI.add(setImageList());
        //Part dreta
        upUI.add(setTexts());
        // Left alignment
        upUI.setLayout(new FlowLayout(FlowLayout.LEFT));

        upUI.setBackground(Color.MAGENTA);
        return upUI;
    }

    /**
     * Method that creates a BoxLayout with som texts
     * @return the BoxLayout created inside a JPanel
     */
    private Component setTexts() {
        JPanel textsFrame = new JPanel();
        // Alignment on Y_AXIS
        textsFrame.setLayout(new BoxLayout(textsFrame, BoxLayout.Y_AXIS));

        textsFrame.add(typeText());
        textsFrame.add(listName());

        return textsFrame;
    }

    /**
     * Method that creates the text of the type List
     * @return the text created inside a JPanel
     */
    private Component typeText() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel jLabel = new JLabel();
        jLabel.setFont(new Font("Arial", Font.BOLD, 15));
        jLabel.setText(PLAYLIST_TYPE);
        jPanel.add(jLabel);

        return jPanel;
    }

    /**
     * Method that creates the text of the name List
     * @return the text created inside a JPanel
     */
    private Component listName() {
        JPanel jPanel = new JPanel();

        JLabel jLabel = new JLabel();
        jLabel.setFont(new Font("Arial", Font.BOLD, 40));
        jLabel.setText(LIST_NAME);
        jPanel.add(jLabel);

        return jPanel;
    }

    /**
     * Method that sets the images of the top JPanel
     * @return JPanel with the cover image and button image
     */
    private Component setImageList(){
        JPanel general = new JPanel();

        JPanel frameImage = new JPanel();
        JPanel frameButton = new JPanel();
        frameButton.setLayout(new FlowLayout(FlowLayout.LEFT));

        general.setLayout(new BoxLayout(general, BoxLayout.Y_AXIS));

        // Defining the image
        // EN UN FUTUR S'HAURA DE TRANSFORMAR A BufferedImage PQ VINDRA DE LA DDBB
        listImage = new JImagePanel(LOGO_IMAGE_PATH, null, null);
        listImage.setPreferredSize(new Dimension(250, 250));
        frameImage.add(listImage);

        playButton = new JImagePanel(LOGO_PLAY_PATH, null, null);
        playButton.setPreferredSize(new Dimension(40, 40));
        frameButton.add(playButton);

        playButton.setBackground(Color.MAGENTA);
        // Add image to frame
        general.add(frameImage);
        general.add(frameButton);

        frameButton.setBackground(Color.MAGENTA);

        return general;
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
            tableSongs = new JPanel(new GridLayout());
        }
        tableSongs.setOpaque(false);
        table = new JTable(data, column);

        table.getTableHeader().setReorderingAllowed(false);
        table.setOpaque(false);
        DefaultTableModel tableModel = new DefaultTableModel(data, column);
        table.setModel(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setDefaultEditor(Object.class, null);

        // Personalizing UI
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
        pane.setPreferredSize(new Dimension(100, 100));
        tableSongs.add(pane);
        notFirstTime = true;
    }

    /**
     * Method that is in charge of the top margins of the window.
     * @return the container with the panel margin (without opacity)
     */
    public Container northMargin() {
        JPanel northMargin = new JPanel();
        northMargin.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));

        northMargin.setBackground(Color.blue);
        return northMargin;
    }

    /**
     * Method that is in charge of the top margins of the window.
     * @return the container with the panel margin (without opacity)
     */
    public Container westMargin() {
        JPanel westMargin = new JPanel();
        westMargin.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 50));

        westMargin.setBackground(Color.RED);
        return westMargin;
    }

    /**
     * Method that is in charge of the top margins of the window.
     * @return the container with the panel margin (without opacity)
     */
    public Container eastMargin() {
        JPanel eastMargin = new JPanel();
        eastMargin.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));

        eastMargin.setBackground(Color.CYAN);
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
        playlistPane.setLayout(new BoxLayout(playlistPane, BoxLayout.Y_AXIS));
        playlistPane.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        playlistPane.add(playlistSelector);
    }
}
