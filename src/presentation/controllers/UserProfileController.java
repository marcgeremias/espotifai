package presentation.controllers;

import business.PlayerManager;
import business.SongManager;

import presentation.views.PlayerView;
import presentation.views.UserProfileView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Public controller that controls the deletion of a {@link business.entities.User}
 */
public class UserProfileController implements ActionListener {

    private static final String ERROR_DELETE_SONG_MSG = "Cannot delete this song!";

    private PlayerViewListener listener;
    private UserProfileView userProfileView;
    private SongManager songManager;
    private PlayerManager playerManager;

    /**
     * Creates an instance of UserProfileController
     * @param listener an instance of PlayerViewListener
     * @param userProfileView an instance of UserProfileView
     * @param songManager an instance of SongManager
     * @param playerManager an instance of PlayerManager
     */
    public UserProfileController(PlayerViewListener listener, UserProfileView userProfileView,
                                 SongManager songManager, PlayerManager playerManager) {
        this.listener = listener;
        this.userProfileView = userProfileView;
        this.songManager = songManager;
        this.playerManager = playerManager;
    }

    /**
     * Sets the user nickname
     * @param username a String containing the username
     */
    public void setNickname(String username){
        userProfileView.setUsername(username);
    }

    /**
     * Decides which action to execut
     * @param e an instance of ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case UserProfileView.DELETE_ACCOUNT:
                // Shows an external panel with the deletion confirmation
                switch (userProfileView.showConfirmationPanel()){
                    case 0:
                        ArrayList<String> currentSong = playerManager.getCurrentSongAttributes();
                        if (currentSong != null) {
                            if (songManager.songCanBeDeleted(Integer.parseInt(currentSong.get(SongManager.SONG_ID_ATTRIBUTE_INDEX)))) {
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