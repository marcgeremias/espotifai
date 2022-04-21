package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import presentation.views.PlaylistDetailView;
import presentation.views.SongDetailView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlaylistDetailController implements ActionListener {
    private PlayerViewListener listener;
    private PlaylistDetailView playlistDetailView;
    private UserManager userManager;
    private SongManager songManager;
    private PlaylistManager playlistManager;

    public PlaylistDetailController(PlayerViewListener listener, PlaylistDetailView playlistDetailView,
                                    UserManager userManager,SongManager songManager, PlaylistManager playlistManager) {
        this.listener = listener;
        this.playlistDetailView = playlistDetailView;
        this.userManager = userManager;
        this.songManager = songManager;
        this.playlistManager = playlistManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
