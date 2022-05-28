package business;

import business.entities.Playlist;
import business.entities.Song;
import persistence.PlaylistDAO;
import persistence.PlaylistDAOException;

import java.util.ArrayList;

public class PlaylistManager {
    private PlaylistDAO playlistDAO;

    //TODO: add comment
    public PlaylistManager(PlaylistDAO playlistDAO) {
        this.playlistDAO = playlistDAO;
    }

    public static final int PLAYLIST_ID_ATTRIBUTE_INDEX = 0;
    public static final int PLAYLIST_NAME_ATTRIBUTE_INDEX = 1;
    public static final int PLAYLIST_OWNER_ATTRIBUTE_INDEX = 2;
    public static final int PLAYLIST_SONGS_ATTRIBUTE_INDEX = 3;
    public static final String PLAYLIST_SONGS_SEPARATOR = ";";
    /**
     * Method that gets all the playlists from the current user
     * @param currentUser the current user on the database
     * @return a playlist arraylist with all the playlists from the current user
     * @throws PlaylistDAOException if there is an error getting the data.
     */
    public ArrayList<ArrayList<String>> getCurrentUserPlaylists(String currentUser) throws PlaylistDAOException{
        ArrayList<Playlist> myPlaylists = playlistDAO.getPlaylistByUserID(currentUser);

        if (myPlaylists == null) {
            return new ArrayList<>();
        }

        ArrayList<ArrayList<String>> myPlaylistsStr = new ArrayList<>();

        for (Playlist myPlaylist : myPlaylists) {
            ArrayList<String> attributes = new ArrayList<>();
            attributes.add(PLAYLIST_ID_ATTRIBUTE_INDEX, Integer.toString(myPlaylist.getId()));
            attributes.add(PLAYLIST_NAME_ATTRIBUTE_INDEX, myPlaylist.getName());
            attributes.add(PLAYLIST_OWNER_ATTRIBUTE_INDEX, myPlaylist.getOwner());

            if (myPlaylist.getSongs().size() > 0) {
                StringBuilder songs = new StringBuilder(Integer.toString(myPlaylist.getSongs().get(0)));

                for (int j = 1; j < myPlaylist.getSongs().size(); j++) {
                    songs.append(PLAYLIST_SONGS_SEPARATOR).append(myPlaylist.getSongs().get(j));
                }

                attributes.add(PLAYLIST_SONGS_ATTRIBUTE_INDEX, songs.toString());
            }

            myPlaylistsStr.add(attributes);
        }

        return myPlaylistsStr;
    }

    /**
     * Method that gets all the playlists that are NOT from the current user
     * @param currentUser the current user on the database
     * @return a playlist arraylist with all the playlists that are NOT from the current user
     * @throws PlaylistDAOException if there is an error getting the data.
     */
    public ArrayList<ArrayList<String>> getOtherUserPlaylists(String currentUser) throws PlaylistDAOException{
        ArrayList<Playlist> otherPlaylists = playlistDAO.getDifferentPlaylistByUserID(currentUser);

        if (otherPlaylists == null) {
            return new ArrayList<>();
        }

        ArrayList<ArrayList<String>> otherPlaylistsStr = new ArrayList<>();

        for (Playlist otherPlaylist : otherPlaylists) {
            ArrayList<String> attributes = new ArrayList<>();
            attributes.add(PLAYLIST_ID_ATTRIBUTE_INDEX, Integer.toString(otherPlaylist.getId()));
            attributes.add(PLAYLIST_NAME_ATTRIBUTE_INDEX, otherPlaylist.getName());
            attributes.add(PLAYLIST_OWNER_ATTRIBUTE_INDEX, otherPlaylist.getOwner());

            if (otherPlaylist.getSongs().size() > 0) {
                StringBuilder songs = new StringBuilder(Integer.toString(otherPlaylist.getSongs().get(0)));

                for (int j = 1; j < otherPlaylist.getSongs().size(); j++) {
                    songs.append(PLAYLIST_SONGS_SEPARATOR).append(otherPlaylist.getSongs().get(j));
                }

                attributes.add(PLAYLIST_SONGS_ATTRIBUTE_INDEX, songs.toString());
            }

            otherPlaylistsStr.add(attributes);
        }

        return otherPlaylistsStr;
    }

    /**
     * Method that gets all the playlists of the database
     * @return a playlist arraylist with all the playlists of the database
     * @throws PlaylistDAOException if there is an error getting the data.
     */
    public ArrayList<Playlist> getAllPlaylists() throws PlaylistDAOException{
        ArrayList<Playlist> allPlaylists = playlistDAO.getAllPlaylists();
        return allPlaylists == null ? new ArrayList<>() : allPlaylists;
    }

    /**
     * Method that gets all the adds a song into a playlist.
     * @return true if is added successful, otherwise false.
     * @throws PlaylistDAOException if there is an error adding the song.
     */
    public boolean addSongToPlaylist(int playlistID, int songID, int order) throws PlaylistDAOException{
        return playlistDAO.addSongToPlaylist(playlistID, songID, order);
    }

    /**
     * Method that deletes a song into a playlist
     * @param playlistID the id of the playlist
     * @param songID the id of the song
     * @return true if the song has correctly deleted to the database and false if not
     * @throws PlaylistDAOException if there is an error deleting the song from the playlist.
     */
    public void deleteSongFromPlaylist(int playlistID, int songID) throws PlaylistDAOException {
        playlistDAO.deleteSongFromPlaylist(playlistID, songID);
    }

    /**
     * Method that creates a playlist in the database
     * @param playlistName the name of the new playlist
     * @param currentUser the current user of the database
     * @return true if the song has correctly deleted to the database and false if not
     * @throws PlaylistDAOException if there is an error creating the playlist.
     */
    public void createPlaylist(String playlistName, String currentUser) throws PlaylistDAOException {
        Playlist newPlaylist = new Playlist(playlistName, currentUser);
        playlistDAO.createPlaylist(newPlaylist);
    }

    /**
     * Method that checks if the current jComboBox song is in the actual playlist
     * @return True in case the song is inside the playlist, otherwise false
     */
    public boolean isSongInsidePlaylist(int songId, ArrayList<ArrayList<String>> songs){
        if(songs != null){
            for (ArrayList<String> song : songs) {
                if (songId == Integer.parseInt(song.get(SongManager.SONG_ID_ATTRIBUTE_INDEX))) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Method that gets the songs order from a playlist
     * @param id unique playlistId
     * @return Arraylist with the playlist order songs.
     * @throws PlaylistDAOException
     */
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
    public int findSongIndex(ArrayList<ArrayList<String>> currentSongs, String songName, String songAuthor) {
        for (int i = 0; i < currentSongs.size(); i++){
            if (currentSongs.get(i).get(SongManager.SONG_TITLE_ATTRIBUTE_INDEX).equals(songName)
                    && currentSongs.get(i).get(SongManager.SONG_AUTHOR_ATTRIBUTE_INDEX).equals(songAuthor)) {
                return i;
            }
        }
        return -1;
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

    //TODO: add comment
    public void swapSongsOrder(int idPlaylist, int idSong1, int idSong2) throws PlaylistDAOException {
        playlistDAO.swapSongsOrder(idPlaylist,idSong1,idSong2);
    }
}