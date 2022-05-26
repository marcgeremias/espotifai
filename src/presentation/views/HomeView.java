package presentation.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HomeView extends JPanel {

    public HomeView() {
        configureView();
    }

    private void configureView() {
        this.setLayout(new BorderLayout());
        this.add(mainSection(), BorderLayout.CENTER);
    }

    private Component mainSection() {
        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.setBackground(Color.DARK_GRAY);
        mainContainer.setForeground(Color.LIGHT_GRAY);

        JLabel titlePlaylists = new JLabel("Playlists");
        titlePlaylists.setFont(new Font("Apple Casual", Font.BOLD, 40));
        titlePlaylists.setForeground(Color.WHITE);
        mainContainer.add(titlePlaylists);
        mainContainer.add(playlistsSection());

        JLabel titleSongs = new JLabel("Songs");
        titleSongs.setFont(new Font("Apple Casual", Font.BOLD, 30));
        titleSongs.setForeground(Color.WHITE);
        mainContainer.add(titleSongs);
        mainContainer.add(songsSection());

        mainContainer.setBorder(BorderFactory.createEmptyBorder(10, 100, 20, 80));
        return mainContainer;
    }

    private Component playlistsSection() {
        JPanel playlistsSection = new JPanel();
        playlistsSection.setBackground(Color.GREEN);
        return playlistsSection;
    }

    private Component songsSection() {
        JPanel songsSection = new JPanel();
        songsSection.setBackground(Color.RED);
        return songsSection;
    }

    public void registerController(ActionListener controller){

    }
}
