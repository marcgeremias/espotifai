package presentation.views;

import javax.swing.*;
import java.awt.*;

public class CreatePlaylistView extends JPanel{
    public static final String BTN_ADD_PLAYLIST = "BTN_ADD_SONG";

    public CreatePlaylistView() {
        this.setLayout(new BorderLayout());
        this.setBackground(PlayerView.CENTER_BACKGROUND_COLOR);
        this.add(titleLabel(), BorderLayout.NORTH);
        this.add(center(), BorderLayout.CENTER);
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setOpaque(true);
    }

    /*
     * Method to configure all the center components and containers of the Create Playlist view
     * @return the JPanel with all the center of the Create Playlist view
     */
    private Component center() {
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        //center.add()
        //center.add(addButton());
        center.setOpaque(false);
        return center;
    }

    private Component addButton() {
        return null;
    }

    private Component titleLabel() {
        JLabel searchSong = new JLabel("CREATE PLAYLIST");
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
