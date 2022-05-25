package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import business.entities.Playlist;
import business.entities.Song;
import persistence.PlaylistDAOException;
import presentation.views.SongDetailView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SongDetailController implements ActionListener, LyricsListener {
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
        // Get song selected
        currentSong = songManager.getAllSongs().get(songNum);
        songDetailView.fillTable(currentSong);

        // Get all playlists
        try {
            allPlaylists = playlistManager.getAllPlaylists();
        } catch (PlaylistDAOException e) {
        }
        songDetailView.showPlaylists(allPlaylists);

        // Get lyrics
        System.out.println(currentSong.getTitle());
        System.out.println(currentSong.getAuthor());
        //songManager.getLyrics(this, currentSong.getTitle(), currentSong.getAuthor());
        songManager.getLyrics(this, currentSong.getTitle(), "Bad Bunny");

        //System.out.println(songLyrics);

        //if (songLyrics != null) {
        //}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case SongDetailView.BTN_ADD_PLAYLIST:
                System.out.println("ADD PLAYLIST CLICKED");

                int playlistIndex = songDetailView.getPlaylistIndexSelected();

                if (playlistIndex != -1) {
                    Playlist playlist = allPlaylists.get(playlistIndex);

                    try {
                        if (playlistManager.addSongToPlaylist(playlist.getId(), currentSong.getId())) {
                            System.out.println("THE SONG HAS CORRECTLY ADDED");
                        }

                    } catch (PlaylistDAOException ex) {
                    }
                }
                break;

            case SongDetailView.BTN_PLAY_IMAGE:
                System.out.println("REPRODUCE MUSICCC");
                break;
        }
    }

    @Override
    public void notifyLyricsDone(String lyrics) {
        songDetailView.setSongLyrics(lyrics);
    }

    @Override
    public void notifyError() {
        songDetailView.lyricsError();
    }
}
