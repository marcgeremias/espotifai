package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import business.entities.User;
import presentation.views.HomeView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeController implements ActionListener {

    private PlayerViewListener listener;
    private HomeView homeView;
    private UserManager userManager;
    private SongManager songManager;
    private PlaylistManager playlistManager;

    public HomeController(PlayerViewListener listener, HomeView homeView, UserManager userManager,
                          SongManager songManager, PlaylistManager playlistManager) {
        this.listener = listener;
        this.homeView = homeView;
        this.userManager = userManager;
        this.songManager = songManager;
        this.playlistManager = playlistManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
