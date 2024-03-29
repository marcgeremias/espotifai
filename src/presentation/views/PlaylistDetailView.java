package presentation.views;

import business.PlaylistManager;
import business.SongManager;
import business.entities.Playlist;
import business.entities.Song;
import presentation.controllers.PlayerViewListener;
import presentation.views.components.HoverButton;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;


/**
 * Class that shows the PLaylistDetail view and extend from JFrame
 */
public class PlaylistDetailView extends JPanel {

    private JPanel panelPlaylistModify;
    private JTable table;
    private JPanel tableSongs;
    private DefaultTableModel tableModel;
    private JLabel playlistTitle;

    private static final String[] columns = {"Title",  "Genre","Album","Author","Uploaded By"};
    public static final String BTN_ADD_SONG = "BTN ADD SONG";
    public static final String BTN_DELATE_SONG = "BTN DELETE SONG";
    public static final String JCOMBOX_SONG = "JCOMBOX_SONG";

    public static final String BTN_MOVE_UP = "BTN MOVE UP";
    public static final String BTN_MOVE_DOWN = "BTN MOVE DOWN";

    private JButton deleteSong;
    private JComboBox<String> jSelectSong;
    private JButton addSong;

    private JButton upButton;
    private JButton downButton;

    /**
     * Method to add the listener to the Login view buttons
     */
    public void registerController(ActionListener controller) {
        // Action listener for delate song
        deleteSong.addActionListener(controller);
        deleteSong.setActionCommand(BTN_DELATE_SONG);

        // Action listener for add song
        addSong.addActionListener(controller);
        addSong.setActionCommand(BTN_ADD_SONG);

        // Action listener for select songs
        jSelectSong.addActionListener(controller);
        jSelectSong.setActionCommand(JCOMBOX_SONG);

        // Action listener for move down
        downButton.addActionListener(controller);
        downButton.setActionCommand(BTN_MOVE_DOWN);

        // Action listener for move up
        upButton.addActionListener(controller);
        upButton.setActionCommand(BTN_MOVE_UP);
    }

    /**
     * Constructor method to set up the view
     */
    public PlaylistDetailView() {
        this.setLayout(new BorderLayout());
        this.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        this.add(titleLabel(), BorderLayout.NORTH);
        this.add(center(), BorderLayout.CENTER);
        this.add(down(), BorderLayout.SOUTH);
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setOpaque(true);
    }

    /*
     * Method that configures all the south side of the view
     */
    private Container down() {
        panelPlaylistModify = new JPanel();

        panelPlaylistModify.setBackground(Color.GRAY);
        panelPlaylistModify.setOpaque(true);

        jSelectSong = new JComboBox<>();

        jSelectSong.setBackground(new Color(76, 76, 76));
        jSelectSong.setForeground(Color.GRAY);

        deleteSong = new HoverButton(Color.DARK_GRAY, Color.BLACK, "Delete Song");
        deleteSong.setBackground(Color.BLACK);
        deleteSong.setForeground(Color.LIGHT_GRAY);
        deleteSong.setFont(new Font("Apple Casual", Font.BOLD, 10));
        //Border Settings
        deleteSong.setBorderPainted(true);
        deleteSong.setBorder(new LineBorder((Color.LIGHT_GRAY)));
        deleteSong.setPreferredSize(new Dimension(100,25));

        addSong = new HoverButton(Color.DARK_GRAY, Color.BLACK, "Add Song");
        addSong.setBackground(Color.BLACK);
        addSong.setForeground(Color.LIGHT_GRAY);
        addSong.setFont(new Font("Apple Casual", Font.BOLD, 10));
        //Border Settings
        addSong.setBorderPainted(true);
        addSong.setBorder(new LineBorder((Color.LIGHT_GRAY)));
        addSong.setPreferredSize(new Dimension(100,25));

        upButton = new HoverButton(Color.DARK_GRAY, Color.BLACK, "Move Up");
        upButton.setBackground(Color.BLACK);
        upButton.setForeground(Color.LIGHT_GRAY);
        upButton.setFont(new Font("Apple Casual", Font.BOLD, 10));
        //Border Settings
        upButton.setBorderPainted(true);
        upButton.setBorder(new LineBorder((Color.LIGHT_GRAY)));
        upButton.setPreferredSize(new Dimension(100,25));

        downButton = new HoverButton(Color.DARK_GRAY, Color.BLACK, "Move Down");
        downButton.setBackground(Color.BLACK);
        downButton.setForeground(Color.LIGHT_GRAY);
        downButton.setFont(new Font("Apple Casual", Font.BOLD, 10));
        //Border Settings
        downButton.setBorderPainted(true);
        downButton.setBorder(new LineBorder((Color.LIGHT_GRAY)));
        downButton.setPreferredSize(new Dimension(100,25));

        panelPlaylistModify.add(deleteSong);
        panelPlaylistModify.add(addSong);
        panelPlaylistModify.add(jSelectSong);

        panelPlaylistModify.add(downButton);
        panelPlaylistModify.add(upButton);

        return panelPlaylistModify;
    }

