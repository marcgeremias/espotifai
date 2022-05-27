package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import business.entities.Playlist;
import persistence.PlaylistDAOException;
import presentation.views.LibraryView;

import java.awt.event.*;
import java.util.ArrayList;

public class LibraryController implements MouseListener {

    private PlayerViewListener listener;
    private LibraryView libraryView;
    private UserManager userManager;
    private SongManager songManager;
    private PlaylistManager playlistManager;
    private ArrayList<Playlist> myPlaylists;

    public LibraryController(PlayerViewListener listener, LibraryView libraryView, UserManager userManager,
                             SongManager songManager, PlaylistManager playlistManager) {
        this.listener = listener;
        this.libraryView = libraryView;
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
            myPlaylists = playlistManager.getCurrentUserPlaylists(currentUser);
        } catch (PlaylistDAOException e) {
            // We display an error if we could not get the user playlists
            libraryView.getPlaylistError("The Playlist could not be added");
        }

        libraryView.fillTable(myPlaylists);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // The user clicked more than once
        if (e.getClickCount() >= 1) {
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
