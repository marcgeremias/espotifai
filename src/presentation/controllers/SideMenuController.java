package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import presentation.views.PlayerView;
import presentation.views.SearchView;
import presentation.views.SideMenuView;
import presentation.views.UserProfileView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static presentation.views.PlayerView.SONG_LIST_VIEW;

public class SideMenuController implements ActionListener {

    private PlayerViewListener listener;
    private SideMenuView sideMenuView;
    private UserManager userManager;
    private SongManager songManager;
    private PlaylistManager playlistManager;

    public SideMenuController(PlayerViewListener listener, SideMenuView sideMenuView,
                              UserManager userManager, PlaylistManager playlistManager,
                              SongManager songManager) {
        this.listener = listener;
        this.sideMenuView = sideMenuView;
        this.userManager = userManager;
        this.playlistManager = playlistManager;
        this.songManager = songManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case SideMenuView.HOME_BUTTON:
                listener.changeView(PlayerView.HOME_VIEW);
                break;
            case SideMenuView.SEARCH_BUTTON:
                listener.changeView(PlayerView.SONG_LIST_VIEW);
                //FER CRIDA A GET COSES

                break;
            case SideMenuView.LIBRARY_BUTTON:
                listener.changeView(PlayerView.LIBRARY_VIEW);
                break;
            case SideMenuView.ADD_MUSIC_BUTTON:
                listener.changeView(PlayerView.ADD_SONG_VIEW);
                break;
            case SideMenuView.CREATE_PLAYLIST_BUTTON:
                System.out.println("Goes to the createPlaylist view");
                break;
            default:
                break;
        }
    }
}
