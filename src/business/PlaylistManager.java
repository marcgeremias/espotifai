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

    public ArrayList<Playlist> getOtherUserPlaylists(String currentUser) throws PlaylistDAOException{
        ArrayList<Playlist> otherPlaylists = playlistDAO.getDifferentPlaylistByUserID(currentUser);
        return otherPlaylists == null ? new ArrayList<>() : otherPlaylists;
    }

    /**
     * Deletes the song from all the playlists which contain it
     * @param song an integer representing the song to delete
     */
    public void removeSongFromPlaylists(int song) {
        try {
            ArrayList<Playlist> playlists = playlistDAO.getAllPlaylists();
            for (Playlist p : playlists) {
                if (p.containsSong(song)) {
                    p.removeSong(song);
                    playlistDAO.updatePlaylist(p);
                }
            }
        } catch (PlaylistDAOException e) {
            //
        }
    }
}