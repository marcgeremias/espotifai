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
    public void initView(Song song) {
        // Get song selected
        currentSong = song;
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
        songManager.getLyrics(this, currentSong.getTitle(), currentSong.getAuthor());

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
                System.out.println("REPRODUCE MUSICCC");
                ArrayList<Song> listSong = new ArrayList<>();
                listSong.add(currentSong);
                listener.playSong(listSong, 0);
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
