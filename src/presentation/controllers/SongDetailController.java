package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import business.entities.Playlist;
import business.entities.Song;
import persistence.PlaylistDAOException;
import presentation.views.AddSongView;
import presentation.views.PlaylistDetailView;
import presentation.views.SongDetailView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SongDetailController implements ActionListener {
    private PlayerViewListener listener;
    private SongDetailView songDetailView;
    private UserManager userManager;
    private SongManager songManager;
    private PlaylistManager playlistManager;
    private Song currentSong;
    ArrayList<Playlist> allPlaylists;

    public SongDetailController(PlayerViewListener listener, SongDetailView songDetailView, UserManager userManager,
                             SongManager songManager, PlaylistManager playlistManager) {
        this.listener = listener;
        this.songDetailView = songDetailView;
        this.userManager = userManager;
        this.songManager = songManager;
        this.playlistManager = playlistManager;
    }

    /**
     * Method that initializes the songListView by getting all current songs of the system
     * and passing them to the JTable of all songs in the system
     */
    public void initView(int songNum) {
        currentSong = songManager.getAllSongs().get(songNum);
        songDetailView.fillTable(currentSong);
        try {
            allPlaylists = playlistManager.getAllPlaylists();
        } catch (PlaylistDAOException e) {
        }
        songDetailView.showPlaylists(allPlaylists);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case PlaylistDetailView.BTN_PLAY_IMAGE:
                System.out.println("PLAY BUTTON CLICKED");
                break;
        }
    }
}
