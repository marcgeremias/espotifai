package business;

import business.entities.Playlist;
import persistence.PlaylistDAO;
import persistence.PlaylistDAOException;

import java.util.ArrayList;

public class PlaylistManager {
    private PlaylistDAO playlistDAO;

    public PlaylistManager(PlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }

    public ArrayList<Playlist> getCurrentUserPlaylists(String currentUser) throws PlaylistDAOException{
            ArrayList<Playlist> allPlaylists = playlistDAO.getPlaylistByUserID(currentUser);
            return allPlaylists == null ? new ArrayList<>() : allPlaylists;
    }

    public ArrayList<Playlist> getOtherUserPlaylists(String currentUser) {
        ArrayList<Playlist> allPlaylists = playlistDAO.getDifferentPlaylistByUserID(currentUser);
        return new ArrayList<>();
    }
}