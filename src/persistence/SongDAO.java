package persistence;

import business.entities.Genre;
import business.entities.Song;

import java.util.ArrayList;

/**
 * Public interface that manages {@link Song} persistence in the system.
 * @see Song
 */
public interface SongDAO {

    /**
     * This method stores an instance of {@link Song} passed in parameters to the storage system. <b>Note</b>
     * that there is no control with adding a song equal to another one. Unique identifiers of a song
     * are generated everytime a song is persisted therefore if we add the same song twice it will be treated
     * as diferent songs.
     * @param song instance of {@link Song} with the values to persist.
     * @return (1) true if the song is added correctly or (2) false if there is an error.
     * @throws Exception if there is an error accessing the database.
     */
    boolean createSong(Song song) throws Exception;

    /**
     * This method searched the storage system and returns a instance of {@link Song} if the values match.
     * @param songID unique identifier of the {@link Song} instance
     * @param userDAO DataAccessObject of {@link business.entities.User} to reconstruct song object.
     * @return (1) Instance of {@link Song} if the identifier matches with stored values in the system or
     * (2) null otherwise
     * @throws Exception if there is an error accessing the database.
     */
    Song getSongByID(int songID, UserDAO userDAO) throws Exception;

    /**
     * This method will return all the songs that are part of a playlist given the playlist ID.
     * @param playlistID {@link business.entities.Playlist} unique identifier
     * @param userDAO DataAccessObject of {@link business.entities.User} to reconstruct song object.
     * @return (1) List of {@link Song} if the values matches any song in the system, (2) null otherwise.
     * @throws Exception if there is an error accessing the database.
     */
    ArrayList<Song> getSongsByPlaylistID(int playlistID, UserDAO userDAO) throws Exception;

    /**
     * This method will return all the songs that were added by a user given its unique identifier.
     * @param userID {@link business.entities.User} unique identifier
     * @param userDAO DataAccessObject of {@link business.entities.User} to reconstruct song object.
     * @return (1) List of {@link Song} if the values matches any song in the system, (2) null otherwise.
     * @throws Exception if there is an error accessing the database.
     */
    ArrayList<Song> getSongsByUserID(String userID, UserDAO userDAO) throws Exception;

    /**
     * This method will return all the songs that belong to an author given its name.
     * @param authorName String containing the name of the author to filter the songs.
     * @param userDAO DataAccessObject of {@link business.entities.User} to reconstruct song object.
     * @return (1) List of {@link Song} if the values matches any song in the system, (2) null otherwise.
     * @throws Exception if there is an error accessing the database.
     */
    ArrayList<Song> getSongsByAuthorName(String authorName, UserDAO userDAO) throws Exception;

    /**
     * This method will return all the songs that have the same title. <b>Note</b> that songs can have the same
     * name and be different.
     * @param title String containing the title of the song.
     * @param userDAO DataAccessObject of {@link business.entities.User} to reconstruct song object.
     * @return (1) List of {@link Song} if the values matches any song in the system, (2) null otherwise.
     * @throws Exception if there is an error accessing the database.
     */
    ArrayList<Song> getSongsByTitle(String title, UserDAO userDAO) throws Exception;

    /**
     * This method will return all the songs listed under the same album name.
     * @param album String containing the album of the song
     * @param userDAO DataAccessObject of {@link business.entities.User} to reconstruct song object.
     * @return (1) List of {@link Song} if the values matches any song in the system, (2) null otherwise.
     * @throws Exception if there is an error accessing the database.
     */
    ArrayList<Song> getSongsByAlbum(String album, UserDAO userDAO) throws Exception;

    /**
     * This method will return all the songs listed under the same genre.
     * @param genre instance of {@link Genre} to filter the songs.
     * @param userDAO DataAccessObject of {@link business.entities.User} to reconstruct song object.
     * @return (1) List of {@link Song} if the values matches any song in the system, (2) null otherwise.
     * @throws Exception if there is an error accessing the database.
     */
    ArrayList<Song> getSongsByGenre(Genre genre, UserDAO userDAO) throws Exception;

    /**
     * This method will search in the storage system any song that matches with the song title, album, author
     * or genre given the <b>key</b> parameter.
     * @param key String containing a value used to filter the search.
     * @param userDAO DataAccessObject of {@link business.entities.User} to reconstruct song object.
     * @return (1) List of {@link Song} if the values matches any song in the system, (2) null otherwise.
     * @throws Exception if there is an error accessing the database.
     */
    ArrayList<Song> getSongsByKeyword(String key, UserDAO userDAO) throws Exception;

    /**
     * This method will update the values of a song given its unique identifier. <b>Note</b> that ALL values will
     * be replaced from the desired song, so it's important to make sure that other values of the song that we do not
     * wish to update remain the same. All information stored in the system regarding this song will be overwritten.
     * @param song instance of {@link Song} containing the new values to persist in the system.
     * @return (1) true if the song has been updated successfully or (2) false if the identifier doesn't match any song.
     * @throws Exception if there is an error accessing the database.
     */
    boolean updateSong(Song song) throws Exception;

    /**
     * This method will delete a song from the system provided its unique identifier.
     * @param songID unique identifier for the song we wish to delete
     * @return (1) true if the song has been deleted successfully or (2) false if the identifier doesn't match any song.
     * @throws Exception if there is an error accessing the database.
     */
    boolean deleteSong(int songID) throws Exception;



}
