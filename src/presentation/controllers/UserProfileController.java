package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;

import presentation.views.MainView;
import presentation.views.PlayerView;
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

    public void setNickname(String username){
        userProfileView.setUsername(username);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case UserProfileView.DELETE_ACCOUNT:
                // Shows an external panel with the deletion confirmation
                switch (userProfileView.showConfirmationPanel()){
                    case 0:
                        listener.delete();
                        break;
                    case 1:
                        listener.changeView(PlayerView.USER_PROFILE_VIEW);
                        break;
                    default:
                        break;
                }
                break;
            case UserProfileView.BACK_BUTTON:
                listener.changeView(PlayerView.DEFAULT_VIEW);
            default:
                break;
        }
    }
}
