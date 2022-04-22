package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import presentation.views.SongDetailView;
import presentation.views.UserProfileView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserProfileController implements ActionListener {

    private PlayerViewListener listener;
    private UserProfileView userProfileView;
    private UserManager userManager;
    private SongManager songManager;
    private PlaylistManager playlistManager;

    public UserProfileController(PlayerViewListener listener, UserProfileView userProfileView, UserManager userManager,
                                SongManager songManager, PlaylistManager playlistManager) {
        this.listener = listener;
        this.userProfileView = userProfileView;
        this.userManager = userManager;
        this.songManager = songManager;
        this.playlistManager = playlistManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
