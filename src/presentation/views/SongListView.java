package presentation.views;

import business.entities.Song;
import presentation.views.components.PlaceholderTextField;
import presentation.views.components.TextField;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class SongListView extends JPanel {

    private PlaceholderTextField searchField;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTable table;
    private JPanel tableSongs;
    private boolean notFirstTime;

    public SongListView() {
        this.setLayout(new BorderLayout());
        this.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        //this.add(rightMargin(), BorderLayout.EAST);
        //this.add(leftMargin(), BorderLayout.WEST);
        this.add(searchSongLabel(), BorderLayout.NORTH);
        //this.add(downMargin(), BorderLayout.SOUTH);
        this.add(center(), BorderLayout.CENTER);
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setOpaque(true);
    }

    private Component center() {
        tableSongs = new JPanel(new GridLayout());
        searchField = new PlaceholderTextField();
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.add(new TextField("Filter", searchField));
        center.add(tableSongs);
        center.setOpaque(false);
        return center;
    }

    public void registerController(KeyListener controller) {
        searchField.addKeyListener(controller);
    }

    public void filter() {
        sorter.setRowFilter(RowFilter.regexFilter(searchField.getText()));
    }

    /**
     * Method that makes the bottom view
     * @return JPanel with the list of songs
     */
    private Component tableSongs() {
        JPanel tableSongs = new JPanel(new GridLayout());
        tableSongs.setOpaque(false);
        String data[][] = {
                {"Nano","Salsa","Machaca","Melendi", "Armand"},
                {"102","Sachin","700000","asdf", "onsdf"},
                {"awefasdfasdfasdf","Sachin","700000","asdf", "onsdf"},
                {"104","Sachin","700000","asdfasfasfdasdf", "onsdf"},
                // {"1ojppopopojompmpmmpm05","Saciiiiijoiiiiiiiiiopooppojpojpojiniiiiiiuhin","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa","asdf", "onpojjopojpopojpopjinooooooooijoooooooooooosdf"},
                {"106","Sachin","700000","asdf", "onsdf"},
                {"107","Sachin","700000","asdf", "onsdf"},
                {"108","Sachin","700000","asdf", "onsdf"},
                {"109","Sachin","700000","asdf", "onsdf"},
                {"110","Sachin","700000","asdf", "onsdf"},
                {"111","Sachin","700000","asdf", "onsdf"},
                {"112","Sachin","700000","asdf", "onsdf"},
                {"113","Sachin","700000","asdf", "onsdf"},
                {"114","Sachin","700000","asdf", "onsdf"},
                {"115","Sachin","700000","asdf", "onsdf"}};
        String column[] = {"Title","Genre","Album","Author", "User Uploaded"};
       // String data [][] = getSongsData();
        table = new JTable(data, column);
        table.getTableHeader().setReorderingAllowed(false);
        table.setOpaque(false);
        DefaultTableModel tableModel = new DefaultTableModel(data, column);
        table.setModel(tableModel);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setDefaultEditor(Object.class, null);
        table.setAutoCreateRowSorter(true);
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);



        table.getTableHeader().setForeground(Color.BLACK);
        table.getTableHeader().setBackground(Color.WHITE);
        table.getTableHeader().setFont(new Font("arial", Font.BOLD, 15));
        table.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        table.setForeground(Color.WHITE);
        table.setFont(new Font("arial", Font.PLAIN, 15));
        table.getTableHeader().setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);

        //DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        //renderer.setHorizontalAlignment(SwingConstants.LEFT);

        //renderer.setBackground(Color.DARK_GRAY);

        table.setRowHeight(40);
        resizeColumnWidth(table);
        //table.getColumnModel().getColumn(1).setPreferredWidth(40);
        //table.getColumnModel().getColumn(4).setPreferredWidth(50);
        for (int i = 0; i < column.length; i++) {
          //  table.getColumnModel().getColumn(i).setCellRenderer(renderer);
            table.getColumnModel().getColumn(i).setResizable(false);
        }
        table.setShowGrid(false);
        tableSongs.setOpaque(false);
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
        return tableSongs;
    }

    /**
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

    public void fillTable(ArrayList<Song> currentSongs) {
        String[][] data = new String[currentSongs.size()][5];

        for (int i = 0; i < currentSongs.size(); i++) {
            data[i][0] = currentSongs.get(i).getTitle();
            data[i][1] = String.valueOf(currentSongs.get(i).getGenre());
            data[i][2] = currentSongs.get(i).getAlbum();
            data[i][3] = currentSongs.get(i).getAuthor();
            data[i][4] = currentSongs.get(i).getUser();
        }

        String[] column = {"Title","Genre","Album","Author", "User Uploaded"};
        tableSongs.setOpaque(false);
        table = new JTable(data, column);
        if (notFirstTime) {
            tableSongs = new JPanel(new GridLayout());
        }

        table.getTableHeader().setReorderingAllowed(false);
        table.setOpaque(false);
        DefaultTableModel tableModel = new DefaultTableModel(data, column);
        table.setModel(tableModel);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setDefaultEditor(Object.class, null);
        table.setAutoCreateRowSorter(true);
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

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
}