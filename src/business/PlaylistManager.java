package business;

import persistence.PlaylistDAO;

import java.util.ArrayList;

public class PlaylistManager {
    private PlaylistDAO playlistDAO;

    public PlaylistManager(PlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }

    public ArrayList<String> getAllPlaylists() {
        return new ArrayList<>();
    }
}