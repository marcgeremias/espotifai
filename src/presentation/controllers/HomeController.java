package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import business.entities.Playlist;
import persistence.PlaylistDAOException;
import presentation.views.HomeView;

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
     * Method that initializes the homeControllerView by filling the table with the other playlist values
     */
    public void initView() {
        // We get the current user
        String currentUser = userManager.getCurrentUser();
        try {
            // We get the other playlist
            otherPlaylists = playlistManager.getOtherUserPlaylists(currentUser);
        } catch (PlaylistDAOException e) {
        }

        homeView.fillTable(otherPlaylists);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // More than one click
        if (e.getClickCount() >= 1 && homeView.getSelectedRow() != -1) {
            listener.showPlaylistDetails(otherPlaylists.get(homeView.getSelectedRow()));
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
