package presentation.views;

import business.SongManager;
import business.entities.Song;
import presentation.views.components.PlaceholderTextField;
import presentation.views.components.TextField;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Public class for the song list graphic interface
 */
public class SongListView extends JPanel {

    private static final String[] column = {"Title","Genre","Album","Author", "Uploaded By"};

    private PlaceholderTextField searchField;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTable table;
    private JPanel tableSongs;
    private DefaultTableModel tableModel;

    /**
     * Public constructor method that initializes all the components in the view
     */
    public SongListView() {
        this.setLayout(new BorderLayout());
        this.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        this.add(searchSongLabel(), BorderLayout.NORTH);
        this.add(center(), BorderLayout.CENTER);
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setOpaque(true);
    }

    /*
     * Method to configure all the center components and containers of the SongList view
     * @return the JPanel with all the center of the Login view
     */
    private Component center() {
        tableSongs = new JPanel(new GridLayout());
        tableSongs.setOpaque(false);
        searchField = new PlaceholderTextField();
        table = new JTable();
        tableModel = new DefaultTableModel(null, column);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setDefaultEditor(Object.class, null);


        table.getTableHeader().setReorderingAllowed(false);
        table.setOpaque(false);

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
        center.add(new TextField("Filter", searchField));
        center.add(tableSongs);
        center.setOpaque(false);
        return center;
    }

    /**
     * Method to adds the listener to the song list view text field to filter songs
     */
    public void registerKeyController(KeyListener keyListener) {
        searchField.addKeyListener(keyListener);
    }

    /**
     * Method to adds the listener to the song list view JTable columns to acceding or reproducing songs
     */
    public void registerMouseController(MouseListener mouseListener) {
        table.addMouseListener(mouseListener);
    }

    /**
     * Method that filters a song with a sorter in the search text field
     */
    public void filter() {
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchField.getText()));
    }

    /**
     * Method that fills the JTable with the songs in the system and personalize the JTable
     * @param currentSongs an arraylist of songs that are currently in the system
     */
    public void fillTable(ArrayList<ArrayList<String>> currentSongs) {
        // Inserting the data to each column
        String[][] data = new String[currentSongs.size()][5];
        for (int i = 0; i < currentSongs.size(); i++) {
            data[i][0] = currentSongs.get(i).get(SongManager.SONG_TITLE_ATTRIBUTE_INDEX);
            data[i][1] = currentSongs.get(i).get(SongManager.SONG_GENRE_ATTRIBUTE_INDEX);
            data[i][2] = currentSongs.get(i).get(SongManager.SONG_ALBUM_ATTRIBUTE_INDEX);
            data[i][3] = currentSongs.get(i).get(SongManager.SONG_AUTHOR_ATTRIBUTE_INDEX);
            data[i][4] = currentSongs.get(i).get(SongManager.SONG_USER_ATTRIBUTE_INDEX);
        }


        // Creating and personalizing JTable
        searchField.setText("");

        tableModel.setDataVector(data, column);

        table.setModel(tableModel);

        tableModel.fireTableDataChanged();

        // Creating the filter sorter
        table.setAutoCreateRowSorter(true);
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        filter();


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
     * Method that returns the JTable with the search song label
     * @return the JTable with the search song label
     */
    private Component searchSongLabel(){
        JLabel searchSong = new JLabel("SEARCH SONG");
        searchSong.setForeground(Color.WHITE);
        searchSong.setFont(new Font("Apple Casual", Font.BOLD, 30));
        searchSong.setHorizontalAlignment(JLabel.CENTER);

        JPanel panelSearch = new JPanel();
        panelSearch.setBorder(BorderFactory.createEmptyBorder(5,15,5,15));
        panelSearch.setOpaque(false);
        panelSearch.add(searchSong);
        return panelSearch;
    }

    /**
     * Method that gets the selected row of the table
     * @return the selected row of the table
     */
    public int getTableRow(){
        return table.getSelectedRow();
    }

    /**
     * Method that gets the selected column of the table
     * @return the selected column of the table
     */
    public int getTableCol(){
        return table.getSelectedColumn();
    }

    /**
     * Method that gets the table value at the current row and column selected
     * @return the table value at the current row and column selected
     */
    public String getTableValue(int row, int col){
        return (String) table.getValueAt(row, col);
    }
}