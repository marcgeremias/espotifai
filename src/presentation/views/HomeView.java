package presentation.views;

import business.entities.Playlist;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class HomeView extends JPanel {

    private JTable table;
    private JPanel tableSongs;
    DefaultTableModel tableModel;
    // Boolean indicating if it's the first time acceding to the view
    private boolean notFirstTime;
    private int selectedRow;

    private static final String[] column = {"Name", "Owner"};

    public HomeView() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.DARK_GRAY);
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

        tableModel = new DefaultTableModel(null, column);
        tableModel.fireTableDataChanged();
        table = new JTable(tableModel);

        tableSongs.setOpaque(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.setOpaque(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setDefaultEditor(Object.class, null);

        // Personalizing UI
        table.getTableHeader().setForeground(Color.BLACK);
        table.getTableHeader().setBackground(Color.WHITE);
        table.getTableHeader().setFont(new Font("arial", Font.BOLD, 15));
        table.setBackground(Color.DARK_GRAY);
        table.setForeground(Color.WHITE);
        table.setFont(new Font("arial", Font.PLAIN, 15));
        table.getTableHeader().setBackground(Color.DARK_GRAY);
        table.getTableHeader().setForeground(Color.WHITE);

        table.setRowHeight(40);
        resizeColumnWidth(table);

        table.setShowGrid(false);
        JScrollPane pane = new JScrollPane(table);
        pane.setBackground(Color.DARK_GRAY);
        pane.setBorder(BorderFactory.createEmptyBorder());
        table.setFillsViewportHeight(true);
        pane.getViewport().setBackground(Color.DARK_GRAY);
        pane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.LIGHT_GRAY;
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
     * Method that fills the JTable with the other playlists of the other users in the JTable
     * @param othersPlaylists an arraylist of songs that are currently in the system
     */
    public void fillTable(ArrayList<Playlist> othersPlaylists) {
        if (notFirstTime) {
            tableSongs = new JPanel(new GridLayout());
            //System.out.println("ROWS: " + tableModel.getRowCount());
        }

        String[][] data = new String[othersPlaylists.size()][2];

        for (int i = 0; i < othersPlaylists.size(); i++) {
            data[i][0] = othersPlaylists.get(i).getName();
            data[i][1] = String.valueOf(othersPlaylists.get(i).getOwner());
        }

        tableModel.setDataVector(data, column);

        // Saving the selected Row to know what song i
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
        JLabel searchSong = new JLabel("OTHER PLAYLISTS");
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
     * We get the selected row of the table
     * @return an int with the index of the selected row
     */
    public int getSelectedRow() {
        return table.getSelectedRow();
    }
}