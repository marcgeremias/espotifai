package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import persistence.PlaylistDAOException;
import presentation.views.PlayerView;
import presentation.views.SongDetailView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Public controller to control the details of a {@link business.entities.Song}
 */
public class SongDetailController implements ActionListener, LyricsListener {
    private PlayerViewListener listener;
    private SongDetailView songDetailView;
    private SongManager songManager;
    private UserManager userManager;
    private PlaylistManager playlistManager;
    private ArrayList<String> currentSong;
    private ArrayList<ArrayList<String>> allPlaylists;

    /**
     * Creates an instance of SongDetailController
     * @param listener an instance of PlayerViewListener
     * @param songDetailView an instance of SongDetailView
     * @param userManager an instance of UserManager
     * @param songManager an instance of SongManager
     * @param playlistManager an instance of PlaylistManager
     */
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
    public void initView(ArrayList<String> song) {
        // Get song selected
        currentSong = song;
        songDetailView.fillTable(currentSong);

        if (currentSong.get(SongManager.SONG_USER_ATTRIBUTE_INDEX).equals(userManager.getCurrentUser())) {
            songDetailView.enableDeleteSongButton();
        } else {
            songDetailView.disableDeleteSongButton();
        }

        // Get all playlists
        try {
            allPlaylists = playlistManager.getCurrentUserPlaylists(userManager.getCurrentUser());
        } catch (PlaylistDAOException e) {
        }
        songDetailView.showPlaylists(allPlaylists);

        // Get lyrics
        songManager.getLyrics(
                this,
                currentSong.get(SongManager.SONG_TITLE_ATTRIBUTE_INDEX),
                currentSong.get(SongManager.SONG_AUTHOR_ATTRIBUTE_INDEX)
        );
    }

    private final String DELETE_SONG_CONFIRMATION_MSG = "Are you sure you want to permanently delete the song?";
    private final String ERROR_DELETE_SONG_MSG = "Cannot delete this song!";

    /**
     * Decides which action to execute
     * @param e an instance of ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case SongDetailView.BTN_ADD_PLAYLIST:
                // We get the playlist selected and add that song to the playlist
                int playlistIndex = songDetailView.getPlaylistIndexSelected();

                // If index is - 1 there is no playlist selected
                if (playlistIndex != -1) {
                    ArrayList<String> playlist = allPlaylists.get(playlistIndex);
                    int playlistID = Integer.parseInt(playlist.get(PlaylistManager.PLAYLIST_ID_ATTRIBUTE_INDEX));

                    try {
                        ArrayList<Integer> arrayListOrder = playlistManager.getPlaylistSongsOrder(playlistID);
                        int maxOrder = 0;

                        if(arrayListOrder.size() > 0){
                            maxOrder = arrayListOrder.get(arrayListOrder.size()-1);
                        }

                        if (playlistManager.addSongToPlaylist(playlistID,
                                Integer.parseInt(currentSong.get(SongManager.SONG_ID_ATTRIBUTE_INDEX)),
                                maxOrder+1)) {
                            songDetailView.showSuccessDialog("Song was added to the playlist: " + playlist.get(PlaylistManager.PLAYLIST_NAME_ATTRIBUTE_INDEX));
                        }
                    } catch (PlaylistDAOException ex) {
                        songDetailView.showErrorDialog("Song already in playlist!");
                    }
                }
                break;

            case SongDetailView.BTN_PLAY_IMAGE:
                // We reproduce the song pressed
                ArrayList<ArrayList<String>> listSong = new ArrayList<>();
                listSong.add(currentSong);
                listener.playSong(listSong, 0);
                break;
            case SongDetailView.BTN_DELETE_SONG:
                if (songDetailView.confirmSongDeletion(DELETE_SONG_CONFIRMATION_MSG) == JOptionPane.YES_OPTION) {
                    if (songManager.songCanBeDeleted(Integer.parseInt(currentSong.get(SongManager.SONG_ID_ATTRIBUTE_INDEX)))) {
                        if (songManager.deleteSong(Integer.parseInt(currentSong.get(SongManager.SONG_ID_ATTRIBUTE_INDEX)))) {
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

    /**
     * Notifies that the lyrics are downloaded
     * @param lyrics a String containing the lyrics
     */
    @Override
    public void notifyLyricsDone(String lyrics) {
        songDetailView.setSongLyrics(lyrics);
    }

    /**
     * Notifies an error when downloading lyrics
     */
    @Override
    public void notifyError() {
        songDetailView.lyricsError();
    }
}