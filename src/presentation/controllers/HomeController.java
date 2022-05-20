package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import presentation.views.HomeView;
import presentation.views.StatsView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeController implements ActionListener {

    private HomeView homeView;
    private SongManager songManager;
    private PlaylistManager playlistManager;

    public HomeController(HomeView homeView, SongManager songManager, PlaylistManager playlistManager) {
        this.homeView = homeView;
        this.songManager = songManager;
        this.playlistManager = playlistManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
