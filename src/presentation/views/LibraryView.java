package presentation.views;

import business.PlaylistManager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class LibraryView extends JPanel {

    private JTable table;
    private JPanel tableSongs;
    DefaultTableModel tableModel;

    // Boolean indicating if it's the first time acceding to the view
    private boolean notFirstTime;

    private static final String[] columns = {"Name"};

    public LibraryView() {
        this.setLayout(new BorderLayout());
        this.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        this.add(titleLabel(), BorderLayout.NORTH);
        this.add(center(), BorderLayout.CENTER);
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setOpaque(true);
    }

    /*
     * Method to configure all the center components and containers of the Library view
     * @return the JPanel with all the center of the Library view
     */
    private Component center() {
        tableSongs = new JPanel(new GridLayout());
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        table.getTableHeader().setReorderingAllowed(false);
        table.setOpaque(false);
        tableModel.setDataVector(null, columns);

        // Creating and personalizing JTable
        if (notFirstTime) {
            tableSongs = new JPanel(new GridLayout());
        }
        tableSongs.setOpaque(false);

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
        table.getColumnModel().getColumn(0).setResizable(false);

        table.setShowGrid(false);
        table.setFillsViewportHeight(true);

        JScrollPane pane = new JScrollPane(table);
        pane.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        pane.setBorder(BorderFactory.createEmptyBorder());
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
        notFirstTime = true;

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
     * Method that fills the JTable with the playlist of the current user in the JTable
     * @param myPlaylists an arraylist of songs that are currently in the system
     */
    //TODO: fix encapsulation
    public void fillTable(ArrayList<ArrayList<String>> myPlaylists) {
        // Inserting the data to each column
        String[][] data = new String[myPlaylists.size()][2];

        for (int i = 0; i < myPlaylists.size(); i++) {
            data[i][0] = myPlaylists.get(i).get(PlaylistManager.PLAYLIST_NAME_ATTRIBUTE_INDEX);
            data[i][1] = myPlaylists.get(i).get(PlaylistManager.PLAYLIST_OWNER_ATTRIBUTE_INDEX);
        }

        tableModel.setDataVector(data, columns);

        revalidate();
        repaint();
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

    /*
     * Method that returns the JTable with the title label
     * @return the JTable with the title label
     */
    private Component titleLabel(){
        JLabel searchSong = new JLabel("MY PLAYLISTS");
        searchSong.setForeground(Color.WHITE);
        searchSong.setFont(new Font("Apple Casual", Font.BOLD, 30));
        searchSong.setHorizontalAlignment(JLabel.CENTER);

        JPanel panelSearch = new JPanel();
        panelSearch.setBorder(BorderFactory.createEmptyBorder(5,15,30,15));
        panelSearch.setOpaque(false);
        panelSearch.add(searchSong);
        return panelSearch;
    }

    /**
     * Method that gets the selected row of the table
     * @return the selected row of the table
     */
    public int getSelectedRow() {
        return table.getSelectedRow();
    }

    /**
     * Method that shows an error message if we could not get the user playlists
     * @param message the message of the error we want to display
     */
    public void getPlaylistError(String message) {
        JOptionPane.showMessageDialog(this,message);
    }
}