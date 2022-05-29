package presentation.controllers;

import presentation.views.PlayerView;
import presentation.views.SideMenuView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Public controller to control the components of the side menu
 */
public class SideMenuController implements ActionListener {

    private PlayerViewListener listener;

    /**
     * Creates a new instance of SideMenuController
     * @param listener an instance of PlayerViewListener
     */
    public SideMenuController(PlayerViewListener listener) {
        this.listener = listener;
    }

    /**
     * Decides which action to execute
     * @param e an instance of ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // We change to the button clicked view
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
                listener.changeView(PlayerView.CREATE_PLAYLIST);
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
