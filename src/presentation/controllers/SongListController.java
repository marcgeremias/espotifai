package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import business.entities.Song;
import presentation.views.SongListView;

import java.awt.event.*;
import java.util.ArrayList;

/**
 * Public controller that controls a list of all the songs in the system
 */
public class SongListController implements KeyListener, MouseListener {

    private SongListView songListView;
    private PlayerViewListener listener;
    private SongManager songManager;
    private PlaylistManager playlistManager;
    private ArrayList<ArrayList<String>> currentSongs;

    /**
     * Creates an instance of SongListController
     * @param listener an instance of PlayerViewListener
     * @param songListView an instance of SongListView
     * @param songManager an instance of SongManager
     * @param playlistManager an instance of PlaylistManager
     */
    public SongListController(PlayerViewListener listener, SongListView songListView,
                              SongManager songManager, PlaylistManager playlistManager) {
        this.listener = listener;
        this.songListView = songListView;
        this.songManager = songManager;
        this.playlistManager = playlistManager;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    /**
     * Applies a filter depending on the pressed key
     * @param e an instance of KeyEvent
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // We filter the view every time a key is typed
        songListView.filter();
    }

    /**
     * Method that initializes the songListView by getting all current songs of the system
     * and passing them to the JTable of all songs in the system
     */
    public void initView() {
        currentSongs = songManager.getAllSongs();
        songListView.fillTable(currentSongs);
    }

    /**
     * Either plays the song or shows its details when clicked
     * @param e an instance of MouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // Single click
        if (songListView.getTableRow() == -1) return;
        int tableCol = songListView.getTableCol();
        int songIndex = findSongIndexBySongAttribute(songListView.getTableRow());
        if (tableCol == 0) {
            //pressTime = System.currentTimeMillis();
            listener.playSong(currentSongs, songIndex);
            //reproduce song
        } else if (tableCol > 0){
            listener.showSongDetails(currentSongs.get(songIndex));
        }
    }

    /*
     * Method that calculates the real index of the song by the song attribute
     */
    private int findSongIndexBySongAttribute(int row){
        String songName = songListView.getTableValue(row, 0);
        String songAuthor = songListView.getTableValue(row, 3);
        return playlistManager.findSongIndex(currentSongs, songName, songAuthor);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}