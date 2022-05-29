package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import business.entities.Playlist;
import persistence.PlaylistDAOException;
import presentation.views.LibraryView;

import java.awt.event.*;
import java.util.ArrayList;

/**
 * Public controller to control the user's playlists
 */
public class LibraryController implements MouseListener {

    private PlayerViewListener listener;
    private LibraryView libraryView;
    private UserManager userManager;
    private PlaylistManager playlistManager;
    private ArrayList<ArrayList<String>> myPlaylists;

    /**
     * Creates an instance of LibraryController
     * @param listener an instance of PlayerViewListener
     * @param libraryView an instance of LibraryView
     * @param userManager an instance of UserManager
     * @param playlistManager an instance of PlaylistManager
     */
    public LibraryController(PlayerViewListener listener, LibraryView libraryView,
                             UserManager userManager, PlaylistManager playlistManager) {
        this.listener = listener;
        this.libraryView = libraryView;
        this.userManager = userManager;
        this.playlistManager = playlistManager;
    }

    /**
     * Method that initializes the libraryView by getting the current user playlists
     * and passing them to the JTable
     */
    public void initView() {
        String currentUser = userManager.getCurrentUser();
        try {
            myPlaylists = playlistManager.getCurrentUserPlaylists(currentUser);
        } catch (PlaylistDAOException e) {
            // We display an error if we could not get the user playlists
            libraryView.getPlaylistError("The Playlist could not be added");
        }

        libraryView.fillTable(myPlaylists);
    }

    /**
     * Displays the playlist when clicked
     * @param e an instance of MouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // The user clicked more than once
        if (e.getClickCount() >= 1 && libraryView.getSelectedRow() != -1) {
            listener.showPlaylistDetails(myPlaylists.get(libraryView.getSelectedRow()));
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
