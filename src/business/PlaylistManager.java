package business;

import business.entities.Playlist;
import business.entities.Song;
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

    public void addSongToPlaylist(int playlistId, int songId) throws PlaylistDAOException {
        playlistDAO.addSongToPlaylist(playlistId,songId);
    }

    public void deleteSongFromPlaylist(int playlistID, int songID) throws PlaylistDAOException {
        playlistDAO.deleteSongFromPlaylist(playlistID, songID);
    }

    /**
     * Method that checks if the current jComboBox song is in the actual playlist
     * @return True in case the song is inside the playlist, otherwise false
     */
    public boolean isSongInsidePlaylist(int songId, ArrayList<Song> songs){
        for (int i=0; i< songs.size(); i++) {
            if(songId == songs.get(i).getId()){
                return true;
            }
        }
        return false;
    }

}