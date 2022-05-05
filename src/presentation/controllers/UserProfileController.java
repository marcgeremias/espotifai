package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
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

        // Sets the user in the User Profile View
        userProfileView.setUsername("Guillem HARDCODED");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case UserProfileView.DELETE_ACCOUNT:
                System.out.println("The Delete Account button has been pressed");
                // Shows an external panel with the deletion confirmation
                userProfileView.showConfirmationPanel();
                // Deletes an account
                break;
            case UserProfileView.BACK_BUTTON:
                System.out.println("The Back Button has been pressed");
                // Goes to the main view
            default:
                break;
        }
    }
}
