package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import presentation.views.AddSongView;
import presentation.views.HomeView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddSongController implements ActionListener {

    private PlayerViewListener listener;
    private AddSongView addSongView;
    private UserManager userManager;
    private SongManager songManager;
    private PlaylistManager playlistManager;

    public AddSongController(PlayerViewListener listener, AddSongView addSongView, UserManager userManager,
                          SongManager songManager, PlaylistManager playlistManager) {
        this.listener = listener;
        this.addSongView = addSongView;
        this.userManager = userManager;
        this.songManager = songManager;
        this.playlistManager = playlistManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}