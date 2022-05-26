package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import presentation.views.LibraryView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibraryController implements ActionListener {

    private PlayerViewListener listener;
    private LibraryView libraryView;
    private UserManager userManager;
    private SongManager songManager;
    private PlaylistManager playlistManager;

    public LibraryController(PlayerViewListener listener, LibraryView libraryView, UserManager userManager,
                             SongManager songManager, PlaylistManager playlistManager) {
        this.listener = listener;
        this.libraryView = libraryView;
        this.userManager = userManager;
        this.songManager = songManager;
        this.playlistManager = playlistManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
