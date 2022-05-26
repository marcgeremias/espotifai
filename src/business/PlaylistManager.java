package business;

import business.entities.Playlist;
import business.entities.Song;
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

    public boolean addSongToPlaylist(int playlistID, int songID) throws PlaylistDAOException{
        return playlistDAO.addSongToPlaylist(playlistID, songID);
    }

    public void deleteSongFromPlaylist(int playlistID, int songID) throws PlaylistDAOException {
        playlistDAO.deleteSongFromPlaylist(playlistID, songID);
    }

    /**
     * Method that checks if the current jComboBox song is in the actual playlist
     * @return True in case the song is inside the playlist, otherwise false
     */
    public boolean isSongInsidePlaylist(int songId, ArrayList<Song> songs){
        if(songs != null){
            for (int i=0; i< songs.size(); i++) {
                if(songId == songs.get(i).getId()){
                    return true;
                }
            }
        }

        return false;
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