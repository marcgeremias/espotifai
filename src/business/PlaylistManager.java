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

    /**
     * Method that gets all the playlists from the current user
     * @param currentUser the current user on the database
     * @return a playlist arraylist with all the playlists from the current user
     * @throws PlaylistDAOException
     */
    public ArrayList<Playlist> getCurrentUserPlaylists(String currentUser) throws PlaylistDAOException{
            ArrayList<Playlist> myPlaylists = playlistDAO.getPlaylistByUserID(currentUser);
            return myPlaylists == null ? new ArrayList<>() : myPlaylists;
    }

    /**
     * Method that gets all the playlists that are NOT from the current user
     * @param currentUser the current user on the database
     * @return a playlist arraylist with all the playlists that are NOT from the current user
     * @throws PlaylistDAOException
     */
    public ArrayList<Playlist> getOtherUserPlaylists(String currentUser) throws PlaylistDAOException{
        ArrayList<Playlist> otherPlaylists = playlistDAO.getDifferentPlaylistByUserID(currentUser);
        return otherPlaylists == null ? new ArrayList<>() : otherPlaylists;
    }

    /**
     * Method that gets all the playlists of the database
     * @return a playlist arraylist with all the playlists of the database
     * @throws PlaylistDAOException
     */
    public ArrayList<Playlist> getAllPlaylists() throws PlaylistDAOException{
        ArrayList<Playlist> allPlaylists = playlistDAO.getAllPlaylists();
        return allPlaylists == null ? new ArrayList<>() : allPlaylists;
    }

    public boolean addSongToPlaylist(int playlistID, int songID, int order) throws PlaylistDAOException{
        return playlistDAO.addSongToPlaylist(playlistID, songID, order);
    }

    /**
     * Method that deletes a song into a playlist
     * @param playlistID the id of the playlist
     * @param songID the id of the song
     * @return true if the song has correctly deleted to the database and false if not
     * @throws PlaylistDAOException
     */
    public void deleteSongFromPlaylist(int playlistID, int songID) throws PlaylistDAOException {
        playlistDAO.deleteSongFromPlaylist(playlistID, songID);
    }

    /**
     * Method that creates a playlist in the database
     * @param playlistName the name of the new playlist
     * @param currentUser the current user of the database
     * @return true if the song has correctly deleted to the database and false if not
     * @throws PlaylistDAOException
     */
    public void createPlaylist(String playlistName, String currentUser) throws PlaylistDAOException {
        Playlist newPlaylist = new Playlist(playlistName, currentUser);
        playlistDAO.createPlaylist(newPlaylist);
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

    public ArrayList<Integer> getPlaylistSongsOrder(int id) throws PlaylistDAOException {
        return playlistDAO.getSongOrderByPlaylistId(id);
    }

    /**
     * This method will determine the index in a given list given two parameters of a song
     * @param currentSongs list with all the songs
     * @param songName String containing song name
     * @param songAuthor String containing song author
     * @return index where the song is located in the list, -1 if song wasn't found
     */
    public int findSongIndex(ArrayList<Song> currentSongs, String songName, String songAuthor) {
        for (int i = 0; i < currentSongs.size(); i++){
            if (currentSongs.get(i).getTitle().equals(songName) && currentSongs.get(i).getAuthor().equals(songAuthor)) {
                return i;
            }
        }
        return -1;
    }

}