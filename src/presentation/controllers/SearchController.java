package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import presentation.views.LibraryView;
import presentation.views.SearchView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchController implements ActionListener {

    private PlayerViewListener listener;
    private SearchView searchView;
    private UserManager userManager;
    private SongManager songManager;
    private PlaylistManager playlistManager;

    public SearchController(PlayerViewListener listener, SearchView searchView, UserManager userManager,
                             SongManager songManager, PlaylistManager playlistManager) {
        this.listener = listener;
        this.searchView = searchView;
        this.userManager = userManager;
        this.songManager = songManager;
        this.playlistManager = playlistManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
