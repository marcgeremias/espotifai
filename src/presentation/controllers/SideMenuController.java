package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import presentation.views.SearchView;
import presentation.views.SideMenuView;
import presentation.views.UserProfileView;

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
        switch (e.getActionCommand()) {
            case SideMenuView.HOME_BUTTON:
                System.out.println("Opens the homeview");
                break;
            case SideMenuView.SEARCH_BUTTON:
                System.out.println("Goes to the searchView");
                break;
            case SideMenuView.LIBRARY_BUTTON:
                System.out.println("Goes to the library section");
                break;
            case SideMenuView.ADD_MUSIC_BUTTON:
                System.out.println("Goes to the addMusic view");
                break;
            case SideMenuView.CREATE_PLAYLIST_BUTTON:
                System.out.println("Goes to the createPlaylist view");
                break;
            default:
                break;
        }
    }
}
