package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import presentation.views.AddSongView;
import presentation.views.CreatePlaylistView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
            case CreatePlaylistView.BTN_ADD_PLAYLIST:

                break;
        }

    }
}
