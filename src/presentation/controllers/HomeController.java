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

/**
 * Public controller to control views
 */
public class HomeController implements MouseListener {
    private PlayerViewListener listener;
    private HomeView homeView;
    private UserManager userManager;
    private PlaylistManager playlistManager;
    private ArrayList<ArrayList<String>> otherPlaylists;

    /**
     * Creates an instance of HomeController
     * @param listener an instance of PlayerViewListener
     * @param homeView an instance of HomeView
     * @param userManager an instance of UserManager
     * @param playlistManager an instance of PlaylistManager
     */
    public HomeController(PlayerViewListener listener, HomeView homeView, UserManager userManager, PlaylistManager playlistManager) {
        this.listener = listener;
        this.homeView = homeView;
        this.userManager = userManager;
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

    /**
     * Shows the selected playlist when clicked
     * @param e an instance of MouseEvent
     */
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
