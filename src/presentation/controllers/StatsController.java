package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import presentation.views.StatsView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatsController implements ActionListener {
    private PlayerViewListener listener;
    private StatsView statsView;
    private UserManager userManager;
    private SongManager songManager;
    private PlaylistManager playlistManager;

    public StatsController(PlayerViewListener listener, StatsView statsView, UserManager userManager,
                             SongManager songManager, PlaylistManager playlistManager) {
        this.listener = listener;
        this.statsView = statsView;
        this.userManager = userManager;
        this.songManager = songManager;
        this.playlistManager = playlistManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}