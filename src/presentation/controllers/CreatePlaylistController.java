package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import persistence.PlaylistDAOException;
import presentation.views.CreatePlaylistView;
import presentation.views.PlayerView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreatePlaylistController implements ActionListener {

    private PlayerViewListener listener;
    private CreatePlaylistView createPlaylistView;
    private UserManager userManager;
    private SongManager songManager;
    private PlaylistManager playlistManager;

    public CreatePlaylistController(PlayerViewListener listener, CreatePlaylistView createPlaylistView, UserManager userManager,
                                    SongManager songManager, PlaylistManager playlistManager) {
        this.listener = listener;
        this.createPlaylistView = createPlaylistView;
        this.userManager = userManager;
        this.songManager = songManager;
        this.playlistManager = playlistManager;
    }


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