    /*
     * Method that configures all the center side of the view
     */
    private Component center() {
        tableSongs = new JPanel(new GridLayout());

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);

        table.getTableHeader().setReorderingAllowed(false);
        table.setOpaque(false);

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
        tableSongs.setOpaque(false);

        table.setShowGrid(false);
        JScrollPane pane = new JScrollPane(table);
        pane.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        pane.setBorder(BorderFactory.createEmptyBorder());
        table.setFillsViewportHeight(true);
        pane.getViewport().setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        pane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.DARK_GRAY;
            }
        });
        pane.getVerticalScrollBar().setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        pane.getHorizontalScrollBar().setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        tableSongs.add(pane);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.add(tableSongs);
        center.setOpaque(false);
        return center;
    }


    /**
     * Method to adds the listener to the song list view JTable columns to acceding or reproducing songs
     */
    public void registerMouseController(MouseListener mouseListener) {
        table.addMouseListener(mouseListener);
    }


    /**
     * Method used to fill the jTable playlist songs
     * @param mySongs an ArrayList of an ArrayList of String with the songs from the actual playlist
     * @param playlistSelected Actual playlist selected
     */
    public void fillTable(ArrayList<ArrayList<String>> mySongs, ArrayList<String> playlistSelected) {
        // Setting the playlist Title
        playlistTitle.setText(playlistSelected.get(PlaylistManager.PLAYLIST_NAME_ATTRIBUTE_INDEX));
        String[][] data = null;

        if(mySongs != null){
            data = new String[mySongs.size()][5];

            // In case there is any song to show
            // Inserting the data to each column

            for (int i = 0; i < mySongs.size(); i++) {
                data[i][0] = mySongs.get(i).get(SongManager.SONG_TITLE_ATTRIBUTE_INDEX);
                data[i][1] = String.valueOf(mySongs.get(i).get(SongManager.SONG_GENRE_ATTRIBUTE_INDEX));
                data[i][2] = mySongs.get(i).get(SongManager.SONG_ALBUM_ATTRIBUTE_INDEX);
                data[i][3] = mySongs.get(i).get(SongManager.SONG_AUTHOR_ATTRIBUTE_INDEX);
                data[i][4] = mySongs.get(i).get(SongManager.SONG_USER_ATTRIBUTE_INDEX);
            }
        }

        tableModel.setDataVector(data, columns);
        tableModel.fireTableDataChanged();


        revalidate();
        repaint();
    }

    /**
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
            //Se establece el ancho de la columna
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    /**
     * Method that returns the JTable with the playlist Label
     * @return the JTable with the playlist label
     */
    private Component titleLabel() {
        playlistTitle = new JLabel();
        playlistTitle.setForeground(Color.WHITE);
        playlistTitle.setFont(new Font("Apple Casual", Font.BOLD, 30));
        playlistTitle.setHorizontalAlignment(JLabel.CENTER);

        JPanel panelSearch = new JPanel();
        panelSearch.setBorder(BorderFactory.createEmptyBorder(5, 15, 30, 15));
        panelSearch.setOpaque(false);
        panelSearch.add(playlistTitle);
        return panelSearch;
    }


    /**
     * Method used to fill the comboBox song options
     * @param notMySongs ArrayList with all songs from the system
     */
    public void fillSongsToAdd(ArrayList<ArrayList<String>> notMySongs) {
        jSelectSong.removeAllItems();

        for (ArrayList<String> notMySong : notMySongs) {
            jSelectSong.addItem(notMySong.get(SongManager.SONG_TITLE_ATTRIBUTE_INDEX));
        }
    }
    /**
     * Getter used to get the actual selected JComboBox
     * @return The song index
     */
    public int getSelectSong() {
        return jSelectSong.getSelectedIndex();
    }

    /**
     * Method used to set the JPanel enable or not in case the user dont' have premisses
     * @param enable If is true the jPanel is visible, otherwise false.
     */
    public void setModify(boolean enable){
        panelPlaylistModify.setVisible(enable);
    }

    /**
     * Method used to notify an error to the user
     * @param message Message to notify
     */
    public void notifyError(String message){
        JOptionPane.showMessageDialog(this, message);
    }

    /**
     * Getter used to know the selected row
     * @return Returns the selected row
     */
    public int getSelectedRow(){
        return table.getSelectedRow();
    }

    /**
     * Public method that gets the currently selected row
     * @return integer containing the row count
     */
    public int getSelectedCol() {
        return table.getSelectedColumn();
    }
}