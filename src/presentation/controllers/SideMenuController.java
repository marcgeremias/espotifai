package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import presentation.views.SearchView;
import presentation.views.SideMenuView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SideMenuController implements ActionListener {

    private PlayerViewListener listener;
    private SideMenuView sideMenuView;
    private UserManager userManager;
    private PlaylistManager playlistManager;

    public SideMenuController(PlayerViewListener listener, SideMenuView sideMenuView,
                              UserManager userManager, PlaylistManager playlistManager) {
        this.listener = listener;
        this.sideMenuView = sideMenuView;
        this.userManager = userManager;
        this.playlistManager = playlistManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
