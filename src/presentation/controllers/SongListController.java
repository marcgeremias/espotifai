package presentation.controllers;

import business.PlaylistManager;
import business.SongManager;
import business.UserManager;
import business.entities.Song;
import presentation.views.SongListView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class SongListController implements KeyListener {

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
        songListView.filter();
    }

    public void initView() {
        currentSongs = songManager.getAllSongs();
        songListView.fillTable(currentSongs);
    }
}