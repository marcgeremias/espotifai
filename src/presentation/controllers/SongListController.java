package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import business.entities.Song;
import presentation.views.PlayerView;
import presentation.views.SongListView;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Timer;

public class SongListController implements KeyListener, MouseListener {

    private SongListView songListView;
    private PlayerViewListener listener;
    private UserManager userManager;
    private SongManager songManager;
    private PlaylistManager playlistManager;
    private ArrayList<Song> currentSongs;
    private int songNum;
    private long pressTime;

    public SongListController(PlayerViewListener listener, SongListView songListView, UserManager userManager,
                              SongManager songManager, PlaylistManager playlistManager) {
        this.listener = listener;
        this.songListView = songListView;
        this.userManager = userManager;
        this.songManager = songManager;
        this.playlistManager = playlistManager;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

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

    @Override
    public void mouseClicked(MouseEvent e) {
        // Single click
        songNum = songListView.getSongValue();

        int tableCol = songListView.getTableCol();
        int tableRow = songListView.getTableRow();
        System.out.println("C:" + tableCol + " R:" + tableRow);
        if (tableCol == 0) {
            //pressTime = System.currentTimeMillis();
            System.out.println("START REPRODUCING SONG");
            System.out.println(songNum);
            listener.playSong(currentSongs, songNum);
            //reproduce song
        } else if (tableCol > 0){
            listener.showSongDetails(currentSongs.get(songListView.getTableRow()));
        }
    }

    public int getSongNum() {
        return songNum;
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