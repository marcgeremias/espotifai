package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import presentation.views.PlayerView;
import presentation.views.SideMenuView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                listener.changeView(PlayerView.DEFAULT_VIEW);
                break;
            case SideMenuView.SEARCH_BUTTON:
                listener.changeView(PlayerView.SONG_LIST_VIEW);
                break;
            case SideMenuView.LIBRARY_BUTTON:
                listener.changeView(PlayerView.LIBRARY_VIEW);
                break;
            case SideMenuView.ADD_MUSIC_BUTTON:
                listener.changeView(PlayerView.ADD_SONG_VIEW);
                break;
            case SideMenuView.CREATE_PLAYLIST_BUTTON:
                //listener.changeView(PlayerView.);
                System.out.println("Goes to the createPlaylist view");
                break;
            case SideMenuView.SETTINGS:
                listener.changeView(PlayerView.USER_PROFILE_VIEW);
                break;
            case SideMenuView.LOGOUT:
                listener.logout();
                break;
            default:
                break;
        }
    }
}
