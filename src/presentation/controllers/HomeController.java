package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import business.entities.Playlist;
import persistence.PlaylistDAOException;
import presentation.views.HomeView;
import presentation.views.LibraryView;
import presentation.views.PlayerView;
import presentation.views.StatsView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class HomeController implements MouseListener {

    private PlayerViewListener listener;
    private HomeView homeView;
    private UserManager userManager;
    private SongManager songManager;
    private PlaylistManager playlistManager;
    private ArrayList<Playlist> otherPlaylists;

    public HomeController(PlayerViewListener listener, HomeView homeView, UserManager userManager, SongManager songManager, PlaylistManager playlistManager) {
        this.listener = listener;
        this.homeView = homeView;
        this.userManager = userManager;
        this.songManager = songManager;
        this.playlistManager = playlistManager;
    }

    /**
     * Method that initializes the songListView by getting all current songs of the system
     * and passing them to the JTable of all songs in the system
     */
    public void initView() {
        String currentUser = userManager.getCurrentUser();
        try {
            otherPlaylists = playlistManager.getOtherUserPlaylists(currentUser);
        } catch (PlaylistDAOException e) {
        }

        homeView.fillTable(otherPlaylists);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Single click
        if (e.getClickCount() >= 1) {
            listener.changeView(PlayerView.PLAYLIST_DETAIL_VIEW);
            //reproduce song
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
