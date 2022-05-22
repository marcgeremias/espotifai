package business;

import business.entities.Playlist;
import persistence.PlaylistDAO;
import persistence.PlaylistDAOException;

import java.util.ArrayList;

import java.util.ArrayList;

public class PlaylistManager {
    private PlaylistDAO playlistDAO;

    public PlaylistManager(PlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }

    public ArrayList<Playlist> getCurrentUserPlaylists(String currentUser) throws PlaylistDAOException{
            ArrayList<Playlist> myPlaylists = playlistDAO.getPlaylistByUserID(currentUser);
            return myPlaylists == null ? new ArrayList<>() : myPlaylists;
    }

    public ArrayList<Playlist> getOtherUserPlaylists(String currentUser) throws PlaylistDAOException{
        ArrayList<Playlist> otherPlaylists = playlistDAO.getDifferentPlaylistByUserID(currentUser);
        return otherPlaylists == null ? new ArrayList<>() : otherPlaylists;
    }

    public ArrayList<Playlist> getAllPlaylists() throws PlaylistDAOException{
        ArrayList<Playlist> allPlaylists = playlistDAO.getAllPlaylists();
        return allPlaylists == null ? new ArrayList<>() : allPlaylists;
    }
}