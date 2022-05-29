package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import persistence.PlaylistDAOException;
import presentation.views.CreatePlaylistView;
import presentation.views.PlayerView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Public controller to control the creation of playlists
 */
public class CreatePlaylistController implements ActionListener {

    private PlayerViewListener listener;
    private CreatePlaylistView createPlaylistView;
    private UserManager userManager;
    private PlaylistManager playlistManager;

    /**
     * Creates a new instance of CreatePlaylistController
     * @param listener an instance of PlayerViewListener
     * @param createPlaylistView an instance of CreatePlaylistView
     * @param userManager an instance of UserManager
     * @param playlistManager an instance of PlaylistManager
     */
    public CreatePlaylistController(PlayerViewListener listener, CreatePlaylistView createPlaylistView,
                                    UserManager userManager, PlaylistManager playlistManager) {
        this.listener = listener;
        this.createPlaylistView = createPlaylistView;
        this.userManager = userManager;
        this.playlistManager = playlistManager;
    }

    /**
     * Decides which action to execute
     * @param e an instance of ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case CreatePlaylistView.BTN_CREATE_PLAYLIST:

                String textField = createPlaylistView.getNameField();
                if (!textField.equals("")) {
                    try {
                        // We create the user's playlist
                        playlistManager.createPlaylist(textField, userManager.getCurrentUser());

                        // We change the view to library view to show that the playlist has correctly added
                        listener.changeView(PlayerView.LIBRARY_VIEW);

                    } catch (PlaylistDAOException ex) {
                        // We display an error if the playlist could not be created
                        createPlaylistView.createPlaylistError("The Playlist could not be added");
                    }
                }
                break;
        }
    }
}