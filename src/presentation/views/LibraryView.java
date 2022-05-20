package presentation.views;

import business.entities.Playlist;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class LibraryView extends JPanel {

    private JTable table;
    private JPanel tableSongs;

    // Boolean indicating if it's the first time acceding to the view
    private boolean notFirstTime;

    public LibraryView() {
        this.setLayout(new BorderLayout());
        this.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        this.add(titleLabel(), BorderLayout.NORTH);
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
    public void fillTable(ArrayList<Playlist> myPlaylists) {
        // Inserting the data to each column
        String[][] data = new String[myPlaylists.size()][2];

        for (int i = 0; i < myPlaylists.size(); i++) {
            data[i][0] = myPlaylists.get(i).getName();
            data[i][1] = String.valueOf(myPlaylists.get(i).getOwner());
        }
        // Inserting the column titles
        String[] column = {"Name","Owner"};

        // Creating and personalizing JTable
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
        notFirstTime = true;
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

    /*
     * Method that returns the JTable with the search song label
     * @return the JTable with the search song label
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
}