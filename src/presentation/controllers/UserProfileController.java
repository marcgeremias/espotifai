package presentation.controllers;

import business.PlayerManager;
import business.PlaylistManager;
import business.SongManager;
import business.UserManager;

import business.entities.Song;
import presentation.views.MainView;
import presentation.views.PlayerView;
import presentation.views.UserProfileView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserProfileController implements ActionListener {

    private static final String ERROR_DELETE_SONG_MSG = "Cannot delete this song!";

    private PlayerViewListener listener;
    private UserProfileView userProfileView;
    private SongManager songManager;
    private PlayerManager playerManager;

    public UserProfileController(PlayerViewListener listener, UserProfileView userProfileView,
                                 SongManager songManager, PlayerManager playerManager) {
        this.listener = listener;
        this.userProfileView = userProfileView;
        this.songManager = songManager;
        this.playerManager = playerManager;
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
                        Song currentSong = playerManager.getCurrentSongAttributes();
                        if (currentSong != null) {
                            if (songManager.songCanBeDeleted(currentSong.getId())) {
                                listener.delete();
                            } else {
                                userProfileView.showErrorDialog(ERROR_DELETE_SONG_MSG);
                            }
                        } else {
                            listener.delete();
                        }
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
