package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import business.entities.Song;
import presentation.views.PlayerView;
import presentation.views.SongListView;

import java.awt.event.*;
import java.util.ArrayList;

public class SongListController implements KeyListener, MouseListener {

    private SongListView songListView;
    private PlayerViewListener listener;
    private UserManager userManager;
    private SongManager songManager;
    private PlaylistManager playlistManager;
    private ArrayList<Song> currentSongs;

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
        if (e.getClickCount() == 1) {
            System.out.println("START REPRODUCING SONG");
            //reproduce song
        } else {
            // Double or more clicks
            if (e.getClickCount() == 2) {
                //Access to detail songs view
                listener.changeView(PlayerView.SONG_DETAIL_VIEW);
            }
        }
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