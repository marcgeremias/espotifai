package persistence;

import business.entities.Playlist;

import java.util.ArrayList;

/**
 * Public interface that manages {@link Playlist} persistence in the system.
 * @see Playlist
 */
public interface PlaylistDAO {

    /**
     * This method will save the instance of {@link Playlist} given on the storage system. <b>Note</b> that this
     * function will no check if the playlist already exists with the same title and owner. Multiple playlists with
     * the same name can be created, it's the user responsibility.
     * @param playlist instance of {@link Playlist} with the values to persist.
     * @return (1) true if the playlist is created correctly or (2) false if it fails to persist.
     * @throws Exception if there is an error storing the data.
     */
    boolean createPlaylist(Playlist playlist) throws Exception;

    /**
     * This method will search the system for a playlist stored with the matching identifier given in parameters.
     * @param playlistID unique identifier for the playlist
     * @param userDAO DataAccessObject of {@link business.entities.User} to reconstruct playlist object.
     * @param songDAO DataAccessObject of {@link business.entities.Song} to reconstruct playlist object.
     * @return (1) instance of {@link Playlist} or (2) <b>null</b>.
     * @throws Exception if there is an error storing the data.
     */
    Playlist getPlaylistByID(int playlistID, UserDAO userDAO, SongDAO songDAO) throws Exception;

    /**
     * This method will return all playlists stored in the system.
     * @param userDAO DataAccessObject of {@link business.entities.User} to reconstruct playlist object.
     * @param songDAO DataAccessObject of {@link business.entities.Song} to reconstruct playlist object.
     * @return (1) List of {@link Playlist} or <b>null</b>
     * @throws Exception if there is an error storing the data.
     */
    ArrayList<Playlist> getAllPlaylists(UserDAO userDAO, SongDAO songDAO) throws Exception;

    /**
     * This method will search all the matches in the storage system given the title of the album. If the title
     * slightly matches the value inputed so far it will also include the playlist.
     * For example:
     *  Playlist name: Mambo
     *  Title = "a" will return playlist Mambo
     *  Title = "ma" will return playlist Mambo alos
     *  And so on...
     * @param title String containing the filter to search the playlist.
     * @param userDAO DataAccessObject of {@link business.entities.User} to reconstruct playlist object.
     * @param songDAO DataAccessObject of {@link business.entities.Song} to reconstruct playlist object.
     * @return (1) List of {@link Playlist} or <b>null</b>
     * @throws Exception if there is an error storing the data.
     */
    ArrayList<Playlist> getPlaylistsByTitle(String title, UserDAO userDAO, SongDAO songDAO) throws Exception;

    /**
     * This method will create a link between a song and a playlist. <b>Note</b> that the same song can be added
     * multiple times to the playlist, so it's the interface responsibility to make sure that a song is not added
     * twice unless user wishes to do so.
     * @param playlistID unique identifier for the playlist
     * @param songID unique identifier for the song
     * @return (1) true if the song is added correctly to the playlist, (2) false otherwise
     * @throws Exception if there is an error storing the data.
     */
    boolean addSongToPlaylist(int playlistID, int songID) throws Exception;

    /**
     * This method will delete the link between the song and the playlist given its unique identifiers.
     * @param playlistID unique identifier for the playlist
     * @param songID unique identifier for the song
     * @return (1) true if the song is deleted correctly from the playlist, (2) false otherwise.
     * @throws Exception if there is an error storing the data.
     */
    boolean deleteSongFromPlaylist(int playlistID, int songID) throws Exception;

    /**
     * This method will replace the existing playlist with the new values. <b>Note</b> that ALL values will
     * be overwritten so its important to make sure that the values we do not wish to change remain the same.
     * @param playlist instance of {@link Playlist} containing the new values to persist
     * @return (1) true if the playlist is updated correctly, (2) false otherwise.
     * @throws Exception if there is an error storing the data.
     */
    boolean updatePlaylist(Playlist playlist) throws Exception;

    /**
     * This method will delete the playlist from the storage system given its unique identifier.
     * @param playlistID unique identifier of playlist.
     * @return (1) true if the playlist is deleted correctly, (2) false otherwise.
     * @throws Exception if there is an error storing the data.
     */
    boolean deletePlaylist(int playlistID) throws Exception;

}
