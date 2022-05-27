package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import business.entities.Playlist;
import business.entities.Song;
import persistence.PlaylistDAOException;
import presentation.views.PlayerView;
import presentation.views.SongDetailView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SongDetailController implements ActionListener, LyricsListener {
    private PlayerViewListener listener;
    private SongDetailView songDetailView;
    private SongManager songManager;
    private UserManager userManager;
    private PlaylistManager playlistManager;
    private Song currentSong;
    private ArrayList<Playlist> allPlaylists;

    public SongDetailController(PlayerViewListener listener, SongDetailView songDetailView, UserManager userManager,
                             SongManager songManager, PlaylistManager playlistManager) {
        this.listener = listener;
        this.songDetailView = songDetailView;
        this.songManager = songManager;
        this.userManager = userManager;
        this.playlistManager = playlistManager;
    }

    /**
     * Method that initializes the songDetailView by getting all current songs of the system
     * and passing the selected one to the table
     */
    public void initView(Song song) {
        // Get song selected
        currentSong = song;
        songDetailView.fillTable(currentSong);

        if (currentSong.getUser().equals(userManager.getCurrentUser())) {
            songDetailView.enableDeleteSongButton();
        } else {
            songDetailView.disableDeleteSongButton();
        }

        // Get all playlists
        try {
            allPlaylists = playlistManager.getAllPlaylists();
        } catch (PlaylistDAOException e) {
        }
        songDetailView.showPlaylists(allPlaylists);

        // Get lyrics
        songManager.getLyrics(this, currentSong.getTitle(), currentSong.getAuthor());
    }

    private final String DELETE_SONG_CONFIRMATION_MSG = "Are you sure you want to permanently delete the song?";
    private final String ERROR_DELETE_SONG_MSG = "Cannot delete this song!";
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case SongDetailView.BTN_ADD_PLAYLIST:
                // We get the playlist selected and add that song to the playlist
                int playlistIndex = songDetailView.getPlaylistIndexSelected();

                // If index is - 1 there is no playlist selected
                if (playlistIndex != -1) {
                    Playlist playlist = allPlaylists.get(playlistIndex);

                    try {
                        ArrayList<Integer> arrayListOrder = playlistManager.getPlaylistSongsOrder(playlist.getId());
                        int maxOrder=0;
                        if(arrayListOrder.size() > 0){
                            maxOrder = arrayListOrder.get(arrayListOrder.size()-1);
                        }

                        if (playlistManager.addSongToPlaylist(playlist.getId(), currentSong.getId(), maxOrder+1)) {
                            System.out.println("THE SONG HAS CORRECTLY ADDED");
                        }

                    } catch (PlaylistDAOException ex) {
                    }
                }
                break;

            case SongDetailView.BTN_PLAY_IMAGE:
                // We reproduce the song pressed
                ArrayList<Song> listSong = new ArrayList<>();
                listSong.add(currentSong);
                listener.playSong(listSong, 0);
                break;
            case SongDetailView.BTN_DELETE_SONG:
                if (songDetailView.confirmSongDeletion(DELETE_SONG_CONFIRMATION_MSG) == JOptionPane.YES_OPTION) {
                    if (songManager.songCanBeDeleted(currentSong.getId())) {
                        if (songManager.deleteSong(currentSong.getId())) {
                            listener.songWasDeleted();
                            listener.changeView(PlayerView.SONG_LIST_VIEW);
                        } else {
                            songDetailView.showErrorDialog("Unexpected error");
                        }
                    } else {
                        songDetailView.showErrorDialog(ERROR_DELETE_SONG_MSG);
                    }
                }
                break;
        }
    }

    @Override
    public void notifyLyricsDone(String lyrics) {
        songDetailView.setSongLyrics(lyrics);
    }

    @Override
    public void notifyError(String message) {
        songDetailView.lyricsError(message);
    }
}
